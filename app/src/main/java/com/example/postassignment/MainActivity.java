package com.example.postassignment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.postassignment.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addImage.setOnClickListener(v -> getPhoto());
        binding.saveButton.setOnClickListener(v -> postResult());


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    private void postResult() {
        Intent intent = new Intent(this, PostResult.class);
        String name = binding.editName.getText().toString();
        String post = binding.editText.getText().toString();

        intent.putExtra("NAME_INPUT", name);
        intent.putExtra("POST_INPUT", post);
        startActivity(intent);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "????????? ???????????? ??????????????? ????????? ???????????? ?????? ???????????????", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLauncher.launch(intent);
        binding.addedImage.setVisibility(View.VISIBLE);
    }

    ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        Intent data = result.getData();
                        Log.i("TEST", "data: " + data);

                        if (result.getResultCode() == RESULT_OK && null != data) {
                            Uri selectedImage = data.getData();
                            Bitmap bitmap = loadBitmap(selectedImage);
                            binding.addedImage.setImageBitmap(bitmap);

                            Intent intent = new Intent(this, PostResult.class);
//                            String uri_Str = selectedImage.toString();
                            intent.putExtra("IMAGE", selectedImage);
                        }
                    });

    private Bitmap loadBitmap(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        return BitmapFactory.decodeFile(picturePath);
    }
}