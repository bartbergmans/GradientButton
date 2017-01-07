package com.bartbergmans.gradientbutton.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by bartbergmans on 07/01/2017.
 */
public class GradientButton extends Button {
    public GradientButton(Context context) {
        super(context);
    }

    public GradientButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
        shape.setColor(Color.BLACK);
        shape.setStroke(3, Color.BLUE);

        setBackground(shape);

        super.onDraw(canvas);
    }
}