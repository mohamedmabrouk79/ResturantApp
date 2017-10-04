package com.example.mohamed.resturantapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohamed.resturantapp.R;
import com.example.mohamed.resturantapp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 04/10/2017.
 */

public class ResturantAdpter  extends RecyclerView.Adapter<ResturantAdpter.ResturantHolder>{
    private Context context;
    private List<Item> items=new ArrayList<>();

    public ResturantAdpter(List<Item> list,Context context){
        this.context=context;
        items=list;
    }

    @Override
    public ResturantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResturantHolder(LayoutInflater.from(context).inflate(R.layout.cart_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ResturantHolder holder, int position) {
        final Item item = items.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());
        holder.price.setText("₹" + item.getPrice());

        Glide.with(context)
                .load(item.getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void RemoveItem(int position){
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void RestoreItem(int position,Item item){
        items.add(position,item);
        notifyItemInserted(position);
    }

   public class ResturantHolder extends RecyclerView.ViewHolder{
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;
        public ResturantHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            price = (TextView) view.findViewById(R.id.price);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            viewBackground = (RelativeLayout) view.findViewById(R.id.view_background);
            viewForeground = (RelativeLayout) view.findViewById(R.id.view_foreground);
        }
    }
}
