package com.android.xiaokecong.bigimagedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;


public class LargeImageViewActivity extends AppCompatActivity {
    private LargeImageView largeImageView;
    private int mLastMoveX;
    private int mLastMoveY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_imageview);

        largeImageView = (LargeImageView) findViewById(R.id.id_largeImage);
        largeImageView.setImage(R.drawable.world);

        largeImageView.setOnTouchListener(new View.OnTouchListener() {
            int mLastX;
            int mLastY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = (int) event.getX();
                        mLastY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = mLastX - (int) event.getX();
                        int moveY = mLastY - (int) event.getY();
                        largeImageView.moveBy(moveX - mLastMoveX,moveY - mLastMoveY);
                        mLastMoveX = moveX;
                        mLastMoveY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        mLastMoveX = 0;
                        mLastMoveY = 0;
                        break;
                }
                return true;
            }
        });

    }
}
