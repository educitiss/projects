package com.example.expensetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Expense;
import com.example.expensetracker.ExpenseAdapter;
import com.example.expensetracker.ExpenseUtils;
import com.example.expensetracker.MainActivity;
import com.example.expensetracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeekFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_week, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        PieChart pieChart = root.findViewById(R.id.pieChart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get current week's start and end dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        String start = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_WEEK, 6);
        String end = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

        List<Expense> filtered = ExpenseUtils.filterByWeek(MainActivity.expenseList, start, end);
        recyclerView.setAdapter(new ExpenseAdapter(filtered));

        loadPieChart(pieChart, filtered);

        return root;
    }

    private void loadPieChart(PieChart pieChart, List<Expense> expenses) {
        List<PieEntry> entries = new ArrayList<>();

        Map<String, Float> categorySums = new HashMap<>();
        for (Expense e : expenses) {
            float amount = (float) e.getAmount();
            categorySums.put(e.getCategory(), categorySums.getOrDefault(e.getCategory(), 0f) + amount);
        }

        for (Map.Entry<String, Float> entry : categorySums.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        if (entries.isEmpty()) {
            pieChart.clear();
            pieChart.setNoDataText("No chart data available");
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Weekly Expenses");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.invalidate(); // refresh
    }
}
