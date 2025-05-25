package com.example.expensetracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpenseActivity extends Activity {

    EditText edtAmount, edtDescription;
    Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        edtAmount = findViewById(R.id.edtAmount);
        edtDescription = findViewById(R.id.edtDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        // Setup Spinner with predefined categories
        String[] categories = {"Groceries", "House", "Transport", "Entertainment", "Pets"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Save button logic
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> {
            try {
                double amount = Double.parseDouble(edtAmount.getText().toString().trim());
                String description = edtDescription.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                Expense expense = new Expense(amount, description, category, date);
                MainActivity.expenseList.add(expense);
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "Please fill all fields correctly.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
