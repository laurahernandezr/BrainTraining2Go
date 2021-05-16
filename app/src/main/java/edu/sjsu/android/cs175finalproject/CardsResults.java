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

public class CardsResults extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards_results);
        int score = getIntent().getIntExtra("score", 0);
        double avg = getIntent().getDoubleExtra("time", 50);
        int r = getIntent().getIntExtra("round",0);

        TextView scoreView = (TextView) findViewById(R.id.results_score);
        TextView avgView = (TextView) findViewById(R.id.results_avg);
        scoreView.setText(Integer.toString(score));
        avgView.setText(Double.toString(avg) + "s");



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Add round data
        Map<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("score", score);
        scoreMap.put("average", avg);
        scoreMap.put("round", r);
        db.collection("MemoryScores").document(currentUser.getUid()).collection("Rounds").add(scoreMap);

    }
    public void onClickHome(View view){
        Intent intent = new Intent(getBaseContext(), ChooseGameActivity.class);
        startActivity(intent);
    }
}
