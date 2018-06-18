package com.braunster.chatsdk.object;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.network.BNetworkManager;

import org.apache.commons.lang3.StringUtils;

public class SaveIndexDetailsTextWatcher implements TextWatcher {

    private static final long INDEX_DELAY_DEFAULT = 500;

    private String metaKey;

    /** Contain the string that was last typed.*/
    private Editable editable;

    public SaveIndexDetailsTextWatcher(String metaKey) {
        this.metaKey = metaKey;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editable = s;
        handler.removeCallbacks(indexRunnable);
        handler.postDelayed(indexRunnable, INDEX_DELAY_DEFAULT);
    }

    private Handler handler = new Handler(Looper.getMainLooper());

    private Runnable indexRunnable = new Runnable() {
        @Override
        public void run() {

            if (StringUtils.isBlank(editable.toString()))
                return;

            BUser user = BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel();
            String metadata = user.metaStringForKey(metaKey);

            if (StringUtils.isNotBlank(metadata) && metadata.equals(editable.toString()))
                return;

            user.setMetadataString(metaKey, editable.toString());

            BNetworkManager.sharedManager().getNetworkAdapter().pushUser();
        }
    };

}
