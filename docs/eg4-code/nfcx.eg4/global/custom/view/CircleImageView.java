package com.nfcx.eg4.global.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class CircleImageView extends ImageView {
    private Paint mPaint;
    private int mRadius;
    private float mScale;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CircleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int iMin = Math.min(getMeasuredWidth(), getMeasuredHeight());
        this.mRadius = iMin / 2;
        setMeasuredDimension(iMin, iMin);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        this.mPaint = new Paint();
        BitmapShader bitmapShader = new BitmapShader(drawableToBitmap(getDrawable()), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.mScale = (this.mRadius * 2.0f) / Math.min(r0.getHeight(), r0.getWidth());
        Matrix matrix = new Matrix();
        float f = this.mScale;
        matrix.setScale(f, f);
        bitmapShader.setLocalMatrix(matrix);
        this.mPaint.setShader(bitmapShader);
        int i = this.mRadius;
        canvas.drawCircle(i, i, i, this.mPaint);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }
}