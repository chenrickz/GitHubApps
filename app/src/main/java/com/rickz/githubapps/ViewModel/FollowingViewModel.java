package com.rickz.githubapps.ViewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rickz.githubapps.R;
import com.rickz.githubapps.Api.Api;
import com.rickz.githubapps.Api.ApiInterface;
import com.rickz.githubapps.Model.Users;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends ViewModel {

    private final MutableLiveData<List<Users>> listFollowing = new MutableLiveData<>();

    public MutableLiveData<List<Users>> getListFollowing() {
        return listFollowing;
    }

    public void setListFollowing(String username, final Context context){
        ApiInterface apiInterface;
        retrofit2.Call<List<Users>> Call;

        try {
            apiInterface = Api.getApi().create(ApiInterface.class);
            Call = apiInterface.getListFollowing(username);
            Call.enqueue(new Callback<List<Users>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Users>> call, Response<List<Users>> response) {

                    Log.d("Response", "" + " " + response.body());
                    List<Users> listUser;
                    listUser = response.body();
                    listFollowing.postValue(listUser);

                    if (listUser.isEmpty()){
                        Toast.makeText(context, context.getString(R.string.belum_ada_following), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<Users>> call, Throwable t) {
                    Log.d("Message", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
