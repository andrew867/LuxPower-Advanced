package com.nfcx.eg4.global.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import com.nfcx.eg4.R;

/* loaded from: C:\Users\andrew\vscode\LuxPython\LuxCloudApp\EG4 Monitor_1.6.4_APKPure\com.nfcx.eg4\classes2.dex */
public class GifView extends View {
    private static final int DEFAULT_MOVIE_DURATION = 1000;
    private int mCurrentAnimationTime;
    private float mLeft;
    private int mMeasuredMovieHeight;
    private int mMeasuredMovieWidth;
    private Movie mMovie;
    private int mMovieResourceId;
    private long mMovieStart;
    private volatile boolean mPaused;
    private float mScale;
    private float mTop;
    private boolean mVisible;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GifView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentAnimationTime = 0;
        this.mVisible = true;
        this.mPaused = false;
        setViewAttributes(context, attributeSet, i);
    }

    private void setViewAttributes(Context context, AttributeSet attributeSet, int i) {
        setLayerType(1, null);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.GifView, i, R.style.Widget_GifView);
        this.mMovieResourceId = typedArrayObtainStyledAttributes.getResourceId(0, -1);
        this.mPaused = typedArrayObtainStyledAttributes.getBoolean(1, false);
        typedArrayObtainStyledAttributes.recycle();
        if (this.mMovieResourceId != -1) {
            this.mMovie = Movie.decodeStream(getResources().openRawResource(this.mMovieResourceId));
        }
    }

    public void setMovieResource(int i) {
        this.mMovieResourceId = i;
        this.mMovie = Movie.decodeStream(getResources().openRawResource(this.mMovieResourceId));
        requestLayout();
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
        requestLayout();
    }

    public Movie getMovie() {
        return this.mMovie;
    }

    public void setMovieTime(int i) {
        this.mCurrentAnimationTime = i;
        invalidate();
    }

    public void setPaused(boolean z) {
        this.mPaused = z;
        if (!z) {
            this.mMovieStart = SystemClock.uptimeMillis() - this.mCurrentAnimationTime;
        }
        invalidate();
    }

    public boolean isPaused() {
        return this.mPaused;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Movie movie = this.mMovie;
        if (movie != null) {
            int iWidth = movie.width();
            int iHeight = this.mMovie.height();
            int size = View.MeasureSpec.getSize(i);
            float f = 1.0f / (iWidth / size);
            this.mScale = f;
            this.mMeasuredMovieWidth = size;
            int i3 = (int) (iHeight * f);
            this.mMeasuredMovieHeight = i3;
            setMeasuredDimension(size, i3);
            return;
        }
        setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mLeft = (getWidth() - this.mMeasuredMovieWidth) / 2.0f;
        this.mTop = (getHeight() - this.mMeasuredMovieHeight) / 2.0f;
        this.mVisible = getVisibility() == 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mMovie != null) {
            if (!this.mPaused) {
                updateAnimationTime();
                drawMovieFrame(canvas);
                invalidateView();
                return;
            }
            drawMovieFrame(canvas);
        }
    }

    private void invalidateView() {
        if (this.mVisible) {
            postInvalidateOnAnimation();
        }
    }

    private void updateAnimationTime() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        if (this.mMovieStart == 0) {
            this.mMovieStart = jUptimeMillis;
        }
        int iDuration = this.mMovie.duration();
        if (iDuration == 0) {
            iDuration = 1000;
        }
        this.mCurrentAnimationTime = (int) ((jUptimeMillis - this.mMovieStart) % iDuration);
    }

    private void drawMovieFrame(Canvas canvas) {
        this.mMovie.setTime(this.mCurrentAnimationTime);
        canvas.save();
        float f = this.mScale;
        canvas.scale(f, f);
        Movie movie = this.mMovie;
        float f2 = this.mLeft;
        float f3 = this.mScale;
        movie.draw(canvas, f2 / f3, this.mTop / f3);
        canvas.restore();
    }

    @Override // android.view.View
    public void onScreenStateChanged(int i) {
        super.onScreenStateChanged(i);
        this.mVisible = i == 1;
        invalidateView();
    }

    @Override // android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        this.mVisible = i == 0;
        invalidateView();
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mVisible = i == 0;
        invalidateView();
    }
}