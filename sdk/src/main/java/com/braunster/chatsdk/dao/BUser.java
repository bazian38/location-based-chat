package com.braunster.chatsdk.dao;

import java.util.List;
import de.greenrobot.dao.DaoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.braunster.chatsdk.Utils.JsonHelper;
import com.braunster.chatsdk.Utils.sorter.ThreadsSorter;
import com.braunster.chatsdk.dao.core.DaoCore;
import com.braunster.chatsdk.dao.entities.BUserEntity;
import com.braunster.chatsdk.Utils.Debug;
import com.braunster.chatsdk.network.BDefines;
import com.braunster.chatsdk.network.BFirebaseDefines;
import com.braunster.chatsdk.network.BNetworkManager;
import com.braunster.chatsdk.network.BPath;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.dao.Property;
import timber.log.Timber;


/**
 * Entity mapped to table BUSER.
 */
public class BUser extends BUserEntity  {

    private Long id;
    private String entityID;
    private Integer AuthenticationType;
    private String messageColor;
    private java.util.Date lastOnline;
    private java.util.Date lastUpdated;
    private Boolean Online;
    private String Metadata;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient BUserDao myDao;

    private List<ContactLink> contactLinks;
    private List<UserThreadLink> userThreadLinks;
    private List<BLinkedAccount> BLinkedAccounts;

    // KEEP FIELDS - put your custom fields here
    private static final boolean DEBUG = Debug.BUser;
    private static final String USER_PREFIX = "user";
    // KEEP FIELDS END

    public BUser() {
    }

    public BUser(Long id) {
        this.id = id;
    }

