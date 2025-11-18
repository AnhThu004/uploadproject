package com.example.a1150070042_lethianhthu_lab3;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText txtNumber;
    private Spinner spnUnits;
    private TextView[] lblResults;
    private final String[] units = {
            "USD", "EUR", "GBP", "INR", "AUD",
            "CAD", "ZAR", "NZD", "JPY", "VNĐ"
    };
    private final double[][] ratio = {
            {1.00000, 0.80518, 0.64070, 63.3318, 1.21828, 1.16236, 11.7129, 1.2931, 118.337, 21385.7},
            {1.24172, 1.00000, 0.79575, 78.6084, 1.51266, 1.44314, 14.5371, 1.60576, 146.927, 26561.8},
            {1.56044, 1.25667, 1.00000, 98.7848, 1.90091, 1.81355, 18.2683, 2.01791, 184.638, 33374.9},
            {0.01580, 0.01272, 0.01012, 1.00000, 0.01924, 0.01836, 0.18493, 0.02043, 1.86910, 337.811},
            {0.82114, 0.66119, 0.52620, 52.0860, 1.00000, 0.95416, 9.61148, 1.06158, 97.1120, 17567.9},
            {0.86059, 0.69296, 0.55148, 54.5885, 1.04804, 1.00000, 10.0732, 1.11258, 101.777, 18401.7},
            {0.08541, 0.06877, 0.05473, 5.40852, 0.10398, 0.09924, 1.00000, 0.11037, 10.0996, 1825.87},
            {0.77402, 0.62319, 0.49597, 49.0031, 0.94215, 0.89951, 9.06754, 1.00000, 91.5139, 16552.1},
            {0.00846, 0.00681, 0.00542, 0.53547, 0.01030, 0.00983, 0.09908, 0.01093, 1.00000, 180.837},
            {0.00005, 0.00004, 0.00003, 0.00296, 0.00006, 0.00005, 0.00055, 0.00006, 0.00553, 1.00000}
    };

    private static final int VND_INDEX = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNumber = findViewById(R.id.txtNumber);
        spnUnits = findViewById(R.id.spnUnit);
        lblResults = new TextView[units.length];
        lblResults[0] = findViewById(R.id.lblUsd);
        lblResults[1] = findViewById(R.id.lblEur);
        lblResults[2] = findViewById(R.id.lblGbp);
        lblResults[3] = findViewById(R.id.lblInr);
        lblResults[4] = findViewById(R.id.lblAud);
        lblResults[5] = findViewById(R.id.lblCad);
        lblResults[6] = findViewById(R.id.lblZar);
        lblResults[7] = findViewById(R.id.lblNzd);
        lblResults[8] = findViewById(R.id.lblJpy);
        lblResults[9] = findViewById(R.id.lblVnd);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUnits.setAdapter(adapter);
        spnUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convertCurrency();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertCurrency();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        convertCurrency();
    }
    private void convertCurrency() {
        String inputStr = txtNumber.getText().toString().trim();
        inputStr = inputStr.replace(',', '.');
        if (inputStr.isEmpty() || inputStr.equals(".")) {
            for (int i = 0; i < units.length; i++) {
                if (lblResults[i] != null) {
                    lblResults[i].setText("0.00");
                }
            }
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(inputStr);
        } catch (NumberFormatException e) {
            return;
        }

        int fromUnitIndex = spnUnits.getSelectedItemPosition();

        for (int toUnitIndex = 0; toUnitIndex < units.length; toUnitIndex++) {

            if (lblResults[toUnitIndex] == null) {
                continue;
            }
            double exchangeRate = ratio[fromUnitIndex][toUnitIndex];
            double result = amount * exchangeRate;
            String formattedResult;

            if (toUnitIndex == VND_INDEX) {
                formattedResult = String.format(Locale.US, "%,.0f", result);
            } else {
                formattedResult = String.format(Locale.US, "%,.5f", result);
            }
            lblResults[toUnitIndex].setText(formattedResult);
        }
    }
}