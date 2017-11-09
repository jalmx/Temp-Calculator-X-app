package xizuth.com.tempcalcux;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RadioButton;
import android.widget.TextView;

import xizuth.com.tempcalcux.lib.Temperature;

public class MainActivity extends AppCompatActivity {

    private RadioButton radioCelsius;
    private TextView valueInput;
    private RadioButton radioFahrenheit;
    private TextView degreesSelect;
    View.OnClickListener radioAction = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
        valueInput = (TextView) findViewById(R.id.input_value);
        radioCelsius = (RadioButton) findViewById(R.id.radio_celsius);
        radioFahrenheit = (RadioButton) findViewById(R.id.radio_fahrenheit);
        degreesSelect = (TextView) findViewById(R.id.text_degrees);
        valueInput.setOnEditorActionListener(onEditorActionListener);
    }

    private void printResult() {
        TextView result = (TextView) findViewById(R.id.result_degrees);

        double degrees = calculate();
        String value = String.format("%.2f%s", degrees, degreesSelectedResult());
        result.setText(value);
    }

    private double calculate() {
        double value = 0.0;

        if (radioCelsius.isSelected()) {
            value = Temperature.celsiusToFahrenheit(getValueUI());
        } else {
            value = Temperature.fahrenheitToCelsius(getValueUI());
        }
        return value;
    }

    private String degreesSelectedResult() {
        if (radioCelsius.isSelected()) {
            return getString(R.string.degrees_celsius);
        } else {
            return getString(R.string.degrees_fahrenheit);
        }
    }

    private void degreesSelectOption() {
        if (radioCelsius.isSelected()) {
            degreesSelect.setText(getString(R.string.degrees_fahrenheit));
        } else {
            degreesSelect.setText(getString(R.string.degrees_celsius));
        }
    }
}
