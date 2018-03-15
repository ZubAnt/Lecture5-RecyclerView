package ru.mail.park.lecture5;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.v7.widget.RecyclerView.INVALID_TYPE;

/**
 * Created by anton on 15.03.18.
 */

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> items;
    private AppCompatActivity activity;

    static final int ITEM_SUBTOTAL = R.layout.item_subtotal;
    static final int ITEM_CHEESE_ORDER = R.layout.item_cheese_order;

    public OrderAdapter(List<Item> itemList, AppCompatActivity pAactivity) {
        items = itemList;
        activity = pAactivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        switch (viewType) {
            case ITEM_SUBTOTAL:
                View titleView = LayoutInflater.from(context).inflate(ITEM_SUBTOTAL, parent, false);
                return new SubtotalViewHolder(titleView);

            case ITEM_CHEESE_ORDER:
                View orderView = LayoutInflater.from(context).inflate(ITEM_CHEESE_ORDER, parent, false);
                return new OrderAdapter.OrderViewHolder(orderView);

            default:
                throw new IllegalArgumentException("invalid view type");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == items.size())
            return ITEM_SUBTOTAL;
        else
            return ITEM_CHEESE_ORDER;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Context context = holder.itemView.getContext();


        switch (getItemViewType(position)) {
            case ITEM_SUBTOTAL:

                int subtotal = 0;

                for(int i = 0; i < items.size(); i++) {
                    Item sub_item = items.get(i);
                    subtotal += ((Cheese) sub_item).getPrice();
                }
                ((OrderAdapter.SubtotalViewHolder) holder).textView.setText(String.valueOf(subtotal));
                break;

            case ITEM_CHEESE_ORDER:
                Item item = items.get(position);
                OrderAdapter.OrderViewHolder orderHolder = ((OrderAdapter.OrderViewHolder) holder);

                int imageResId = ((Cheese) item).getImageResId();
                Glide.with(context).load(imageResId).into(orderHolder.imageView);

                String cheese = item.getTitle();
                orderHolder.textView.setText(cheese);
                orderHolder.price.setText(context.getString(R.string.price, ((Cheese) item).getPrice()));
                orderHolder.removeButton.setImageResource(R.drawable.ic_remove);

                break;

            default:
                throw new IllegalArgumentException("invalid view type");
        }
    }



    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        ImageView removeButton;
        TextView price;

        OrderViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);

            price = itemView.findViewById(R.id.price);
            removeButton = itemView.findViewById(R.id.remove);

            removeButton.setOnClickListener(new OrderAdapter.RemoveClickListener(this));
        }
    }

    class RemoveClickListener implements View.OnClickListener {

        OrderViewHolder holder;

        RemoveClickListener(OrderViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            int position = holder.getLayoutPosition();
            if (position != RecyclerView.NO_POSITION) {
                Cheese item = (Cheese) items.get(position);

                if (Cheeses.getShoppingCart().contains(item)) {
                    Cheeses.getShoppingCart().remove(item);
                    items.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(items.size());
                    activity.setResult(RESULT_OK);
                }
            }
        }
    }

    class SubtotalViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        SubtotalViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.subtotal);
        }
    }
}
