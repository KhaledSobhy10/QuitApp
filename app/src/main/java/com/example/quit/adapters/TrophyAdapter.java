package com.example.quit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quit.R;
import com.example.quit.databinding.TrophyItemBinding;
import com.example.quit.models.Trophy;

import java.util.List;

public class TrophyAdapter extends RecyclerView.Adapter<TrophyAdapter.TrophyHolder> {
    private List<Trophy> trophies;
    private Context context;

    public TrophyAdapter(List<Trophy> trophies) {
        this.trophies = trophies;
    }

    @NonNull
    @Override
    public TrophyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        TrophyItemBinding trophyItemBinding = TrophyItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrophyHolder(trophyItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrophyHolder holder, int position) {
        Trophy trophy = trophies.get(position);
        holder.binding.estimatedTimeToAchieve.setText(trophy.getEstimatedTime());
        holder.binding.trophyIv.setImageResource(trophy.getTrophyImageId());
        holder.binding.trophyTitleLabel.setText(trophy.getTrophyType().toString());
        if (trophy.isAchieved()) {
            holder.binding.progressBar.setProgressDrawable(AppCompatResources.getDrawable(context, R.drawable.linear_pb_bg_achieved));
        } else {
            holder.binding.progressBar.setProgressDrawable(AppCompatResources.getDrawable(context, R.drawable.linear_pb_bg));
        }
        holder.binding.progressBar.setProgress(trophy.getProgress());
    }

    public void setData(List<Trophy> trophies) {
        this.trophies = trophies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trophies != null ? trophies.size() : 0;
    }

    public static class TrophyHolder extends RecyclerView.ViewHolder {
        TrophyItemBinding binding;

        public TrophyHolder(@NonNull TrophyItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
