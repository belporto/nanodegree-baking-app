package com.porto.isabel.bakingapp.screens.recipes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private List<Recipe> mRecipes = new ArrayList<>();
    private Context mContext;
    private RecipesAdapterOnClickHandler mClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipesAdapter(RecipesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_item, parent, false);

        return new RecipesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesAdapterViewHolder holder, int position) {
        Recipe recipe = mRecipes.get(position);

        if (recipe.image != null && !recipe.image.isEmpty()) {
            Picasso.with(mContext)
                    .load(recipe.image)
                    .placeholder(R.drawable.recipe_placeholder)
                    .error(R.drawable.recipe_placeholder)
                    .into(holder.mRecipeImage);
        } else {
            holder.mRecipeImage.setImageResource(R.drawable.recipe_placeholder);
        }

        holder.mNameTextView.setText(recipe.name);
        //TODO: resources with plurals
        holder.mServingTextView.setText("Servings: " + recipe.servings);
    }

    public void setData(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mRecipeImage;
        final TextView mNameTextView;
        final TextView mServingTextView;

        RecipesAdapterViewHolder(View view) {
            super(view);
            mRecipeImage = (ImageView) view.findViewById(R.id.recipe_image);
            mNameTextView = (TextView) view.findViewById(R.id.recipe_name);
            mServingTextView = (TextView) view.findViewById(R.id.recipe_servings);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mRecipes.get(getAdapterPosition()));
        }
    }
}
