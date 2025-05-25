package com.example.expensetracker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.*;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class DayFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_day, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PieChart pieChart = root.findViewById(R.id.pieChart);

        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        List<Expense> filtered = ExpenseUtils.filterByDay(MainActivity.expenseList, today);

        recyclerView.setAdapter(new ExpenseAdapter(filtered));
        loadPieChart(pieChart, filtered);

        return root;
    }

    private void loadPieChart(PieChart pieChart, List<Expense> expenses) {
        Map<String, Float> categorySums = new HashMap<>();
        for (Expense e : expenses) {
            float amount = e.getAmount();
            categorySums.put(e.getCategory(), categorySums.getOrDefault(e.getCategory(), 0f) + amount);
        }

        if (categorySums.isEmpty()) {
            pieChart.setNoDataText("No chart data available");
            return;
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Float> entry : categorySums.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses");
        dataSet.setColors(com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS);
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
