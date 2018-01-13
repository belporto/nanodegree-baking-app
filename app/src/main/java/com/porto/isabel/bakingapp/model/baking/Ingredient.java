package com.porto.isabel.bakingapp.model.baking;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {

  public double quantity;
  public String measure;
  public String ingredient;

  protected Ingredient(Parcel in) {
    quantity = in.readDouble();
    measure = in.readString();
    ingredient = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(quantity);
    dest.writeString(measure);
    dest.writeString(ingredient);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
    @Override
    public Ingredient createFromParcel(Parcel in) {
      return new Ingredient(in);
    }

    @Override
    public Ingredient[] newArray(int size) {
      return new Ingredient[size];
    }
  };
}
