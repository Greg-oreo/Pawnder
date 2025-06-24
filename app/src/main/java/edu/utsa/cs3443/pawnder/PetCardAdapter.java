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
    private List<UserPetProfile> petList;
    private Context context;

    public PetCardAdapter(List<UserPetProfile> petList, Context context) {
        this.petList = petList;
        this.context = context;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_card, parent, false);
        return new PetViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        UserPetProfile profile = petList.get(position);

        // --- Pet Info ---
        holder.petName.setText(profile.petName + ", " + profile.petAge);
        holder.petBreed.setText(profile.petBreed);
        holder.petLocation.setText(profile.petLocation);
        holder.petTemperament.setText("Temperament: " + profile.petTemperament);
        holder.petDescription.setText(profile.petDescription);

        if (profile.getPhotos() != null && !profile.getPhotos().isEmpty()) {
            Glide.with(context)
                    .load(profile.getPhotos().get(0))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(32)))
                    .into(holder.petImage);
        }

        if (profile.petGender != null) {
            if (profile.petGender.equalsIgnoreCase("Male")) {
                holder.petGender.setImageResource(R.drawable.male);
            } else if (profile.petGender.equalsIgnoreCase("Female")) {
                holder.petGender.setImageResource(R.drawable.female);
            } else {
                holder.petGender.setVisibility(View.GONE);
            }
        }

        // --- User Info ---
        holder.userName.setText(profile.userName + ", " + profile.userAge);
        holder.userLocation.setText(profile.userLocation);
        holder.userBio.setText(profile.userBio);

        if (profile.userImageUri != null) {
            Glide.with(context)
                    .load(profile.userImageUri != null ? profile.userImageUri : R.drawable.alex)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(32)))
                    .into(holder.userImage);
        }
    }


    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {
        TextView petName, petBreed, petTemperament, petLocation, petDescription;
        ImageView petImage,petGender;

        TextView userName, userLocation, userBio;
        ImageView userImage;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            // Pet
            petGender = itemView.findViewById(R.id.petGender);
            petName = itemView.findViewById(R.id.petName);
            petBreed = itemView.findViewById(R.id.petBreed);
            petLocation = itemView.findViewById(R.id.petLocation);
            petTemperament = itemView.findViewById(R.id.petTemperament);
            petDescription = itemView.findViewById(R.id.petDescription);
            petImage = itemView.findViewById(R.id.petImage);

            // User
            userName = itemView.findViewById(R.id.userName);
            userLocation = itemView.findViewById(R.id.userLocation);
            userBio = itemView.findViewById(R.id.userBio);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }
}
