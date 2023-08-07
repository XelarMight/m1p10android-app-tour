package com.example.basictour;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder> {

    private List<Attraction> attractions;
    private Context context;

    public AttractionAdapter(List<Attraction> attractions) {
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public AttractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_attraction, parent, false);
        return new AttractionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttractionViewHolder holder, int position) {
        Attraction attraction = attractions.get(position);

        holder.ratingBarAttraction.setRating(attraction.getRating());
        holder.textViewAttractionName.setText(attraction.getName());

        String packageName = context.getPackageName();

        String resourceName = attraction.getBackgroundImageUrl().substring(0, attraction.getBackgroundImageUrl().indexOf("."));
        Log.d("RES: ", resourceName);
        int resourceId = ((Activity) context).getResources().getIdentifier(resourceName, "drawable", packageName);
        Log.d("RES: ", "ID: "+resourceId);
        holder.imageViewAttractionBackground.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public class AttractionViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewAttractionBackground;
        private RatingBar ratingBarAttraction;
        private TextView textViewAttractionName;

        public AttractionViewHolder(View itemView) {
            super(itemView);
            imageViewAttractionBackground = itemView.findViewById(R.id.imageViewAttractionBackground);
            ratingBarAttraction = itemView.findViewById(R.id.ratingBarAttraction);
            textViewAttractionName = itemView.findViewById(R.id.textViewAttractionName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
