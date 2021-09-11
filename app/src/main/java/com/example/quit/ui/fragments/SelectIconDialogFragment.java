package com.example.quit.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quit.adapters.SelectIconDialogAdapter;
import com.example.quit.databinding.SelectIconDialogBinding;

public class SelectIconDialogFragment extends DialogFragment implements SelectIconDialogAdapter.IconClickListener {

    private final SelectIconDialogAdapter.IconClickListener iconClickListener;

    public SelectIconDialogFragment(SelectIconDialogAdapter.IconClickListener iconClickListener) {
        this.iconClickListener = iconClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        SelectIconDialogBinding dialogBinding = SelectIconDialogBinding.inflate(getLayoutInflater());

        dialogBinding.iconsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogBinding.iconsRv.setHasFixedSize(true);
        SelectIconDialogAdapter dialogAdapter = new SelectIconDialogAdapter(this);
        dialogBinding.iconsRv.setAdapter(dialogAdapter);


        dialogBinding.cancelSelectionBtn.setOnClickListener((btn) -> dismiss());

        builder.setView(dialogBinding.getRoot());
        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    @Override
    public void iconClick(String addictionName, int selectedIcon) {
        dismiss();
        iconClickListener.iconClick(addictionName, selectedIcon);
    }


}
