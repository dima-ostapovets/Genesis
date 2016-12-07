package test.com.genesis.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 06.12.16.
 */

public class Post implements Parcelable {
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
    @SerializedName("likes")
    public Likes likes;
    @SerializedName("attachments")
    @Expose
    public Attachments attachments;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeString(this.story);
        dest.writeString(this.createdTime);
        dest.writeString(this.id);
        dest.writeParcelable(this.likes, flags);
        dest.writeParcelable(this.attachments, flags);
    }

    public Post() {
    }

    protected Post(Parcel in) {
        this.message = in.readString();
        this.story = in.readString();
        this.createdTime = in.readString();
        this.id = in.readString();
        this.likes = in.readParcelable(Likes.class.getClassLoader());
        this.attachments = in.readParcelable(Attachments.class.getClassLoader());
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
