package com.porto.isabel.bakingapp.screens.details.adapter;

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

    private static final int TYPE_INGREDIENTS = 0;
    private static final int TYPE_STEPS = 1;

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
            return TYPE_INGREDIENTS;
        }
        return TYPE_STEPS;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_INGREDIENTS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ingredients_item, parent, false);
            return new IngredientsViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.steps_item, parent, false);
            return new StepsViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_INGREDIENTS) {
            IngredientsViewHolder ingredientsViewHolder = (IngredientsViewHolder) holder;
            ingredientsViewHolder.bind(ingredients);
        } else {
            StepsViewHolder stepsViewHolder = (StepsViewHolder) holder;
            stepsViewHolder.bind(steps.get(position - 1));
        }
    }

    public void setData(Recipe recipe) {
        steps = recipe.steps;
        ingredients = recipe.ingredients;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1; //1 ingredients card + steps
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
            mClickHandler.onClick(steps.get(getAdapterPosition() - 1));
        }

        void bind(Step step) {
            mShortDescriptionTextView.setText(step.shortDescription);
        }
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {


        IngredientsViewHolder(View view) {
            super(view);
        }


        public void bind(List<Ingredient> ingredients) {

        }
    }
}
