package com.iteso.android_tarea6;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteso.android_tarea6.beans.Category;
import com.iteso.android_tarea6.beans.ItemProduct;
import com.iteso.android_tarea6.beans.Store;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private ArrayList<ItemProduct> mDataSet;
    private ArrayList<Store> storesSet;
    private ArrayList<Category> categoriesSet;
    private Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterProduct(Context context, ArrayList<ItemProduct> myDataSet) {
        mDataSet = myDataSet;
        this.context = context;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


public static class ViewHolder extends RecyclerView.ViewHolder {
    public Button mDetail;
    public CardView mCardView;
    public TextView mProductTitle;
    public TextView mProductStore;
    public TextView mProductLocation;
    public TextView mProductPhone;
    public ImageView mProductImage;
    public ImageView mProductThumbnail;
    public RelativeLayout mEventLayout;
    public Context context;

    public ViewHolder(View v) {
        super(v);
        context = v.getContext();
        mCardView = (CardView) v.findViewById(R.id.card_view);
        mEventLayout = (RelativeLayout) v.findViewById(R.id.item_product_layout);
        mDetail = (Button) v.findViewById(R.id.item_product_detail);
        mProductTitle = (TextView) v.findViewById(R.id.item_product_title);
        mProductStore = (TextView) v.findViewById(R.id.item_product_store);
        mProductLocation = (TextView) v.findViewById(R.id.item_product_location);
        mProductPhone = (TextView) v.findViewById(R.id.item_product_phone);
        mProductImage = (ImageView) v.findViewById(R.id.item_product_image);
        mProductThumbnail = (ImageView) v.findViewById(R.id.item_product_thumbnail);
    }
}

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mProductTitle.setText(mDataSet.get(position).getTitle());
        holder.mProductStore.setText(mDataSet.get(position).getStore().getName());
        holder.mProductLocation.setText(mDataSet.get(position).getStore().getCity().getName());
        holder.mProductPhone.setText(mDataSet.get(position).getStore().getPhone());
        holder.mProductPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:" + mDataSet.get(position).getStore().getPhone()));
                context.startActivity(intent);
            }
        });
        switch(mDataSet.get(position).getImage()){
            case 0:
                holder.mProductImage.setImageResource(R.drawable.alienware); break;
            case 1:
                holder.mProductImage.setImageResource(R.drawable.mac); break;
            case 2:
                holder.mProductImage.setImageResource(R.drawable.pillows); break;
            case 3:
                holder.mProductImage.setImageResource(R.drawable.refrigerator); break;
            case 4:
                holder.mProductImage.setImageResource(R.drawable.micro); break;
        }
        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, mDataSet.get(position).toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
        Bitmap bitmap = ((BitmapDrawable)holder.mProductThumbnail.getDrawable()).getBitmap();
        holder.mProductThumbnail.setImageBitmap(bitmap);
        holder.mEventLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ActivityProduct.class);
//                intent.putExtra(Constants.PRODUCT, mDataSet.get(position));
//                int category = mDataSet.get(position).getCategory().getId();
//                ((ActivityMain) context).startActivityForResult(intent, category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}