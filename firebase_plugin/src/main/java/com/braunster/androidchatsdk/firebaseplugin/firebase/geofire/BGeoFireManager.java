package com.braunster.androidchatsdk.firebaseplugin.firebase.geofire;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.braunster.androidchatsdk.firebaseplugin.R;
import com.braunster.androidchatsdk.firebaseplugin.firebase.FirebasePaths;
import com.braunster.androidchatsdk.firebaseplugin.firebase.wrappers.BThreadWrapper;
import com.braunster.androidchatsdk.firebaseplugin.firebase.wrappers.BUserWrapper;
import com.braunster.chatsdk.Utils.Debug;
import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.interfaces.GeoInterface;
import com.braunster.chatsdk.interfaces.GeoThreadInterface;
import com.braunster.chatsdk.network.AbstractGeoFireManager;
import com.braunster.chatsdk.network.BNetworkManager;
import com.google.firebase.database.DatabaseError;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;

import org.jdeferred.DoneCallback;

import java.util.List;

import timber.log.Timber;

public class BGeoFireManager extends AbstractGeoFireManager implements LocationListener {

    public static final int LOCATION_PERMISSION = 1;
    public GeoInterface delegate;
    public GeoThreadInterface threadDelegate;

    private static final String TAG = BGeoFireManager.class.getSimpleName();
    private static final boolean DEBUG = Debug.BGeoFireManager;

    private double bSearchRadius = 1000.0;
    private long bLocationUpdateTime = 5000;
    private float bMinDistance = 0.1f;
    private String currentProvider = "";

    private LocationManager locationManager = null;

    private boolean isUpdating;

    private static BGeoFireManager instance;
    private static Context context;
    private GeoQuery circleQuery = null;
    private GeoQuery threadsCircleQuery = null;

    public static BGeoFireManager sharedManager() {
        if (instance == null) {
            instance = new BGeoFireManager();
        }

        return instance;
    }

    @Override
    public void setGeoDelegate(GeoInterface geoDelegate) {
        delegate = geoDelegate;
    }

    @Override
    public void setThreadDelegate(GeoThreadInterface threadDelegate) {
        this.threadDelegate = threadDelegate;
    }

    @Override
    public void start(Context ctx, Activity activity) {
        context = ctx;

        if (DEBUG) Timber.v("init GeoFireManager");

        Log.d(TAG, "11");
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        } else {
            locationManager.removeUpdates(this);
        }

        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        currentProvider = locationManager.getBestProvider(crit, false);

        if (DEBUG) Timber.v("current provider: " + currentProvider);

