package com.teamgehem.gehemengine.provider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class GehemDBHelper extends SQLiteOpenHelper
{
    private final Context context;
    
    private SQLiteDatabase sqdb;
    
    private GehemDBPath gdbp;
    
    public GehemDBHelper(Context context, GehemDBPath gdbp)
    {
        super(context, gdbp.getDbName() , null, 1);
        this.context = context;
        this.gdbp = gdbp;
    }
    
    public void createDatabase() {
        try
        {
            if(!checkDatabase()){
                this.getWritableDatabase();
                try{
                    copyDatabase();
                }catch(IOException e)
                {throw new Error("Error copying database from system assets");}
            }
        }
        catch (Exception e)
        {throw new Error("Failed to create database: "+ gdbp.getDbName()); }
    }
    
    private boolean checkDatabase(){
        SQLiteDatabase checkableDatabase = null;
        try{
            checkableDatabase = SQLiteDatabase.openDatabase(gdbp.getDbFullPath(), null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){}
        if(checkableDatabase !=null )
            checkableDatabase.close();
        return checkableDatabase != null ? true : false;
    }
    
    private void copyDatabase() throws IOException{
        InputStream is = context.getAssets().open(gdbp.getDbAssetPath());
        OutputStream os = new FileOutputStream(gdbp.getDbFullPath());
        
        long fileSize = is.available();
        byte[] buffer = new byte[(int)fileSize];
        is.read(buffer);
        os.write(buffer);
        
        os.flush();
        os.close();
        is.close();
    }
    
    public void openDatabase() throws SQLException{
        sqdb = SQLiteDatabase.openDatabase(gdbp.getDbFullPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }
    
    @Override
    public synchronized void close()
    {
        if(sqdb != null)
            sqdb.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase arg0)
    {
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2)
    {
    }

}// class
