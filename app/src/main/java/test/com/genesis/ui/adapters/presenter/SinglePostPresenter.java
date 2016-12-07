package test.com.genesis.ui.adapters.presenter;

import java.util.ArrayList;
import java.util.List;

import test.com.genesis.pojo.Attachment;
import test.com.genesis.pojo.Attachments;
import test.com.genesis.pojo.Post;
import test.com.genesis.ui.LceView;
import test.com.genesis.ui.SinglePostFragment;

/**
 * Created by dima on 07.12.16.
 */

public class SinglePostPresenter extends BasePresenter<LceView<SinglePostFragment.Model>> {
    private Post post;

    @Override
    public void attachView(LceView<SinglePostFragment.Model> view) {
        super.attachView(view);
        view.showContent(new SinglePostFragment.Model(post, getImages(post.attachments)));
    }

    private List<String> getImages(Attachments attachments) {
        List<String> list = new ArrayList<>();
        if (attachments == null || attachments.data == null) return list;
        for (Attachment attachment : attachments.data) {
            if (attachment.type.equals(Attachment.TYPE_PHOTO)
                    || attachment.type.equals(Attachment.TYPE_IMAGE_SHARE)) {
                list.add(attachment.media.image.src);
            } else if (attachment.type.equals(Attachment.TYPE_ALBUM)) {
                list.addAll(getImages(attachment.subattachments));
            }
        }
        return list;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
