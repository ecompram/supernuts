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

import com.google.android.gms.common.util.NumberUtils;
import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.di.HSH;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.ApiSuccess;
import com.parsjavid.supernuts.models.Product;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private TextView providerFullNameTV;
    private TextView minimumOrderValueTV;
    private TextView stockValueTV;
    private TextView productReview;
    private TextView productProvderName;
    private CardView productCardView;
    private ImageView productImage;
    private EditText orderValueET;
    private EditText ProposedPriceET;

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
        orderValueET = (EditText) findViewById(R.id.orderValue);
        ProposedPriceET = (EditText) findViewById(R.id.proposedPrice);
        providerFullNameTV=(TextView)findViewById(R.id.providerFullName);
        minimumOrderValueTV=(TextView)findViewById(R.id.minimumOrderValue);
        stockValueTV=(TextView)findViewById(R.id.stockValue);

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
                        providerFullNameTV.setText(product.getProviderFullName());
                        minimumOrderValueTV.setText(product.getMinimumOrderValue()==null ?"1":product.getMinimumOrderValue().toString());
                        stockValueTV.setText(product.getStockValue()==null?getString(R.string.unlimited):product.getStockValue().toString());
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

    /**
     * ثبت سفارش
     * @param view
     */
    public void clickOrderProduct(View view) {

        String orderCount= orderValueET.getText().toString();
        String ProposedPrice= ProposedPriceET.getText().toString();

        Map<String, String> data = new HashMap<>();
        data.put("orderValue", orderCount);
        data.put("productId", productId.toString());
        data.put("proposedPrice",ProposedPrice);

        String mobile=Application.preferences.getString(getString(R.string.mobile),"");
        String token=Application.preferences.getString(getString(R.string.ApToken), "");

        String errorMessage="";
        if(mobile==null || mobile.trim().equals("") || token==null || token.trim().equals(""))
        {
            errorMessage=getString(R.string.common_systemUser_invalid);
        }
        if(!NumberUtils.isNumeric(orderCount) || Integer.parseInt(orderCount)<=0)
            errorMessage=getString(R.string.productOrder_saveOrder_orderCountInvalid);

        if(productId ==null || productId<=0)
            errorMessage=getString(R.string.productOrder_saveOrder_productInvalid);

        if(errorMessage=="") {

            data.put("mobileNumber", mobile);
            data.put("token", token);

            Call<ApiSuccess> call = retrofit.create(ApiInterface.class).SaveProductOrder(data);
            progressBar.setVisibility(View.VISIBLE);

            call.enqueue(new Callback<ApiSuccess>() {
                @Override
                public void onResponse(Call<ApiSuccess> call, Response<ApiSuccess> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiSuccess result = null;

                            result = response.body();
                            Integer type = result.getResultKey();
                            String message = result.getMessage();
                            if (type > 0) {
                                //HSH.getInstance().editor(getString(R.string.systemUser_latest_order), type.toString());
                                final SweetAlertDialog dialog = new SweetAlertDialog(ProductDetailInfoActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                dialog.setTitleText("");
                                dialog.setContentText(getString(R.string.productOrder_saveOrder_successMessage, type));
                                dialog.setConfirmText(getString(R.string.common_confirm));

                                dialog.setConfirmClickListener((SweetAlertDialog sDialog) -> {
                                    dialog.dismiss();
                                    Intent intent = new Intent(ProductDetailInfoActivity.this, MainActivity.class);
                                    startActivity(intent);
                                });

                                HSH.getInstance().dialog(dialog);

                            } else {
                                HSH.getInstance().showToast(ProductDetailInfoActivity.this, message == null ? getString(R.string.productOrder_saveOrderProblem_message) : message);
                            }

                    } else {
                        HSH.getInstance().showToast(ProductDetailInfoActivity.this, getString(R.string.productOrder_saveOrderProblem_message));
                        //HSH.getInstance().editor(getString(R.string.systemUser_latest_order), getString(R.string.productOrder_saveOrderProblem_message));
                    }
                }

                @Override
                public void onFailure(Call<ApiSuccess> call, Throwable t) {
                    //HSH.getInstance().editor(getString(R.string.systemUser_latest_order), getString(R.string.productOrder_saveOrderProblem_message));
                    HSH.getInstance().showToast(ProductDetailInfoActivity.this, getString(R.string.productOrder_saveOrderProblem_message));
                }
            });
        }else{
           // HSH.getInstance().editor(getString(R.string.systemUser_latest_order), errorMessage);
            HSH.getInstance().showToast(ProductDetailInfoActivity.this, errorMessage);
        }
    }
}
