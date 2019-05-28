package com.idpz.bazarayesh.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ToggleButton;

/**
 * Created by Mehrdad on 8/27/2017.
 */

public class mToggle extends ToggleButton {
    public mToggle(Context context) {
        super(context);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans(FaNum).ttf");
        this.setTypeface(irsans);
    }

    public mToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSans(FaNum).ttf");
        this.setTypeface(irsans);
    }
}
