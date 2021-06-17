package com.app.vector.vectores.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.app.vector.vectores.entity.Vector;
import com.app.vector.vectores.entity.Configuration;

import com.app.vector.vectores.dao.VectorDao;
import com.app.vector.vectores.dao.ConfigurationDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig vectorDaoConfig;
    private final DaoConfig configurationDaoConfig;

    private final VectorDao vectorDao;
    private final ConfigurationDao configurationDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        vectorDaoConfig = daoConfigMap.get(VectorDao.class).clone();
        vectorDaoConfig.initIdentityScope(type);

        configurationDaoConfig = daoConfigMap.get(ConfigurationDao.class).clone();
        configurationDaoConfig.initIdentityScope(type);

        vectorDao = new VectorDao(vectorDaoConfig, this);
        configurationDao = new ConfigurationDao(configurationDaoConfig, this);

        registerDao(Vector.class, vectorDao);
        registerDao(Configuration.class, configurationDao);
    }
    
    public void clear() {
        vectorDaoConfig.clearIdentityScope();
        configurationDaoConfig.clearIdentityScope();
    }

    public VectorDao getVectorDao() {
        return vectorDao;
    }

    public ConfigurationDao getConfigurationDao() {
        return configurationDao;
    }

}