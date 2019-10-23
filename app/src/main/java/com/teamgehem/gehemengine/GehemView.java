package com.teamgehem.gehemengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.teamgehem.gehemengine.bean.ObjectSizeBean;
import com.teamgehem.gehemengine.util.Graphics;
import com.teamgehem.gehemengine.util.ImageGroup;

// TODO: Auto-generated Javadoc
/**
 * <pre>
 * 화면 View를 담당하는 GehemEngine의 MainView Class.
 * Abstract Class로써 SurfaceView를 상속받고, SurfaceHolder.Classback Interface를 상속받는 클래스.
 * Class        :	GehemView
 * FileName     :	GehemView.java
 * Package      :	com.teamgehem.gehemengine
 * Date         :	2011. 5. 11 오전 4:13:33
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:       1.1
 */
public abstract class GehemView extends SurfaceView implements SurfaceHolder.Callback {
    
    
    /** Logo 화면에 엔진 버전을 출력하기 위한 string 변수
     * The version. */
    private String version = "1.2";//MSG.getStr("lib.version.name","lib.version.num");
    
    /** Logging TAG Name. */
    private final String TAG = "GeheView";//MSG.getStr("log.title");    

    /** The TICK. */
    private int TICK = 1000/30;
    
    /** The touch Event Action. */
    protected int tE;
    
    private boolean gameEnd=false;
    
    /**
     * Sets the tick.
     *
     * @param tick the new tick
     */
    public void setTick(int tick) {
        TICK = 1000/tick;
    }
   
    /** The Constant Scene Number, S_LOGO. */
    private final static int
        S_LOGO              = 0;
    
    /** The Constant Scene Number. */
    protected final static int
        S_TITLE             = 1,
        S_PLAY              = 2,
        S_GAMEOVER          = 3;
    
    /** The holder. */
    private SurfaceHolder holder;
    
    /** The timer. */
    private Timer timer;
    
    /** The timer task. */
    private TimerTask tt;
    
    /** The FontManager. */
    private AssetManager am;
    
    /** The ImageGroup 객체. */
    protected ImageGroup ig;
    
    /** The Graphics Util. */
    protected Graphics g;
    
    /** The ObjectSizeBean 객체. */
    protected ObjectSizeBean sz;
    
    /** The Touch Point. */
    protected Point tP;
    
    /** The scene. */
    protected int scene;
    
    protected Resources res;
    
    public int temp;

    protected Context context;
    

    /**
     * Instantiates a new main view.
     *
     * @param context the context
     * @param ig the num
     */
    public GehemView(Context context, ImageGroup ig) {
        super(context);
        initGehemView(context);
        this.ig = ig;
        //getVersionName(context);
//        int a = Integer.MAX_VALUE;
//        g.setTypeface(a--,Typeface.DEFAULT);
//        g.setTypeface(a--,MSG.getStr("path.tf.aj"));
//        g.setTypeface(a--,MSG.getStr("path.tf.albas"));
//        g.setTypeface(a--,MSG.getStr("path.tf.candice"));
//        g.setTypeface(a--,MSG.getStr("path.tf.house"));
//        g.setTypeface(a--,MSG.getStr("path.tf.sd"));
    }
    
    public GehemView(Context context) {
        super(context);
        initGehemView(context);
    }
    
    private void initGehemView(Context context){
        holder = getHolder();
        holder.addCallback(this);
        am = context.getAssets();
        g= new Graphics(holder, am);
        res = getResources();
        this.ig = new ImageGroup(context,9);

        this.context = context;
    }
    
