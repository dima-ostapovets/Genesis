package test.com.genesis.ui;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.com.genesis.App;
import test.com.genesis.R;
import test.com.genesis.pojo.Post;

public class MainActivity extends AppCompatActivity implements LceView<List<Post>> {

    @Bind(R.id.vPostPager)
    ViewPager viewPager;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(App.app.getAppComponent().getFbModel());
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

    }

    @Override
    public void showError(String error) {
        Snackbar.make(viewPager, error, Snackbar.LENGTH_SHORT).show();
    }
}
