package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private EditText displayEditText;
    private BigDecimal operand1 = BigDecimal.ZERO;
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
                operand1 = BigDecimal.ZERO;
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
                    BigDecimal number = new BigDecimal(value);
                    operand1 = number.multiply(number); // Возвести в квадрат
                    displayEditText.setText(operand1.toString());
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
        BigDecimal operand2 = new BigDecimal(value);

        switch (pendingOperation) {
            case "=":
                operand1 = operand2;
                break;
            case "+":
                operand1 = operand1.add(operand2);
                break;
            case "-":
                operand1 = operand1.subtract(operand2);
                break;
            case "*":
                operand1 = operand1.multiply(operand2);
                break;
            case "/":
                if (!operand2.equals(BigDecimal.ZERO)) {
                    operand1 = operand1.divide(operand2, 5, BigDecimal.ROUND_HALF_UP);
                } else {
                    Toast.makeText(this, "Ошибка: деление на ноль", Toast.LENGTH_SHORT).show();
                    return;                }
                break;
            case "^":
                operand1 = operand1.pow(operand2.intValue());
                break;
            case "√":
                if (operand1.compareTo(BigDecimal.ZERO) >= 0) {
                    operand1 = BigDecimal.valueOf(Math.sqrt(operand1.doubleValue()));
                } else {
                    displayEditText.setText("Error");
                    return;
                }
                break;
            case "%":
                operand1 = operand1.multiply(BigDecimal.valueOf(0.01));
                break;
        }
        displayEditText.setText(operand1.toString());
    }
}
