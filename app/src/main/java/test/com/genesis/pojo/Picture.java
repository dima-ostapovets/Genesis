package test.com.genesis.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 08.12.16.
 */
public class Picture {
    @SerializedName("data")
    @Expose
    public Data data;

    public static class Data {
        @SerializedName("is_silhouette")
        @Expose
        public Boolean isSilhouette;
        @SerializedName("url")
        @Expose
        public String url;
    }
}
