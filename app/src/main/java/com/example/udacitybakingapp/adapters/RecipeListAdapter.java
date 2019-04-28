package com.example.udacitybakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.udacitybakingapp.R;
import com.example.udacitybakingapp.RecipeWidgetUpdateService;
import com.example.udacitybakingapp.models.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private ArrayList<Recipe> mRecipe;
    private int id;
    private int recipeId;
    private String mRecipeName;
    private Context mContext;
    OnStepClickListener mListener;
    private int lastSelectedPosition = -1;


    public interface OnStepClickListener {
        void onStepSelected(View v, int position, int id);
    }
    public RecipeListAdapter(ArrayList<Recipe> mRecipe, Context context, OnStepClickListener listener) {
        this.mRecipe = mRecipe;
        this.mListener = listener;
        this.mContext = context;
    }

    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.ViewHolder holder, final int position) {
        recipeId = mRecipe.get(position).getId();
        mRecipeName = mRecipe.get(position).getName();

        holder.recipeName.setText(mRecipeName);
        holder.recipeServings.setText(String.valueOf(mRecipe.get(position).getServings()));

        holder.recipeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = mRecipe.get(position).getId();
                mListener.onStepSelected(view, position, id);
            }
        });

        holder.selectedRecipe.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name) TextView recipeName;
        @BindView(R.id.recipe_servings) TextView recipeServings;
        @BindView(R.id.recipe_cardview) CardView recipeCardView;
        @BindView(R.id.radio_button) RadioButton selectedRecipe;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            selectedRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedPosition = getAdapterPosition();
                    RecipeWidgetUpdateService.startActionUpdateRecipe(mContext, lastSelectedPosition + 1, mRecipe.get(lastSelectedPosition).getName(), mRecipe);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
