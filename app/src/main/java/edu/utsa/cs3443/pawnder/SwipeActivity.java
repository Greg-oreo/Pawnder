package edu.utsa.cs3443.pawnder;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetCardAdapter adapter;
    private List<PetProfile> petList = new ArrayList<>();
    private TextView emptyMessage;
    private TextView subMessage;
    private ImageButton likeBtn, passBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        emptyMessage = findViewById(R.id.emptyMessage);
        subMessage = findViewById(R.id.subMessage);
        recyclerView = findViewById(R.id.cardRecyclerView);
        likeBtn = findViewById(R.id.button_like);
        passBtn = findViewById(R.id.button_pass);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample data
        petList.add(new PetProfile("Male","Buddy", 3, "Golden Retriever", "San Antonio", "Friendly",
                "Loves kids and playing fetch.",  Arrays.asList(R.drawable.golden_retriever)));

        petList.add(new PetProfile("Female","Luna", 2, "Siamese Cat", "Austin", "Shy",
                "Prefers quiet homes.",  Arrays.asList(R.drawable.golden_retriever)));

        adapter = new PetCardAdapter(petList, this);
        recyclerView.setAdapter(adapter);

        // Swipe logic
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView rv, @NonNull RecyclerView.ViewHolder vh, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override

            public void onSwiped(@NonNull RecyclerView.ViewHolder vh, int direction) {
                int pos = vh.getAdapterPosition();

                if (pos >= 0 && pos < petList.size()) {
                    PetProfile pet = petList.get(pos);
                    String name = pet.name;

                    if (direction == ItemTouchHelper.LEFT) {
                        Toast.makeText(SwipeActivity.this, "Passed on " + name, Toast.LENGTH_SHORT).show();
                    } else if (direction == ItemTouchHelper.RIGHT) {
                        Toast.makeText(SwipeActivity.this, "Liked " + name, Toast.LENGTH_SHORT).show();
                    }

                    petList.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    checkIfListIsEmpty();
                }
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);

        // Button click handlers
        ImageButton likeBtn = findViewById(R.id.button_like);
        ImageButton passBtn = findViewById(R.id.button_pass);

        likeBtn.setOnClickListener(v -> handleSwipe(ItemTouchHelper.RIGHT));
        passBtn.setOnClickListener(v -> handleSwipe(ItemTouchHelper.LEFT));
    }

    private void handleSwipe(int direction) {

        if (!petList.isEmpty()) {
            int pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            if (pos == RecyclerView.NO_POSITION) {
                pos = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            }

            if (pos >= 0 && pos < petList.size()) {
                PetProfile pet = petList.get(pos);
                String name = pet.name;
                if (direction == ItemTouchHelper.LEFT) {
                    Toast.makeText(SwipeActivity.this, "Passed on " + name, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SwipeActivity.this, "Liked " + name, Toast.LENGTH_SHORT).show();
                }
                petList.remove(pos);
                adapter.notifyItemRemoved(pos);
                checkIfListIsEmpty();
            }
        }
    }

    private void checkIfListIsEmpty() {
        if (petList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            subMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            likeBtn.setVisibility(View.GONE);
            passBtn.setVisibility(View.GONE);
        }
    }
}
