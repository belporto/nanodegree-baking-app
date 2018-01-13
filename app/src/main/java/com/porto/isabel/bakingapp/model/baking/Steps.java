package com.porto.isabel.bakingapp.model.baking;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {

  public long id;
  public String shorDescription;
  public String description;
  public String videoURL;
  public String thumbnailURL;

  protected Steps(Parcel in) {
    id = in.readLong();
    shorDescription = in.readString();
    description = in.readString();
    videoURL = in.readString();
    thumbnailURL = in.readString();
  }

  public static final Creator<Steps> CREATOR = new Creator<Steps>() {
    @Override
    public Steps createFromParcel(Parcel in) {
      return new Steps(in);
    }

    @Override
    public Steps[] newArray(int size) {
      return new Steps[size];
    }
  };

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeLong(id);
    parcel.writeString(shorDescription);
    parcel.writeString(description);
    parcel.writeString(videoURL);
    parcel.writeString(thumbnailURL);
  }
}