    public BUser(Long id, String entityID, Integer AuthenticationType, String messageColor, java.util.Date lastOnline, java.util.Date lastUpdated, Boolean Online, String Metadata) {
        this.id = id;
        this.entityID = entityID;
        this.AuthenticationType = AuthenticationType;
        this.messageColor = messageColor;
        this.lastOnline = lastOnline;
        this.lastUpdated = lastUpdated;
        this.Online = Online;
        this.Metadata = Metadata;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBUserDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public Integer getAuthenticationType() {
        return AuthenticationType;
    }

    public void setAuthenticationType(Integer AuthenticationType) {
        this.AuthenticationType = AuthenticationType;
    }

    public String getMessageColor() {
        return messageColor;
    }

    public void setMessageColor(String messageColor) {
        this.messageColor = messageColor;
    }

    public java.util.Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(java.util.Date lastOnline) {
        this.lastOnline = lastOnline;
    }

    public java.util.Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(java.util.Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Boolean getOnline() {
        return Online;
    }

    public void setOnline(Boolean Online) {
        this.Online = Online;
    }

    public String getMetadata() {
        return Metadata;
    }

    public void setMetadata(String Metadata) {
        this.Metadata = Metadata;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<ContactLink> getContactLinks() {
        if (contactLinks == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContactLinkDao targetDao = daoSession.getContactLinkDao();
            List<ContactLink> contactLinksNew = targetDao._queryBUser_ContactLinks(id);
            synchronized (this) {
                if(contactLinks == null) {
                    contactLinks = contactLinksNew;
                }
            }
        }
        return contactLinks;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetContactLinks() {
        contactLinks = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<UserThreadLink> getUserThreadLinks() {
        if (userThreadLinks == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserThreadLinkDao targetDao = daoSession.getUserThreadLinkDao();
            List<UserThreadLink> userThreadLinksNew = targetDao._queryBUser_UserThreadLinks(id);
            synchronized (this) {
                if(userThreadLinks == null) {
                    userThreadLinks = userThreadLinksNew;
                }
            }
        }
        return userThreadLinks;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetUserThreadLinks() {
        userThreadLinks = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<BLinkedAccount> getBLinkedAccounts() {
        if (BLinkedAccounts == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            BLinkedAccountDao targetDao = daoSession.getBLinkedAccountDao();
            List<BLinkedAccount> BLinkedAccountsNew = targetDao._queryBUser_BLinkedAccounts(id);
            synchronized (this) {
                if(BLinkedAccounts == null) {
                    BLinkedAccounts = BLinkedAccountsNew;
                }
            }
        }
        return BLinkedAccounts;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here

    @Override
    public BPath getBPath() {
        return new BPath().addPathComponent(BFirebaseDefines.Path.BUsersPath, getEntityID());
    }

    @Override
    public Type getEntityType() {
        return Type.bEntityTypeUser;
    }

    public Date lastUpdated() {
        return lastUpdated;
    }

    public String[] getCacheIDs(){
        return new String[]{entityID != null ? entityID : ""};
    }

    /** Get a link account of the user by type.
     * @return BLinkedAccount if found
     * @return null if no account found.*/
    public BLinkedAccount getAccountWithType(int type){
        for (BLinkedAccount account : getBLinkedAccounts())
        {
            if (account.getType() == type)
                return account;
        }
        return null;
    }

    @Override
    public List<BThread> getThreads(){
        return getThreads(-1);
    }

    @Override
    public List<BThread> getThreads(int type){
        return getThreads(type, false);
    }

    /**
     * Method updated by Kyle
     *
     * @param type the type of the threads to get, Pass -1 to get all types.
     * @param allowDeleted if true deleted threads will be included in the result list
     * @return a list with all the threads.
     ** */
    @Override
    public List<BThread> getThreads(int type, boolean allowDeleted){
        List<BThread> bThreads = new ArrayList<>();

        // Freshen up the data by calling reset before getting the list
        resetUserThreadLinks();
        List<UserThreadLink> UserThreadLinkList = getUserThreadLinks();
        //List<UserThreadLink> UserThreadLinkList = DaoCore.fetchEntitiesWithProperty(UserThreadLink.class,
        //        UserThreadLinkDao.Properties.BUserDaoId, getEntityID());
        // In case the list is empty
        if (UserThreadLinkList == null) return null;
        // Pull the threads out of the link object . . . if only gDao supported manyToMany . . .
        for (UserThreadLink userThreadLink : UserThreadLinkList ){
            if(userThreadLink.getBThread() == null) continue;
            // Do not retrieve deleted threads unless otherwise specified
            if(userThreadLink.getBThread().isDeleted() && !allowDeleted) continue;
            // If the thread type was specified, only add this type
            // TODO: find out why some threads have null types, getTypeSafely should not be needed
            if(userThreadLink.getBThread().getTypeSafely() != type && type != -1) continue;

            bThreads.add(userThreadLink.getBThread());
        }

        // Sort the threads list before returning
        Collections.sort(bThreads, new ThreadsSorter());
        return bThreads;
    }

    @Override
    public List<BUser> getContacts() {
        List<BUser> contactList = new ArrayList<>();
        List<ContactLink> contactLinks;
        // For some reason the default ContactLinks do not persist, have to find in DB
        contactLinks = DaoCore.fetchEntitiesWithProperty(ContactLink.class,
                ContactLinkDao.Properties.LinkOwnerBUserDaoId, this.getId());
        for (ContactLink contactLink : contactLinks){
            contactList.add(contactLink.getBUser());
        }

        return contactList;
    }

    @Override
    public void addContact(BUser newContact) {
        if (newContact.equals(this))
            return;

        // Retrieve contacts
        List contacts = getContacts();
        // Check if user is already in contact list
        if ( contacts.contains(newContact)) return;

        // refresh contact list before updating
        resetContactLinks();
        List contactLinkList = getContactLinks();
        ContactLink contactLink = new ContactLink();
        // Set link owner
        contactLink.setLinkOwnerBUser(this);
        contactLink.setLinkOwnerBUserDaoId(this.getId());
        // Set contact
        contactLink.setBUser(newContact);
        contactLink.setBUserDaoId(newContact.getId());
        // insert contact link entity into DB
        daoSession.insertOrReplace(contactLink);
        // make the connection to the user
        contactLinkList.add(newContact);

        this.update();
    }


    private FollowerLink fetchFollower(BUser follower, int type){
        return DaoCore.fetchEntityWithProperties(FollowerLink.class,
                new Property[]{FollowerLinkDao.Properties.BUserDaoId, FollowerLinkDao.Properties.LinkOwnerBUserDaoId, FollowerLinkDao.Properties.Type},
                follower.getId(), getId(),  type);
    }

    @Override
    public FollowerLink fetchOrCreateFollower(BUser follower, int type) {

        FollowerLink follows = fetchFollower(follower, type);

        if (follows== null)
        {
            follows = new FollowerLink();

            follows.setLinkOwnerBUser(this);
            follows.setBUser(follower);
            follows.setType(type);

            follows = DaoCore.createEntity(follows);
        }

        return follows;
    }
   
    @Override
    public void setMetaPictureUrl(String imageUrl) {
        setMetadataString(BDefines.Keys.BPictureURL, imageUrl);
    }

    @Override
    public String getMetaPictureUrl() {
        return metaStringForKey(BDefines.Keys.BPictureURL);
    }

    @Override
    public String getThumbnailPictureURL() {
        return metaStringForKey(BDefines.Keys.BPictureURLThumbnail);
    }

    @Override
    public void setMetaPictureThumbnail(String thumbnailUrl) {
        setMetadataString(BDefines.Keys.BPictureURLThumbnail, thumbnailUrl);
    }

    @Override
    public void setMetaName(String name) {
        setMetadataString(BDefines.Keys.BName, name);
    }

    @Override
    public String getMetaName() {
        return metaStringForKey(BDefines.Keys.BName);
    }

    @Override
    public void setMetaEmail(String email) {
        setMetadataString(BDefines.Keys.BEmail, email);
    }

    @Override
    public String getMetaEmail() {
        return metaStringForKey(BDefines.Keys.BEmail);
    }

    @Override
    public void setMetaStatus(String status) {
        setMetadataString(BDefines.Keys.BStatus, status);
    }

    @Override
    public String getMetaStatus() {
        return metaStringForKey(BDefines.Keys.BStatus);
    }

    @Override
    public void setMetaDepartment(String department) {
        setMetadataString(BDefines.Keys.BDepartment, department);
    }

    @Override
    public String getMetaDepartment() {
        return metaStringForKey(BDefines.Keys.BDepartment);
    }

    @Override
    public String getMetaCourses() {
        return metaStringForKey(BDefines.Keys.BCourses);
    }

    public void setMetaCourses(String courses) {
        setMetadataString(BDefines.Keys.BCourses, courses);
    }

    public String metaStringForKey(String key){
        return (String) metaMap().get(key);
    }

    public void setMetadataString(String key, String value){
        Map<String, Object> map = metaMap();
        map.put(key, value);
        
        setMetaMap(map);
        DaoCore.updateEntity(this);
    }

    /**
     * Setting the metadata, The Map will be converted to a Json String.
     **/
    public void setMetaMap(Map<String, Object> metadata){
        metadata = updateMetaDataFormat(metadata);
        
        this.Metadata = new JSONObject(metadata).toString();
    }
    
    @Deprecated()
    /**
     * This is for maintaining compatibility with older chat versions, It will be removed in a few versions.
     **/
    private Map<String, Object> updateMetaDataFormat(Map<String, Object> map){
        
        Map<String, Object> newData = new HashMap<>();

        String newKey, value;
        for (String key : map.keySet())
        {
            if (map.get(key) instanceof Map)
            {
                value = (String) ((Map) map.get(key)).get(BDefines.Keys.BValue);
                newKey = (String) ((Map) map.get(key)).get(BDefines.Keys.BKey);
                newData.put(newKey, value);
                
                if (DEBUG) Timber.i("convertedData, Key: %s, Value: %s", newKey, value);
            }
            else 
                newData.put(key, map.get(key));
        }
        
        return newData;
    }

    /**
     * Converting the metadata json to a map object
     **/
    public Map<String, Object> metaMap(){
        if (StringUtils.isEmpty(Metadata))
            return new HashMap<>();

        try {
            return JsonHelper.toMap(new JSONObject(Metadata));
        } catch (JSONException e) {
            e.printStackTrace();
            Timber.e(e.getCause(), "Cant parse metadata json to map. Meta: %s", Metadata);

            return new HashMap<>();
        }
    }

    
    
    
    public boolean hasThread(BThread thread){
        com.braunster.chatsdk.dao.UserThreadLink data =
                DaoCore.fetchEntityWithProperties(com.braunster.chatsdk.dao.UserThreadLink.class,
                        new Property[]{UserThreadLinkDao.Properties.BThreadDaoId, UserThreadLinkDao.Properties.BUserDaoId}, thread.getId(), getId());

        return data != null;
    }

    public String getPushChannel(){
        if (entityID == null)
            return "";

        return USER_PREFIX + (entityID.replace(":","_"));
    }

    public boolean isMe(){
        return getId().longValue()
                == BNetworkManager.sharedManager().getNetworkAdapter().currentUserModel().getId().longValue();
    }

    @Override
    public String toString() {
        return String.format("BUser, id: %s meta: %s", id, getMetadata());
    }
    // KEEP METHODS END

}
