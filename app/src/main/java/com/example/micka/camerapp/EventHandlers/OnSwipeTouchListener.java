package com.example.micka.camerapp.EventHandlers;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.micka.camerapp.R;

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;
    private Context ctx;
    private View view;

    public OnSwipeTouchListener(Context ctx,View view){
        this.ctx=ctx;
        gestureDetector = new GestureDetector(ctx,new GestureListener());
        this.view = view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int SWIPE_THRESHOLD = 50;
        private static final int SWIPE_VELOCITY_THRESHOLD = 50;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        view.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.right_to_left));
       // view.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_to_right));
    }

    public void onSwipeLeft() {
        view.startAnimation(AnimationUtils.loadAnimation(ctx,R.anim.left_to_right));
       // view.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.right_to_left));
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}

