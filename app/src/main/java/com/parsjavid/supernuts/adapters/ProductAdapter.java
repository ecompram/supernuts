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

import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(Product item);
    }

    private List<Product> productList;
    private final OnItemClickListener listener;


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
        public void bind(final Product item,final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    public ProductAdapter(List<Product> productList,OnItemClickListener listener) {
        this.productList = productList;
        this.listener=listener;

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

            if(product.getPrice()!=null)
            holder.price.setText(String.format(Locale.forLanguageTag("fa-IR"), "%,.0f", product.getPrice().
                    setScale(2, RoundingMode.DOWN)));
            holder.supplier.setText(product.getProviderFullName());
            //holder.year.setText(movie.getYear());
            if (product.getBaseImageFilePath200() != null && product.getBaseImageFilePath200().trim() != "")
                Picasso.get()
                        .load(product.getBaseImageFilePath200())
                        .resize(200, 200)
                        .centerCrop()
                        .into(holder.imageView);
            else {
               holder.imageView.setImageResource(R.drawable.empty_image);
            }
            holder.bind(productList.get(position),listener);
        }
    }
    @Override
    public int getItemCount() {
        return productList!=null?productList.size():0;
    }
}