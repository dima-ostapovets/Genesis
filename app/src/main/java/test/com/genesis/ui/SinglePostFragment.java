package test.com.genesis.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.com.genesis.R;
import test.com.genesis.pojo.Post;
import test.com.genesis.ui.adapters.ImagesAdapter;
import test.com.genesis.ui.adapters.presenter.SinglePostPresenter;

public class SinglePostFragment extends Fragment implements LceView<SinglePostFragment.Model>{
    public static final String POST = "post";
    @Bind(R.id.tvContent)
    TextView tvContent;
    @Bind(R.id.vpPhotos)
    ViewPager vpPhotos;
    private ImagesAdapter adapter;
    private SinglePostPresenter presenter;

    public SinglePostFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Post post = getArguments().getParcelable(POST);
        presenter = new SinglePostPresenter();
        presenter.setPost(post);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_post, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        adapter = new ImagesAdapter(getChildFragmentManager());
        vpPhotos.setAdapter(adapter);
        presenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.detachView();
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public static SinglePostFragment newInstance(Post post) {
        SinglePostFragment fragment = new SinglePostFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(POST, post);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showProgress(boolean show) {

    }

    @Override
    public void showContent(Model model) {
        Post post = model.post;
        tvContent.setText(post.story + "\n" + post.message + "\n" + post.createdTime);
        adapter.setImages(model.images);
    }

    @Override
    public void showError(String error) {

    }

    public static class Model{
        public Post post;
        public List<String> images;

        public Model(Post post, List<String> images) {
            this.post = post;
            this.images = images;
        }
    }
}