    /**
     * Context 객체로 부터 패키지 버젼만 추출하는 method.
     * Logo 화면에 버젼을 출력하기 위함.
     * Gets the version name.
     *
     * @param context void
     * @return the version name
     * @Comment :
     */
    private void getVersionName(Context context) {
        PackageInfo i;
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            version = i.versionName;
//            Log.d(TAG,version);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 매개 변수(객체)의 필드 개수를 넘겨주는 method
     * Gets the field num.
     *
     * @param object the Object
     * @return the field num
     */
    private int getFieldNum(Object object) {
        int length=-1;
        try {
            length =object.getClass().getDeclaredFields().length; 
 //           Log.d(TAG,String.valueOf(length));
        } catch (Exception ex) {
            Log.e("Error",ex.toString()+": Input Constructor param");
        }
        return  length;
    }
    
    /**
     * <PRE>
     * Override surfaceChanged
     * </PRE>.
     *
     * @param holder the holder
     * @param format the format
     * @param width the width
     * @param height the height
     * @see SurfaceHolder.Callback#surfaceChanged(SurfaceHolder, int, int, int)
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        Log.d(TAG,"surfaceChanged");
    }
    
    /**
     * <PRE>
     * Override surfaceCreated 
     * </PRE>
     * @see SurfaceHolder.Callback#surfaceCreated(SurfaceHolder)
     * @param holder
     */

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"surfaceCreated");
        sz = new ObjectSizeBean(0, 0, getWidth(), getHeight());
        tP = new Point(sz.HalfW,sz.HalfH);
        tt = new PTimerTask();
        timer=timerStart(TICK, tt);
    }

    /**
     * <PRE>
     * Override surfaceDestroyed 
     * </PRE>
     * @see SurfaceHolder.Callback#surfaceDestroyed(SurfaceHolder)
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG,"surfaceDestroyed");
            //gameEnd=true;
            try
            {
                synchronized (holder)
                {
                    timerEnd(timer, tt);
                    if(gameEnd){
                        ig.dispose();
                        dispose();
                    }
                }
            }
            catch (Exception e){}
    }
    
    /**
     * 타이머가 시작될 때 호출되는 method.
     * Timer start.
     *
     * @param tick the tick
     * @param tt the tt
     * @return the timer
     */
    public Timer timerStart(int tick, TimerTask tt) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(tt,0,tick);
        return timer;
    }

    /**
     * 타이머가 종료되면서 타이머 소거시킴.
     * Timer end.
     *
     * @param timer the timer
     */
    private void timerEnd(Timer timer, TimerTask tt) {
        tt.cancel();
        tt=null;
        timer.cancel();
        timer.purge();
        timer=null;
    }

    /**
     * <PRE>
     * Override onTouchEvent 
     * </PRE>
     * @see android.view.View#onTouchEvent(MotionEvent)
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tE = event.getAction();
        switch (scene) {
            case S_LOGO:
                if(tE==MotionEvent.ACTION_DOWN){
                    scene=S_PLAY;
                }
                break;
            default:
                if(tE==MotionEvent.ACTION_DOWN) 
                    tP.set((int)event.getX(), (int)event.getY());
                else if(tE==MotionEvent.ACTION_UP)
                    tP.set(-1, -1);
                else if(tE==MotionEvent.ACTION_MOVE)
                    tP.set((int)event.getX(),(int)event.getY());
                break;
        }
        return true;
    }

    /**
     * <pre>
     * 타이머를 위한 내부 Class.
     * 이 class에서 loop를 처리함.
     * Class        :	PTimerTask
     * FileName     :	MainView.java
     * Package      :	com.teamgehem.gehemengine
     * Date         :	2011. 5. 11 오전 8:13:36
     * </pre>
     *
     * @author	:	Gehem_um
     * @version	:
     */
    public class PTimerTask extends TimerTask{
        
        /**
         * <PRE>
         * Override run 
         * </PRE>
         * @see TimerTask#run()
         */
        @Override
        public void run() {
            if(gameEnd)
                return;
            synchronized (holder)
            {
                if(g.lock()!=null){
                    // BackGround Color
                    g.backGround();
                    mainLoop();
                    g.unlock();
                }
            }
        }
    }// class
    
    /**
     * Sets the scene.
     *
     * @param scene the new scene
     */
    public void setScene(int scene) {
        this.scene=scene;
    }
    
    /**
     * Gets the scene.
     *
     * @return the scene
     */
    public int getScene() {
        return this.scene;
    }
    
    /**
     * 그리기 및 연산이 반복되는 mainLoop method.
     * MainView Class를 상속받은 Class들이 구현해야할 method.
     * 추상 method.
     */
    public abstract void mainLoop();
    
    /**
     * App이 종료 될 때 호출되는 method.
     */
    public abstract void dispose();
    
    public void setGameEnd(boolean b){
        this.gameEnd = b;
        Log.d(TAG,"SetGameEnd");
    }
    
    private String mScreenshotPath = Environment.getExternalStorageDirectory() + "/gehem";
    
    public void saveScreenshot() {
        if (ensureSDCardAccess()) {
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            g.lock(canvas);
            mainLoop();
            File file = new File(mScreenshotPath + "/" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.e("Panel", "FileNotFoundException", e);
            } catch (IOException e) {
                Log.e("Panel", "IOEception", e);
            }
        }
    }
    
    /**
     * Helper method to ensure that the given path exists.
     * TODO: check external storage state
     */
    private boolean ensureSDCardAccess() {
        File file = new File(mScreenshotPath);
        if (file.exists()) {
            return true;
        } else if (file.mkdirs()) {
            return true;
        }
        return false;
    }
    
}// class


