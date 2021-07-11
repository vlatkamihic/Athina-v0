package com.example.athina.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_plan.Plan;
import com.example.athina.database_profile.Feature;
import com.example.athina.ui.ItemTouchHelperAdapter;
import com.example.athina.ui.planner.PlanListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeatureListAdapter extends RecyclerView.Adapter<FeatureListAdapter.FeatureViewHolder> implements  ItemTouchHelperAdapter{

    private final Context context;
    private List<Feature> featureList;
    private OnFeatureRemoved deleteListener;

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

        FeatureViewHolder featureViewHolder = new FeatureViewHolder(view);

        return featureViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FeatureViewHolder holder, int position) {
        holder.feature.setText(this.featureList.get(position).text);
    }

    @Override
    public int getItemCount() {
        return this.featureList.size();
    }



    public class FeatureViewHolder extends RecyclerView.ViewHolder{

        TextView feature;

        public FeatureViewHolder(View view) {
            super(view);

            feature = view.findViewById(R.id.editTextFeature);
        }
    }

    public void setDeleteListener(OnFeatureRemoved listener) {
        deleteListener = listener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {
        Feature feature = featureList.get(position);
        featureList.remove(feature);
        if (deleteListener != null) {
            deleteListener.onFeatureRemoved(feature);
        }
        notifyItemRemoved(position);
    }

    interface OnFeatureRemoved {
        void onFeatureRemoved(Feature feature);
    }

}
