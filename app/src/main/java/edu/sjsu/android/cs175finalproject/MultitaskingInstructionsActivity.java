package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MultitaskingInstructionsActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_instruction_view);

    }
    public void onInstructions(View view){

    }

    public void onTrain(View view){
        Intent intent = new Intent(this, MultitaskingActivity.class);
        startActivity(intent);
    }
}
