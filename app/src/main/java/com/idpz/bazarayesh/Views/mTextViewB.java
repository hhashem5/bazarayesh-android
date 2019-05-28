package com.idpz.bazarayesh.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Mehrdad on 8/27/2017.
 */

public class mTextViewB extends android.support.v7.widget.AppCompatTextView {
    public mTextViewB(Context context) {
        super(context);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/iranbold.ttf");
        this.setTypeface(irsans);
    }

    public mTextViewB(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/iranbold.ttf");
        this.setTypeface(irsans);
    }
}
