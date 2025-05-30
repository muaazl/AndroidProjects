package com.example.appinteraqtion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appinteraqtion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ActivityResultLauncher launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButtonTapped(v);
            }
        });

        binding.btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextButtonTapped(v);
            }
        });

        binding.btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMapButtonTapped(v);
            }
        });

        binding.btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseButtonTapped(v);
            }
        });

        binding.btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageCapture.class);
                startActivity(intent);
            }
        });
    }

    private void callButtonTapped(View v) {
        int phoneNumber = 761164425;
        Uri data= Uri.parse(String.format("tel:%d", phoneNumber));
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(data);

        if (intent.resolveActivity(getPackageManager())!=null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No matching app available", Toast.LENGTH_LONG).show();
        }
    }

    private void sendTextButtonTapped(View v) {
        int phoneNumber = 761164425;
        Uri data= Uri.parse(String.format("smsto:%d", phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String message = "Hello this is a new message";
        intent.setData(data);
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }
    private void viewMapButtonTapped(View v) {
        double longitude = 79.8549;
        double latitude = 6.9010;
        Uri data = Uri.parse(String.format("geo:%f,%f", latitude, longitude));
        Intent intent = new Intent().setAction(Intent.ACTION_VIEW).setData(data);
        startActivity(intent);
    }

    private void browseButtonTapped(View v) {
        String url = "https://vle.sliitcityuni.lk/";
        Uri data = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(data);
        startActivity(intent);
    }


}