package com.parsjavid.supernuts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parsjavid.supernuts.activities.ProductDetailInfoActivity;
import com.parsjavid.supernuts.adapters.ProductAdapter;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;
    List<Product> productList;
    RecyclerView productRecyclerView;
    ProductAdapter productAdapter;
    private ProgressBar progressBar;
    private Map<String, String> params = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Application.getmainComponent().Inject(this);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        productList = new ArrayList<>();
        productRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        productRecyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {

                Intent intent=new Intent(MainActivity.this, ProductDetailInfoActivity.class);
                intent.putExtra("productId",item.getId());
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_LONG).show();

            }
        });
        productRecyclerView.setAdapter(productAdapter);
        productRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Call<List<Product>> call = retrofit.create(ApiInterface.class).LoadProducts();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    productList = response.body();
                    Log.d("TAG", "Response = " + productList);
                    productAdapter.setProductList(productList);
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG","Response = "+t.toString());
            }
        });

    }
}
