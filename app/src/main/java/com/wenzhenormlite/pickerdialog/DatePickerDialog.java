package com.wenzhenormlite.pickerdialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Calendar;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DatePickerDialog extends DialogFragment implements OnClickListener {


  private TextView mCancelBtn;
  private TextView mAcceptBtn;

  //日期选择器
  private NumberPicker mYearPicker;
  private NumberPicker mMonthPicker;
  private NumberPicker mDayOfMonthPicker;

  private static String str1 = "1999";
  private static String str2 = "1";
  private static String str3 = "1";

  private Calendar mCalendar;

  private int[] mSelDate;


  public DatePickerDialog() {
  }

  private OnDatePickerClickListener mOnDatePickerClickListener;

  public void setOnDatePickerClickListener(OnDatePickerClickListener l) {
    mOnDatePickerClickListener = l;
  }

  public void show(FragmentManager fragmentManager, String date) {
  }

  public interface OnDatePickerClickListener {
//    public void onCancelClick();

    public void onDateChanged( int year,int monthOfYear, int dayOfMonth);
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // 使用不带theme的构造器，获得的dialog边框距离屏幕仍有几毫米的缝隙。
    // Dialog dialog = new Dialog(getActivity());
    Dialog dialog = new Dialog(getActivity(), R.style.CustomDatePickerDialog);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // must be called before set content
    dialog.setContentView(R.layout.dialog_datepicker);
    dialog.setCanceledOnTouchOutside(true);

    // 设置宽度为屏宽、靠近屏幕底部。
    Window window = dialog.getWindow();
    WindowManager.LayoutParams wlp = window.getAttributes();
    wlp.gravity = Gravity.BOTTOM;
    wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
    window.setAttributes(wlp);


    mCancelBtn = (TextView) dialog.findViewById(R.id.dialog_dashboard_date_cancel);
    mAcceptBtn = (TextView) dialog.findViewById(R.id.dialog_dashboard_date_accept);

    mYearPicker = (NumberPicker) dialog.findViewById(R.id.np_year);
    mMonthPicker = (NumberPicker) dialog.findViewById(R.id.np_month);
    mDayOfMonthPicker =(NumberPicker) dialog.findViewById(R.id.np_day);

    mCalendar = Calendar.getInstance();

    mYearPicker.getChildAt(0).setFocusable(false);
    mMonthPicker.getChildAt(0).setFocusable(false);
    mDayOfMonthPicker.getChildAt(0).setFocusable(false);
    mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

    setDate();
    try {
      SetFormater();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    //为每个numberpicker设置监听器
    mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override
      public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        str1 = mYearPicker.getValue() + "";
        if (Integer.parseInt(str1) % 4 == 0
                && Integer.parseInt(str1) % 100 != 0
                || Integer.parseInt(str1) % 400 == 0) {
          if(str2.equals("1")||str2.equals("3")||str2.equals("5")||str2.equals("7")||str2.equals("8")||str2.equals("10")||str2.equals("12")){
            mDayOfMonthPicker.setMaxValue(31);
            mDayOfMonthPicker.setMinValue(1);
          }else if(str2.equals("4")||str2.equals("6")||str2.equals("9")||str2.equals("11")){
            mDayOfMonthPicker.setMaxValue(30);
            mDayOfMonthPicker.setMinValue(1);
          }else{
            mDayOfMonthPicker.setMaxValue(29);
            mDayOfMonthPicker.setMinValue(1);
          }

        } else {
          if(str2.equals("1")||str2.equals("3")||str2.equals("5")||str2.equals("7")||str2.equals("8")||str2.equals("10")||str2.equals("12")){
            mDayOfMonthPicker.setMaxValue(31);
            mDayOfMonthPicker.setMinValue(1);
          }else if(str2.equals("4")||str2.equals("6")||str2.equals("9")||str2.equals("11")){
            mDayOfMonthPicker.setMaxValue(30);
            mDayOfMonthPicker.setMinValue(1);
          }else{
            mDayOfMonthPicker.setMaxValue(28);
            mDayOfMonthPicker.setMinValue(1);
          }
        }

      }

    });

    mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override
      public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        str2 = mMonthPicker.getValue()+"";
        if(str2.equals("1")||str2.equals("3")||str2.equals("5")||str2.equals("7")||str2.equals("8")||str2.equals("10")||str2.equals("12")){
          mDayOfMonthPicker.setMaxValue(31);
          mDayOfMonthPicker.setMinValue(1);
        }else if(str2.equals("4")||str2.equals("6")||str2.equals("9")||str2.equals("11")){
          mDayOfMonthPicker.setMaxValue(30);
          mDayOfMonthPicker.setMinValue(1);
        }else{
          if (Integer.parseInt(str1) % 4 == 0
                  && Integer.parseInt(str1) % 100 != 0
                  || Integer.parseInt(str1) % 400 == 0) {
            mDayOfMonthPicker.setMaxValue(29);
            mDayOfMonthPicker.setMinValue(1);
          } else {
            mDayOfMonthPicker.setMaxValue(28);
            mDayOfMonthPicker.setMinValue(1);
          }
        }
      }
    });

    mDayOfMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override
      public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        str3 = mDayOfMonthPicker.getValue()+"";

      }
    });


    setNumberPickerDividerColor(mYearPicker);
    setNumberPickerDividerColor(mMonthPicker);
    setNumberPickerDividerColor(mDayOfMonthPicker);


    mCancelBtn.setOnClickListener(this);
    mAcceptBtn.setOnClickListener(this);


    return dialog;
  }

  public void setSelectedDate(int[] date) {
    mSelDate = date;
  }


  public void setDate(){
    mCalendar.setTime(mCalendar.getTime());
    mYearPicker.setMaxValue(2050);
    mYearPicker.setMinValue(1900);

    mMonthPicker.setMaxValue(12);
    mMonthPicker.setMinValue(1);

    mDayOfMonthPicker.setMinValue(1);
    mDayOfMonthPicker.setMaxValue(31);
    if (mSelDate.length != 0) {
      mYearPicker.setValue(mSelDate[0]);
      mMonthPicker.setValue(mSelDate[1]);
      mDayOfMonthPicker.setValue(mSelDate[2]);
    }else{
      mYearPicker.setValue(mCalendar.get(Calendar.YEAR));
      mMonthPicker.setValue(mCalendar.get(Calendar.MONTH) + 1);
      mDayOfMonthPicker.setValue(mCalendar.get(Calendar.DAY_OF_MONTH));
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (mOnDatePickerClickListener != null) {
      if (id == R.id.dialog_dashboard_date_cancel) {
//        mOnDatePickerClickListener.onCancelClick();
         this.dismiss();
      } else if (id == R.id.dialog_dashboard_date_accept) {
        int year = mYearPicker.getValue();
        int month = mMonthPicker.getValue();
        int dayOfMonth = mDayOfMonthPicker.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, dayOfMonth);
        mOnDatePickerClickListener.onDateChanged(year,month,dayOfMonth);
      }
    }
  }



  /**
   * DatePicker设置分割线
   * @param
   */
  private void setNumberPickerDividerColor(NumberPicker numberPicker) {
    NumberPicker picker = numberPicker;
    Field[] pickerFields = NumberPicker.class.getDeclaredFields();
    for (Field pf : pickerFields) {
      if (pf.getName().equals("mSelectionDivider")) {
        pf.setAccessible(true);

        //设置分割线的颜色值
        try {
          pf.set(picker, null);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }


      }
    }
  }

  /**
   * 设置年、月、日后缀
   * @throws NoSuchFieldException
   * @throws IllegalAccessException
     */
  private void SetFormater() throws NoSuchFieldException, IllegalAccessException {
    mYearPicker.setFormatter(new NumberPicker.Formatter() {
      @Override
      public String format(int value) {
        String str=String.valueOf(value);
        str=str+"年";
        return str;
      }
    });

      Field f = NumberPicker.class.getDeclaredField("mInputText");
      f.setAccessible(true);
      EditText inputText = (EditText)f.get(mYearPicker);
      inputText.setFilters(new InputFilter[0]);


    mMonthPicker.setFormatter(new NumberPicker.Formatter() {
      @Override
      public String format(int value) {
        String str=String.valueOf(value);
        str=str+"月";
        return str;
      }
    });
    EditText inputText2 = (EditText)f.get(mMonthPicker);
    inputText2.setFilters(new InputFilter[0]);


    mDayOfMonthPicker.setFormatter(new NumberPicker.Formatter() {
      @Override
      public String format(int value) {
        String str=String.valueOf(value);
        str=str+"日";
        return str;
      }
    });
    EditText inputText3 = (EditText)f.get(mDayOfMonthPicker);
    inputText3.setFilters(new InputFilter[0]);

  }


}
