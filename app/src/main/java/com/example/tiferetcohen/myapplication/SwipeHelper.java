package com.example.tiferetcohen.myapplication;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by tiferet.cohen on 7/22/2017.
 */

public class SwipeHelper implements View.OnTouchListener {
    private GestureDetector gestureDetector;

    public SwipeHelper(Context context) {
        gestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e){
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY){
            boolean result = false;
            try{
                float diffX = e2.getX()-e1.getX();
                float diffY = e2.getY()-e1.getY();
                if(Math.abs(diffX)>Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeLeftToRight();
                        } else {
                            onSwipeRightToLeft();
                        }
                    }
                    result = true;
                }
                else if(Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velY)>SWIPE_VELOCITY_THRESHOLD){
                    if(diffY > 0){
                        onSwipeTopToBottom();
                    } else {
                        onSwipeBottomToTop();
                    }
                }
                result = true;
            } catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeLeftToRight(){

    }
    public void onSwipeRightToLeft(){

    }
    public void onSwipeTopToBottom(){

    }
    public void onSwipeBottomToTop(){

    }
}