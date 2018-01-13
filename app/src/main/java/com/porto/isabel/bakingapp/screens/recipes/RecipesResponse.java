package com.porto.isabel.bakingapp.screens.recipes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.porto.isabel.bakingapp.common.Status;
import com.porto.isabel.bakingapp.model.baking.Recipe;

import java.util.List;

import static com.porto.isabel.bakingapp.common.Status.ERROR;
import static com.porto.isabel.bakingapp.common.Status.LOADING;
import static com.porto.isabel.bakingapp.common.Status.SUCCESS;

public class RecipesResponse {

    public final Status status;

    @Nullable
    public final List<Recipe> data;

    @Nullable
    public final Throwable error;

    private RecipesResponse(Status status, @Nullable List<Recipe> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static RecipesResponse loading() {
        return new RecipesResponse(LOADING, null, null);
    }

    public static RecipesResponse success(@NonNull List<Recipe> data) {
        return new RecipesResponse(SUCCESS, data, null);
    }

    public static RecipesResponse error(@NonNull Throwable error) {
        return new RecipesResponse(ERROR, null, error);
    }
}