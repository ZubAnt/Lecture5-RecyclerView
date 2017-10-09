package ru.mail.park.lecture5;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MIN_COLUMNS = 2;

    private CheeseGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.cheeses_title);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final int columns = getColumnCount();

        List<Item> items = buildItemList(columns);
        adapter = new CheeseGridAdapter(items);

        GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case CheeseGridAdapter.ITEM_HEADER:
                        return columns;
                    case CheeseGridAdapter.ITEM_CHEESE_CARD:
                        return 1;
                    default:
                        return -1;
                }
            }
        };

        GridLayoutManager layoutManager = new GridLayoutManager(this, columns);
        layoutManager.setSpanSizeLookup(spanSizeLookup);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    public List<Item> buildItemList(int columns) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < Cheeses.cheeseNames.length; i++) {
            if (i % (2*columns) == 0)
                items.add(new HeaderItem(Cheeses.getRandomTitle()));

            String cheeseName = Cheeses.cheeseNames[i];
            items.add(new CheeseItem(cheeseName, Cheeses.getCheeseDrawable(cheeseName)));
        }

        return items;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_fav) {
            startActivity(new Intent(this, FavoriteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public int getColumnCount() {
        Resources res = getResources();
        int screenWidth = res.getDisplayMetrics().widthPixels;
        int padding = res.getDimensionPixelSize(R.dimen.padding);
        int minColumnWidth = res.getDimensionPixelSize(R.dimen.min_image_width);

        return Math.max((screenWidth - 2*padding) / minColumnWidth, MIN_COLUMNS);
    }

}
