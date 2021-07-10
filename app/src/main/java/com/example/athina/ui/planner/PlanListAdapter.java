package com.example.athina.ui.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_plan.Plan;
import com.example.athina.ui.ItemTouchHelperAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    private final Context context;
    private List<Plan> planList;
    private OnItemRemoved deleteListener;

    public PlanListAdapter(Context context){
        this.context = context;
    }

    public void setPlanList(List<Plan> planList){
        this.planList = planList;
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public PlanListAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_cell, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlanListAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(this.planList.get(position).title);
        holder.tvDescription.setText(this.planList.get(position).description);

    }

    @Override
    public int getItemCount() {
        return this.planList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    public void setDeleteListener(OnItemRemoved listener) {
        deleteListener = listener;
    }

    @Override
    public void onItemDismiss(int position) {
        Plan plan = planList.get(position);
        planList.remove(plan);
        if (deleteListener != null) {
            deleteListener.onItemRemoved(plan);
        }
        notifyItemRemoved(position);
    }

    interface OnItemRemoved {
        void onItemRemoved(Plan plan);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvDescription;

        public MyViewHolder(View view){
            super(view);

            tvTitle = view.findViewById(R.id.tv_plan_title);
            tvDescription = view.findViewById(R.id.tv_plan_description);

        }


    }



}
