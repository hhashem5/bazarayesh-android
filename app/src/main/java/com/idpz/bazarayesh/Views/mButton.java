package com.idpz.bazarayesh.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Mehrdad on 8/27/2017.
 */

public class mButton extends android.support.v7.widget.AppCompatButton {
    public mButton(Context context) {
        super(context);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans(FaNum).ttf");
        this.setTypeface(irsans);
    }

    public mButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans(FaNum).ttf");
        this.setTypeface(irsans);
    }
}
