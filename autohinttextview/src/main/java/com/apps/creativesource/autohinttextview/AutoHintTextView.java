package com.apps.creativesource.autohinttextview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class AutoHintTextView extends LinearLayout {

    Paint paint;
    Rect rect;
    int squareColor, entryEnd;
    private EditText hintEditText, entryEditText;
    private ArrayList<String> suggestions;

    public AutoHintTextView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoHintTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoHintTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoHintTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet set) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new Rect();
        suggestions = new ArrayList<>();

        LayoutInflater.from(context).inflate(R.layout.auto_hint_text_view, this, true);

        hintEditText = findViewById(R.id.et_hint);
        entryEditText = findViewById(R.id.et_entry);

        entryEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                entryEnd = count;

                String resultString = searchStrings(String.valueOf(s));

                Spannable wordToSpan = new SpannableString(resultString);

                if(wordToSpan.length() > 0) {
                    wordToSpan.setSpan(new ForegroundColorSpan(Color.GRAY), entryEnd, wordToSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    hintEditText.setText(wordToSpan);
                } if(wordToSpan.length() == 0)
                    hintEditText.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(set == null)
            return;

        TypedArray ta = context.obtainStyledAttributes(set, R.styleable.AutoHintTextView);
        squareColor = ta.getColor(R.styleable.AutoHintTextView_square_color, Color.WHITE);
        paint.setColor(squareColor);
        ta.recycle();

    }

    private String searchStrings(String s) {
        if(!s.equals("")) {
            for (String i : suggestions) {
                if (i.contains(s)) {
                    return i;
                }
            }
        }
        return "";
    }

    public void setSuggestions(ArrayList<String> suggestions) {
        this.suggestions.addAll(suggestions);
    }
}
