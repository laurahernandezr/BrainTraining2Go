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

public class SearchResults extends AppCompatActivity {
    private FirebaseAuth mAuth;

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
        int r = getIntent().getIntExtra("round",0);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Add round data
        Map<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("score",score);
        scoreMap.put("round", r);
        scoreMap.put("game", "SearchScores");
        db.collection("SearchScores").document(currentUser.getUid()).collection("Rounds").add(scoreMap);

    }
    public void onClickHome(View view){
        Intent intent = new Intent(getBaseContext(), ChooseGameActivity.class);
        startActivity(intent);
    }
}
