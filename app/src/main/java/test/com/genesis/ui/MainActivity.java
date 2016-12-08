package test.com.genesis.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import test.com.genesis.App;
import test.com.genesis.R;
import test.com.genesis.pojo.Post;
import test.com.genesis.pojo.User;
import test.com.genesis.ui.adapters.ImagesAdapter;
import test.com.genesis.ui.adapters.PostsAdapter;
import test.com.genesis.presenter.MainPresenter;
import test.com.genesis.utils.PostUtils;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView {

    @Bind(R.id.vPostPager)
    ViewPager viewPager;
    @Bind(R.id.vpFull)
    ViewPager viewPagerFull;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.ivAvatar)
    ImageView ivAvatar;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.vFlip)
    FlipView flipView;
    private MainPresenter mainPresenter;
    private PostsAdapter adapter;
    private ImagesAdapter galleryAdapter;
    private List<Post> posts;
    private HashMap<String, ViewPager> pagers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new PostsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        galleryAdapter = new ImagesAdapter(getSupportFragmentManager());
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (flipView.getState() == FlipView.State.POST) {
                    resetGallery(position);
                }
                resetFlipView(position);
            }
        });
        viewPagerFull.setAdapter(galleryAdapter);
        flipView.setListener(new FlipView.StateChangeListener() {
            @Override
            public void onChanged(FlipView.State state) {
                if (state == FlipView.State.GALLERY) {
                    viewPager.setCurrentItem(Math.min(adapter.getCount() - 1, viewPager.getCurrentItem() + 1), false);
                }
            }
        });
        mainPresenter = App.app.getAppComponent().getMainPresenter();
        mainPresenter.attachView(this);
    }

    private void resetFlipView(int position) {
        Post post = posts.get(position);
        flipView.setPagers(pagers.get(post.id), viewPagerFull);
    }

    private void resetGallery(int position) {
        galleryAdapter.setImages(PostUtils.getImages(posts.get(position).attachments));
    }

    @OnClick(R.id.btnClose)
    void onCloseGalleryClick() {
        flipView.closeGallery();
        viewPager.setCurrentItem(Math.max(0, viewPager.getCurrentItem() - 1), false);
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showContent(List<Post> posts) {
        this.posts = posts;
        adapter.setPosts(posts);
        if (posts.size() > 0) {
            resetGallery(0);
            resetFlipView(0);
        }
    }

    @Override
    public void showError(String error) {
        Snackbar.make(viewPager, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showUser(User user) {
        tvName.setText(user.name);
        Picasso.with(this)
                .load(user.picture.data.url)
                .fit()
                .centerCrop()
                .transform(new CropCircleTransformation())
                .into(ivAvatar);
    }

    public void addPager(String postId, ViewPager viewPager) {
        pagers.put(postId, viewPager);
    }

    public void removePager(String postId) {
        pagers.remove(postId);
    }
}
