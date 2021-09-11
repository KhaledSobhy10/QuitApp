package com.example.quit.ui.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quit.databinding.FragmentSelectDateBinding;
import com.example.quit.ui.activities.AddAddictionActivity;
import com.example.quit.utilities.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SelectDateFragment extends Fragment {
    private FragmentSelectDateBinding binding;
    private long lastTime;
    SetNameAndIconFragment setNameAndIconFragment;
    Calendar c;


    public static SelectDateFragment newInstance(SetNameAndIconFragment iconFragment) {

        SelectDateFragment fragment = new SelectDateFragment();
       fragment.setNameAndIconFragment = iconFragment;
        return fragment;
    }
    public SelectDateFragment() {
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

        binding = FragmentSelectDateBinding.inflate(inflater, container, false);

        c = Calendar.getInstance();
        lastTime = c.getTimeInMillis();

        binding.selectDate.setText(Time.formatTimeToDMYHMA(c.getTimeInMillis()));
        binding.imageView5.setImageResource(setNameAndIconFragment.getSelectedIconID());


        binding.selectDate.setOnClickListener((btn) -> {
            buildDatePickerDialog().show();
        });

        return binding.getRoot();
    }

    private DatePickerDialog buildDatePickerDialog() {
        int mYear, mMonth, mDay, mHour, mMinute;
        // Get Current Date
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR);
        mMinute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, month, dayOfMonth) -> {
                    new TimePickerDialog(getActivity(), (view1, hourOfDay, minute) -> {
                        c.set(year, month, dayOfMonth, hourOfDay, minute);

                        lastTime = c.getTimeInMillis();
                        binding.selectDate.setText(Time.formatTimeToDMYHMA(c.getTimeInMillis()));

                    }, mHour, mMinute, false).show();

                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    public long getLastTime() {
        return lastTime;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}