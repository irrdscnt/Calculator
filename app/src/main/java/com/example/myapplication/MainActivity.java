package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText displayEditText;
    private double operand1 = Double.NaN;
    private String pendingOperation = "=";
    private boolean isNewNumber = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayEditText = findViewById(R.id.displayEditText);
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEditText.setText("");
                operand1 = Double.NaN;
                pendingOperation = "=";
                isNewNumber = true;
            }
        });
        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (isNewNumber) {
                    displayEditText.setText(button.getText().toString());
                    isNewNumber = false;
                } else {
                    displayEditText.append(button.getText().toString());
                }
            }
        };


        findViewById(R.id.buttonSquare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = displayEditText.getText().toString();
                if (!value.isEmpty()) {
                    double number = Double.parseDouble(value);
                    operand1 = number;
                    displayEditText.setText("");
                    pendingOperation = "^";
                }
            }
        });

        int[] numberButtonIds = {
                R.id.button0,
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9,
                R.id.buttonDot
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberListener);
        }

        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = ((Button) v).getText().toString();
                String value = displayEditText.getText().toString();

                if (!value.isEmpty()) {
                    performOperation(value);
                }

                if (!op.equals("=")) {
                    pendingOperation = op;
                }

                isNewNumber = true;
            }
        };

        int[] operationButtonIds = {
                R.id.buttonPlus,
                R.id.buttonMinus,
                R.id.buttonMultiply,
                R.id.buttonDivide,
                R.id.buttonEquals,
                R.id.buttonPercent,
                R.id.buttonSqrt,
                R.id.buttonSquare
        };

        for (int id : operationButtonIds) {
            findViewById(id).setOnClickListener(operationListener);
        }
    }

    private void performOperation(String value) {
        double operand2 = Double.parseDouble(value);

        switch (pendingOperation) {
            case "=":
                operand1 = operand2;
                break;
            case "+":
                operand1 += operand2;
                break;
            case "-":
                operand1 -= operand2;
                break;
            case "*":
                operand1 *= operand2;
                break;
            case "/":
                if (operand2 != 0)
                    operand1 /= operand2;
                break;
            case "^":
                operand1 = Math.pow(operand1, operand2);
                break;
            case "√":
                if (operand1 >= 0) {
                    operand1 = Math.sqrt(operand1);
                    displayEditText.setText(String.valueOf(operand1));
                } else {
                    displayEditText.setText("Error");
                }
                break;
            case "%":
                operand1 = operand1 * 0.01;
                displayEditText.setText(String.valueOf(operand1));
                break;
        }
        displayEditText.setText(String.valueOf(operand1));
    }
}
