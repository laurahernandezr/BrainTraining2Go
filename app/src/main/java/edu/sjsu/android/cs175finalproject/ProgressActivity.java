package edu.sjsu.android.cs175finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ProgressActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    int game;
    private FirestoreRecyclerAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = getIntent().getIntExtra("GAME",0);
        setContentView(R.layout.progress_view);
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycler_view);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String database;
        //Query
        if (game == 0){
            database = "MultitaskingScores";
        }else if(game == 1){
            database = "SearchScores";
        }else {
            database = "MemoryScores";
        }
        Query query = firebaseFirestore.collection(database).document(currentUser.getUid()).collection("Rounds").whereEqualTo("game",database).orderBy("round");

        //RecyclerOptions
        FirestoreRecyclerOptions<ProgressModel> options = new FirestoreRecyclerOptions.Builder<ProgressModel>()
                .setQuery(query, ProgressModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProgressModel, ProgressViewHolder>(options) {
            @NonNull
            @Override
            public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
                return new ProgressViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProgressViewHolder holder, int position, @NonNull ProgressModel model) {
                holder.round_tv.setText(model.getRound() + "");
                holder.score_tv.setText(model.getScore() + "");
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private class ProgressViewHolder extends RecyclerView.ViewHolder{
        TextView round_tv, score_tv;
        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            round_tv = itemView.findViewById(R.id.round_tv);
            score_tv = itemView.findViewById(R.id.score_tv);
        }
    }
}
