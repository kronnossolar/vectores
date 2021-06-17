package com.app.vector.vectores.greendao;


import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class Main {
    public static void main(String[] args) {
        Schema schema = new Schema(2,"com.app.vector.vectores.entity");
        schema.setDefaultJavaPackageDao("com.app.vector.vectores.dao");
        Entity vector = schema.addEntity("Vector");
        Entity config = schema.addEntity("Configuration");
        vector.addIdProperty();
        vector.addDoubleProperty("Magnitud").notNull();
        vector.addDoubleProperty("Angulo").notNull();
        vector.addFloatProperty("CompX");
        vector.addFloatProperty("CompY");
        /*-------------------------------*/
        config.addIdProperty();
        config.addIntProperty("cantDecimal");
        config.addIntProperty("tipoView");
        config.addIntProperty("escala");
        config.addIntProperty("tipoCoordenada");
        config.addBooleanProperty("showHelp");
        try {
            DaoGenerator daoGenerator = new DaoGenerator();
            daoGenerator.generateAll(schema,"../app/src/main/java/");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
