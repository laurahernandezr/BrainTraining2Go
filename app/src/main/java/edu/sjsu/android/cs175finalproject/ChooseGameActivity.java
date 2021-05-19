package edu.sjsu.android.cs175finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ChooseGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_game);
    }

    public void onClickMultitasking(View view){
        Intent intent = new Intent(this, GameDescriptionActivity.class);
        intent.putExtra("GAME", 0);
        startActivity(intent);
    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(this, GameDescriptionActivity.class);
        intent.putExtra("GAME", 1);
        startActivity(intent);
    }
    public void onClickMemory(View view) {
        Intent intent = new Intent(this, GameDescriptionActivity.class);
        intent.putExtra("GAME", 3);
        startActivity(intent);
    }
    public void onClickLogout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}