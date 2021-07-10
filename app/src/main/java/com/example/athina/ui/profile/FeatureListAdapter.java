package com.example.athina.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_plan.Plan;
import com.example.athina.database_profile.Feature;
import com.example.athina.ui.planner.PlanListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeatureListAdapter extends RecyclerView.Adapter<FeatureListAdapter.FeatureViewHolder>{

    private final Context context;
    private List<Feature> featureList;

    public FeatureListAdapter(Context context){
        this.context = context;
    }

    public void setFeatureList(List<Feature> featureList){
        this.featureList = featureList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feature_cell, parent, false);

        return new FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeatureViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder{

        EditText feature;
        ImageButton buttonFeature;

        public FeatureViewHolder(View view) {
            super(view);

            feature = view.findViewById(R.id.editTextFeature);
            buttonFeature = view.findViewById(R.id.imageButtonFeature);
        }
    }
}
