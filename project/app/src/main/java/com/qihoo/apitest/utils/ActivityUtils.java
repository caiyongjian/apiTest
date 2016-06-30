package com.qihoo.apitest.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by caiyongjian on 16-6-30.
 */
public class ActivityUtils {
    public static void installAllButtonListener(ViewGroup group, View.OnClickListener listener) {
        for (int index = 0; index < group.getChildCount(); index++) {
            View view = group.getChildAt(index);
            if (view instanceof Button) {
                view.setOnClickListener(listener);
            } else if (view instanceof ViewGroup) {
                installAllButtonListener((ViewGroup) view, listener);
            }
        }
    }

    public static void installAllButtonListener(Activity activity, View.OnClickListener listener) {
        View root = activity.getWindow().getDecorView();
        if (root instanceof ViewGroup) {
            installAllButtonListener((ViewGroup) root, listener);
        }
    }
}
