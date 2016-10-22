package com.helper.west2ol.fzuhelper.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class RevealButton extends Button {

    private static final int INVALIDATE_DURATION = 10;
    private static int DIFFUSE_GAP = 35;

    private Paint paint;
    private MotionEvent motionEvent;

    private float maxRadio = 0;
    private float radio = 0;
    private float pointX = 0;
    private float pointY = 0;
    private int viewHeight;
    private int viewWidth;

    private boolean actionUpFlag = false;
    private boolean actionDownFlag = false;
    private boolean actionCancelFlag = false;

    public RevealButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public RevealButton(Context context) {
        super(context);
        initPaint();
    }

    public void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAlpha(50);
    }

    /**
     * 设置水波纹画笔
     * setColor控制颜色
     * setAlpha控制透明度
     * **/
    public void setPaint(Paint p){
        paint = p;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
    }
    private void countMaxRadio() {
        if (viewWidth >= viewHeight) {
            if (pointX <= viewWidth / 2) {
                maxRadio = viewWidth - pointX;
            } else {
                maxRadio = pointX;
            }
        } else {
            if (pointY <= viewHeight / 2) {
                maxRadio = viewHeight - pointY;
            } else {
                maxRadio = pointY;
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointX = event.getX();
                pointY = event.getY();
                actionDownFlag = true;
                countMaxRadio();
                postInvalidateDelayed(INVALIDATE_DURATION);
                break;
            case MotionEvent.ACTION_UP:
                if (!actionUpFlag && !actionCancelFlag) {
                    actionUpFlag = true;
                    actionCancelFlag = false;
                    motionEvent = event;
                    return true;
                }
                actionUpFlag = false;
                clearData();
                postInvalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                actionCancelFlag = true;
                clearData();
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!actionDownFlag) {
            return;
        }

        canvas.save();
        canvas.clipRect(0, 0, viewWidth, viewHeight);
        canvas.drawCircle(pointX, pointY, radio, paint);
        canvas.restore();

        if (radio <= maxRadio) {
            radio = radio + DIFFUSE_GAP;
            postInvalidateDelayed(INVALIDATE_DURATION);
        } else {
            if (actionUpFlag) {
                RevealButton.this.dispatchTouchEvent(motionEvent);
            } else {
                actionUpFlag = true;
            }
        }
    }

    private void clearData() {
        actionDownFlag = false;
        maxRadio = 0;
        radio = 0;
        pointX = 0;
        pointY = 0;
    }
}