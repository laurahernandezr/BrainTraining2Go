package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameDescriptionActivity extends AppCompatActivity {
    int gameType;
    TextView gameTitle;
    TextView gameDescription;
    TextView gameUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_description);
        gameTitle = (TextView)findViewById(R.id.game_title);
        gameDescription = (TextView)findViewById(R.id.game_description);
        gameUse = (TextView)findViewById(R.id.game_use);
        //Hiding Button until implemented
        Button b = (Button)findViewById(R.id.button2);
        b.setEnabled(false);

        gameType = getIntent().getIntExtra("GAME",-1);
        if (gameType == 0) {
            gameTitle.setText(R.string.multitasking_title);
            gameDescription.setText(R.string.multitasking_description);
            gameUse.setText(R.string.multitasking_use);

        }else if(gameType == 1){
            gameTitle.setText(R.string.search_title);
            gameDescription.setText(R.string.search_description);
            gameUse.setText(R.string.search_use);

        }else {
            gameTitle.setText(R.string.memory_title);
            gameDescription.setText(R.string.memory_description);
            gameUse.setText(R.string.memory_use);

        }
    }
    public void onClickInstructions(View view){
        Intent intent = new Intent(this, GameInstructionsActivity.class);
        intent.putExtra("GAME", gameType);
        startActivity(intent);

    }
}
