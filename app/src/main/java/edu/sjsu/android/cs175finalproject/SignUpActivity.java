package edu.sjsu.android.cs175finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    private String email ,password = "";
    private EditText emailView, passwordView, passwordView2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        mAuth = FirebaseAuth.getInstance();
        emailView  = (EditText) findViewById(R.id.editTextTextEmailAddress);
        passwordView = (EditText) findViewById(R.id.editTextTextPassword);
        passwordView2 = (EditText) findViewById(R.id.editTextTextPassword2);
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

    private void reload() {
        Intent intent = new Intent(this, ChooseGameActivity.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        String email1 = emailView.getText().toString().trim();
        String password1 = passwordView.getText().toString();
        String password2 =  passwordView2.getText().toString();
        if (isValidEmail(email1)) {
            email = email1;
        }else {
            Toast.makeText(getApplicationContext(), "This is not a valid email, please try again", Toast.LENGTH_SHORT).show();
        }
        if (!password1.equals("") && password1.equals(password2)){
            password = password1;
        }else {
            Toast.makeText(getApplicationContext(), "Passwords don't match, please check them and try again", Toast.LENGTH_SHORT).show();
        }
        // [START create_user_with_email]
        if(email!= null && password!= null){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Please fill out all of the fields", Toast.LENGTH_SHORT);
        }

    }

    private void updateUI(Object o) {
        if (o != null){
            Intent intent = new Intent(this, ChooseGameActivity.class);
            startActivity(intent);
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

}
