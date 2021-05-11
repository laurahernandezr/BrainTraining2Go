package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SearchInstructionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_instruction_view);

    }

    public void onPlay(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
