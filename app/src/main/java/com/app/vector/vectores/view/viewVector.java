package com.app.vector.vectores.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.vector.vectores.dao.ConfigurationDao;
import com.app.vector.vectores.dao.VectorDao;
import com.app.vector.vectores.entity.Configuration;
import com.app.vector.vectores.entity.Vector;
import com.app.vector.vectores.dao.SessionDB;

import java.util.List;

public class viewVector extends AppCompatActivity {
    Paint paint, plano, resultante;
    InternalView myView;
    private int resultado;
    Configuration configuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Vector vectorResultante;
        resultado = bundle.getInt("resultado");
        SessionDB sessionDB = new SessionDB(viewVector.this);
        VectorDao vectorDao = sessionDB.getDaoSession().getVectorDao();
        ConfigurationDao configurationDao = sessionDB.getDaoSession().getConfigurationDao();
        List<Configuration> configurations = configurationDao.loadAll();
        configuration = configurations.get(0);
        List<Vector> vectores;
        vectores = vectorDao.loadAll();
        if(resultado == 1){
            vectorResultante = calcular_resultado(vectores);
            myView = new InternalView(viewVector.this, vectores,vectorResultante);
        }else {
            myView = new InternalView(viewVector.this, vectores);
        }
        setContentView(myView);
    }
    public Vector calcular_resultado(List<Vector> vectores){
        float resultX = 0,resultY = 0;
        Vector vector = new Vector();
        for (Vector v: vectores) {
            resultX = v.getCompX() + resultX;
            resultY = v.getCompY() + resultY;
        }
        vector.setCompX(resultX);
        vector.setCompY(resultY);

        return vector;
    }
    private class InternalView extends View {
        private int width, height;
        private float posX, posY;
        private List<Vector> vectores;
        private Vector vectorResultante;
        public InternalView(Context context, List<Vector> vectores){
            super(context);
            paint = new Paint();
            plano = new Paint();
            resultante = new Paint();
            this.vectores = vectores;
            if(configuration.getTipoView() == 0){
                resultado = 0;
            }else{
                resultado = 2;
            }
        }

        public InternalView(Context context,List<Vector> vectores,Vector result){
            super(context);
            paint = new Paint();
            plano = new Paint();
            resultante = new Paint();
            this.vectores = vectores;
            vectorResultante = result;
            if(configuration.getTipoView() == 0){
                resultado = 0;
            }else{
                resultado = 2;
            }
        }
        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            this.width = w;
            this.height = h;
            super.onSizeChanged(w, h, oldw, oldh);
        }
        private float getPosX(Vector v,float posFinX){
            float x = v.getCompX() / escala();
            Log.i("posVX",String.valueOf(v.getCompX()));

            if(resultado == 2){
                if(x >= 0){
                    x = (width / 2) + posFinX + Math.abs(x);
                }else{
                    x = (width / 2) + posFinX - Math.abs(x);
                }
            }else if(resultado == 0 || resultado ==1){
                if(x > 0){
                    x = (width / 2) + Math.abs(x);
                }else{
                    x = (width / 2) - Math.abs(x);
                }
            }
            Log.i("posX",String.valueOf(x));
            return x;
        }
        private float getPosY(Vector v, float posFinY){
            float y = v.getCompY() / escala();
            Log.i("posVY",String.valueOf(v.getCompY()));
            if(resultado == 2){
                if(y >= 0){
                    y = (height / 2) - posFinY - Math.abs(y);
                }else{
                    y = (height / 2) - posFinY + Math.abs(y);
                }
            }else if(resultado == 0 || resultado ==1){

                if(y > 0){
                    y = (height / 2) - Math.abs(y);
                }else{
                    y = (height / 2) + Math.abs(y);
                }
            }
            Log.i("posY",String.valueOf(y));
            return  y;
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float posFinX = 0,posFinY = 0;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawPaint(paint);
            paint.setColor(Color.BLACK);
            paint.setAntiAlias(true);
            plano.setStyle(Paint.Style.FILL);
            plano.setColor(Color.RED);
            plano.setAntiAlias(true);
            canvas.drawLine(width/2,10,width/2,height-10,plano);
            canvas.drawLine(10,height/2,width-10,height/2,plano);
            int i = 1;

            Log.i("posW",String.valueOf(width/2));
            Log.i("posH",String.valueOf(height/2));
            for (Vector v: vectores) {
                Log.i("finX",String.valueOf(posFinX));
                Log.i("finY",String.valueOf(posFinY));
                posX = getPosX(v,posFinX);
                posY = getPosY(v,posFinY);
                canvas.drawLine((width/2)+ posFinX, (height/2)-posFinY,(int)(posX),(int)(posY),paint);
                paint.setTextSize(30);
                canvas.drawPath(drawArrow(new Point((width/2)+(int)posFinX,(height/2)-(int) posFinY)
                        ,new Point((int)(posX),(int)(posY))),paint);
                if(resultado == 0){
                    canvas.drawText("F"+String.valueOf(i),(int)(posX+20),(int)(posY),paint);
                }else if(resultado == 2){
                    posFinX = posX -(width/2) ;
                    posFinY = (height/2) - posY;
                }

                i++;
            }
            if(vectorResultante != null){
                resultante.setColor(Color.BLUE);
                posX = getPosX(vectorResultante,0);
                posY = getPosY(vectorResultante,0);
                canvas.drawLine(width/2, height/2,(int)(posX),(int)(posY),resultante);
                canvas.drawPath(drawArrow(new Point(width/2, height/2)
                        ,new Point((int)(posX),(int)(posY)))
                        ,resultante);
                canvas.drawText("VR",(int)(posX+20),(int)(posY),paint);
                canvas.drawText(String.valueOf(redondear(vectorResultante.getCompX(),configuration.getCantDecimal())) + " i "
                        + String.valueOf(redondear(vectorResultante.getCompY(),configuration.getCantDecimal())) + " j"
                ,5,height-50,paint);
                canvas.drawText(String.valueOf(magnitud(vectorResultante.getCompX(),vectorResultante.getCompY()))
                        + " N , " + String.valueOf(angulo(vectorResultante.getCompX(),vectorResultante.getCompY())) + "°"
                        , 5, height-10,paint);
            }
        }
        public float escala(){
            float newEscala;
            switch (configuration.getEscala()){
                case 1:
                    newEscala = 1;
                    break;
                case 2:
                    newEscala = 5;
                    break;
                case 3:
                    newEscala = 10;
                    break;
                case 4:
                    newEscala = 50;
                    break;
                case 5:
                    newEscala = 100;
                    break;
                case 6:
                    newEscala = 1000;
                    break;
                case 7:
                    newEscala = 10000;
                    break;
                case 0:
                    newEscala = (float) 0.05;
                    break;
                default:
                    newEscala = -1;
                    break;
            }
            return newEscala;
        }
        public float redondear(double valorInicial, int numeroDecimales) {
            double parteEntera, resultado;
            resultado = valorInicial;
            parteEntera = Math.floor(resultado);
            resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
            resultado = Math.round(resultado);
            resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
            return (float) (resultado);
        }
        public float magnitud(float x,float y){
            return redondear(Math.sqrt(Math.pow((double)(x),2) + Math.pow((double) (y),2)), configuration.getCantDecimal());
        }
        public float angulo(float x, float y){
            double angulo = 0;
            if(x>=0 && y >=0){
                angulo = Math.abs(Math.toDegrees(Math.atan((y)/(x))));
            }else if(x < 0 && y >=0){
                angulo = Math.abs(Math.toDegrees(Math.atan((x)/(y)))) + 90;
            }else if(x < 0 && y < 0){
                angulo = Math.abs(Math.toDegrees(Math.atan((y)/(x)))) + 180;
            }else if(x >= 0 && y < 0){
                angulo = Math.abs(Math.toDegrees(Math.atan((x)/(y)))) + 270;
            }
            return   redondear(angulo,configuration.getCantDecimal());
        }
        private Path drawArrow(Point startPoint, Point endPoint) {
            Path mPath = new Path();
            float deltaX = endPoint.x - startPoint.x;
            float deltaY = endPoint.y - startPoint.y;
            int ARROWHEAD_LENGTH = 15;
            float sideZ = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            float frac = ARROWHEAD_LENGTH < sideZ ? ARROWHEAD_LENGTH / sideZ : 1.0f;
            float point_x_1 = startPoint.x + ((1 - frac) * deltaX + frac * deltaY);
            float point_y_1 = startPoint.y + ((1 - frac) * deltaY - frac * deltaX);
            float point_x_2 = endPoint.x;
            float point_y_2 = endPoint.y;
            float point_x_3 = startPoint.x + ((1 - frac) * deltaX - frac * deltaY);
            float point_y_3 = startPoint.y + ((1 - frac) * deltaY + frac * deltaX);
            mPath.moveTo(point_x_1, point_y_1);
            mPath.lineTo(point_x_2, point_y_2);
            mPath.lineTo(point_x_3, point_y_3);
            return mPath;
        }
    }
}
