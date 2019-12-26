package com.example.mydemo.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

public class MyVideoView extends VideoView {

    int width = 0;
    int height = 0;


    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = getDefaultSize(0, widthMeasureSpec);
        int h = getDefaultSize(0, heightMeasureSpec);
        if(width == 0 || height == 0){
            width = w;
            height = h;
        }
        setMeasuredDimension(width, height);
    }

    public void setMeasure(int w,int h){
        this.width = w;
        this.height = h;
    }

    @Override
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
        super.setOnPreparedListener(l);




    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
