package com.teamgehem.survivinginspace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.teamgehem.gehemengine.GehemView;
import com.teamgehem.gehemengine.bean.ObjectSizeBean;
import com.teamgehem.gehemengine.manager.ManagerRandom;
import com.teamgehem.gehemengine.msg.Msg;
import com.teamgehem.survivinginspace.bean.BeanMissile;
import com.teamgehem.survivinginspace.system.ManagerPoolMissile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by terdo on 2017-12-14 014.
 */

public class ImplementsView extends GehemView {
    private final static int SPEED = 10;

    private final String TAG = "ImplementsView";

    public class Player{
        private Point uP;               // User Point
        private int speed;
        private int radius;
        private int score;
        private int time;
        private int total;
        private int life;
        private int strLength;
        private int fontSize;
        private int fontHeight;
        private StringBuffer strBuff;
        public Player(int HalfW, int HalfH, int radius) {
            this.uP = new Point(HalfW, HalfH);
            this.radius= radius;
            this.score = 0;
            this.time = 0;
            this.total = 0;
            this.life = 10;
            this.speed = SPEED;
            this.strLength = dipToInt(50);
            this.strBuff = new StringBuffer();
            this.fontSize = dipToInt(20);
            this.fontHeight = dipToInt(30);
            Log.d(TAG,String.valueOf(strLength));
        }
        /**
         * void
         *
         * @Comment : 터치 포인트로 unit 이동
         */
        private void move() {
            if (tP.x != -1 && tP.y != -1) {
                if (uP.x == tP.x) {
                    if (uP.y < tP.y)
                        uP.y += speed;
                    else if (uP.y > tP.y)
                        uP.y += -speed;
                } else if (uP.y == tP.y) {
                    if (uP.x < tP.x)
                        uP.x += speed;
                    else if (uP.x > tP.x)
                        uP.x += -speed;
                } else if (uP.x < tP.x) {
                    uP.x += speed;
                    if (uP.y < tP.y) {
                        uP.y += speed;
                    } else if (uP.y > tP.y) {
                        uP.y += -speed;
                    }
                } else if (uP.x > tP.x) {
                    uP.x += -speed;
                    if (uP.y < tP.y) {
                        uP.y += speed;
                    } else if (uP.y > tP.y) {
                        uP.y += -speed;
                    }
                }
                if (tP.x - speed < uP.x && uP.x < tP.x + speed)
                    uP.x = tP.x;
                if (tP.y - speed < uP.y && uP.y < tP.y + speed)
                    uP.y = tP.y;
            }
        }
    }

    private ManagerPoolMissile mpm;

    private ManagerRandom mr;

    private Queue<BeanMissile> deckQue;

    private ArrayList<BeanMissile> fieldList;

    private Player p;

    private int missileRadius;

    private int missileSpeed;

    private int level;

    private boolean swt;

    private boolean explosion;

    private int expI, expJ;

    private Rect src, dst;

    private int frame;

    private MediaPlayer mPlayer;

    private boolean onMusic;

    private SoundPool sp;

    private int track[];

    private Vibrator vib;

    private Msg MSG;

    private Point size;

    /**
     * ImplementsView's Constructor
     *
     * @param context
     * @param
     * @Comment :
     */

