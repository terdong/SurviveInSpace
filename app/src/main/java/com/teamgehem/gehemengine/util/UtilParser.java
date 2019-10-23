package com.teamgehem.gehemengine.util;

import android.content.res.Resources;
import android.util.Log;

// TODO: Auto-generated Javadoc

/**
 * <pre>
 * (write description)
 * Class        :	UtilParser
 * FileName     :	UtilParser.java
 * Package      :	com.teamgehem.gehemengine.util
 * Date         :	2011. 5. 13 오전 7:20:09
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:
 */
public class UtilParser
{
    private static final String TAG = "UtilParser";
    
    private static final float GESTURE_THRESHOLD_DIP = 1.0f;
    private float mDensity;
    
    /** The instance. */
    private static UtilParser instance;
    
    /** The r. */
    private Resources r;

    /**
     * Instantiates a new util parser.
     *
     * @param r the r
     */
    private UtilParser(Resources r){
        this.r=r;
        mDensity = r.getDisplayMetrics().density;
        if(mDensity==1.25f)
            mDensity=1.5f;
        Log.d(TAG, String.valueOf(mDensity));
    }
    
    /**
     * Gets the single instance of UtilParser.
     *
     * @param r the r
     * @return single instance of UtilParser
     */
    public static UtilParser getInstance(Resources r){
        synchronized(UtilParser.class){
            if(instance==null)
                instance=new UtilParser(r);
        }
        return instance;
    }
    
    /**
     * Sets the resources.
     *
     * @param r the new resources
     */
    public void setResources(Resources r){
        this.r=r;
    }
    
    /**
     * dip를 pixel로 변환.
     * Dip to int.
     *
     * @param dip the number
     * @return the int
     */
    public int dipToPixel(float dip) {
//        int pixel = (int) TypedValue.applyDimension(
//                    TypedValue.COMPLEX_UNIT_DIP, dip,
//                    r.getDisplayMetrics());
        int pixel = (int) (dip * mDensity +0.5f);
        return pixel;
    }
    
    public int pixelToDip(float pixel){
        return (int) (pixel / mDensity * GESTURE_THRESHOLD_DIP);
    }
    
    public int invertPixel(int pixel){
        return dipToPixel(pixelToDip(pixel));
    }
}//class






