package com.bartbergmans.gradientbutton.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;

import com.bartbergmans.gradientbutton.R;

/**
 * A {@link Button} that has a gradient background or stroke.
 *
 * Created by bartbergmans on 07/01/2017.
 */
public class GradientButton extends AppCompatButton {

    private final static String TAG = "GradientButton";

    private int mStroke;
    private boolean mFilled;
    private GradientDrawable.Orientation mOrientation;
    private int[] mGradientColors;
    private boolean mRound;
    private int mBackgroundColor;

    private Path mPath;

    public GradientButton(Context context) {
        super(context);

        mStroke = 0;
        mFilled = false;
        mGradientColors = new int[]{0xFF616261, 0xFF131313};
        mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
    }

    public GradientButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GradientButton,
                0, 0);

        mStroke = a.getDimensionPixelSize(R.styleable.GradientButton_stroke, 0);
        mFilled = a.getBoolean(R.styleable.GradientButton_filled, true);
        switch (a.getInteger(R.styleable.GradientButton_orientation, 0)) {
            case 0:
            default:
                mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 1:
                mOrientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 2:
                mOrientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
            case 3:
                mOrientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 4:
                mOrientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 5:
                mOrientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 6:
                mOrientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 7:
                mOrientation = GradientDrawable.Orientation.TL_BR;
                break;
        }

        mGradientColors = new int[]{Color.GREEN, Color.BLUE};

        mRound = a.getBoolean(R.styleable.GradientButton_round, false);

        mBackgroundColor = a.getColor(R.styleable.GradientButton_fill_color, Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mRound && getHeight() > getWidth()) {
            setWidth(getHeight());
        } else if (mRound && getWidth() > getHeight()) {
            setHeight(getWidth());
        }

        // TODO: Add ripple effect like test_ripple.xml in the Example project
        setBackground(getBackgroundDrawable());

        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mPath != null) {
            canvas.clipPath(mPath);
        }
        super.dispatchDraw(canvas);
    }

    protected Drawable getBackgroundDrawable() {
        GradientDrawable backgroundDrawable = new GradientDrawable(
                mOrientation,
                mGradientColors);
        backgroundDrawable.setCornerRadius(getHeight() / 2);
        backgroundDrawable.setShape(GradientDrawable.RECTANGLE);

        mPath = new Path();
        mPath.addRoundRect(
                new RectF(0, 0, getWidth(), getHeight()),
                getHeight() / 2, getHeight() / 2, Path.Direction.CW);

        if (!mFilled) {
            Bitmap background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas backgroundCanvas = new Canvas(background);
            backgroundCanvas.drawARGB(0, 0, 0, 0);
            backgroundDrawable.setBounds(0, 0, getWidth(), getHeight());
            backgroundDrawable.draw(backgroundCanvas);

            Paint rectPaint = new Paint();
            rectPaint.setAntiAlias(true);
            rectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            backgroundCanvas.drawRoundRect(new RectF(mStroke, mStroke,
                            getWidth() - mStroke,
                            getHeight() - mStroke),
                    getHeight() / 2,
                    getHeight() / 2,
                    rectPaint);

            rectPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
            rectPaint.setColor(mBackgroundColor);
            backgroundCanvas.drawRoundRect(new RectF(mStroke, mStroke,
                            getWidth() - mStroke,
                            getHeight() - mStroke),
                    getHeight() / 2,
                    getHeight() / 2,
                    rectPaint);

            return new BitmapDrawable(getResources(), background);
        } else {
            return backgroundDrawable;
        }
    }
}