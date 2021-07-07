package com.example.athina.ui.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.ui.notifications.Notifications;
import com.example.athina.R;
import com.example.athina.database_plan.AppDatabasePlan;
import com.example.athina.database_plan.Plan;
import com.example.athina.databinding.FragmentPlannerBinding;

import java.util.List;

public class PlannerFragment extends Fragment {
    private PlannerViewModel dashboardViewModel;
    private FragmentPlannerBinding binding;
    private PlanListAdapter planListAdapter;
    ItemTouchHelper.SimpleCallback simpleCallback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(PlannerViewModel.class);

        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addPlanBtn = (Button) root.findViewById(R.id.add_plann_button);
        addPlanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                Intent redirect = new Intent(getActivity(), AddPlan.class);
                startActivity(redirect);
            }
        });

        //action_bar_menu called
        setHasOptionsMenu(true);

        //deletePlan();

        initPlanRecyclerView(root);

        loadPlanList();



        return root;
    }

    private void initPlanRecyclerView(View root) {
        RecyclerView planRecyclerView = root.findViewById(R.id.recyclerView_Plan);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        planListAdapter = new PlanListAdapter(this.requireActivity());
        planRecyclerView.setAdapter(planListAdapter);
        //new ItemTouchHelper(simpleCallback).attachToRecyclerView(planRecyclerView);

    }

    private void loadPlanList(){
        AppDatabasePlan databasePlan = AppDatabasePlan.getDBInstance(this.getActivity().getApplicationContext());
        List<Plan> planList = databasePlan.planDao().getAllPlans();
        planListAdapter.setPlanList(planList);
    }

    private void deletePlan(){
        AppDatabasePlan databasePlan = AppDatabasePlan.getDBInstance(this.getActivity().getApplicationContext());
        List<Plan> planList = databasePlan.planDao().getAllPlans();

        planListAdapter.setPlanList(planList);


        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        Plan deletedPlan = null;
                        deletedPlan = planList.get(position);
                        planList.remove(position);
                        planListAdapter.notifyItemRemoved(position);
                        databasePlan.planDao().delete(deletedPlan);
                        break;
                }
            }

        };

    }



    //when clicked on bell icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notify:  {
                Intent intent = new Intent(getActivity(), Notifications.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //action_bar_menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}