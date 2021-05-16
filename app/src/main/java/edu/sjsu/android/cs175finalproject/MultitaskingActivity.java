package edu.sjsu.android.cs175finalproject;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MultitaskingActivity extends AppCompatActivity {
    private int[] imageArray = {R.drawable.shape1fill1, R.drawable.shape1fill2, R.drawable.shape2fill1, R.drawable.shape2fill2};
    private ImageView[] viewsArray = new ImageView[2];
    private ImageView fullView;
    private int imageNumber;
    private int pastImage = 4;
    private int viewNumber;
    private int pastView = 0;
    private int score = 0;
    private int rounds = 0;
    private int maxRounds = 0;
    private int taskNumber = 1;
    private int singleTaskScore = 0;
    private int switchingTaskScore = 0;
    private int totalScore = 0;
    final private int MAX_ROUNDS_PRACTICE = 5;
    final private int MAX_ROUNDS_DATACOLLECTION = 10;
    private FirebaseAuth mAuth;
    private int r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multitasking_view);
        taskNumber = 1;
        viewsArray[0] = (ImageView)findViewById(R.id.topView);
        viewsArray[1] = (ImageView)findViewById(R.id.bottomView);
        fullView =  (ImageView)findViewById(R.id.FullImageView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Check which round that was and update the round count in firebase
        db.collection("MultitaskingScores").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Object data = document.getData().get("rounds");
                        r = Integer.parseInt(String.valueOf(data));
                        db.collection("MultitaskingScores").document(currentUser.getUid()).update("rounds",++r);

                    } else {
                        Map<String, Object> firstRounds = new HashMap<>();
                        firstRounds.put("rounds",0);
                        db.collection("MultitaskingScores").document(currentUser.getUid()).set(firstRounds);
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());

                }
            }
        });

        showInstruction();

    }
    public void onRightClick(View view){
        if(imageNumber == 3 || imageNumber == 1 && viewNumber == 1 ||imageNumber == 2 && viewNumber == 0 ) {
            if (taskNumber > 3) {
                score++;
            }

        }else {
            if(taskNumber > 3) {
                score--;
           }
            wrongKey(viewsArray[viewNumber]);
        }
        System.out.println(score);
        showInstruction();
    }
    public void onLeftClick(View view){
        if (imageNumber == 0 || imageNumber == 1 && viewNumber == 0 || imageNumber == 2 && viewNumber == 1) {
            if (taskNumber > 3) {
                score++;
            }
        } else {
            if(taskNumber > 3) {
                score--;
            }
            wrongKey(viewsArray[viewNumber]);
        }
        System.out.println(score);
        showInstruction();
    }

    public void reloadImages(){
        if(taskNumber == 1 || taskNumber == 2 || taskNumber == 3)
        {
            maxRounds = MAX_ROUNDS_PRACTICE;
            if(rounds <= maxRounds)
            {
                if (taskNumber == 1 ){
                    viewNumber = 0;
                }else if(taskNumber == 2 ){
                    viewNumber = 1;
                }else{
                    viewNumber = new Random().nextInt(2);
                }
                imageNumber = new Random().nextInt(4);

                while (imageNumber == pastImage && taskNumber < 3){
                    imageNumber = new Random().nextInt(4);
                }
                setImage();
            }

        }else {
            if (taskNumber == 4 || taskNumber == 5){
                singleTaskScore += score;
                totalScore += score;
                score = 0;
            }else{
                switchingTaskScore += score;
                totalScore += score;
            }
            maxRounds = MAX_ROUNDS_DATACOLLECTION;
            if(rounds <= maxRounds)
            {
                if (taskNumber == 4){
                    viewNumber = 0;
                }else if(taskNumber == 5){
                    viewNumber = 1;
                }else{
                    viewNumber = new Random().nextInt(2);
                }
                imageNumber = new Random().nextInt(4);

                while (imageNumber == pastImage && viewNumber == pastView && taskNumber == 6){
                    imageNumber = new Random().nextInt(4);
                    viewNumber = new Random().nextInt(2);
                }
                setImage();
            }
        }

        if(rounds >= maxRounds && taskNumber < 6 ){
            rounds = 0;
            taskNumber++;
        }

    }
    public void setImage() {

        viewsArray[pastView].setImageResource(0);
        viewsArray[viewNumber].setImageResource(imageArray[imageNumber]);
        pastImage = imageNumber;
        pastView = viewNumber;
        rounds++;


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
    }

    public void wrongKey(ImageView imageView){
        setWrong(R.drawable.wrongkey);
        if(imageView == (ImageView)findViewById(R.id.topView)){
            setWrong(R.drawable.shapeinstruction);
        }else if(imageView == (ImageView)findViewById(R.id.bottomView)){
            setWrong(R.drawable.fillinginstruction);

        }

    }

    public void showInstruction(){
        System.out.println("Instruction task:" + taskNumber);
        System.out.println("Instruction round: " + rounds);
        Toast toast;
        if (taskNumber == 1 && rounds == 0 || taskNumber == 4  && rounds == 0){
            if(taskNumber > 3){
                toast = Toast.makeText(getApplicationContext()," \n  \n  \nDATA\nCOLLECTION\nPORTION",Toast.LENGTH_SHORT);
                setToast(toast);
                toast.show();

            }else {
                toast = Toast.makeText(getApplicationContext()," \n  \n  \nPRACTICE\nPORTION",Toast.LENGTH_SHORT);
                setToast(toast);
                toast.show();

            }

        }
        if (taskNumber == 1 && rounds == 0 || taskNumber == 4  && rounds == 0) {
            toast = Toast.makeText(this, " \n  \n  \nBlock of just the\nSHAPE TASK", Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        }
        if (taskNumber == 2 && rounds == 0 || taskNumber == 5  && rounds == 0){
            toast = Toast.makeText(getApplicationContext()," \n  \n  \nBlock of just the\nFILLING TASK",Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        }else if (taskNumber == 3 && rounds == 0 || taskNumber == 6  && rounds == 0){
            toast = Toast.makeText(getApplicationContext()," \n  \n  \nMix of\nSHAPE\nand\nFILLING TASK",Toast.LENGTH_SHORT);
            setToast(toast);
            toast.show();
        }
        if (taskNumber == 1 && rounds == 0 || taskNumber == 4  && rounds == 0) {
            showConcentrationToast();
        }
        reloadImages();
        if( rounds > maxRounds && taskNumber >= 6){
            Intent intent = new Intent(getBaseContext(), MultitaskingResults.class);
            if(totalScore>0){
                totalScore = totalScore/maxRounds;
            }
            if (singleTaskScore>0){
                singleTaskScore = singleTaskScore/maxRounds;
            }
            if(switchingTaskScore>0){
                switchingTaskScore = switchingTaskScore/maxRounds;
            }
            intent.putExtra("totalAverage", totalScore);
            intent.putExtra("repeatingAverage", singleTaskScore);
            intent.putExtra("switchingAverage", switchingTaskScore);
            intent.putExtra("round", r);
            startActivity(intent);
        }
    }
    private void setToast(Toast toast){
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.black_background);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.parseColor("#FFFFFFFF"));
        toast.setGravity(Gravity.FILL, 0, 0);
        text.setTextSize(40);
    }
    private void setWrong(int resource){
        int toastLength;
        if(resource == R.drawable.wrongkey){
            toastLength = Toast.LENGTH_SHORT;
        }else {
            toastLength = Toast.LENGTH_LONG;
        }
        Toast toast = Toast.makeText(getApplicationContext(), "",toastLength);
        View view2 = toast.getView();
        view2.setBackgroundResource(resource);
        toast.setGravity(Gravity.FILL, 0, 0);
        toast.show();

    }




    }
