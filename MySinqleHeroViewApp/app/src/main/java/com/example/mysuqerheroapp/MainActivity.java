package com.example.mysuqerheroapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Hero> heroList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        populateData();

        RecyclerView rv = findViewById(R.id.myrecyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));

        HeroAdapter adapter = new HeroAdapter(this, heroList);
        rv.setAdapter(adapter);
    }

    private void populateData() {
        heroList.add(new Hero("Alan Scott", "DC Comics", "Male", R.drawable.alan_scott, "https://en.wikipedia.org/wiki/Alan_Scott"));
        heroList.add(new Hero("Ant Man", "Giant Man", "Male", R.drawable.ant_man, "https://en.wikipedia.org/wiki/Ant-Man"));
        heroList.add(new Hero("Batman", "DC Comics", "Male", R.drawable.batman, "https://en.wikipedia.org/wiki/Batman"));
        heroList.add(new Hero("Elle Bishop", "NBC - Heroes", "Female", R.drawable.elle_bishop, "https://en.wikipedia.org/wiki/Elle_Bishop"));
        heroList.add(new Hero("Flash", "DC Comics", "Male", R.drawable.flash, "https://en.wikipedia.org/wiki/Flash_(DC_Comics_character)"));
        heroList.add(new Hero("Hawkgirl", "DC Comics", "Female", R.drawable.hawkgirl, "https://en.wikipedia.org/wiki/Hawkgirl"));
        heroList.add(new Hero("Loki", "Marvel Comics", "Male", R.drawable.loki, "https://en.wikipedia.org/wiki/Loki_(Marvel_Comics)"));
        heroList.add(new Hero("Shang Chi", "Marvel Comics", "Male", R.drawable.shang_chi, "https://en.wikipedia.org/wiki/Shang-Chi"));
        heroList.add(new Hero("Spiderman", "Marvel Comics", "Male", R.drawable.spider_man, "https://en.wikipedia.org/wiki/Spider-Man"));
        heroList.add(new Hero("Supergirl", "DC Comics", "Female", R.drawable.supergirl, "https://en.wikipedia.org/wiki/Supergirl"));
    }
}