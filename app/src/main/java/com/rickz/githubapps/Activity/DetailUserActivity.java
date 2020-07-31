package com.rickz.githubapps.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.rickz.githubapps.R;
import com.rickz.githubapps.Adapter.PageAdapter;
import com.rickz.githubapps.Api.Api;
import com.rickz.githubapps.Api.ApiInterface;
import com.rickz.githubapps.databinding.ActivityDetailUserBinding;
import com.rickz.githubapps.Model.Users;

import java.util.Objects;

import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {

    ActivityDetailUserBinding binding;
    public static final String EXTRA_USER = "extra_user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Users users = getIntent().getParcelableExtra(EXTRA_USER);

        assert users != null;
        getDetailUser(users.getLogin());

        Glide.with(getApplicationContext())
                .load(users.getAvatarUrl())
                .apply(new RequestOptions().override(150,150))
                .into(binding.ivDetailUserAvatar);

        PageAdapter pageAdapter = new PageAdapter(this, getSupportFragmentManager(), users);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pageAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        String titleDetail = getString(R.string.titleDetail);
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleDetail);
    }

    private void getDetailUser(String login) {
        ApiInterface apiInterface;
        retrofit2.Call<Users> Call;

        try {
            apiInterface = Api.getApi().create(ApiInterface.class);
            Call = apiInterface.getDetailUser(login);
            Call.enqueue(new Callback<Users>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(retrofit2.Call<Users> call, Response<Users> response) {
                    String followers = getString(R.string.followers);
                    String following = getString(R.string.following);
                    Users users = response.body();
                    assert users != null;
                    binding.tvDetailUserName.setText(users.getLogin());
                    binding.tvDetailName.setText(users.getName());
                    binding.tvDetailBlog.setText(users.getBlog());
                    binding.tvDetailLocation.setText(users.getLocation());
                    binding.tvDetailCompany.setText(users.getCompany());
                    binding.tvDetailUserFollowingFollowers.setText(users.getFollowers() + " " + followers + " " + users.getFollowing() + " " + following);
                    if (users.getBio() != null) {
                        binding.tvDetailUserBio.setText(users.getBio());
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Users> call, Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}