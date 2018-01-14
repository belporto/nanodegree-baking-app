package com.porto.isabel.bakingapp.screens.details.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.porto.isabel.bakingapp.R;
import com.porto.isabel.bakingapp.model.baking.Ingredient;
import com.porto.isabel.bakingapp.model.baking.Recipe;
import com.porto.isabel.bakingapp.model.baking.Step;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Step> steps;
    private List<Ingredient> ingredients;

    private static final int TYPE_INGREDIENTS_TITLE = 0;
    private static final int TYPE_INGREDIENTS = 1;
    private static final int TYPE_STEPS = 2;

    private DetailsAdapterOnClickHandler mClickHandler;

    public interface DetailsAdapterOnClickHandler {
        void onClick(Step step);
    }

    public DetailsAdapter(DetailsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_INGREDIENTS_TITLE;
        } else if ((position - 1) / ingredients.size() < 1) {
            return TYPE_INGREDIENTS;
        }
        return TYPE_STEPS;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_INGREDIENTS_TITLE) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredients_title, parent, false);
            return new IngredientTitleViewHolder(view);
        } else if (viewType == TYPE_INGREDIENTS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredients_item, parent, false);
            return new IngredientViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.steps_item, parent, false);
            return new StepsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_INGREDIENTS) {
            IngredientViewHolder ingredientViewHolder = (IngredientViewHolder) holder;
            ingredientViewHolder.bind(ingredients.get(getIngredientsPosition(position)));
        } else if (holder.getItemViewType() == TYPE_STEPS) {
            StepsViewHolder stepsViewHolder = (StepsViewHolder) holder;
            stepsViewHolder.bind(steps.get(getStepPosition(position)));
        }
    }

    private int getIngredientsPosition(int position) {
        return position - 1;
    }


    private int getStepPosition(int position) {
        return position - 1 - ingredients.size();
    }

    public void setData(Recipe recipe) {
        steps = recipe.steps;
        ingredients = recipe.ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1 + ingredients.size() + steps.size(); // title+ ingredients + steps
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mShortDescriptionTextView;

        StepsViewHolder(View view) {
            super(view);
            mShortDescriptionTextView = view.findViewById(R.id.step_short_desc);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(steps.get(getStepPosition(getAdapterPosition())));
        }

        void bind(Step step) {
            mShortDescriptionTextView.setText(step.shortDescription);
        }
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        final TextView mIngredient;

        IngredientViewHolder(View view) {
            super(view);
            mIngredient = view.findViewById(R.id.ingredient);
        }


        void bind(Ingredient ingredient) {
            Context context = mIngredient.getContext();
            String ingredientInfo = context.getString(R.string.ingredient, ingredient.ingredient, String.valueOf(ingredient.quantity), ingredient.measure);
            mIngredient.setText(ingredientInfo);
        }
    }

    class IngredientTitleViewHolder extends RecyclerView.ViewHolder {

        IngredientTitleViewHolder(View view) {
            super(view);
        }
    }
}
