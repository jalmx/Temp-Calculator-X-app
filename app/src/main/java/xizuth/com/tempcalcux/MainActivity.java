package xizuth.com.tempcalcux;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import xizuth.com.tempcalcux.lib.Temperature;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioCelsius;
    private EditText valueInput;
    private RadioButton radioFahrenheit;
    private TextView degreesSelect;

    View.OnClickListener radioAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.radio_celsius:
                    radioFahrenheit.setChecked(false);
                    break;
                case R.id.radio_fahrenheit:
                    radioCelsius.setChecked(false);
                    break;
            }
            degreesSelectOption();
            printResult();
        }
    };
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            printResult();
        }
    };

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_NEXT) {
                printResult();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loadListener();
    }

    private void loadListener(){
        radioCelsius.setOnClickListener(radioAction);
        radioFahrenheit.setOnClickListener(radioAction);
        valueInput.setOnEditorActionListener(onEditorActionListener);
        valueInput.addTextChangedListener(textWatcher);
    }

    private double getValueUI() {
        double value = 0.0;
        valueInput.setError(null);
        String valueString = valueInput.getText().toString();
        try {
            value = Double.parseDouble(valueString);
        } catch (Exception e) {
            setError();
        }
        return value;
    }

    private void setError() {
        valueInput.setError(getText(R.string.error_digit));
    }

    private void initViews() {
        valueInput = findViewById(R.id.input_value);
        radioCelsius =  findViewById(R.id.radio_celsius);
        radioFahrenheit = findViewById(R.id.radio_fahrenheit);
        degreesSelect = findViewById(R.id.text_degrees);
    }

    private void printResult() {
        TextView result = (TextView) findViewById(R.id.result_degrees);

        double degrees = calculate();
        String value = String.format("%.2f%s", degrees, degreesSelectedResult());
        result.setText(value);
    }

    private double calculate() {
        double value = 0.0;

        if (radioCelsius.isChecked()) {
            value = Temperature.fahrenheitToCelsius(getValueUI());
        } else {
            value = Temperature.celsiusToFahrenheit(getValueUI());
        }
        return value;
    }

    private String degreesSelectedResult() {
        if (radioCelsius.isChecked()) {
            return getString(R.string.degrees_celsius);
        } else {
            return getString(R.string.degrees_fahrenheit);
        }
    }

    private void degreesSelectOption() {
        if (radioCelsius.isChecked()) {
            degreesSelect.setText(getString(R.string.degrees_fahrenheit));
        } else {
            degreesSelect.setText(getString(R.string.degrees_celsius));
        }
    }
}
