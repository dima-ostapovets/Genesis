package test.com.genesis.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by dima on 08.12.16.
 */

public class MagicViewPager extends ViewPager {
    public MagicViewPager(Context context) {
        super(context);
    }

    public MagicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interceptTouchEvent = super.onInterceptTouchEvent(ev);
        Log.d(MagicViewPager.class.getName(), "intercept "+interceptTouchEvent);
        return interceptTouchEvent;
    }
}
