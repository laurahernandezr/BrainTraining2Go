package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Random;

public class SearchActivity extends AppCompatActivity {
    private int score = 0;
    private int cur_round = 0;
    private final int MAX_ROUNDS = 1;

    private final int MAX_T = 25;
    private final int MAX_ORANGE_T = 1;
    private final int MAX_REV_ORANGE_T = (int)((MAX_T - MAX_ORANGE_T) * 0.75);
    private final int MAX_BLUE_T = MAX_T - MAX_REV_ORANGE_T;
    private final int SPAWN_CHANCE = 50; //percent that a T will spawn
    private final int SPAWN_CHANGE_DISTRACTION = 80;

    private Random rand = new Random();

    private ImageView[] views_arr = new ImageView[MAX_T];
    private HashSet<Integer> available_views = new HashSet<>();

    private int distractions = 0;
    private boolean orange_t = false;
    private long response_time;
    private long average_res_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view);
        /* get all image views */
        for(int i = 0; i < MAX_T; i++) {
            String image_view_id = "img" + i;
            int r_id = getResources().getIdentifier(image_view_id, "id", getPackageName());
            views_arr[i] = (ImageView)findViewById(r_id);
            available_views.add(i);
        }
        /* seed random */
        rand.setSeed(System.currentTimeMillis());
        /* set images on board and start clock*/
        nextRound();
    }

    public void onFoundClick(View view) {
        if(hasOrangeT()) {
            response_time = System.currentTimeMillis() - response_time;
            average_res_time += response_time;
            score += distractions * (response_time/1000);
            Toast toast = Toast.makeText(getApplicationContext(), "\n\n\nScore: " + score, Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "\n\n\nWrong!\nThere was no\norange T", Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        }
        cur_round++;
        nextRound();
    }

    public void onNotFoundClick(View view) {
        if(hasOrangeT()) {
            Toast toast = Toast.makeText(getApplicationContext(), "\n\n\nWrong!\nThere was an\norange T", Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        } else {
            response_time = System.currentTimeMillis() - response_time;
            average_res_time += response_time;
            score += distractions * (response_time/1000);
            Toast toast = Toast.makeText(getApplicationContext(), "\n\n\nScore: " + score, Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        }
        cur_round++;
        nextRound();
    }

    private boolean hasOrangeT() {
        return orange_t;
    }

    private boolean willSpawn(boolean distraction) {
        if(distraction)
            return rand.nextInt(100) < SPAWN_CHANGE_DISTRACTION;
        return rand.nextInt(100) < SPAWN_CHANCE;
    }

    private void nextRound() {
        if(cur_round < MAX_ROUNDS) {
            /* fill in blue Ts */
            for(int i = 0; i < MAX_BLUE_T; i++) {
                int view_id = rand.nextInt(available_views.size());
                if(willSpawn(true)) {
                    System.out.println("Blue at img" + view_id);
                    views_arr[view_id].setImageResource(R.drawable.blue_t);
                    distractions++;
                    available_views.remove(view_id);
                }
            }

            /* fill in rev orange Ts */
            for(int i = 0; i < MAX_REV_ORANGE_T; i++) {
                int view_id = rand.nextInt(available_views.size());
                if(willSpawn(true)) {
                    System.out.println("Rev Orange at img" + view_id);
                    views_arr[view_id].setImageResource(R.drawable.rev_orange_t);
                    distractions++;
                    available_views.remove(view_id);
                }
            }

            /* fill in orange Ts */
            for(int i = 0; i < MAX_ORANGE_T; i++) {
                int view_id = rand.nextInt(available_views.size());
                if(willSpawn(false)) {
                    System.out.println("Orange at img" + view_id);
                    views_arr[view_id].setImageResource(R.drawable.orange_t);
                    orange_t = true;
                    available_views.remove(view_id);
                }
            }
            showConcentrationToast();
        } else {
            Intent intent = new Intent(this, SearchResults.class);
            intent.putExtra("score", score);
            System.out.println(average_res_time);
            intent.putExtra("average", average_res_time/MAX_ROUNDS);
            startActivity(intent);
        }
    }

    public void showConcentrationToast(){
        Toast toast = Toast.makeText(getApplicationContext(), " \n  \n  \nCONCENTRATE", Toast.LENGTH_SHORT);
        setToast(toast);
        toast.show();

        toast = Toast.makeText(getApplicationContext(), " \n  \n  \n3", Toast.LENGTH_SHORT);
        setToast(toast);
        toast.show();

        toast = Toast.makeText(getApplicationContext(), " \n  \n  \n2", Toast.LENGTH_SHORT);
        setToast(toast);
        toast.show();

        toast = Toast.makeText(getApplicationContext(), " \n  \n  \n1", Toast.LENGTH_SHORT);
        setToast(toast);
        toast.show();

        /* start clock */
        response_time = System.currentTimeMillis();
    }

    private void setToast(Toast toast){
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.screen_shot_2021_05_10_at_1_08_32_pm);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FFFFFFFF"));
        toast.setGravity(Gravity.FILL, 0, 0);
        text.setTextSize(40);
    }
}
