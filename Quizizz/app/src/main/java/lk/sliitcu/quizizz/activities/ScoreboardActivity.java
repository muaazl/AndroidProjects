package lk.sliitcu.quizizz.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lk.sliitcu.quizizz.R;
import lk.sliitcu.quizizz.adapters.ScoreAdapter;
import lk.sliitcu.quizizz.models.ScoreEntry;

public class ScoreboardActivity extends AppCompatActivity {

    private RecyclerView recyclerViewScores;
    private TextView textViewNoScores, textViewUsername;
    private final List<ScoreEntry> scoreList = new ArrayList<>();
    private ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        initializeViews();
        loadScores();
        setupRecyclerView();
    }

    private void initializeViews() {
        recyclerViewScores = findViewById(R.id.recyclerViewScores);
        textViewNoScores = findViewById(R.id.textViewNoScores);
        textViewUsername = findViewById(R.id.textViewUsername);
    }

    // method taken from chatgpt
    private void loadScores() {
        SharedPreferences prefs = getSharedPreferences("TriviaQuizApp", MODE_PRIVATE);
        String username = prefs.getString("current_user", "Player");
        textViewUsername.setText("Player: " + username);

        String scoresKey = "scores_" + username;
        String jsonString = prefs.getString(scoresKey, "[]");

        scoreList.clear();
        try {
            JSONArray scoresArray = new JSONArray(jsonString);
            for (int i = 0; i < scoresArray.length(); i++) {
                JSONObject scoreObject = scoresArray.getJSONObject(i);
                int attempt = scoreObject.getInt("attempt");
                int score = scoreObject.getInt("score");
                long timestamp = scoreObject.getLong("timestamp");
                scoreList.add(new ScoreEntry(attempt, score, timestamp));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (scoreList.isEmpty()) {
            textViewNoScores.setVisibility(View.VISIBLE);
            recyclerViewScores.setVisibility(View.GONE);
        } else {
            scoreList.sort((o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));
            textViewNoScores.setVisibility(View.GONE);
            recyclerViewScores.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView() {
        scoreAdapter = new ScoreAdapter(scoreList);
        recyclerViewScores.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewScores.setAdapter(scoreAdapter);
    }
}