package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MultitaskingResults extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_results);
        int totalAverage = getIntent().getIntExtra("totalAverage",0);
        int repeating = getIntent().getIntExtra("repeatingAverage",0);
        int switching = getIntent().getIntExtra("switchingAverage",0);
        int r = getIntent().getIntExtra("round",0);
        TextView totalAverageView = (TextView) findViewById(R.id.totalScore);
        TextView repeatingView = (TextView) findViewById(R.id.repeatingScore);
        TextView switchingView = (TextView) findViewById(R.id.switchingScore);
        totalAverageView.setText(String.valueOf(totalAverage));
        repeatingView.setText(String.valueOf(repeating));
        switchingView.setText(String.valueOf(switching));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Add round data
        Map<String, Object> score = new HashMap<>();
        score.put("score", totalAverage);
        score.put("round", r);
        score.put("game", "MultitaskingScores");
        db.collection("MultitaskingScores").document(currentUser.getUid()).collection("Rounds").add(score);

    }
    public void onClickHome(View view){
        Intent intent = new Intent(getBaseContext(), ChooseGameActivity.class);
        startActivity(intent);
    }
}
