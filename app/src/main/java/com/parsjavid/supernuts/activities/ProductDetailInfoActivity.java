package com.parsjavid.supernuts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.Product;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductDetailInfoActivity extends AppCompatActivity {
    @Inject
    Retrofit retrofit;
    private ProgressBar progressBar;
    private Long productId;
    private TextView productTitle;
    private TextView productPrice;
    private TextView productReview;
    private TextView productProvderName;
    private CardView productCardView;
    private ImageView productImage;
    private EditText orderValue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_info);
        Application.getmainComponent().Inject(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBarInProductDetail);

        productCardView = (CardView) findViewById(R.id.productCardView);
        productTitle = (TextView) findViewById(R.id.productTitle);
        productPrice = (TextView) findViewById(R.id.productPrice);
        productImage = (ImageView) findViewById(R.id.productImage);
        orderValue = (EditText) findViewById(R.id.orderValue);

        Intent intent = getIntent();
        productId = intent.getLongExtra("productId", 0);

        //Map<String,Long> data=new HashMap<>();
        //data.put("id",productId);
        Call<Product> call = retrofit.create(ApiInterface.class).LoadProductById(productId);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        productTitle.setText(product.getTitle());
                        if (product.getPrice() != null)
                            productPrice.
                                    setText(String.format(Locale.forLanguageTag("fa-IR"), "%,.0f", product.getPrice().
                                            setScale(2, RoundingMode.DOWN)) + " " + getString(R.string.toman));
                        if (product.getBaseImageFilePath600() != null && product.getBaseImageFilePath600().trim() != "")
                            Picasso.get()
                                    .load(product.getBaseImageFilePath600())
                                    .centerCrop()
                                    .resize(350, 350)
                                    .into(productImage);
                        else
                            productImage.setImageResource(R.drawable.empty_image_600x600);

                        productImage.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "Response = " + t.toString());
            }
        });

    }

    public void clickOrderProduct(View view) {

        Map<String, String> data = new HashMap<>();
        data.put("id", "");
        Call<ResponseBody> call = retrofit.create(ApiInterface.class).SaveProductOrder(data);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    JSONObject result=null;
                    try {
                        result=new JSONObject(response.body().toString().trim());
                        Integer type=Integer.parseInt(result.getString("Type"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
