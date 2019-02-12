package com.creativesourceapps.android.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creativesourceapps.android.app.R;
import com.creativesourceapps.android.autohinttextview.AutoHintTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoHintTextView autoHintTextView;
    ArrayList<String> suggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoHintTextView = findViewById(R.id.tv_auto_hint);

        suggestions = new ArrayList<>();

        suggestions.add("Blue");
        suggestions.add("Green");
        suggestions.add("Pink");
        suggestions.add("Red");
        suggestions.add("Orange");
        suggestions.add("Black");
        suggestions.add("White");
        suggestions.add("Purple");
        suggestions.add("Yellow");

        autoHintTextView.setSuggestions(suggestions);
    }
}