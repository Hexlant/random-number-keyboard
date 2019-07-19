package com.hexlant.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorRes;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomNumberKeyboard extends GridLayout {

    private static final int COUNT = 10;
    private static final int DEFAULT_KEY_TEXT_SIZE_SP = 32;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Dimension private int verticalSpacing;
    @Dimension private int horizontalSpacing;
    @DrawableRes private int numberKeyBackground;
    @Dimension private int numberKeyTextSize;
    @ColorRes private int numberKeyTextColor;
    @DrawableRes private int leftAuxBackground;
    @DrawableRes private int rightAuxBackground;
//    @ColorRes private int borderColor;
    private String leftAuxText;
    private String rightAuxText;

    private boolean isRandom;

    private List<TextView> numericKeys;
    private TextView tvLeftAux;
    private TextView tvRightAux;

    private NumberKeyboardListener listener;

    public RandomNumberKeyboard(@NonNull Context context) {
        super(context);
        inflateView();
    }

    public RandomNumberKeyboard(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
        initializeAttributes(attrs);
        inflateView();
    }

    public RandomNumberKeyboard(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeAttributes(attrs);
        inflateView();
    }

    public void setListener(NumberKeyboardListener listener) {
        this.listener = listener;
    }

    public void hideLeftAux() {
        tvLeftAux.setVisibility(GONE);
    }

    public void showLeftAux() {
        tvLeftAux.setVisibility(VISIBLE);
    }

    public void hideRightAux() {
        tvRightAux.setVisibility(GONE);
    }

    public void showRightAux() {
        tvRightAux.setVisibility(VISIBLE);
    }

    public void setBackground(@DrawableRes int background){
        setBackground(ContextCompat.getDrawable(getContext(), background));
    }

    public void setVerticalSpacing(int px){
    }

    public void setHorizontalSpacing(int px){
    }

    public void setNumberKeyBackground(@DrawableRes int background) {
        for (TextView key : numericKeys) {
            key.setBackground(ContextCompat.getDrawable(getContext(), background));
        }
    }

    public void setNumberKeyTextColor(@ColorRes int color) {
        for (TextView key : numericKeys) {
            key.setTextColor(ContextCompat.getColorStateList(getContext(), color));
        }

        tvLeftAux.setTextColor(ContextCompat.getColorStateList(getContext(), color));
        tvRightAux.setTextColor(ContextCompat.getColorStateList(getContext(), color));
    }

    public void setLeftAuxBackground(@DrawableRes int background){
        tvLeftAux.setBackground(ContextCompat.getDrawable(getContext(), background));
    }

    public void setRightAuxBackground(@DrawableRes int background) {
        tvRightAux.setBackground(ContextCompat.getDrawable(getContext(), background));
    }

    public void setLeftAuxText(String text) {
        tvLeftAux.setText(text);
    }

    public void setRightAuxText(String text) {
        tvRightAux.setText(text);
    }

    private void initializeAttributes(AttributeSet attrs) {
        TypedArray array = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.RandomNumberKeyboard, 0, 0);
        try {
            isRandom = array.getBoolean(R.styleable.RandomNumberKeyboard_random, false);

            verticalSpacing = array.getLayoutDimension(R.styleable.RandomNumberKeyboard_verticalSpacing, 0);
            horizontalSpacing = array.getLayoutDimension(R.styleable.RandomNumberKeyboard_horizontalSpacing, 0);

            leftAuxText = array.getString(R.styleable.RandomNumberKeyboard_leftAuxText);
            rightAuxText = array.getString(R.styleable.RandomNumberKeyboard_rightAuxText);

            leftAuxBackground = array.getResourceId(R.styleable.RandomNumberKeyboard_leftAuxBackground,
                    R.drawable.key_aux_bg);

            rightAuxBackground = array.getResourceId(R.styleable.RandomNumberKeyboard_rightAuxBackground,
                    R.drawable.key_aux_bg);

            numberKeyTextColor = array.getResourceId(R.styleable.RandomNumberKeyboard_leftAuxBackground,
                    android.R.color.transparent);
            numberKeyTextColor = array.getResourceId(R.styleable.RandomNumberKeyboard_rightAuxBackground,
                    android.R.color.transparent);

            numberKeyBackground = array.getResourceId(R.styleable.RandomNumberKeyboard_numberKeyBackground,
                    R.drawable.key_bg);
            numberKeyTextSize = array.getDimensionPixelSize(R.styleable.RandomNumberKeyboard_numberKeyTextSize,
                    spToPx(DEFAULT_KEY_TEXT_SIZE_SP));
            numberKeyTextColor = array.getResourceId(R.styleable.RandomNumberKeyboard_numberKeyTextColor,
                    R.drawable.key_text_color);

//            borderColor = array.getResourceId(R.styleable.RandomNumberKeyboard_borderColor, android.R.color.darker_gray);

        } finally {
            array.recycle();
        }
    }

    private void inflateView() {
        View view = inflate(getContext(), R.layout.layout_number_keyboard, this);

        numericKeys = new ArrayList<>(COUNT);

        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < COUNT; i++){
            TextView tv = (TextView) view.findViewById(getResources().getIdentifier("tvKey" + i, "id", getContext().getPackageName()));
            tv.setTextSize(numberKeyTextSize);
            numericKeys.add(tv);
            numbers.add(i);
        }

        if(isRandom) Collections.shuffle(numbers);

        tvLeftAux = view.findViewById(R.id.tvLeftAux);
        tvRightAux = view.findViewById(R.id.tvRightAux);

        for(int i = 0; i < COUNT; i++){
            TextView key = numericKeys.get(i);

            final int number = numbers.get(i);

            key.setText(number + "");
            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onNumberClicked(number);
                    }
                }
            });
        }

        tvLeftAux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onLeftAuxButtonClicked();
                }
            }
        });

        tvRightAux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRightAuxButtonClicked();
                }
            }
        });

        setStyles();
    }

    public void resetRandom(){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < COUNT; i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for(int i = 0; i < COUNT; i++){
            TextView key = numericKeys.get(i);

            final int number = numbers.get(i);

            key.setText(number + "");
            key.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onNumberClicked(number);
                    }
                }
            });
        }
    }

    private void setStyles() {
        setVerticalSpacing(verticalSpacing);
        setHorizontalSpacing(horizontalSpacing);
        setNumberKeyBackground(numberKeyBackground);
        setNumberKeyTextColor(numberKeyTextColor);
        setLeftAuxBackground(leftAuxBackground);
        setRightAuxBackground(rightAuxBackground);
        setLeftAuxText(leftAuxText);
        setRightAuxText(rightAuxText);
    }


    public int dpToPx(float valueInDp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, getResources().getDisplayMetrics());
    }

    public int spToPx(float valueInSp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, valueInSp, getResources().getDisplayMetrics());
    }
}
