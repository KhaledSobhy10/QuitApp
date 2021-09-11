package com.example.quit.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quit.R;
import com.example.quit.adapters.RelapseEntryAdapter;
import com.example.quit.databinding.RelapsesBottomSheetBinding;
import com.example.quit.models.Relapse;
import com.example.quit.ui.activities.NewEntryActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;



public class RelapseBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener, RelapseEntryAdapter.RelapseEntryClickListener {

    private RelapsesBottomSheetBinding binding;
    private List<Relapse> relapses;

    public static RelapseBottomSheet newInstance(List<Relapse> relapses) {
        RelapseBottomSheet fragment = new RelapseBottomSheet();
        fragment.relapses = relapses;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RelapsesBottomSheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable  Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.entriesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.entriesRv.setHasFixedSize(true);
        RelapseEntryAdapter relapseEntryAdapter = new RelapseEntryAdapter(relapses,this);
        binding.entriesRv.setAdapter(relapseEntryAdapter);


        binding.addNewEntryBtn.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.addNewEntryBtn.getId()){
            dismiss();
            Intent intent = new Intent(getActivity(), NewEntryActivity.class);
            intent.putExtra(NewEntryActivity.ACTIVITY_USAGE_KEY, NewEntryActivity.ACTIVITY_USAGE_INSERT_VALUE);
            intent.putExtra(NewEntryActivity.ADDICTION_ID_KEY, relapses.get(0).getAddictionId());
            intent.putExtra(NewEntryActivity.RELAPSE_DATE_KEY, relapses.get(0).getRelapseDate());
            startActivity(intent);
        }

    }

    @Override
    public void entryCLicked(Relapse relapse) {
        dismiss();
        EDRelapseBottomSheet edRelapseBottomSheet = EDRelapseBottomSheet.newInstance(relapse);
        edRelapseBottomSheet.show(getParentFragmentManager(), "");

    }
}
