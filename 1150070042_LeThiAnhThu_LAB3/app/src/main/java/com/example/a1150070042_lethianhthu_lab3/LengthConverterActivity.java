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

public class LengthConverterActivity extends AppCompatActivity {

    private EditText txtNumber;
    private Spinner spnUnits;
    private TextView[] lblResults;

    private final String[] units = {
            "Kilometre", "Hectometre", "Metre", "Dặm (Mile)", "Yard", "Foot", "Inch"
    };
    private final double[][] ratio = {
            {  1.0,     10.0,    1000.0,   0.621371, 1093.61,   3280.84,   39370.1  },
            {  0.1,     1.0,     100.0,    0.062137, 109.361,   328.084,   3937.01  },
            {  0.001,   0.01,    1.0,      0.00062137, 1.09361,   3.28084,   39.3701  },
            {  1.60934, 16.0934, 1609.34,  1.0,      1760.0,    5280.0,    63360.0  },
            {  0.0009144, 0.009144, 0.9144, 0.000568, 1.0,       3.0,       36.0     },
            {  0.0003048, 0.003048, 0.3048, 0.000189, 0.333333,  1.0,       12.0     },
            {  0.0000254, 0.000254, 0.0254, 0.00001578, 0.027778,  0.083333,  1.0      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_converter);

        txtNumber = findViewById(R.id.txtNumber);
        spnUnits = findViewById(R.id.spnUnit);

        lblResults = new TextView[units.length];
        lblResults[0] = findViewById(R.id.lblKm);
        lblResults[1] = findViewById(R.id.lblHm);
        lblResults[2] = findViewById(R.id.lblM);
        lblResults[3] = findViewById(R.id.lblMile);
        lblResults[4] = findViewById(R.id.lblYard);
        lblResults[5] = findViewById(R.id.lblFoot);
        lblResults[6] = findViewById(R.id.lblInch);
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
                convertLength();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        txtNumber.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertLength();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        convertLength();
    }
    private void convertLength() {
        String inputStr = txtNumber.getText().toString().trim().replace(',', '.');

        if (inputStr.isEmpty() || inputStr.equals(".")) {
            for (TextView lbl : lblResults) {
                if (lbl != null) lbl.setText("0.00");
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
            String formattedResult = String.format(Locale.US, "%,.5f", result);

            lblResults[toUnitIndex].setText(formattedResult);
        }
    }
}
