package com.example.quit.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quit.R;
import com.example.quit.databinding.FragmentOverviewBinding;
import com.example.quit.models.Addiction;
import com.example.quit.models.AddictionWithRelapse;
import com.example.quit.models.Relapse;
import com.example.quit.models.TargetDateType;
import com.example.quit.ui.activities.NewEntryActivity;
import com.example.quit.utilities.CalenderDaysDecorator;
import com.example.quit.utilities.Time;
import com.example.quit.viewmodels.AddictionItemViewModel;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.HashSet;
import java.util.List;

public class OverviewFragment extends Fragment  {
    private static final String TAG = OverviewFragment.class.getSimpleName();


    private FragmentOverviewBinding binding;
    private AddictionItemViewModel addictionItemViewModel;


    public OverviewFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOverviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addictionItemViewModel = new ViewModelProvider(requireActivity()).get(AddictionItemViewModel.class);
        addictionItemViewModel.getAddictionWithRelapseLiveData().observe(getViewLifecycleOwner(), data -> {

           setupChronometer(data.addiction.getLastDateOfQuit());

            float percent = (float) data.addiction.getPercent();
            binding.arcProgress.setProgress(percent >= 100 ? 100 : percent);


//          --------------------------------------
            HashSet<CalendarDay> calendarDaysRelapse = data.getRelapseDates();

            binding.customCv.addDecorator(getCalenderDaysDecorator(calendarDaysRelapse, data.addiction.getIcon12dp(), true));
//          -----------------------------------
            HashSet<CalendarDay> calendarDaysClean = data.addiction.getCleanDays(calendarDaysRelapse);
            binding.customCv.addDecorator(getCalenderDaysDecorator(calendarDaysClean, R.drawable.ic_chain_day, false));
//            ---------------------------------
            HashSet<CalendarDay> calendarDaysDayQuit = new HashSet<>();
            calendarDaysDayQuit.add(Time.getDateCalendarDayFormat(data.addiction.getFirstDateOfQuit()));
            binding.customCv.addDecorator(getCalenderDaysDecorator(calendarDaysDayQuit, R.drawable.ic_quit_date_12dp, true));
//          -----------------------------------------

            binding.targetTimeTv.setText(data.addiction.getTargetDateType().toString());
            binding.popMenuBtn.setOnClickListener(v -> showPopupMenuThatChangeTargetDate(v, data.addiction));


            binding.customCv.setOnDateChangedListener((widget, date, selected) -> {
                /* get relapses dates for that selected date if exist else go to add entry direct * */
               handleDateChanged(date, data);
            });

            binding.btnResetTimer.setOnClickListener((btn) -> addNewEntry(data.addiction.getId(),System.currentTimeMillis()));
        });


    }


    private void addNewEntry(int addictionId,long relapseTime) {
        Intent intent = new Intent(getActivity(), NewEntryActivity.class);
        intent.putExtra(NewEntryActivity.ACTIVITY_USAGE_KEY, NewEntryActivity.ACTIVITY_USAGE_INSERT_VALUE);
        intent.putExtra(NewEntryActivity.RELAPSE_DATE_KEY, relapseTime);
        intent.putExtra(NewEntryActivity.ADDICTION_ID_KEY, addictionId);
        startActivity(intent);
    }

    private CalenderDaysDecorator getCalenderDaysDecorator(HashSet<CalendarDay> calendarDays, int drawableId, boolean hideText) {
        return new CalenderDaysDecorator(calendarDays, AppCompatResources.getDrawable(requireContext(), drawableId), hideText);
    }

    private void showPopupMenuThatChangeTargetDate(View button, Addiction addiction) {
        // inflate menu
        PopupMenu popup = new PopupMenu(button.getContext(), button);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.over_view_fragment, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> handleTargetDateMenuItemClicked(item.getItemId(), addiction));
        popup.show();

    }

    private boolean handleTargetDateMenuItemClicked(int itemId, Addiction addiction) {
        TargetDateType targetDateType = addiction.getTargetDateType();
        if (itemId == R.id.action_24h) {
            targetDateType = TargetDateType.TD_24HOUR;

        } else if (itemId == R.id.action_3days) {
            targetDateType = TargetDateType.TD_3DAY;

        } else if (itemId == R.id.action_1week) {
            targetDateType = TargetDateType.TD_1WEEK;

        } else if (itemId == R.id.action_10days) {
            targetDateType = TargetDateType.TD_10DAY;

        } else if (itemId == R.id.action_2weeks) {
            targetDateType = TargetDateType.TD_2WEEK;

        } else if (itemId == R.id.action_1month) {
            targetDateType = TargetDateType.TD_1MONTH;

        } else if (itemId == R.id.action_3months) {
            targetDateType = TargetDateType.TD_3MONTH;

        } else if (itemId == R.id.action_6months) {
            targetDateType = TargetDateType.TD_6MONTH;

        } else if (itemId == R.id.action_1year) {
            targetDateType = TargetDateType.TD_1YEAR;

        } else if (itemId == R.id.action_5years) {
            targetDateType = TargetDateType.TD_5YEAR;

        }
        addiction.setTargetDateType(targetDateType);
        addiction.setTargetDate(targetDateType.getTargetDateFromThatTime(addiction.getLastDateOfQuit()));
        addictionItemViewModel.updateAddiction(addiction);
        return true;
    }


    private void handleDateChanged(CalendarDay date, AddictionWithRelapse data){
        if (date.isInRange(Time.getDateCalendarDayFormat(data.addiction.getFirstDateOfQuit()),CalendarDay.today())){
            List<Relapse> relapsesForThatDay = data.getRelapseListForThatDay(date);
            if (relapsesForThatDay.size() > 0) {
                RelapseBottomSheet.newInstance(relapsesForThatDay).show(getParentFragmentManager(), "relapse-fragment");
            } else {
                if (Time.compare(date, data.addiction.getFirstDateOfQuit()) > 0)
                    addNewEntry(data.addiction.getId(), Time.fromCalenderDayToLong(date));
            }
        }
    }

    private void setupChronometer(long  lastDate){
        binding.chronometer.setOnChronometerTickListener(cArg -> cArg.setText(Time.getAchievedTime(lastDate)));
        binding.chronometer.setBase(SystemClock.elapsedRealtime());
        binding.chronometer.start();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}