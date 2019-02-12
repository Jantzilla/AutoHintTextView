package com.creativesourceapps.android.app;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.creativesourceapps.android.autohinttextview.AutoHintTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout root;
    AutoHintTextView autoHintTextView;
    ArrayList<String> suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoHintTextView = findViewById(R.id.tv_auto_hint);
        root = findViewById(R.id.cl_root);

        suggestions = new ArrayList<>();

        suggestions.add("Blue");
        suggestions.add("Cyan");
        suggestions.add("Green");
        suggestions.add("Magenta");
        suggestions.add("Red");
        suggestions.add("Gray");
        suggestions.add("Yellow");
        suggestions.add("White");

        autoHintTextView.setSuggestions(suggestions);

        autoHintTextView.addHintChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (String.valueOf(s)) {
                    case "Blue":
                        root.setBackgroundColor(Color.BLUE);
                        break;
                    case "Green":
                        root.setBackgroundColor(Color.GREEN);
                        break;
                    case "Red":
                        root.setBackgroundColor(Color.RED);
                        break;
                    case "Cyan":
                        root.setBackgroundColor(Color.CYAN);
                        break;
                    case "Gray":
                        root.setBackgroundColor(Color.GRAY);
                        break;
                    case "Yellow":
                        root.setBackgroundColor(Color.YELLOW);
                        break;
                    case "Magenta":
                        root.setBackgroundColor(Color.MAGENTA);
                        break;
                    default:
                        root.setBackgroundColor(Color.WHITE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}