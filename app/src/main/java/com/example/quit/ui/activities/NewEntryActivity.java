package com.example.quit.ui.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.quit.R;
import com.example.quit.databinding.ActivityNewEntryBinding;
import com.example.quit.models.Relapse;
import com.example.quit.utilities.Time;
import com.example.quit.viewmodels.EntryViewModel;

import java.util.Calendar;

public class NewEntryActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    public static final String ACTIVITY_USAGE_KEY = "usage";
    public static final String ADDICTION_ID_KEY = "addiction_id";
    public static final String RELAPSE_DATE_KEY = "relapse_date";

    public static final String ACTIVITY_USAGE_EDIT_VALUE = "usage_edit";
    public static final String ACTIVITY_USAGE_INSERT_VALUE = "usage_insert";

    private static final String TAG = NewEntryActivity.class.getSimpleName();

    private EntryViewModel entryViewModel;
    ActivityNewEntryBinding binding;
    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        String usage = getIntent().getStringExtra(ACTIVITY_USAGE_KEY);
        if (ACTIVITY_USAGE_EDIT_VALUE.contentEquals(usage)) {
            binding.toolBar.setTitle("Edit Entry");

            entryViewModel.getRelapseToEditByDate(getIntent().getLongExtra(RELAPSE_DATE_KEY, 0)).observe(this, (relapse) -> {
               if (relapse != null){
                binding.relapseTimeTv.setText(Time.formatTimeToHM(relapse.getRelapseDate()));
                binding.commentEt.setText(relapse.getComment());
                calendar.setTimeInMillis(relapse.getRelapseDate());}
            });

        } else {
            binding.relapseTimeTv.setText(Time.getNowTime());
        }


        binding.relapseTimeTv.setOnClickListener((btn) -> showTimePicker());

        binding.toolBar.setNavigationOnClickListener((closeBtn) -> finish());

        binding.toolBar.setOnMenuItemClickListener(this);


    }


    private Relapse getRelapseDataFromViews() {
        int id = getIntent().getIntExtra(ADDICTION_ID_KEY, -1);
        long relapseTime = getIntent().getLongExtra(RELAPSE_DATE_KEY, 0);
        if (relapseTime > 0) {
            Calendar helper = Calendar.getInstance();
            helper.setTimeInMillis(relapseTime);
            calendar.set(helper.get(Calendar.YEAR), helper.get(Calendar.MONTH), helper.get(Calendar.DAY_OF_MONTH));
        }

        if (calendar.getTimeInMillis() > System.currentTimeMillis()){
            Toast.makeText(this, "Can't set time in future !!",Toast.LENGTH_SHORT).show();
         return null;
        }
        return new Relapse(calendar.getTimeInMillis(), binding.commentEt.getText().toString(), id);
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(NewEntryActivity.this, (view1, hourOfDay, minute) -> {
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            calendar = c;
            binding.relapseTimeTv.setText(Time.formatCalenderToHM(c));
        }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            String usage = getIntent().getStringExtra(ACTIVITY_USAGE_KEY);
            if (ACTIVITY_USAGE_EDIT_VALUE.contentEquals(usage))
                entryViewModel.updateRelapseDateAndComment(calendar.getTimeInMillis(), binding.commentEt.getText().toString());
            else if (ACTIVITY_USAGE_INSERT_VALUE.contentEquals(usage))
                entryViewModel.insertEntry(getRelapseDataFromViews());
            finish();
            return true;
        }
        return false;
    }
}