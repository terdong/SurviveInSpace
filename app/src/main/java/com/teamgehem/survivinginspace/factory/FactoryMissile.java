/**
 * @FileName : FactoryMissile.java
 * @Project	 : SurviveInSpace
 * @Package : com.android.surviveinspace.factory
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Version  : 
 * @Edit     :
 * @Comment  : 
 */
package com.teamgehem.survivinginspace.factory;


import com.teamgehem.survivinginspace.bean.BeanMissile;

/**
 * @Class	 : FactoryMissile
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Edit     :
 * @Comment  : 
 */
public class FactoryMissile {
    private final static String DEFAULTFileName = "scene_02_play_missile_small";
    /**
     * FactoryMissile's Constructor
     * @Comment  :
     */
    private FactoryMissile() {}
    public static BeanMissile createMissile() {
        return new BeanMissile(DEFAULTFileName);
    }
    public static BeanMissile createMissile(String fileName) {
        return new BeanMissile(fileName);
    }
}







