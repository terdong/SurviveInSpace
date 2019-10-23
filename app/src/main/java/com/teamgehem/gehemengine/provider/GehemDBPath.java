package com.teamgehem.gehemengine.provider;

/**
 * <pre>
 * db 경로를 저장하기 위한 Bean.
 * Class        :	GehemDBPath
 * FileName     :	GehemDBPath.java
 * Package      :	com.teamgehem.provider
 * Date         :	2011. 6. 17 오전 3:06:14
 * </pre>
 *
 * @author	:	Gehem_um
 * @version	:
 */
public class GehemDBPath
{
    private String dbPath;
    private String dbDir;
    private String dbName;
    private String dbFullPath;
    public GehemDBPath(String dbPath, String dbDir, String dbName)
    {
        this.dbPath = dbPath;
        this.dbDir = dbDir;
        this.dbName = dbName;
        this.dbFullPath = dbPath+dbDir+dbName;
    }
    public String getDbPath()
    {
        return dbPath;
    }
    public String getDbDir()
    {
        return dbDir;
    }
    public String getDbName()
    {
        return dbName;
    }
    public String getDbFullPath(){
        return dbFullPath;
    }
    public String getDbAssetPath(){
        return dbDir+dbName;
    }
    
}//class
