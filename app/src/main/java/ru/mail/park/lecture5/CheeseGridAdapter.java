package ru.mail.park.lecture5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CheeseGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "Adapter";
    static final int ITEM_HEADER = R.layout.item_title;
    static final int ITEM_CHEESE_CARD = R.layout.item_cheese_card;
    static final int INVALID_TYPE = -1;

    private List<Item> items;

    public CheeseGridAdapter(List<Item> itemList) {
        items = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: " + (viewType == ITEM_HEADER ? "HEADER" : "CARD"));
        Context context = parent.getContext();
        switch (viewType) {
            case ITEM_HEADER:
                View titleView = LayoutInflater.from(context).inflate(ITEM_HEADER, parent, false);
                return new TitleViewHolder(titleView);

            case ITEM_CHEESE_CARD:
                View cardView = LayoutInflater.from(context).inflate(ITEM_CHEESE_CARD, parent, false);
                return new CardViewHolder(cardView);

            default:
                throw new IllegalArgumentException("invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);
        Context context = holder.itemView.getContext();

        switch (getItemViewType(position)) {
            case ITEM_HEADER:
                String header = item.getTitle();
                Log.v(TAG, "onBindViewHolder HEADER: " + header);

                ((TitleViewHolder) holder).headingView.setText(header);
                break;

            case ITEM_CHEESE_CARD:
                CardViewHolder cardHolder = ((CardViewHolder) holder);
                String cheese = item.getTitle();

                Log.v(TAG, "onBindViewHolder CARD: " + cheese);

                cardHolder.textView.setText(cheese);
                cardHolder.price.setText(context.getString(R.string.price, ((Cheese) item).getPrice()));

                int imageResId = ((Cheese) item).getImageResId();
                Glide.with(context).load(imageResId).into(cardHolder.imageView);

                boolean isAdded = Cheeses.getShoppingCart().contains(item);
                cardHolder.addRemoveButton.setImageResource(isAdded ?
                        R.drawable.ic_remove_cart : R.drawable.ic_add_cart);

                break;

            default:
                throw new IllegalArgumentException("invalid view type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch(items.get(position).getType()) {
            case CHEESE:
                return ITEM_CHEESE_CARD;
            case HEADER:
                return ITEM_HEADER;
        }
        return INVALID_TYPE;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        ImageView addRemoveButton;
        TextView price;

        CardViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
            price = itemView.findViewById(R.id.price);
            addRemoveButton = itemView.findViewById(R.id.button);

            addRemoveButton.setOnClickListener(new AddRemoveClickListener(this));
        }
    }

    class AddRemoveClickListener implements View.OnClickListener {
        CardViewHolder holder;

        AddRemoveClickListener(CardViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            int position = holder.getLayoutPosition();
            if (position != RecyclerView.NO_POSITION) {
                Cheese item = (Cheese) items.get(position);

                if (Cheeses.getShoppingCart().contains(item)) {
                    Cheeses.getShoppingCart().remove(item);
                } else {
                    Cheeses.getShoppingCart().add(item);
                }
                notifyItemChanged(position);
            }
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        TextView headingView;

        TitleViewHolder(View itemView) {
            super(itemView);
            headingView = itemView.findViewById(R.id.heading);
        }
    }
}
