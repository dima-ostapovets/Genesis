package test.com.genesis.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima on 07.12.16.
 */

public class Likes implements Parcelable {
    @SerializedName("summary")
    @Expose
    public Summary summary;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.summary, flags);
    }

    public Likes() {
    }

    protected Likes(Parcel in) {
        this.summary = in.readParcelable(Summary.class.getClassLoader());
    }

    public static final Parcelable.Creator<Likes> CREATOR = new Parcelable.Creator<Likes>() {
        @Override
        public Likes createFromParcel(Parcel source) {
            return new Likes(source);
        }

        @Override
        public Likes[] newArray(int size) {
            return new Likes[size];
        }
    };
}
