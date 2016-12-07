package test.com.genesis.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 07.12.16.
 */
public class Attachment implements Parcelable {
    public static final String TYPE_ALBUM = "album";
    public static final String TYPE_PHOTO = "photo";
    public static final String TYPE_IMAGE_SHARE = "image_share";
    @SerializedName("subattachments")
    @Expose
    public Attachments subattachments;
    @SerializedName("target")
    @Expose
    public Target target;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("media")
    @Expose
    public Media media;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.subattachments, flags);
        dest.writeParcelable(this.target, flags);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeParcelable(this.media, flags);
    }

    public Attachment() {
    }

    protected Attachment(Parcel in) {
        this.subattachments = in.readParcelable(Attachments.class.getClassLoader());
        this.target = in.readParcelable(Target.class.getClassLoader());
        this.title = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.media = in.readParcelable(Media.class.getClassLoader());
    }

    public static final Parcelable.Creator<Attachment> CREATOR = new Parcelable.Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel source) {
            return new Attachment(source);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };
}
