package com.example.quit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quit.databinding.EntryItemBinding;
import com.example.quit.models.Relapse;
import com.example.quit.ui.fragments.EDRelapseBottomSheet;
import com.example.quit.utilities.Time;

import java.util.List;

public class RelapseEntryAdapter extends RecyclerView.Adapter<RelapseEntryAdapter.EntryViewHolder> {

  private final List<Relapse> relapses;
  private RelapseEntryClickListener clickListener;
    public RelapseEntryAdapter(List<Relapse> relapses,RelapseEntryClickListener clickListener) {
        this.relapses = relapses;
    this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EntryItemBinding binding = EntryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EntryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
      holder.binding.entryTv.setText(Time.formatTimeToHM(relapses.get(position).getRelapseDate()));
    }

    @Override
    public int getItemCount() {
        return relapses == null ? 0 : relapses.size();
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EntryItemBinding binding;

        public EntryViewHolder(@NonNull EntryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.entryCLicked(relapses.get(getAdapterPosition()));
        }
    }


    public interface RelapseEntryClickListener{
       void entryCLicked(Relapse relapse);
    }
}