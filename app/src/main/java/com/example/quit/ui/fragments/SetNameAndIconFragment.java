package com.example.quit.ui.fragments;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quit.R;
import com.example.quit.adapters.SelectIconDialogAdapter;
import com.example.quit.databinding.FragmentNameAndIconBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetNameAndIconFragment extends Fragment implements SelectIconDialogAdapter.IconClickListener  {

private static final String TAG = SetNameAndIconFragment.class.getSimpleName();
   private ObjectAnimator scaleDown;
   private FragmentNameAndIconBinding binding;
   private String addictionName;
   private int selectedIconID = R.drawable.ic_addiction_generic;

    public SetNameAndIconFragment() {
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
         binding = FragmentNameAndIconBinding.inflate(getLayoutInflater(), container, false);

         binding.addictionNameEt.setActivated(true);
        binding.addictionNameEt.setPressed(true);

//        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        setupGrowAndShrinkAnimation(binding.warningIb);

        binding.warningIb.setOnClickListener((btn)->{
            if (scaleDown != null){
                scaleDown.cancel();
                scaleDown = null;
            }
               new  SelectIconDialogFragment(this).show(getActivity().getSupportFragmentManager(), "test");
        });

        return binding.getRoot();
    }


    private void setupGrowAndShrinkAnimation(View view){
        if (canRunAnimation()) {
            scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                    view,
                    PropertyValuesHolder.ofFloat("scaleX", 0.9f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.9f)
            );
            scaleDown.setDuration(500);
            scaleDown.setRepeatMode(ValueAnimator.REVERSE);
            scaleDown.setRepeatCount(ValueAnimator.INFINITE);
            scaleDown.start();
        }

    }

    @Override
    public void iconClick(String addictionName,int selectedIconID) {
        this.selectedIconID = selectedIconID;
        binding.addictionIconIv.setImageResource(selectedIconID);
        binding.addictionNameEt.setText(addictionName , TextView.BufferType.EDITABLE);
        binding.addictionNameEt.setError(null);
        Log.d(TAG, "iconClick: from name&con "+addictionName);
    }


    public int getSelectedIconID() {
        return selectedIconID;
    }

    public String getAddictionName() {
       addictionName = binding.addictionNameEt.getText().toString();
       if (addictionName.isEmpty()){
           binding.addictionNameEt.setError("Invalid input");
           return null;
       }
        return addictionName;
    }




    private boolean canRunAnimation(){
        PowerManager powerManager ;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M ) {
            powerManager = (PowerManager) getContext().getSystemService(getContext().POWER_SERVICE);
           return !powerManager.isPowerSaveMode();
        }
        return true;
    }
}