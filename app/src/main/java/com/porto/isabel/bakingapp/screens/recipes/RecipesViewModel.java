package com.porto.isabel.bakingapp.screens.recipes;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.porto.isabel.bakingapp.network.BakingApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RecipesViewModel extends ViewModel {

    private final BakingApi mBakingApi;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<RecipesResponse> response = new MutableLiveData<>();

    public RecipesViewModel(BakingApi bakingApi) {
        this.mBakingApi = bakingApi;
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    MutableLiveData<RecipesResponse> response() {
        return response;
    }

    public void loadRecipes() {
        disposables.add(mBakingApi.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> response.setValue(RecipesResponse.loading()))
                .subscribe(recipes -> response.setValue(RecipesResponse.success(recipes)),
                        throwable -> response.setValue(RecipesResponse.error(throwable))));
    }
}
