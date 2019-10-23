package com.teamgehem.gehemengine.util;

import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.teamgehem.gehemengine.bean.ImageBean;
import com.teamgehem.survivinginspace.R;

// TODO: Auto-generated Javadoc

/**
 * <pre>
 * R Class - drawable Class 내부 이미지들을 (key,bitmap)으로 매칭하여 저장함.
 * Class        :	ImageGroup
 * FileName     :	ImageGroup.java
 * Package      :	com.teamgehem.gehemengine.util
 * Date         :	2011. 5. 13 오전 7:12:19
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:       1.0    
 */
public class ImageGroup {
    
    private static final String TAG = "ImageGroup";
    
    /** The Resources. */
    private Resources r;
    
    /** The FIELDNUM. */
    private final int FIELDNUM;
    
    /** The m. */
    private HashMap <String,ImageBean> m;
    
    /**
     * Images's Constructor.
     *
     * @param FIELDNUM the fIELDNUM
     * @param r the r
     * @param handler 
     * @param w the w
     * @param h the h
     * @Comment  :
     */
    public ImageGroup(Context context,int FIELDNUM) {
        this.r = context.getResources();
        this.FIELDNUM=FIELDNUM;
        this.m = new HashMap<String,ImageBean>();
        init();
    }
    
    /**
     * Inits the.
     */
    private void init() {
        
        final int RESID=0x7f060053;
        String bName;
        Bitmap bm;
        BitmapFactory.Options option = new BitmapFactory.Options();
        //option.inJustDecodeBounds = true;
    //    option.inPreferredConfig = Bitmap.Config.ARGB_4444;
        option.inSampleSize = 1;
        option.inDither = true;
/*        for(int i=0; i<=0x7f060069 - 0x7f060053; i++) {
            int id = RESID + i;
            bm = setBm(id,option);
            bName = r.getResourceEntryName(id);
            m.put(bName,new ImageBean(bName,bm));
        }*/

        int [] ids = {
                R.drawable.body_00_paras,
                R.drawable.body_02_rect_green,
                R.drawable.scene_00_company,
                R.drawable.scene_00_logo,
                R.drawable.scene_02_play_ani_explosion,
                R.drawable.scene_02_play_missile_middle,
                R.drawable.scene_02_play_missile_small_paras,
                R.drawable.scene_02_play_missile_small,
                R.mipmap.scene_02_play_ui_background
        };

        for(int i=0; i< ids.length; ++i) {
            int id = ids[i];
            bm = setBm(id,option);
            bName = r.getResourceEntryName(id);
            m.put(bName,new ImageBean(bName,bm));
        }
    }
    
    /**
     * Sets the bm.
     *
     * @param id the id
     * @return the bitmap
     */
    private Bitmap setBm(int id) {
        return BitmapFactory.decodeResource(r,id);
    }
    private Bitmap setBm(int id, BitmapFactory.Options option){
        return BitmapFactory.decodeResource(r,id, option);
    }
    
    /**
     * bName과 매칭되는 Bitmap을 return.
     * Gets the bitmap.
     *
     * @param bName the b name
     * @return the bitmap
     */
    public Bitmap getBitmap(String bName) {
        return m.get(bName).getBitMap();
    }
    
    public Bitmap getBitmapGrayScale(String bName){
        return toGrayscale(m.get(bName).getBitMap());
    }
    
    public int getSize(){
        return m.size();
    }
    
    public Bitmap getBitmapScale(String bName, float rate){
        Bitmap bitmap = m.get(bName).getBitMap();
        int dw = (int)((float)bitmap.getHeight() * rate);
        int dh = (int)((float)bitmap.getWidth() * rate);
        return Bitmap.createScaledBitmap(bitmap, dw, dh, true);
    }
    
    /**
     * bName과 매칭되는 Bitmap의 가로 길이를 return.
     * Gets the bitmap width.
     *
     * @param bName the b name
     * @return the bitmap width
     */
    public int getBitmapWidth(String bName) {
        return m.get(bName).getWidth();
    }
    
    /**
     * bName과 매칭되는 Bitmap의 세로 길이를 return.
     * Gets the bitmap height.
     *
     * @param bName the b name
     * @return the bitmap height
     */
    public int getBitmapHeight(String bName) {
        return m.get(bName).getHeight();
    }
    
    public Point getBitmapSize(String bName){
        return m.get(bName).getSizeP();
    }
    
    public void dispose(){
        Iterator<ImageBean> iterator = m.values().iterator();
        while(iterator.hasNext()){
            iterator.next().disposeBitmap();
        }
        m.clear();
        m=null;
    }
    
    public boolean nullImageGroup(){
        if(m!=null&&!m.isEmpty()){
            return false;
        }
        return true;
    }
    
    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {        
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();    

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

}//class














