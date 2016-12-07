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
public class Attachments implements Parcelable {
    @SerializedName("data")
    @Expose
    public List<Attachment> data = new ArrayList<>();

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public Attachments() {
    }

    protected Attachments(Parcel in) {
        this.data = new ArrayList<Attachment>();
        in.readList(this.data, Attachment.class.getClassLoader());
    }

    public static final Parcelable.Creator<Attachments> CREATOR = new Parcelable.Creator<Attachments>() {
        @Override
        public Attachments createFromParcel(Parcel source) {
            return new Attachments(source);
        }

        @Override
        public Attachments[] newArray(int size) {
            return new Attachments[size];
        }
    };
}
