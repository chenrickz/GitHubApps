package com.rickz.githubapps.ViewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rickz.githubapps.R;
import com.rickz.githubapps.Api.Api;
import com.rickz.githubapps.Api.ApiInterface;
import com.rickz.githubapps.Model.ResponseUser;
import com.rickz.githubapps.Model.Users;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends ViewModel {

    private final MutableLiveData<List<Users>> usersList = new MutableLiveData<>();

    public MutableLiveData<List<Users>> getUsersList() {
        return usersList;
    }

    public void setListUsers(String username, final Context context) {
        ApiInterface apiInterface;
        Call<ResponseUser> call;

        try {
            apiInterface = Api.getApi().create(ApiInterface.class);
            call = apiInterface.getListUser(username);
            call.enqueue(new Callback<ResponseUser>() {
                @Override
                public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                    Log.d("Response", "" + " " + response.body());
                    List<Users> listUser;
                    assert response.body() != null;
                    listUser = response.body().getmResultMember();
                    usersList.postValue(listUser);
                    if (listUser.isEmpty()) {
                        Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseUser> call, Throwable t) {
                    Log.d("Message", Objects.requireNonNull(t.getMessage()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
