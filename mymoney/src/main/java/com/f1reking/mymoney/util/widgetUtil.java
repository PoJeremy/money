package com.f1reking.mymoney.util;

import android.text.Selection;
import android.text.Spannable;
import android.widget.EditText;

/**
 * 控件辅助工具类
 * Created by F1ReKing on 2016/1/2.
 */
public class widgetUtil {

	public static void setEditTextCursorLocation(EditText editText) {
		CharSequence text = editText.getText();
		if (text instanceof Spannable) {
			Spannable spanText = (Spannable) text;
			Selection.setSelection(spanText, text.length());
		}
	}
}
