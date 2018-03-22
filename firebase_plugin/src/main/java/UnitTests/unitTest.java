package UnitTests;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.braunster.androidchatsdk.firebaseplugin.R;
import com.braunster.androidchatsdk.firebaseplugin.firebase.BChatcatNetworkAdapter;
import com.braunster.chatsdk.Utils.helper.ChatSDKUiHelper;
import com.braunster.chatsdk.network.AbstractNetworkAdapter;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BNetworkManager;
import com.google.firebase.FirebaseApp;

import org.jdeferred.DoneCallback;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    static private Context appContext;
    static private BChatcatNetworkAdapter adapter;

 /*   @Before
    public void initTests() {
        SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);
        SharedPreferences sharedPrefs = Mockito.mock(SharedPreferences.class);
        Context context = Mockito.mock(Context.class);

        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs);
        when(context.getSharedPreferences(anyString(), anyInt()).getString(anyString(), anyString())).thenReturn("");
        when(sharedPrefs.getString(anyString(), anyString())).thenReturn("");
        when(sharedPrefs.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(null);
        when(editor.commit()).thenReturn(true);

        //ChatSDKUiHelper.initDefault();
        //BNetworkManager.init(context);
        FirebaseApp.initializeApp(context);
        adapter = new BChatcatNetworkAdapter(context, true);

        // change database root
        BDefines.changeRootForTesting();
    }*/

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
