package com.example.mvp.view.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp.R;
import com.example.mvp.MvpApplication;
import com.example.mvp.utils.DisplayUtils;


public class ToastView {

    private static View mview;
    private static Toast toast;
    private static TextView textView;
    private static LayoutInflater inflater;

    public static void showToast(String text) {
        if (mview == null) {
            if (inflater == null)
                inflater = (LayoutInflater) MvpApplication.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview = inflater.inflate(R.layout.view_toast, null);
        }
        if (textView == null)
            textView = mview.findViewById(R.id.tv_toast);
        textView.setText(text);
        if (toast == null) {
            toast = new Toast(MvpApplication.getAppContext());
            mview.setMinimumWidth(DisplayUtils.getSystemWidth(MvpApplication.getAppContext()));
            mview.setMinimumHeight((int) MvpApplication.getAppContext().getResources().getDimension(R.dimen.dp38));
            mview.setBackgroundResource(android.R.color.transparent);
            toast.setView(mview);
            toast.setDuration(Toast.LENGTH_SHORT);
            float dimension = MvpApplication.getAppContext().getResources().getDimension(R.dimen.dp130);
            toast.setGravity(Gravity.BOTTOM, 0, (int) dimension);
        }
        toast.show();
    }

}
