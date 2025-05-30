package com.example.appinteraqtion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appinteraqtion.databinding.ActivityImageCaptureBinding;

public class ImageCapture extends AppCompatActivity {

    Button capture;
    ActivityImageCaptureBinding binding;
//    static final int REQUEST_THUMBNAIL = 1;

    private ActivityResultLauncher launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityImageCaptureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if (o.getResultCode() == RESULT_OK) {
                            Intent intent = o.getData();
                            Bitmap img = (Bitmap) intent.getExtras().get("data");
                            binding.imgPic.setImageBitmap(img);
                        }
                    }
                });

        capture = binding.btnCaptureImage;
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageButtonTapped(v);
            }
        });
    }

    private void captureImageButtonTapped(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivity(intent, REQUEST_THUMBNAIL);
        launcher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Bitmap resultImage = (Bitmap) data.getExtras().get("data");
            binding.imgPic.setImageBitmap(resultImage);
        }
    }
}