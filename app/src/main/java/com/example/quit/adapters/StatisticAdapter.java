package com.example.quit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quit.databinding.StatisticItemBinding;
import com.example.quit.models.Statistic;

import java.util.List;

public class StatisticAdapter  extends  RecyclerView.Adapter<StatisticAdapter.MyViewHolder>{

    List<Statistic> statisticList;
    public StatisticAdapter() {
    }
    public StatisticAdapter(List<Statistic> statisticList) {
        this.statisticList = statisticList;
    }

   void setData(List<Statistic> statisticList){
       this.statisticList =statisticList;
       notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StatisticItemBinding binding = StatisticItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Statistic statistic = statisticList.get(position);

        holder.binding.appCompatImageView.setImageResource(statistic.getStatisticIconId());
        holder.binding.statisticItemLabel.setText(statistic.getStatisticType().toString());
        holder.binding.statisticValue.setText(statistic.getStatisticValue());

    }

    @Override
    public int getItemCount() {
        return statisticList != null ? statisticList.size() : 0;
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        StatisticItemBinding binding;
        public MyViewHolder(@NonNull StatisticItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
