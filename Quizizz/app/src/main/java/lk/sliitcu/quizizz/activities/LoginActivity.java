package lk.sliitcu.quizizz.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import lk.sliitcu.quizizz.R;

/**
 * Handles user login by saving their name.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    public String defaultPassword = "Password";

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonPlay = findViewById(R.id.buttonPlay);

        buttonPlay.setOnClickListener(v -> handleLogin());
    }

    /**
     * Validates the name, saves it, and launches CategoryActivity.
     */
    private void handleLogin() {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your name and password both", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!defaultPassword.equals(password)) {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences prefs = getSharedPreferences("TriviaQuizApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("current_user", name);
        editor.apply();

        Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
        startActivity(intent);
        finish();
    }
}