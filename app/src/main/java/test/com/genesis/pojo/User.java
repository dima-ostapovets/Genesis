package test.com.genesis.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 08.12.16.
 */

public class User {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("picture")
    @Expose
    public Picture picture;
    @SerializedName("name")
    @Expose
    public String name;
}
