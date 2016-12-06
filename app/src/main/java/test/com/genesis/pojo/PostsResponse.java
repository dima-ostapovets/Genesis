package test.com.genesis.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 06.12.16.
 */

public class PostsResponse {
    @SerializedName("data")
    @Expose
    public List<Post> data = new ArrayList<>();
}
