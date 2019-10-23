package com.teamgehem.gehemengine.file.abst;

import java.io.IOException;
import java.io.InputStream;

public interface IntfFileIO
{
    public InputStream read(String fileName) throws IOException;
    
}// interface
