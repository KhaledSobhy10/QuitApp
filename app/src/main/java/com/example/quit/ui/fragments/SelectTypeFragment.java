package com.example.quit.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quit.databinding.FragmentSelectTypeBinding;
import com.example.quit.models.AddictionType;


public class SelectTypeFragment extends Fragment {

    private AddictionType selectedAddictionType = AddictionType.MONEY_WASTING;
    private FragmentSelectTypeBinding binding;


    public SelectTypeFragment() {
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
        binding = FragmentSelectTypeBinding.inflate(getLayoutInflater(), container, false);

        binding.moneyRb.setOnCheckedChangeListener((rb, isChecked) ->
        {
            if (isChecked) {
                binding.moneyDiv.setVisibility(View.VISIBLE);
                binding.timeDiv.setVisibility(View.GONE);
                binding.eventRb.setChecked(false);
                binding.timeRb.setChecked(false);
                selectedAddictionType = AddictionType.MONEY_WASTING;
            }
        });

        binding.timeRb.setOnCheckedChangeListener((rb, isChecked) ->
        {
            if (isChecked) {
                binding.moneyDiv.setVisibility(View.GONE);
                binding.timeDiv.setVisibility(View.VISIBLE);
                binding.eventRb.setChecked(false);
                binding.moneyRb.setChecked(false);
                selectedAddictionType = AddictionType.TIME_WASTING;

            }

        });

        binding.eventRb.setOnCheckedChangeListener((rb, isChecked) -> {
            if (isChecked) {
                binding.timeDiv.setVisibility(View.GONE);
                binding.moneyDiv.setVisibility(View.GONE);
                binding.moneyRb.setChecked(false);
                binding.timeRb.setChecked(false);
                selectedAddictionType = AddictionType.EVENT;


            }
        });
        return binding.getRoot();
    }

    public AddictionType getSelectedAddictionType() {
        return selectedAddictionType;
    }

    public double getWastedMoney() {
        String wastedMoneyStr = binding.moneyMountEt.getText().toString();
        if (wastedMoneyStr.isEmpty()) {
            binding.moneyMountEt.setError("Invalid input");
            return 0;
        } else {
            binding.moneyMountEt.setError(null);
            return Double.parseDouble(wastedMoneyStr);
        }

    }

    public double getWastedTime() {
        String wastedTimeStr = binding.timeMountEt.getText().toString();
        if (wastedTimeStr.isEmpty()) {
            binding.timeMountEt.setError("Invalid input");
            return 0;
        } else {
            binding.timeMountEt.setError(null);
            return Double.parseDouble(wastedTimeStr);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}