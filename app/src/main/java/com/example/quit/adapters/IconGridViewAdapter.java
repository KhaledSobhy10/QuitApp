package com.example.quit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quit.R;
import com.example.quit.databinding.AddictionIconItemBinding;

public class IconGridViewAdapter extends RecyclerView.Adapter<IconGridViewAdapter.MyViewHolder>  {
    int[] iconIds;
   private OnIconClickListener onIconClickListener;
    public IconGridViewAdapter(OnIconClickListener onIconClickListener) {
        this.onIconClickListener = onIconClickListener;
        iconIds = new int[26];
        iconIds[0] = R.drawable.ic_addiction_generic;
        iconIds[1] = R.drawable.ic_addiction_facebook;
        iconIds[2] = R.drawable.ic_addiction_youtube;
        iconIds[3] = R.drawable.ic_addiction_games;
        iconIds[4] = R.drawable.ic_addiction_alcohol;
        iconIds[5] = R.drawable.ic_addiction_cannabis;
        iconIds[6] = R.drawable.ic_addiction_coffee;
        iconIds[7] = R.drawable.ic_addiction_cursing;
        iconIds[8] = R.drawable.ic_addiction_drugs;
        iconIds[9] = R.drawable.ic_addiction_instagram;
        iconIds[10] = R.drawable.ic_addiction_lie;
        iconIds[11] = R.drawable.ic_addiction_food;
        iconIds[12] = R.drawable.ic_addiction_overeating;
        iconIds[13] = R.drawable.ic_addiction_gamebling;
        iconIds[14] = R.drawable.ic_addiction_meat;
        iconIds[15] = R.drawable.ic_addiction_pils;
        iconIds[16] = R.drawable.ic_addiction_porn;
        iconIds[17] = R.drawable.ic_addiction_procrastination;
        iconIds[18] = R.drawable.ic_addiction_shopping;
        iconIds[19] = R.drawable.ic_addiction_twitter;
        iconIds[20] = R.drawable.ic_addiction_tv;
        iconIds[21] = R.drawable.ic_addiction_sugar;
        iconIds[22] = R.drawable.ic_addiction_smoke;
        iconIds[23] = R.drawable.ic_addiction_soda;
        iconIds[24] = R.drawable.ic_addiction_reddit;
        iconIds[25] = R.drawable.ic_addiction_quarrel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(AddictionIconItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.iconIv.setImageResource(iconIds[position]);

    }

    @Override
    public int getItemCount() {
        return iconIds != null ? iconIds.length : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AddictionIconItemBinding binding;

        public MyViewHolder(@NonNull AddictionIconItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.iconIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onIconClickListener.onIconClicked(iconIds[getAdapterPosition()]);

        }
    }

    public interface OnIconClickListener {
        void onIconClicked(int iconId);
    }
}
