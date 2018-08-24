package com.njz.letsgoapp.widget.myTextView;

import android.graphics.Paint;

public class PaintUtils{

        /**
         * 计算指定画笔下指定字符串需要的宽度
         */
        public static int getTheTextNeedWidth(Paint thePaint, String text) {
            float[] widths = new float[text.length()];
            thePaint.getTextWidths(text, widths);
            int length = widths.length, nowLength = 0;
            for (int i = 0; i < length; i++) {
                nowLength += widths[i];
            }
            return nowLength;
        }
}