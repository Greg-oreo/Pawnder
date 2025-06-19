package edu.utsa.cs3443.pawnder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PetCardAdapter extends RecyclerView.Adapter<PetCardAdapter.PetViewHolder> {
    private List<PetProfile> petList;
    private Context context;

    public PetCardAdapter(List<PetProfile> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pet_card, parent, false);
        return new PetViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        PetProfile pet = petList.get(position);
        holder.petName.setText(pet.name + ", " + pet.age);
        holder.petBreed.setText(pet.breed);
        holder.petLocation.setText(pet.location);
        holder.petTemperament.setText("Temperament: " + pet.temperament);
        holder.petDescription.setText(pet.description);

        if (pet.getPhotos() != null && !pet.getPhotos().isEmpty()) {
            // Load image resource with Glide
            Glide.with(context)
                    .load(pet.getPhotos().get(0))  // this is the drawable resource id (e.g., R.drawable.your_image)
                    .into(holder.petImage);
        }
        Glide.with(context)
                .load(pet.getPhotos().get(0))  // Drawable resource ID
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(32))) // 32 = corner radius in pixels
                .into(holder.petImage);
        if (pet.gender.equalsIgnoreCase("Male")) {
            holder.petGender.setImageResource(R.drawable.male);
        } else if (pet.gender.equalsIgnoreCase("Female")) {
            holder.petGender.setImageResource(R.drawable.female);
        } else {
            holder.petGender.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petBreed, petTemperament, petLocation, petDescription;
        ImageView petImage, petGender;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petGender = itemView.findViewById(R.id.petGender);
            petName = itemView.findViewById(R.id.petName);
            petBreed = itemView.findViewById(R.id.petBreed);
            petLocation = itemView.findViewById(R.id.petLocation); // Make sure this ID exists in XML
            petTemperament = itemView.findViewById(R.id.petTemperament);
            petDescription = itemView.findViewById(R.id.petDescription);
            petImage = itemView.findViewById(R.id.petImage);
        }
    }
}
