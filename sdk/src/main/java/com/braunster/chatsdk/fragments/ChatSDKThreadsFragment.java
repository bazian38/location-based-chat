package com.braunster.chatsdk.fragments;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.braunster.chatsdk.R;
import com.braunster.chatsdk.Utils.Debug;
import com.braunster.chatsdk.Utils.DialogUtils;
import com.braunster.chatsdk.adapter.ChatSDKThreadsListAdapter;
import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.entities.Entity;
import com.braunster.chatsdk.interfaces.GeoThreadInterface;
import com.braunster.chatsdk.network.BNetworkManager;
import com.braunster.chatsdk.object.BError;
import com.braunster.chatsdk.object.ChatSDKThreadPool;
import com.braunster.chatsdk.object.UIUpdater;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.util.GeoUtils;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import timber.log.Timber;

public class ChatSDKThreadsFragment extends ChatSDKBaseFragment implements GeoThreadInterface {

    private static boolean DEBUG = Debug.ThreadsFragment;

    private ListView listThreads;
    private ChatSDKThreadsListAdapter adapter;
    private ProgressBar progressBar;
    private UIUpdater uiUpdater;
    private TextView noUsersTextView;

    private Map<BThread, GeoLocation> threadsLocationsMap;
    private GeoLocation currentUserGeoLocation = new GeoLocation(0.0, 0.0);;
    private boolean isVisibleToUser;


    public static ChatSDKThreadsFragment newInstance() {
        return new ChatSDKThreadsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        threadsLocationsMap = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init(inflater);

        loadDataOnBackground();

        return mainView;
    }

    private void init(LayoutInflater inflater){
        mainView = inflater.inflate(R.layout.chat_sdk_activity_threads, null);
        initViews();

        BNetworkManager.sharedManager().getNetworkAdapter().getGeoFireManager().setThreadDelegate(this);
        BNetworkManager.sharedManager().getNetworkAdapter().getGeoFireManager().startForThreads(getActivity().getApplicationContext(), getActivity());
    }

    @Override
    public void initViews() {
        listThreads = mainView.findViewById(R.id.list_threads);
        progressBar = mainView.findViewById(R.id.chat_sdk_progress_bar);
        noUsersTextView = mainView.findViewById(R.id.textView);
        initList();
    }

    private void initList(){
        adapter = new ChatSDKThreadsListAdapter(getActivity());
        listThreads.setAdapter(adapter);

        listThreads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startChatActivityForID(adapter.getItem(position).getId());
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();

        if (mainView == null)
            return;
        List a = BNetworkManager.sharedManager().getNetworkAdapter().threadItemsWithType(BThread.Type.Public, adapter.getItemMaker());
        a.addAll(BNetworkManager.sharedManager().getNetworkAdapter().threadItemsWithType(BThread.Type.PublicPrivate, adapter.getItemMaker()));
        adapter.setThreadItems(a);
    }

