package com.app.vector.vectores.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Mario on 29/04/2017.
 */
public class SessionDB {
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private SQLiteDatabase dataBase;
    public SessionDB(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"appVectores.db3",null);
        dataBase = helper.getWritableDatabase();
        daoMaster = new DaoMaster(dataBase);
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession(){
        return this.daoSession;
    }
}
