package com.teamgehem.gehemengine.util;

import java.text.NumberFormat;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * The Class Graphics.
 *
 * @author	:	Gehem_um
 * @version	:
 */
public class Graphics {
    
    private static final String TAG="Graphics";
    
    private static final int ORIGIN=0;
    
    /** The Constant FILL. */
    public static final int FILL = 0;
    
    /** The Constant STROKE. */
    public static final int STROKE = FILL+1;
    
    /** The Constant FILL_AND_STROKE. */
    public static final int FILL_AND_STROKE = FILL+2;
    
    /** The Constant FONT_SOYA_DADUM. */
    public static final int FONT_DEFAULT = Integer.MAX_VALUE,
                            FONT_ACTION_JACK = FONT_DEFAULT-1,
                            FONT_ALBAS = FONT_ACTION_JACK-1,
                            FONT_CANDICE = FONT_ALBAS-1,
                            FONT_HOUSE = FONT_CANDICE-1,
                            FONT_SOYA_DADUM =FONT_HOUSE-1; 
    
    /** The holder. */
    protected SurfaceHolder holder;
    
    /** The canvas. */
    protected Canvas canvas;
    
    /** The alpha. */
    protected int alphaIn;
    protected int alphaOut;
    
    /** The bg paint. */
    private Paint bgPaint;
    
    /** The paint. */
    private Paint paint;
    
    private Paint alphaPaint;
    
    /** The defalut paint. */
    private Paint defalutPaint;
    
    private Paint touchFilterPaint; 
    
    /** The tf. */
    private HashMap<Integer, Typeface> tf;
    
    /** The hashmap. */
    private HashMap<String, Paint> hashmap;
    
    /** The r. */
    private Rect r = new Rect();
    
    /** The am. */
    private AssetManager am;
    
    private Matrix mRotateMatrix;
    private float mRotateAngle;
    
    
    
    
    
    /**
     * Instantiates a new graphics.
     *
     * @param holder the holder
     * @param am the am
     */
    public Graphics(SurfaceHolder holder, AssetManager am) {
        this.holder=holder;
        this.am = am;
        paint = defalutPaint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        bgPaint = new Paint();
        bgPaint.setColor(Color.BLACK);
        touchFilterPaint = new Paint();
        ColorMatrix colormatrix = new ColorMatrix(
        new float[]{
            0.299f, 0.587f, 0.114f, 0, 0, 
            0.299f, 0.587f, 0.114f, 0, 0, 
            0.299f, 0.587f, 0.114f, 0, 0, 
            0, 0, 0, 1, 0 });
        touchFilterPaint.setColorFilter(new ColorMatrixColorFilter(colormatrix));
        
        alphaPaint = new Paint();
        alphaPaint.setAlpha(200);
        
        alphaIn = 0;
        alphaOut = 255;
        tf = new HashMap<Integer, Typeface>();
        hashmap = new HashMap<String, Paint>();
        
        mRotateMatrix = new Matrix();
        mRotateAngle = 0;
    }
    
    // lock
    /**
     * Lock.
     *
     * @return the canvas
     */
    public Canvas lock() {
        canvas=holder.lockCanvas();
        return canvas;
    }
    
    public Canvas lock(Canvas canvas){
        this.canvas=canvas;
        return this.canvas;
    }
    // unlock
    /**
     * Unlock.
     */
    public void unlock() {
        holder.unlockCanvasAndPost(canvas);
    }
    
    // set Color
    /**
     * Sets the color.
     *
     * @param color the new color
     */
    public void setColor(int color) {
        paint.setColor(color);
        
        
    }
    
    // set FontSize
    /**
     * Sets the font size.
     *
     * @param fontSize the new font size
     */
    public void setFontSize(float fontSize) {
        paint.setTextSize(fontSize);
    }
    
    public void setTextAlign(Align align){
        paint.setTextAlign(align);
    }
    
    // set FontType
    /**
     * Sets the font type.
     *
     * @param tf the new font type
     */
    public void setFontType(Typeface tf) {
        paint.setTypeface(tf);
    }
    
    
    // get String Width
    /**
     * String width.
     *
     * @param str the str
     * @return the int
     */
    public int stringWidth(String str) {
        return (int) paint.measureText(str);
    }
    // get String Heigh
    /**
     * String height.
     *
     * @return the int
     */
    public int stringHeight() {
        return (int) paint.getTextSize();
    }
    
