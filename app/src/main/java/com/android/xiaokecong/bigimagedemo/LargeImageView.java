package com.android.xiaokecong.bigimagedemo;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 使用BitmapRegionDecoder自定义超大图片显示控件
 *
 */

public class LargeImageView extends View {
    private BitmapRegionDecoder mDecoder;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapFactory.Options mOptions;
    private Rect mRect;
    private boolean mIsFirstMesure;
    public LargeImageView(Context context) {
        super(context);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs,
                          int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mIsFirstMesure = true;
        mRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

    }

    public void setImage(int resId){
        InputStream inputStream = null;

        try {
            inputStream = getResources().openRawResource(resId);
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), resId, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;

            requestLayout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void moveBy(int moveX, int moveY){
        mRect.offset(moveX,moveY);
        checkHeight();
        checkWidth();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = mDecoder.decodeRegion(mRect, mOptions);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mIsFirstMesure){
            mRect.left = 0;
            mRect.top = 0;
            mRect.bottom = getMeasuredHeight();
            mRect.right = getMeasuredWidth();
            mIsFirstMesure = false;
        }
    }

    private void checkHeight(){
        Rect rect = mRect;
        int imageHeight= mImageHeight;

        if (rect.bottom > imageHeight){
            rect.bottom = imageHeight;
            rect.top = imageHeight - getHeight();
        }

        if (rect.top < 0){
            rect.top = 0;
            rect.bottom = getHeight();
        }
    }

    private void checkWidth(){
        Rect rect = mRect;
        int imageWidth = mImageWidth;
        if (rect.right > imageWidth){
            rect.right = imageWidth;
            rect.left = imageWidth - getWidth();
        }

        if (rect.left < 0){
            rect.left = 0;
            rect.right = getWidth();
        }
    }
}
