package com.teamgehem.gehemengine.bean;

import android.graphics.Point;

// TODO: Auto-generated Javadoc
/**
 * <pre>
 * 해당 이미지 객체의 위치 및 사이즈를 계산해주는 Class
 * Class        :	ObjectSizeBean
 * FileName     :	ObjectSizeBean.java
 * Package      :	com.teamgehem.gehemengine.bean
 * Date         :	2011. 5. 11 오전 4:15:22
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:       1.0
 */
public class ObjectSizeBean{
    
    /** The position. */
    private Point position;
    
    /** The Half h. */
    public int WIDTH,HEIGHT, HalfW, HalfH;
    
    /**
     * Instantiates a new object size bean.
     *
     * @param x the x
     * @param y the y
     * @param WIDTH the wIDTH
     * @param HEIGHT the hEIGHT
     */
    public ObjectSizeBean(int x, int y,int WIDTH, int HEIGHT) {
        this.position = new Point(x,y);
        this.WIDTH = WIDTH; this.HEIGHT = HEIGHT;
        this.HalfW = WIDTH/2; this.HalfH = HEIGHT/2;
    }
    
    /**
     * Gets the start p.
     *
     * @return the start p
     */
    public Point getStartP() {
        return position;
    }
    
    /**
     * Sets the start p.
     *
     * @param x the x
     * @param y the y
     */
    public void setStartP(int x, int y) {
        this.position.set(x, y);
    }
    
    /**
     * Sets the start px.
     *
     * @param x the new start px
     */
    public void setStartPX(int x) {
        position.x = x;
    }
    
    /**
     * Sets the start py.
     *
     * @param y the new start py
     */
    public void setStartPY(int y) {
        position.y = y;
    }
}//class
