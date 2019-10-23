/**
 * @FileName : ManagerPoolMissile.java
 * @Project	 : SurviveInSpace
 * @Package : com.android.surviveinspace.system
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Version  : 
 * @Edit     :
 * @Comment  : 
 */
package com.teamgehem.survivinginspace.system;

import java.util.LinkedList;
import java.util.Queue;

import com.teamgehem.gehemengine.manager.ManagerRandom;
import com.teamgehem.survivinginspace.bean.BeanMissile;
import com.teamgehem.survivinginspace.factory.FactoryMissile;

/**
 * @Class	 : ManagerPoolMissile
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Edit     :
 * @Comment  : Missile Pool Manager, Singleton
 */
public class ManagerPoolMissile {
     private static ManagerPoolMissile instance = new ManagerPoolMissile();
     private Queue<BeanMissile> qu;
     private static final int poolSize = 100;
     private ManagerRandom mr = ManagerRandom.getInstance();
    /**
     * ManagerPoolMissile's Constructor
     * @Comment  :
     */
    private ManagerPoolMissile() {}
    public static ManagerPoolMissile getInstance() {
        return instance;
    }
    public void setMissiles() {
        if(qu==null || qu.isEmpty()) {
            qu = new LinkedList<BeanMissile>();
            BeanMissile bm = null;
            int rand;
            for(int i=0; i<poolSize; i++) {
                rand=mr.randWithZero(100);
                if(rand>90)
                    bm = FactoryMissile.createMissile("scene_02_play_missile_middle");
                else if(rand>85)
                    bm = FactoryMissile.createMissile("scene_02_play_missile_small_paras");
                else 
                    bm = FactoryMissile.createMissile();
                qu.offer(bm);
            }
        }
    }
    public boolean isEmpty() {
        return qu.isEmpty();
    }
    public BeanMissile getMissile() {
        BeanMissile bm = null;
        if(qu.isEmpty())
            return null;
        bm = qu.poll();
        return bm;
    }
    public Queue<BeanMissile> getMissiles() {
        if(qu.isEmpty())
            return null;
        return qu;
    }
    public void releaseMissile(BeanMissile bm) {
        if(qu.size()<poolSize) {
            qu.offer(bm);
        }
    }
}





