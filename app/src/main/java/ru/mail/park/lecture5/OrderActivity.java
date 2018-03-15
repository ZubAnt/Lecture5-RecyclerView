package ru.mail.park.lecture5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        setTitle(R.string.order_title);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinkedHashSet<Cheese> items = Cheeses.getShoppingCart();
        List<Item> itemList = new ArrayList<Item>(items);
        adapter = new OrderAdapter(itemList, this);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }
}
