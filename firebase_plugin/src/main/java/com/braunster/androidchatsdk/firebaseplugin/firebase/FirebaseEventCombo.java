package com.braunster.androidchatsdk.firebaseplugin.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseEventCombo {

    private FirebaseGeneralEvent listener;
    private String ref;
    private Object tag;

    public FirebaseEventCombo(FirebaseGeneralEvent listener, String ref) {
        this.listener = listener;
        this.ref = ref;
    }

    public FirebaseEventCombo(FirebaseGeneralEvent listener, String ref, Object tag) {
        this.listener = listener;
        this.ref = ref;
        this.tag = tag;
    }

    public static FirebaseEventCombo getNewInstance(FirebaseGeneralEvent listener, String ref) {
        return new FirebaseEventCombo(listener, ref);
    }

    public static FirebaseEventCombo getNewInstance(FirebaseGeneralEvent listener, String ref, Object tag) {
        return new FirebaseEventCombo(listener, ref, tag);
    }

    public FirebaseGeneralEvent getListener() {
        return listener;
    }

    public String getRef() {
        return ref;
    }

    public Object getTag() {
        return tag;
    }

    /** Remove the saved listener from the saved ref.*/
    public void breakCombo(){
        DatabaseReference comboRef = FirebaseDatabase.getInstance().getReferenceFromUrl(ref);

        listener.killEvent();

        if (listener.getType() == FirebaseGeneralEvent.ChildEvent)
        {
            comboRef.removeEventListener((ChildEventListener) listener);
        }
        else if (listener.getType() == FirebaseGeneralEvent.ValueEvent)
        {
            comboRef.removeEventListener((ValueEventListener) listener);
        }
    }
}