    @Override
    public void loadDataOnBackground() {
        super.loadDataOnBackground();

        if (mainView == null)
            return;

        final boolean isFirst;
        if (uiUpdater != null)
        {
            isFirst = false;
            uiUpdater.setKilled(true);
            ChatSDKThreadPool.getInstance().removeSchedule(uiUpdater);
        }
        else
        {
            isFirst = true;
        }

        final boolean noItems = adapter != null && adapter.getThreadItems().size() == 0;
        if (isFirst && noItems) {
            listThreads.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        uiUpdater = new UIUpdater() {
            @Override
            public void run() {

                if (isKilled() && !isFirst && noItems)
                    return;

                Message message = new Message();
                message.what = 1;
                List a = BNetworkManager.sharedManager().getNetworkAdapter().threadItemsWithType(BThread.Type.Public, adapter.getItemMaker());
                a.addAll(BNetworkManager.sharedManager().getNetworkAdapter().threadItemsWithType(BThread.Type.PublicPrivate, adapter.getItemMaker()));
                message.obj = a;

                handler.sendMessageAtFrontOfQueue(message);

                uiUpdater = null;
            }
        };

        ChatSDKThreadPool.getInstance().scheduleExecute(uiUpdater, noItems && isFirst ? 0 : isFirst ? 1 : 4);
    }

    @Override
    public void refreshForEntity(Entity entity) {
        super.refreshForEntity(entity);
        adapter.replaceOrAddItem((BThread) entity);
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case 1:
                    adapter.setThreadItems((List<ChatSDKThreadsListAdapter.ThreadListItem>) msg.obj);
                    progressBar.setVisibility(View.GONE);
                    listThreads.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item =
                menu.add(Menu.NONE, R.id.action_chat_sdk_add, 10, getString(R.string.public_thread_fragment_add_item_text));
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setIcon(R.drawable.ic_plus);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Cant use switch in the library*/
        int id = item.getItemId();

        if (id == R.id.action_chat_sdk_add)
        {
            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getActivity());
            alert.setView(input);
            alert.setTitle("Create New Chat Room With Name:");
            alert.setPositiveButton("Public", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    final String s = input.getText().toString().trim();
                    showProgDialog(getString(R.string.add_public_chat_dialog_progress_message));
                    BNetworkManager.sharedManager().getNetworkAdapter().createPublicThreadWithName(s)
                            .done(new DoneCallback<BThread>() {
                                @Override
                                public void onDone(final BThread thread) {
                                    // Add the current user to the thread.
                                    getNetworkAdapter().addUsersToThread(thread, BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel())
                                            .done(new DoneCallback<BThread>() {
                                                @Override
                                                public void onDone(BThread thread) {

                                                    BNetworkManager.sharedManager().getNetworkAdapter().getGeoFireManager().setThreadLocation(
                                                            getActivity().getApplicationContext(), getActivity(), thread);
                                                    dismissProgDialog();
                                                    adapter.addRow(thread);
                                                    showToast( getString(R.string.add_public_chat_dialog_toast_success_before_thread_name)
                                                            + s
                                                            + getString(R.string.add_public_chat_dialog_toast_success_after_thread_name) ) ;
                                                }
                                            });
                                }
                            })
                            .fail(new FailCallback<BError>() {
                                @Override
                                public void onFail(BError bError) {
                                    showAlertToast(getString(R.string.add_public_chat_dialog_toast_error_before_thread_name) + s);

                                    if (DEBUG) Timber.e("Error: %s", bError.message);

                                    dismissProgDialog();
                                }
                            });
                }
            });

            alert.setNegativeButton("Private", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    final String s = input.getText().toString().trim();
                    showProgDialog(getString(R.string.add_public_chat_dialog_progress_message));
                    BNetworkManager.sharedManager().getNetworkAdapter().createPublicPrivateThreadWithName(s)
                            .done(new DoneCallback<BThread>() {
                                @Override
                                public void onDone(final BThread thread) {
                                    // Add the current user to the thread.
                                    getNetworkAdapter().addUsersToThread(thread, BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel())
                                            .done(new DoneCallback<BThread>() {
                                                @Override
                                                public void onDone(BThread thread) {

                                                    BNetworkManager.sharedManager().getNetworkAdapter().getGeoFireManager().setThreadLocation(
                                                            getActivity().getApplicationContext(), getActivity(), thread);
                                                    dismissProgDialog();
                                                    adapter.addRow(thread);
                                                    showToast(getString(R.string.add_public_chat_dialog_toast_success_before_thread_name)
                                                            + s
                                                            + getString(R.string.add_public_chat_dialog_toast_success_after_thread_name));
                                                }
                                            });
                                }
                            })
                            .fail(new FailCallback<BError>() {
                                @Override
                                public void onFail(BError bError) {
                                    showAlertToast(getString(R.string.add_public_chat_dialog_toast_error_before_thread_name) + s);

                                    if (DEBUG) Timber.e("Error: %s", bError.message);

                                    dismissProgDialog();
                                }
                            });
                }
            });
            alert.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();

        BNetworkManager.sharedManager().getNetworkAdapter().getGeoFireManager().setThreadDelegate(this);
        BNetworkManager.sharedManager().getNetworkAdapter().getGeoFireManager().startForThreads(getActivity().getApplicationContext(), getActivity());

        if (isVisibleToUser)
            updateList(getSortedThreadsDistanceMap());

