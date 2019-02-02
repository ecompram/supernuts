package com.parsjavid.supernuts.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.components.FormatHelper;
import com.parsjavid.supernuts.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_info_for_save);

        productPrice = findViewById(R.id.txtProductPrice);
        productDescription = findViewById(R.id.txtProductDescription);
        productCount = findViewById(R.id.txtProductCount);
        productName=findViewById(R.id.txtProductName);

        productImage=findViewById(R.id.productImage);

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

        if(filePath!=null) {
            File file = new File(filePath);
            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
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
            RequestBody priceBody = RequestBody.create(MediaType.parse("text/plain"), productPrice.getText().toString());
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), productName.getText().toString());
            RequestBody descBody = RequestBody.create(MediaType.parse("text/plain"), productDescription.getText().toString());
            RequestBody countBody = RequestBody.create(MediaType.parse("text/plain"), productCount.getText().toString());
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
}
