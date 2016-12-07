package test.com.genesis.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.com.genesis.R;
import test.com.genesis.pojo.Post;

public class ImageFragment extends Fragment {

    public static final String IMAGE = "image";

    @Bind(R.id.iv)
    ImageView imageView;
    private String image;

    public ImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        image = getArguments().getString(IMAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        loadImage();
    }

    private void loadImage() {
        Picasso.with(getActivity())
                .load(image)
                .centerCrop()
                .fit()
                .into(imageView);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    public static ImageFragment newInstance(String image) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE, image);
        fragment.setArguments(bundle);
        return fragment;
    }
}
