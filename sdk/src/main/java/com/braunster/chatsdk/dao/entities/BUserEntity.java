package com.braunster.chatsdk.dao.entities;

import com.braunster.chatsdk.dao.BThread;
import com.braunster.chatsdk.dao.BUser;
import com.braunster.chatsdk.dao.FollowerLink;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BFirebaseDefines;
import com.braunster.chatsdk.network.BPath;

import java.util.List;

public abstract class BUserEntity extends Entity {

    public String email ="";

    @Override
    public BPath getBPath() {
        return new BPath().addPathComponent(BFirebaseDefines.Path.BUsersPath, getEntityID());
    }

    public abstract String[] getCacheIDs();

    public abstract List<BThread> getThreads();

    public abstract List<BThread> getThreads(int type);

    public abstract List<BThread> getThreads(int type, boolean allowDeleted);

    public abstract List<BUser> getContacts();

    public abstract void addContact(BUser user);

    public abstract FollowerLink fetchOrCreateFollower(BUser follower, int type);
    
    public abstract void setMetaPictureUrl(String imageUrl);

    public abstract String getMetaPictureUrl();

    public abstract void setMetaPictureThumbnail(String thumbnailUrl);
    
    public abstract void setMetaName(String name);

    public abstract String getMetaName();

    public abstract void setMetaEmail(String email);

    public abstract String getMetaEmail();

    public abstract void setMetaStatus(String status);

    public abstract String getMetaStatus();

    public abstract void setMetaDepartment(String department);

    public abstract String getMetaDepartment();

    public abstract String getThumbnailPictureURL();

    public abstract String getMetaCourses();

}
