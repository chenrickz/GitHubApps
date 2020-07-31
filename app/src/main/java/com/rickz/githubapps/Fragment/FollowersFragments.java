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
import com.rickz.githubapps.databinding.FragmentFollowersFragmentsBinding;
import com.rickz.githubapps.Model.Users;
import com.rickz.githubapps.Activity.DetailUserActivity;
import com.rickz.githubapps.ViewModel.FollowersViewModel;

public class FollowersFragments extends Fragment {

    private UserAdapter userAdapter;
    private FragmentFollowersFragmentsBinding binding;

    public static final String EXTRA_FOLLOWERS = "extra_followers";

    public FollowersFragments() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFollowersFragmentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvFollowers.setLayoutManager(new LinearLayoutManager(getContext()));

        assert getArguments() != null;
        String username = getArguments().getString(EXTRA_FOLLOWERS);
        showLoading(false);
        FollowersViewModel followersViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);
        followersViewModel.setListFollowers(username, getContext());

        followersViewModel.getListFollowers().observe(getViewLifecycleOwner(), users -> {
            userAdapter = new UserAdapter();
            userAdapter.notifyDataSetChanged();
            userAdapter.setUsersList(users);
            binding.rvFollowers.setAdapter(userAdapter);
            showLoading(false);

            userAdapter.setOnItemClickCallback((Users data) -> {
                showLoading(true);
                Intent intent = new Intent(getContext(), DetailUserActivity.class);
                intent.putExtra(DetailUserActivity.EXTRA_USER, data);
                startActivity(intent);
            });
        });
    }

    private void showLoading(boolean b) {
        if (b) {
            binding.progressBarDetailFollowers.setVisibility(View.VISIBLE);
        } else {
            binding.progressBarDetailFollowers.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading(false);
    }
}