package test.com.genesis.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import retrofit.http.POST;

/**
 * Created by dima on 08.12.16.
 */

public class MagicView extends FrameLayout {

    public static final String TAG = MagicView.class.getName();
    public static final int SLOP = 20;
    private static final long ANIMATION_DURATION = 300;
    public static final int PREVIEW_COUNT = 2;
    private float lastX;
    private ViewPager smallPager;
    private ViewPager fullPager;
    private View postView;
    private View galleryView;
    private Interpolator decelerateInterpolator = new DecelerateInterpolator();
    private CurrView curView = CurrView.POST;

    public MagicView(Context context) {
        super(context);
    }

    public MagicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        postView = getChildAt(0);
        galleryView = getChildAt(1);
        galleryView.setX(getWidth());
    }

    public void setPagers(ViewPager smallPager, ViewPager fullPager) {
        this.smallPager = smallPager;
        this.fullPager = fullPager;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (smallPager == null || fullPager == null) {
            return false;
        }
        float x = ev.getRawX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                return interceptMove(ev);
        }
        return false;
    }

    private boolean interceptMove(MotionEvent ev) {
        float x = ev.getRawX();
        boolean isGesture = lastX - x > 0;
        lastX = ev.getX();
        switch (curView) {
            case POST:
                if (smallPager.getAdapter() == null || smallPager.getAdapter().getCount() == 0)
                    return false;
                Rect smallPagerrect = new Rect();
                smallPager.getHitRect(smallPagerrect);
                if (!smallPagerrect.contains((int) ev.getX(), (int) ev.getY())) return false;
                if (smallPager.getCurrentItem() < PREVIEW_COUNT - 1)
                    return false;
                break;
            case GALLERY:
                if (fullPager.getCurrentItem() < fullPager.getAdapter().getCount() - 1) {
                    return false;
                }
                break;
        }
        return isGesture;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                return false;
            case MotionEvent.ACTION_MOVE:
                handleMove(ev);
                lastX = ev.getX();
                return true;
            case MotionEvent.ACTION_UP:
                handleUp(ev);
            case MotionEvent.ACTION_CANCEL:
                handleUp(ev);
        }
        return false;
    }

    private void handleUp(MotionEvent event) {
        int half = getWidth() / 2;
        switch (curView) {
            case POST:
                if (galleryView.getX() < half) {
                    performOpenGallery();
                } else {
                    performCloseGallery();
                }
                break;
            case GALLERY:
                if (postView.getX() < half) {
                    performOpenPost();
                } else {
                    performCloseGallery();
                }
                break;
        }
    }

    private void handleMove(MotionEvent ev) {
        float x = ev.getRawX();
        float delta = x - lastX;
        Log.d(TAG, "x= " + ev.getX() + " delta= " + delta);
        switch (curView) {
            case POST:
                if (galleryView.getX() <= 0) {
                    galleryView.setX(0);
                    return;
                }
                galleryView.setX(galleryView.getX() + delta);
                break;
            case GALLERY:
                if (postView.getX() <= 0) {
                    postView.setX(0);
                    return;
                }
                postView.setX(postView.getX() + delta);
                galleryView.setX(galleryView.getX() + delta);
                break;
        }
    }

    public void closeGallery() {
        performCloseGallery();
    }

    private void performCloseGallery() {
        postView.setX(0);
        galleryView.animate().x(getWidth())
                .setInterpolator(decelerateInterpolator)
                .setDuration(ANIMATION_DURATION)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        curView = CurrView.POST;
                    }
                });
    }

    private void performOpenGallery() {
        galleryView.animate().x(0)
                .setInterpolator(decelerateInterpolator)
                .setDuration(ANIMATION_DURATION)
                .setListener(new SimpleAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        curView = CurrView.GALLERY;
                        postView.setX(getWidth());
                    }
                });
    }

    private void performOpenPost() {
        ObjectAnimator animX = ObjectAnimator.ofFloat(postView, "x", 0);
        ObjectAnimator animY = ObjectAnimator.ofFloat(galleryView, "x", -getWidth());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.setInterpolator(decelerateInterpolator);
        set.setDuration(ANIMATION_DURATION);
        set.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                curView = CurrView.POST;
            }
        });
        set.start();
    }

    enum CurrView {
        POST,
        GALLERY
    }
}
