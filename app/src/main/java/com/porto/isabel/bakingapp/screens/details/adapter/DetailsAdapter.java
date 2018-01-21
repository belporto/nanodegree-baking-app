package com.porto.isabel.bakingapp.screens.details.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
    private int mSelectedItemPos;

    private DetailsAdapterOnClickHandler mClickHandler;

    public interface DetailsAdapterOnClickHandler {
        void onClick(int stepPosition);
    }

    public DetailsAdapter(DetailsAdapterOnClickHandler clickHandler, int selectedItem) {
        mClickHandler = clickHandler;
        mSelectedItemPos = selectedItem;
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
            stepsViewHolder.bind(steps.get(getStepPosition(position)), mSelectedItemPos == getStepPosition(position));
        }
    }

    private int getIngredientsPosition(int position) {
        return position - 1;
    }


    private int getStepPosition(int position) {
        return position - 1 - ingredients.size();
    }

    private int getAdapterPositionFromStepPosition(int stepPosition) {
        return stepPosition + 1 + ingredients.size();
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
        final View mStepItem;

        StepsViewHolder(View view) {
            super(view);
            mShortDescriptionTextView = view.findViewById(R.id.step_short_desc);
            mStepItem = view.findViewById(R.id.step_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            if (context.getResources().getBoolean(R.bool.isTablet)) {
                int previousItem = mSelectedItemPos;
                mSelectedItemPos = getStepPosition(getAdapterPosition());

                notifyItemChanged(getAdapterPositionFromStepPosition(previousItem));
                notifyItemChanged(getAdapterPositionFromStepPosition(mSelectedItemPos));
            }

            mClickHandler.onClick(getStepPosition(getAdapterPosition()));
        }

        void bind(Step step, boolean selected) {
            Context context = mShortDescriptionTextView.getContext();
            if (context.getResources().getBoolean(R.bool.isTablet)) {
                if (selected) {
                    mStepItem.setBackgroundColor(ContextCompat.getColor(context, R.color.item_selected_background));
                } else {
                    mStepItem.setBackgroundColor(ContextCompat.getColor(context, R.color.item_default_background));
                }
            }
            mShortDescriptionTextView.setText(step.shortDescription);
        }
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        final TextView mIngredient;
        final TextView mMeasure;

        IngredientViewHolder(View view) {
            super(view);
            mIngredient = view.findViewById(R.id.ingredient);
            mMeasure = view.findViewById(R.id.measure);
        }


        void bind(Ingredient ingredient) {
            Context context = mIngredient.getContext();
            String ingredientInfo = context.getString(R.string.ingredient_measure, String.valueOf(ingredient.quantity), ingredient.measure);
            String ingredientBullet = context.getString(R.string.ingredient, ingredient.ingredient);
            mIngredient.setText(ingredientBullet);
            mMeasure.setText(ingredientInfo);
        }
    }

    class IngredientTitleViewHolder extends RecyclerView.ViewHolder {

        IngredientTitleViewHolder(View view) {
            super(view);
        }
    }
}
