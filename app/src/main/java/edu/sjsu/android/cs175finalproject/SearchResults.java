package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        int score = getIntent().getIntExtra("score", 0);
        long avg = getIntent().getLongExtra("average", 0);
        TextView scoreView = (TextView) findViewById(R.id.results_score);
        TextView avgView = (TextView) findViewById(R.id.results_avg);
        scoreView.setText(Integer.toString(score));
        avgView.setText(Double.toString(avg) + "ms");

    }
    public void onClickHome(View view){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
