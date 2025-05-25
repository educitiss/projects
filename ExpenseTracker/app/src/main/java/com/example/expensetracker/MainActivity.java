package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.expensetracker.fragments.DayFragment;
import com.example.expensetracker.fragments.WeekFragment;
import com.example.expensetracker.fragments.MonthFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Expense> expenseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add Expense FAB
        FloatingActionButton fab = findViewById(R.id.fabAddExpense);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        // Default fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new DayFragment())
                .commit();

        // Bottom nav
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);
        nav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_day:
                    selectedFragment = new DayFragment();
                    break;
                case R.id.nav_week:
                    selectedFragment = new WeekFragment();
                    break;
                case R.id.nav_month:
                    selectedFragment = new MonthFragment();
                    break;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}
