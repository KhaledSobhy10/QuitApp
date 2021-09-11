package com.example.quit.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quit.R;
import com.example.quit.databinding.AddictionItemBinding;
import com.example.quit.databinding.EditAddictionNameBinding;
import com.example.quit.databinding.SelectAddictionIconBinding;
import com.example.quit.models.Addiction;
import com.example.quit.utilities.Time;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Addiction> addictionList;
    private OnAddictionItemClickListener listener;


    public MainAdapter(List<Addiction> addictionList, OnAddictionItemClickListener listener) {
        this.addictionList = addictionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddictionItemBinding addictionItemBinding = AddictionItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(addictionItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        Addiction addiction = addictionList.get(position);

        holder.itemBinding.addictionNameTv.setText(addiction.getAddictionName());
        holder.itemBinding.addictionIconIv.setImageResource(addiction.getIconId());

        holder.itemBinding.chronometer.setOnChronometerTickListener(cArg -> cArg.setText(Time.getAchievedTime(addiction.getLastDateOfQuit())));
        holder.itemBinding.chronometer.setBase(SystemClock.elapsedRealtime());
        holder.itemBinding.chronometer.start();

        double percent = addiction.getPercent();
        if (percent >= 100) {
            addiction.goToNextTargetDate();
            listener.updateActionItemClicked(addiction);
            percent = addiction.getPercent();
        }
        holder.itemBinding.targetTimeTv.setText(addiction.getTargetDateType().toString());
        holder.itemBinding.arcProgress.setProgress((float) percent);

        holder.itemBinding.popMenu.setOnClickListener((btn) -> showPopupMenu(holder.itemBinding.popMenu, position));

    }

    private void showPopupMenu(AppCompatImageButton popMenu, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(popMenu.getContext(), popMenu);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.addiction_actions_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_change_addiction_name) {
                showAlertRenameAddiction(position, popMenu.getContext());
                return true;
            } else if (itemId == R.id.action_change_addiction_icon) {
                showAlertChangeIcon(position, popMenu.getContext());
                return true;
            } else if (itemId == R.id.action_delete_addiction) {
                listener.deleteActionItemClicked(addictionList.get(position).getId());
                return true;
            }
            return false;
        });

        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        popup.show();

    }

    private void showAlertRenameAddiction(int position, Context context) {
        Addiction addiction = addictionList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        EditAddictionNameBinding binding = EditAddictionNameBinding.inflate(LayoutInflater.from(context));

        binding.renameEt.setText(addiction.getAddictionName());
        builder.setView(binding.getRoot());
        Dialog dialog = builder.create();

        binding.cancelChangingBtn.setOnClickListener((cancelBtn) -> dialog.dismiss());

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        binding.saveChangingBtn.setOnClickListener(v -> {
                    Editable newAddictionName = binding.renameEt.getText();
                    if (newAddictionName != null && !newAddictionName.toString().isEmpty()) {
                        binding.renameEt.setError(null);
                        addiction.setAddictionName(newAddictionName.toString());
                        listener.updateActionItemClicked(addiction);
                        dialog.dismiss();
                    } else
                        binding.renameEt.setError("Invalid input");

                }
        );

        dialog.show();
    }

    public void updateData(List<Addiction> newAddictionList) {
        addictionList = newAddictionList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addictionList != null ? addictionList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AddictionItemBinding itemBinding;


        public ViewHolder(@NonNull AddictionItemBinding addictionItemBinding) {
            super(addictionItemBinding.getRoot());
            this.itemBinding = addictionItemBinding;
            itemBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.addictionItemClicked(addictionList.get(getAdapterPosition()).getId());
        }
    }

    private void showAlertChangeIcon(int position, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        SelectAddictionIconBinding binding = SelectAddictionIconBinding.inflate(LayoutInflater.from(context));
        binding.iconGv.setLayoutManager(new GridLayoutManager(context, 4));
        builder.setView(binding.getRoot());
        Dialog dialog = builder.create();
        binding.iconGv.setAdapter(new IconGridViewAdapter(iconId -> {
            addictionList.get(position).setIconId(iconId);
            listener.updateActionItemClicked(addictionList.get(position));
            dialog.dismiss();
        }));

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        binding.cancelSelectionIconBtn.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public interface OnAddictionItemClickListener {
        void addictionItemClicked(int addictionId);

        void deleteActionItemClicked(int addictionId);

        void updateActionItemClicked(Addiction addiction);

    }
}
