package com.braunster.chatsdk.interfaces;

import com.braunster.chatsdk.dao.BThread;
import com.firebase.geofire.GeoLocation;

/**
 * Created by khale on 28-Dec-17.
 */

public interface GeoThreadInterface {

    boolean threadAdded(BThread thread, GeoLocation location);
    boolean threadRemoved(BThread thread);
    boolean setCurrentUserGeoLocation(GeoLocation location);
    boolean setState(int stringResId);
}
