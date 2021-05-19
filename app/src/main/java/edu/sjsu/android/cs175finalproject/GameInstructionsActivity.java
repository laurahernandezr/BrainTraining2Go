package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameInstructionsActivity extends AppCompatActivity {
    int gameType;
    TextView gameTitle;
    TextView gameInstructions;
    ImageView gameImage;
    Class gameClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructions_view);
        gameTitle = (TextView)findViewById(R.id.game_title);
        gameInstructions = (TextView)findViewById(R.id.game_instructions);
        gameImage = (ImageView) findViewById(R.id.instructionsImageView);
        gameType = getIntent().getIntExtra("GAME",-1);

        if (gameType == 0) {
            gameTitle.setText(R.string.multitasking_title);
            gameInstructions.setText(R.string.multitasking_instructions);
            gameImage.setImageResource(R.drawable.multitasking_instruction);
            gameClass = MultitaskingActivity.class;

        }else if(gameType == 1){
            gameTitle.setText(R.string.search_title);
            gameInstructions.setText(R.string.search_instr_desc);
            gameImage.setImageResource(R.drawable.search_instruction);
            gameClass = SearchActivity.class;

        }else {
            gameTitle.setText(R.string.memory_title);
            gameInstructions.setText(R.string.memory_instructions);
            gameClass = CardMatchActivity.class;

        }
    }
    public void onClickStart(View view){
        Intent intent = new Intent(this, gameClass);
        startActivity(intent);

    }
}
