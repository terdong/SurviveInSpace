/**
 * @FileName : BeanMissile.java
 * @Project	 : SurviveInSpace
 * @Package : com.android.surviveinspace.bean
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Version  : 
 * @Edit     :
 * @Comment  : 
 */
package com.teamgehem.survivinginspace.bean;

import android.graphics.Point;

/**
 * @Class	 : BeanMissile
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Edit     :
 * @Comment  : 
 */
public class BeanMissile {
    private String imgFileName;
    private Point p;
    private Point arrow;
    private int radius;
    private int dmg;
    private int speed;
    /**
     * BeanMissile's Constructor
     * @Comment  :
     */
    public BeanMissile(String imgFileName) {
        this.imgFileName = imgFileName;
        p = new Point();
        arrow = new Point();
    }
    public String getImgFileName() {
        return imgFileName;
    }
    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public Point getP() {
        return p;
    }
    public void setP(Point p) {
        this.p = p;
    }
    public void setP(int x,int y) {
        this.p.x=x;this.p.y=y;
    }
    public int getDmg() {
        return dmg;
    }
    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public Point getArrow() {
        return arrow;
    }
    public void setArrow(Point arrow) {
        this.arrow = arrow;
    }
}



