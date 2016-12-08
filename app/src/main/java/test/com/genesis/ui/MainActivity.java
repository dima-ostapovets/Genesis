package test.com.genesis.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import test.com.genesis.App;
import test.com.genesis.R;
import test.com.genesis.pojo.Post;
import test.com.genesis.pojo.User;
import test.com.genesis.ui.adapters.PostsAdapter;
import test.com.genesis.ui.adapters.presenter.MainPresenter;

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
    MagicView viewFlipper;
    private MainPresenter mainPresenter;
    private PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewFlipper.setPagers(viewPager, viewPagerFull);
        adapter = new PostsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        mainPresenter = App.app.getAppComponent().getMainPresenter();
        mainPresenter.attachView(this);
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
        adapter.setPosts(posts);
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
}
