package ru.mail.park.lecture5;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CheeseGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TITLE = R.layout.item_title;
    public static final int ITEM_CHEESE_CARD = R.layout.item_cheese_card;
    private final int columnCount;

    public CheeseGridAdapter(int columns) {
        columnCount = columns;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        switch (viewType) {
            case ITEM_TITLE:
                View titleView = LayoutInflater.from(context).inflate(ITEM_TITLE, parent, false);
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
        switch (getItemViewType(position)) {
            case ITEM_TITLE:
                ((TitleViewHolder) holder).headingView.setText(Cheeses.getRandomTitle());
                break;

            case ITEM_CHEESE_CARD:
                CardViewHolder cardHolder = ((CardViewHolder) holder);
                String cheese = Cheeses.cheeseNames[getCheeseArrayIndex(position)];
                cardHolder.imageView.setImageResource(Cheeses.getCheeseDrawable(cheese));
                cardHolder.textView.setText(cheese);
                boolean isFavorite = Cheeses.favoriteCheeses.contains(cheese);
                cardHolder.favButton.setImageResource(isFavorite ? R.drawable.ic_favorite_selected : R.drawable.ic_favorite_unselected);
                break;

            default:
                throw new IllegalArgumentException("invalid view type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position % (columnCount + 1) == 0)
            return R.layout.item_title;
        else
            return R.layout.item_cheese_card;
    }

    @Override
    public int getItemCount() {
        return Cheeses.cheeseNames.length + (int) Math.ceil(Cheeses.cheeseNames.length / (double)(columnCount));
    }

    // чтобы получить реальную позицию карточки в списке сыров,
    // вычитаем количество заголовков, которое было до данного элемента RecyclerView
    private int getCheeseArrayIndex(int position) {
        return (int) (position - Math.ceil(position / (double)(columnCount + 1)));
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
                String cheeseName = holder.textView.getText().toString();

                if (Cheeses.favoriteCheeses.contains(cheeseName)) {
                    Cheeses.favoriteCheeses.remove(cheeseName);
                } else {
                    Cheeses.favoriteCheeses.add(cheeseName);
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
