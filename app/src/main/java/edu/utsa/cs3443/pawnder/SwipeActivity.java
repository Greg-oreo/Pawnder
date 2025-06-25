package edu.utsa.cs3443.pawnder;

import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class SwipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetCardAdapter adapter;
    private List<UserPetProfile> petList = new ArrayList<>();
    private TextView emptyMessage;
    private TextView subMessage;

    private float initialX = -1f;
    private float initialY = -1f;

    private Map<String, Set<String>> likesMap = new HashMap<>();
    private List<Match> matches = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        emptyMessage = findViewById(R.id.emptyMessage);
        subMessage = findViewById(R.id.subMessage);
        recyclerView = findViewById(R.id.cardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sample images
        List<Uri> userImages = Arrays.asList(
                drawableToUri(R.drawable.alex),
                drawableToUri(R.drawable.alex2)
        );

        List<Integer> petImages = Arrays.asList(
                R.drawable.golden_retriever,
                R.drawable.golden_retriever2
        );
        List<Uri> userImages1 = Arrays.asList(
                drawableToUri(R.drawable.laura),
                drawableToUri(R.drawable.laura2)
        );

        List<Integer> petImages1 = Arrays.asList(
                R.drawable.oakley,
                R.drawable.oakley2
        );


        petList.add(new UserPetProfile(
                "Alex", "20", "Austin", "Love to go for hikes", userImages,
                "Buddy", "3", "Golden Retriever", "Austin", "Friendly",
                "Loves kids and playing fetch.", "Male", petImages
        ));
        petList.add(new UserPetProfile(
                "Laura", "25", "Del Rio", "Love to read", userImages1,
                "Oakley", "1", "German Shepard", "Del Rio", "Friendly",
                "Loves escape the house.", "Male", petImages1
        ));

        adapter = new PetCardAdapter(petList, this);
        recyclerView.setAdapter(adapter);

        // Touch tracking for swipe filtering
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                initialX = event.getRawX();
                initialY = event.getRawY();
            }
            return false;
        });

        // Swipe-to-like/pass logic
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(@NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder) {
                if (isTouchOnViewPager(viewHolder.itemView)) {
                    return 0; // Disable swipe if touching image slider
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder vh, int direction) {
                int pos = vh.getAdapterPosition();

                if (pos >= 0 && pos < petList.size()) {
                    UserPetProfile pet = petList.get(pos);
                    String likedUser = pet.userName;
                    String currentUser = "Alex"; // Assume this device is running as "Alex"

                    if (direction == ItemTouchHelper.RIGHT) {
                        Toast.makeText(SwipeActivity.this, "Liked " + likedUser, Toast.LENGTH_SHORT).show();

                        // Save like
                        if (!likesMap.containsKey(currentUser)) {
                            likesMap.put(currentUser, new HashSet<>());
                        }
                        likesMap.get(currentUser).add(likedUser);

                        // Check if the other user liked back
                        if (likesMap.containsKey(likedUser) && likesMap.get(likedUser).contains(currentUser)) {
                            MatchManager.getInstance().addMatch(new Match(currentUser, likedUser, pet.petName));
                            Toast.makeText(SwipeActivity.this, "🎉 It's a Match with " + likedUser + "!", Toast.LENGTH_LONG).show();
                        }

                    } else if (direction == ItemTouchHelper.LEFT) {
                        Toast.makeText(SwipeActivity.this, "Passed on " + likedUser, Toast.LENGTH_SHORT).show();
                    }

                    petList.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    checkIfListIsEmpty();
                }
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }

    private void checkIfListIsEmpty() {
        if (petList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            subMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private Uri drawableToUri(int resId) {
        return Uri.parse("android.resource://" + getPackageName() + "/" + resId);
    }

    private boolean isTouchOnViewPager(View itemView) {
        if (initialX < 0 || initialY < 0) return false;

        ViewPager2 userViewPager = itemView.findViewById(R.id.userImagePager);
        ViewPager2 petViewPager = itemView.findViewById(R.id.petImagePager);

        return isPointInsideView(initialX, initialY, userViewPager)
                || isPointInsideView(initialX, initialY, petViewPager);
    }

    private boolean isPointInsideView(float x, float y, View view) {
        if (view == null) return false;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        return (x >= viewX && x <= (viewX + view.getWidth()))
                && (y >= viewY && y <= (viewY + view.getHeight()));
    }
}
