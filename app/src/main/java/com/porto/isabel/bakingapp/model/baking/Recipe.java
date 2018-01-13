package com.porto.isabel.bakingapp.model.baking;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    public long id;
    public String name;
    public double servings;
    public String image;
    public List<Steps> steps;
    public List<Ingredient> ingredients;

    protected Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
        servings = in.readDouble();
        image = in.readString();
        steps = in.createTypedArrayList(Steps.CREATOR);
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeDouble(servings);
        parcel.writeString(image);
        parcel.writeTypedList(steps);
        parcel.writeTypedList(ingredients);
    }
}
