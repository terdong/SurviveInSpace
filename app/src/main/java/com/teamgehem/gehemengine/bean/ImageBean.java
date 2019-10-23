package com.teamgehem.gehemengine.bean;

import android.graphics.Bitmap;
import android.graphics.Point;

// TODO: Auto-generated Javadoc
/**
 * <pre>
 * Bitmap 및 Bitmap 정보를 담고 있는 Bean 객체
 * Class        :	ImageBean
 * FileName     :	ImageBean.java
 * Package      :	com.teamgehem.gehemengine.bean
 * Date         :	2011. 5. 13 오전 6:47:53
 * </pre>
 * 
 * @author : Gehem_um
 * @version : 1.0
 */
public class ImageBean
{
    
    /** The b name. */
    private String bName;
    
    /** The bitmap. */
    private Bitmap bitmap;
    
    /** The height. */
    private int width, height;
    
    private Point sizeP;
    
    /** The c p. */
    private Point cP;

    /**
     * Instantiates a new image bean.
     *
     * @param bName the b name
     * @param b the b
     */
    public ImageBean(String bName, Bitmap b)
    {
        this.bName = bName;
        this.bitmap = b;
        width = b.getWidth();
        height = b.getHeight();
        sizeP = new Point(width, height);
        cP = new Point(width / 2, height / 2);
    }
    
    /**
     * Gets the bit map.
     * 
     * @return the bit map
     */
    public Bitmap getBitMap()
    {
        return bitmap;
    }
    
    // public void setBitMap(Bitmap bitmap) {
    // this.bitmap = bitmap;
    // }
    
    /**
     * Gets the b name.
     * 
     * @return the b name
     */
    public String getbName()
    {
        return bName;
    }
    
    /**
     * Gets the width.
     * 
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }
    
    // public void setWidth(int width) {
    // this.width = width;
    // }
    
    /**
     * Gets the height.
     * 
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }
    
    // public void setHeight(int height) {
    // this.height = height;
    // }
    
    /**
     * Gets the c p.
     * 
     * @return the c p
     */
    public Point getcP()
    {
        return cP;
    }
    
    /**
     * Sets the c p.
     * 
     * @param cP
     *            the new c p
     */
    public void setcP(Point cP)
    {
        this.cP = cP;
    }
    
    public Point getSizeP()
    {
        return sizeP;
    }

    public void disposeBitmap(){
        this.bitmap.recycle();
        bitmap=null;
    }
}//class
