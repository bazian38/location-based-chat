package com.braunster.chatsdk.activities;

import android.os.Bundle;
import android.widget.AbsListView;

import com.braunster.chatsdk.R;
import com.braunster.chatsdk.Utils.Debug;
import com.braunster.chatsdk.activities.abstracted.ChatSDKAbstractChatActivity;

public class ChatSDKChatActivity extends ChatSDKAbstractChatActivity implements AbsListView.OnScrollListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_sdk_activity_chat);

        initViews();

        chatSDKChatHelper.checkIfWantToShare(getIntent());
    }
}
