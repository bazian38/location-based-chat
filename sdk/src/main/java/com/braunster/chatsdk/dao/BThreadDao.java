package com.braunster.chatsdk.dao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.SqlUtils;
import de.greenrobot.dao.internal.DaoConfig;

import com.braunster.chatsdk.dao.BThread;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table BTHREAD.
*/
public class BThreadDao extends AbstractDao<BThread, Long> {

    public static final String TABLENAME = "BTHREAD";

    /**
     * Properties of entity BThread.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property EntityID = new Property(1, String.class, "entityID", false, "ENTITY_ID");
        public final static Property CreationDate = new Property(2, java.util.Date.class, "creationDate", false, "CREATION_DATE");
        public final static Property HasUnreadMessages = new Property(3, Boolean.class, "hasUnreadMessages", false, "HAS_UNREAD_MESSAGES");
        public final static Property Deleted = new Property(4, Boolean.class, "deleted", false, "DELETED");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property LastMessageAdded = new Property(6, java.util.Date.class, "LastMessageAdded", false, "LAST_MESSAGE_ADDED");
        public final static Property Type = new Property(7, Integer.class, "type", false, "TYPE");
        public final static Property CreatorEntityId = new Property(8, String.class, "creatorEntityId", false, "CREATOR_ENTITY_ID");
        public final static Property ImageUrl = new Property(9, String.class, "imageUrl", false, "IMAGE_URL");
        public final static Property RootKey = new Property(10, String.class, "rootKey", false, "root_key");
        public final static Property ApiKey = new Property(11, String.class, "apiKey", false, "api_key");
        public final static Property Creator_ID = new Property(12, Long.class, "creator_ID", false, "CREATOR__ID");
        public final static Property Description = new Property(13, String.class, "description", false, "DESC");
    };

    private DaoSession daoSession;


    public BThreadDao(DaoConfig config) {
        super(config);
    }
    
    public BThreadDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'BTHREAD' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'ENTITY_ID' TEXT," + // 1: entityID
                "'CREATION_DATE' INTEGER," + // 2: creationDate
                "'HAS_UNREAD_MESSAGES' INTEGER," + // 3: hasUnreadMessages
                "'DELETED' INTEGER," + // 4: deleted
                "'NAME' TEXT," + // 5: name
                "'LAST_MESSAGE_ADDED' INTEGER," + // 6: LastMessageAdded
                "'TYPE' INTEGER," + // 7: type
                "'CREATOR_ENTITY_ID' TEXT," + // 8: creatorEntityId
                "'IMAGE_URL' TEXT," + // 9: imageUrl
                "'root_key' TEXT," + // 10: rootKey
                "'api_key' TEXT," + // 11: apiKey
                "'CREATOR__ID' INTEGER," + // 12: creator_ID
                "'DESC' TEXT);"); // 13: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'BTHREAD'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BThread entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String entityID = entity.getEntityID();
        if (entityID != null) {
            stmt.bindString(2, entityID);
        }
 
        java.util.Date creationDate = entity.getCreationDate();
        if (creationDate != null) {
            stmt.bindLong(3, creationDate.getTime());
        }
 
        Boolean hasUnreadMessages = entity.getHasUnreadMessages();
        if (hasUnreadMessages != null) {
            stmt.bindLong(4, hasUnreadMessages ? 1l: 0l);
        }
 
        Boolean deleted = entity.getDeleted();
        if (deleted != null) {
            stmt.bindLong(5, deleted ? 1l: 0l);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }

        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(14, description);
        }

        java.util.Date LastMessageAdded = entity.getLastMessageAdded();
        if (LastMessageAdded != null) {
            stmt.bindLong(7, LastMessageAdded.getTime());
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(8, type);
        }
 
        String creatorEntityId = entity.getCreatorEntityId();
        if (creatorEntityId != null) {
            stmt.bindString(9, creatorEntityId);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(10, imageUrl);
        }
 
        String rootKey = entity.getRootKey();
        if (rootKey != null) {
            stmt.bindString(11, rootKey);
        }
 
        String apiKey = entity.getApiKey();
        if (apiKey != null) {
            stmt.bindString(12, apiKey);
        }
 
        Long creator_ID = entity.getCreator_ID();
        if (creator_ID != null) {
            stmt.bindLong(13, creator_ID);
        }
    }

    @Override
    protected void attachEntity(BThread entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public BThread readEntity(Cursor cursor, int offset) {
        BThread entity = new BThread( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // entityID
            cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)), // creationDate
            cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0, // hasUnreadMessages
            cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0, // deleted
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // description
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // LastMessageAdded
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // type
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // creatorEntityId
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // imageUrl
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // rootKey
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // apiKey
            cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12) // creator_ID
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BThread entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEntityID(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setCreationDate(cursor.isNull(offset + 2) ? null : new java.util.Date(cursor.getLong(offset + 2)));
        entity.setHasUnreadMessages(cursor.isNull(offset + 3) ? null : cursor.getShort(offset + 3) != 0);
        entity.setDeleted(cursor.isNull(offset + 4) ? null : cursor.getShort(offset + 4) != 0);
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDescription(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setLastMessageAdded(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setType(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setCreatorEntityId(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setImageUrl(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setRootKey(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setApiKey(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setCreator_ID(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BThread entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BThread entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getBUserDao().getAllColumns());
            builder.append(" FROM BTHREAD T");
            builder.append(" LEFT JOIN BUSER T0 ON T.'CREATOR__ID'=T0.'_id'");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected BThread loadCurrentDeep(Cursor cursor, boolean lock) {
        BThread entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        BUser creator = loadCurrentOther(daoSession.getBUserDao(), cursor, offset);
        entity.setCreator(creator);

        return entity;    
    }

    public BThread loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<BThread> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<BThread> list = new ArrayList<BThread>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<BThread> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<BThread> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
