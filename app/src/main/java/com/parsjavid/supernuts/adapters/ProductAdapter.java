package com.parsjavid.supernuts.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parsjavid.supernuts.R;
import com.parsjavid.supernuts.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, price, supplier;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            price = (TextView) view.findViewById(R.id.price);
            supplier=(TextView)view.findViewById(R.id.supplier);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }


    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (productList != null) {
            Product product = productList.get(position);
            holder.title.setText(product.getTitle());
            holder.price.setText(product.getPrice().toString());
            holder.supplier.setText(product.getProviderName());
            //holder.year.setText(movie.getYear());
            if (product.getBaseImageFilePath350() != null && product.getBaseImageFilePath350().trim() != "")
                Picasso.get()
                        .load(product.getBaseImageFilePath350())
                        .resize(50, 50)
                        .centerCrop()
                        .into(holder.imageView);
            else {
               holder.imageView.setImageResource(R.drawable.empty_image);
            }
        }
    }
    @Override
    public int getItemCount() {
        return productList!=null?productList.size():0;
    }
}