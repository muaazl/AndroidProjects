package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    EditText number1, number2;
    TextView display;
    int num1, num2;
    View main;


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
        display = findViewById(R.id.display);
        main = findViewById(R.id.main);
    }

    private void readValues() {
        num1 = Integer.parseInt(((EditText) findViewById(R.id.et_number1)).getText().toString());
        num2 = Integer.parseInt(((EditText)findViewById(R.id.et_number2)).getText().toString());
    }

    private int calculation(int num1, int num2, char op) {
        switch (op) {
            case '+':
                return num1+num2;
            case '-':
                return num1-num2;
            case '*':
                return num1*num2;
            case '/':
                return num1/num2;
            default:
                return 99;
        }
    }

    public void addButtonClicked(View view) {
        readValues();
        int answer = calculation(num1,num2,'+');
//        display.setText(answer+"");
//        Toast.makeText(this, answer+"", Toast.LENGTH_SHORT).show();
        Snackbar.make(main, answer+"", Snackbar.LENGTH_SHORT).show();
    }

    public void mulButtonClicked(View view) {
        readValues();
        int answer = calculation(num1,num2,'*');
//        display.setText(answer+"");
//        Toast.makeText(this, answer+"", Toast.LENGTH_SHORT).show();
        Snackbar.make(main, answer+"", Snackbar.LENGTH_SHORT).show();
    }

    public void divButtonClicked(View view) {
        readValues();
        int answer = calculation(num1,num2,'/');
//        display.setText(answer+"");
//        Toast.makeText(this, answer+"", Toast.LENGTH_SHORT).show();
        Snackbar.make(main, answer+"", Snackbar.LENGTH_SHORT).show();
    }

    public void subButtonClicked(View view) {
        readValues();
        int answer = calculation(num1,num2,'-');
//        display.setText(answer+"");
//        Toast.makeText(this, answer+"", Toast.LENGTH_SHORT).show();
        Snackbar.make(main, answer+"", Snackbar.LENGTH_SHORT).show();
    }
}