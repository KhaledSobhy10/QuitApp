package com.example.quit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quit.databinding.SelectIconDialogItemBinding;
import com.example.quit.models.Addiction;

public class SelectIconDialogAdapter extends RecyclerView.Adapter<SelectIconDialogAdapter.MyViewHolder> {
    
    Addiction[] addictions;
    IconClickListener iconClickListener;
    public SelectIconDialogAdapter(IconClickListener iconClickListener) {
        addictions = Addiction.getAddictions();
        this.iconClickListener = iconClickListener;
        
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SelectIconDialogItemBinding binding = SelectIconDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectIconDialogAdapter.MyViewHolder holder, int position) {
        holder.binding.iconIv.setImageResource(addictions[position].getIconId());
        holder.binding.iconNameTv.setText(addictions[position].getAddictionName());
    }

    @Override
    public int getItemCount() {
        return addictions.length;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        SelectIconDialogItemBinding binding;

        public MyViewHolder(@NonNull SelectIconDialogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> iconClickListener.iconClick(addictions[getAdapterPosition()].getAddictionName(),addictions[getAdapterPosition()].getIconId()));
        }

    }
    
    public interface IconClickListener{
             void iconClick(String  addictionName , int selectedIcon);
    }
}
