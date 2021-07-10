package com.example.athina.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_profile.Feature;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FeatureListAdapter extends RecyclerView.Adapter<FeatureListAdapter.FeatureViewHolder>{

    private final Context context;
    private List<Feature> featureList;
    //boolean isClicked = false;
    Dialog featureDialog;

    public FeatureListAdapter(Context context){
        this.context = context;
    }

    public void setFeatureList(List<Feature> featureList){
        this.featureList = featureList;
        notifyDataSetChanged();
    }

    public List<Feature> getFeatureList(){
        return  this.featureList;
    }

    @NonNull
    @NotNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feature_cell, parent, false);

        FeatureViewHolder featureViewHolder = new FeatureViewHolder(view);

        featureViewHolder.buttonFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "testing", Toast.LENGTH_SHORT).show();
            }
        });

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

        EditText feature;
        ImageButton buttonFeature;

        public FeatureViewHolder(View view) {
            super(view);

            feature = view.findViewById(R.id.editTextFeature);
            feature.setEnabled(false);
            buttonFeature = view.findViewById(R.id.imageButtonFeature);


            /*buttonFeature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isClicked == false){
                        feature.setEnabled(true);
                        isClicked = true;

                    }else{
                        feature.setEnabled(false);
                        isClicked = false;

                        Feature featuree = new Feature();
                        featuree.text =  feature.getText().toString();
                        featuree.isSet = true;
                        featureList.set(getPosition(), featuree);

                        notifyDataSetChanged();
                    }
                }
            });*/
        }
    }
}
