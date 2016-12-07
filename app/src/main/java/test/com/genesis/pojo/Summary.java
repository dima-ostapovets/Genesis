package test.com.genesis.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 07.12.16.
 */
public class Summary implements Parcelable {
    @SerializedName("total_count")
    @Expose
    public Integer totalCount;
    @SerializedName("can_like")
    @Expose
    public Boolean canLike;
    @SerializedName("has_liked")
    @Expose
    public Boolean hasLiked;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalCount);
        dest.writeValue(this.canLike);
        dest.writeValue(this.hasLiked);
    }

    public Summary() {
    }

    protected Summary(Parcel in) {
        this.totalCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.canLike = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.hasLiked = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<Summary> CREATOR = new Parcelable.Creator<Summary>() {
        @Override
        public Summary createFromParcel(Parcel source) {
            return new Summary(source);
        }

        @Override
        public Summary[] newArray(int size) {
            return new Summary[size];
        }
    };
}
