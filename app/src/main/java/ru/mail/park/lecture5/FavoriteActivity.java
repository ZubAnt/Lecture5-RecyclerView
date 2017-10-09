package ru.mail.park.lecture5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        setTitle(R.string.favorite_title);
    }
}
