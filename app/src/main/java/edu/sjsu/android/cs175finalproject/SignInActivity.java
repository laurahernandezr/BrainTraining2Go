package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private String email ,password;
    private EditText emailView, passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        emailView  = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordView = (EditText) findViewById(R.id.editTextTextPassword);
        email = emailView.getText().toString().trim();

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    public void onClickSignIn(View view) {
        String email1 = emailView.getText().toString().trim();
        password = passwordView.getText().toString().trim();
        if (isValidEmail(email1) && !email1.equals("")) {
            email = email1;
        }else {
            Toast.makeText(getApplicationContext(), "This is not a valid email, please try again", Toast.LENGTH_SHORT).show();
        }
        if(email!= null && password!= null){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }else {
            Toast.makeText(getApplicationContext(), "Please input an email and a password", Toast.LENGTH_SHORT).show();

        }

    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(this, ChooseGameActivity.class);
            startActivity(intent);
        }
    }


    public void onCreateAccount(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void reload() {
        Intent intent = new Intent(this, ChooseGameActivity.class);
        startActivity(intent);
    }


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

