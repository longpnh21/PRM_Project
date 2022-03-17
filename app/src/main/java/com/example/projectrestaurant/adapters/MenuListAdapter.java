package com.example.projectrestaurant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectrestaurant.R;
import com.example.projectrestaurant.dtos.Item;

import java.util.List;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.MyViewHolder> {

    private List<Item> menu;
    private MenuListClickListener clickListener;

    public MenuListAdapter(List<Item> menu, MenuListClickListener clickListener) {
        this.menu = menu;
        this.clickListener = clickListener;
    }

    public void updateData(List<Item> menuList) {
        this.menu = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuListAdapter.MyViewHolder holder, int position) {
        holder.menuName.setText(menu.get(position).getName());
        holder.menuPrice.setText("Price: $"+ menu.get(position).getPrice());
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item menu  = MenuListAdapter.this.menu.get(position);
                menu.setQuantity(1);
                clickListener.onAddToCartClick(menu);
                holder.addMoreLayout.setVisibility(View.VISIBLE);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(menu.getQuantity()+"");
            }
        });
        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item menu  = MenuListAdapter.this.menu.get(position);
                int total = menu.getQuantity();
                total--;
                if(total > 0 ) {
                    menu.setQuantity(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total +"");
                } else {
                    holder.addMoreLayout.setVisibility(View.GONE);
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    menu.setQuantity(total);
                    clickListener.onRemoveFromCartClick(menu);
                }
            }
        });

        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item menu  = MenuListAdapter.this.menu.get(position);
                int total = menu.getQuantity();
                total++;
                if(total <= 10 ) {
                    menu.setQuantity(total);
                    clickListener.onUpdateCartClick(menu);
                    holder.tvCount.setText(total +"");
                }
            }
        });

//        Glide.with(holder.thumbImage)
//                .load(menu.get(position).getUrl())
//                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return menu.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView menuPrice;
        TextView addToCartButton;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView tvCount;
        LinearLayout addMoreLayout;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.itemName);
            menuPrice = view.findViewById(R.id.itemPrice);
            addToCartButton = view.findViewById(R.id.addToCartButton);
            imageMinus = view.findViewById(R.id.imageMinus);
            imageAddOne = view.findViewById(R.id.imageAddOne);
            tvCount = view.findViewById(R.id.tvCount);

            addMoreLayout = view.findViewById(R.id.addMoreLayout);
        }
    }

    public interface MenuListClickListener {
        public void onAddToCartClick(Item item);

        public void onUpdateCartClick(Item item);

        public void onRemoveFromCartClick(Item item);
    }
}
