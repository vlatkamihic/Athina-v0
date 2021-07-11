package com.example.athina.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.athina.R;
import com.example.athina.database_profile.AppDatabaseProfile;
import com.example.athina.database_profile.Feature;

public class EditFeatureDialog extends DialogFragment {


    private EditText mEditText;
    private Button saveButton;
    private Button deleteButton;
    private FeatureListAdapter editListener;

    public EditFeatureDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditFeatureDialog newInstance(String title) {
        EditFeatureDialog frag = new EditFeatureDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feature_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.etSaveFeature);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        saveButton = (Button) view.findViewById(R.id.saveFeature);
        deleteButton = (Button) view.findViewById(R.id.deleteFeature);

    }

   /* @Override
    public void onItemClick(Feature feature) {
        editFeatureOnClick(feature);
    }

    private void editFeatureOnClick(Feature feature) {

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/



}
