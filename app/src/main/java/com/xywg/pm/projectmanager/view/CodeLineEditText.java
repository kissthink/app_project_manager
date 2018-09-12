package com.xywg.pm.projectmanager.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.xywg.pm.projectmanager.R;

public class CodeLineEditText extends LinearLayout {

    private LineEditText lineEditText;
    private ToggleButton toggleButton;

    public CodeLineEditText(Context context) {
        this(context, null);
    }

    public CodeLineEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeLineEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_code_line_edit, this);
        lineEditText = view.findViewById(R.id.line_et);
        toggleButton = view.findViewById(R.id.togglePwd);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    lineEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    lineEditText.setSelection(lineEditText.getText().toString().trim().length());
                } else {
                    //否则隐藏密码
                    lineEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    lineEditText.setSelection(lineEditText.getText().toString().trim().length());
                }
            }
        });
    }

    public String getString() {
        if (null != lineEditText) {
            String s = lineEditText.getText().toString().trim();
            return s;
        }
        return "";
    }

    public void setText(CharSequence text) {
        if (null != lineEditText)
            lineEditText.setText(text);
    }

}
