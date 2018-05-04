package com.braunster.chatsdk.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braunster.chatsdk.R;
import com.braunster.chatsdk.Utils.Debug;
import com.braunster.chatsdk.fragments.abstracted.ChatSDKAbstractConversationsFragment;

public class ChatSDKConversationsFragment extends ChatSDKAbstractConversationsFragment {

    public static ChatSDKConversationsFragment newInstance() {
        ChatSDKConversationsFragment f = new ChatSDKConversationsFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(inflateMenuItems);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mainView = inflater.inflate(R.layout.chat_sdk_activity_threads, container, false);

        initViews();

        loadDataOnBackground();

        return mainView;
    }

}
