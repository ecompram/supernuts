package com.parsjavid.supernuts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.Product;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetailInfoActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;
    private ProgressBar progressBar;
    private Long productId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_info);
        Application.getmainComponent().Inject(this);
        progressBar=(ProgressBar)findViewById(R.id.progressBarInProductDetail);

        Intent intent=getIntent();
        productId=intent.getLongExtra("productId",0);

        //Map<String,Long> data=new HashMap<>();
        //data.put("id",productId);
        Call<Product> call = retrofit.create(ApiInterface.class).LoadProductById(productId);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    Product product = response.body();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG","Response = "+t.toString());
            }
        });

    }
}
