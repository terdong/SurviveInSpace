package com.teamgehem.gehemengine.manager;

import java.util.Random;

/**
 * Created by terdo on 2017-12-15 015.
 */

public class ManagerRandom {
    private static ManagerRandom instance = new ManagerRandom();
    private static Random rand = new Random();

    private ManagerRandom() {
    }

    public static ManagerRandom getInstance() {
        return instance;
    }

    public int randWithZero(int num) {
        return this.rand() % num;
    }

    public int randFromOne(int num) {
        return this.rand() % num + 1;
    }

    public int randFromAny(int num, int any) {
        return this.rand() % num + any;
    }

    private int rand() {
        return rand.nextInt() >>> 1;
    }
}
