/**
 * @FileName : ManagerRandom.java
 * @Project	 : SurviveInSpace
 * @Package : com.android.surviveinspace.system
 * @Date	 : 2010. 11. 16.
 * @Writer   : Gehem_um
 * @Version  : 
 * @Edit     :
 * @Comment  : 
 */
package com.teamgehem.gehemengine.util;

import java.util.Random;

// TODO: Auto-generated Javadoc

/**
 * <pre>
 * 난수발생 관리 클래스.
 * 싱글톤 패턴 사용.
 * Class        :	ManagerRandom
 * FileName     :	ManagerRandom.java
 * Package      :	com.teamgehem.gehemengine.manager
 * Date         :	2011. 5. 13 오전 6:50:08
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:       1.0
 */
public class UtilRandom {
    
    /** The instance. */
    private static UtilRandom instance = new UtilRandom();
    
    /** The rand. */
    private static Random rand=new Random();
    
    /**
     * Instantiates a new manager random.
     */
    private UtilRandom() {}
    
    /**
     * Gets the single instance of ManagerRandom.
     *
     * @return single instance of ManagerRandom
     */
    public static UtilRandom getInstance() {
        return instance;
    }
    
    /**
     * 0부터 num까지 난수 발생.
     * Rand with zero.
     *
     * @param num the num
     * @return the int
     */
    public int randWithZero(int num) {
        return rand()%num;
    }
    
    /**
     * 1부터 num까지 난수 발생.
     * Rand from one.
     *
     * @param num the num
     * @return the int
     */
    public int randFromOne(int num) {
        return (rand()%num)+1;
    }
    
    /**
     * any부터 num까지 난수 발생.
     * Rand from any.
     *
     * @param num the num
     * @param any the any
     * @return the int
     */
    public int randFromAny(int num,int any) {
        return (rand()%num)+any;
    }
    
    /**
     * 양수의 int형 랜덤값을 넘겨줌.
     * Rand.
     *
     * @return the int
     */
    private int rand() {
        return (rand.nextInt()>>>1);
    }

}//class





