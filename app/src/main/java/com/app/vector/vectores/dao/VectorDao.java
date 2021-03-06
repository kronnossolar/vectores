package com.app.vector.vectores.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.app.vector.vectores.entity.Vector;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "VECTOR".
*/
public class VectorDao extends AbstractDao<Vector, Long> {

    public static final String TABLENAME = "VECTOR";

    /**
     * Properties of entity Vector.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Magnitud = new Property(1, double.class, "Magnitud", false, "MAGNITUD");
        public final static Property Angulo = new Property(2, double.class, "Angulo", false, "ANGULO");
        public final static Property CompX = new Property(3, Float.class, "CompX", false, "COMP_X");
        public final static Property CompY = new Property(4, Float.class, "CompY", false, "COMP_Y");
    }


    public VectorDao(DaoConfig config) {
        super(config);
    }
    
    public VectorDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"VECTOR\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MAGNITUD\" REAL NOT NULL ," + // 1: Magnitud
                "\"ANGULO\" REAL NOT NULL ," + // 2: Angulo
                "\"COMP_X\" REAL," + // 3: CompX
                "\"COMP_Y\" REAL);"); // 4: CompY
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"VECTOR\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Vector entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMagnitud());
        stmt.bindDouble(3, entity.getAngulo());
 
        Float CompX = entity.getCompX();
        if (CompX != null) {
            stmt.bindDouble(4, CompX);
        }
 
        Float CompY = entity.getCompY();
        if (CompY != null) {
            stmt.bindDouble(5, CompY);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Vector entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindDouble(2, entity.getMagnitud());
        stmt.bindDouble(3, entity.getAngulo());
 
        Float CompX = entity.getCompX();
        if (CompX != null) {
            stmt.bindDouble(4, CompX);
        }
 
        Float CompY = entity.getCompY();
        if (CompY != null) {
            stmt.bindDouble(5, CompY);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Vector readEntity(Cursor cursor, int offset) {
        Vector entity = new Vector( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getDouble(offset + 1), // Magnitud
            cursor.getDouble(offset + 2), // Angulo
            cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // CompX
            cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4) // CompY
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Vector entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMagnitud(cursor.getDouble(offset + 1));
        entity.setAngulo(cursor.getDouble(offset + 2));
        entity.setCompX(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
        entity.setCompY(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Vector entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Vector entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Vector entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
