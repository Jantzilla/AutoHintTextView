package com.creativesource.autohinttextview;

import android.annotation.TargetApi;
import android.app.Activity;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoHintTextView extends LinearLayout {

    Matcher matcher;
    Pattern pattern;
    Paint paint;
    Rect rect;
    int entryEnd, textSize, textColor;
    private EditText hintEditText, entryEditText;
    private ArrayList<String> suggestions;
    private String textHint;
    private boolean caseSensitive;

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

        entryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    entryEditText.setText(hintEditText.getText().toString());
                    entryEditText.setSelection(entryEditText.getText().length());
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

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
        CharSequence[] entries = ta.getTextArray(R.styleable.AutoHintTextView_android_entries);
        if (entries != null) {
            suggestions = new ArrayList<>();
            ArrayList<CharSequence> arrayList = new ArrayList<>(Arrays.asList(entries));
            for (CharSequence item : arrayList) {
                suggestions.add(String.valueOf(item));
            }
        }
        textHint = ta.hasValue(R.styleable.AutoHintTextView_hint) ? ta.getString(R.styleable.AutoHintTextView_hint) : "hint";
        caseSensitive = ta.getBoolean(R.styleable.AutoHintTextView_caseSensitive, true);
        textColor = ta.getColor(R.styleable.AutoHintTextView_android_textColor, Color.BLACK);
        textSize = ta.getDimensionPixelSize(R.styleable.AutoHintTextView_android_textSize, (int) (18 * getResources().getDisplayMetrics().scaledDensity));
        entryEditText.setHint(textHint);
        entryEditText.setTextColor(textColor);
        entryEditText.setTextSize(textSize / getResources().getDisplayMetrics().scaledDensity);
        hintEditText.setTextSize(textSize / getResources().getDisplayMetrics().scaledDensity);
        ta.recycle();

    }

    private String searchStrings(String s) {
        if(!suggestions.isEmpty()) {
            pattern = caseSensitive ? Pattern.compile("^" + s) : Pattern.compile("^" + s, Pattern.CASE_INSENSITIVE);
            if (!s.equals("")) {
                for (String i : suggestions) {
                    matcher = pattern.matcher(i);
                    if (matcher.find()) {
                        return caseSensitive ? i : s + i.substring(s.length());
                    }
                }
            }
        }
        return "";
    }

    public void setSuggestions(ArrayList<String> suggestions) {
        this.suggestions.addAll(suggestions);
    }

    public void setSuggestions(int resId) {
        suggestions = new ArrayList<>(Arrays.asList(getResources().getStringArray(resId)));
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public void addHintChangedListener(TextWatcher textWatcher) {
        hintEditText.addTextChangedListener(textWatcher);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        entryEditText.addTextChangedListener(textWatcher);
    }
}
