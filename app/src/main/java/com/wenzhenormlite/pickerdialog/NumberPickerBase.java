package com.wenzhenormlite.pickerdialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by
 * zhangqianqian
 * on 2016/6/13.
 */
class NumberPickerBase extends NumberPicker{


    public NumberPickerBase(Context context) {
        super(context);
    }

    public NumberPickerBase(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public NumberPickerBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumberPickerBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    public void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改字体的属性
//            ((EditText) view).setTextColor(Color.parseColor("#BAA785"));

            ((EditText) view).setTextSize(25);

        }
    }
}
