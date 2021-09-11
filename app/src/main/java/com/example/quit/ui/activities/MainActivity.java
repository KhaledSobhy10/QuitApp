package com.example.quit.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quit.R;
import com.example.quit.adapters.MainAdapter;
import com.example.quit.databinding.ActivityMainBinding;
import com.example.quit.models.Addiction;
import com.example.quit.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnAddictionItemClickListener {
    public static final String EXTRA_CLICKED_ADDICTION_ID = "addiction_id";
    private ActivityMainBinding binding;
    private MainAdapter mainAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getAddictionListLiveData().observe(this, addictions -> {
            mainAdapter.updateData(addictions);
            setupView();
        });


        binding.fab.setOnClickListener((view) -> startActivity(new Intent(this, AddAddictionActivity.class)));

        binding.toolBar.setOnMenuItemClickListener(this::onOptionsItemSelected);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete_all_data_action) {
            mainViewModel.deleteAllData();
            return true;
        } else if (itemId == R.id.setting) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);
        mainAdapter = new MainAdapter(null, this);
        binding.recyclerView.setAdapter(mainAdapter);
    }

    private void setupView() {
        if (mainAdapter.getItemCount() != 0) {
            binding.commitQuitLabel.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyRidOfYourAddictionLabel.setVisibility(View.GONE);
            binding.emptyLisImage.setVisibility(View.GONE);
        } else {
            binding.commitQuitLabel.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyRidOfYourAddictionLabel.setVisibility(View.VISIBLE);
            binding.emptyLisImage.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void addictionItemClicked(int addictionId) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(EXTRA_CLICKED_ADDICTION_ID, addictionId);
        startActivity(intent);
    }

    @Override
    public void deleteActionItemClicked(int addictionId) {
        mainViewModel.deleteAddictionById(addictionId);
    }

    @Override
    public void updateActionItemClicked(Addiction addiction) {
        mainViewModel.updateAddiction(addiction);
    }
}