        if (currentProvider != "") {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
                Log.d(TAG, "location permission needed");
                return;
            }
            locationManager.requestLocationUpdates(currentProvider, bLocationUpdateTime, bMinDistance, this);
            startUpdatingUserLocation();
            findNearbyUsersWithRadius(bSearchRadius);
        }

        if (currentProvider == "") {
            delegate.setState(R.string.location_disabled);
        } else {
            delegate.setState(R.string.searching_nearby_users);
        }
    }

    @Override
    public void startForThreads(Context ctx, Activity activity) {
        if (context == null)
            context = ctx;

        Log.d(TAG, "1");
        if (locationManager == null) {
            locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        } else {
            locationManager.removeUpdates(this);
        }

        Criteria crit = new Criteria();
        crit.setAccuracy(Criteria.ACCURACY_FINE);
        currentProvider = locationManager.getBestProvider(crit, false);

        if (currentProvider != "") {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
                Log.d(TAG, "location permission needed");
                return;
            }
            locationManager.requestLocationUpdates(currentProvider, bLocationUpdateTime, bMinDistance, this);
            findNearbyThreadsWithRadius(bSearchRadius);
        }
    }

    private void findNearbyThreadsWithRadius(double bSearchRadius) {
        Log.d(TAG, "2");
        if (getCurrentGeoLocation() == null) return;
        initThreadCircleQuery();
    }

    private void initThreadCircleQuery() {
        if (threadsCircleQuery == null) {
            GeoFire geoFire = new GeoFire(FirebasePaths.threadsLocationRef());
            threadsCircleQuery = geoFire.queryAtLocation(getCurrentGeoLocation(), bSearchRadius / 1000.0);
        } else {
            threadsCircleQuery.setCenter(getCurrentGeoLocation());
        }

        threadsCircleQuery.removeAllListeners();
        threadsCircleQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, final GeoLocation location) {
                BThreadWrapper.initWithEntityId(key).once().then(new DoneCallback<BThread>() {
                    @Override
                    public void onDone(BThread bThread) {
                        BThreadWrapper userWrapper = BThreadWrapper.initWithModel(bThread);
                        threadDelegate.threadAdded(userWrapper.getModel(), location);
                        Log.d(TAG, "new thread nearby " + bThread.displayName());
                    }
                });
            }

            @Override
            public void onKeyExited(String key) {

                BThreadWrapper threadWrapper = BThreadWrapper.initWithEntityId(key);
                threadDelegate.threadRemoved(threadWrapper.getModel());
                Log.d(TAG, "thread nearby out " + threadWrapper.getModel().displayName());
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    @Override
    public void setThreadLocation(Context ctx, Activity activity, BThread thread) {
        updateCurrentThreadLocation(thread);
    }

    public void findNearbyUsersWithRadius(double radiusInMetres) {
        Log.d(TAG, "22");
        if (getCurrentGeoLocation() == null) return;
        initCircleQuery();
    }

    private void initCircleQuery() {
        Log.d(TAG, "current location: " + getCurrentGeoLocation());
        final String userEntityID = BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel().getEntityID();

        if (circleQuery == null) {
            GeoFire geoFire = new GeoFire(FirebasePaths.locationRef());
            circleQuery = geoFire.queryAtLocation(getCurrentGeoLocation(), bSearchRadius / 1000.0);
        } else {
            circleQuery.setCenter(getCurrentGeoLocation());
        }

        circleQuery.removeAllListeners();
        circleQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String entityID, final GeoLocation location) {
                if (!entityID.equals(userEntityID)) {
                    BUserWrapper.initWithEntityId(entityID).once().then(new DoneCallback<BUser>() {
                        @Override
                        public void onDone(BUser bUser) {
                            BUserWrapper userWrapper = BUserWrapper.initWithModel(bUser);
                            delegate.userAdded(userWrapper.getModel(), location);
                            Log.d(TAG, "new nearby user " + bUser.getMetaEmail());
                        }
                    });
                }
            }

            @Override
            public void onKeyExited(String entityID) {
                if (!entityID.equals(userEntityID)) {
                    BUserWrapper userWrapper = BUserWrapper.initWithEntityId(entityID);
                    delegate.userRemoved(userWrapper.getModel());
                    Log.d(TAG, "nearby user out " + userWrapper.getModel().getMetaEmail());
                }
            }

            @Override
            public void onKeyMoved(String entityID, GeoLocation location) {
                if (!entityID.equals(userEntityID)) {
                    BUserWrapper userWrapper = BUserWrapper.initWithEntityId(entityID);
                    delegate.userMoved(userWrapper.getModel(), location);
                }
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void startUpdatingUserLocation() {
        updateCurrentUserLocation();

        isUpdating = true;
    }

    private void stopUpdatingUserLocation() {
        if (!isUpdating) return;

        isUpdating = false;
    }

    private GeoLocation getCurrentGeoLocation() {
        Location center = getCurrentUserLocation();

        if (center == null) return null;

        GeoLocation centerGeo = new GeoLocation(center.getLatitude(), center.getLongitude());

        return centerGeo;
    }

    private void updateCurrentUserLocation() {
        GeoLocation currentLocation = getCurrentGeoLocation();

        if (currentLocation == null) return;

        BUser currentUser = BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel();

        if (currentUser != null) {
            GeoFire geoFire = new GeoFire(FirebasePaths.locationRef());
            geoFire.setLocation(currentUser.getEntityID(), currentLocation);
        }

        delegate.setCurrentUserGeoLocation(currentLocation);
        threadDelegate.setCurrentUserGeoLocation(currentLocation);

        initCircleQuery();
        initThreadCircleQuery();
    }

    private void updateCurrentThreadLocation(BThread thread) {
        GeoLocation currentLocation = getCurrentGeoLocation();

        if (currentLocation == null) return;
        GeoFire geoFire = new GeoFire(FirebasePaths.threadsLocationRef());
        geoFire.setLocation(thread.getEntityID(), currentLocation);
    }

    public Location getCurrentUserLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling ActivityCompat#requestPermissions
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }

        return bestLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(DEBUG) Timber.e("Location changed: " + location.toString());

        if(isUpdating) {
            updateCurrentUserLocation();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if(DEBUG) Timber.v("status changed: " + provider + " " + status);
    }

    @Override
    public void onProviderDisabled(String provider) {
        if(DEBUG) Timber.e("Provider disabled");
        stopUpdatingUserLocation();
    }

    @Override
    public void onProviderEnabled(String provider) {
        if(DEBUG) Timber.e("Provider enabled");
        startUpdatingUserLocation();
    }
}
