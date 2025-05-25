package com.example.expensetracker;

import java.text.SimpleDateFormat;
import java.util.*;

public class ExpenseUtils {

    public static List<Expense> filterByDay(List<Expense> list, String day) {
        List<Expense> filtered = new ArrayList<>();
        for (Expense e : list) {
            if (e.getDate().equals(day)) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    public static List<Expense> filterByWeek(List<Expense> list, String start, String end) {
        List<Expense> filtered = new ArrayList<>();
        for (Expense e : list) {
            String date = e.getDate();
            if (date.compareTo(start) >= 0 && date.compareTo(end) <= 0) {
                filtered.add(e);
            }
        }
        return filtered;
    }

    public static List<Expense> filterByMonth(List<Expense> list, String monthPrefix) {
        List<Expense> filtered = new ArrayList<>();
        for (Expense e : list) {
            if (e.getDate().startsWith(monthPrefix)) {
                filtered.add(e);
            }
        }
        return filtered;
    }
}

