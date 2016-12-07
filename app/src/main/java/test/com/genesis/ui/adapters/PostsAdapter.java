package test.com.genesis.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import test.com.genesis.pojo.Post;
import test.com.genesis.ui.SinglePostFragment;

/**
 * Created by dima on 07.12.16.
 */

public class PostsAdapter extends FragmentStatePagerAdapter {
    private List<Post> posts = new ArrayList<>();

    public PostsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SinglePostFragment.newInstance(posts.get(position));
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }
}
