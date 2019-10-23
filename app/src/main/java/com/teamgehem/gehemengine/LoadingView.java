package com.teamgehem.gehemengine;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.SurfaceHolder;

import com.teamgehem.gehemengine.util.Graphics;

public class LoadingView extends GehemView
{
    protected static final String TAG = "LoadingView";
    
    /** The MSG. */
//    protected Msg MSG = Msg.getInstance();
    
    /** The logo. */
    protected Bitmap mBitmap[];
    
    /** The arrow. */
    private int arrow=0;
    
    /** The 임시변수 */
    protected int temp1,temp2;
    
    private String version = "1.2";
    
    protected LoadingView(Context context)
    {
        super(context);
        // 패키지내 게헴로고를 가지고 오기 위한 작업
        initClass();
    }
    
    protected void initClass()
    {
        mBitmap = new Bitmap[2];
        InputStream is;
//        is = GehemView.class.getResourceAsStream(MSG.getStr("path.img.company"));
        is = GehemView.class.getResourceAsStream("com.teamgehem.gehemengine.scene_00_company.png");
        mBitmap[0] = BitmapFactory.decodeStream(is);
//        is = GehemView.class.getResourceAsStream(MSG.getStr("path.img.logo"));
        is = GehemView.class.getResourceAsStream("com.teamgehem.gehemengine.scene_00_logo.png");
        mBitmap[1] = BitmapFactory.decodeStream(is);
//        try{is.close();}catch (IOException e){Log.d(TAG,e.getMessage());}
        is = null;
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        super.surfaceCreated(holder);
        temp1 = sz.HEIGHT;
        temp2 = 0;
    }
    @Override
    public void mainLoop()
    {
        sLogo();
    }
    /**
     * GehemEngine 라이센스 로고 구현 method.
     */
    private void sLogo() {
        g.drawBitmap(mBitmap[0],
                sz.HalfW-(mBitmap[0].getWidth()/2),temp1);
        g.drawBitmap(mBitmap[1],
                sz.HalfW-(mBitmap[1].getHeight()/2),temp2);
        g.setFontType(Graphics.FONT_SOYA_DADUM);
        g.setColor(Color.WHITE);
        g.setFontSize(20F);
        g.drawString(version, sz.HalfW, sz.HEIGHT);
        if(arrow<=150)
            temp1=sz.HEIGHT-(arrow+5);
        if(arrow<=60)
            temp2=arrow+5;
        else if(arrow>=250)
            g.fadeOut();
        if(arrow++>=400) {
            scene++;
            g.backGround();
        }
    }

    @Override
    public void dispose()
    {
        
    }
    
}
