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
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * Created by dima on 08.12.16.
 */

public class FlipView extends FrameLayout {

    public static final String TAG = FlipView.class.getName();
    private static final long ANIMATION_DURATION = 300;
    public static final int PREVIEW_COUNT = 2;
    private float lastX;
    private ViewPager previewPager;
    private ViewPager galleryPager;
    private View postView;
    private View galleryView;
    private Interpolator decelerateInterpolator = new DecelerateInterpolator();
    private State state;
    private StateChangeListener listener;

    public FlipView(Context context) {
        super(context);
    }

    public FlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public State getState() {
        return state;
    }

    public void setListener(StateChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        postView = getChildAt(0);
        galleryView = getChildAt(1);
    }

    public void setPagers(ViewPager previewPager, ViewPager fullPager) {
        this.previewPager = previewPager;
        this.galleryPager = fullPager;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (state == null) {
            setState(State.POST);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (previewPager == null || galleryPager == null) {
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
        //TODO use velocityTracker to handle fling
        float x = ev.getRawX();
        boolean isGesture = lastX - x > 0;
        lastX = ev.getX();
        switch (state) {
            case POST:
                if (previewPager.getAdapter() == null || previewPager.getAdapter().getCount() == 0)
                    return false;
                int[] location = new int[2];
                previewPager.getLocationOnScreen(location);
                Rect previewPagerrect = new Rect(location[0], location[1], location[0] + previewPager.getWidth(), location[1] + previewPager.getHeight());
                if (!previewPagerrect.contains((int) ev.getRawX(), (int) ev.getRawY())) return false;
                if (previewPager.getCurrentItem() < PREVIEW_COUNT - 1)
                    return false;
                break;
            case GALLERY:
                if (galleryPager.getCurrentItem() < galleryPager.getAdapter().getCount() - 1) {
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
                lastX = x;
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
        switch (state) {
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
                    performClosePost();
                }
                break;
        }
    }

    private void handleMove(MotionEvent ev) {
        float x = ev.getRawX();
        float delta = x - lastX;
        Log.d(TAG, "x= " + ev.getX() + " delta= " + delta);
        switch (state) {
            case POST:
                if (galleryView.getX() <= 0) {
                    galleryView.setX(0);
                    return;
                }
                galleryView.setX(galleryView.getX() + delta);
                break;
            case GALLERY:
                if (galleryView.getX() >= 0 && delta > 0) {
                    galleryView.setX(0);
                    postView.setX(getWidth());
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
                        setState(State.POST);
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
                        setState(State.GALLERY);
                    }
                });
    }

    private void performOpenPost() {
        ObjectAnimator animPost = ObjectAnimator.ofFloat(postView, "x", 0);
        ObjectAnimator animGallery = ObjectAnimator.ofFloat(galleryView, "x", -getWidth());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animPost, animGallery);
        set.setInterpolator(decelerateInterpolator);
        set.setDuration(ANIMATION_DURATION);
        set.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setState(State.POST);
            }
        });
        set.start();
    }

    private void performClosePost() {
        ObjectAnimator animPost = ObjectAnimator.ofFloat(postView, "x", getWidth());
        ObjectAnimator animGallery = ObjectAnimator.ofFloat(galleryView, "x", 0);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animPost, animGallery);
        set.setInterpolator(decelerateInterpolator);
        set.setDuration(ANIMATION_DURATION);
        set.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setState(State.GALLERY);
            }
        });
        set.start();
    }

    private void setState(State newState) {
        switch (newState) {
            case POST:
                galleryView.setX(getWidth());
                postView.setX(0);
                break;
            case GALLERY:
                postView.setX(getWidth());
                galleryView.setX(0);
                break;
        }
        State lastState = state;
        state = newState;
        if (newState != lastState && listener != null) {
            listener.onChanged(newState);
        }
    }

    enum State {
        POST,
        GALLERY
    }

    public interface StateChangeListener {
        void onChanged(State currView);
    }
}
