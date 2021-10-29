package com.example.postassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.postassignment.databinding.ActivityMainBinding;
import com.example.postassignment.databinding.ActivityPostResultBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PostResult extends AppCompatActivity {

    private ActivityPostResultBinding binding;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String todayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntent();


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        todayDate = dateFormat.format(calendar.getTime());
        binding.date.setText(todayDate);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME_INPUT");
        String post = intent.getStringExtra("POST_INPUT");

        binding.name.setText(name);
        binding.userText.setText(post);
//        binding.image.setImageURI(uriImage);
        binding.closeButton.setOnClickListener(v -> finish());

        Bundle ex = getIntent().getExtras();
        Uri uri;

        if(ex != null && ex.containsKey("IMAGE")) {
            uri = Uri.parse(ex.getString("KEY"));
            binding.image.setImageURI(uri);
        }

//        Bitmap bitmap = loadBitmap(uri);
//        binding.image.setImageBitmap(bitmap);

    }

//    private Bitmap loadBitmap(Uri uri) {
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//        Cursor cursor = getContentResolver().query(uri,
//                filePathColumn, null, null, null);
//        cursor.moveToFirst();
//
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        String picturePath = cursor.getString(columnIndex);
//        cursor.close();
//
//        return BitmapFactory.decodeFile(picturePath);
//    }

}