package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MultitaskingResults extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_results);
        String totalAverage = getIntent().getStringExtra("totalAverage");
        String repeating = getIntent().getStringExtra("repeatingAverage");
        String switching = getIntent().getStringExtra("switchingAverage");
        int r = getIntent().getIntExtra("round",0);
        TextView totalAverageView = (TextView) findViewById(R.id.totalScore);
        TextView repeatingView = (TextView) findViewById(R.id.repeatingScore);
        TextView switchingView = (TextView) findViewById(R.id.switchingScore);
        totalAverageView.setText(totalAverage);
        repeatingView.setText(repeating);
        switchingView.setText(switching);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Add round data
        Map<String, Object> score = new HashMap<>();
        score.put("totalAverage", totalAverage);
        score.put("repeatingAverage", repeating);
        score.put("switchingAverage", switching);
        score.put("round", r);
        db.collection("MultitaskingScores").document(currentUser.getUid()).collection("Rounds").add(score);

    }
    public void onClickHome(View view){
        Intent intent = new Intent(getBaseContext(), ChooseGameActivity.class);
        startActivity(intent);
    }
}
