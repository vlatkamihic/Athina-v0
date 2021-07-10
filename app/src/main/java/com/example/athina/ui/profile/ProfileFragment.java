package com.example.athina.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_plan.AppDatabasePlan;
import com.example.athina.database_plan.Plan;
import com.example.athina.database_profile.AppDatabaseProfile;
import com.example.athina.database_profile.Feature;
import com.example.athina.databinding.FragmentProfileBinding;
import com.example.athina.ui.notifications.Notifications;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel notificationsViewModel;
    private FragmentProfileBinding binding;
    private FeatureListAdapter featureListAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //action_bar_menu called
        setHasOptionsMenu(true);

        Button addPlanBtn = (Button) root.findViewById(R.id.add_feature_button);
        addPlanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                addRowFeature();
                loadFeatureList();
            }
        });

        initPlanRecyclerView(root);
        loadFeatureList();

        return root;
    }

    private void addRowFeature() {
        AppDatabaseProfile database = AppDatabaseProfile.getDBInstance(getActivity().getApplicationContext());

        Feature feature = new Feature();
        feature.text = "Input text";
        feature.isSet = false;
        database.featureDao().insertFeature(feature);

        Toast.makeText(getActivity().getApplicationContext(), "New feature added!", Toast.LENGTH_SHORT).show();

    }

    private void loadFeatureList() {
        AppDatabaseProfile databaseProfile = AppDatabaseProfile.getDBInstance(this.getActivity().getApplicationContext());
        List<Feature> featureList = databaseProfile.featureDao().getAllFeatures();
        featureListAdapter.setFeatureList(featureList);
    }

    private void initPlanRecyclerView(View root) {
        RecyclerView featureRecyclerView = root.findViewById(R.id.recyclerView_Feature);
        featureRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        featureListAdapter = new FeatureListAdapter(this.requireActivity());
        featureRecyclerView.setAdapter(featureListAdapter);



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

    @Override
    public void onStart() {
        super.onStart();
        loadFeatureList();
    }
}