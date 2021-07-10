package com.example.athina.ui.profile;

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

import com.example.athina.R;
import com.example.athina.databinding.FragmentProfileBinding;
import com.example.athina.ui.notifications.Notifications;

public class ProfileFragment extends Fragment {

    private ProfileViewModel notificationsViewModel;
    private FragmentProfileBinding binding;

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
                Intent redirect = new Intent(getActivity(), AddFeature.class);
                redirect.putExtra("some","some extra");
                onDestroyView();
                startActivity(redirect);
            }
        });


        return root;
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