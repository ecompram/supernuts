package com.parsjavid.supernuts.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.activities.UserProfileActivity;
import com.parsjavid.supernuts.adapters.ProductAdapter;
import com.parsjavid.supernuts.di.HSH;
import com.parsjavid.supernuts.di.MainComponent;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.ApiSuccess;
import com.parsjavid.supernuts.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileProductsFragment extends Fragment {

    @Inject
    Retrofit retrofit;
    RecyclerView productRecyclerView;
    ArrayList<Product> products;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View views = inflater.inflate(R.layout.user_profile_products, container, false);

        productRecyclerView = views.findViewById(R.id.userprofile_listView_product);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        productRecyclerView.setLayoutManager(layoutManager);

        Application.getmainComponent().Inject(this);

        String mobile = Application.preferences.getString(getString(R.string.mobile), "0");
        String token = Application.preferences.getString(getString(R.string.ApToken), "0");


        Map<String,String> data=new HashMap<>();
        data.put("mobile",mobile);
        data.put("token",token);

        Call<ApiSuccess<Product>> apiSuccessCall = retrofit.create(ApiInterface.class).LoadProductsForProvider(data);
        apiSuccessCall.enqueue(new Callback<ApiSuccess<Product>>() {
            @Override
            public void onResponse(Call<ApiSuccess<Product>> call, Response<ApiSuccess<Product>> response) {
                String errorMessage="";
                if(response.isSuccessful()){
                    ApiSuccess<Product> body = response.body();
                    if(body.getErrorMessage()!=null && body.getErrorMessage().equals("")){
                        errorMessage=body.getErrorMessage();
                    }else{
                        List<Product> products=body.getData();
                        ProductAdapter productAdapter=new ProductAdapter(products, new ProductAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Product item) {

                            }
                        });
                        productRecyclerView.setAdapter(productAdapter);
                    }
                }else{
                    errorMessage=getString(R.string.common_loadProblem_message);
                }
                if(errorMessage.equals("")){
                    HSH.showToast(getContext(),errorMessage,HSH.MESSAGE_TYPE_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ApiSuccess<Product>> call, Throwable t) {
                HSH.showToast(getContext(),getString(R.string.common_loadProblem_message),HSH.MESSAGE_TYPE_ERROR);
            }
        });


        return views;
    }

}
