package com.app.vector.vectores.entity;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "VECTOR".
 */
@Entity
public class Vector {

    @Id
    private Long id;
    private double Magnitud;
    private double Angulo;
    private Float CompX;
    private Float CompY;

    @Generated(hash = 878029600)
    public Vector() {
    }

    public Vector(Long id) {
        this.id = id;
    }

    @Generated(hash = 2097539056)
    public Vector(Long id, double Magnitud, double Angulo, Float CompX, Float CompY) {
        this.id = id;
        this.Magnitud = Magnitud;
        this.Angulo = Angulo;
        this.CompX = CompX;
        this.CompY = CompY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getMagnitud() {
        return Magnitud;
    }

    public void setMagnitud(double Magnitud) {
        this.Magnitud = Magnitud;
    }

    public double getAngulo() {
        return Angulo;
    }

    public void setAngulo(double Angulo) {
        this.Angulo = Angulo;
    }

    public Float getCompX() {
        return CompX;
    }

    public void setCompX(Float CompX) {
        this.CompX = CompX;
    }

    public Float getCompY() {
        return CompY;
    }

    public void setCompY(Float CompY) {
        this.CompY = CompY;
    }

    public void calcular_componentes(){
        float result = (float) (Math.cos(this.Angulo * Math.PI/180) * this.Magnitud);
        setCompX(result);
        result = (float) (Math.sin(this.Angulo * 2.0 * Math.PI/360) * this.Magnitud);
        setCompY(result);
    }
    public void setMagnitud(){
        this.Magnitud = (Math.sqrt(Math.pow((double)(this.CompX),2) + Math.pow((double) (this.CompY),2)));
    }
    public void setAngulo(){
        double angulo = 0;
        if(CompX>=0 && CompY >=0){
            angulo = Math.abs(Math.toDegrees(Math.atan((CompY)/(CompX))));
        }else if(CompX < 0 && CompY >=0){
            angulo = Math.abs(Math.toDegrees(Math.atan((CompX)/(CompY)))) + 90;
        }else if(CompX < 0 && CompY < 0){
            angulo = Math.abs(Math.toDegrees(Math.atan((CompY)/(CompX)))) + 180;
        }else if(CompX >= 0 && CompY < 0){
            angulo = Math.abs(Math.toDegrees(Math.atan((CompX)/(CompY)))) + 270;
        }
        this.Angulo = Math.abs(angulo);
    }

}
