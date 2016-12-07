package test.com.genesis.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 07.12.16.
 */
public class Target implements Parcelable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("url")
    @Expose
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.url);
    }

    public Target() {
    }

    protected Target(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Target> CREATOR = new Parcelable.Creator<Target>() {
        @Override
        public Target createFromParcel(Parcel source) {
            return new Target(source);
        }

        @Override
        public Target[] newArray(int size) {
            return new Target[size];
        }
    };
}
