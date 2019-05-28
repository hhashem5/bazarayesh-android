package com.idpz.bazarayesh.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Mehrdad on 8/27/2017.
 */

public class mEditText extends android.support.v7.widget.AppCompatEditText {
    public mEditText(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSans(FaNum).ttf");
        this.setTypeface(tf);
    }

    public mEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/IRANSans(FaNum).ttf");
        this.setTypeface(tf);
    }
}
