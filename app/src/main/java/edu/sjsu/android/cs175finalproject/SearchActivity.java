package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SearchActivity extends AppCompatActivity {
    private int score = 0;
    private int cur_round = 0;
    private final int MAX_ROUNDS = 5;
    private final int MAX_T = 25;
    private final int MAX_DISTRACTIONS = MAX_T - 1;

    private final int BLUE_IMG_ID = 0;
    private final int ORANGE_IMG_ID = 1;
    private final int REVERSE_ORANGE_IMG_ID = 2;

    private ImageView[] views_arr = new ImageView[MAX_T];

    private int distractions;
    private boolean orange_t = false;
    private long response_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        /* get all image views */
        for(int i = 0; i < MAX_T; i++) {
            String image_view_id = "img" + i;
            int r_id = getResources().getIdentifier(image_view_id, "id", getPackageName());
            System.out.println(r_id);
            views_arr[i] = (ImageView)findViewById(r_id);
        }
        /* set images on board and start clock*/
        nextRound();
    }

    public void onFoundClick(View view) {
        if(isUpsidedownOrangeT()) {
            response_time -= System.currentTimeMillis();
            score += distractions * response_time;
        } else {
            //no points added
        }

    }

    private boolean isUpsidedownOrangeT() {
        return orange_t;
    }

    private void nextRound() {
        if(cur_round < MAX_ROUNDS) {
            /* randomly generate which views should get filled */
            Random rand = new Random();
            rand.setSeed(response_time);
            for(int i = 0; i < MAX_T; i++) {
                switch (rand.nextInt(3)) {
                    case 0:
                        views_arr[i].setImageResource(R.drawable.blue_t);
                        distractions++;
                        break;
                    case 1:
                        views_arr[i].setImageResource(R.drawable.orange_t);
                        orange_t = true;
                        break;
                    case 2:
                        views_arr[i].setImageResource(R.drawable.rev_orange_t);
                        distractions++;
                        break;
                }
            }
        } else {
            // Goto results screen
        }
        System.out.println("Distractions: " + distractions);
        System.out.println("Orange T: " + orange_t);
        /* start clock */
        response_time = System.currentTimeMillis();
    }
}
