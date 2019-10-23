package com.teamgehem.gehemengine.msg;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

// TODO: Auto-generated Javadoc
/**
 * The Class Msg.
 */
public class Msg {
    
    private static final String PATH = "com.teamgehem.gehemengine.msg.messages";
    
    /** The instance. */
    private static Msg instance=null;

    /** The RESOURC e_ bundle. */
    private ResourceBundle RESOURCE_BUNDLE=null; 
    
    private StringBuilder sb=null;

    /**
     * Instantiates a new msg.
     *
     * @param path the path
     */
    private Msg(String path) {
        RESOURCE_BUNDLE = ResourceBundle.getBundle(path);
        sb = new StringBuilder();
    }
    
    /**
     * Gets the single instance of Msg.
     *
     * @return single instance of Msg
     */
    public static Msg getInstance() {
        synchronized (Msg.class)
        {
            if(instance==null){
                instance = new Msg(PATH);
            }
        }
        return instance;
    }
    
    /**
     * Gets the single instance of Msg.
     *
     * @param path the path
     * @return single instance of Msg
     */
    public static Msg getInstance(String path) {
        synchronized (Msg.class)
        {
            if(instance==null){
                instance = new Msg(path);
            }
        }
        return instance;
    }
    
    /**
     * Gets the str.
     *
     * @param key the key
     * @return the str
     */
    @SuppressWarnings("finally")
    public String getStr(String ... key) {
        sb.setLength(0);
        try {
            for(String str : key){
                str=RESOURCE_BUNDLE.getString(str);
                sb.append(str);
            }
        } catch (MissingResourceException e) {
            for(String str : key)
                sb.append(str).append('!');
        }finally{
            return sb.toString();
        }
    }
}//class
