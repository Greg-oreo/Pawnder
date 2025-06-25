package edu.utsa.cs3443.pawnder;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        RecyclerView recyclerView = findViewById(R.id.matchesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Match> matches = MatchManager.getInstance().getMatchList();
        recyclerView.setAdapter(new MatchAdapter(matches, this));
    }
}
