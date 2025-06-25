package edu.utsa.cs3443.pawnder;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder> {
    private final Context context;
    private final List<Integer> imageList;   // For drawable resource images
    private final List<Uri> imageUris;       // For Uri images
    private final boolean useUri;            // Flag to determine which to use

    // Constructor for drawable resource images
    public ImageSliderAdapter(Context context, List<Integer> imageList) {
        this.context = context;
        this.imageList = imageList;
        this.imageUris = null;
        this.useUri = false;
    }

    // Constructor for URI images
    public ImageSliderAdapter(Context context, List<Uri> imageUris, boolean useUri) {
        this.context = context;
        this.imageUris = imageUris;
        this.imageList = null;
        this.useUri = useUri;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        // Load the appropriate image type
        Object image = useUri ? imageUris.get(position) : imageList.get(position);

        Glide.with(context)
                .load(image)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(32)))
                .into(holder.sliderImage);

        // Handle tap to advance
        holder.sliderImage.setOnClickListener(v -> {
            View parentView = (View) holder.itemView.getParent();
            if (parentView instanceof RecyclerView) {
                ViewPager2 viewPager = (ViewPager2) parentView.getParent();
                int current = viewPager.getCurrentItem();
                int total = getItemCount();
                viewPager.setCurrentItem((current + 1) % total, true); // Loop to start
            }
        });
    }

    @Override
    public int getItemCount() {
        return useUri ? (imageUris != null ? imageUris.size() : 0)
                : (imageList != null ? imageList.size() : 0);
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            sliderImage = itemView.findViewById(R.id.sliderImage);
        }
    }
}
