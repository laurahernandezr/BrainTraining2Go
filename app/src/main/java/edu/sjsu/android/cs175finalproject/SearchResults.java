package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_results);
        String score = getIntent().getStringExtra("score");
        String avg = getIntent().getStringExtra("average");
        TextView scoreView = (TextView) findViewById(R.id.results_score);
        TextView avgView = (TextView) findViewById(R.id.results_avg);
        scoreView.setText(score);
        avgView.setText(avg);

    }
    public void onClickHome(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
