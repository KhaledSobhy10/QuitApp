package com.example.quit.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quit.adapters.ViewPagerAdapter;
import com.example.quit.databinding.ActivityAddAddictionBinding;
import com.example.quit.models.Addiction;
import com.example.quit.models.AddictionType;
import com.example.quit.database.AppDatabase;
import com.example.quit.models.TargetDateType;
import com.example.quit.repo.AppRepo;
import com.example.quit.ui.fragments.SelectDateFragment;
import com.example.quit.ui.fragments.SelectTypeFragment;
import com.example.quit.ui.fragments.SetNameAndIconFragment;

public class AddAddictionActivity extends AppCompatActivity {
    private ActivityAddAddictionBinding binding;
    public Addiction enteredAddiction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAddictionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        enteredAddiction = new Addiction();

        ViewPagerAdapter myViewPagerAdapter = new ViewPagerAdapter(this);

        binding.createAddictionVp.setAdapter(myViewPagerAdapter);
        binding.createAddictionVp.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        binding.createAddictionVp.setUserInputEnabled(false);
        binding.circleIndicator.setViewPager(binding.createAddictionVp);
        binding.circleIndicator.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        binding.nextBtn.setOnClickListener((btn) ->
        {
            Fragment fragment = getSupportFragmentManager().getFragments().get(binding.createAddictionVp.getCurrentItem());
            if (canGoNext(fragment)) {
                int currentItem = binding.createAddictionVp.getCurrentItem();
                if (currentItem == 1){
                    binding.nextBtn.setVisibility(View.GONE);
                    binding.completeBtn.setVisibility(View.VISIBLE);

                }
                binding.createAddictionVp.setCurrentItem(currentItem + 1);
                binding.backBtn.setVisibility(View.VISIBLE);
            }
        });

        binding.backBtn.setOnClickListener((btn) ->
        {
            if (binding.createAddictionVp.getCurrentItem() == 1)
                binding.backBtn.setVisibility(View.INVISIBLE);
            else if(binding.createAddictionVp.getCurrentItem() == 2) {
                binding.nextBtn.setVisibility(View.VISIBLE);
                binding.backBtn.setVisibility(View.VISIBLE);
                binding.completeBtn.setVisibility(View.GONE);
            }else {
                binding.backBtn.setVisibility(View.VISIBLE);
            }
            binding.createAddictionVp.setCurrentItem(binding.createAddictionVp.getCurrentItem() - 1);

        });

        binding.completeBtn.setOnClickListener(v -> {
            Fragment fragment = getSupportFragmentManager().getFragments().get(binding.createAddictionVp.getCurrentItem());
            if (canGoNext(fragment)){
                AppRepo.getInstance(getApplication()).insertAddiction(enteredAddiction);
            finish();}
        });

    }

    public boolean canGoNext(Fragment currentFragment) {

        if (currentFragment instanceof SetNameAndIconFragment) {
            String addictionName = ((SetNameAndIconFragment) currentFragment).getAddictionName();
            if (addictionName != null) {
                enteredAddiction.setAddictionName(addictionName);
                enteredAddiction.setIconId(((SetNameAndIconFragment) currentFragment).getSelectedIconID());
                return true;
            } else
                return false;

        }
        else if (currentFragment instanceof SelectTypeFragment) {
            AddictionType addictionType = ((SelectTypeFragment) currentFragment).getSelectedAddictionType();
            enteredAddiction.setAddictionType(addictionType);
            switch (addictionType) {
                case MONEY_WASTING:
                    double wastedMoney = ((SelectTypeFragment) currentFragment).getWastedMoney();
                    if (wastedMoney > 0) {
                        enteredAddiction.setMoneyWasting(wastedMoney);
                        return true;
                     } else return false;
                case TIME_WASTING:
                    double wastedTime = ((SelectTypeFragment) currentFragment).getWastedTime();
                    if (wastedTime > 0) {
                        enteredAddiction.setMoneyWasting(wastedTime);
                        return true;
                    } else return false;

                case EVENT: {
                    enteredAddiction.setAddictionType(addictionType);
                    return true;
                }
            }
            return false;
        }
        else if (currentFragment instanceof SelectDateFragment){
            long lastTime = ((SelectDateFragment) currentFragment).getLastTime();
            enteredAddiction.setLastDateOfQuit(lastTime);
            enteredAddiction.setFirstDateOfQuit(lastTime);
            enteredAddiction.setTargetDateType(TargetDateType.TD_24HOUR);
            enteredAddiction.setTargetDate(enteredAddiction.getTargetDateType().getTargetDateFromThatTime(lastTime));
            return true;
        }
        return false;
    }
}
