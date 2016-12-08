package test.com.genesis.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import test.com.genesis.pojo.Post;
import test.com.genesis.ui.ImageFragment;
import test.com.genesis.ui.SinglePostFragment;

/**
 * Created by dima on 07.12.16.
 */

public class ImagesAdapter extends FragmentStatePagerAdapter {
    private List<String> images = new ArrayList<>();

    public ImagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public void setImages(List<String> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
