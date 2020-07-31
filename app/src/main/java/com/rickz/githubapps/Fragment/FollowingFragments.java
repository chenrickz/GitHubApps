package com.rickz.githubapps.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rickz.githubapps.Adapter.UserAdapter;
import com.rickz.githubapps.databinding.FragmentFollowingFragmentsBinding;
import com.rickz.githubapps.Model.Users;
import com.rickz.githubapps.Activity.DetailUserActivity;
import com.rickz.githubapps.ViewModel.FollowingViewModel;


public class FollowingFragments extends Fragment {

    private UserAdapter userAdapter;
    private FragmentFollowingFragmentsBinding binding;

    public static final String EXTRA_FOLLOWING = "extra_following";

    public FollowingFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@Nullable LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        binding = FragmentFollowingFragmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvFollowing.setLayoutManager(new LinearLayoutManager(getContext()));
        assert getArguments() != null;
        String username = getArguments().getString(EXTRA_FOLLOWING);


        showLoading(false);
        FollowingViewModel followingViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);
        followingViewModel.setListFollowing(username, getContext());
        followingViewModel.getListFollowing().observe(getViewLifecycleOwner(), users -> {
            userAdapter = new UserAdapter();
            userAdapter.notifyDataSetChanged();
            userAdapter.setUsersList(users);

            binding.rvFollowing.setAdapter(userAdapter);
            showLoading(false);
            userAdapter.setOnItemClickCallback((Users data) -> {
                showLoading(true);
                Intent goToDetailUser = new Intent(getContext(), DetailUserActivity.class);
                goToDetailUser.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(goToDetailUser);
            });
        });
    }

    private void showLoading(boolean b) {
        if (b) {
            binding.progressBarDetailFollowing.setVisibility(View.VISIBLE);
        } else {
            binding.progressBarDetailFollowing.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
    }
}