    /**
     * Draw string position.
     *
     * @param str the str
     * @param x the x
     * @param y the y
     * @return the int[]
     */
    private int[] drawStringPosition(String str, int x, int y) {
        int p[]=new int[2];
        p[0] = x - stringWidth(str)/2;
        p[1] = y - stringHeight();
        return p;
    }
    
    // drawing String
    /**
     * Draw string.
     *
     * @param str the str
     * @param x the x
     * @param y the y
     */
    public void drawString(String str, int x, int y) {
        canvas.drawText(str,x,y,paint);
      //  canvas.dra
    }
    
    /**
     * Draw string.
     *
     * @param num the num
     * @param x the x
     * @param y the y
     */
    public void drawString(int num, int x, int y) {
        String str = NumberFormat.getNumberInstance().format(num);
        canvas.drawText(str,x,y,paint);
    }
    
    public void drawRelativeString(String str, int x, int y ,int rX, int rY){
        canvas.save();
        canvas.translate(rX, rY);
        canvas.drawText(str,x,y,paint);
        canvas.restore();
    }
    public void drawRelativeString(String str, Point point, Point rPoint){
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        canvas.drawText(str,point.x,point.y,paint);
        canvas.restore();
    }
    
    public void drawRelativeString(String str[], Point point[], int rX, int rY){
        canvas.save();
        canvas.translate(rX, rY);
        for(int i=0; i<str.length; i++)
            canvas.drawText(str[i],point[i].x,point[i].y,paint);
        canvas.restore();
    }
    public void drawRelativeString(String str[], Point point[], Point rPoint){
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        for(int i=0; i<str.length; i++)
            canvas.drawText(str[i],point[i].x,point[i].y,paint);
        canvas.restore();
    }
    
    // drawing Rect
    /**
     * Draw rect.
     *
     * @param style the style
     * @param x the x
     * @param y the y
     * @param w the w
     * @param h the h
     */
    public void drawRect(int style, int x, int y, int w, int h) {
        switch (style) {
            case FILL:
                paint.setStyle(Paint.Style.FILL);
                break;
            case FILL_AND_STROKE:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case STROKE:
            default:
                paint.setStyle(Paint.Style.STROKE);
                break;
        }
        r.set(x,y,x+w,y+h);
        canvas.drawRect(r,paint);
    }
    public void drawRect(int style,Rect rect) {
        switch (style) {
            case FILL:
                paint.setStyle(Paint.Style.FILL);
                break;
            case FILL_AND_STROKE:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case STROKE:
            default:
                paint.setStyle(Paint.Style.STROKE);
                break;
        }
        r.set(rect);
        canvas.drawRect(r,paint);
    }
    
    public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter)
    {
        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }
//    
//    public void drawArcAlpha(RectF oval, float startAngle, float sweepAngle, boolean useCenter){
//        canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
//    }
    
