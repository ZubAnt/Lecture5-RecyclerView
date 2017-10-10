package ru.mail.park.lecture5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class CheeseGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int ITEM_HEADER = R.layout.item_title;
    static final int ITEM_CHEESE_CARD = R.layout.item_cheese_card;
    static final int INVALID_TYPE = -1;

    private List<Item> items;

    public CheeseGridAdapter(List<Item> itemList) {
        items = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
                ((TitleViewHolder) holder).headingView.setText(item.getTitle());
                break;

            case ITEM_CHEESE_CARD:
                CardViewHolder cardHolder = ((CardViewHolder) holder);
                String cheese = item.getTitle();

                int imageResId = ((CheeseItem) item).getImageResId();
                cardHolder.textView.setText(cheese);
                Glide.with(context).load(imageResId).into(cardHolder.imageView);

                boolean isFavorite = Cheeses.getFavoriteCheeses().contains(cheese);
                cardHolder.favButton.setImageResource(isFavorite ? R.drawable.ic_favorite_selected : R.drawable.ic_favorite_unselected);

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
        ImageView favButton;

        CardViewHolder(final View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
            favButton = itemView.findViewById(R.id.button);

            favButton.setOnClickListener(new FavClickListener(this));
        }
    }

    class FavClickListener implements View.OnClickListener {
        CardViewHolder holder;

        FavClickListener(CardViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            int position = holder.getLayoutPosition();
            if (position != RecyclerView.NO_POSITION) {
                String cheeseName = items.get(position).getTitle();

                if (Cheeses.getFavoriteCheeses().contains(cheeseName)) {
                    Cheeses.getFavoriteCheeses().remove(cheeseName);
                } else {
                    Cheeses.getFavoriteCheeses().add(cheeseName);
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
