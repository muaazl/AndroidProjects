package com.example.colorpicqer;

import static com.example.colorpicqer.R.id.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameGUI extends AppCompatActivity {

    Button firstButton;
    Button secondButton;
    TextView nameTextView;
    List<ColorDetail> colorDetails = new ArrayList<>();
    int index=0, points=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_gui);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firstButton = findViewById(R.id.btn_first);
        secondButton = findViewById(R.id.btn_second);
        nameTextView = findViewById(R.id.tv_welcome);

        Intent intent = getIntent();
        String player_Name = intent.getStringExtra("playerName");
        nameTextView.setText("Welcome "+player_Name);

        populateColors();
        setColors();
    }

    private void populateColors() {
        colorDetails.clear();
        colorDetails.add(new ColorDetail("Blue", "#ff33b5e5", "#ff669900"));
        colorDetails.add(new ColorDetail("Green", "#ff669900", "#FFA500"));
        colorDetails.add(new ColorDetail("Orange", "#FFA500", "#ffaa66cc"));
        colorDetails.add(new ColorDetail("Purple", "#ffaa66cc", "#ffcc0000"));
        colorDetails.add(new ColorDetail("Red", "#ffcc0000", "#A9A9A9"));
        colorDetails.add(new ColorDetail("Gray", "#A9A9A9", "#fff757f7"));
        colorDetails.add(new ColorDetail("Pink", "#fff757f7", "#ff33b5e5"));
    }

    private void setColors() {
        if (index < colorDetails.size()) {
            Random random = new Random();
            int number = random.nextInt(10);

            ColorDetail colorDetail = colorDetails.get(index);

            if (number % 2 == 0) {
                configureButtons(colorDetail, secondButton, firstButton);
            } else {
                configureButtons(colorDetail, firstButton, secondButton);
            }
            index++;
        } else {
            int score = (int) ((double) points/ (double) colorDetails.size()*100);
            Toast.makeText(this, "Your score is "+ score, Toast.LENGTH_SHORT).show();
        }
    }

    private void configureButtons(ColorDetail colorDetail, Button correctButton, Button incorrectButton) {
        correctButton.setBackgroundColor(Color.parseColor(colorDetail.getHex()));
        correctButton.setTag(1);
        correctButton.setText(colorDetail.getName());
        incorrectButton.setBackgroundColor(Color.parseColor(colorDetail.getWrongHex()));
        incorrectButton.setTag(0);
        incorrectButton.setText(colorDetail.getName());
    }

    public void onButtonTapped(View view) {
        Button button = (Button) view;
        int tag = (int)button.getTag();
        if (tag == 1) {
            points++;
        } else {
            points--;
        }
        setColors();
    }
}