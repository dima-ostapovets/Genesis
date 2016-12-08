package test.com.genesis.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by dima on 08.12.16.
 */

public class MagicView extends FrameLayout {

    public static final String TAG = MagicView.class.getName();
    public static final int SLOP = 20;
    private float lastX;
    private ViewPager smallPager;
    private ViewPager fullPager;

    public MagicView(Context context) {
        super(context);
    }

    public MagicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPagers(ViewPager smallPager, ViewPager fullPager){
        this.smallPager = smallPager;
        this.fullPager = fullPager;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                boolean isGesture = lastX -x > SLOP;
                return isGesture;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float delta = x - lastX;
        lastX = ev.getX();
        Log.d(TAG, "x= "+ev.getX());
//        View first = getChildAt(0);
//        first.setX(first.getX() + delta);
        View second = getChildAt(1);
        second.setX(second.getX() + delta);
       return true;
    }
}
