package com.teamgehem.gehemengine.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public abstract class GehemDBHandler
{
    private static final String sql = "select count(*) from ";
    
    protected static final int RETURN_ERROR = -1;
    
    private GehemDBHelper helper;
    
    protected SQLiteDatabase db;
    protected SQLiteDatabase dbRead;
    
    protected StringBuilder sb;
    
    protected Cursor cursor=null;
    
    protected GehemDBHandler(Context context, GehemDBPath gdbp)
    {
        this.helper = new GehemDBHelper(context, gdbp);
        helper.createDatabase();
        helper.openDatabase();
        db = helper.getWritableDatabase();
        dbRead = helper.getReadableDatabase();
        sb = new StringBuilder();
    }
    
    public void close(){
        if(cursor!=null) {
            cursor.close();
            cursor = null;
        }
        db.close();
        dbRead.close();
        helper.close();
    }
    
    /**
     * Gets the row count in table.
     *
     * @param table the table
     * @return the row count
     */
    public Cursor getRowCount(String table){
        sb.append(sql).append(table);
        try
        {
            if((cursor=getCursorRawQuery(sb))!=null)
                return cursor;
        }
        catch (SQLiteException e){}
        return null;
    }
    
    /**
     * Gets the row count in table where.
     *
     * @param table the table
     * @param where the where
     * @return the row count
     */
    public Cursor getRowCount(String table, String where, String [] args){
        sb.append(sql).append(table).append(" ").append(where);
        try
        {
            if((cursor=getCursorRawQuery(sb,args))!=null)
                return cursor;
        }
        catch (SQLiteException e){}
        return null;
    }
    
    public Cursor getCursorRawQuery(CharSequence cs) {
        try
        {
            cursor = dbRead.rawQuery(cs.toString(),null);
            cursor.moveToFirst();
        }
        catch (SQLiteException e){}
        return cursor;
    }
    
    public Cursor getCursorRawQuery(CharSequence cs, String [] args){
        try
        {
            
            cursor = dbRead.rawQuery(cs.toString(), args);
            cursor.moveToFirst();
        }
        catch (SQLiteException e){}
        return cursor;
    }
    
    
//    public Cursor select(int id) throws SQLExceptioin{
//        Cursor cursor = db.query(true,
//    }
//    
    
}//class














