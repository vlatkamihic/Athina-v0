package com.example.athina.ui.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_plan.AppDatabasePlan;
import com.example.athina.database_plan.Plan;
import com.example.athina.database_profile.AppDatabaseProfile;
import com.example.athina.database_profile.Feature;
import com.example.athina.databinding.FragmentProfileBinding;
import com.example.athina.ui.SimpleItemTouchHelperCallback;
import com.example.athina.ui.notifications.Notifications;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements FeatureListAdapter.OnFeatureRemoved{

    private ProfileViewModel notificationsViewModel;
    private FragmentProfileBinding binding;
    private FeatureListAdapter featureListAdapter;
    EditText editName;
    private int clicked = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //action_bar_menu called
        setHasOptionsMenu(true);

        EditText editFeature = (EditText) root.findViewById(R.id.editTextFeatureEdit);

        editName = (EditText) root.findViewById(R.id.name);


        Button addPlanBtn = (Button) root.findViewById(R.id.add_feature_button);
        addPlanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if(editFeature.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Cannot add empty feature!", Toast.LENGTH_SHORT).show();
                }else{
                    // TODO Auto-generated method stub
                    addRowFeature(editFeature);
                    loadFeatureList();
                    if(clicked == 0){
                        Toast.makeText(getContext(), "Hint: Swipe feature to delete :)", Toast.LENGTH_SHORT).show();
                        clicked++;
                    }
                }
            }
        });

        initPlanRecyclerView(root);
        loadFeatureList();

        return root;

    }

    // Fetch the stored data in onResume()
    // Because this is what will be called
    // when the app opens again
    @Override
    public void onResume() {
        super.onResume();

        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sh = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        String s1 = sh.getString("name", "");

        // Setting the fetched data
        // in the EditTexts
        editName.setText(s1);
    }

    // Store the data in the SharedPreference
    // in the onPause() method
    // When the user closes the application
    // onPause() will be called
    // and data will be stored
    @Override
    public void onPause() {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putString("name", editName.getText().toString());
        myEdit.apply();
    }

    private void addRowFeature(EditText editText) {
        AppDatabaseProfile database = AppDatabaseProfile.getDBInstance(this.getActivity().getApplicationContext());

        Feature feature = new Feature();
        feature.text = editText.getText().toString();
        feature.isSet = false;
        database.featureDao().insertFeature(feature);
        editText.clearFocus();
        editText.getText().clear();

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

        featureListAdapter.setDeleteListener(this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(featureListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);

        touchHelper.attachToRecyclerView(featureRecyclerView);


    }

    private void deleteFeature(Feature feature) {
        AppDatabaseProfile databaseProfile = AppDatabaseProfile.getDBInstance(this.getActivity().getApplicationContext());

        databaseProfile.featureDao().delete(feature);
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


    @Override
    public void onFeatureRemoved(Feature feature) {
        deleteFeature(feature);
    }

}