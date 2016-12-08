package test.com.genesis.presenter;

import java.util.List;

import test.com.genesis.pojo.Post;
import test.com.genesis.ui.SinglePostFragment;
import test.com.genesis.utils.PostUtils;

/**
 * Created by dima on 07.12.16.
 */

public class SinglePostPresenter extends BasePresenter<SinglePostPresenter.SinglePostView> {
    private Post post;

    @Override
    public void attachView(SinglePostPresenter.SinglePostView view) {
        super.attachView(view);
        List<String> images = PostUtils.getImages(post.attachments);
        List<String> toShow = images.size() > 2 ? images.subList(0, 2) : images;
        view.showContent(new SinglePostFragment.Model(post, toShow));
        view.showPreviewPager(toShow.size() > 0);
    }


    public void setPost(Post post) {
        this.post = post;
    }

    public interface SinglePostView extends LceView<SinglePostFragment.Model> {
        void showPreviewPager(boolean show);
    }
}
