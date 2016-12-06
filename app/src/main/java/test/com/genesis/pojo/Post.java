package test.com.genesis.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 06.12.16.
 */

public class Post {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("story")
    @Expose
    public String story;
    @SerializedName("created_time")
    @Expose
    public String createdTime;
    @SerializedName("id")
    @Expose
    public String id;
}
