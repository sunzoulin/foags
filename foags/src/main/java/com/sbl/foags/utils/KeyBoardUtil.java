package com.sbl.foags.utils;

import android.content.Context;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class KeyBoardUtil {

    public static void openKeyBoard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void closeKeyBoard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static void closeKeyBoard(Window window, Context mContext){
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
        if (window.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }
}
