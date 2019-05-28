package com.idpz.bazarayesh.Views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Mehrdad on 8/27/2017.
 */

public class fontTextView extends android.support.v7.widget.AppCompatTextView {

    public fontTextView(Context context) {
        super(context);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/fws.ttf");
        this.setTypeface(irsans);
    }

    public fontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface irsans = Typeface.createFromAsset(context.getAssets(), "fonts/fws.ttf");
        this.setTypeface(irsans);
    }
}