/*        BatchedEvent batchedEvents = new BatchedEvent(APP_EVENT_TAG, "", Event.Type.AppEvent, handler);

        batchedEvents.setBatchedAction(Event.Type.AppEvent, 3000, new Batcher.BatchedAction<String>() {
            @Override
            public void triggered(List<String> list) {
                loadDataOnBackground();
            }
        });

        getNetworkAdapter().getEventManager().removeEventByTag(APP_EVENT_TAG);
        getNetworkAdapter().getEventManager().addEvent(batchedEvents);*/
    }

    @Override
    public boolean threadAdded(BThread thread, GeoLocation location) {
        threadsLocationsMap.put(thread, location);
        updateList(getSortedThreadsDistanceMap());
        return true;
    }

    private void updateList(Map<BThread, Double> threadsDistanceMap) {
        if(isVisibleToUser)
        {
            if (adapter != null) {
                adapter.clear();
            }

            // Set the display state to searching if the user location has not been aquired yet
            if(currentUserGeoLocation.latitude == 0.0 && currentUserGeoLocation.longitude == 0.0)
            {
                setState(R.string.searching_nearby_threads);
            }
            else
            {
                // Set the display state to searching if there are no items in the locationsmap
                if (threadsLocationsMap == null || threadsLocationsMap.isEmpty())
                {
                    setState(R.string.no_nearby_threads);
                }
                else
                {
                    setState(R.string.show_list);

                    if (adapter != null)
                    {
                        for (BThread thread : threadsDistanceMap.keySet()) {
                            adapter.addRow(thread);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean threadRemoved(BThread thread) {
        threadsLocationsMap.remove(thread);
        updateList(getSortedThreadsDistanceMap());
        return true;
    }

    @Override
    public boolean setCurrentUserGeoLocation(GeoLocation location) {
        currentUserGeoLocation = location;
        updateList(getSortedThreadsDistanceMap());

        return true;
    }

    public boolean setState(int stringResId) {
        if (noUsersTextView != null) {
            noUsersTextView.setText(getResources().getString(stringResId));
        }

        if(stringResId == R.string.show_list) {
            noUsersTextView.setVisibility(View.INVISIBLE);
            listThreads.setVisibility(View.VISIBLE);
        } else {
            listThreads.setVisibility(View.INVISIBLE);
            noUsersTextView.setVisibility(View.VISIBLE);
        }

        return true;
    }

    public Map<BThread, Double> getSortedThreadsDistanceMap() {
        Map<BThread, Double> threadsDistanceMap = getThreadsDistanceMap();

        threadsDistanceMap = sortByComparator(threadsDistanceMap, true);

        return threadsDistanceMap;
    }

    public Map<BThread, Double> getThreadsDistanceMap() {

        Map<BThread, Double> threadsDistanceMap = new HashMap<>();
        ConcurrentHashMap<BThread, GeoLocation> threadsLocationMapLocal = new ConcurrentHashMap<>(threadsLocationsMap);

        for(BThread thread : threadsLocationMapLocal.keySet()) {
            GeoLocation location = threadsLocationMapLocal.get(thread);

            Double distance = GeoUtils.distance(currentUserGeoLocation, location);

            threadsDistanceMap.put(thread, distance);
        }

        return threadsDistanceMap;
    }

    private static Map<BThread, Double> sortByComparator(Map<BThread, Double> unsortMap, final boolean order)
    {
        List<Map.Entry<BThread, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<BThread, Double>>() {
            public int compare(Map.Entry<BThread, Double> o1,
                               Map.Entry<BThread, Double> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<BThread, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<BThread, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if (isVisibleToUser) {
            setState(R.string.searching_nearby_users);
            updateList(getSortedThreadsDistanceMap());
        }
    }
}