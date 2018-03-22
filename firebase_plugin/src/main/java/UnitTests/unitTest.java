package UnitTests;

import android.content.Context;
import android.content.SharedPreferences;

import com.braunster.androidchatsdk.firebaseplugin.R;
import com.braunster.androidchatsdk.firebaseplugin.firebase.BChatcatNetworkAdapter;
import com.braunster.chatsdk.Utils.helper.ChatSDKUiHelper;
import com.braunster.chatsdk.network.AbstractNetworkAdapter;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;

import org.jdeferred.DoneCallback;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Alaa Agbaria on 29-Dec-17.
 * Testing the application adapter
 */

public class unitTest {

    private BChatcatNetworkAdapter adapter;

    @Before
    public void initTests() {
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        Context context = Mockito.mock(Context.class);

        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        when(context.getString(R.string.backendless_app_id)).thenReturn("1C9AB22A-D442-4D0C-FFB6-403D71FBA800");
        when(context.getString(R.string.backendless_secret_key)).thenReturn("DDCC204E-A921-127D-FFFC-B3EAF04B9000");
        when(context.getString(R.string.backendless_app_version)).thenReturn("v1");
        when(context.getSharedPreferences(anyString(), anyInt()).getString(anyString(), anyString())).thenReturn("");
        when(sharedPrefs.getString(anyString(), anyString())).thenReturn("");
        when(sharedPrefs.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(null);
        when(editor.commit()).thenReturn(true);

        ChatSDKUiHelper.initDefault();
        BNetworkManager.init(context);
        adapter = new BChatcatNetworkAdapter(context);

        // change database root
        BDefines.changeRootForTesting();
    }

    @Test
    public void test() {
        Map<String, Object> data = AbstractNetworkAdapter.getMap(
                new String[]{BDefines.Prefs.LoginTypeKey, BDefines.Prefs.LoginEmailKey, BDefines.Prefs.LoginPasswordKey},
                BDefines.BAccountType.Register, "agbaria2@post.bgu.ac.il", "123456");

        adapter.authenticateWithMap(data).done(new DoneCallback<Object>() {
            @Override
            public void onDone(Object result) {
                assertTrue((Boolean) result);
            }
        });
    }
}
