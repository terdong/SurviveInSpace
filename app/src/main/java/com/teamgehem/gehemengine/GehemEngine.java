/*****************************************************************************
 * Copyright (c) 2010 - 2011, Gehem_um, All rights reserved. 
 * 
 * 0. Project	: GehemEngine
 * 1. FileName	: GehemEngine.java
 * 2. Package	: com.teamgehem.gehemengine
 * 3. Commnet	: Description for Project GehemEngine.
 *                안드로이드의 2D Game에 최적화된 통합엔진.
 * 4. Writer	: Gehem_um
 * 5. Date	: 2011. 5. 11 오전 4:41:22
 * 6. Edit	: 
 *               name    :  date         : reference    : version  : comment
 *              ------------------------------------------------------
 *              Gehem_um  : 2010. 11. 16 :              :  1.0     : 신규 개발.
 *              Gehem_um  : 2011. 5. 10  :              :  1.0     : 주석 정리.
 *              Gehem_um  : 2011. 5. 11  :              :  1.1     : 엔진 보수.
 *              Gehem_um  : 2011. 6. 25  :              :  1.2     : graphics.java 날라가서 재구축작업.
 *
 *****************************************************************************/
package com.teamgehem.gehemengine;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.teamgehem.gehemengine.util.ImageGroup;
import com.teamgehem.survivinginspace.R;

// TODO: Auto-generated Javadoc

/**
 * <pre>
 * Activity를 상속받은 GehemEngine의 기본 Activity. 이 Class를 상속받아 설정한다.
 * Class        :	GehemEngine
 * FileName     :	GehemEngine.java
 * Package      :	com.teamgehem.gehemengine
 * Date         :	2011. 5. 11 오전 3:52:43
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:       1.0
 */
public class GehemEngine extends Activity {
    protected ImageGroup ig;
    private static final String TAG="GehemEngine";
    /** 화면에 띄울 GehemEngine's Activity의 최초 View. */
    private GehemView gv;
    protected Context context;
    protected int rFieldNum;
    private Handler mHandler;
    
    /**
     * <PRE>
     * Override onCreate 
     * </PRE>
     * @see Activity#onCreate(Bundle)
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mHandler = new Handler();
        initClass();
        initGehemEngine();
    }

    /**
     *  MainView 필드를 초기화(객체 생성)하기 위한 메서드.
     */
    private void initGehemEngine() {
        if(ig==null){
            new Thread(){
                @Override
                public void run()
                {
                    ig = new ImageGroup(context,rFieldNum);
                    if(ig.getSize()!=0){
                        mHandler.post(new Runnable() {
                            public void run()
                            {
                                changeView();
                            }
                        });
                    }
                    else
                        Log.d(TAG,"ig.size==0");
 //                   handler.sendEmptyMessage(99);
                }
            }.start();
        }
    }
    
    protected void changeView(){
        setGv(new ImplementsView(GehemEngine.this, ig));
        Log.d(TAG,"ig.size()="+String.valueOf(ig.getSize()));
    }
    
    protected void initClass(){
     //   setGv(new LoadingView(this));
        context = this;
        rFieldNum = R.drawable.class.getDeclaredFields().length;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG,"OnPause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG,"OnResume");
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d(TAG,"OnStop");
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
//        if(gv!=null)gv.dispose();
//        if(ig!=null)ig.dispose();
        Log.d(TAG,"OnDestroy");
    }

    @Override
    public void onBackPressed()
    {
  //      gv.saveScreenshot();
        if(gv!=null)
            gv.setGameEnd(true);
        super.onBackPressed();
    }
    
    /**
     * 화면에 띄울 MainView를 변경한다.
     *
     * @param mv the new 화면에 띄울 GehemEngine's Activity의 최초 View
     */
    protected void setGv(GehemView gv) {
        this.gv = gv;
        setContentView(gv);
    }
    
    protected GehemView getGv(){
        return gv;
    }
    
}// class




