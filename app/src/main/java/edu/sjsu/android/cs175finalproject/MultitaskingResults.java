package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MultitaskingResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_results);
        String totalAverage = getIntent().getStringExtra("totalAverage");
        String repeating = getIntent().getStringExtra("repeatingAverage");
        String switching = getIntent().getStringExtra("switchingAverage");
        TextView totalAverageView = (TextView) findViewById(R.id.totalScore);
        TextView repeatingView = (TextView) findViewById(R.id.repeatingScore);
        TextView switchingView = (TextView) findViewById(R.id.switchingScore);
        totalAverageView.setText(totalAverage);
        repeatingView.setText(repeating);
        switchingView.setText(switching);

    }
    public void onClickHome(){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }
}
