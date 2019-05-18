package com.sorgs.ndktest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.ByteArrayOutputStream;

/**
 * description: xxx.
 *
 * @author Sorgs.
 * Created date: 2019/5/5.
 */
public class XgimiCamera extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 线程运行的标识，用于控制线程
     */
    private boolean flag;
    private Paint mPaint;
    private SurfaceHolder mHolder;
    private int mDefaultWidth;
    private int mDefaultHeight;
    private Rect mRect;
    private HandlerThread mHandlerThread;
    private SurfaceViewHandler mSurfaceViewHandler;


    public XgimiCamera(Context context) {
        this(context, null);
    }

    public XgimiCamera(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XgimiCamera(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }


    /**
     * SurfaceView创建
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHandlerThread = new HandlerThread("SurfaceViewThread");
        mHandlerThread.start();
        mSurfaceViewHandler = new SurfaceViewHandler(mHandlerThread.getLooper());
        mSurfaceViewHandler.post(new DrawRunnable());
        flag = true;
        doDraw();
        //mThread.start();
    }

    /**
     * SurfaceView的视图发生改变
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * SurfaceView销毁
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //把线程运行的标识设置成false
        flag = false;
    }

    public void doDraw() {
        Log.i("sorgsOnDraw", "doDraw: ");
        //getCameraDate(123);
        Canvas canvas = mHolder.lockCanvas();
        if (mPaint == null) {
            mPaint = new Paint();
        }
        byte[] bytes = Bitmap2Bytes(
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, new BitmapFactory.Options()));
        canvas.drawBitmap(Bytes2Bimap(bytes), 500, 500, mPaint);
        mHolder.unlockCanvasAndPost(canvas);
    }

    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    private void getBitmapDimension(Integer integer) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.getResources(), integer, options);
        mDefaultWidth = options.outWidth;
        mDefaultHeight = options.outHeight;
        mRect = new Rect(0, 0, mDefaultWidth, mDefaultHeight);
    }

    @Override
    public void run() {

    }

    public native final byte[] getCameraDate(int add);

    private static class SurfaceViewHandler extends Handler {

        SurfaceViewHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private class DrawRunnable implements Runnable {

        @Override
        public void run() {
        }
    }
}
