package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MultitaskingInstructionsActivity  extends AppCompatActivity {
    private int[] imageArray = {R.drawable.info1,R.drawable.info2,R.drawable.info3, R.drawable.info4, R.drawable.info5, R.drawable.info6, R.drawable.info8};
    private int instructionNumber = 0;
    Button train;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_instruction_view);
        train = (Button)findViewById(R.id.trainButton);
        train.setEnabled(false);
        ImageView instructionView = (ImageView)findViewById(R.id.instructionsView);
        instructionView.setImageResource(imageArray[0]);
    }
    public void onNextInstruction(View view){
        if(instructionNumber< imageArray.length){
            instructionNumber++;
            ImageView instructionView = (ImageView)findViewById(R.id.instructionsView);
            instructionView.setImageResource(imageArray[instructionNumber]);
        }
        if (instructionNumber == imageArray.length - 1){
            train.setEnabled(true);
        }


    }
    public void onLastInstruction(View view){
        if(instructionNumber > 0){
            instructionNumber--;
            ImageView instructionView = (ImageView)findViewById(R.id.instructionsView);
            instructionView.setImageResource(imageArray[instructionNumber]);
        }

    }

    public void onTrain(View view){
        Intent intent = new Intent(this, MultitaskingActivity.class);
        startActivity(intent);
    }
}
