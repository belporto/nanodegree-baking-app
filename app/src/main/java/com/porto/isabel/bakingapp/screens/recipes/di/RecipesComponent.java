package com.porto.isabel.bakingapp.screens.recipes.di;


import com.porto.isabel.bakingapp.di.AppComponent;
import com.porto.isabel.bakingapp.screens.recipes.RecipesActivity;

import dagger.Component;

@RecipesScope
@Component(modules = {RecipesModule.class},
        dependencies = {AppComponent.class})
public interface RecipesComponent {

    void inject(RecipesActivity activity);

}