package com.example.liuteng.livedemo.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by 刘腾 on 2017/3/4.
 */

public class DefaultDialog {
    private Builder builder;

    public DefaultDialog(Builder builder) {
        this.builder = builder;
    }

    public AlertDialog show() {
        AlertDialog dialog = builder.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
        return dialog;
    }

    public static class Builder {
        private AlertDialog.Builder builder;

        public Builder(Context context) {
            builder = new AlertDialog.Builder(context);
        }

        public Builder setTitle(String title) {
            builder.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            builder.setMessage(message);
            return this;
        }

        public Builder setView(int layoutId) {
            builder.setView(layoutId);
            return this;
        }
        public Builder setView(View view){
            builder.setView(view);
            return this;
        }

        public Builder setNegativeButton(String content, DialogInterface.OnClickListener listener) {
            builder.setNegativeButton(content, listener);
            return this;
        }

        public Builder setPositiveButton(String content, DialogInterface.OnClickListener listener) {
            builder.setPositiveButton(content, listener);
            return this;
        }

        protected AlertDialog show() {
            return builder.show();
        }
    }

}
