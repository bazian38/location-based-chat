/*
package com.braunster.androidchatsdk.firebaseplugin.firebase.wrappers;


import android.support.annotation.NonNull;

import com.braunster.androidchatsdk.firebaseplugin.firebase.BFirebaseNetworkAdapter;
import com.braunster.androidchatsdk.firebaseplugin.firebase.FirebasePaths;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.braunster.chatsdk.network.AbstractNetworkAdapter;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.object.BError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import org.jdeferred.Deferred;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

*/
/**
 * Created by khale on 14-Jan-18.
 *
 *//*


public class BFirebaseNetworkLogic {

    protected enum AuthStatus{
        IDLE {
            @Override
            public String toString() {
                return "Idle";
            }
        },
        AUTH_WITH_MAP{
            @Override
            public String toString() {
                return "Auth with map";
            }
        },
        HANDLING_F_USER{
            @Override
            public String toString() {
                return "Handling F user";
            }
        },
        UPDATING_USER{
            @Override
            public String toString() {
                return "Updating user";
            }
        },
        PUSHING_USER{
            @Override
            public String toString() {
                return "Pushing user";
            }
        },
        CHECKING_IF_AUTH{
            @Override
            public String toString() {
                return "Checking if Authenticated";
            }
        }
    }

    private AuthStatus authingStatus = AuthStatus.IDLE;

    public boolean isAuthing(){
        return authingStatus != AuthStatus.IDLE;
    }

    protected void resetAuth(){
        authingStatus = AuthStatus.IDLE;
    }

    public void register(final Map<String, Object> details, final Deferred<Object, BError, Void> deferred) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword((String) details.get(BDefines.Prefs.LoginEmailKey),
                (String) details.get(BDefines.Prefs.LoginPasswordKey)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                // Resetting so we could auth again.
                resetAuth();

                if(task.isSuccessful()) {
                    // Send verification email
                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        deferred.resolve(task.isSuccessful());
                                    else
                                        deferred.reject(new BError(1));
                                }
                            }
                    );
                } else {
                    resetAuth();
                    deferred.reject(BFirebaseNetworkAdapter.getFirebaseError(DatabaseError.fromException(task.getException())));
                }
            }
        });
    }

    public void login(final Map<String, Object> details, final Deferred<Object, BError, Void> deferred) {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword((String) details.get(BDefines.Prefs.LoginEmailKey),
                (String) details.get(BDefines.Prefs.LoginPasswordKey)).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if(task.isComplete() && task.isSuccessful()) {
                            if (task.getResult().getUser().isEmailVerified()) {
                                handleFAUser(task.getResult().getUser()).then(new DoneCallback<BUser>() {
                                    @Override
                                    public void onDone(BUser bUser) {
                                        resetAuth();
                                        deferred.resolve(task.getResult().getUser());
                                        resetAuth();
                                    }
                                }, new FailCallback<BError>() {
                                    @Override
                                    public void onFail(BError bError) {
                                        resetAuth();
                                        deferred.reject(bError);
                                    }
                                });
                            } else {
                                resetAuth();
                                deferred.reject(new BError(BError.Code.NO_VERIFY_EMAIL));
                            }
                        } else {
                            resetAuth();
                            deferred.reject(new BError(BError.Code.USER_DOES_NOT_EXIST, "User doesn't exist"));
                        }
                    }
                }
        );
    }

    public Promise<BUser, BError, Void> handleFAUser(final FirebaseUser authData){

        final Deferred<BUser, BError, Void> deferred = new DeferredObject<>();

        //authingStatus = BFirebaseNetworkAdapter.AuthStatus.HANDLING_F_USER;

        if (authData == null) {
            resetAuth();
            deferred.reject(new BError(BError.Code.SESSION_CLOSED));
        }
        else {
            final Map<String, Object> loginInfoMap = new HashMap<>();
            loginInfoMap.put(BDefines.Prefs.AuthenticationID, authData.getUid());
            loginInfoMap.put(
                    BDefines.Prefs.AccountTypeKey,
                    FirebasePaths.providerToInt(BDefines.ProviderString.Password)
            );

            // Doint a once() on the user to push its details to firebase.
            final BUserWrapper wrapper = BUserWrapper.initWithAuthData(authData);
            wrapper.once().then(new DoneCallback<BUser>() {
                @Override
                public void onDone(BUser bUser) {
                    DaoCore.updateEntity(bUser);

                    getEventManager().userOn(bUser);

                    // TODO push a default image of the user to the cloud.

                    if(!pushHandler.subscribeToPushChannel(wrapper.pushChannel())) {
                        deferred.reject(new BError(BError.Code.BACKENDLESS_EXCEPTION));
                    }

                    goOnline();

                    wrapper.push().done(new DoneCallback<BUser>() {
                        @Override
                        public void onDone(BUser u) {

                            resetAuth();
                            deferred.resolve(u);
                        }
                    }).fail(new FailCallback<BError>() {
                        @Override
                        public void onFail(BError error) {
                            resetAuth();
                            deferred.reject(error);
                        }
                    });
                }
            }, new FailCallback<BError>() {
                @Override
                public void onFail(BError bError) {
                    deferred.reject(bError);
                }
            });
        }

        return deferred.promise();

    }
}
*/