    public ImplementsView(Context context, Point size) {
        // 생성자 두번쨰 파라미터 수정요망
        super(context);
        Log.d("Space",  String.valueOf(R.drawable.class.getDeclaredFields().length));
        MSG = Msg.getInstance("msg.messages");
        mPlayer = MediaPlayer.create(context, R.raw.bensound_elevate);
        mPlayer.setVolume(0.6f, 0.6f);
        mPlayer.setLooping(true);
        vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        this.size = size;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
       super.surfaceCreated(holder);
       init();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.android.gehemengine.MainView#mainLoop()
     */
    protected void init() {
        mpm = ManagerPoolMissile.getInstance();
        mpm.setMissiles();
        frame = 0;
        mr = ManagerRandom.getInstance();
        deckQue = new LinkedList<BeanMissile>();
        fieldList = new ArrayList<BeanMissile>();
        p = new Player(sz.HalfW, sz.HalfH, ig.getBitmapWidth("body_00_paras")/2);
        missileRadius = ig.getBitmapWidth("scene_02_play_missile_small")/2;
        missileSpeed = 10;
        level=0;
        setMissile();
        swt=true;
        explosion=false;
        expI=expJ=0;
        src = new Rect();
        dst = new Rect(p.uP.x-ig.getBitmapWidth("body_00_paras"),p.uP.y-ig.getBitmapHeight("body_00_paras")
                ,p.uP.x+ig.getBitmapWidth("body_00_paras"),p.uP.y+ig.getBitmapHeight("body_00_paras"));
        onMusic = false;
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        track = new int[5];
        track[0] = sp.load(getContext(),R.raw.explosion,1);
        track[1] = sp.load(getContext(),R.raw.drop_spell,2);
    }

    @Override
    public void mainLoop() {
        if(!onMusic) {mPlayer.start();onMusic=true;}
        process();
        draw();
    }

    @Override
    public void dispose() {
        mPlayer.stop();
    }


    private void process() {
        p.move();
        if(swt) {
            if(frame%(mr.randFromOne(30))==0 && !deckQue.isEmpty())
                fire();
            checkMissile();
        }
        if(deckQue.isEmpty())
            setMissile();
        p.time = frame/30;
        p.total = p.time + p.score;
        if(p.total>level+100) { missileSpeed+=2; level+=100;}
        if(swt)frame++;
    }
    private void fire() {
        BeanMissile bm=deckQue.poll();
        bm.setArrow(opArrow(p.uP,bm.getP(),bm.getSpeed()));
        fieldList.add(bm);
    }
    private void checkMissile() {
        BeanMissile bm;
        int deltaX,deltaY;
        double disSquared,rSquared;
        for(int i=0; i<fieldList.size(); i++) {
            bm = fieldList.get(i);
            deltaX = (p.uP.x - bm.getP().x);
            deltaY = (p.uP.y - bm.getP().y);
            disSquared =  Math.pow(deltaX,2) + Math.pow(deltaY,2);
            rSquared = Math.pow(p.radius + bm.getRadius(),2);
            if(disSquared < rSquared) {
                Log.d(TAG,"check!!");
                fieldList.remove(i);
                if(bm.getImgFileName().equals("scene_02_play_missile_middle"))
                    p.life-=3;
                else if(bm.getImgFileName().equals("scene_02_play_missile_small_paras")) {
                    p.life++;
                    sp.play(track[1],1f,1f,0,0,1f);
                    continue;
                }
                else
                    p.life--;
                if(explosion) {expI=expJ=0;}
                sp.play(track[0],1f,1f,0,0,1f);
                vib.vibrate(100);
                explosion=true;
                if(p.life<1) {swt=false;mPlayer.pause();}
            }
            else if(bm.getP().x < 0 || bm.getP().y < 0 ||
                    bm.getP().x > sz.WIDTH || bm.getP().y > sz.HEIGHT) {
                p.score++;
                mpm.releaseMissile(fieldList.remove(i));
            }
            else {
                Point p = bm.getP();
                p.offset(bm.getArrow().x, bm.getArrow().y);
            }
        }
    }
    private Point opArrow(Point uP, Point bmP, int speed) {
        double degree = Math.atan2(p.uP.y - bmP.y, p.uP.x - bmP.x);
        return new Point((int)(speed*Math.cos(degree)),(int)(speed*Math.sin(degree)));
    }

    private void setMissile() {
        mpm.setMissiles();
        BeanMissile bm;
        while(!mpm.isEmpty()) {
            bm = mpm.getMissile();
            bm.setSpeed(mr.randFromAny(missileSpeed, missileSpeed/3));
            bm.setRadius(missileRadius);
            switch (mr.randWithZero(4)) {
                case 0:// east
                    bm.setP(sz.WIDTH, mr.randWithZero(sz.HEIGHT));
                    break;
                case 1:// north
                    bm.setP(mr.randWithZero(sz.WIDTH), sz.HEIGHT);
                    break;
                case 2:// west
                    bm.setP(0, mr.randWithZero(sz.HEIGHT));
                    break;
                case 3:// south
                    bm.setP(mr.randWithZero(sz.WIDTH), 0);
                    break;
                default:
                    break;
            }
            deckQue.offer(bm);
        }
    }

    private void draw() {
        // draw Image
        g.backGround();

        Bitmap bitmap = ig.getBitmap("scene_02_play_ui_background");
        Rect src = new Rect(0,0,bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(0,0,size.x, size.y);

        g.drawBitmap(ig.getBitmap("scene_02_play_ui_background"), src, dst);
        g.drawBitmap(ig.getBitmap("body_00_paras"), p.uP.x - p.radius, p.uP.y - p.radius);
        for (BeanMissile bm : fieldList)
            g.drawBitmap(ig.getBitmap(bm.getImgFileName()),
                    bm.getP().x - bm.getRadius(), bm.getP().y - bm.getRadius());
        if(explosion) {

            int w,h;
            w=expJ*96;h=expI*96;
            src.set(w, h, w+96, h+96);
            dst.set(p.uP.x-ig.getBitmapWidth("body_00_paras"),p.uP.y-ig.getBitmapHeight("body_00_paras")
                    ,p.uP.x+ig.getBitmapWidth("body_00_paras"),p.uP.y+ig.getBitmapHeight("body_00_paras"));
            g.drawBitmap(ig.getBitmap("scene_02_play_ani_explosion"), src, dst);
            expJ+=2;
            if(expJ>5) {expI++;expJ=0;}
            if(expI>3) {explosion=false;expI=expJ=0;}
        }


        // draw String
        g.setFontType(1);
        g.setFontSize(p.fontSize);
        g.setColor(Color.WHITE);
        p.strBuff.append(MSG.getStr("str1")+" "+p.score+" + "+MSG.getStr("str2")+" "+p.time+" = "
                +MSG.getStr("str3")+" "+p.total);
        g.drawString(p.strBuff.toString(),0,p.fontHeight);
        p.strBuff.setLength(0);
        p.strBuff.append(MSG.getStr("str4")+" "+p.life);
        g.drawString(p.strBuff.toString(),0,p.fontHeight*2);
        p.strBuff.setLength(0);
        if(!swt) {
            g.setFontType(2);
            g.setFontSize(p.fontSize*2);
            g.drawString(MSG.getStr("str5"),sz.HalfW-g.stringWidth(MSG.getStr("str5"))/2,sz.HalfH);
            p.strBuff.append(MSG.getStr("str3")+" = "+p.total);
            g.drawString(p.strBuff.toString(),sz.HalfW-g.stringWidth(p.strBuff.toString())/2,sz.HalfH+g.stringHeight());
            p.strBuff.setLength(0);
        }
    }

    private int dipToInt(float number) {
        int num = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, number,
                getResources().getDisplayMetrics());
        return num;
    }
}
