package edu.ucsb.cs.cs185.a_hoang.hw5.hw5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.graphics.drawable.Drawable;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

/**
 * Created by Anthony on 5/15/14.
 */
public class TouchView extends ImageView {
    private Matrix matrix = new Matrix();

    private static final int NONE = 0;
    private static final int SCALE = 1;
    private static final int ROTATE = 2;
    private int mode = NONE;
    private Bitmap bm;
    private ScaleGestureDetector sgd;
    private Canvas canvas;
    private ArrayList<Point> circles;
    private float scaleFactor;
    private float lastTouchX;
    private float lastTouchY;
    private float lastGestX;
    private float lastGestY;
    private float posX;
    private float posY;
    private int mActivePointerId = -1;
    private String picPath;




    public TouchView(Context context, String path) {
        super(context);
        picPath = path;
        this.setBackground(Drawable.createFromPath(path));
        circles = new ArrayList<Point>();
        sgd = new ScaleGestureDetector(getContext(), new ScaleListener());
        this.setImageMatrix(matrix);
        this.setScaleType(ScaleType.MATRIX);
        //this.setOnTouchListener(this);

    }



    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector){
            scaleFactor *= detector.getScaleFactor();
            matrix.setScale(scaleFactor, scaleFactor);
            invalidate();
            return true;
        }

    }


    @Override
    public boolean onTouchEvent( MotionEvent event) {

        sgd.onTouchEvent(event);
        final int action = event.getAction();
        switch(action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:

                   // http://stackoverflow.com/questions/13126752/how-can-i-draw-multiple-circles-through-touch-listening
                   Point p = new Point();
                   p.x = event.getX();
                   p.y = event.getY();
                   circles.add(p);
            case MotionEvent.ACTION_DOWN:
                //http://stackoverflow.com/questions/12169905/zoom-and-panning-imageview-android
                if(!sgd.isInProgress()){
                    final float scaleX = event.getX();
                    final float scaleY = event.getY();

                    lastTouchX = scaleX;
                    lastTouchY = scaleY;

                    mActivePointerId = event.getPointerId(0);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:{
                if (sgd.isInProgress()){
                    final float gx = sgd.getFocusX();
                    final float gy = sgd.getFocusY();
                    lastGestX = gy;
                    lastGestY = gx;
                }
            }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!sgd.isInProgress()){
                    final int pointerIndex = event.findPointerIndex(mActivePointerId);
                    final float x = event.getX(pointerIndex);
                    final float y = event.getY(pointerIndex);

                    final float dx = x - lastTouchX;
                    final float dy = y - lastTouchY;

                    posX += dx;
                    posY += dy;

                    invalidate();

                    lastTouchX = x;
                    lastTouchY = y;

                }else{
                    final float gx = sgd.getFocusX();
                    final float gy = sgd.getFocusY();

                    final float gdx = gx - lastGestX;
                    final float gdy = gy - lastGestY;

                    posX += gdx;
                    posY += gdy;

                    invalidate();

                    lastGestX=gx;
                    lastGestY=gy;

                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {

                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    lastTouchX = event.getX(newPointerIndex);
                    lastTouchY = event.getY(newPointerIndex);
                    mActivePointerId = event.getPointerId(newPointerIndex);
                }
                else{
                    final int tempPointerIndex = event.findPointerIndex(mActivePointerId);
                    lastTouchX = event.getX(tempPointerIndex);
                    lastTouchY = event.getY(tempPointerIndex);
                }

                break;
        }


        return true;
    }


    @Override
    public void onDraw(Canvas c){

        c.save();
        c.translate(posX, posY);
        Bitmap bitmap = BitmapFactory.decodeFile(picPath);
        super.onDraw(c);



        this.setImageMatrix(matrix);
        if(bm == null) {
            bm = Bitmap.createBitmap(c.getWidth(), c.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Paint p = new Paint();
        p.setColor(Color.RED);

        c.drawBitmap(bitmap, this.getWidth(), this.getHeight(), p);

        for(int i = 0; i < circles.size(); i++ ){
            c.drawCircle(circles.get(i).x, circles.get(i).y, 10, p);
        }
        c.restore();



    }

    class Point{
        float x;
        float y;
    }





}