//    private static Bitmap makeDst(int w, int h) {
//            
//        c.drawOval(new RectF(0, 0, w*3/4, h*3/4), p);
//        return bm;
//    }
    
    public void drawBitmapCliiping(Bitmap bitmap,RectF oval, Xfermode xfermode,float startAngle, float sweepAngle, float tX, float tY, int x, int y) {
        try
        {
            int sc = canvas.saveLayer(tX,tY,tX+bitmap.getWidth(),tY+bitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);
            canvas.translate(tX, tY);
            canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(bitmap,x,y,paint);
            paint.setXfermode(null);
            canvas.restoreToCount(sc);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    public void drawBitmap(Bitmap bitmap) {
        try
        {
            canvas.drawBitmap(bitmap,ORIGIN,ORIGIN, null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    /**
     * Draw bitmap.
     *
     * @param bitmap the bitmap
     * @param x the x
     * @param y the y
     */
    public void drawBitmap(Bitmap bitmap, int x, int y) {
        try
        {
            canvas.drawBitmap(bitmap,x,y,null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    public void drawBitmapRotate(Bitmap bitmap, int x, int y, float delta){
        try
        {
            canvas.save();
            canvas.translate(x, y);
            mRotateMatrix.setRotate(mRotateAngle, (float)bitmap.getWidth()/2, (float)bitmap.getHeight()/2);
            canvas.drawBitmap(bitmap,mRotateMatrix,null);
          //  mRotateAngle = mRotateAngle < -360 ? 0 : mRotateAngle-delta;
            mRotateAngle-=delta;
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }finally{
            canvas.restore();
        }
    }
    public void drawRelativeBitmapRotate(Bitmap bitmap, Point p, Point rPoint,  float delta){
        try
        {
            canvas.save();
            canvas.translate(rPoint.x, rPoint.y);
            mRotateMatrix.setRotate(mRotateAngle, (float)bitmap.getWidth()/2, (float)bitmap.getHeight()/2);
            canvas.drawBitmap(bitmap,mRotateMatrix,null);
          //  mRotateAngle = mRotateAngle < -360 ? 0 : mRotateAngle-delta;
            mRotateAngle-=delta;
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }finally{
            canvas.restore();
        }
    }
    
    public boolean drawBitmapFadeIn(Bitmap bitmap, int x, int y, int depth){
        try
        {
            alphaPaint.setAlpha(alphaIn);
            canvas.drawBitmap(bitmap,x,y,alphaPaint);
            if((alphaIn+=depth) > 255){
                alphaIn = 0;  return false;
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        return true;
    }
    
    public boolean drawBitmapFadeOut(Bitmap bitmap, int x, int y, int depth){
        try
        {
            alphaPaint.setAlpha(alphaOut);
            canvas.drawBitmap(bitmap,x,y,alphaPaint);
            if((alphaOut-=depth) < 0){
                alphaOut = 255; return false;
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        return true;
    }
    public boolean drawBitmapFadeIn(Bitmap bitmap, int x, int y, Point rPoint, int depth){
        try
        {
            canvas.save();
            canvas.translate(rPoint.x, rPoint.y);
            alphaPaint.setAlpha(alphaIn);
            canvas.drawBitmap(bitmap,x,y,alphaPaint);
            if((alphaIn+=depth) > 255){
                alphaIn = 0;  return false;
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }finally{
            canvas.restore();
        }
        return true;
    }
    
    public boolean drawBitmapFadeOut(Bitmap bitmap, int x, int y,Point rPoint,  int depth){
        try
        {
            canvas.save();
            canvas.translate(rPoint.x, rPoint.y);
            alphaPaint.setAlpha(alphaOut);
            canvas.drawBitmap(bitmap,x,y,alphaPaint);
            if((alphaOut-=depth) < 0){
                alphaOut = 255; return false;
            }
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }finally{
            canvas.restore();
        }
        return true;
    }
    
    public void drawBitmapTouch(Bitmap bitmap, int x, int y) {
        try
        {
            canvas.drawBitmap(bitmap,x,y,touchFilterPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    public void drawBitmapTouch(Bitmap bitmap, int x, int y, boolean filter) {
        try
        {
            Paint tempPaint = filter ? touchFilterPaint : null;
            canvas.drawBitmap(bitmap,x,y, tempPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    public void drawBitmapTouch(Bitmap bitmap, Point p, boolean filter) {
        try
        {
            Paint tempPaint = filter ? touchFilterPaint : null;
            canvas.drawBitmap(bitmap,p.x,p.y, tempPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    public void drawRelativeBitmapTouch(Bitmap bitmap, Point p, Point rPoint, boolean filter) {
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        try
        {
            Paint tempPaint = filter ? touchFilterPaint : null;
            canvas.drawBitmap(bitmap,p.x,p.y, tempPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    
    public void drawBitmap(Bitmap bitmap, int x, int y, boolean filter)
    {
        try
        {
            if(filter)
                canvas.drawBitmap(bitmap, x, y, paint);
            else
                canvas.drawBitmap(bitmap, x, y, null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    /**
     * Draw bitmap.
     *
     * @param bitmap the bitmap
     * @param p the p
     */
    public void drawBitmap(Bitmap bitmap, Point p) {
        try
        {
            canvas.drawBitmap(bitmap,p.x,p.y,null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    /**
     * Draw bitmap.
     *
     * @param bitmap the bitmap
     * @param src the src
     * @param dst the dst
     */
    public void drawBitmap(Bitmap bitmap, Rect src, Rect dst) {
        try
        {
            canvas.drawBitmap(bitmap,src,dst,null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
    }
    
    public void drawRelativeBitmap(Bitmap bitmap, Point point, Point rPoint, boolean filter){
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        try
        {
            Paint tempPaint = filter ? touchFilterPaint : null;
            canvas.drawBitmap(bitmap,point.x, point.y, tempPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    public void drawRelativeBitmap(Bitmap bitmap, Point point, Point rPoint){
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        try
        {
            canvas.drawBitmap(bitmap,point.x, point.y,null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    public void drawRelativeBitmap(Bitmap bitmap, Point point, int x, int y){
        canvas.save();
        canvas.translate(x, y);
        try
        {
            canvas.drawBitmap(bitmap,point.x, point.y,null);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    public void drawRelativeBitmap(Bitmap bitmap, int x, int y, Point rPoint, boolean filter){
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        try
        {
            Paint tempPaint = filter ? touchFilterPaint : null;
            canvas.drawBitmap(bitmap,x, y, tempPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    public void drawRelativeBitmap(Bitmap bitmap, int x, int y, int rX, int rY, boolean filter){
        canvas.save();
        canvas.translate(rX, rY);
        try
        {
            Paint tempPaint = filter ? touchFilterPaint : null;
            canvas.drawBitmap(bitmap,x, y, tempPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    
    public void drawRelativeAlphaBitmap(Bitmap bitmap, Point point, Point rPoint, int alpha){
        canvas.save();
        canvas.translate(rPoint.x, rPoint.y);
        try
        {
            alphaPaint.setAlpha(alpha);
            canvas.drawBitmap(bitmap,point.x, point.y,alphaPaint);
        }
        catch (Exception e)
        {
            Log.d(TAG,e.toString() + bitmap.toString());
        }
        canvas.restore();
    }
    
    // drawing BackGround
    /**
     * Back ground.
     *
     */
    public void backGround() {
        canvas.drawColor(Color.BLACK);
    }
    
    public void backGround(int c){
        canvas.drawColor(c);
    }
    
    public void backGroundAlpha(int alpha){
        canvas.drawARGB(alpha, 0, 0, 0);
    }
  
    /**
     * Sets the typeface.
     *
     * @param idx the idx
     * @param tf the tf
     */
    public void addTypeface(int idx, Typeface tf) {
        this.tf.put(idx, tf);
    }
  
    /**
     * Sets the typeface.
     *
     * @param idx the idx
     * @param fontPath the font path
     */
    public void addTypeface(int idx, String fontPath) {
        this.tf.put(idx, Typeface.createFromAsset(am, fontPath));
    }
    
    /**
     * Sets the font type.
     *
     * @param idx the new font type
     */
    public void setFontType(int idx) {
        paint.setTypeface(tf.get(idx));
    }
    
    /**
     * Fade out.
     */
    public void fadeOut() {
        canvas.drawColor(Color.argb(alphaIn=alphaIn<255?alphaIn+=3:255, 0, 0, 0));
        
    }
    
    /**
     * Adds the paint.
     *
     * @param key the key
     * @param value the value
     */
    public void addPaint(String key, Paint value) {
        value.setAntiAlias(true);
        this.hashmap.put(key, value);
        this.paint = value;
        Log.d(TAG,"hashmap Size = " + String.valueOf(hashmap.size()));
    }
    
    /**
     * Sets the panit.
     *
     * @param key the key
     * @return true, if successful
     */
    public boolean setPanit(String key) {
        this.paint = hashmap.get(key);
        if(paint==null) {
            paint = defalutPaint;
            Log.d(TAG,"paint("+key+") has null");
            return false;
        }
        return true;
    }
    
    public boolean setPanit(String key, float textSize) {
        this.paint = hashmap.get(key);
        this.paint.setTextSize(textSize);
        if(paint==null) {
            paint = defalutPaint;
            paint.setTextSize(textSize);
            Log.d(TAG,"paint("+key+") has null");
            return false;
        }
        return true;
    }
    
    public Paint getPaint(String key)
    {
        return (Paint)hashmap.get(key);
    }
    
    public void setBGPaintColor(int c){
        bgPaint.setColor(c);
    }
    
}// class















