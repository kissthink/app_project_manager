package com.xywg.pm.projectmanager.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.common.widget.view.CountDownTextView;
import com.xywg.pm.projectmanager.R;

public class CountLineEditText extends LinearLayout implements View.OnClickListener {

    private LineEditText lineEditText;
    private CountDownTextView countDownTextView;

    public CountLineEditText(Context context) {
        this(context, null);
    }

    public CountLineEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountLineEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_send_code_line_edit, this);
        lineEditText = view.findViewById(R.id.line_et);
        countDownTextView = view.findViewById(R.id.count_text);
        countDownTextView.setOnClickListener(this);
    }

    public String getString() {
        if (null != lineEditText) {
            String s = lineEditText.getText().toString().trim();
            return s;
        }
        return "";
    }

    @Override
    public void onClick(View view) {

    }

}
