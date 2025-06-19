public class BrowseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetCardAdapter adapter;
    private BrowseViewModel viewModel;
    private TextView titleText;
    private ProgressBar loadingSpinner;
    private Button createProfileBtn;
    private View noPetsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        recyclerView = findViewById(R.id.recyclerView);
        titleText = findViewById(R.id.titleText);
        loadingSpinner = findViewById(R.id.loadingSpinner);
        createProfileBtn = findViewById(R.id.createProfileBtn);
        noPetsView = findViewById(R.id.noPetsView);

        viewModel = new ViewModelProvider(this).get(BrowseViewModel.class);

        adapter = new PetCardAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        observeViewModel();
        setupSwipeGesture();

        createProfileBtn.setOnClickListener(v -> {
            startActivity(new Intent(BrowseActivity.this, CreateProfileActivity.class));
        });
    }

    private void observeViewModel() {
        viewModel.getBrowsablePets().observe(this, pets -> {
            loadingSpinner.setVisibility(View.GONE);
            if (pets == null || pets.isEmpty()) {
                if (viewModel.getMyPets().getValue() == null || viewModel.getMyPets().getValue().isEmpty()) {
                    noPetsView.setVisibility(View.VISIBLE);
                    titleText.setText("Please create a profile for your pet.");
                    createProfileBtn.setVisibility(View.VISIBLE);
                } else {
                    titleText.setText("No more pets to browse.");
                }
            } else {
                adapter.updatePets(pets);
                noPetsView.setVisibility(View.GONE);
            }
        });
    }

    private void setupSwipeGesture() {
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView rv, RecyclerView.ViewHolder vh, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vh, int direction) {
                int position = vh.getAdapterPosition();
                Pet swipedPet = adapter.getPetAt(position);

                if (direction == ItemTouchHelper.RIGHT) {
                    viewModel.likePet(swipedPet.getId());
                } else {
                    viewModel.passPet(swipedPet.getId());
                }

                adapter.removePetAt(position);
            }
        };

        new ItemTouchHelper(swipeCallback).attachToRecyclerView(recyclerView);
    }
}
