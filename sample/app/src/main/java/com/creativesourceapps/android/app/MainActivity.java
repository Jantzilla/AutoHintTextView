package com.creativesourceapps.android.app;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.creativesourceapps.android.autohinttextview.AutoHintTextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout root;
    AutoHintTextView autoHintTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoHintTextView = findViewById(R.id.tv_auto_hint);
        root = findViewById(R.id.cl_root);

        autoHintTextView.addHintChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (String.valueOf(s).equalsIgnoreCase("Blue")) {
                    root.setBackgroundColor(Color.BLUE);
                } else if (String.valueOf(s).equalsIgnoreCase("Green")) {
                    root.setBackgroundColor(Color.GREEN);
                } else if (String.valueOf(s).equalsIgnoreCase("Red")) {
                    root.setBackgroundColor(Color.RED);
                } else if (String.valueOf(s).equalsIgnoreCase("Cyan")) {
                    root.setBackgroundColor(Color.CYAN);
                } else if (String.valueOf(s).equalsIgnoreCase("Gray")) {
                    root.setBackgroundColor(Color.GRAY);
                } else if (String.valueOf(s).equalsIgnoreCase("Yellow")) {
                    root.setBackgroundColor(Color.YELLOW);
                } else if (String.valueOf(s).equalsIgnoreCase("Magenta")) {
                    root.setBackgroundColor(Color.MAGENTA);
                } else {
                    root.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}