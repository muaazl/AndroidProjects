package lk.sliitcu.quizizz.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import lk.sliitcu.quizizz.R;
import lk.sliitcu.quizizz.models.Question;
import lk.sliitcu.quizizz.utils.VolleySingleton;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int TOTAL_QUESTIONS_PER_GAME = 12;
    private static final int QUESTIONS_PER_LEVEL = 4;
    private static final long[] LEVEL_TIMERS = {90000, 60000, 30000};

    private TextView textViewTimer, textViewScore, textViewQuestion, textViewLevel;
    private ProgressBar gameProgressBar;
    private final Button[] answerButtons = new Button[4];
    private final List<Question> questionList = new ArrayList<>();

    private int currentQuestionIndex = 0;
    private int score = 0;
    private int currentLevel = 1;
    private CountDownTimer countDownTimer;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeViews();
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", 0);
        categoryName = getIntent().getStringExtra("CATEGORY_NAME");
        setTitle("Category: " + categoryName);
        fetchQuestions(categoryId);
    }

    //fetch questions method taken from AI
    private void fetchQuestions(int categoryId) {
        gameProgressBar.setVisibility(View.VISIBLE);
        String url = String.format("https://opentdb.com/api.php?amount=%d&category=%d&encode=base64", TOTAL_QUESTIONS_PER_GAME, categoryId);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        questionList.clear();

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject questionObject = results.getJSONObject(i);
                            String questionText = decodeBase64(questionObject.getString("question"));
                            String correctAnswer = decodeBase64(questionObject.getString("correct_answer"));
                            JSONArray incorrectAnswersArray = questionObject.getJSONArray("incorrect_answers");
                            List<String> incorrectAnswers = new ArrayList<>();
                            for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                                incorrectAnswers.add(decodeBase64(incorrectAnswersArray.getString(j)));
                            }
                            questionList.add(new Question(questionText, correctAnswer, incorrectAnswers));
                        }

                        if (questionList.size() < TOTAL_QUESTIONS_PER_GAME) {
                            Toast.makeText(this, "Not enough questions, try another category.", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }

                        gameProgressBar.setVisibility(View.GONE);
                        startLevel(1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing questions.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Failed to fetch questions.", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(request);
    }

    // method taken from AI
    private String decodeBase64(String coded) {
        byte[] data = Base64.decode(coded, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questionList.size()) {
            endGame(false);
            return;
        }

        int questionInLevel = (currentQuestionIndex % QUESTIONS_PER_LEVEL) + 1;
        textViewLevel.setText(String.format(Locale.getDefault(), "Level %d: Question %d/%d", currentLevel, questionInLevel, QUESTIONS_PER_LEVEL));

        Question currentQuestion = questionList.get(currentQuestionIndex);

        textViewQuestion.setText(Html.fromHtml(currentQuestion.getQuestionText(), Html.FROM_HTML_MODE_LEGACY));

        List<String> answers = new ArrayList<>(currentQuestion.getIncorrectAnswers());
        answers.add(currentQuestion.getCorrectAnswer());
        Collections.shuffle(answers);

        for (int i = 0; i < 4; i++) {
            if (i < answers.size()) {
                answerButtons[i].setVisibility(View.VISIBLE);
                answerButtons[i].setText(Html.fromHtml(answers.get(i), Html.FROM_HTML_MODE_LEGACY));
                answerButtons[i].setBackgroundColor(getColor(com.google.android.material.R.color.design_default_color_primary));
                answerButtons[i].setEnabled(true);
            } else {
                answerButtons[i].setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        Question currentQuestion = questionList.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        for(Button btn : answerButtons) {
            btn.setEnabled(false);
            if(btn.getText().toString().equalsIgnoreCase(correctAnswer)){
                btn.setBackgroundColor(Color.GREEN);
            }
        }

        if (clickedButton.getText().toString().equalsIgnoreCase(correctAnswer)) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            score--;
            clickedButton.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }

        updateScoreDisplay();
        currentQuestionIndex++;
        v.postDelayed(this::goToNextQuestionOrLevel, 1200);
    }

    //muaazi
    private void saveScore() {
        SharedPreferences prefs = getSharedPreferences("TriviaQuizApp", MODE_PRIVATE);
        String username = prefs.getString("current_user", "guest");
        String scoresKey = "scores_" + username;

        String jsonString = prefs.getString(scoresKey, "[]");
        JSONArray scoresArray;
        try {
            scoresArray = new JSONArray(jsonString);

            JSONObject newScoreObject = new JSONObject();
            newScoreObject.put("attempt", scoresArray.length() + 1);
            newScoreObject.put("score", score);
            newScoreObject.put("timestamp", System.currentTimeMillis());

            scoresArray.put(newScoreObject);

        } catch (JSONException e) {
            e.printStackTrace();
            scoresArray = new JSONArray();
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(scoresKey, scoresArray.toString());
        editor.apply();
    }

    private void initializeViews(){
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewScore = findViewById(R.id.textViewScore);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewLevel = findViewById(R.id.textViewLevel);
        gameProgressBar = findViewById(R.id.gameProgressBar);
        answerButtons[0] = findViewById(R.id.buttonAnswer1);
        answerButtons[1] = findViewById(R.id.buttonAnswer2);
        answerButtons[2] = findViewById(R.id.buttonAnswer3);
        answerButtons[3] = findViewById(R.id.buttonAnswer4);
        for (Button btn : answerButtons) { btn.setOnClickListener(this); }
        updateScoreDisplay();
    }
    private void startLevel(int level){
        currentLevel = level;
        long timerDuration = LEVEL_TIMERS[level - 1];
        if (countDownTimer != null) { countDownTimer.cancel(); }
        countDownTimer = new CountDownTimer(timerDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                textViewTimer.setText(String.format(Locale.getDefault(), "Time: %02d:%02d", minutes, seconds));
            }
            @Override
            public void onFinish() { endGame(true); }
        }.start();
        displayQuestion();
    }
    private void updateScoreDisplay(){
        textViewScore.setText(String.format(Locale.getDefault(), "Score: %d", score));
    }
    private void goToNextQuestionOrLevel(){
        if (currentQuestionIndex % QUESTIONS_PER_LEVEL == 0 && currentQuestionIndex < TOTAL_QUESTIONS_PER_GAME) {
            startLevel(currentLevel + 1);
        } else if (currentQuestionIndex < TOTAL_QUESTIONS_PER_GAME) {
            displayQuestion();
        } else {
            endGame(false);
        }
    }
    private void endGame(boolean timeUp){
        if (countDownTimer != null) { countDownTimer.cancel(); }
        saveScore();
        if (score < 0) {
            showBadgeDialog();
        } else {
            showResultDialog(timeUp);
        }
    }
    private void showBadgeDialog(){
        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("You've earned the 'Quiz Dumbster' badge for scoring 10 or more!")
                .setIcon(R.drawable.badge_quiz_master)
                .setPositiveButton("Awesome!", (dialog, which) -> showResultDialog(false))
                .setCancelable(false).show();
    }
    private void showResultDialog(boolean timeUp){
        String message = timeUp ? "Time's up! Your final score is: " + score : "Quiz finished! Your final score is: " + score;
        new AlertDialog.Builder(this)
                .setTitle("Game Over").setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(GameActivity.this, ScoreboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNeutralButton("Play Again", (dialog, which) ->{
                    Intent intent = new Intent(GameActivity.this, CategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setCancelable(false).show();
    }

    private void showSurprise() {

    }
}