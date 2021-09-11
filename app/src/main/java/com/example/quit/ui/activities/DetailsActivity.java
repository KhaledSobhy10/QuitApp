package com.example.quit.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.quit.R;
import com.example.quit.databinding.ActivityDetailsBinding;
import com.example.quit.viewmodels.AddictionItemViewModel;
import com.example.quit.ui.fragments.MotivationFragment;
import com.example.quit.ui.fragments.OverviewFragment;
import com.example.quit.ui.fragments.StatisticFragment;
import com.example.quit.ui.fragments.TrophiesFragment;
import com.google.android.material.navigation.NavigationBarView;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    private final StatisticFragment statisticFragment  = new StatisticFragment();
    private final OverviewFragment overviewFragment = new OverviewFragment();
    private final MotivationFragment motivationFragment  = new MotivationFragment();
    private final TrophiesFragment trophiesFragment = new TrophiesFragment();

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        Intent intent = getIntent();
        if (intent != null) {
            int addictionId = intent.getIntExtra(MainActivity.EXTRA_CLICKED_ADDICTION_ID, -1);
            AddictionItemViewModel addictionItemViewModel = new ViewModelProvider(this).get(AddictionItemViewModel.class);

            addictionItemViewModel.getAddictionWithRelapseLiveData(addictionId).observe(this, data -> {
                binding.detailsToolbar.setTitle(data.addiction.getAddictionName());
            });

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, overviewFragment)
                    .commit();
        }

        binding.detailsToolbar.setNavigationOnClickListener(this);

        binding.bottomNavigationView.setOnItemSelectedListener(this);






    }

    private void switchToThatFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.bottom_nav_overview) {
            switchToThatFragment(overviewFragment);
            return true;
        } else if (itemId == R.id.bottom_nav_motivation) {
            switchToThatFragment(motivationFragment);
            return true;
        } else if (itemId == R.id.bottom_nav_statistic) {
            switchToThatFragment(statisticFragment );
            return true;
        } else if (itemId == R.id.bottom_nav_trophies) {
            switchToThatFragment(trophiesFragment);
            return true;
        }
        return false;
    }
}