package com.example.expensetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Expense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_month, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        PieChart pieChart = root.findViewById(R.id.pieChart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String month = new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
        List<Expense> filtered = ExpenseUtils.filterByMonth(MainActivity.expenseList, month);
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

        PieDataSet dataSet = new PieDataSet(entries, "Monthly Expenses");
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

