package test.com.genesis.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dima on 07.12.16.
 */
public class Image implements Parcelable {
    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("src")
    @Expose
    public String src;
    @SerializedName("width")
    @Expose
    public Integer width;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.height);
        dest.writeString(this.src);
        dest.writeValue(this.width);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.src = in.readString();
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
