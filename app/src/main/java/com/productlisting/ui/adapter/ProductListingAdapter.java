package com.productlisting.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.productlisting.R;
import com.productlisting.http.apimodel.listing.Product;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This Adapter is to Search Images in Grid View
 */
public class ProductListingAdapter extends RecyclerView.Adapter<ProductListingAdapter.GalleryViewHolder> {

    private List<Product> productList;
    private Context context;
    private final OnListingItemClickListener itemClickListener;
    private final String IMAGE_DOWNLOAD_URL = "http://media.redmart.com/newmedia/200p";

    public ProductListingAdapter(List<Product> items, Context context, OnListingItemClickListener itemClickListener) {
        productList = items;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void setValues(List<Product> mValues) {
        this.productList.addAll(mValues);
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_image_list_item, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        if (productList != null && productList.get(position) != null) {
            Product product = productList.get(position);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.ic_image_error)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide
                    .with(context)
                    .load(IMAGE_DOWNLOAD_URL + product.getImg().getName())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .apply(options)
                    .into(holder.imageView);
            ViewCompat.setTransitionName(holder.imageView, String.valueOf(product.getId()));
            holder.productTitle.setText(product.getTitle());
            holder.productPrice.setText(String.format(context.getString(R.string.item_price),
                    String.valueOf(product.getPricing().getPrice())));
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_Image)
        public ImageView imageView;
        @BindView(R.id.grid_item_loading_indicator)
        public ProgressBar progressBar;
        @BindView(R.id.title)
        public TextView productTitle;
        @BindView(R.id.description)
        public TextView productDesc;
        @BindView(R.id.price)
        public TextView productPrice;

        public GalleryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @OnClick(R.id.list_item)
        public void itemClick() {
            if (itemClickListener != null && productList != null) {
                itemClickListener.onItemClick(productList.get(getAdapterPosition()).getId(), imageView);
            }
        }
    }

    public interface OnListingItemClickListener {
        void onItemClick(Integer productId, ImageView listItem);
    }
}
