package com.example.quit.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quit.adapters.TrophyAdapter;
import com.example.quit.background.AppExecutor;
import com.example.quit.databinding.FragmentTrophiesBinding;
import com.example.quit.models.Trophy;
import com.example.quit.viewmodels.AddictionItemViewModel;

import java.util.List;


public class TrophiesFragment extends Fragment {
    private FragmentTrophiesBinding binding;

    public TrophiesFragment() {
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
        binding = FragmentTrophiesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrophyAdapter adapter = new TrophyAdapter(null);
        binding.trophiesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.trophiesRv.setHasFixedSize(true);
        binding.trophiesRv.setAdapter(adapter);
        AddictionItemViewModel addictionItemViewModel = new ViewModelProvider(requireActivity()).get(AddictionItemViewModel.class);

        addictionItemViewModel.getAddictionWithRelapseLiveData().observe(getViewLifecycleOwner(), addictionWithRelapse -> AppExecutor.getAppExecutor().getBackgroundThread().execute(()->{
           List< Trophy>  trophies = Trophy.getTrophyListToCurrentAchievedTime(addictionWithRelapse.addiction.getLastDateOfQuit());
           AppExecutor.getAppExecutor().getMainThread().execute(()-> adapter.setData(trophies));
        }));


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}