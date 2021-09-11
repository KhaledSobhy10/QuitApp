package com.example.quit.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quit.databinding.EditDeleteRelapseBottomSheetBinding;
import com.example.quit.models.Relapse;
import com.example.quit.repo.AppRepo;
import com.example.quit.ui.activities.NewEntryActivity;
import com.example.quit.utilities.Time;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class EDRelapseBottomSheet extends BottomSheetDialogFragment {

    private EditDeleteRelapseBottomSheetBinding binding;
    private Relapse relapse;

    public static EDRelapseBottomSheet newInstance(Relapse relapse) {
        EDRelapseBottomSheet fragment = new EDRelapseBottomSheet();
        fragment.relapse = relapse;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EditDeleteRelapseBottomSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.relapseDateTv.setText(Time.formatTimeToDMYHMA(relapse.getRelapseDate()));
        binding.relapseCommentTv.setText(relapse.getComment());

        AppRepo appRepo = AppRepo.getInstance(getActivity().getApplication());
        binding.deleteThatRelapse.setOnClickListener((btn) -> {
            appRepo.deleteRelapse(relapse);
            dismiss();
        });

        binding.editThatRelapse.setOnClickListener((btn) -> {
            dismiss();
            Intent intent  = new Intent(getActivity(), NewEntryActivity.class);
            intent.putExtra(NewEntryActivity.ACTIVITY_USAGE_KEY, NewEntryActivity.ACTIVITY_USAGE_EDIT_VALUE);
            intent.putExtra(NewEntryActivity.ADDICTION_ID_KEY,relapse.getAddictionId());
            intent.putExtra(NewEntryActivity.RELAPSE_DATE_KEY, relapse.getRelapseDate());
            startActivity(intent);
        });


    }
}
