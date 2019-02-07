package com.parsjavid.supernuts.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.components.FormatHelper;
import com.parsjavid.supernuts.Application;
import com.parsjavid.supernuts.MainActivity;
import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.adapters.CustomEntityAdapter;
import com.parsjavid.supernuts.di.HSH;
import com.parsjavid.supernuts.interfaces.ApiInterface;
import com.parsjavid.supernuts.models.ApiSuccess;
import com.parsjavid.supernuts.models.Product;
import com.parsjavid.supernuts.models.ProductGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductInfoForSaveActivity extends BaseActivity {

    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    private boolean et_change = false;
    private EditText productPrice;
    private EditText productName;
    private EditText productDescription;
    private EditText productCount;
    String filePath = null;
    private ImageView productImage;
    Uri imageUri;
    private Bitmap bitmap;
    Spinner productCombo;
    @Inject
    Retrofit retrofit;
    private long productGroupId;
    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_info_for_save);

        productPrice = findViewById(R.id.txtProductPrice);
        productDescription = findViewById(R.id.txtProductDescription);
        productCount = findViewById(R.id.txtProductCount);
        productName=findViewById(R.id.txtProductName);
        productImage=findViewById(R.id.productImage);
        progressBar=findViewById(R.id.saveProductProgressBar);
        progressBar.setVisibility(View.GONE);

        Application.getmainComponent().Inject(this);

        productCombo = (Spinner) findViewById(R.id.productComboId);

        List<ProductGroup> productGroups=new ArrayList<>();
        productGroups.add(new ProductGroup(ProductGroup.Pistachio,"پسته"));
        productGroups.add(new ProductGroup(ProductGroup.Pistachio_Kernel,"مغز پسته"));
        productGroups.add(new ProductGroup(ProductGroup.Walnut,"گردو"));
        productGroups.add(new ProductGroup(ProductGroup.Almond,"بادام"));

        CustomEntityAdapter<ProductGroup> productGroupCustomEntityAdapter=new CustomEntityAdapter<>(this,productGroups);

        productCombo.setAdapter(productGroupCustomEntityAdapter );
        productCombo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                productGroupId = productGroups.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }
    public void clickProductImageForUpload(final View view){
        try {
            Intent gintent = new Intent();
            gintent.setType("image/*");
            gintent.setAction(Intent.ACTION_PICK);
            startActivityForResult(
                    Intent.createChooser(gintent, "تصویر را انتخاب نمایید"),
                    PICK_IMAGE);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;

        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
		 		    	/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if(selectedImageUri != null){
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
    public void clickSaveProductInfo(final View view ){

        File file =null;
        if(filePath!=null) {
            file = new File(filePath);
        }
        if(productName.getText().toString().trim().equals(""))
            productName.setError("لطفا نام کالا را وارد نمایید.");
        else if(productCount.getText().toString().trim().equals(""))
            productCount.setError("لطفا مقدار کالا را وارد نمایید.");

        else if(productPrice.getText().toString().trim().equals(""))
            productPrice.setError("لطفا قیمت کالا را وارد نمایید.");

        else if(productDescription.getText().toString().trim().equals(""))
            productDescription.setError("لطفا شرح کالا را وارد نمایید.");
        else {

            String mobile = Application.preferences.getString(getString(R.string.mobile), "0");
            String token = Application.preferences.getString(getString(R.string.ApToken), "0");

            //productCombo.getSelectedItemId()

            RequestBody priceBody = RequestBody.create(okhttp3.MultipartBody.FORM, productPrice.getText().toString());
            RequestBody nameBody = RequestBody.create(okhttp3.MultipartBody.FORM, productName.getText().toString());
            RequestBody descBody = RequestBody.create(okhttp3.MultipartBody.FORM, productDescription.getText().toString());
            RequestBody countBody = RequestBody.create(okhttp3.MultipartBody.FORM, productCount.getText().toString());
            RequestBody productBaseGroupBody = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(productGroupId));
            RequestBody mobileBody = RequestBody.create(okhttp3.MultipartBody.FORM, mobile);
            RequestBody tokenBody = RequestBody.create(okhttp3.MultipartBody.FORM, token);



            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);

            MultipartBody.Part multipartBody =MultipartBody.Part.createFormData("file",file.getName(),requestFile);

            progressBar.setVisibility(View.VISIBLE);
            // finally, execute the request
            Call<ApiSuccess> apiSuccessCall = retrofit.create(ApiInterface.class).SaveProduct(multipartBody,tokenBody,mobileBody,
                    nameBody,descBody,countBody,priceBody,productBaseGroupBody);
            apiSuccessCall.enqueue(new Callback<ApiSuccess>() {
                @Override
                public void onResponse(Call<ApiSuccess> call, Response<ApiSuccess> response) {
                    progressBar.setVisibility(View.GONE);

                    new AlertDialog.Builder(ProductInfoForSaveActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                            .setCancelable(false)
                            .setMessage(getString(R.string.common_saveProduct_message))
                            .setPositiveButton(getString(R.string.common_confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(ProductInfoForSaveActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }

                @Override
                public void onFailure(Call<ApiSuccess> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    HSH.showToast(ProductInfoForSaveActivity.this,getString(R.string.common_saveProblem_message),HSH.MESSAGE_TYPE_ERROR);
                }
            });
        }
    }
    private String getPath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    private void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        productImage.setImageBitmap(bitmap);

    }
/*
    @Override
    protected int getLayoutId() {
        return R.layout.product_info_for_save;
    }
*/
}
