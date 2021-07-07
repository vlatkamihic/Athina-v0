package com.example.athina.ui.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.athina.R;
import com.example.athina.database_plan.AppDatabasePlan;
import com.example.athina.database_plan.Plan;
import com.example.athina.ui.planner.PlannerFragment;

public class AddPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_a_plan);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        EditText planTitle = findViewById(R.id.et_plan_title_add);
        EditText planDescription = findViewById(R.id.et_plan_description_add);
        Button addPlanButton = findViewById(R.id.add_new_plan);
        addPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planTitle.getText().toString().isEmpty() || planDescription.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Cannot add empty plan!", Toast.LENGTH_SHORT).show();
                }else{
                    saveNewPlan(planTitle.getText().toString(), planDescription.getText().toString());
                }
            }
        });

    }

    private void saveNewPlan(String title, String description){
        AppDatabasePlan database = AppDatabasePlan.getDBInstance(this.getApplicationContext());

        Plan plan = new Plan();
        plan.title = title;
        plan.description = description;
        database.planDao().insertFeature(plan);

        Toast.makeText(getApplicationContext(), "New plan was made!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}