package com.nou.scd.dao;

import com.nou.DAO;
import com.acer.db.DBManager;
import com.acer.util.DateUtil;
import com.acer.util.Utility;
import java.sql.Connection;
import java.util.Hashtable;
import javax.servlet.http.HttpSession;

public class SCDT021DAO extends DAO
{
    private SCDT021DAO(){
        this.columnAry    =    new String[]{"AYEAR","SMS","STNO","KIND","RANK","CENTER_CODE","CRSNO","AWARD_NO","ASYS"};
    }

    public SCDT021DAO(DBManager dbManager, Connection conn)
    {
        this();
        this.dbManager    =    dbManager;
        this.conn    =    conn;
        this.tableName    =    "SCDT021";
    }

    public SCDT021DAO(DBManager dbManager, Connection conn, String USER_ID) throws Exception
    {
        this();
        this.dbManager    =    dbManager;
        this.conn    =    conn;
        this.tableName    =    "SCDT021";
    }

    public SCDT021DAO(DBManager dbManager, Connection conn, Hashtable requestMap, HttpSession session) throws Exception
    {
        this();
        this.dbManager    =    dbManager;
        this.conn    =    conn;
        this.tableName    =    "SCDT021";
        for (int i = 0; i < this.columnAry.length; i++)
        {
            if (requestMap.get(this.columnAry[i]) != null)
                this.columnMap.put(this.columnAry[i], Utility.dbStr(requestMap.get(this.columnAry[i])));
        }
    }

    public SCDT021DAO(DBManager dbManager, Connection conn, Hashtable requestMap, String USER_ID) throws Exception
    {
        this();
        this.dbManager    =    dbManager;
        this.conn    =    conn;
        this.tableName    =    "SCDT021";
        for (int i = 0; i < this.columnAry.length; i++)
        {
            if (requestMap.get(this.columnAry[i]) != null)
                this.columnMap.put(this.columnAry[i], Utility.dbStr(requestMap.get(this.columnAry[i])));
        }
    }
    
    public void setAYEAR(String AYEAR)
    {
        this.columnMap.put ("AYEAR", AYEAR);
    }

    public void setSMS(String SMS)
    {
        this.columnMap.put ("SMS", SMS);
    }

    public void setSTNO(String STNO)
    {
        this.columnMap.put ("STNO", STNO);
    }

    public void setKIND(String KIND)
    {
        this.columnMap.put ("KIND", KIND);
    }

    public void setRANK(String RANK)
    {
        this.columnMap.put ("RANK", RANK);
    }

    public void setCENTER_CODE(String CENTER_CODE)
    {
        this.columnMap.put ("CENTER_CODE", CENTER_CODE);
    }

    public void setCRSNO(String CRSNO)
    {
        this.columnMap.put ("CRSNO", CRSNO);
    }

    public void setAWARD_NO(String AWARD_NO)
    {
        this.columnMap.put ("AWARD_NO", AWARD_NO);
    }

    public void setASYS(String ASYS)
    {
        this.columnMap.put ("ASYS", ASYS);
    }

}
