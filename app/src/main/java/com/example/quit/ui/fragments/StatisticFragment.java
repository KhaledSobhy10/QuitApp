package com.example.quit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quit.adapters.StatisticAdapter;
import com.example.quit.background.AppExecutor;
import com.example.quit.databinding.FragmentStatisticBinding;
import com.example.quit.models.Statistic;
import com.example.quit.viewmodels.AddictionItemViewModel;

import java.util.List;


public class StatisticFragment extends Fragment {
    private static final String TAG = StatisticFragment.class.getSimpleName();

    private FragmentStatisticBinding binding;


    public StatisticFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatisticBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddictionItemViewModel addictionItemViewModel = new ViewModelProvider(requireActivity()).get(AddictionItemViewModel.class);
        addictionItemViewModel.getAddictionWithRelapseLiveData().observe(getViewLifecycleOwner(), addictionWithRelapse -> {
            AppExecutor.getAppExecutor().getBackgroundThread().execute(()->{
                List<Statistic> statistics =Statistic.getAllStatistic(addictionWithRelapse);
                AppExecutor.getAppExecutor().getMainThread().execute(()-> setupRecyclerView(statistics));
            });
        });
    }

    private void setupRecyclerView(List<Statistic> statistics) {
        binding.statisticsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.statisticsRv.setHasFixedSize(true);
        binding.statisticsRv.setAdapter(new StatisticAdapter(statistics));

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}