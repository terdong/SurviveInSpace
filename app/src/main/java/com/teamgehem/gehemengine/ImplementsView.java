package com.teamgehem.gehemengine;

import android.content.Context;

import com.teamgehem.gehemengine.util.ImageGroup;

// TODO: Auto-generated Javadoc
/**
 * <pre>
 * MainView를 상속받은 예제 Class.
 * Class        :	ImplementsView
 * FileName     :	ImplementsView.java
 * Package      :	com.teamgehem.gehemengine
 * Date         :	2011. 5. 11 오전 4:13:51
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:       1.0
 */
class ImplementsView extends GehemView {

    /**
     * Instantiates a new implements view.
     *
     * @param context the context
     */
    public ImplementsView(Context context, ImageGroup ig) {
        super(context,ig);
        scene = GehemView.S_PLAY;
    }
    

    /**
     * <PRE>
     * Override mainLoop 
     * </PRE>
     * @see GehemView#mainLoop()
     */
    @Override
    public void mainLoop() {
        switch (scene) {
            case S_PLAY:
                
                g.drawBitmap(ig.getBitmap("icon"));
                break;
            default:
                break;
        }
    }


    @Override
    public void dispose()
    {
    }
}// class

