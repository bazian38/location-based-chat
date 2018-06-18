package com.braunster.chatsdk.object;

public abstract class UIUpdater implements Runnable{

    private boolean killed = false;

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public boolean isKilled() {
        return killed;
    }
}