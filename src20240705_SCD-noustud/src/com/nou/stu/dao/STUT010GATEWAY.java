package com.nou.stu.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Vector;

import com.acer.apps.Page;
import com.acer.db.DBManager;
import com.acer.db.query.DBResult;
import com.acer.util.Utility;
import com.nou.UtilityX;
import com.nou.sys.SYSGETSMSDATA;

/*
 * (STUT010) Gateway/*
 *-------------------------------------------------------------------------------*
 * Author    : 國長      2007/05/04
 * Modification Log :
 * Vers     Date           By             Notes
 *--------- -------------- -------------- ----------------------------------------
 * V0.0.1   2007/05/04     國長           建立程式
 *                                        新增 getStut010ForUse(Hashtable ht)
 * V0.0.1   2007/06/04     poto           建立程式
 *                                        新增getPassQcrsId(Vector vt,Vector VtQcrs,Vector VtPass,String ASYS,String AYEAR,String SMS,String STNO)
 * V0.0.2	2007/07/28		north	建立程式		getDataToResult2FormForSCD021M_2(Hashtable requestMap)
 * V0.0.3	2007/08/07		north	建立程式		getMarkDataToPrintForSCD207R(Hashtable requestMap)
 * V0.0.4	2007/08/07		poto	建立程式        getScd206rSubPrint(String STNO, Vector vt)
 * V0.0.5	2007/08/30		poto	建立程式        getStu009mQuery(Hashtable requestMap)
 *--------------------------------------------------------------------------------
 */
public class STUT010GATEWAY {

    /** 資料排序方式 */
    private String orderBy = "";
    private DBManager dbmanager = null;
    private Connection conn = null;
    /* 頁數 */
    private int pageNo = 0;
    /** 每頁筆數 */
    private int pageSize = 0;

    /** 記錄是否分頁 */
    private boolean pageQuery = false;

    /** 用來存放 SQL 語法的物件 */
    private StringBuffer sql = new StringBuffer();

    /** <pre>
     *  設定資料排序方式.
     *  Ex: "AYEAR, SMS DESC"
     *      先以 AYEAR 排序再以 SMS 倒序序排序
     *  </pre>
     */
    public void setOrderBy(String orderBy) {
        if(orderBy == null) {
            orderBy = "";
        }
        this.orderBy = orderBy;
    }

    /** 取得總筆數 */
    public int getTotalRowCount() {
        return Page.getTotalRowCount();
    }

    /** 不允許建立空的物件 */
    private STUT010GATEWAY() {}

    /** 建構子，查詢全部資料用 */
    public STUT010GATEWAY(DBManager dbmanager, Connection conn) {
        this.dbmanager = dbmanager;
        this.conn = conn;
    }

    /** 建構子，查詢分頁資料用 */
    public STUT010GATEWAY(DBManager dbmanager, Connection conn, int pageNo, int pageSize) {
        this.dbmanager = dbmanager;
        this.conn = conn;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        pageQuery = true;
    }

    /**
     *
     * @param ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     *         若該欄位有中文名稱，則其 KEY 請加上 _NAME, EX: SMS 其中文欄位請設為 SMS_NAME
     * @throws Exception
     */
    public Vector getStut010ForUse(Hashtable ht) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        sql.append(
            "SELECT S10.IDNO, S10.BIRTHDATE, S10.CRSNO, S10.AYEAR, S10.SMS, S10.ASYS, S10.STNO, S10.MARK, S10.CRD, S10.GET_MANNER, S10.RMK, S10.UPD_DATE, S10.UPD_TIME, S10.UPD_USER_ID, S10.ROWSTAMP " +
            "FROM STUT010 S10 " +
            "WHERE 1 = 1 "
        );
        if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
            sql.append("AND S10.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("BIRTHDATE")).equals("")) {
            sql.append("AND S10.BIRTHDATE = '" + Utility.nullToSpace(ht.get("BIRTHDATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CRSNO")).equals("")) {
            sql.append("AND S10.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
            sql.append("AND S10.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
            sql.append("AND S10.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("ASYS")).equals("")) {
            sql.append("AND S10.ASYS = '" + Utility.nullToSpace(ht.get("ASYS")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            sql.append("AND S10.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("MARK")).equals("")) {
            sql.append("AND S10.MARK = '" + Utility.nullToSpace(ht.get("MARK")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("CRD")).equals("")) {
            sql.append("AND S10.CRD = '" + Utility.nullToSpace(ht.get("CRD")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("GET_MANNER")).equals("")) {
            sql.append("AND S10.GET_MANNER = '" + Utility.nullToSpace(ht.get("GET_MANNER")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("RMK")).equals("")) {
            sql.append("AND S10.RMK = '" + Utility.nullToSpace(ht.get("RMK")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("UPD_DATE")).equals("")) {
            sql.append("AND S10.UPD_DATE = '" + Utility.nullToSpace(ht.get("UPD_DATE")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("UPD_TIME")).equals("")) {
            sql.append("AND S10.UPD_TIME = '" + Utility.nullToSpace(ht.get("UPD_TIME")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("UPD_USER_ID")).equals("")) {
            sql.append("AND S10.UPD_USER_ID = '" + Utility.nullToSpace(ht.get("UPD_USER_ID")) + "' ");
        }
        if(!Utility.nullToSpace(ht.get("ROWSTAMP")).equals("")) {
            sql.append("AND S10.ROWSTAMP = '" + Utility.nullToSpace(ht.get("ROWSTAMP")) + "' ");
        }

        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "S10." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                    rowHt.put(rs.getColumnName(i), rs.getString(i));

                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

  /**
     * 學分抵免管理子系統  學分銀行資料(STUT010)
     * @param vt 回傳值
     * @param ASYS 學制代碼
     * @param SMS 學期代碼
     * @param STNO 學號代碼
     * @param AYEAR 學年代碼
     * @param  VtQcrs 已修
     * @param  VtPass 已抵
     * @throws Exception
     */
   public void getPassQcrsId(Vector vt,Vector VtQcrs,Vector VtPass,String ASYS,String AYEAR,String SMS,String STNO) throws Exception {

		String SQL="";
		String SQL_APP_SEQ="";
		String SQL_NAME="";
		
		String SQL_SMS_NAME="";
		String SQL_APPRV="";
		
	    String QData="";
		String PData="";
        Vector crs_no=new Vector();

        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        int count=VtPass.size();

		for (int j = 0; j < VtPass.size(); j++){
			if(j==VtPass.size()-1&&VtQcrs.size()==0){
				PData=PData+"'"+((Hashtable)VtPass.get(j)).get("ID").toString()+"'";
			}else{
				PData=PData+"'"+((Hashtable)VtPass.get(j)).get("ID").toString()+"'"+",";
			}
			crs_no.add(j,((Hashtable)VtPass.get(j)).get("APP_SEQ").toString());
		}

		for (int i = 0; i < VtQcrs.size(); i++){

			if(i==VtQcrs.size()-1){
				QData=QData+"'"+((Hashtable)VtQcrs.get(i)).get("ID").toString()+"'";
			}else{
				QData=QData+"'"+((Hashtable)VtQcrs.get(i)).get("ID").toString()+"'"+",";
			}
			crs_no.add(i+count,((Hashtable)VtQcrs.get(i)).get("APP_SEQ").toString());
		}

		SQL_APP_SEQ="SELECT  CCST012.APP_SEQ  FROM CCST012 WHERE 1=1 "+
					"AND CCST012.STNO = STUT010.STNO "+
					"AND CCST012.CRSNO=STUT010.CRSNO "+
					"AND CCST012.AYEAR=STUT010.AYEAR "+
					"AND CCST012.SMS=STUT010.SMS "+
					"AND ROWNUM =1";

		SQL_NAME =  "SELECT  STUT002.NAME  FROM STUT003,STUT002 WHERE 1=1  "+
					"AND STUT003.STNO = STUT010.STNO  "+
					"AND STUT003.IDNO = STUT002.IDNO   "+
					"AND ROWNUM =1 ";

		

	

		SQL_SMS_NAME="SELECT  CODE_NAME from SYST001  WHERE KIND='SMS' AND  SYST001.code=STUT010.SMS ";

		SQL_APPRV =	"SELECT CCST001.APPRV_BASEDATE FROM CCST001 WHERE  1=1 "+
					"AND CCST001.ASYS=STUT010.ASYS "+
					"AND CCST001.AYEAR=STUT010.AYEAR "+
					"AND CCST001.SMS=STUT010.SMS";

			   SQL ="SELECT "+
					"IDNO AS IDNO, "+
					"BIRTHDATE AS BIRTHDATE, "+
					"("+SQL_APP_SEQ+") AS APP_SEQ, "+
					"STUT010.STNO AS STNO, "+
					"("+SQL_NAME+") AS ST_NAME , "+
					"(case when (select crsno from cout010 where cout010.crsno_old = stut010.crsno) is null then (select stut010.crsno from dual) else (select crsno from cout010 where cout010.crsno_old = stut010.crsno) end) as crsno, "+
				    "(select crs_name from cout002 where crsno in (case when (select crsno from cout010 where cout010.crsno_old = stut010.crsno) is null then (select stut010.crsno from dual) else (select crsno from cout010 where cout010.crsno_old = stut010.crsno) end)) AS crs_name, "+
					"(STUT010.AYEAR||( "+SQL_SMS_NAME+")) AS  AYEAR, "+
					"STUT010.CRSNO AS CRS_ENG, "+
					" DECODE(STUT010.GET_MANNER,'1','已修及格','2','已抵免','') AS CRD_TYPE, "+
					"("+SQL_APPRV+")  AS CRD_DATA  "+
					"FROM  STUT010 ";

        if(VtQcrs.size()==0&&VtPass.size()==0){
			SQL =SQL+"WHERE ROWID ='' ";
		}else{
			SQL =SQL+"WHERE ROWID IN("+PData+QData+") ";
		}
		System.out.println("getPassQcrsId:"+SQL);
		sql.append(SQL);
        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "C11." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
				rs.executeQuery(sql.toString());
            }

            Hashtable rowHt = null;
            int a= 0;
            while (rs.next()) {
				rowHt = new Hashtable();
                /** 將欄位抄一份過去 */

				for (int i = 1; i <= rs.getColumnCount(); i++){
				    if(i==3){
				        rowHt.put(rs.getColumnName(i),crs_no.get(a).toString());
                    }else if(i==11){
                        if("".equals(rs.getString(i))||rs.getString(i)==null ){
                           rowHt.put(rs.getColumnName(i), "");
                        }else{
                           rowHt.put(rs.getColumnName(i), rs.getString(i).substring(0,4)+"/"+rs.getString(i).substring(4,6)+"/"+rs.getString(i).substring(6,8));
                        }

                    }else{
                     	rowHt.put(rs.getColumnName(i), rs.getString(i));
                    }


					//System.out.println("第"+i+"筆"+rs.getColumnName(i)+"===="+rs.getString(i));
				}
                    a++;
					vt.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }

    }

	/**
	 * north 2007/7/28 查詢歷年成績(CCST011)
	 *
	 * @param vt -
	 *            回傳值, 內容為學生修習成績結果
	 * @param ht -
	 *            條件值,前端的查詢條件
	 * @throws Exception
	 *
	 */
	public Vector getDataToResult2FormForSCD021M_2(Hashtable requestMap) throws Exception {
		Vector result = new Vector();

		// 子查詢部分,ex:中心代碼-->帶出所對應的中文
		// 學期
		StringBuffer sb1 = new StringBuffer();

		sb1.append("SELECT CODE, CODE_NAME " + "FROM SYST001 " + "WHERE "
				+ "KIND = 'SMS' ");

		// 將 SQL 清空
		if (sql.length() > 0)
			sql.delete(0, sql.length());

		sql.append("SELECT DISTINCT STUT010.AYEAR, STUT010.ASYS, STUT010.CRSNO, STUT010.CRD, STUT010.MARK, "
				+ "STUT010.RMK, STUT010.STNO, COUT002.CRS_NAME, "
				+ "A.CODE_NAME AS SMS_NAME, B.FACULTY_NAME AS OPEN_FACULTY_NAME, C.FACULTY_NAME AS USE_FACULTY_NAME "
				+ "FROM STUT010, COUT002, "
				//學期
				+ "("+sb1+") A, "
				//開設學系名稱
				+ "(SELECT FACULTY_CODE,FACULTY_NAME FROM SYST003) B, "
				//採計學系名稱
				+ "(SELECT FACULTY_CODE,FACULTY_NAME FROM SYST003) C WHERE "
				+ "STUT010.SMS=A.CODE(+) AND "
				+ "STUT010.CRSNO=COUT002.CRSNO AND "
				+ "STUT010.STNO='"+requestMap.get("STNO")+"' AND "
				+ "STUT010.GET_MANNER='1' ORDER BY STUT010.AYEAR, STUT010.CRSNO");

		System.out.println("getDataToResult2FormForSCD021M_2:"+sql);

		DBResult rs = null;
		try {
			if (pageQuery) {
				// 依分頁取出資料
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(),
						pageNo, pageSize);
			} else {
				// 取出所有資料
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

			//用來暫時存放查詢出來的結果
			Vector temp = new Vector();
			while (rs.next()) {
				Hashtable content = new Hashtable();
				 /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++)
                	content.put(rs.getColumnName(i), rs.getString(i));
                temp.add(content);
			}

			//開始處理學分合計,平均的部分
			for(int i=0; i<temp.size(); i++){
				//有關總學分數與平均
				int totalMark = 0;
				int totalScroe = 0;
				String CRD = (String)((Hashtable)temp.get(i)).get("CRD");
				String MARK = (String)((Hashtable)temp.get(i)).get("MARK");
				if(CRD!=null && CRD.trim().length()!=0){
					totalMark = Integer.parseInt((String)((Hashtable)temp.get(i)).get("CRD"));
					if(MARK!=null && MARK.trim().length()!=0)
						totalScroe = Integer.parseInt(MARK)*Integer.parseInt(CRD);
				}

				double averageScore = 0.0;
				//計算該學年度共幾筆資料
				int count=0;
				String ayear = (String)((Hashtable)temp.get(i)).get("AYEAR");
				if(i!=temp.size()-1){
					while(ayear.equals((String)((Hashtable)temp.get(i+1)).get("AYEAR"))){
						//計算總學分
						String crdTemp = (String)((Hashtable)temp.get(i+1)).get("CRD");
						if(crdTemp==null || crdTemp.trim().length()==0)
							crdTemp="0";
						totalMark += Integer.parseInt(crdTemp);
						//求得總分權數,以備平均之用
						String markTemp = (String)((Hashtable)temp.get(i+1)).get("MARK");
						if(markTemp==null || markTemp.trim().length()==0)
							markTemp="0";
						totalScroe += Integer.parseInt(markTemp)*Integer.parseInt(crdTemp);
						count++;
						if(i+count==temp.size()-1)
							break;
					}
				}

				//求出平均,精確計算四捨五入至第二位
				if(totalMark!=0){
					BigDecimal b1 = new BigDecimal(Double.toString(totalScroe));
					BigDecimal b2 = new BigDecimal(Double.toString(totalMark));
					averageScore = b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
				}

				//學年起訖年月
				if(ayear.charAt(0)=='0')
					ayear = ayear.substring(1);
				String AYEAR_START_AND_END = ayear+"年8月至"+(Integer.parseInt(ayear)+1)+"年7月";

				//學年期
				String SMS_NAME = (String)((Hashtable)temp.get(i)).get("SMS_NAME");
				String smsTemp = "";
				if(SMS_NAME!=null && SMS_NAME.trim().length()!=0)
					smsTemp = SMS_NAME.substring(0,1);
				String yearAndSms = ayear+smsTemp;

				/** 放入前端所欲顯示的資料 */
				//先放第一筆
				Hashtable content1 = new Hashtable();

				//學年起訖年月
				content1.put("AYEAR_START_AND_END", AYEAR_START_AND_END);

				//學年期
				content1.put("YEAR_SMS", yearAndSms);

				//總學分數
				content1.put("TOTAL_CRD", new Integer(totalMark).toString());

				//平均成績
				content1.put("AVERAGE_MARK", new Double(averageScore).toString());

				//科目代號
				content1.put("CRSNO", (String)((Hashtable)temp.get(i)).get("CRSNO"));

				//科目名稱
				String CRS_NAME = (String)((Hashtable)temp.get(i)).get("CRS_NAME");
				if(CRS_NAME==null || CRS_NAME.trim().length()==0)
					CRS_NAME = "";
				content1.put("CRS_NAME", CRS_NAME);

				//學分數
				if(CRD==null || CRD.trim().length()==0)
					CRS_NAME = "0";
				content1.put("CRD", CRD);

				//成績
				if(MARK==null || MARK.trim().length()==0)
					MARK = "0";
				content1.put("MARK", (String)((Hashtable)temp.get(i)).get("MARK"));

				//開設學系(科)及學類
				String OPEN_FACULTY_NAME = (String)((Hashtable)temp.get(i)).get("OPEN_FACULTY_NAME");
				if(OPEN_FACULTY_NAME==null || OPEN_FACULTY_NAME.trim().length()==0)
					OPEN_FACULTY_NAME = "";
				content1.put("OPEN_FACULTY_NAME", OPEN_FACULTY_NAME);

				//可採計之學系
				String USE_FACULTY_NAME = (String)((Hashtable)temp.get(i)).get("USE_FACULTY_NAME");
				if(USE_FACULTY_NAME==null || USE_FACULTY_NAME.trim().length()==0)
					USE_FACULTY_NAME = "";
				content1.put("USE_FACULTY_NAME", USE_FACULTY_NAME);

				//備註
				String RMK = (String)((Hashtable)temp.get(i)).get("RMK");
				if(RMK==null || RMK.trim().length()==0)
					RMK = "";
				content1.put("RMK", RMK);

				result.add(content1);

				//當同一年度有多筆資料時且不為第一筆資料
				if(count>0){
					for(int j=i+1; j<i+count+1; j++){
						Hashtable content2 = new Hashtable();

						//學年起訖年月
						ayear = (String)((Hashtable)temp.get(j)).get("AYEAR");
						if(ayear.charAt(0)=='0')
							ayear = ayear.substring(1);
						AYEAR_START_AND_END = ayear+"年8月至"+(Integer.parseInt(ayear)+1)+"年7月";
						content2.put("AYEAR_START_AND_END", AYEAR_START_AND_END);

						//學年期
						smsTemp = ((String)((Hashtable)temp.get(j)).get("SMS_NAME")).substring(0,1);
						yearAndSms = ayear+smsTemp;
						content2.put("YEAR_SMS", yearAndSms);

						//總學分
						content2.put("TOTAL_CRD", "");

						//平均分數
						content2.put("AVERAGE_MARK", "");

						//科目代號
						content2.put("CRSNO", (String)((Hashtable)temp.get(j)).get("CRSNO"));

						//科目名稱
						CRS_NAME = (String)((Hashtable)temp.get(j)).get("CRS_NAME");
						if(CRS_NAME==null || CRS_NAME.trim().length()==0)
							CRS_NAME = "";
						content2.put("CRS_NAME", CRS_NAME);

						//學分數
						CRD = (String)((Hashtable)temp.get(j)).get("CRD");
						if(CRD==null || CRD.trim().length()==0)
							CRD = "0";
						content2.put("CRD", CRD);

						//成績
						MARK = (String)((Hashtable)temp.get(j)).get("MARK");
						if(MARK==null || MARK.trim().length()==0)
							MARK = "0";
						content2.put("MARK", MARK);

						//開設學系(科)及學類
						OPEN_FACULTY_NAME = (String)((Hashtable)temp.get(j)).get("OPEN_FACULTY_NAME");
						if(OPEN_FACULTY_NAME==null || OPEN_FACULTY_NAME.trim().length()==0)
							OPEN_FACULTY_NAME = "";
						content2.put("OPEN_FACULTY_NAME", OPEN_FACULTY_NAME);

						//可採計之學系
						USE_FACULTY_NAME = (String)((Hashtable)temp.get(j)).get("USE_FACULTY_NAME");
						if(USE_FACULTY_NAME==null || USE_FACULTY_NAME.trim().length()==0)
							USE_FACULTY_NAME = "";
						content2.put("USE_FACULTY_NAME", USE_FACULTY_NAME);

						//備註
						RMK = (String)((Hashtable)temp.get(j)).get("RMK");
						if(RMK==null || RMK.trim().length()==0)
							RMK = "";
						content2.put("RMK", RMK);
						result.add(content2);
					}
				}
				//直接跳至該筆
				i+=count;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return result;
	}

	/**
	 * north 2007/8/7 列印英文學業成績表(SCD207R)
	 *
	 * @param vt -
	 *            回傳值
	 * @param requestMap -
	 *            條件值
	 * @throws Exception
	 *
	 */
	public Vector getMarkDataToPrintForSCD207R(Hashtable requestMap) throws Exception {
		Vector result = new Vector();

		// 將 SQL 清空
		if (sql.length() > 0)
			sql.delete(0, sql.length());

		sql
				.append("SELECT DISTINCT STUT010.AYEAR, STUT010.SMS, STUT010.STNO, STUT010.CRD, STUT010.MARK, "
						+ "STUT010.CRSNO, STUT010.RMK, COUT002.CRS_ENG, SYST003.FACULTY_CODE, "
						+ "SYST003.FACULTY_ENG_NAME, SYST004.DISCIPLINE_CODE, SYST004.DISCIPLINE_ENG_NAME "
						+ "FROM STUT010, COUT002, SYST003, SYST004 "
						+ "WHERE "
						//JOIN條件
						+ "STUT010.CRSNO = COUT002.CRSNO AND "
						+ "COUT002.FACULTY_CODE = SYST003.FACULTY_CODE AND "
						+ "COUT002.DISCIPLINE_CODE = SYST004.DISCIPLINE_CODE(+) AND "
						+ "COUT002.FACULTY_CODE = SYST004.FACULTY_CODE(+) AND "
						// 開始加入所篩選的條件
						// 學號
						+ "STUT010.STNO='" + requestMap.get("STNO")+ "' "
						+ "ORDER BY SYST003.FACULTY_CODE, SYST004.DISCIPLINE_CODE, STUT010.CRSNO, STUT010.AYEAR, "
						+ "STUT010.SMS ");

		System.out.println("getMarkDataToPrintForSCD207R:"+sql);

		DBResult rs = null;
		try {
			if (pageQuery) {
				// 依分頁取出資料
				rs = Page.getPageResultSet(dbmanager, conn, sql.toString(),
						pageNo, pageSize);
			} else {
				// 取出所有資料
				rs = dbmanager.getSimpleResultSet(conn);
				rs.open();
				rs.executeQuery(sql.toString());
			}

						//將結果存放至暫存區
			Vector temp = new Vector();
			while (rs.next()) {
				Hashtable content = new Hashtable();
				/** 將欄位抄一份過去 */
				for (int i = 1; i <= rs.getColumnCount(); i++) {
					content.put(rs.getColumnName(i), rs.getString(i));
				}
				temp.add(content);
			}

			SYSGETSMSDATA sys = new SYSGETSMSDATA(dbmanager);
			//開始組合所想要顯示的結果
			for(int i=0; i<temp.size(); i++){
				Hashtable content = new Hashtable();

				// 學年期
				String AYEAR = (String)((Hashtable)temp.get(i)).get("AYEAR");
				String SMS = (String)((Hashtable)temp.get(i)).get("SMS");
				String ayearAndSms = "";
				if(AYEAR!=null && AYEAR.trim().length()!=0 &&
						SMS!=null && SMS.trim().length()!=0){
					sys.setAYEAR(AYEAR);
					sys.setSMS(SMS);
					int s = sys.execute();
					if(s==1){
						String ayearStart = sys.getSMS_SDATE().substring(0,4);
						String ayearEnd = sys.getSMS_EDATE().substring(0,4);
						if(SMS.equals("1"))
							SMS = "(1st)";
						else if(SMS.equals("2"))
							SMS = "(2st)";
						else if(SMS.equals("3"))
							SMS = "(Summer)";
						ayearAndSms = ayearStart+"-"+ayearEnd+SMS;
					}
				}
				content.put("ayearAndSms", ayearAndSms);

				// 學號
				String STNO = (String)((Hashtable)temp.get(i)).get("STNO");
				if(STNO==null || STNO.trim().length()==0)
					STNO = "";
				content.put("STNO", STNO);

				// 學分
				String CRD = (String)((Hashtable)temp.get(i)).get("CRD");
				if(CRD==null || CRD.trim().length()==0)
					CRD = "0";
				content.put("CRD", CRD);

				// 成績
				String MARK = (String)((Hashtable)temp.get(i)).get("MARK");
				if(MARK==null || MARK.trim().length()==0)
					MARK = "0";
				content.put("MARK", MARK);

				// 科目英文名稱
				String CRS_ENG = (String)((Hashtable)temp.get(i)).get("CRS_ENG");
				if(CRS_ENG==null || CRS_ENG.trim().length()==0)
					CRS_ENG = "";
				content.put("CRS_ENG", CRS_ENG);

				/** 組合學系學類字串 */
				// 學系英文名稱
				String FACULTY_ENG_NAME = (String)((Hashtable)temp.get(i)).get("FACULTY_ENG_NAME");
				// 學類英文名稱
				String DISCIPLINE_ENG_NAME = (String)((Hashtable)temp.get(i)).get("DISCIPLINE_ENG_NAME");
				String FACULTY_AND_DISCIPLINE = "";
				if(FACULTY_ENG_NAME!=null && FACULTY_ENG_NAME.trim().length()!=0){
					FACULTY_AND_DISCIPLINE = "Department of " + FACULTY_ENG_NAME + "/";
					if(DISCIPLINE_ENG_NAME!=null && DISCIPLINE_ENG_NAME.trim().length()!=0)
						FACULTY_AND_DISCIPLINE += DISCIPLINE_ENG_NAME;
				}
				content.put("FACULTY_AND_DISCIPLINE", FACULTY_AND_DISCIPLINE);

				// 學系
				String FACULTY_CODE = (String)((Hashtable)temp.get(i)).get("FACULTY_CODE");
				if(FACULTY_CODE==null || FACULTY_CODE.trim().length()==0)
					FACULTY_CODE = "";
				content.put("FACULTY_CODE", FACULTY_CODE);

				// 備註
				String RMK = (String)((Hashtable)temp.get(i)).get("RMK");
				if(RMK==null || RMK.trim().length()==0)
					RMK = "";
				content.put("RMK", RMK);

				result.add(content);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
		return result;
	}


	
  /**stut009m  query架構調整
    *by poto
    */
    public Vector getStu009mQuery(Hashtable requestMap) throws Exception {
        if(requestMap == null) {
            requestMap = new Hashtable();
        }
        Vector result = new Vector();
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
		sql.append("SELECT A.IDNO,A.SMS,A.AYEAR,A.BIRTHDATE,DECODE(A.GET_MANNER,'3','1',A.GET_MANNER) GET_MANNER,G.CODE_NAME AS ASYS_DESC,A.STNO,A.CRSNO,C.CRS_NAME,A.CRD,A.MARK,D.CODE_NAME AS SMS_DESC, ");
		sql.append("E.FACULTY_NAME AS EST_FACULTY_DESC , A.ASYS ");
		sql.append("FROM STUT010 A ");
		sql.append("JOIN COUT002 C ON A.CRSNO = C.CRSNO ");
		//mod by teno 980925 for 推廣有sms=4  sql.append("JOIN SYST001 D ON A.SMS = D.CODE AND D.KIND = 'SMS' ");
		//mod by teno 980925 for 推廣asys=5   sql.append("JOIN SYST001 G ON A.ASYS = G.CODE AND G.KIND = 'ASYS' ");
		sql.append("LEFT JOIN SYST001 D ON A.SMS = D.CODE AND D.KIND = 'SMS' ");
		sql.append("LEFT JOIN SYST001 G ON A.ASYS = G.CODE AND G.KIND = 'ASYS' ");
        sql.append("LEFT JOIN COUT001 F ON A.AYEAR = F.AYEAR AND A.SMS = F.SMS AND A.CRSNO = F.CRSNO ");
        sql.append("LEFT JOIN SYST003 E ON E.FACULTY_CODE = NVL(F.FACULTY_CODE,C.FACULTY_CODE)    ");
		//sql.append("WHERE 1=1 and a.asys='1' ");//mod by teno 980925 暫時排除顯示空專 and a.asys<>'2'
        sql.append("WHERE 1=1 ");//by pot 復原teno排除空專

		// == 查詢條件 ST
		//if(!Utility.checkNull(requestMap.get("IDNO"), "").equals(""))
			sql.append("AND A.IDNO	=	'").append(Utility.dbStr(requestMap.get("IDNO"))).append("' ");
		if(!Utility.checkNull(requestMap.get("BIRTHDATE"), "").equals(""))
			sql.append("AND A.BIRTHDATE	=	'").append(Utility.dbStr(requestMap.get("BIRTHDATE"))).append("'");
		if(!Utility.checkNull(requestMap.get("CRD_TYPE"), "1").equals(""))
			sql.append("AND A.GET_MANNER	=	'").append(Utility.dbStr(requestMap.get("CRD_TYPE"))).append("'");
		// 查詢條件 ED
		sql.append(" ORDER BY A.IDNO, A.BIRTHDATE, A.AYEAR desc, A.SMS, A.CRSNO ");
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
			String ASYS ="";
            String AYEAR ="";
            String SMS ="";
            String CRSNO ="";
			String sql = "";
            String STNO ="";
            String ADOPT_FACULTY_DESC ="";
            DBResult rs1 = null;
            rs1 = dbmanager.getSimpleResultSet(conn);
            while (rs.next()) {
                rowHt = new Hashtable();
                for (int i = 1; i <= rs.getColumnCount(); i++){
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }

				ASYS =rs.getString("ASYS");
                AYEAR =rs.getString("AYEAR");
				SMS =rs.getString("SMS");
				CRSNO =rs.getString("CRSNO");
				STNO =rs.getString("STNO");
                ADOPT_FACULTY_DESC = "";
				if(ASYS.equals("1") || ASYS.equals("5")){
					// 1030410 mars 條件增加檢查grat008 學系之科目有效期限參數設定
					sql = "SELECT DISTINCT SYST008.FACULTY_ABBRNAME AS FACULTY_NAME, M.FACULTY_CODE, M.AYEAR, M.SMS ";
					sql = sql + "  FROM COUT103 M  ";
					sql = sql + "  JOIN STUT010 STUT010 ON STUT010.GET_MANNER = '1' AND STUT010.CRSNO = M.CRSNO AND STUT010.STNO = '" + STNO + "'   ";
					sql = sql + "  JOIN SYST008 SYST008 ON SYST008.FACULTY_CODE = M.FACULTY_CODE  ";
					sql = sql + "  AND SYST008.ASYS = '" + ASYS + "' AND SYST008.TOTAL_CRS_NO = M.TOTAL_CRS_NO ";
					sql = sql + " WHERE 1 = 1 AND M.CRS_GROUP_CODE = '003'  ";
					sql = sql + "   AND M.AYEAR || DECODE (M.SMS, '3', '0', M.SMS) = ";
					sql = sql + "    (SELECT MAX (A.AYEAR || DECODE (A.SMS, '3', '0', A.SMS)) ";
					sql = sql + "       FROM COUT103 A ";
					sql = sql + "       WHERE 1 = 1 AND A.CRS_GROUP_CODE = M.CRS_GROUP_CODE AND A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO ";
					//科目採計原採計學系已中止採計,即不列入採計，故科目須判斷大於等於修課學年期, 2021/11/24因學系助教未設定中止採計資料，僅在cout103設定，故修改判斷取得科目學年期之採計資料與畢業承辦人宛潞確認
					//sql = sql + "         AND A.CRSNO = M.CRSNO AND A.AYEAR || DECODE (A.SMS, '3', '0', A.SMS) = '" + AYEAR + SMS + "') ";
					sql = sql + "         AND A.CRSNO = M.CRSNO AND A.AYEAR || A.SMS = '" + AYEAR + SMS + "') ";
					sql = sql + "   AND M.CRSNO = '" + CRSNO + "' ";
					sql = sql + "   AND M.FACULTY_CODE NOT IN NVL ( ";
					sql = sql + "   	(SELECT C1.FACULTY_CODE FROM GRAT008 C1 WHERE C1.AYEAR = '" + AYEAR + "' AND C1.SMS = '" + SMS + "'  AND C1.CRSNO = M.CRSNO AND C1.FACULTY_CODE = M.FACULTY_CODE ";
					sql = sql + "          AND NOT ('" + AYEAR + SMS+ "' BETWEEN NVL (C1.START_AYEAR, '000') || DECODE (NVL (C1.START_SMS, '3'), '3', '0', C1.START_SMS)  ";
					sql = sql + "          AND NVL (C1.EXPIRE_AYEAR, '999') || DECODE (NVL (C1.EXPIRE_SMS, '2'), '3', '0', C1.EXPIRE_SMS))  AND C1.IS_GRAD_APPLY = 'N'), ' ') ";
					sql = sql + "UNION ";
					sql = sql + "SELECT DISTINCT SYST008.FACULTY_ABBRNAME AS FACULTY_NAME, M.FACULTY_CODE, M.AYEAR, M.SMS ";
					sql = sql + "  FROM COUT103 M  ";
					sql = sql + "  JOIN STUT010 STUT010 ON STUT010.GET_MANNER = '2' AND STUT010.CRSNO = M.CRSNO AND STUT010.STNO = '" + STNO + "'   ";
					sql = sql + "  JOIN SYST008 SYST008 ON SYST008.FACULTY_CODE = M.FACULTY_CODE  ";
					sql = sql + "  AND SYST008.ASYS = '" + ASYS + "' AND SYST008.TOTAL_CRS_NO = M.TOTAL_CRS_NO ";
					sql = sql + " WHERE 1 = 1 AND M.CRS_GROUP_CODE = '003'  ";
					sql = sql + "   AND M.AYEAR || DECODE (M.SMS, '3', '0', M.SMS) = ";
					sql = sql + "    (SELECT MAX (A.AYEAR || DECODE (A.SMS, '3', '0', A.SMS)) ";
					sql = sql + "       FROM COUT103 A ";
					sql = sql + "       WHERE 1 = 1 AND A.CRS_GROUP_CODE = M.CRS_GROUP_CODE AND A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO ";
					//已抵免、採認、減修科目科目採計原採計學系已中止採計,即不列入採計，採計學系判斷小於等於取得學年期, 2021/12/07
					sql = sql + "         AND A.CRSNO = M.CRSNO AND A.AYEAR || A.SMS <= '" + AYEAR + SMS + "') ";
					sql = sql + "   AND M.CRSNO = '" + CRSNO + "' ";
					sql = sql + "   AND M.FACULTY_CODE NOT IN NVL ( ";
					sql = sql + "   	(SELECT C1.FACULTY_CODE FROM GRAT008 C1 WHERE C1.AYEAR = '" + AYEAR + "' AND C1.SMS = '" + SMS + "'  AND C1.CRSNO = M.CRSNO AND C1.FACULTY_CODE = M.FACULTY_CODE ";
					sql = sql + "          AND NOT ('" + AYEAR + SMS+ "' BETWEEN NVL (C1.START_AYEAR, '000') || DECODE (NVL (C1.START_SMS, '3'), '3', '0', C1.START_SMS)  ";
					sql = sql + "          AND NVL (C1.EXPIRE_AYEAR, '999') || DECODE (NVL (C1.EXPIRE_SMS, '2'), '3', '0', C1.EXPIRE_SMS))  AND C1.IS_GRAD_APPLY = 'N'), ' ') ";
					//sql = sql + "ORDER BY M.FACULTY_CODE ";

					// SQL = "SELECT "+
					// "DISTINCT SYST008.FACULTY_ABBRNAME AS FACULTY_NAME "+
					// "FROM COUT103 COUT103 "+
					// "JOIN SYST008 SYST008 ON SYST008.FACULTY_CODE = COUT103.FACULTY_CODE AND SYST008.ASYS='"
					// +ASYS+"' AND SYST008.TOTAL_CRS_NO=COUT103.TOTAL_CRS_NO "+
					// "WHERE 1=1 "+
					// "AND COUT103.AYEAR||decode(COUT103.SMS,'3','0',COUT103.SMS) = ( "+
					// "select MAX(a.AYEAR||decode(a.SMS,'3','0',a.SMS)) "+
					// "from COUT103 a "+
					// "where 1=1 "+
					// "and a.CRS_GROUP_CODE =COUT103.CRS_GROUP_CODE "+
					// "and a.FACULTY_CODE =COUT103.FACULTY_CODE  "+
					// "and a.TOTAL_CRS_NO =COUT103.TOTAL_CRS_NO   "+
					// "and a.crsno =COUT103.crsno  "+
					// "and a.ayear||decode(a.SMS,'3','0',a.SMS) <=  '" + AYEAR
					// + SMS +"'  "+
					// ")  "+
					// "AND COUT103.CRSNO ='"+CRSNO+"'" ;
    				rs1.open();
					rs1.executeQuery(sql);
                    while(rs1.next()){
                          ADOPT_FACULTY_DESC =  ADOPT_FACULTY_DESC + rs1.getString("FACULTY_NAME")+ "、"; // mod by teno 980925"<br>";
                    }
                    rs1.close();
                    if(ADOPT_FACULTY_DESC.length()>2){ // mod by teno 980925 原>4 for remove <br>
                       ADOPT_FACULTY_DESC = ADOPT_FACULTY_DESC.substring(0,ADOPT_FACULTY_DESC.length()-1); // -4
                    }
                    if ( ASYS.equals("5") ) {
                    	rowHt.remove("ASYS_DESC");
                    	rowHt.put("ASYS_DESC","推廣中心");
                    	rowHt.remove("SMS_DESC");
                    	rowHt.put("SMS_DESC","第"+SMS+"期");
                    }
				}else{
				    STUT003GATEWAY SS = new STUT003GATEWAY(dbmanager, conn);
                    Hashtable htt = SS.getCRS_GROUP_CODE_NAME(STNO,CRSNO,AYEAR,SMS);
					ADOPT_FACULTY_DESC = (String)htt.get("CRS_GROUP_CODE_NAME");
                }
				//System.out.println(CRSNO+ADOPT_FACULTY_DESC);
				if (ADOPT_FACULTY_DESC==null){ ADOPT_FACULTY_DESC = "未定義"; }
                rowHt.put("ADOPT_FACULTY_DESC",ADOPT_FACULTY_DESC);
                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }



   /**
     *
     *  by poto 2011/06/02    get_manner !='1' ->  ='2'
     *                        get_manner !='2' ->  ='1' 
     */
    public Vector ccs015m_query(Hashtable ht,Vector result) throws Exception {
        if(ht == null) {
            ht = new Hashtable();
        }
        if(sql.length() > 0) {
            sql.delete(0, sql.length());
        }
        String SQL ="";
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
            SQL = "AND CCST011.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' \n";
        }else{
            SQL = "AND SUBSTR(CCST011.STNO,1,LENGTH(CCST011.STNO)) LIKE '%' \n";
        }
        
        String ayearsms = (String)ht.get("AYEAR") + (String)ht.get("SMS");  
        
        sql.append(" SELECT CCST011.ASYS,CCST012.STNO, CCST012.CRSNO,CCST012.APP_SEQ,COUT002.CRS_NAME,'已抵免' AS RMK,CCST001.APPRV_BASEDATE,STUT002.NAME,TO_NUMBER(CCST011.AYEAR)||DECODE(CCST011.SMS,'1','上','2','下','暑') AS AYEAR_SMS, \n");
        sql.append(" STUT002.IDNO,STUT002.BIRTHDATE,  \n");
        sql.append(" NVL(  \n");
        sql.append(" ( SELECT TO_NUMBER(a.AYEAR)||DECODE(a.SMS,'1','上','2','下','暑')||','||a.CRSNO||c012.CRS_NAME  FROM STUT010 a   \n");
        sql.append("JOIN COUT002 C012 ON C012.CRSNO = A.CRSNO  \n");
        sql.append("WHERE a.STNO = CCST012.STNO  \n");
        sql.append("AND a.CRSNO = CCST012.CRSNO    \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE    \n");
        sql.append("AND a.IDNO = STUT003.IDNO   \n");
        sql.append("AND a.GET_MANNER ='2' and rownum = 1 ),  \n");
        sql.append(" ( SELECT TO_NUMBER(a.AYEAR)||DECODE(a.SMS,'1','上','2','下','暑')||','||a.CRSNO||c012.CRS_NAME  FROM STUT010 a   \n");
        sql.append("JOIN COUT002 C012 ON C012.CRSNO = A.CRSNO  \n");
        sql.append("WHERE a.STNO = CCST012.STNO   \n");
        sql.append("AND a.CRSNO in (SELECT b.CRSNO_OLD FROM CCST010 b WHERE b.CRSNO=CCST012.CRSNO )    \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE   \n");
        sql.append("AND a.IDNO = STUT003.IDNO    \n");
        sql.append("AND a.GET_MANNER ='2' and rownum = 1 ))  \n");
        sql.append(" AS AYEARSMS_CRSNO,  \n");
        sql.append(" CCST012.AUDIT_STATUS,CCST012.PRIORITY   \n");
        sql.append("FROM CCST011 CCST011   \n");
        sql.append("JOIN CCST012 CCST012 ON 1=1   \n");
        sql.append("AND CCST011.AYEAR = CCST012.AYEAR  \n");
        sql.append("AND CCST011.SMS = CCST012.SMS    \n");
        sql.append("AND CCST011.APP_SEQ = CCST012.APP_SEQ   \n");
        sql.append("AND CCST011.STNO = CCST012.STNO  \n");
        sql.append("JOIN STUT003 STUT003 ON 1=1 AND CCST011.STNO = STUT003.STNO   \n");
        sql.append("JOIN STUT002 STUT002 ON 1=1 AND STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE   \n");
        sql.append("JOIN COUT002 COUT002 ON 1=1 AND COUT002.CRSNO = CCST012.CRSNO   \n");
        sql.append("JOIN CCST001 CCST001 ON 1=1   \n");
        sql.append("AND CCST001.AYEAR = CCST011.AYEAR   \n");
        sql.append("AND CCST001.SMS = CCST011.SMS   \n");
        sql.append("AND CCST001.ASYS = CCST011.ASYS   \n");
        sql.append("WHERE 1=1  \n");
        sql.append("AND CCST011.AYEAR ='"+ht.get("AYEAR")+"'   \n");
        sql.append("AND CCST011.SMS='"+ht.get("SMS")+"'   \n");
        //sql.append("AND CCST011.APP_TYPE='1'   \n");
        //by poto 抵免採認都要檢查
        sql.append("AND CCST011.APP_TYPE IN ('1','2')   \n");
        sql.append("AND CCST011.ASYS ='"+ht.get("ASYS")+"'   \n");
        sql.append("AND CCST012.RE_AUDIT_STATUS !='2'   \n");
        sql.append("AND CCST011.CONFIRM_MK = 'Y'   \n"); //by poto加入要送出註記的才檢查
        sql.append(SQL);
        sql.append("AND ( 0<(   \n");
        sql.append("SELECT count(1)   \n");
        sql.append("FROM STUT010 a    \n");
        sql.append("WHERE a.STNO = CCST012.STNO   \n");
        sql.append("AND a.CRSNO = CCST012.CRSNO  \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE  \n");
        sql.append("AND a.IDNO = STUT003.IDNO  \n");
        sql.append("AND a.GET_MANNER ='2'   \n");
        sql.append("AND a.ayear||a.sms !='"+ ayearsms+"'   \n");
        sql.append("AND trim(a.OLD_STNO) is null   \n");
        sql.append(")  \n");
        sql.append("OR 0<(   \n");
        sql.append("SELECT count(1)  \n");
        sql.append("FROM STUT010 a   \n");
        sql.append("WHERE a.STNO = CCST012.STNO  \n");
        sql.append("AND a.CRSNO in (SELECT b.CRSNO_OLD FROM CCST010 b WHERE b.CRSNO=CCST012.CRSNO )   \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE    \n");
        sql.append("AND a.IDNO = STUT003.IDNO  \n");
        sql.append("AND a.GET_MANNER ='2' \n");
        sql.append("AND a.ayear||a.sms !='"+ ayearsms+"'   \n");
        sql.append("AND trim(a.OLD_STNO) is null   \n");
        sql.append(") \n");
        sql.append(")  \n");
        sql.append(" UNION ALL   \n");
        sql.append(" SELECT CCST011.ASYS,CCST012.STNO, CCST012.CRSNO,CCST012.APP_SEQ,COUT002.CRS_NAME,'已修及格' AS RMK,CCST001.APPRV_BASEDATE,STUT002.NAME,TO_NUMBER(CCST011.AYEAR)||DECODE(CCST011.SMS,'1','上','2','下','暑') AS AYEAR_SMS,  \n");
        sql.append(" STUT002.IDNO,STUT002.BIRTHDATE,  \n");
        sql.append(" NVL(  \n");
        sql.append(" ( SELECT TO_NUMBER(a.AYEAR)||DECODE(a.SMS,'1','上','2','下','暑')||','||a.CRSNO||c012.CRS_NAME  FROM STUT010 a   \n");
        sql.append("JOIN COUT002 C012 ON C012.CRSNO = A.CRSNO  \n");
        sql.append("WHERE a.STNO = CCST012.STNO   \n");
        sql.append("AND a.CRSNO = CCST012.CRSNO    \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE    \n");
        sql.append("AND a.IDNO = STUT003.IDNO    \n");
        sql.append("AND a.GET_MANNER ='1' and rownum = 1 ),  \n");
        sql.append(" ( SELECT TO_NUMBER(a.AYEAR)||DECODE(a.SMS,'1','上','2','下','暑')||','||a.CRSNO||c012.CRS_NAME  FROM STUT010 a   \n");
        sql.append("JOIN COUT002 C012 ON C012.CRSNO = A.CRSNO  \n");
        sql.append("WHERE a.STNO = CCST012.STNO   \n");
        sql.append("AND a.CRSNO in (SELECT b.CRSNO_OLD FROM CCST010 b WHERE b.CRSNO=CCST012.CRSNO )    \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE   \n");
        sql.append("AND a.IDNO = STUT003.IDNO   \n");
        sql.append("AND a.GET_MANNER ='1' and rownum = 1 ))  \n");
        sql.append(" AS AYEARSMS_CRSNO,  \n");
        sql.append(" CCST012.AUDIT_STATUS,CCST012.PRIORITY   \n");
        sql.append("FROM CCST011 CCST011   \n");
        sql.append("JOIN CCST012 CCST012 ON 1=1   \n");
        sql.append("AND CCST011.AYEAR = CCST012.AYEAR   \n");
        sql.append("AND CCST011.SMS = CCST012.SMS   \n");
        sql.append("AND CCST011.APP_SEQ = CCST012.APP_SEQ   \n");
        sql.append("AND CCST011.STNO = CCST012.STNO  \n");
        sql.append("JOIN STUT003 STUT003 ON 1=1 AND CCST011.STNO = STUT003.STNO   \n");
        sql.append("JOIN STUT002 STUT002 ON 1=1 AND STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE   \n");
        sql.append("JOIN COUT002 COUT002 ON 1=1 AND COUT002.CRSNO = CCST012.CRSNO   \n");
        sql.append("JOIN CCST001 CCST001 ON 1=1  \n");
        sql.append("AND CCST001.AYEAR = CCST011.AYEAR   \n");
        sql.append("AND CCST001.SMS = CCST011.SMS   \n");
        sql.append("AND CCST001.ASYS = CCST011.ASYS   \n");
        sql.append("WHERE 1=1   \n");
        sql.append("AND CCST011.AYEAR ='"+ht.get("AYEAR")+"'   \n");
        sql.append("AND CCST011.SMS='"+ht.get("SMS")+"'   \n");
        //sql.append("AND CCST011.APP_TYPE='1'   \n");
        //by poto 抵免採認都要檢查
        sql.append("AND CCST011.APP_TYPE IN ('1','2')   \n");
        sql.append("AND CCST011.ASYS ='"+ht.get("ASYS")+"'  \n");
        sql.append("AND CCST012.RE_AUDIT_STATUS !='2'  \n");
        sql.append("AND CCST011.CONFIRM_MK = 'Y'   \n"); //by poto加入要送出註記的才檢查
        sql.append(SQL);
        sql.append("AND ( 0<(   \n");
        sql.append("SELECT count(1)    \n");
        sql.append("FROM STUT010 a    \n");
        sql.append("WHERE a.STNO = CCST012.STNO   \n");
        sql.append("AND a.CRSNO in (SELECT b.CRSNO_OLD FROM CCST010 b WHERE b.CRSNO=CCST012.CRSNO )  \n");
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE  \n");
        sql.append("AND a.IDNO = STUT003.IDNO   \n");
        sql.append("AND a.GET_MANNER ='1'   \n");
        sql.append("AND trim(a.OLD_STNO) is null   \n");
        sql.append(")  \n");
        sql.append("OR 0<(  \n");
        sql.append("SELECT count(1)   \n");
        sql.append("FROM STUT010 a   \n");
        sql.append("WHERE a.STNO = CCST012.STNO   \n");
        sql.append("AND a.CRSNO = CCST012.CRSNO  \n");        
        sql.append("AND a.BIRTHDATE = STUT003.BIRTHDATE   \n");
        sql.append("AND a.IDNO = STUT003.IDNO  \n");
        sql.append("AND a.GET_MANNER ='1'   \n");
        sql.append("AND trim(a.OLD_STNO) is null   \n");
        sql.append(")  \n");
        sql.append(")  \n");
        /*
        sql.append(" UNION ALL   \n");
        sql.append("SELECT CCST011.ASYS,CCST012.STNO, CCST012.CRSNO,CCST012.APP_SEQ,COUT002.CRS_NAME,'本學期抵免過' AS RMK,CCST001.APPRV_BASEDATE,STUT002.NAME,TO_NUMBER(CCST011.AYEAR)||DECODE(CCST011.SMS,'1','上','2','下','暑') AS AYEAR_SMS, \n");
        sql.append("STUT002.IDNO,STUT002.BIRTHDATE,   \n");
        sql.append("TO_NUMBER(CCST011.AYEAR)||DECODE(CCST011.SMS,'1','上','2','下','暑')||','||CCST010.CRSNO_OLD, \n");
        sql.append("CCST012.AUDIT_STATUS,CCST012.PRIORITY \n");
        sql.append("FROM CCST011 CCST011    \n");
        sql.append("JOIN CCST012 CCST012 ON 1=1     \n");
        sql.append("AND CCST011.AYEAR = CCST012.AYEAR     \n");
        sql.append("AND CCST011.SMS = CCST012.SMS \n");
        sql.append("AND CCST011.APP_SEQ = CCST012.APP_SEQ  \n");
        sql.append("AND CCST011.STNO = CCST012.STNO    \n");
        sql.append("JOIN CCST010 ON CCST012.CRSNO = CCST010.CRSNO    \n");
        sql.append("JOIN STUT003 STUT003 ON 1=1 AND CCST011.STNO = STUT003.STNO  \n");
        sql.append("JOIN STUT002 STUT002 ON 1=1 AND STUT003.IDNO = STUT002.IDNO AND STUT003.BIRTHDATE = STUT002.BIRTHDATE \n");
        sql.append("JOIN COUT002 COUT002 ON 1=1 AND COUT002.CRSNO = CCST012.CRSNO    \n");
        sql.append("JOIN CCST001 CCST001 ON 1=1   \n");
        sql.append("AND CCST001.AYEAR = CCST011.AYEAR   \n");
        sql.append("AND CCST001.SMS = CCST011.SMS   \n");
        sql.append("AND CCST001.ASYS = CCST011.ASYS    \n");
        sql.append("WHERE 1=1       \n");
        sql.append("AND CCST011.AYEAR ='"+ht.get("AYEAR")+"'   \n");
        sql.append("AND CCST011.SMS='"+ht.get("SMS")+"'  \n");
        sql.append("AND CCST011.APP_TYPE='1'   \n");
        sql.append("AND CCST011.ASYS ='"+ht.get("ASYS")+"'   \n");
        sql.append("AND CCST012.RE_AUDIT_STATUS !='2'  \n");
        sql.append(SQL);
        sql.append("and CCST010.CRSNO_OLD  IN (SELECT b1.CRSNO FROM CCST012 b1 WHERE 1=1 AND b1.AYEAR = CCST012.AYEAR AND b1.SMS = CCST012.SMS AND b1.STNO = CCST012.STNO )  \n");
        sql.append("and ( \n");
        sql.append("1<(  \n");
        sql.append("SELECT count(1)   \n");
        sql.append("FROM ccst012 a    \n");
        sql.append("WHERE 1=1   \n");
        sql.append("AND a.AYEAR = CCST012.AYEAR      \n");
        sql.append("AND a.SMS = CCST012.SMS    \n");
        sql.append("AND a.CRSNO = CCST012.CRSNO     \n");
        sql.append("AND a.STNO = CCST012.STNO         \n");
        sql.append("AND a.APP_SEQ = CCST012.APP_SEQ    \n");
        sql.append(")     \n");
        sql.append("or   \n");
        sql.append("0<(      \n");
        sql.append("SELECT count(1)       \n");
        sql.append("FROM ccst012 a   \n");
        sql.append("WHERE 1=1  \n");
        sql.append("AND a.AYEAR = CCST012.AYEAR   \n");
        sql.append("AND a.SMS = CCST012.SMS \n");
        sql.append("AND a.CRSNO in (SELECT b.CRSNO_OLD FROM CCST010 b WHERE b.CRSNO=CCST012.CRSNO AND b.CRSNO_OLD = CCST010.CRSNO_OLD AND b.CRSNO!= b.CRSNO_OLD AND  a.RE_AUDIT_STATUS !='2' ) \n");
        sql.append("AND a.STNO = CCST012.STNO   \n");
        sql.append("AND a.APP_SEQ = CCST012.APP_SEQ  \n");
        sql.append(")   \n");
        sql.append(")  \n");
                */
        sql.append(" ORDER BY APP_SEQ,PRIORITY,STNO,CRSNO  \n");


        if(!orderBy.equals("")) {
            String[] orderByArray = orderBy.split(",");
            orderBy = "";
            for(int i = 0; i < orderByArray.length; i++) {
                orderByArray[i] = "S10." + orderByArray[i].trim();

                if(i == 0) {
                    orderBy += "ORDER BY ";
                } else {
                    orderBy += ", ";
                }
                orderBy += orderByArray[i].trim();
            }
            sql.append(orderBy.toUpperCase());
            orderBy = "";
        }
        System.out.println("SQL="+sql.toString());
        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            String[] AYEARSMS_CRSNO = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                AYEARSMS_CRSNO =  Utility.split(rs.getString("AYEARSMS_CRSNO"), ",");
                rowHt.put("AYEARSMS_1",AYEARSMS_CRSNO[0]);
                rowHt.put("CRSNO_1",AYEARSMS_CRSNO[1]);
                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }

    /**
     * JOIN 其它 TABLE 將欄位的值抓出來
     * @param ht 條件值
     * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
     *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
     * @throws Exception
     */
     public Vector getCCS009MStut010ForUse(Hashtable ht) throws Exception {
        Vector result = new Vector();

        if(sql.length() > 0)
            sql.delete(0, sql.length());

        // stut010(get_manner=3)的學號是推廣的學號,非學籍的學號
        if(!Utility.nullToSpace(ht.get("STNO")).equals("")){
        	sql.append(" select M.* from ( ");
        	sql.append(
                "SELECT 'STU' AS KIND,TO_CHAR(D.STNO) AS STNO,B.NAME,A.AYEAR,A.SMS,S1.CODE_NAME AS SMS_NAME, C.CRSNO,C.CRS_NAME,TO_CHAR(A.CRD) AS CRD,A.MARK, TO_CHAR(A.ASYS) AS ASYS, a.STNO as POP_STNO,A.IDNO,A.BIRTHDATE " +
                "FROM STUT010 A  "+
                "JOIN STUT003 D ON D.IDNO = A.IDNO AND D.BIRTHDATE = A.BIRTHDATE "+
                "JOIN STUT002 B ON B.IDNO = D.IDNO  AND B.BIRTHDATE = D.BIRTHDATE "+
                "JOIN COUT002 C ON C.CRSNO = A.CRSNO "+
                "JOIN SYST001 S1 ON S1.KIND = 'SMS' AND S1.CODE = A.SMS "+
                "WHERE A.GET_MANNER = '3' AND D.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "'  "+
                ""
	        );
        	sql.append(" UNION ");
        	sql.append(
	        	"SELECT  "+
	        	"'POP' AS KIND, "+
	        	"TO_CHAR('"+Utility.nullToSpace(ht.get("STNO")) +"') AS STNO, "+
	        	"A.NAME, "+
	        	"M.YEAR,M.SEASON AS SMS, S1.CODE_NAME AS SMS_NAME, "+
	        	"M.CRSNO, "+
	        	"B.CRS_NAME, "+
	        	"TO_CHAR(B.CRD)AS CRD, "+
	        	"M.MARK, "+
	        	"TO_CHAR('') AS ASYS, "+
	        	"M.STNO AS POP_STNO, "+
	        	"A.IDNO,A.BIRTHDATE "+
	        	"FROM POPT402 M  "+
	        	"JOIN POPT201 A ON M.STNO = A.STNO "+
	        	"JOIN COUT002 B ON M.CRSNO = B.CRSNO AND B.NOUADOPT = 'Y' "+
	        	"JOIN SYST001 S1 ON S1.KIND = 'SEASON' AND S1.CODE = M.SEASON "+
	        	"WHERE ( A.IDNO,A.BIRTHDATE ) IN  ( SELECT X.IDNO,X.BIRTHDATE FROM STUT003 X WHERE X.STNO = '"+Utility.nullToSpace(ht.get("STNO")) +"' )  "+
	        	"AND EXISTS ( "+
	        	"	SELECT 1  "+
	        	"	FROM  "+
	        	"	( "+
	        	"		SELECT CRSNO FROM POPT102 "+
	        	"		INTERSECT "+
	        	"		SELECT CRSNO FROM COUT002  "+
	        	"	) X WHERE X.CRSNO = M.CRSNO "+
	        	") "+
	        	"AND NOT EXISTS( "+
	        	"	SELECT 1 FROM STUT010 Y WHERE Y.IDNO = A.IDNO AND Y.BIRTHDATE = A.BIRTHDATE AND M.CRSNO = Y.CRSNO "+
	        	") "
        	);
        	sql.append(" ) M WHERE 1=1  ");
        	if(!Utility.nullToSpace(ht.get("KIND")).equals("")){
        		sql.append(" AND M.KIND = '"+Utility.nullToSpace(ht.get("KIND"))+"' ");
        	}
        	sql.append(" ORDER BY M.STNO, M.AYEAR, M.SMS, M.CRSNO ");
        }else{
        	sql.append(
                    "SELECT " +
                    "'STU' AS KIND ," +
                    "to_char(WMSYS.WM_CONCAT(to_char(D.STNO))) AS STNO ," +
                    "B.NAME,A.AYEAR,A.SMS,S1.CODE_NAME AS SMS_NAME,C.CRSNO,C.CRS_NAME,A.CRD,A.MARK, a.ASYS, a.STNO as POP_STNO " +
                    "FROM STUT010 A  "+
                    "JOIN STUT003 D ON D.IDNO = A.IDNO AND D.BIRTHDATE = A.BIRTHDATE "+
                    "JOIN STUT002 B ON B.IDNO = D.IDNO  AND B.BIRTHDATE = D.BIRTHDATE "+
                    "JOIN COUT002 C ON C.CRSNO = A.CRSNO "+
                    "LEFT JOIN SYST001 S1 ON S1.KIND = 'SMS' AND S1.CODE = A.SMS "+
                    "WHERE A.GET_MANNER = '3' "+
                    "GROUP BY B.NAME,A.AYEAR,A.SMS,C.CRSNO,C.CRS_NAME,A.CRD,A.MARK, a.ASYS, a.STNO "+
                    "ORDER BY A.STNO,A.AYEAR,A.SMS,C.CRSNO "
        	);
        }
        

        DBResult rs = null;
        try {
            if(pageQuery) {
                // 依分頁取出資料
                rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
            } else {
                // 取出所有資料
                rs = dbmanager.getSimpleResultSet(conn);
                rs.open();
                rs.executeQuery(sql.toString());
            }
            Hashtable rowHt = null;
            while (rs.next()) {
                rowHt = new Hashtable();
                /** 將欄位抄一份過去 */
                for (int i = 1; i <= rs.getColumnCount(); i++){
                    rowHt.put(rs.getColumnName(i), rs.getString(i));
                }
                result.add(rowHt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) {
                rs.close();
            }
        }
        return result;
    }


     /**
      * 篩選空專畢業學生的資料以便寫入GRA026--->供SpecialSieverOut使用
      * @param ht 條件值
      * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
      *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
      * @throws Exception
      */
      public Vector getStuDataForGra(Hashtable ht, boolean isFirst) throws Exception {
         Vector result = new Vector();

         // 取得當學期的選課學分數
			String getThisSmsCrd =
				"("+
					"SELECT SUM(E.CRD) " +
					"FROM REGT007 D, COUT001 E " +
					"WHERE " +
					"    D.AYEAR='"+ht.get("AYEAR")+"' " +
					"AND D.SMS='"+ht.get("SMS")+"' " +
					"AND D.STNO=A.STNO " +
					"AND E.AYEAR=D.AYEAR " +
					"AND E.SMS=D.SMS " +
					"AND E.CRSNO=D.CRSNO " +
					"AND D.UNQUAL_TAKE_MK='N' " +
					"AND D.UNTAKECRS_MK='N' " +
					"AND D.PAYMENT_STATUS>'1' "+
				")";


         if(sql.length() > 0)
             sql.delete(0, sql.length());

         /** 取得篩選畢業資料時,主要的學分數資料 */
         sql.append(
        		 // 礙於UtilityX的合併method(請見下方的method),因此只好將CRS_GROUP_CODE和TOTAL合併為一個欄位
        		 "SELECT A.STNO,A.CRS_GROUP_CODE||'-'||NVL(SUM(A.TOTAL),'0') AS CRS_GROUP_AND_TOTAL, "+
        		 		"A.SPECIAL_STTYPE_TYPE,A.ACCUM_REDUCE_CRD,A.CENTER_CODE,A.ENROLL_AYEARSMS "+
        		 "FROM  "+
        		 "(  "+
        		 // 從學分銀行取得該生的實得科目
        		 "  SELECT B.STNO,C.CRS_GROUP_CODE, SUM(A.CRD) AS TOTAL, B.SPECIAL_STTYPE_TYPE,B.ACCUM_REDUCE_CRD,B.CENTER_CODE, "+
        		 "          B.ENROLL_AYEARSMS "+
        		 "  FROM STUT010 A "+
        		 "  JOIN STUT003 B ON A.STNO = B.STNO AND B.SPECIAL_STTYPE_TYPE IS NOT NULL AND B.ENROLL_STATUS = '2' AND B.ASYS = '2' "+
        		 "  join cout103 c on c.ayear||decode(c.sms, 3,0, c.sms) =( "+
                 "  select max(ayear||decode(sms,3,0,sms))  "+
                 "  from cout103 "+
                 "  where 1=1  "+
                 "  and c.crsno =crsno  and ayear||decode(sms, 3, 0, sms) <= A.ayear||decode(A.sms, 3, 0, A.sms) "+
                 "  ) and A.crsno = c.crsno  AND A.CRSNO = C.CRSNO AND B.PRE_MAJOR_FACULTY||B.J_FACULTY_CODE = C.FACULTY_CODE||C.TOTAL_CRS_NO "+
        		 "  WHERE A.GET_MANNER IN ('1','2') " +
        		 // 如為篩選應屆時,則需額外加上當學期的學分(一律假設均已通過),在STUT010中均為舊資料,並非應屆資料,因此不會與下面的regt007的學分重複計算
        		 "        and ((B.ACCUM_repl_CRD + B.ACCUM_REDUCE_CRD + B.ACCUM_PASS_CRD)+"+(isFirst==true? "NVL("+getThisSmsCrd+",'0')":"0" )+" >= 80 AND B.ACCUM_PASS_CRD+B.ACCUM_REDUCE_CRD+"+(isFirst==true? "NVL("+getThisSmsCrd+",'0')":"0" )+">=50) "+
        		 "  GROUP BY B.STNO, B.SPECIAL_STTYPE_TYPE, B.ACCUM_REDUCE_CRD,B.CENTER_CODE,B.ENROLL_AYEARSMS, C.CRS_GROUP_CODE "
        );

         // 應屆篩選時從REGT007取得當學期的選課科目
         if(isFirst){
        	 sql.append(
        		 "  union  all "+
        		 "  select B.STNO, d.CRS_GROUP_CODE, SUM(c.CRD) AS TOTAL, B.SPECIAL_STTYPE_TYPE,B.ACCUM_REDUCE_CRD,B.CENTER_CODE, "+
        		 "          B.ENROLL_AYEARSMS "+
        		 "  from regt007 a    "+
        		 "  join stut003 b on a.stno = b.stno AND B.SPECIAL_STTYPE_TYPE IS NOT NULL AND B.ENROLL_STATUS = '2' AND B.ASYS = '2' "+
        		 "  join cout002 c on a.crsno = c.crsno  "+
        		 "  join cout103 d on a.ayear = d.ayear and a.sms = d.sms and a.crsno = d.crsno and b.pre_major_faculty = d.faculty_code "+
        		 "  where  "+
        		 "      a.ayear = '"+ht.get("AYEAR")+"' "+
        		 "  and a.sms = '"+ht.get("SMS")+"'  "+
        		 "  and a.asys = '2'  "+
        		 "  and d.total_crs_no = b.j_faculty_code "+
        		 "  and a.unqual_take_mk = 'N' "+
        		 "  and a.untakecrs_mk = 'N' "+
        		 "  and a.payment_status > '1' "+
        		 "  GROUP BY B.STNO, B.SPECIAL_STTYPE_TYPE, B.ACCUM_repl_CRD, B.ACCUM_REDUCE_CRD, B.ACCUM_PASS_CRD, B.CENTER_CODE,B.ENROLL_AYEARSMS,d.CRS_GROUP_CODE "+
        		 // 舊有的累計學分數+當學期的累計學分數
        		 "  HAVING ((B.ACCUM_repl_CRD + B.ACCUM_REDUCE_CRD + B.ACCUM_PASS_CRD)+SUM(c.CRD) >= 80 AND B.ACCUM_PASS_CRD+B.ACCUM_REDUCE_CRD+SUM(c.CRD)>=50) "
        	);
         }

         sql.append(" ) A WHERE 1=1 ");
         String STNO = (String)ht.get("STNO");
         if (STNO != null && !"".equals(STNO)) {
             sql.append(" and A.stno = '"+STNO+"' ");
         }
         sql.append(
        	 "GROUP BY A.STNO,A.SPECIAL_STTYPE_TYPE,A.ACCUM_REDUCE_CRD,A.CENTER_CODE,A.ENROLL_AYEARSMS,A.CRS_GROUP_CODE " +
        	 "ORDER BY A.STNO, A.CRS_GROUP_CODE"
         );

         System.out.println("我是主段SQL:"+sql.toString());

         DBResult rs = null;
         try {
        	 rs = dbmanager.getSimpleResultSet(conn);
             rs.open();
             rs.executeQuery(sql.toString());

             Vector tmp = new Vector();
             while (rs.next()) {
            	 Hashtable rowHt = new Hashtable();
                 for (int i = 1; i <= rs.getColumnCount(); i++)
                	 rowHt.put(rs.getColumnName(i), rs.getString(i));
                 tmp.add(rowHt);
             }
             rs.close();

             Vector pk = new Vector();
             pk.add("STNO");

             // 因上面這段主SQL,會產生同一個學號會有多筆資料(因COUT103的CRS_GROUP_CODE不同),因此用此method組合成一個學號一筆資料,不同的CRS_CROUP_CODE則用逗號隔開
             UtilityX.combinCompareNextTheSameData(pk, "CRS_GROUP_AND_TOTAL", ",", tmp, result);
         } catch (Exception e) {
             throw e;
         } finally {
             if(rs != null) {
                 rs.close();
             }
         }
         return result;
     }

      /**
       * 篩選空專畢業學生的資料以便寫入GRA026--->供SpecialSieverOut使用
       * 取得有關暑期學分的資料
       * @param ht 條件值
       * @return 回傳 Vector 物件，內容為 Hashtable 的集合，<br>
       *         每一個 Hashtable 其 KEY 為欄位名稱，KEY 的值為欄位的值<br>
       * @throws Exception
       */
       public Hashtable getStuSummerAndPopCrd() throws Exception {
    	   Hashtable result = new Hashtable();

          /** 取得在學分銀行中的暑期/推廣的學分數 */
          String getSummerCrd =
          	"select a.stno, " +
          		"(select nvl(sum(b.crd),'0') from stut010 b where b.get_manner='1' and b.sms='3' and b.stno=a.stno) as summer_crd, "+
          		"(select nvl(sum(b.crd),'0') from stut010 b where b.get_manner='2' and substr(b.crsno,3,1)='8' and substr(b.crsno,1,3)<>'498' and b.stno=a.stno) as pop_crd "+
			"from stut003 a "+
			"where "+
			"      a.SPECIAL_STTYPE_TYPE IS NOT NULL "+
			"  AND a.ENROLL_STATUS = '2' "+
			"  AND a.ASYS = '2' "+
			"  and ((a.ACCUM_repl_CRD + a.ACCUM_REDUCE_CRD + a.ACCUM_PASS_CRD) >= 80 AND a.ACCUM_PASS_CRD>=50) "+
			"group by a.stno ";

          DBResult rs = null;
          try {
         	 rs = dbmanager.getSimpleResultSet(conn);
             rs.open();
             rs.executeQuery(getSummerCrd);

             while (rs.next()){
            	 result.put(rs.getString("STNO")+"SUMMER", rs.getString("SUMMER_CRD"));
            	 result.put(rs.getString("STNO")+"POP", rs.getString("POP_CRD"));
             }
             rs.close();
          } catch (Exception e) {
              throw e;
          } finally {
              if(rs != null) {
                  rs.close();
              }
          }
          return result;
      }

       public Vector getCCS028MStut010ForUse(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());

           sql.append(
    		   "SELECT "+
    		   "A.ASYS,A.STNO,A.AYEAR,A.SMS,A.CRD,A.MARK,A.GET_MANNER,A.IDNO, "+
    		   "C.NAME, "+
    		   "D.CRSNO,D.CRS_NAME "+
    		   "FROM STUT010 A "+
    		   "JOIN STUT002 C ON A.IDNO = C.IDNO  "+
    		   "JOIN COUT002 D ON A.CRSNO = D.CRSNO  "+
    		   "WHERE 1=1 "+
    		   "AND A.GET_MANNER IN('4','5','6')  "  //4空大試辦  5空專78年以前成績   6磨課師
           );
           
           if(!Utility.nullToSpace(ht.get("IDNO")).equals("")) {
               sql.append("AND A.IDNO = '" + Utility.nullToSpace(ht.get("IDNO")) + "' ");
           }
           //if(!Utility.nullToSpace(ht.get("STNO")).equals("")) {
           //    sql.append("AND A.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "' ");
           //}
           if(!Utility.nullToSpace(ht.get("GET_MANNER")).equals("")) {
               sql.append("AND A.GET_MANNER = '" + Utility.nullToSpace(ht.get("GET_MANNER")) + "' ");
           }
           if(!Utility.nullToSpace(ht.get("AYEAR")).equals("")) {
               sql.append("AND A.AYEAR = '" + Utility.nullToSpace(ht.get("AYEAR")) + "' ");
           }
           if(!Utility.nullToSpace(ht.get("SMS")).equals("")) {
               sql.append("AND A.SMS = '" + Utility.nullToSpace(ht.get("SMS")) + "' ");
           }        
           if(!Utility.nullToSpace(ht.get("CRSNO")).equals("")) {
               sql.append("AND A.CRSNO = '" + Utility.nullToSpace(ht.get("CRSNO")) + "' ");
           }
           sql.append("ORDER BY A.STNO,A.AYEAR,A.SMS,A.CRSNO ");

           DBResult rs = null;
           try {
               if(pageQuery) {
                   // 依分頁取出資料
                   rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
               } else {
                   // 取出所有資料
                   rs = dbmanager.getSimpleResultSet(conn);
                   rs.open();
                   rs.executeQuery(sql.toString());
               }
               Hashtable rowHt = null;
               while (rs.next()) {
                   rowHt = new Hashtable();
                   /** 將欄位抄一份過去 */
                   for (int i = 1; i <= rs.getColumnCount(); i++){
                       rowHt.put(rs.getColumnName(i), rs.getString(i));
                   }
                   result.add(rowHt);
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }

       public Vector getCcs110mQueryCrsno(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
           String ym = "1020";
           
	   	   String SCHOOL_TYPE = Utility.dbStr(ht.get("SCHOOL_TYPE"));
	   	   String HEDU_TYPE = Utility.dbStr(ht.get("HEDU_TYPE"));
	   	   String ASYS = Utility.dbStr(ht.get("ASYS"));
	   	   String STNO = Utility.dbStr(ht.get("STNO"));
	   	   String IDNO = Utility.dbStr(ht.get("IDNO"));
	   	   String AYEAR = Utility.dbStr(ht.get("NOW_AYEAR"));
	   	   String SMS = Utility.dbStr(ht.get("NOW_SMS"));
	   	   String condition = ""; 
	   	   int STNO_LEN = STNO.length();
	   	   
	   	   if("6".equals(SCHOOL_TYPE)){	
	   		   sql.append(
	    		    "SELECT \n" +
	    		    "A.IDNO,A.BIRTHDATE, \n"+
	    		    "A.AYEAR, \n"+
	    		    "A.SMS,A.ASYS,A.STNO,A.MARK, \n"+
	    		    "A.GET_MANNER, \n"+
	    		    "A.OLD_STNO, \n"+
	    		    "TO_NUMBER(A.AYEAR) AS AYEAR_NAME, \n"+
	    		    "E.CODE_NAME AS SMS_NAME, \n"+
	    		    "F.CODE_NAME AS ASYS_NAME, \n"+
	    		    "B.CRS_NAME,  \n"+
	    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000') > '"+ym+"' AND TRIM(H.CRSNO) IS NULL AND B.FACULTY_CODE = '90' THEN 'N'  ELSE 'Y' END AS GEL_CHK, \n"+
	    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"'  THEN NVL(H.CRSNO,A.CRSNO)  ELSE A.CRSNO END AS CRSNO, \n"+
	    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000') > '"+ym+"'  THEN TO_CHAR(NVL(H.CRD,A.CRD))  ELSE TO_CHAR(A.CRD) END AS CRD, \n"+
	    		    "A.CRSNO AS CRSNO_UNIV, \n"+
	    		    "A.CRD AS CRD_UNIV, \n"+
	    		    "D.NAME, \n"+		
					"A.AYEAR AS GRADE_OLD, \n"+
					"DECODE(A.SMS,'1','2','2','3','3','4','')  AS SMS_OLD, \n"+
					"'0'AS GRAD_TYPE, \n"+
					"'"+SCHOOL_TYPE+"' AS SCHOOL_TYPE, \n"+
					"'"+HEDU_TYPE+"' AS HEDU_TYPE \n"+
					"FROM STUT010 A \n"+
					"JOIN COUT002 B ON A.CRSNO = B.CRSNO   \n"+					
					"JOIN STUT002 D ON 1=1 AND A.IDNO = D.IDNO  \n"+
					"LEFT JOIN SYST001 E ON E.KIND ='SMS' AND A.SMS = E.CODE \n"+
					"LEFT JOIN SYST001 F ON F.KIND ='ASYS' AND A.ASYS = F.CODE \n"+
					"LEFT JOIN COUT031 G ON A.CRSNO = G.CRSNO_OLD    \n"+
					"LEFT JOIN COUT002 H ON G.CRSNO = H.CRSNO \n"+
					"WHERE A.GET_MANNER ='3' \n"+
					"AND A.IDNO = '" + IDNO  + "' \n"+
					"AND ( (A.STNO != '" + STNO  + "' OR SUBSTR(A.OLD_STNO,2) = SUBSTR('" + STNO + "',2) ) OR A.GET_MANNER IN ('3','4','5','6')) \n"+
					//"AND NOT EXISTS ( SELECT 1 FROM CCST012 C012 WHERE 1=1  AND A.CRSNO = C012.CRSNO AND C012.STNO = " + STNO  + " AND NVL(TRIM(C012.RE_AUDIT_STATUS),'0') in('0','1')  ) \n"
					"AND NOT EXISTS ( SELECT 1 FROM CCST012 C012 WHERE C012.AYEAR = '"+AYEAR+"' AND C012.SMS = '"+SMS+"' AND ( A.CRSNO = C012.CRSNO OR A.CRSNO = C012.CRSNO_UNIV ) AND C012.STNO = '" + STNO  + "' AND NVL(TRIM(C012.RE_AUDIT_STATUS),'0') in('0','1')  ) \n"+
					"AND NOT EXISTS ( SELECT 1 FROM CCST003 C03 WHERE ( A.CRSNO = C03.CRSNO OR A.CRSNO = H.CRSNO ) AND C03.STNO = " + STNO  + " ) \n"
	           );
	           sql.append("ORDER BY A.ASYS,A.STNO,A.AYEAR,DECODE(A.SMS,'3','0',A.SMS) ");
	   	   }
	   	   else if("7".equals(SCHOOL_TYPE)){  //空大肄畢業
	   	   		sql.append(																																																														
		    		    "SELECT \n" +
		    		    "A.IDNO,A.BIRTHDATE, \n"+
		    		    "A.AYEAR, \n"+
		    		    "A.SMS,A.ASYS,A.STNO,A.MARK, \n"+
		    		    "A.GET_MANNER, \n"+
		    		    "A.OLD_STNO, \n"+
		    		    "TO_NUMBER(A.AYEAR) AS AYEAR_NAME, \n"+
		    		    "E.CODE_NAME AS SMS_NAME, \n"+
		    		    "F.CODE_NAME AS ASYS_NAME, \n"+
		    		    "B.CRS_NAME,  \n"+
		    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"' AND TRIM(H.CRSNO) IS NULL AND B.FACULTY_CODE = '90' THEN 'N'  ELSE 'Y' END AS GEL_CHK, \n"+
		    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"' THEN NVL(H.CRSNO,A.CRSNO)  ELSE A.CRSNO END AS CRSNO, \n"+
		    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"' THEN TO_CHAR(NVL(H.CRD,A.CRD))  ELSE TO_CHAR(A.CRD) END AS CRD, \n"+
		    		    "A.CRSNO AS CRSNO_UNIV, \n"+
		    		    "A.CRD AS CRD_UNIV, \n"+
		    		    "D.NAME, \n"+
						"A.AYEAR AS GRADE_OLD, \n"+
						"DECODE(A.SMS,'1','2','2','3','3','4','')  AS SMS_OLD, \n"+
						"DECODE(ENROLL_STATUS,'5','1','0') AS GRAD_TYPE, \n"+
						"'"+SCHOOL_TYPE+"' AS SCHOOL_TYPE, \n"+
						"'"+HEDU_TYPE+"' AS HEDU_TYPE \n"+
						"FROM STUT010 A \n"+
						"JOIN COUT002 B ON A.CRSNO = B.CRSNO   \n"+
						"JOIN STUT003 C ON 1=1 AND C.STNO=A.STNO \n"+
						"JOIN STUT002 D ON 1=1 AND D.IDNO = C.IDNO AND D.BIRTHDATE=C.BIRTHDATE  \n"+
						"LEFT JOIN SYST001 E ON E.KIND ='SMS' AND A.SMS = E.CODE \n"+
						"LEFT JOIN SYST001 F ON F.KIND ='ASYS' AND A.ASYS = F.CODE \n"+
						"LEFT JOIN COUT031 G ON A.CRSNO = G.CRSNO_OLD    \n"+
						"LEFT JOIN COUT002 H ON G.CRSNO = H.CRSNO \n"+
						"WHERE 1=1 \n"+
						"AND A.GET_MANNER ='1' AND A.ASYS = '1' \n"+
						"AND A.IDNO = '" + IDNO  + "' \n"+
						"AND ( (A.STNO != '" + STNO  + "' OR SUBSTR(A.OLD_STNO,2) = SUBSTR('" + STNO + "',2) ) OR A.GET_MANNER IN ('3','4','5','6')) \n"+
						//"AND NOT EXISTS ( SELECT 1 FROM CCST012 C012 WHERE 1=1  AND A.CRSNO = C012.CRSNO AND C012.STNO = '" + Utility.dbStr(ht.get("STNO"))  + "' AND NVL(TRIM(C012.RE_AUDIT_STATUS),'0') in('0','1')  ) \n"
						"AND NOT EXISTS ( SELECT 1 FROM CCST012 C012 WHERE C012.AYEAR = '"+AYEAR+"' AND C012.SMS = '"+SMS+"' AND ( A.CRSNO = C012.CRSNO OR A.CRSNO = C012.CRSNO_UNIV ) AND C012.STNO = '" + STNO  + "' AND NVL(TRIM(C012.RE_AUDIT_STATUS),'0') in('0','1')  ) \n"+
						"AND NOT EXISTS ( SELECT 1 FROM CCST003 C03 WHERE  ( A.CRSNO = C03.CRSNO OR A.CRSNO = H.CRSNO ) AND C03.STNO = '" + STNO  + "' ) \n"+
						" \n"
	   	   				);
	   	   			if(STNO_LEN==9){
	   	   			sql.append(
						"AND A.CRSNO NOT IN ( \n"+
						"					SELECT SCDT004.CRSNO \n"+
						"					FROM scdt004 \n"+
						"					JOIN stut003 ON scdt004.stno = stut003.stno \n"+
						"					JOIN cout001 ON cout001.ayear = scdt004.ayear AND cout001.sms = scdt004.sms AND cout001.crsno = scdt004.crsno \n"+
						"					LEFT JOIN  (  \n"+
						"						select grat003.AYEAR,grat003.SMS,grat003.STNO,grat004.CRSNO,grat004.ADOPT_FACULTY from grat003 \n"+
						"						JOIN grat004 ON grat003.ayear = grat004.ayear AND grat003.sms = grat004.sms AND grat003.stno = grat004.stno and grat004.ADOPT_FACULTY is not null \n"+
						"						WHERE 1=1 AND grat003.GRAD_REEXAM_STATUS ='2' AND GRAT003.IDNO = '" + IDNO  + "' \n"+
						"						 ) a ON scdt004.stno = a.stno AND scdt004.crsno = a.crsno \n"+
						"					WHERE  1=1 AND STUT003.IDNO = '" + IDNO  + "' \n"+
						"						AND STUT003.STTYPE = '1' AND STUT003.ENROLL_STATUS = '5' \n"+
						"					AND (A.ADOPT_FACULTY IN (SELECT R1.GRAD_MAJOR_FACULTY FROM GRAT003 R1 WHERE R1.IDNO = '" + IDNO  + "' AND R1.GRAD_REEXAM_STATUS = '2' \n"+
						"							UNION SELECT R2.DBMAJOR_GRAD_FACULTY_CODE1 FROM GRAT003 R2 WHERE R2.IDNO = '" + IDNO  + "' AND R2.GRAD_REEXAM_STATUS = '2' \n"+
						"							UNION SELECT R3.DBMAJOR_GRAD_FACULTY_CODE2 FROM GRAT003 R3 WHERE R3.IDNO = '" + IDNO  + "' AND R3.GRAD_REEXAM_STATUS = '2') \n"+
						"					  OR (COUT001.FACULTY_CODE IN (SELECT R1.GRAD_MAJOR_FACULTY FROM GRAT003 R1 WHERE R1.IDNO = '" + IDNO  + "' AND R1.GRAD_REEXAM_STATUS = '2' \n"+
						"							UNION SELECT R2.DBMAJOR_GRAD_FACULTY_CODE1 FROM GRAT003 R2 WHERE R2.IDNO = '" + IDNO  + "' AND R2.GRAD_REEXAM_STATUS = '2' \n"+
						"							UNION SELECT R3.DBMAJOR_GRAD_FACULTY_CODE2 FROM GRAT003 R3 WHERE R3.IDNO = '" + IDNO  + "' AND R3.GRAD_REEXAM_STATUS = '2')))  \n"+
						"						 )  \n"+
						" \n"
	   	   				);
	   	   			}
		           sql.append("ORDER BY A.ASYS,A.STNO,A.AYEAR,DECODE(A.SMS,'3','0',A.SMS) ");
	   	   }else{
	   		   if("5".equals(SCHOOL_TYPE)){			
		   	       condition = " AND A.GET_MANNER ='4' ";	   	      		   
		   	   //}else if("7".equals(SCHOOL_TYPE)){
		   	   //	   condition = " AND A.GET_MANNER ='1' AND A.ASYS = '1' ";
		   	   }else if("8".equals(SCHOOL_TYPE)){
		   		   condition = " AND A.GET_MANNER IN ('1','4') AND A.ASYS = '2' ";
		   	   }else if("10".equals(SCHOOL_TYPE)){
		   		   condition = " AND A.GET_MANNER ='5' ";
		   	   }else if("11".equals(SCHOOL_TYPE)){
		   		   condition = " AND A.GET_MANNER ='6' ";
		   	   }else{
		   		   condition = " AND 1=2 ";
		   	   }
	   		   
   		   sql.append(																																																														
	    		    "SELECT \n" +
	    		    "A.IDNO,A.BIRTHDATE, \n"+
	    		    "A.AYEAR, \n"+
	    		    "A.SMS,A.ASYS,A.STNO,A.MARK, \n"+
	    		    "A.GET_MANNER, \n"+
	    		    "A.OLD_STNO, \n"+
	    		    "TO_NUMBER(A.AYEAR) AS AYEAR_NAME, \n"+
	    		    "E.CODE_NAME AS SMS_NAME, \n"+
	    		    "F.CODE_NAME AS ASYS_NAME, \n"+
	    		    "B.CRS_NAME,  \n"+
	    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"' AND TRIM(H.CRSNO) IS NULL AND B.FACULTY_CODE = '90' THEN 'N'  ELSE 'Y' END AS GEL_CHK, \n"+
	    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"' THEN NVL(H.CRSNO,A.CRSNO)  ELSE A.CRSNO END AS CRSNO, \n"+
	    		    "CASE WHEN NVL((SELECT X.ENROLL_AYEARSMS FROM STUT003 X WHERE X.STNO = '"+STNO+"' AND X.ASYS = '1' ),'0000')  > '"+ym+"' THEN TO_CHAR(NVL(H.CRD,A.CRD))  ELSE TO_CHAR(A.CRD) END AS CRD, \n"+
	    		    "A.CRSNO AS CRSNO_UNIV, \n"+
	    		    "A.CRD AS CRD_UNIV, \n"+
	    		    "D.NAME, \n"+
					"A.AYEAR AS GRADE_OLD, \n"+
					"DECODE(A.SMS,'1','2','2','3','3','4','')  AS SMS_OLD, \n"+
					"DECODE(ENROLL_STATUS,'5','1','0') AS GRAD_TYPE, \n"+
					"'"+SCHOOL_TYPE+"' AS SCHOOL_TYPE, \n"+
					"'"+HEDU_TYPE+"' AS HEDU_TYPE \n"+
					"FROM STUT010 A \n"+
					"JOIN COUT002 B ON A.CRSNO = B.CRSNO   \n"+
					"JOIN STUT003 C ON 1=1 AND C.STNO=A.STNO \n"+
					"JOIN STUT002 D ON 1=1 AND D.IDNO = C.IDNO AND D.BIRTHDATE=C.BIRTHDATE  \n"+
					"LEFT JOIN SYST001 E ON E.KIND ='SMS' AND A.SMS = E.CODE \n"+
					"LEFT JOIN SYST001 F ON F.KIND ='ASYS' AND A.ASYS = F.CODE \n"+
					"LEFT JOIN COUT031 G ON A.CRSNO = G.CRSNO_OLD    \n"+
					"LEFT JOIN COUT002 H ON G.CRSNO = H.CRSNO \n"+
					"WHERE 1=1 "+condition+"\n"+
					"AND A.IDNO = '" + IDNO  + "' \n"+
					"AND ( (A.STNO != '" + STNO  + "' OR SUBSTR(A.OLD_STNO,2) = SUBSTR('" + STNO + "',2) ) OR A.GET_MANNER IN ('3','4','5','6')) \n"+
					//"AND NOT EXISTS ( SELECT 1 FROM CCST012 C012 WHERE 1=1  AND A.CRSNO = C012.CRSNO AND C012.STNO = '" + Utility.dbStr(ht.get("STNO"))  + "' AND NVL(TRIM(C012.RE_AUDIT_STATUS),'0') in('0','1')  ) \n"
					"AND NOT EXISTS ( SELECT 1 FROM CCST012 C012 WHERE C012.AYEAR = '"+AYEAR+"' AND C012.SMS = '"+SMS+"' AND ( A.CRSNO = C012.CRSNO OR A.CRSNO = C012.CRSNO_UNIV ) AND C012.STNO = '" + STNO  + "' AND NVL(TRIM(C012.RE_AUDIT_STATUS),'0') in('0','1')  ) \n"+
					"AND NOT EXISTS ( SELECT 1 FROM CCST003 C03 WHERE  ( A.CRSNO = C03.CRSNO OR A.CRSNO = H.CRSNO ) AND C03.STNO = '" + STNO  + "' ) \n"
	           );
	           sql.append("ORDER BY A.ASYS,A.STNO,A.AYEAR,DECODE(A.SMS,'3','0',A.SMS) ");
	           
	   	   }           
         
           DBResult rs = null;
           try {
               if(pageQuery) {
                   // 依分頁取出資料
                   rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
               } else {
                   // 取出所有資料
                   rs = dbmanager.getSimpleResultSet(conn);
                   rs.open();
                   rs.executeQuery(sql.toString());
               }
               Hashtable rowHt = null;
               
               Hashtable ht1 = new Hashtable();
               
               while (rs.next()) {
                   rowHt = new Hashtable();
                   //重複的科目不加入
                   if( !ht1.containsKey(rs.getString("CRSNO"))){
                	   /** 將欄位抄一份過去 */
                       for (int i = 1; i <= rs.getColumnCount(); i++){
                           rowHt.put(rs.getColumnName(i), rs.getString(i));
                       }
                       result.add(rowHt);
                   }
                   ht1.put(rs.getString("CRSNO"), rs.getString("MARK"));
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }
       
       /**
        * 查詢學程修課狀況
        */
       public Vector getGra052qQuery(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
		sql.append("  SELECT M.FACULTY_CODE ");
		sql.append("       , M.FACULTY_NAME ");
		sql.append("       , M.TOTAL_CRS_NO ");
		sql.append("       , M.TOTAL_CRS_CH ");
		sql.append("       , DECODE (D.TOTAL_CRS_CH_MK, 'N', '', TOTAL_CRS_CH_MK) AS TOTAL_CRS_CH_MK ");
		sql.append("       , M.STNO ");
		sql.append("       , '至少需選修' || M.CRD_NUM || '學分，已修' || SUM (M.CRD) || '學分' AS GET_COND ");
		sql.append("       ,    '必修' ");
		sql.append("         || (  SELECT DECODE (A.CLASS_GROUP_COND, '1', SUM (C.CRD) || '學分', COUNT (B.CRSNO) || '科數') ");
		sql.append("                 FROM COUT018 A ");
		sql.append("                      JOIN COUT019 B ");
		sql.append("                         ON B.FACULTY_CODE = A.FACULTY_CODE AND B.TOTAL_CRS_NO = A.TOTAL_CRS_NO AND B.CLASS_GROUP_CODE = A.CLASS_GROUP_CODE ");
		sql.append("                      JOIN COUT002 C ");
		sql.append("                         ON C.CRSNO = B.CRSNO ");
		sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '1' AND A.CLASS_GROUP_COND = '1' AND A.CLASS_GROUP_AUDIT = '1' ");
		sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
		sql.append("         || (  SELECT DECODE (A.CLASS_GROUP_COND, '1', SUM (C.CRD) || '學分', COUNT (B.CRSNO) || '科數') ");
		sql.append("                 FROM COUT018 A ");
		sql.append("                      JOIN COUT019 B ");
		sql.append("                         ON B.FACULTY_CODE = A.FACULTY_CODE AND B.TOTAL_CRS_NO = A.TOTAL_CRS_NO AND B.CLASS_GROUP_CODE = A.CLASS_GROUP_CODE ");
		sql.append("                      JOIN COUT002 C ");
		sql.append("                         ON C.CRSNO = B.CRSNO ");
		sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '1' AND A.CLASS_GROUP_COND = '2' AND A.CLASS_GROUP_AUDIT = '1' ");
		sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
	
		sql.append("         || (  SELECT SUM (A.CRD_NUM) || DECODE (A.CLASS_GROUP_COND, '1', '學分', '科數') ");
		sql.append("                 FROM COUT018 A ");
		sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '1' AND A.CLASS_GROUP_AUDIT = '2' ");
		sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
		sql.append("         || '，已修' ");
		sql.append("            AS A_GET_COND_1 ");
		sql.append("       ,    '選修' ");
		sql.append("         || (  SELECT DECODE (A.CLASS_GROUP_COND, '1', SUM (C.CRD) || '學分', COUNT (B.CRSNO) || '科數') ");
		sql.append("                 FROM COUT018 A ");
		sql.append("                      JOIN COUT019 B ");
		sql.append("                         ON B.FACULTY_CODE = A.FACULTY_CODE AND B.TOTAL_CRS_NO = A.TOTAL_CRS_NO AND B.CLASS_GROUP_CODE = A.CLASS_GROUP_CODE ");
		sql.append("                      JOIN COUT002 C ");
		sql.append("                         ON C.CRSNO = B.CRSNO ");
		sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '2' AND A.CLASS_GROUP_AUDIT = '1' ");
		sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
		sql.append("         || (  SELECT SUM (A.CRD_NUM) || DECODE (A.CLASS_GROUP_COND, '1', '學分', '科數') ");
		sql.append("                 FROM COUT018 A ");
		sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '2' AND A.CLASS_GROUP_AUDIT = '2' ");
		sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
		sql.append("         || '，已修' ");
		sql.append("            AS B_GET_COND_1 ");
		//sql.append("    FROM GRAV035 M ");
		sql.append("   FROM (  SELECT DISTINCT  M.STNO 					");	//學號
		sql.append("    , R6.NAME                                        ");	//姓名
		sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
		sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
		sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
		sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
		sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
		sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
		sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
		sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
		sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
		sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
		sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
		sql.append("    ,                                                       ");
		sql.append("    (                                                       ");
		sql.append("      SELECT SUM (C2.CRD)                                   ");
		sql.append("      FROM COUT019 C1                                       ");
		sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
		sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
		sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
		sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
		sql.append("    ,                                                       ");
		sql.append("    (                                                       ");
		sql.append("      SELECT COUNT (1)                                      ");
		sql.append("      FROM COUT019 C1                                       ");
		sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
		sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
		sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
		sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
		sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
		sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
		sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
		sql.append("    , R2.GRP 		                                       "); //組別
		//抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
		sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
		sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
		sql.append("    , M.CRSNO                                                                                        		"); //科目代號
		sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
		sql.append("    , M.CRD 	                                                                                                "); //學分數
		//抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
		sql.append("    ,                                                                                                               ");
		sql.append("    CASE                                                                                                            ");
		sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
		sql.append("        THEN                                                                                                        ");
		sql.append("      CASE                                                                                                          ");
		sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
		sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
		sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
		sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
		sql.append("      ELSE '抵免'                                                                                                   ");
		sql.append("      END                                                                                                           ");
		sql.append("    ELSE                                                                                                            ");
		sql.append("      TO_CHAR (M.MARK)                                                                                              ");
		sql.append("    END                                                                                                             ");
		sql.append("    AS MARK 																											"); //成績
		sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
		sql.append("    FROM STUT012 M																									");
		sql.append("    JOIN                                                                                                            ");
		sql.append("    (                                                                                                               ");
		sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
		sql.append("      FROM COUT019 C1                                                                                               ");
		sql.append("      UNION                                                                                                         ");
		sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
		sql.append("      FROM COUT020 C2                                                                                               ");
		sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
		sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
		sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
		sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
		sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
		sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
		sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
		sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
		sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
		sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");
		// GRA052Q GRAV035 End
		sql.append("    JOIN COUT016 D ON D.FACULTY_CODE = M.FACULTY_CODE AND D.TOTAL_CRS_NO = M.TOTAL_CRS_NO  AND D.TOTAL_CRS_KIND = '01' AND TO_CHAR (SYSDATE, 'YYYYMMDD') < D.STOP_APPLY_DATE ");
		sql.append("   WHERE M.STNO = '"
				+ Utility.dbStr(UtilityX.checkNullEmpty(ht.get("STNO"), " "))
				+ "' ");
		sql.append("GROUP BY M.FACULTY_CODE, M.FACULTY_NAME, M.TOTAL_CRS_NO, M.TOTAL_CRS_CH, TOTAL_CRS_CH_MK, M.CRD_NUM, M.STNO ");
		sql.append("ORDER BY M.FACULTY_CODE, M.TOTAL_CRS_NO ");

           
           DBResult rs = null;
           try {
               if(pageQuery) {
                   // 依分頁取出資料
                   rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
               } else {
                   // 取出所有資料
                   rs = dbmanager.getSimpleResultSet(conn);
                   rs.open();
                   rs.executeQuery(sql.toString());
               }
               Hashtable rowHt = null;
               while (rs.next()) {
                   rowHt = new Hashtable();
                   /** 將欄位抄一份過去 */
                   for (int i = 1; i <= rs.getColumnCount(); i++){
                       rowHt.put(rs.getColumnName(i), rs.getString(i));
                   }
                   
                   //取得真正通過的學分數
                   /*
            	   STUT010GATEWAY S010 = new STUT010GATEWAY(dbmanager,conn);
            	   Hashtable htt = S010.getPassCrd(rowHt);
            	   System.out.println("htt="+htt);
            	   
            	   rowHt.put("A_GET_COND", rs.getString("A_GET_COND_1")+Utility.checkNull(htt.get("1"), "")+rs.getString("A_GET_COND_2"));
            	   rowHt.put("B_GET_COND", rs.getString("B_GET_COND_1")+Utility.checkNull(htt.get("2"), "")+rs.getString("B_GET_COND_2"));
                   */
                   result.add(rowHt);
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }
       
       /**
        * 取得學生姓名
        * @param ht
        * @return
        * @throws Exception
        */
       public Vector getGra052QStnoName(Hashtable ht) throws Exception {
           if(ht == null) {
               ht = new Hashtable();
           }
           Vector result = new Vector();
           if(sql.length() > 0) {
               sql.delete(0, sql.length());
           }

           sql.append(" SELECT R1.*,NVL(R2.STTYPE,M.STTYPE) AS STTYPE, M.ENROLL_STATUS ");
           sql.append(" FROM STUT003 M  ");
           sql.append(" 		LEFT JOIN STUT002 R1  ");
           sql.append(" 		  ON R1.IDNO = M.IDNO  ");
           sql.append(" 		LEFT JOIN STUT004 R2 ON R2.AYEAR = '"+UtilityX.checkNullEmpty(ht.get("NOW_AYEAR")," ") +"' AND R2.SMS = '"+UtilityX.checkNullEmpty(ht.get("NOW_SMS")," ") +"' AND R2.STNO = M.STNO ");
           sql.append(" WHERE M.STNO = '").append( Utility.nullToSpace(ht.get("STNO")) ).append("' ");
           
           
           DBResult rs = null;
           try {
        	   // 取出所有資料
               rs = dbmanager.getSimpleResultSet(conn);
               rs.open();
               rs.executeQuery(sql.toString());
               
               Hashtable rowHt = null;
               while (rs.next()) {
                   rowHt = new Hashtable();
                   /** 將欄位抄一份過去 */
                   for (int i = 1; i <= rs.getColumnCount(); i++)
                       rowHt.put(rs.getColumnName(i), rs.getString(i));

                   result.add(rowHt);
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }
       
       /**
        * 取得學生姓名
        * @param ht
        * @return
        * @throws Exception
        */
       public Vector getScd216QStnoName(Hashtable ht) throws Exception {
           if(ht == null) {
               ht = new Hashtable();
           }
           Vector result = new Vector();
           if(sql.length() > 0) {
               sql.delete(0, sql.length());
           }

           sql.append(" SELECT R1.*,NVL(R2.STTYPE,M.STTYPE) AS STTYPE, M.ENROLL_STATUS ");
           sql.append(" FROM STUT003 M  ");
           sql.append(" 		LEFT JOIN STUT002 R1  ");
           sql.append(" 		  ON R1.IDNO = M.IDNO  ");
           sql.append(" 		LEFT JOIN STUT004 R2 ON R2.AYEAR = '"+UtilityX.checkNullEmpty(ht.get("NOW_AYEAR")," ") +"' AND R2.SMS = '"+UtilityX.checkNullEmpty(ht.get("NOW_SMS")," ") +"' AND R2.STNO = M.STNO ");
           sql.append(" WHERE M.STNO = '").append( Utility.nullToSpace(ht.get("STNO")) ).append("' ");
           
           
           DBResult rs = null;
           try {
        	   // 取出所有資料
               rs = dbmanager.getSimpleResultSet(conn);
               rs.open();
               rs.executeQuery(sql.toString());
               
               Hashtable rowHt = null;
               while (rs.next()) {
                   rowHt = new Hashtable();
                   /** 將欄位抄一份過去 */
                   for (int i = 1; i <= rs.getColumnCount(); i++)
                       rowHt.put(rs.getColumnName(i), rs.getString(i));

                   result.add(rowHt);
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }       
       
       /**
        * 學程修課資料1
        * @param ht
        * @return
        * @throws Exception
        */
       public Vector getGra053rPrint1(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
        
           String PROG_CODE = Utility.checkNull(ht.get("PROG_CODE"),"");
           String FACULTY_CODE  = Utility.checkNull(ht.get("FACULTY_CODE"),"");
           String TOTAL_CRS_NO  = Utility.checkNull(ht.get("TOTAL_CRS_NO"),"");
           
           
           sql.append("SELECT M.STNO,M.NAME, ");
           sql.append("	   M.FACULTY_CODE, ");
           sql.append("	   M.FACULTY_NAME, ");
           sql.append("	   M.TOTAL_CRS_NO, ");
           sql.append("	   M.TOTAL_CRS_CH, ");
           sql.append("	   DECODE(C16. TOTAL_CRS_CH_MK,'N','',TOTAL_CRS_CH_MK) AS  TOTAL_CRS_CH_MK, ");
           sql.append("	   C16.PROGSCR_RPT_RMK, ");
           sql.append("	   M.CRD_NUM AS GET_COND,  ");
           sql.append("	   M.NAME AS ST_NAME,  ");
           sql.append("	   SUM(M.CRD) AS ST_TOT_CRD ");
           //sql.append("  FROM GRAV035 M "); //20210224將view拉出來
           sql.append("   FROM (  SELECT DISTINCT  M.STNO 					");	//學號
           sql.append("    , R6.NAME                                        ");	//姓名
           sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
           sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
           sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
           sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
           sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
           sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
           sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
           sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
           sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
           sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
           sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT SUM (C2.CRD)                                   ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT COUNT (1)                                      ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
           sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
           sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
           sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
           sql.append("    , R2.GRP 		                                       "); //組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
           sql.append("    , M.CRSNO                                                                                        		"); //科目代號
           sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
           sql.append("    , M.CRD 	                                                                                                "); //學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
           sql.append("    ,                                                                                                               ");
           sql.append("    CASE                                                                                                            ");
           sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
           sql.append("        THEN                                                                                                        ");
           sql.append("      CASE                                                                                                          ");
           sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
           sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
           sql.append("      ELSE '抵免'                                                                                                   ");
           sql.append("      END                                                                                                           ");
           sql.append("    ELSE                                                                                                            ");
           sql.append("      TO_CHAR (M.MARK)                                                                                              ");
           sql.append("    END                                                                                                             ");
           sql.append("    AS MARK 																											"); //成績
           sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
           sql.append("    FROM STUT012 M																									");
           sql.append("    JOIN                                                                                                            ");
           sql.append("    (                                                                                                               ");
           sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
           sql.append("      FROM COUT019 C1                                                                                               ");
           sql.append("      UNION                                                                                                         ");
           sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
           sql.append("      FROM COUT020 C2                                                                                               ");
           sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
           sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
           sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
           sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
           sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
           sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
           sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
           sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
           sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
           sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");      
           //GRAV035的view end
           sql.append("  JOIN COUT016 C16 ON C16.TOTAL_CRS_NO=M.TOTAL_CRS_NO ");
           sql.append("  WHERE 1=1 ");
           
           DBResult rs = null;
           
           if("GRA051R".equals(PROG_CODE)){
        	   
        	   if(!"".equals(Utility.nullToSpace(ht.get("FACULTY_CODE")))){
            	   sql.append("  AND  M.FACULTY_CODE = '"+Utility.dbStr(FACULTY_CODE)+"' ");
               }

        	   if(!"".equals(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))){
            	   sql.append("  AND  M.TOTAL_CRS_NO = '"+Utility.dbStr(TOTAL_CRS_NO)+"' ");
               }
        	   
        	   /*
        	   sql.append("  AND EXISTS(" +
        	   			  "		SELECT 1 FROM COUT021 X " +
        	   			  "		WHERE X.STNO = M.STNO AND X.FACULTY_CODE = M.FACULTY_CODE AND X.TOTAL_CRS_NO = M.TOTAL_CRS_NO " +
        	   			  "		AND NOT EXISTS ( SELECT 1 FROM GRAT034 Y WHERE X.STNO = Y.STNO AND X.FACULTY_CODE = Y.FACULTY_CODE AND X.TOTAL_CRS_NO = Y.PROGRAM  AND Y.STATUS ='2'   )"+
        	   			  "  ) ");
        	   */
        	   
        	   sql.append(" AND M.STNO in (").append(this.getGra053rPrint1STNO(FACULTY_CODE, TOTAL_CRS_NO)).append(") ");
        	   
           }else{
        	   
               sql.append(" AND M.STNO IN ( '"+Utility.replace(Utility.dbStr(Utility.nullToSpace(ht.get("STNO"))), ",", "','")+"')  ");
               if(!"".equals(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))){
            	   sql.append("  AND  M.TOTAL_CRS_NO IN ( '"+Utility.replace(Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO"))), ",", "','")+"') ");
               }
           }
           
           sql.append(" GROUP BY M.STNO,M.NAME, ");
           sql.append("		  M.FACULTY_CODE, ");
           sql.append("		  M.FACULTY_NAME, ");
           sql.append("		  M.TOTAL_CRS_NO, ");
           sql.append("		  M.TOTAL_CRS_CH, ");
           sql.append("		  C16.TOTAL_CRS_CH_MK, ");
           sql.append("		  C16.PROGSCR_RPT_RMK, ");
           sql.append("		  M.CRD_NUM, ");
           sql.append("		  M.NAME ");
           sql.append(" ORDER BY M.STNO,M.FACULTY_CODE,M.TOTAL_CRS_NO ");
    	   
           try {
               if(pageQuery) {
                   // 依分頁取出資料
                   rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
               } else {
                   // 取出所有資料
                   rs = dbmanager.getSimpleResultSet(conn);
                   rs.open();
                   rs.executeQuery(sql.toString());
               }
               Hashtable rowHt = null;
               while (rs.next()) {
                   rowHt = new Hashtable();
                   /** 將欄位抄一份過去 */
                   for (int i = 1; i <= rs.getColumnCount(); i++){
                       rowHt.put(rs.getColumnName(i), rs.getString(i));
                   }
                   result.add(rowHt);
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }
       /**
        * 學程修課資料1
        * @param ht
        * @return
        * @throws Exception
        */
       public Vector getGra063rPrint1(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
        
           String PROG_CODE = Utility.checkNull(ht.get("PROG_CODE"),"");
           String FACULTY_CODE  = Utility.checkNull(ht.get("FACULTY_CODE"),"");
           String TOTAL_CRS_NO  = Utility.checkNull(ht.get("TOTAL_CRS_NO"),"");
           
           
           sql.append("SELECT M.STNO,M.NAME, ");
           sql.append("	   M.FACULTY_CODE, ");
           sql.append("	   M.FACULTY_NAME, ");
           sql.append("	   M.TOTAL_CRS_NO, ");
           sql.append("	   M.TOTAL_CRS_CH, ");
           sql.append("	   DECODE(C16. TOTAL_CRS_CH_MK,'N','',TOTAL_CRS_CH_MK) AS  TOTAL_CRS_CH_MK, ");
           sql.append("	   C16.PROGSCR_RPT_RMK, ");
           sql.append("	   M.CRD_NUM AS GET_COND,  ");
           sql.append("	   M.NAME AS ST_NAME,  ");
           sql.append("	   SUM(M.CRD) AS ST_TOT_CRD ");
           sql.append("  FROM GRAV035S M ");
           sql.append("  JOIN COUT016 C16 ON C16.TOTAL_CRS_NO=M.TOTAL_CRS_NO ");
           sql.append("  WHERE 1=1 ");
           
           DBResult rs = null;
           
           if("GRA051R".equals(PROG_CODE)){
        	   
        	   if(!"".equals(Utility.nullToSpace(ht.get("FACULTY_CODE")))){
            	   sql.append("  AND  M.FACULTY_CODE = '"+Utility.dbStr(FACULTY_CODE)+"' ");
               }

        	   if(!"".equals(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))){
            	   sql.append("  AND  M.TOTAL_CRS_NO = '"+Utility.dbStr(TOTAL_CRS_NO)+"' ");
               }
        	   
        	   /*
        	   sql.append("  AND EXISTS(" +
        	   			  "		SELECT 1 FROM COUT021 X " +
        	   			  "		WHERE X.STNO = M.STNO AND X.FACULTY_CODE = M.FACULTY_CODE AND X.TOTAL_CRS_NO = M.TOTAL_CRS_NO " +
        	   			  "		AND NOT EXISTS ( SELECT 1 FROM GRAT034 Y WHERE X.STNO = Y.STNO AND X.FACULTY_CODE = Y.FACULTY_CODE AND X.TOTAL_CRS_NO = Y.PROGRAM  AND Y.STATUS ='2'   )"+
        	   			  "  ) ");
        	   */
        	   
        	   sql.append(" AND M.STNO in (").append(this.getGra053rPrint1STNO(FACULTY_CODE, TOTAL_CRS_NO)).append(") ");
        	   
           }else{
        	   
               sql.append(" AND M.STNO IN ( '"+Utility.replace(Utility.dbStr(Utility.nullToSpace(ht.get("STNO"))), ",", "','")+"')  ");
               if(!"".equals(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))){
            	   sql.append("  AND  M.TOTAL_CRS_NO IN ( '"+Utility.replace(Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO"))), ",", "','")+"') ");
               }
           }
           
           sql.append(" GROUP BY M.STNO,M.NAME, ");
           sql.append("		  M.FACULTY_CODE, ");
           sql.append("		  M.FACULTY_NAME, ");
           sql.append("		  M.TOTAL_CRS_NO, ");
           sql.append("		  M.TOTAL_CRS_CH, ");
           sql.append("		  C16.TOTAL_CRS_CH_MK, ");
           sql.append("		  C16.PROGSCR_RPT_RMK, ");
           sql.append("		  M.CRD_NUM, ");
           sql.append("		  M.NAME ");
           sql.append(" ORDER BY M.STNO,M.FACULTY_CODE,M.TOTAL_CRS_NO ");
    	   
           try {
               if(pageQuery) {
                   // 依分頁取出資料
                   rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
               } else {
                   // 取出所有資料
                   rs = dbmanager.getSimpleResultSet(conn);
                   rs.open();
                   rs.executeQuery(sql.toString());
               }
               Hashtable rowHt = null;
               while (rs.next()) {
                   rowHt = new Hashtable();
                   /** 將欄位抄一份過去 */
                   for (int i = 1; i <= rs.getColumnCount(); i++){
                       rowHt.put(rs.getColumnName(i), rs.getString(i));
                   }
                   result.add(rowHt);
               }
           } catch (Exception e) {
               throw e;
           } finally {
               if(rs != null) {
                   rs.close();
               }
           }
           return result;
       }
       
       /**
        * 取得STNO
        * @param FACULTY_CODE
        * @param TOTAL_CRS_NO
        * @return
        * @throws Exception
        */
       private String getGra053rPrint1STNO(String FACULTY_CODE, String TOTAL_CRS_NO)throws Exception{
    	   if("".equals(FACULTY_CODE) || "".equals(TOTAL_CRS_NO)){
    		   return "";
    	   }
    	   StringBuffer sb = new StringBuffer();
    	   StringBuffer rtnStr = new StringBuffer();
    	   try {
    		   DBResult rs = null;
    		   
    		   sb.append(" SELECT   X.STNO from COUT021 X ");
    		   sb.append(" WHERE X.FACULTY_CODE = '").append( Utility.dbStr(FACULTY_CODE) ).append("' ");
    		   sb.append("   AND X.TOTAL_CRS_NO = '").append(Utility.dbStr( TOTAL_CRS_NO) ).append("' ");
    		   sb.append(" AND NOT EXISTS  ");
    		   sb.append(" ( SELECT 1 FROM GRAT034 Y WHERE X.STNO = Y.STNO AND X.FACULTY_CODE = Y.FACULTY_CODE AND X.TOTAL_CRS_NO = Y.PROGRAM  AND Y.STATUS ='2')  ");
    		   
    		   rs = dbmanager.getSimpleResultSet(conn);
               rs.open();
               rs.executeQuery(sb.toString());
               
               while (rs.next()) {
            	   rtnStr.append(",'").append(rs.getString("STNO")).append("' ");
               }
               
               return rtnStr.length()>0?rtnStr.substring(1):"";
           } catch (Exception e) {
               throw e;
           } finally {
               if(sb != null) {
            	   sb.setLength(0);
            	   sb = null;
               }
               if(rtnStr != null) {
            	   rtnStr.setLength(0);
            	   rtnStr = null;
               }
           }
       }
       
       /**
        * 學程修課資料2
        * @param ht
        * @return
        * @throws Exception
      	*/
       
       public Vector getGra053rPrint2(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
           sql.append("SELECT DISTINCT");
           sql.append("	   R8.FACULTY_CODE, ");
           sql.append("	   R10.FACULTY_NAME, ");
           sql.append("	   R1.TOTAL_CRS_NO, ");
           sql.append("	   R9.TOTAL_CRS_CH, ");
           sql.append("	   R8.CLASS_GROUP_CODE, ");
           sql.append("	   R8.CLASS_GROUP_NAME, ");
           sql.append("	   R8.IS_REQUIRED, ");
           sql.append("	   CASE WHEN R8.CLASS_GROUP_AUDIT = '1' THEN '全部通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_TOT_CRS || '科次' ");
           sql.append("			END ");
           sql.append("			WHEN R8.CLASS_GROUP_AUDIT = '2' THEN '至少通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_COND_CRD || '科次' ");
           sql.append("			END ");
           sql.append("	   END AS GROUP_COND, ");
           sql.append("	   R3.CRSNO, ");
           sql.append("	   R3.CRS_NAME,  ");
           sql.append("	   R3.CRD,  ");
           sql.append("	   R4.CRSNO AS MULTI_CRSNO,  ");
           sql.append("	   R4.CRSNO||R4.CRS_NAME AS MULTI_CRS_NAME, ");
           sql.append("	   R4.CRD AS MULTI_CRD,  ");
           sql.append("	   R1.GRP, ");
           sql.append("	   R5.GET_AYEAR, ");
           sql.append("	   R5.GET_SMS, ");
           sql.append("	   R5.CRSNO AS GET_CRSNO, ");
           sql.append("	   R5.CRS_NAME AS GET_CRS_NAME, ");
           sql.append("	   R5.CRD AS GET_CRD,  ");
           sql.append("	   R5.MARK,  ");
           sql.append("	   R6.CODE_NAME AS IS_REQUIRED_NAME,  ");
           sql.append("    R8.CLASS_GROUP_COND,");
           sql.append("	   R7.GRP_NUM ");
           sql.append("  FROM COUT019 R1 ");
		   sql.append("  LEFT JOIN COUT018 R8 ");
           sql.append("	ON R1.FACULTY_CODE = R8.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = R8.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = R8.CLASS_GROUP_CODE ");
           //sql.append("  LEFT JOIN GRAV034 M ");
           sql.append("    LEFT JOIN (								");
           sql.append("         SELECT                     ");
           sql.append("         DISTINCT                   ");
           sql.append("         M.STNO                                         ");//學號
           sql.append("         , R6.NAME                                        ");//姓名
           sql.append("         , R5.FACULTY_CODE 	                              ");//學系代碼
           sql.append("         , R5.FACULTY_ABBRNAME 		                      ");//學系簡稱
           sql.append("         , R5.FACULTY_NAME 		                          ");//學系名稱
           sql.append("         , R4.TOTAL_CRS_NO 		                          ");//學程代碼
           sql.append("         , R4.TOTAL_CRS_KIND 	                          ");//學程類別
           sql.append("         , R4.TOTAL_CRS_CH 		                          ");//學程名稱
           sql.append("         , R4.CRD_NUM  		                              ");//學分數(科次)
           sql.append("         , R4.APP_ITEM_CODE 	                          ");//證件代碼
           sql.append("         , R3.CLASS_GROUP_CODE 		                      ");//課群代碼
           sql.append("         , R3.CLASS_GROUP_NAME 		                      ");//課群名稱
           sql.append("         , R3.CLASS_GROUP_COND 		                      ");//課群條件
           sql.append("         , R3.CLASS_GROUP_AUDIT 	                      ");//課群審核
           sql.append("         ,                                                      ");
           sql.append("         (                                                      ");
           sql.append("           SELECT SUM (C2.CRD)                                  ");
           sql.append("           FROM COUT019 C1                                      ");
           sql.append("           JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO               ");
           sql.append("           WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO              ");
           sql.append("           AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE        ");
           sql.append("         ) AS GROUP_TOT_CRD  			                        ");//課程總學分
           sql.append("         ,                                                      ");
           sql.append("         (                                                      ");
           sql.append("           SELECT COUNT (1)                                     ");
           sql.append("           FROM COUT019 C1                                      ");
           sql.append("           JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO               ");
           sql.append("           WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO              ");
           sql.append("           AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE        ");
           sql.append("         ) AS GROUP_TOT_CRS 			                        ");//課程總科次
           sql.append("         , R3.CRD_NUM AS GROUP_COND_CRD 			            ");//課群條件學分
           sql.append("         , R3.IS_REQUIRED 		                                ");//必選修
           sql.append("         , R2.CRSNO AS COUT019_CRSNO 				            ");//學程主要科目
           sql.append("         , R2.GRP 		                                        ");//組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期
           sql.append("         , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 			");//取得學年
           sql.append("         , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS       			");//取得學期
           sql.append("         , M.CRSNO 			                                                                                        ");//科目代碼
           sql.append("         , R7.CRS_NAME 			                                                                                    ");//科目名稱
           sql.append("         , M.CRD 			                                                                                        ");//學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)
           sql.append("         ,                                                                                                          ");
           sql.append("         CASE                                                                                                       ");
           sql.append("           WHEN M.GET_MANNER = '2'                                                                                  ");
           sql.append("             THEN                                                                                                   ");
           sql.append("           CASE                                                                                                     ");
           sql.append("             WHEN R8.AYEAR < '098' THEN '抵免'                                                                      ");
           sql.append("             WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                     ");
           sql.append("             WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                   ");
           sql.append("             WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                        ");
           sql.append("           ELSE '抵免'                                                                                              ");
           sql.append("           END                                                                                                      ");
           sql.append("         ELSE                                                                                                       ");
           sql.append("           TO_CHAR (M.MARK)                                                                                         ");
           sql.append("         END                                                                                                        ");
           sql.append("         AS MARK 		                                                                                            ");//成績
           sql.append("         , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS ");//最大取得學年期
           sql.append("         FROM STUT012 M																								");
           sql.append("         JOIN                                                                                                       ");
           sql.append("         (                                                                                                          ");
           sql.append("           SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                       ");
           sql.append("           FROM COUT019 C1                                                                                          ");
           sql.append("           UNION                                                                                                    ");
           sql.append("           SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                 ");
           sql.append("           FROM COUT020 C2                                                                                          ");
           sql.append("           JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                             ");
           sql.append("         ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                             ");
           sql.append("         JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE ");
           //sql.append("         JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.TOTAL_CRS_KIND = '01' AND R4.ANNO_MK = '1' ");//取已公告的學程
           sql.append("         JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.TOTAL_CRS_KIND = '01' AND R4.ANNO_MK = '1' ");//取已公告的學程
           sql.append("         JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE														");
           sql.append("         JOIN STUT003 R9 ON R9.STNO = M.GET_STNO AND R9.STTYPE = '1'                                                ");
           sql.append("         JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                        ");
           sql.append("         JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                      ");
           sql.append("         LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.GET_STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5') ");
           sql.append("         WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' 												");
           sql.append("         AND r4.total_crs_no = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"'											");
           sql.append("         AND EXISTS(																");
           sql.append("                 SELECT 1 FROM GRAT035 X WHERE X.STNO = M.STNO AND X.CRSNO = M.CRSNO AND X.ASYS = M.ASYS AND X.PROGRAM = R4.TOTAL_CRS_NO ");
           sql.append("                  )														                                                                 ");
           sql.append("     ) M                                                                                                                                ");
           //GRAV034 M END
           sql.append("	ON R1.FACULTY_CODE = M.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE ");
           sql.append("	  AND M.STNO =  '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' "); 
           //取得主要科目出現申請表錯誤
           //sql.append("  LEFT JOIN COUT020 R2 ");
           
           sql.append(" LEFT JOIN ( ");
           sql.append("  	SELECT X2.FACULTY_CODE, X2.TOTAL_CRS_NO, X2.CRSNO, X2.MULTI_CRSNO, X2.CLASS_GROUP_CODE FROM COUT020 X2 WHERE X2.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
           sql.append("  	UNION ALL ");
           sql.append("  	SELECT X1.FACULTY_CODE, X1.TOTAL_CRS_NO, X1.CRSNO, X1.CRSNO AS MULTI_CRSNO, X1.CLASS_GROUP_CODE FROM COUT019 X1 WHERE X1.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
           sql.append("  	ORDER BY 1,5,3 ");
           sql.append("  	) R2  ");
           sql.append("	ON R2.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append("   AND R2.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append("   AND R2.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
           sql.append("   AND R2.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R3 ");
           sql.append("	ON R3.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R4 ");
           sql.append("	ON R4.CRSNO = R2.MULTI_CRSNO ");
           //sql.append("  LEFT JOIN GRAV034 R5 ");
           sql.append(" LEFT JOIN (								");
           sql.append("         SELECT                     ");
           sql.append("         DISTINCT                   ");
           sql.append("         M.STNO                                         ");//學號
           sql.append("         , R6.NAME                                        ");//姓名
           sql.append("         , R5.FACULTY_CODE 	                              ");//學系代碼
           sql.append("         , R5.FACULTY_ABBRNAME 		                      ");//學系簡稱
           sql.append("         , R5.FACULTY_NAME 		                          ");//學系名稱
           sql.append("         , R4.TOTAL_CRS_NO 		                          ");//學程代碼
           sql.append("         , R4.TOTAL_CRS_KIND 	                          ");//學程類別
           sql.append("         , R4.TOTAL_CRS_CH 		                          ");//學程名稱
           sql.append("         , R4.CRD_NUM  		                              ");//學分數(科次)
           sql.append("         , R4.APP_ITEM_CODE 	                          ");//證件代碼
           sql.append("         , R3.CLASS_GROUP_CODE 		                      ");//課群代碼
           sql.append("         , R3.CLASS_GROUP_NAME 		                      ");//課群名稱
           sql.append("         , R3.CLASS_GROUP_COND 		                      ");//課群條件
           sql.append("         , R3.CLASS_GROUP_AUDIT 	                      ");//課群審核
           sql.append("         ,                                                      ");
           sql.append("         (                                                      ");
           sql.append("           SELECT SUM (C2.CRD)                                  ");
           sql.append("           FROM COUT019 C1                                      ");
           sql.append("           JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO               ");
           sql.append("           WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO              ");
           sql.append("           AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE        ");
           sql.append("         ) AS GROUP_TOT_CRD  			                        ");//課程總學分
           sql.append("         ,                                                      ");
           sql.append("         (                                                      ");
           sql.append("           SELECT COUNT (1)                                     ");
           sql.append("           FROM COUT019 C1                                      ");
           sql.append("           JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO               ");
           sql.append("           WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO              ");
           sql.append("           AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE        ");
           sql.append("         ) AS GROUP_TOT_CRS 			                        ");//課程總科次
           sql.append("         , R3.CRD_NUM AS GROUP_COND_CRD 			            ");//課群條件學分
           sql.append("         , R3.IS_REQUIRED 		                                ");//必選修
           sql.append("         , R2.CRSNO AS COUT019_CRSNO 				            ");//學程主要科目
           sql.append("         , R2.GRP 		                                        ");//組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期
           sql.append("         , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 			");//取得學年
           sql.append("         , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS       			");//取得學期
           sql.append("         , M.CRSNO 			                                                                                        ");//科目代碼
           sql.append("         , R7.CRS_NAME 			                                                                                    ");//科目名稱
           sql.append("         , M.CRD 			                                                                                        ");//學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)
           sql.append("         ,                                                                                                          ");
           sql.append("         CASE                                                                                                       ");
           sql.append("           WHEN M.GET_MANNER = '2'                                                                                  ");
           sql.append("             THEN                                                                                                   ");
           sql.append("           CASE                                                                                                     ");
           sql.append("             WHEN R8.AYEAR < '098' THEN '抵免'                                                                      ");
           sql.append("             WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                     ");
           sql.append("             WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                   ");
           sql.append("             WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                        ");
           sql.append("           ELSE '抵免'                                                                                              ");
           sql.append("           END                                                                                                      ");
           sql.append("         ELSE                                                                                                       ");
           sql.append("           TO_CHAR (M.MARK)                                                                                         ");
           sql.append("         END                                                                                                        ");
           sql.append("         AS MARK 		                                                                                            ");//成績
           sql.append("         , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS ");//最大取得學年期
           sql.append("         FROM STUT012 M																								");
           sql.append("         JOIN                                                                                                       ");
           sql.append("         (                                                                                                          ");
           sql.append("           SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                       ");
           sql.append("           FROM COUT019 C1                                                                                          ");
           sql.append("           UNION                                                                                                    ");
           sql.append("           SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                 ");
           sql.append("           FROM COUT020 C2                                                                                          ");
           sql.append("           JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                             ");
           sql.append("         ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                             ");
           sql.append("         JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE ");
           //sql.append("         JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.TOTAL_CRS_KIND = '01' AND R4.ANNO_MK = '1' ");//取已公告的學程
           sql.append("         JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.TOTAL_CRS_KIND = '01' AND R4.ANNO_MK = '1' ");//取已公告的學程
           sql.append("         JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE														");
           sql.append("         JOIN STUT003 R9 ON R9.STNO = M.GET_STNO AND R9.STTYPE = '1'                                                ");
           sql.append("         JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                        ");
           sql.append("         JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                      ");
           sql.append("         LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.GET_STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5') ");
           sql.append("         WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' 												");
           sql.append("         AND r4.total_crs_no = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"'											");
           sql.append("         AND EXISTS(																");
           sql.append("                 SELECT 1 FROM GRAT035 X WHERE X.STNO = M.STNO AND X.CRSNO = M.CRSNO AND X.ASYS = M.ASYS AND X.PROGRAM = R4.TOTAL_CRS_NO ");
           sql.append("                  )																														");
           sql.append("     ) R5                                                                                                                                ");           
           //GRAV034 END
           sql.append(" ON R5.TOTAL_CRS_NO = R2.TOTAL_CRS_NO ");
		   sql.append(" AND R5.CRSNO =  R2.MULTI_CRSNO ");           
           sql.append("	AND R5.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' ");

           sql.append("  LEFT JOIN SYST001 R6 ");
           sql.append("	ON R6.KIND ='IS_REQUIRED' AND R6.CODE = R8.IS_REQUIRED ");
           sql.append("  LEFT JOIN (SELECT FACULTY_CODE, TOTAL_CRS_NO, GRP, COUNT(1) AS GRP_NUM ");
           sql.append(" 		   FROM COUT019 ");
           sql.append(" 		  WHERE 1=1 ");
           sql.append(" 		  GROUP BY FACULTY_CODE, TOTAL_CRS_NO, GRP) R7 ");
           sql.append(" ON R7.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" AND R7.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append(" AND R7.GRP = R1.GRP ");
		   sql.append(" JOIN COUT016 R9 ");
           sql.append("	ON R9.FACULTY_CODE = R1.FACULTY_CODE AND R9.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		   sql.append("	JOIN SYST003 R10 ON R10.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" WHERE R1.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
           //sql.append(" ORDER by R8.CLASS_GROUP_CODE, NVL(R1.GRP, 0),  R3.CRSNO, R5.GET_AYEAR||DECODE(R5.GET_SMS,'3','0',R5.GET_SMS) DESC ");
		   sql.append(" ORDER by R8.CLASS_GROUP_CODE, NVL(R1.GRP, 0),  R3.CRSNO, CASE WHEN R3.CRSNO = R5.CRSNO THEN TO_CHAR('1') ELSE TO_CHAR(R5.CRSNO) END, case when R5.GET_AYEAR is not null then to_char(R5.GET_AYEAR||DECODE(R5.GET_SMS,'3','0',R5.GET_SMS)) else to_char('') end DESC ");
           try {
        	   
               result =  UtilityX.getVtGroupData( dbmanager, conn,sql.toString(),new String[]{"CLASS_GROUP_CODE"});
               
           } catch (Exception e) {
               throw e;
           } finally {
               
           }
           return result;
       }
       
       public Vector getGra053rPrint3(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
           sql.append("SELECT DISTINCT");
           sql.append("	   R8.FACULTY_CODE, ");
           sql.append("	   R10.FACULTY_NAME, ");
           sql.append("	   R1.TOTAL_CRS_NO, ");
           sql.append("	   R9.TOTAL_CRS_CH, ");
           sql.append("	   R8.CLASS_GROUP_CODE, ");
           sql.append("	   R8.CLASS_GROUP_NAME, ");
           sql.append("	   R8.IS_REQUIRED, ");
           sql.append("	   CASE WHEN R8.CLASS_GROUP_AUDIT = '1' THEN '全部通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_TOT_CRS || '科次' ");
           sql.append("			END ");
           sql.append("			WHEN R8.CLASS_GROUP_AUDIT = '2' THEN '至少通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_COND_CRD || '科次' ");
           sql.append("			END ");
           sql.append("	   END AS GROUP_COND, ");
           sql.append("	   R3.CRSNO, ");
           sql.append("	   R3.CRS_NAME,  ");
           sql.append("	   R3.CRD,  ");
           sql.append("	   R4.CRSNO AS MULTI_CRSNO,  ");
           sql.append("	   R4.CRSNO||R4.CRSNO||R4.CRS_NAME AS MULTI_CRS_NAME, ");
           sql.append("	   R4.CRD AS MULTI_CRD,  ");
           sql.append("	   R1.GRP, ");
           sql.append("	   R5.GET_AYEAR, ");
           sql.append("	   R5.GET_SMS, ");
           sql.append("	   R5.CRSNO AS GET_CRSNO, ");
           sql.append("	   R5.CRS_NAME AS GET_CRS_NAME, ");
           sql.append("	   R5.CRD AS GET_CRD,  ");
           sql.append("	   R5.MARK,  ");
           sql.append("	   R6.CODE_NAME AS IS_REQUIRED_NAME,  ");
           sql.append("    R8.CLASS_GROUP_COND,");
           sql.append("	   R7.GRP_NUM ");
           sql.append("  FROM COUT019 R1 ");
		   sql.append("  LEFT JOIN COUT018 R8 ");
           sql.append("	ON R1.FACULTY_CODE = R8.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = R8.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = R8.CLASS_GROUP_CODE ");
           //sql.append("  LEFT JOIN GRAV035 M ");
           sql.append("  LEFT JOIN ");
           sql.append("   (  SELECT DISTINCT  M.STNO 					");	//學號
           sql.append("    , R6.NAME                                        ");	//姓名
           sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
           sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
           sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
           sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
           sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
           sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
           sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
           sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
           sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
           sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
           sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT SUM (C2.CRD)                                   ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT COUNT (1)                                      ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
           sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
           sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
           sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
           sql.append("    , R2.GRP 		                                       "); //組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
           sql.append("    , M.CRSNO                                                                                        		"); //科目代號
           sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
           sql.append("    , M.CRD 	                                                                                                "); //學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
           sql.append("    ,                                                                                                               ");
           sql.append("    CASE                                                                                                            ");
           sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
           sql.append("        THEN                                                                                                        ");
           sql.append("      CASE                                                                                                          ");
           sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
           sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
           sql.append("      ELSE '抵免'                                                                                                   ");
           sql.append("      END                                                                                                           ");
           sql.append("    ELSE                                                                                                            ");
           sql.append("      TO_CHAR (M.MARK)                                                                                              ");
           sql.append("    END                                                                                                             ");
           sql.append("    AS MARK 																											"); //成績
           sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
           sql.append("    FROM STUT012 M																									");
           sql.append("    JOIN                                                                                                            ");
           sql.append("    (                                                                                                               ");
           sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
           sql.append("      FROM COUT019 C1                                                                                               ");
           sql.append("      UNION                                                                                                         ");
           sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
           sql.append("      FROM COUT020 C2                                                                                               ");
           sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
           sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
           sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
           sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
           sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
           sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
           sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
           sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
           sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
           sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");
           // LEFT JOIN GRAV035 M End
           sql.append("	ON R1.FACULTY_CODE = M.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE ");
           sql.append("	  AND M.STNO =  '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' "); 
           //sql.append("  LEFT JOIN COUT020 R2 ");
           sql.append("  LEFT JOIN (SELECT X2.FACULTY_CODE, X2.TOTAL_CRS_NO, X2.CRSNO, X2.MULTI_CRSNO, X2.CLASS_GROUP_CODE ");
           sql.append("               FROM COUT020 X2 ");
           sql.append("               WHERE X2.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
           sql.append("              UNION ALL ");
           sql.append("              SELECT X1.FACULTY_CODE, X1.TOTAL_CRS_NO, X1.CRSNO,X1.CRSNO AS MULTI_CRSNO, X1.CLASS_GROUP_CODE ");
           sql.append("               FROM COUT019 X1 ");
           sql.append("               WHERE X1.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
           sql.append("  ORDER BY 1, 5, 3) R2 ");
           sql.append("	ON R2.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append("   AND R2.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append("   AND R2.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
           sql.append("   AND R2.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R3 ");
           sql.append("	ON R3.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R4 ");
           sql.append("	ON R4.CRSNO = R2.MULTI_CRSNO ");
           //sql.append("  LEFT JOIN GRAV035 R5 ");
           sql.append("  LEFT JOIN ");
           sql.append("   (  SELECT DISTINCT  M.STNO 					");	//學號
           sql.append("    , R6.NAME                                        ");	//姓名
           sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
           sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
           sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
           sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
           sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
           sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
           sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
           sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
           sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
           sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
           sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT SUM (C2.CRD)                                   ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT COUNT (1)                                      ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
           sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
           sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
           sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
           sql.append("    , R2.GRP 		                                       "); //組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
           sql.append("    , M.CRSNO                                                                                        		"); //科目代號
           sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
           sql.append("    , M.CRD 	                                                                                                "); //學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
           sql.append("    ,                                                                                                               ");
           sql.append("    CASE                                                                                                            ");
           sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
           sql.append("        THEN                                                                                                        ");
           sql.append("      CASE                                                                                                          ");
           sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
           sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
           sql.append("      ELSE '抵免'                                                                                                   ");
           sql.append("      END                                                                                                           ");
           sql.append("    ELSE                                                                                                            ");
           sql.append("      TO_CHAR (M.MARK)                                                                                              ");
           sql.append("    END                                                                                                             ");
           sql.append("    AS MARK 																											"); //成績
           sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
           sql.append("    FROM STUT012 M																									");
           sql.append("    JOIN                                                                                                            ");
           sql.append("    (                                                                                                               ");
           sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
           sql.append("      FROM COUT019 C1                                                                                               ");
           sql.append("      UNION                                                                                                         ");
           sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
           sql.append("      FROM COUT020 C2                                                                                               ");
           sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
           sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
           sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
           sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
           sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
           sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
           sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
           sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
           sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
           sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) R5                       ");
           // Left join GRAV035 R5 END
           sql.append(" ON R1.TOTAL_CRS_NO = R5.TOTAL_CRS_NO ");
           sql.append("	AND R5.CRSNO = R2.MULTI_CRSNO ");           
           sql.append("	AND R5.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' ");
		// 1030729 mars 如果只修一科在COUT020會出不來
		// sql.append("   AND R5.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		// sql.append("   AND R5.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
		// sql.append("   AND R5.COUT019_CRSNO = R1.CRSNO  ");
		// sql.append("    AND (R5.COUT019_CRSNO = R1.CRSNO OR R5.COUT019_CRSNO = R2.MULTI_CRSNO ) ");
           sql.append("  LEFT JOIN SYST001 R6 ");
           sql.append("	ON R6.KIND ='IS_REQUIRED' AND R6.CODE = R8.IS_REQUIRED ");
           sql.append("  LEFT JOIN (SELECT FACULTY_CODE, TOTAL_CRS_NO, GRP, COUNT(1) AS GRP_NUM ");
           sql.append(" 		   FROM COUT019 ");
           sql.append(" 		  WHERE 1=1 ");
           sql.append(" 		  GROUP BY FACULTY_CODE, TOTAL_CRS_NO, GRP) R7 ");
           sql.append(" ON R7.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" AND R7.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append(" AND R7.GRP = R1.GRP ");
		   sql.append(" JOIN COUT016 R9 ");
           sql.append("	ON R9.FACULTY_CODE = R1.FACULTY_CODE AND R9.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		   sql.append("	JOIN SYST003 R10 ON R10.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" WHERE R1.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
		   sql.append(" ORDER by R8.CLASS_GROUP_CODE, NVL(R1.GRP, 0),  R3.CRSNO,  ");
		   sql.append(" 		 CASE WHEN R3.CRSNO = R5.CRSNO THEN TO_CHAR('1') ELSE TO_CHAR(R5.CRSNO) END, ");
		   sql.append(" 		 CASE WHEN R5.GET_AYEAR IS NOT NULL THEN TO_CHAR(R5.GET_AYEAR || DECODE(R5.GET_SMS, '3', '0', R5.GET_SMS)) ELSE TO_CHAR('') END DESC ");
           try {
        	   
               result =  UtilityX.getVtGroupData( dbmanager, conn,sql.toString(),new String[]{"CLASS_GROUP_CODE"});
               
           } catch (Exception e) {
               throw e;
           } finally {
               
           }
           return result;
       }
       
       /**
        * 學程修課資料2
        * @param ht
        * @return
        * @throws Exception
      	*/
       
       public Vector getGra063rPrint2(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
           sql.append("SELECT DISTINCT");
           sql.append("	   R8.FACULTY_CODE, ");
           sql.append("	   R10.FACULTY_NAME, ");
           sql.append("	   R1.TOTAL_CRS_NO, ");
           sql.append("	   R9.TOTAL_CRS_CH, ");
           sql.append("	   R8.CLASS_GROUP_CODE, ");
           sql.append("	   R8.CLASS_GROUP_NAME, ");
           sql.append("	   R8.IS_REQUIRED, ");
           sql.append("	   CASE WHEN R8.CLASS_GROUP_AUDIT = '1' THEN '全部通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_TOT_CRS || '科次' ");
           sql.append("			END ");
           sql.append("			WHEN R8.CLASS_GROUP_AUDIT = '2' THEN '至少通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_COND_CRD || '科次' ");
           sql.append("			END ");
           sql.append("	   END AS GROUP_COND, ");
           sql.append("	   R3.CRSNO, ");
           sql.append("	   R3.CRS_NAME,  ");
           sql.append("	   R3.CRD,  ");
           sql.append("	   R4.CRSNO AS MULTI_CRSNO,  ");
           sql.append("	   R4.CRS_NAME AS MULTI_CRS_NAME, ");
           sql.append("	   R4.CRD AS MULTI_CRD,  ");
           sql.append("	   R1.GRP, ");
           sql.append("	   R5.GET_AYEAR, ");
           sql.append("	   R5.GET_SMS, ");
           sql.append("	   R5.CRSNO AS GET_CRSNO, ");
           sql.append("	   R5.CRS_NAME AS GET_CRS_NAME, ");
           sql.append("	   R5.CRD AS GET_CRD,  ");
           sql.append("	   R5.MARK,  ");
           sql.append("	   R6.CODE_NAME AS IS_REQUIRED_NAME,  ");
           sql.append("    R8.CLASS_GROUP_COND,");
           sql.append("	   R7.GRP_NUM ");
           sql.append("  FROM COUT019 R1 ");
		   sql.append("  LEFT JOIN COUT018 R8 ");
           sql.append("	ON R1.FACULTY_CODE = R8.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = R8.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = R8.CLASS_GROUP_CODE ");
           sql.append("  LEFT JOIN GRAV035S M ");
           sql.append("	ON R1.FACULTY_CODE = M.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE ");
           sql.append("	  AND M.STNO =  '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' "); 
           sql.append("  LEFT JOIN COUT020 R2 ");
           sql.append("	ON R2.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append("   AND R2.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append("   AND R2.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
           sql.append("   AND R2.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R3 ");
           sql.append("	ON R3.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R4 ");
           sql.append("	ON R4.CRSNO = R2.MULTI_CRSNO ");
           sql.append("  LEFT JOIN GRAV035S R5 ");
           sql.append(" ON R1.TOTAL_CRS_NO = R5.TOTAL_CRS_NO ");
           sql.append("	AND R5.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' ");
		// 1030729 mars 如果只修一科在COUT020會出不來
		// sql.append("   AND R5.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		// sql.append("   AND R5.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
		// sql.append("   AND R5.COUT019_CRSNO = R1.CRSNO  ");
		sql.append("    AND (R5.COUT019_CRSNO = R1.CRSNO OR R5.COUT019_CRSNO = R2.MULTI_CRSNO ) ");
           sql.append("  LEFT JOIN SYST001 R6 ");
           sql.append("	ON R6.KIND ='IS_REQUIRED' AND R6.CODE = R8.IS_REQUIRED ");
           sql.append("  LEFT JOIN (SELECT FACULTY_CODE, TOTAL_CRS_NO, GRP, COUNT(1) AS GRP_NUM ");
           sql.append(" 		   FROM COUT019 ");
           sql.append(" 		  WHERE 1=1 ");
           sql.append(" 		  GROUP BY FACULTY_CODE, TOTAL_CRS_NO, GRP) R7 ");
           sql.append(" ON R7.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" AND R7.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append(" AND R7.GRP = R1.GRP ");
		   sql.append(" JOIN COUT016 R9 ");
           sql.append("	ON R9.FACULTY_CODE = R1.FACULTY_CODE AND R9.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		   sql.append("	JOIN SYST003 R10 ON R10.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" WHERE R1.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
		   sql.append(" ORDER by R8.CLASS_GROUP_CODE,NVL(R1.GRP,0),R3.CRSNO ");
           try {
        	   
               result =  UtilityX.getVtGroupData( dbmanager, conn,sql.toString(),new String[]{"CLASS_GROUP_CODE"});
               
           } catch (Exception e) {
               throw e;
           } finally {
               
           }
           return result;
       }
       
       public Vector getGra063rPrint3(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
           sql.append("SELECT DISTINCT");
           sql.append("	   R8.FACULTY_CODE, ");
           sql.append("	   R10.FACULTY_NAME, ");
           sql.append("	   R1.TOTAL_CRS_NO, ");
           sql.append("	   R9.TOTAL_CRS_CH, ");
           sql.append("	   R8.CLASS_GROUP_CODE, ");
           sql.append("	   R8.CLASS_GROUP_NAME, ");
           sql.append("	   R8.IS_REQUIRED, ");
           sql.append("	   CASE WHEN R8.CLASS_GROUP_AUDIT = '1' THEN '全部通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_TOT_CRS || '科次' ");
           sql.append("			END ");
           sql.append("			WHEN R8.CLASS_GROUP_AUDIT = '2' THEN '至少通過' || ");
           sql.append("			CASE WHEN R8.CLASS_GROUP_COND = '1' THEN R8.CRD_NUM ||'學分' ");
           sql.append("				 ELSE M.GROUP_COND_CRD || '科次' ");
           sql.append("			END ");
           sql.append("	   END AS GROUP_COND, ");
           sql.append("	   R3.CRSNO, ");
           sql.append("	   R3.CRS_NAME,  ");
           sql.append("	   R3.CRD,  ");
           sql.append("	   R4.CRSNO AS MULTI_CRSNO,  ");
           sql.append("	   R4.CRS_NAME AS MULTI_CRS_NAME, ");
           sql.append("	   R4.CRD AS MULTI_CRD,  ");
           sql.append("	   R1.GRP, ");
           sql.append("	   R5.GET_AYEAR, ");
           sql.append("	   R5.GET_SMS, ");
           sql.append("	   R5.CRSNO AS GET_CRSNO, ");
           sql.append("	   R5.CRS_NAME AS GET_CRS_NAME, ");
           sql.append("	   R5.CRD AS GET_CRD,  ");
           sql.append("	   R5.MARK,  ");
           sql.append("	   R6.CODE_NAME AS IS_REQUIRED_NAME,  ");
           sql.append("    R8.CLASS_GROUP_COND,");
           sql.append("	   R7.GRP_NUM ");
           sql.append("  FROM COUT019 R1 ");
		   sql.append("  LEFT JOIN COUT018 R8 ");
           sql.append("	ON R1.FACULTY_CODE = R8.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = R8.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = R8.CLASS_GROUP_CODE ");
           sql.append("  LEFT JOIN GRAV035S M ");
           sql.append("	ON R1.FACULTY_CODE = M.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE ");
           sql.append("	  AND M.STNO =  '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' "); 
           sql.append("  LEFT JOIN COUT020 R2 ");
           sql.append("	ON R2.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append("   AND R2.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append("   AND R2.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
           sql.append("   AND R2.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R3 ");
           sql.append("	ON R3.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R4 ");
           sql.append("	ON R4.CRSNO = R2.MULTI_CRSNO ");
           sql.append("  LEFT JOIN GRAV035S R5 ");
           sql.append(" ON R1.TOTAL_CRS_NO = R5.TOTAL_CRS_NO ");
           sql.append("	AND R5.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' ");
		// 1030729 mars 如果只修一科在COUT020會出不來
		// sql.append("   AND R5.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		// sql.append("   AND R5.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
		// sql.append("   AND R5.COUT019_CRSNO = R1.CRSNO  ");
		sql.append("    AND (R5.COUT019_CRSNO = R1.CRSNO OR R5.COUT019_CRSNO = R2.MULTI_CRSNO ) ");
           sql.append("  LEFT JOIN SYST001 R6 ");
           sql.append("	ON R6.KIND ='IS_REQUIRED' AND R6.CODE = R8.IS_REQUIRED ");
           sql.append("  LEFT JOIN (SELECT FACULTY_CODE, TOTAL_CRS_NO, GRP, COUNT(1) AS GRP_NUM ");
           sql.append(" 		   FROM COUT019 ");
           sql.append(" 		  WHERE 1=1 ");
           sql.append(" 		  GROUP BY FACULTY_CODE, TOTAL_CRS_NO, GRP) R7 ");
           sql.append(" ON R7.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" AND R7.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append(" AND R7.GRP = R1.GRP ");
		   sql.append(" JOIN COUT016 R9 ");
           sql.append("	ON R9.FACULTY_CODE = R1.FACULTY_CODE AND R9.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
		   sql.append("	JOIN SYST003 R10 ON R10.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" WHERE R1.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
		   sql.append(" ORDER by R8.CLASS_GROUP_CODE,NVL(R1.GRP,0),R3.CRSNO ");
           try {
        	   
               result =  UtilityX.getVtGroupData( dbmanager, conn,sql.toString(),new String[]{"CLASS_GROUP_CODE"});
               
           } catch (Exception e) {
               throw e;
           } finally {
               
           }
           return result;
       }
       
       /**
        * 學程修課資料2
        * @param ht
        * @return
        * @throws Exception
        */
	public Hashtable getPassCrd(Hashtable ht) throws Exception {
           Vector result = new Vector();

           if(sql.length() > 0)
               sql.delete(0, sql.length());
           
           sql.append("SELECT DISTINCT ");
           sql.append("	   M.FACULTY_CODE, ");
           sql.append("	   M.FACULTY_NAME, ");
           sql.append("	   M.TOTAL_CRS_NO, ");
           sql.append("	   M.TOTAL_CRS_CH, ");
           sql.append("	   M.CLASS_GROUP_CODE, ");
           sql.append("	   M.CLASS_GROUP_NAME, ");
           sql.append("	   M.IS_REQUIRED, ");
           sql.append("	   R3.CRSNO, ");
           sql.append("	   R3.CRS_NAME,  ");
           sql.append("	   R3.CRD,  ");
           sql.append("	   R4.CRSNO AS MULTI_CRSNO,  ");
           sql.append("	   R4.CRS_NAME AS MULTI_CRS_NAME, ");
           sql.append("	   R4.CRD AS MULTI_CRD,  ");
           sql.append("	   R1.GRP, ");
           sql.append("	   R5.GET_AYEAR, ");
           sql.append("	   R5.GET_SMS, ");
           sql.append("	   R5.CRSNO AS GET_CRSNO, ");
           sql.append("	   R5.CRS_NAME AS GET_CRS_NAME, ");
           sql.append("	   R5.CRD AS GET_CRD,  ");
           sql.append("	   R5.MARK,  ");
           sql.append("	   R6.CODE_NAME AS IS_REQUIRED_NAME,  ");
           sql.append("    M.CLASS_GROUP_COND,");
           sql.append("	   R7.GRP_NUM ");
           //sql.append("  FROM GRAV035 M ");
           sql.append("   FROM (  SELECT DISTINCT  M.STNO 					");	//學號
           sql.append("    , R6.NAME                                        ");	//姓名
           sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
           sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
           sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
           sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
           sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
           sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
           sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
           sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
           sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
           sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
           sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT SUM (C2.CRD)                                   ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT COUNT (1)                                      ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
           sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
           sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
           sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
           sql.append("    , R2.GRP 		                                       "); //組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
           sql.append("    , M.CRSNO                                                                                        		"); //科目代號
           sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
           sql.append("    , M.CRD 	                                                                                                "); //學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
           sql.append("    ,                                                                                                               ");
           sql.append("    CASE                                                                                                            ");
           sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
           sql.append("        THEN                                                                                                        ");
           sql.append("      CASE                                                                                                          ");
           sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
           sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
           sql.append("      ELSE '抵免'                                                                                                   ");
           sql.append("      END                                                                                                           ");
           sql.append("    ELSE                                                                                                            ");
           sql.append("      TO_CHAR (M.MARK)                                                                                              ");
           sql.append("    END                                                                                                             ");
           sql.append("    AS MARK 																											"); //成績
           sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
           sql.append("    FROM STUT012 M																									");
           sql.append("    JOIN                                                                                                            ");
           sql.append("    (                                                                                                               ");
           sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
           sql.append("      FROM COUT019 C1                                                                                               ");
           sql.append("      UNION                                                                                                         ");
           sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
           sql.append("      FROM COUT020 C2                                                                                               ");
           sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
           sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
           sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
           sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
           sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
           sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
           sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
           sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
           sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
           sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");           
           
           //GRAV035 END
           sql.append("  JOIN COUT019 R1 ");
           sql.append("	ON R1.FACULTY_CODE = M.FACULTY_CODE ");
           sql.append("   AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO ");
           sql.append("   AND R1.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE ");
           sql.append("  LEFT JOIN COUT020 R2 ");
           sql.append("	ON R2.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append("   AND R2.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append("   AND R2.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
           sql.append("   AND R2.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R3 ");
           sql.append("	ON R3.CRSNO = R1.CRSNO ");
           sql.append("  LEFT JOIN COUT002 R4 ");
           sql.append("	ON R4.CRSNO = R2.MULTI_CRSNO ");
           //sql.append("  LEFT JOIN GRAV035 R5 ");
           sql.append("   LEFT JOIN (  SELECT DISTINCT  M.STNO 				");	//學號
           sql.append("    , R6.NAME                                        ");	//姓名
           sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
           sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
           sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
           sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
           sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
           sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
           sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
           sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
           sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
           sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
           sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT SUM (C2.CRD)                                   ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
           sql.append("    ,                                                       ");
           sql.append("    (                                                       ");
           sql.append("      SELECT COUNT (1)                                      ");
           sql.append("      FROM COUT019 C1                                       ");
           sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
           sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
           sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
           sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
           sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
           sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
           sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
           sql.append("    , R2.GRP 		                                       "); //組別
           //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
           sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
           sql.append("    , M.CRSNO                                                                                        		"); //科目代號
           sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
           sql.append("    , M.CRD 	                                                                                                "); //學分數
           //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
           sql.append("    ,                                                                                                               ");
           sql.append("    CASE                                                                                                            ");
           sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
           sql.append("        THEN                                                                                                        ");
           sql.append("      CASE                                                                                                          ");
           sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
           sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
           sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
           sql.append("      ELSE '抵免'                                                                                                   ");
           sql.append("      END                                                                                                           ");
           sql.append("    ELSE                                                                                                            ");
           sql.append("      TO_CHAR (M.MARK)                                                                                              ");
           sql.append("    END                                                                                                             ");
           sql.append("    AS MARK 																											"); //成績
           sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
           sql.append("    FROM STUT012 M																									");
           sql.append("    JOIN                                                                                                            ");
           sql.append("    (                                                                                                               ");
           sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
           sql.append("      FROM COUT019 C1                                                                                               ");
           sql.append("      UNION                                                                                                         ");
           sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
           sql.append("      FROM COUT020 C2                                                                                               ");
           sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
           sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
           sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
           sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
           sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
           sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
           sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
           sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
           sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
           sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) R5                       ");
           //GRAV035 END
           sql.append("	ON R5.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' ");
           sql.append("   AND R5.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append("   AND R5.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
           sql.append("   AND R5.COUT019_CRSNO = R1.CRSNO  ");           
           sql.append("  LEFT JOIN SYST001 R6 ");
           sql.append("	ON R6.KIND ='IS_REQUIRED' AND R6.CODE = M.IS_REQUIRED ");
           sql.append("  LEFT JOIN (SELECT FACULTY_CODE, TOTAL_CRS_NO, GRP, COUNT(1) AS GRP_NUM ");
           sql.append(" 		   FROM COUT019 ");
           sql.append(" 		  WHERE GRP IS NOT NULL ");
           sql.append(" 		  GROUP BY FACULTY_CODE, TOTAL_CRS_NO, GRP) R7 ");
           sql.append(" ON R7.FACULTY_CODE = R1.FACULTY_CODE ");
           sql.append(" AND R7.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
           sql.append(" AND R7.GRP = R1.GRP ");
           sql.append(" WHERE M.STNO =  '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'  ");
		   sql.append("  AND  M.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
		   sql.append(" ORDER by M.CLASS_GROUP_CODE,NVL(R1.GRP,0),R3.CRSNO,R5.GET_AYEAR||DECODE(R5.GET_SMS,'3','0',R5.GET_SMS) DESC ");
		   
		   Hashtable htt = new Hashtable();
           try {
        	   
               result =  UtilityX.getVtGroupData( dbmanager, conn,sql.toString(),new String[]{"CLASS_GROUP_CODE"});
               
		       for (int j = 0 ; j < result.size(); j++) {
		   			Vector vt = (Vector)result.get(j);
		   			int UMULTI_CRD_TOTAL = 0;	//學分累計
		   			int SUBJECT_TOTAL = 0;	//科次累計
		   			int grp_num_count = 0;
		   			String key_tmp = "";
		   			String grp = "";
		   	        for (int i = 0 ; i < vt.size(); i++) {
		   	        	Hashtable ht1 = (Hashtable)vt.get(i);
		   	        	String is_required = UtilityX.checkNull(ht1.get("IS_REQUIRED"));
					String class_group_code = UtilityX.checkNull(ht1
							.get("CLASS_GROUP_CODE"));
		   	        	String key = UtilityX.checkNull(ht1.get("IS_REQUIRED")) + UtilityX.checkNull(ht1.get("CRSNO"));
		   	        	if ("".equals(grp)){
		   	        		grp = UtilityX.checkNull(ht1.get("GRP"));
		   	        	}
		   	        	
		   	        	int grp_num = Integer.parseInt(UtilityX.checkNullEmpty(ht1.get("GRP_NUM"),"0"));
		   	        	
		   	        	//課群表身
		   	        	if(!key.equals(key_tmp)){
		   	        	
		   	        		//有修課的才累計(學分/科次)
		   	        		if(!"".equals(ht1.get("GET_CRSNO"))){
		   	        			UMULTI_CRD_TOTAL +=  Integer.parseInt(UtilityX.checkNullEmpty(ht1.get("GET_CRD"),"0"));	   	        			

		   		        		if(!"".equals(grp)){
		   		        			//組別改變時要重新計算
			   	        			if (!grp.equals(UtilityX.checkNull(ht1.get("GRP")))){
			   	        				grp_num_count = 0;
			   	        				grp = UtilityX.checkNull(ht1.get("GRP"));			   	        				
			   	        			}		   		        			
		   		        			grp_num_count++;
		   		        			if(grp_num_count==grp_num){
		   		        				grp_num_count = 0;
		   		        				SUBJECT_TOTAL ++;
		   		        			}
		   		        		}else{
		   		        			grp_num_count = 0;
		   		        			SUBJECT_TOTAL ++;
		   		        		}
		   		        	}
		   	        	}
		   	        	
		   				if(i==(vt.size()-1)){
		   					//1 學分 2科目
		   					//System.out.println("1 學分 2科目="+UtilityX.checkNull(ht1.get("CLASS_GROUP_CODE")));
		   					//System.out.println("is_required="+is_required);
		   					//System.out.println("UMULTI_CRD_TOTAL="+UMULTI_CRD_TOTAL);
		   					//System.out.println("SUBJECT_TOTAL="+SUBJECT_TOTAL);
		   					
		   					if("1".equals(UtilityX.checkNull(ht1.get("CLASS_GROUP_COND")))){
							htt.put(is_required + "_" + class_group_code,
									String.valueOf(UMULTI_CRD_TOTAL));
		   					}else if("2".equals(UtilityX.checkNull(ht1.get("CLASS_GROUP_COND")))){
							htt.put(is_required + "_" + class_group_code,
									String.valueOf(SUBJECT_TOTAL));
		   					}
		   	        	}
		   	        	
		   	        	key_tmp = key;
		   				     
		   	        }
		       }
               
           } catch (Exception e) {
               throw e;
           } finally {
               
           }
           return htt;
       }
	
    /**
     * 微學程修課資料2
     * @param ht
     * @return
     * @throws Exception
     */
	public Hashtable getPassCrd_S(Hashtable ht) throws Exception {
        Vector result = new Vector();

        if(sql.length() > 0)
            sql.delete(0, sql.length());
        
        sql.append("SELECT DISTINCT ");
        sql.append("	   M.FACULTY_CODE, ");
        sql.append("	   M.FACULTY_NAME, ");
        sql.append("	   M.TOTAL_CRS_NO, ");
        sql.append("	   M.TOTAL_CRS_CH, ");
        sql.append("	   M.CLASS_GROUP_CODE, ");
        sql.append("	   M.CLASS_GROUP_NAME, ");
        sql.append("	   M.IS_REQUIRED, ");
        sql.append("	   R3.CRSNO, ");
        sql.append("	   R3.CRS_NAME,  ");
        sql.append("	   R3.CRD,  ");
        sql.append("	   R4.CRSNO AS MULTI_CRSNO,  ");
        sql.append("	   R4.CRS_NAME AS MULTI_CRS_NAME, ");
        sql.append("	   R4.CRD AS MULTI_CRD,  ");
        sql.append("	   R1.GRP, ");
        sql.append("	   R5.GET_AYEAR, ");
        sql.append("	   R5.GET_SMS, ");
        sql.append("	   R5.CRSNO AS GET_CRSNO, ");
        sql.append("	   R5.CRS_NAME AS GET_CRS_NAME, ");
        sql.append("	   R5.CRD AS GET_CRD,  ");
        sql.append("	   R5.MARK,  ");
        sql.append("	   R6.CODE_NAME AS IS_REQUIRED_NAME,  ");
        sql.append("    M.CLASS_GROUP_COND,");
        sql.append("	   R7.GRP_NUM ");
        //sql.append("  FROM GRAV035S M ");
        sql.append("   FROM (  SELECT DISTINCT  M.STNO 					");	//學號
        sql.append("    , R6.NAME                                        ");	//姓名
        sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
        sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
        sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
        sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
        sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
        sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
        sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
        sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
        sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
        sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
        sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
        sql.append("    ,                                                       ");
        sql.append("    (                                                       ");
        sql.append("      SELECT SUM (C2.CRD)                                   ");
        sql.append("      FROM COUT019 C1                                       ");
        sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
        sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
        sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
        sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
        sql.append("    ,                                                       ");
        sql.append("    (                                                       ");
        sql.append("      SELECT COUNT (1)                                      ");
        sql.append("      FROM COUT019 C1                                       ");
        sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
        sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
        sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
        sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
        sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
        sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
        sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
        sql.append("    , R2.GRP 		                                       "); //組別
        //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
        sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
        sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
        sql.append("    , M.CRSNO                                                                                        		"); //科目代號
        sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
        sql.append("    , M.CRD 	                                                                                                "); //學分數
        //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
        sql.append("    ,                                                                                                               ");
        sql.append("    CASE                                                                                                            ");
        sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
        sql.append("        THEN                                                                                                        ");
        sql.append("      CASE                                                                                                          ");
        sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
        sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
        sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
        sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
        sql.append("      ELSE '抵免'                                                                                                   ");
        sql.append("      END                                                                                                           ");
        sql.append("    ELSE                                                                                                            ");
        sql.append("      TO_CHAR (M.MARK)                                                                                              ");
        sql.append("    END                                                                                                             ");
        sql.append("    AS MARK 																											"); //成績
        sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
        sql.append("    FROM STUT014 M																									");
        sql.append("    JOIN                                                                                                            ");
        sql.append("    (                                                                                                               ");
        sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
        sql.append("      FROM COUT019 C1                                                                                               ");
        sql.append("      UNION                                                                                                         ");
        sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
        sql.append("      FROM COUT020 C2                                                                                               ");
        sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
        sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
        sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
        sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
        sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
        sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
        sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
        sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
        sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
        sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");
        //GRAV035S M End
        sql.append("  JOIN COUT019 R1 ");
        sql.append("	ON R1.FACULTY_CODE = M.FACULTY_CODE ");
        sql.append("   AND R1.TOTAL_CRS_NO = M.TOTAL_CRS_NO ");
        sql.append("   AND R1.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE ");
        sql.append("  LEFT JOIN COUT020 R2 ");
        sql.append("	ON R2.FACULTY_CODE = R1.FACULTY_CODE ");
        sql.append("   AND R2.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
        sql.append("   AND R2.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
        sql.append("   AND R2.CRSNO = R1.CRSNO ");
        sql.append("  LEFT JOIN COUT002 R3 ");
        sql.append("	ON R3.CRSNO = R1.CRSNO ");
        sql.append("  LEFT JOIN COUT002 R4 ");
        sql.append("	ON R4.CRSNO = R2.MULTI_CRSNO ");
        //sql.append("  LEFT JOIN GRAV035S R5 ");
        sql.append("   LEFT JOIN (  SELECT DISTINCT  M.STNO 					");	//學號
        sql.append("    , R6.NAME                                        ");	//姓名
        sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
        sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
        sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
        sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
        sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
        sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
        sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
        sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
        sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
        sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
        sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
        sql.append("    ,                                                       ");
        sql.append("    (                                                       ");
        sql.append("      SELECT SUM (C2.CRD)                                   ");
        sql.append("      FROM COUT019 C1                                       ");
        sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
        sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
        sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
        sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
        sql.append("    ,                                                       ");
        sql.append("    (                                                       ");
        sql.append("      SELECT COUNT (1)                                      ");
        sql.append("      FROM COUT019 C1                                       ");
        sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
        sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
        sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
        sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
        sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
        sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
        sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
        sql.append("    , R2.GRP 		                                       "); //組別
        //抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
        sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
        sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
        sql.append("    , M.CRSNO                                                                                        		"); //科目代號
        sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
        sql.append("    , M.CRD 	                                                                                                "); //學分數
        //抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
        sql.append("    ,                                                                                                               ");
        sql.append("    CASE                                                                                                            ");
        sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
        sql.append("        THEN                                                                                                        ");
        sql.append("      CASE                                                                                                          ");
        sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
        sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
        sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
        sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
        sql.append("      ELSE '抵免'                                                                                                   ");
        sql.append("      END                                                                                                           ");
        sql.append("    ELSE                                                                                                            ");
        sql.append("      TO_CHAR (M.MARK)                                                                                              ");
        sql.append("    END                                                                                                             ");
        sql.append("    AS MARK 																											"); //成績
        sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
        sql.append("    FROM STUT014 M																									");
        sql.append("    JOIN                                                                                                            ");
        sql.append("    (                                                                                                               ");
        sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
        sql.append("      FROM COUT019 C1                                                                                               ");
        sql.append("      UNION                                                                                                         ");
        sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
        sql.append("      FROM COUT020 C2                                                                                               ");
        sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
        sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
        sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
        sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
        sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
        sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
        sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
        sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
        sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
        sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) R5                       ");
        // GRAV035S R5 End
        sql.append("	ON R5.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"' ");
        sql.append("   AND R5.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
        sql.append("   AND R5.CLASS_GROUP_CODE = R1.CLASS_GROUP_CODE ");
        sql.append("   AND R5.COUT019_CRSNO = R1.CRSNO  ");           
        sql.append("  LEFT JOIN SYST001 R6 ");
        sql.append("	ON R6.KIND ='IS_REQUIRED' AND R6.CODE = M.IS_REQUIRED ");
        sql.append("  LEFT JOIN (SELECT FACULTY_CODE, TOTAL_CRS_NO, GRP, COUNT(1) AS GRP_NUM ");
        sql.append(" 		   FROM COUT019 ");
        sql.append(" 		  WHERE GRP IS NOT NULL ");
        sql.append(" 		  GROUP BY FACULTY_CODE, TOTAL_CRS_NO, GRP) R7 ");
        sql.append(" ON R7.FACULTY_CODE = R1.FACULTY_CODE ");
        sql.append(" AND R7.TOTAL_CRS_NO = R1.TOTAL_CRS_NO ");
        sql.append(" AND R7.GRP = R1.GRP ");
        sql.append(" WHERE M.STNO =  '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'  ");
		   sql.append("  AND  M.TOTAL_CRS_NO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))+"' ");
		   sql.append(" ORDER by M.CLASS_GROUP_CODE,NVL(R1.GRP,0),R3.CRSNO ");
		   
		   Hashtable htt = new Hashtable();
           try {
        	   
               result =  UtilityX.getVtGroupData( dbmanager, conn,sql.toString(),new String[]{"CLASS_GROUP_CODE"});
               
		       for (int j = 0 ; j < result.size(); j++) {
		   			Vector vt = (Vector)result.get(j);
		   			int UMULTI_CRD_TOTAL = 0;	//學分累計
		   			int SUBJECT_TOTAL = 0;	//科次累計
		   			int grp_num_count = 0;
		   			String key_tmp = "";
		   			String grp = "";
		   	        for (int i = 0 ; i < vt.size(); i++) {
		   	        	Hashtable ht1 = (Hashtable)vt.get(i);
		   	        	String is_required = UtilityX.checkNull(ht1.get("IS_REQUIRED"));
					String class_group_code = UtilityX.checkNull(ht1
							.get("CLASS_GROUP_CODE"));
		   	        	String key = UtilityX.checkNull(ht1.get("IS_REQUIRED")) + UtilityX.checkNull(ht1.get("CRSNO"));
		   	        	if ("".equals(grp)){
		   	        		grp = UtilityX.checkNull(ht1.get("GRP"));
		   	        	}
		   	        	
		   	        	int grp_num = Integer.parseInt(UtilityX.checkNullEmpty(ht1.get("GRP_NUM"),"0"));
		   	        	
		   	        	//課群表身
		   	        	if(!key.equals(key_tmp)){
		   	        	
		   	        		//有修課的才累計(學分/科次)
		   	        		if(!"".equals(ht1.get("GET_CRSNO"))){
		   	        			UMULTI_CRD_TOTAL +=  Integer.parseInt(UtilityX.checkNullEmpty(ht1.get("GET_CRD"),"0"));	   	        			

		   		        		if(!"".equals(grp)){
		   		        			//組別改變時要重新計算
			   	        			if (!grp.equals(UtilityX.checkNull(ht1.get("GRP")))){
			   	        				grp_num_count = 0;
			   	        				grp = UtilityX.checkNull(ht1.get("GRP"));			   	        				
			   	        			}		   		        			
		   		        			grp_num_count++;
		   		        			if(grp_num_count==grp_num){
		   		        				grp_num_count = 0;
		   		        				SUBJECT_TOTAL ++;
		   		        			}
		   		        		}else{
		   		        			grp_num_count = 0;
		   		        			SUBJECT_TOTAL ++;
		   		        		}
		   		        	}
		   	        	}
		   	        	
		   				if(i==(vt.size()-1)){
		   					//1 學分 2科目
		   					//System.out.println("1 學分 2科目="+UtilityX.checkNull(ht1.get("CLASS_GROUP_CODE")));
		   					//System.out.println("is_required="+is_required);
		   					//System.out.println("UMULTI_CRD_TOTAL="+UMULTI_CRD_TOTAL);
		   					//System.out.println("SUBJECT_TOTAL="+SUBJECT_TOTAL);
		   					
		   					if("1".equals(UtilityX.checkNull(ht1.get("CLASS_GROUP_COND")))){
							htt.put(is_required + "_" + class_group_code,
									String.valueOf(UMULTI_CRD_TOTAL));
		   					}else if("2".equals(UtilityX.checkNull(ht1.get("CLASS_GROUP_COND")))){
							htt.put(is_required + "_" + class_group_code,
									String.valueOf(SUBJECT_TOTAL));
		   					}
		   	        	}
		   	        	
		   	        	key_tmp = key;
		   				     
		   	        }
		       }
               
           } catch (Exception e) {
               throw e;
           } finally {
               
           }
           return htt;
       }
       
	/**
	 * 判斷是否通過_學分學程
	 * 
	 * @param ht
	 *            STNO,TOTAL_CRS_NO
	 * @return
	 * @throws Exception
	 */
	public boolean chkPassGraProgram(Hashtable ht) throws Exception {
		boolean chk = true;

		if (sql.length() > 0)
			sql.delete(0, sql.length());

		sql.append("SELECT M.STNO,M.CLASS_GROUP_CODE,M.COND_CRD,M.IS_REQUIRED  ");
		sql.append(",(SELECT COUNT(1) FROM cout018 C1 WHERE  C1.FACULTY_CODE = M.FACULTY_CODE AND C1.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND DECODE (C1.IS_REQUIRED,'1' ,1,'2', C1.CRD_NUM) > 0) AS T018_CNT ");
		//sql.append("FROM GRAV021 M ");
		sql.append("FROM ( ");
		sql.append(" SELECT M.STNO, M.NAME, M.FACULTY_CODE,M.FACULTY_NAME, M.TOTAL_CRS_NO, M.TOTAL_CRS_CH,       ");
		sql.append("        M.CRD_NUM, M.IS_REQUIRED, M.CLASS_GROUP_CODE, M.CLASS_GROUP_NAME, M.CLASS_GROUP_COND,");
		sql.append("        M.CLASS_GROUP_AUDIT, M.COND_CRD, SUM(M.ST_GET_CRD) AS ST_GET_CRD                     ");
		sql.append(" FROM                                                                                        ");
		sql.append(" (SELECT M.STNO,                                                                             ");
		sql.append("        M.NAME,                                                                              ");
		sql.append("        M.FACULTY_CODE,                                                                      ");
		sql.append("        M.FACULTY_NAME,                                                                      ");
		sql.append("        M.TOTAL_CRS_NO,                                                                      ");
		sql.append("        M.TOTAL_CRS_CH,                                                                      ");
		sql.append("        M.CRD_NUM,                                                                           ");
		sql.append("        M.IS_REQUIRED,                                                                       ");
		sql.append("        M.CLASS_GROUP_CODE,                                                                  ");
		sql.append("        M.CLASS_GROUP_NAME,                                                                  ");
		sql.append("        M.CLASS_GROUP_COND,                                                                  ");
		sql.append("        M.CLASS_GROUP_AUDIT,                                                                 ");
		sql.append("        M.GRP,                                                                               ");
		sql.append("        (SELECT COUNT(DISTINCT A.CRSNO)                                                      ");
		sql.append("           FROM COUT019 A                                                                    ");
		sql.append("          WHERE A.TOTAL_CRS_NO = M.TOTAL_CRS_NO                                              ");
		sql.append("            AND A.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE                                      ");
		sql.append("            AND A.GRP = M.GRP) AS GRP_CNT,                                                   ");
		sql.append("        CASE                                                                                 ");
		sql.append("          WHEN M.CLASS_GROUP_COND = '1' THEN                                                 ");
		sql.append("           DECODE(M.GROUP_COND_CRD, NULL, M.GROUP_TOT_CRD, M.GROUP_COND_CRD)                 ");
		sql.append("          ELSE                                                                               ");
		sql.append("           DECODE(M.GROUP_COND_CRD, NULL, M.GROUP_TOT_CRS, M.GROUP_COND_CRD)                 ");
		sql.append("        END AS COND_CRD,                                                                     ");
		sql.append("        CASE                                                                                 ");
		sql.append("          WHEN M.CLASS_GROUP_COND = '1' THEN                                                 ");
		sql.append("           SUM(M.CRD)                                                                        ");
		sql.append("          ELSE                                                                               ");
		sql.append("           CASE                                                                              ");
		sql.append("             WHEN M.GRP IS NULL THEN                                                         ");
		sql.append("              COUNT(M.COUT019_CRSNO)                                                         ");
		sql.append("             ELSE                                                                            ");
		sql.append("              COUNT(DISTINCT M.GRP)                                                          ");
		sql.append("           END                                                                               ");
		sql.append("        END AS ST_GET_CRD                                                                    ");
		sql.append("                                                                                             ");
		//sql.append("   FROM GRAV035 M                                                                            ");
		sql.append("   FROM (  SELECT DISTINCT  M.STNO 					");	//學號
		sql.append("    , R6.NAME                                        ");	//姓名
		sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
		sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
		sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
		sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
		sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
		sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
		sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
		sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
		sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
		sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
		sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
		sql.append("    ,                                                       ");
		sql.append("    (                                                       ");
		sql.append("      SELECT SUM (C2.CRD)                                   ");
		sql.append("      FROM COUT019 C1                                       ");
		sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
		sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
		sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
		sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
		sql.append("    ,                                                       ");
		sql.append("    (                                                       ");
		sql.append("      SELECT COUNT (1)                                      ");
		sql.append("      FROM COUT019 C1                                       ");
		sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
		sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
		sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
		sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
		sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
		sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
		sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
		sql.append("    , R2.GRP 		                                       "); //組別
		//抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
		sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
		sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
		sql.append("    , M.CRSNO                                                                                        		"); //科目代號
		sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
		sql.append("    , M.CRD 	                                                                                                "); //學分數
		//抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
		sql.append("    ,                                                                                                               ");
		sql.append("    CASE                                                                                                            ");
		sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
		sql.append("        THEN                                                                                                        ");
		sql.append("      CASE                                                                                                          ");
		sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
		sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
		sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
		sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
		sql.append("      ELSE '抵免'                                                                                                   ");
		sql.append("      END                                                                                                           ");
		sql.append("    ELSE                                                                                                            ");
		sql.append("      TO_CHAR (M.MARK)                                                                                              ");
		sql.append("    END                                                                                                             ");
		sql.append("    AS MARK 																											"); //成績
		sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, M.AYEAR||DECODE(M.SMS,'3','0',M.SMS), R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
		sql.append("    FROM STUT012 M																									");
		sql.append("    JOIN                                                                                                            ");
		sql.append("    (                                                                                                               ");
		sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
		sql.append("      FROM COUT019 C1                                                                                               ");
		sql.append("      UNION                                                                                                         ");
		sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
		sql.append("      FROM COUT020 C2                                                                                               ");
		sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
		sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
		sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
		sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
		sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
		sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
		sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
		sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
		sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
		sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");		
		//GRAV035 END
		sql.append("   WHERE M.STNO = '"+ Utility.dbStr(Utility.nullToSpace(ht.get("STNO"))) + "'                ");
		sql.append("  GROUP BY M.STNO,                                                                           ");
		sql.append("           M.NAME,                                                                           ");
		sql.append("           M.FACULTY_CODE,                                                                   ");
		sql.append("           M.FACULTY_NAME,                                                                   ");
		sql.append("           M.TOTAL_CRS_NO,                                                                   ");
		sql.append("           M.TOTAL_CRS_CH,                                                                   ");
		sql.append("           M.CRD_NUM,                                                                        ");
		sql.append("           M.IS_REQUIRED,                                                                    ");
		sql.append("           M.CLASS_GROUP_CODE,                                                               ");
		sql.append("           M.CLASS_GROUP_NAME,                                                               ");
		sql.append("           M.CLASS_GROUP_COND,                                                               ");
		sql.append("           M.CLASS_GROUP_AUDIT,                                                              ");
		sql.append("           M.GROUP_TOT_CRD,                                                                  ");
		sql.append("           M.GROUP_TOT_CRS,                                                                  ");
		sql.append("           M.GROUP_COND_CRD,                                                                 ");
		sql.append("           M.GRP                                                                             ");
		sql.append("                                                                                             ");
		sql.append(" ) M                                                                                         ");
		sql.append("                                                                                             ");
		sql.append("  GROUP BY  M.STNO,                                                                          ");
		sql.append("         M.NAME,                                                                             ");
		sql.append("         M.FACULTY_CODE,                                                                     ");
		sql.append("        M.FACULTY_NAME,                                                                      ");
		sql.append("        M.TOTAL_CRS_NO,                                                                      ");
		sql.append("        M.TOTAL_CRS_CH,                                                                      ");
		sql.append("        M.CRD_NUM,                                                                           ");
		sql.append("        M.IS_REQUIRED,                                                                       ");
		sql.append("        M.CLASS_GROUP_CODE,                                                                  ");
		sql.append("        M.CLASS_GROUP_NAME,                                                                  ");
		sql.append("        M.CLASS_GROUP_COND,                                                                  ");
		sql.append("        M.CLASS_GROUP_AUDIT,                                                                 ");
		sql.append("        M.COND_CRD                                                                           ");
		sql.append(") M ");
		//GARV021 END
		sql.append(" WHERE M.STNO =  '"
				+ Utility.dbStr(Utility.nullToSpace(ht.get("STNO"))) + "'  ");
		sql.append("  AND  M.TOTAL_CRS_NO = '"
				+ Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))
				+ "' ");
		sql.append(" ORDER BY M.IS_REQUIRED ");

		String T018_CNT = "0";

		try {

			// 取得真正通過的學分數
			STUT010GATEWAY S010 = new STUT010GATEWAY(dbmanager, conn);
			Hashtable htd = S010.getPassCrd(ht);
			System.out.println("ht=" + ht);

			DBResult rs = null;
			rs = dbmanager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());
			int row_cnt = 0;
			while (rs.next()) {
				row_cnt++;
				String COND_CRD = UtilityX.checkNullEmpty(
						rs.getString("COND_CRD"), "0");
				String IS_REQUIRED = UtilityX.checkNullEmpty(
						rs.getString("IS_REQUIRED"), "0");
				String CLASS_GROUP_CODE = UtilityX.checkNullEmpty(
						rs.getString("CLASS_GROUP_CODE"), "0");

				String ST_CRD = UtilityX.checkNullEmpty(
						htd.get(IS_REQUIRED + "_" + CLASS_GROUP_CODE),
						"0");
				T018_CNT = UtilityX.checkNullEmpty(rs.getString("T018_CNT"),
						"0");

				System.out.println("COND_CRD=" + COND_CRD);
				System.out.println("IS_REQUIRED=" + IS_REQUIRED);
				System.out.println("CLASS_GROUP_CODE=" + CLASS_GROUP_CODE);
				System.out.println("ST_CRD=" + ST_CRD);

				if (Integer.parseInt(COND_CRD) > Integer.parseInt(ST_CRD)) {
					chk = false;
				}

				}
			// 若修得群組數不等於規定總群組數(可能是完全沒修到該科目)，則為不通過
			if (row_cnt < Integer.parseInt(T018_CNT)) {
				chk = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return chk;
	}
	
	/**
	 * 判斷是否通過_微學程
	 * 
	 * @param ht
	 *            STNO,TOTAL_CRS_NO
	 * @return
	 * @throws Exception
	 */
	public boolean chkPassGraProgram_S(Hashtable ht) throws Exception {
		boolean chk = true;

		if (sql.length() > 0)
			sql.delete(0, sql.length());

		sql.append("SELECT M.STNO,M.CLASS_GROUP_CODE,M.COND_CRD,M.IS_REQUIRED  ");
		sql.append(",(SELECT COUNT(1) FROM cout018 C1 WHERE  C1.FACULTY_CODE = M.FACULTY_CODE AND C1.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND DECODE (C1.IS_REQUIRED,'1' ,1,'2', C1.CRD_NUM) > 0) AS T018_CNT ");
		//sql.append("FROM GRAV021 M ");
		sql.append("FROM ( ");
		sql.append(" SELECT M.STNO, M.NAME, M.FACULTY_CODE,M.FACULTY_NAME, M.TOTAL_CRS_NO, M.TOTAL_CRS_CH,       ");
		sql.append("        M.CRD_NUM, M.IS_REQUIRED, M.CLASS_GROUP_CODE, M.CLASS_GROUP_NAME, M.CLASS_GROUP_COND,");
		sql.append("        M.CLASS_GROUP_AUDIT, M.COND_CRD, SUM(M.ST_GET_CRD) AS ST_GET_CRD                     ");
		sql.append(" FROM                                                                                        ");
		sql.append(" (SELECT M.STNO,                                                                             ");
		sql.append("        M.NAME,                                                                              ");
		sql.append("        M.FACULTY_CODE,                                                                      ");
		sql.append("        M.FACULTY_NAME,                                                                      ");
		sql.append("        M.TOTAL_CRS_NO,                                                                      ");
		sql.append("        M.TOTAL_CRS_CH,                                                                      ");
		sql.append("        M.CRD_NUM,                                                                           ");
		sql.append("        M.IS_REQUIRED,                                                                       ");
		sql.append("        M.CLASS_GROUP_CODE,                                                                  ");
		sql.append("        M.CLASS_GROUP_NAME,                                                                  ");
		sql.append("        M.CLASS_GROUP_COND,                                                                  ");
		sql.append("        M.CLASS_GROUP_AUDIT,                                                                 ");
		sql.append("        M.GRP,                                                                               ");
		sql.append("        (SELECT COUNT(DISTINCT A.CRSNO)                                                      ");
		sql.append("           FROM COUT019 A                                                                    ");
		sql.append("          WHERE A.TOTAL_CRS_NO = M.TOTAL_CRS_NO                                              ");
		sql.append("            AND A.CLASS_GROUP_CODE = M.CLASS_GROUP_CODE                                      ");
		sql.append("            AND A.GRP = M.GRP) AS GRP_CNT,                                                   ");
		sql.append("        CASE                                                                                 ");
		sql.append("          WHEN M.CLASS_GROUP_COND = '1' THEN                                                 ");
		sql.append("           DECODE(M.GROUP_COND_CRD, NULL, M.GROUP_TOT_CRD, M.GROUP_COND_CRD)                 ");
		sql.append("          ELSE                                                                               ");
		sql.append("           DECODE(M.GROUP_COND_CRD, NULL, M.GROUP_TOT_CRS, M.GROUP_COND_CRD)                 ");
		sql.append("        END AS COND_CRD,                                                                     ");
		sql.append("        CASE                                                                                 ");
		sql.append("          WHEN M.CLASS_GROUP_COND = '1' THEN                                                 ");
		sql.append("           SUM(M.CRD)                                                                        ");
		sql.append("          ELSE                                                                               ");
		sql.append("           CASE                                                                              ");
		sql.append("             WHEN M.GRP IS NULL THEN                                                         ");
		sql.append("              COUNT(M.COUT019_CRSNO)                                                         ");
		sql.append("             ELSE                                                                            ");
		sql.append("              COUNT(DISTINCT M.GRP)                                                          ");
		sql.append("           END                                                                               ");
		sql.append("        END AS ST_GET_CRD                                                                    ");
		sql.append("                                                                                             ");
		//sql.append("   FROM GRAV035S M                                                                            ");
		sql.append("   FROM (  SELECT DISTINCT  M.STNO 					");	//學號
		sql.append("    , R6.NAME                                        ");	//姓名
		sql.append("    , R5.FACULTY_CODE 	                            ");	//學系代號
		sql.append("    , R5.FACULTY_ABBRNAME 	                        "); //學系簡稱
		sql.append("    , R5.FACULTY_NAME 	                            ");	//學系名稱
		sql.append("    , R4.TOTAL_CRS_NO 	                            "); //學程代號
		sql.append("    , R4.TOTAL_CRS_CH 	                            "); //學程名稱
		sql.append("    , R4.CRD_NUM  		                            "); //學分數(科次)
		sql.append("    , R4.APP_ITEM_CODE 	                            "); //證件代號
		sql.append("    , R3.CLASS_GROUP_CODE 	                        "); //課群代號
		sql.append("    , R3.CLASS_GROUP_NAME 	                        "); //課群名稱
		sql.append("    , R3.CLASS_GROUP_COND 	                        "); //課群條件
		sql.append("    , R3.CLASS_GROUP_AUDIT 		                    "); //課群審核
		sql.append("    ,                                                       ");
		sql.append("    (                                                       ");
		sql.append("      SELECT SUM (C2.CRD)                                   ");
		sql.append("      FROM COUT019 C1                                       ");
		sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
		sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
		sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
		sql.append("    ) AS GROUP_TOT_CRD  				                       "); //課程總學分
		sql.append("    ,                                                       ");
		sql.append("    (                                                       ");
		sql.append("      SELECT COUNT (1)                                      ");
		sql.append("      FROM COUT019 C1                                       ");
		sql.append("      JOIN COUT002 C2 ON C2.CRSNO = C1.CRSNO                ");
		sql.append("      WHERE C1.TOTAL_CRS_NO = R2.TOTAL_CRS_NO               ");
		sql.append("      AND C1.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE         ");
		sql.append("    ) AS GROUP_TOT_CRS 				                       "); //課程總科次
		sql.append("    , R3.CRD_NUM AS GROUP_COND_CRD 				           "); //課群條件學分
		sql.append("    , R3.IS_REQUIRED 		                               "); //必選修
		sql.append("    , R2.CRSNO AS COUT019_CRSNO 				               "); //學程主要科目
		sql.append("    , R2.GRP 		                                       "); //組別
		//抵免則要取原成績取得學年期，反之則用學分銀行的學年期  
		sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END AS GET_AYEAR 		"); //取得學年
		sql.append("    , CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END AS GET_SMS 	            "); //取得學期
		sql.append("    , M.CRSNO                                                                                        		"); //科目代號
		sql.append("    , R7.CRS_NAME                                                                                         	"); //科目名稱
		sql.append("    , M.CRD 	                                                                                                "); //學分數
		//抵免呈現抵免字樣，反之則呈現成績(103gra0007抵免改為採認)                                                      
		sql.append("    ,                                                                                                               ");
		sql.append("    CASE                                                                                                            ");
		sql.append("      WHEN M.GET_MANNER = '2'                                                                                       ");
		sql.append("        THEN                                                                                                        ");
		sql.append("      CASE                                                                                                          ");
		sql.append("        WHEN R8.AYEAR < '098' THEN '抵免'                                                                           ");
		sql.append("        WHEN R8.AYEAR >= '098' AND R8.SOURCE_AYEAR IS NOT NULL THEN '採認'                                          ");
		sql.append("        WHEN R8.AYEAR >= '098' AND SUBSTR (R8.CRSNO, 3, 1) = '8' THEN '採認'                                        ");
		sql.append("        WHEN R8.AYEAR >= '098' AND R8.ADOPT_CRD IS NOT NULL THEN '採認'                                             ");
		sql.append("      ELSE '抵免'                                                                                                   ");
		sql.append("      END                                                                                                           ");
		sql.append("    ELSE                                                                                                            ");
		sql.append("      TO_CHAR (M.MARK)                                                                                              ");
		sql.append("    END                                                                                                             ");
		sql.append("    AS MARK 																											"); //成績
		sql.append("    , MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE M.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE M.SMS END)) OVER (PARTITION BY M.STNO, R3.TOTAL_CRS_NO, R2.CRSNO) AS MAX_GET_AYEARSMS  "); //最大取得學年期
		sql.append("    FROM STUT014 M																									");
		sql.append("    JOIN                                                                                                            ");
		sql.append("    (                                                                                                               ");
		sql.append("      SELECT C1.*, C1.CRSNO AS GET_CRSNO                                                                            ");
		sql.append("      FROM COUT019 C1                                                                                               ");
		sql.append("      UNION                                                                                                         ");
		sql.append("      SELECT C1.*, C2.MULTI_CRSNO AS GET_CRSNO                                                                      ");
		sql.append("      FROM COUT020 C2                                                                                               ");
		sql.append("      JOIN COUT019 C1 ON C2.CRSNO = C1.CRSNO AND C2.TOTAL_CRS_NO = C1.TOTAL_CRS_NO                                  ");
		sql.append("    ) R2 ON R2.GET_CRSNO = M.CRSNO                                                                                  ");
		sql.append("    JOIN COUT018 R3 ON R3.FACULTY_CODE = R2.FACULTY_CODE AND R3.TOTAL_CRS_NO = R2.TOTAL_CRS_NO AND R3.CLASS_GROUP_CODE = R2.CLASS_GROUP_CODE			  "); //取已公告的學程
		sql.append("    JOIN COUT016 R4  ON R4.FACULTY_CODE = R3.FACULTY_CODE AND R4.TOTAL_CRS_NO = R3.TOTAL_CRS_NO AND R4.ANNO_MK = '1' 					              ");
		sql.append("    JOIN SYST003 R5  ON R5.FACULTY_CODE = R4.FACULTY_CODE                                                                                               ");
		sql.append("    JOIN STUT002 R6 ON R6.IDNO = M.IDNO                                                                                                                 ");
		sql.append("    JOIN COUT002 R7 ON R7.CRSNO = M.CRSNO                                                                                                               ");
		sql.append("    LEFT JOIN CCST003 R8 ON R8.AYEAR = M.AYEAR AND R8.SMS = M.SMS AND R8.STNO = M.STNO AND R8.CRSNO = M.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5')    ");
		sql.append("    WHERE M.STNO = '"+Utility.dbStr(Utility.nullToSpace(ht.get("STNO")))+"'                                                                                      ");
		sql.append("    ORDER BY M.STNO, R6.NAME, R5.FACULTY_CODE, R5.FACULTY_ABBRNAME, R4.TOTAL_CRS_NO, R4.TOTAL_CRS_CH, R3.IS_REQUIRED, R2.CRSNO) M                       ");		
		//GRAV035 END
		sql.append("   WHERE M.STNO = '"+ Utility.dbStr(Utility.nullToSpace(ht.get("STNO"))) + "'                ");
		sql.append("  GROUP BY M.STNO,                                                                           ");
		sql.append("           M.NAME,                                                                           ");
		sql.append("           M.FACULTY_CODE,                                                                   ");
		sql.append("           M.FACULTY_NAME,                                                                   ");
		sql.append("           M.TOTAL_CRS_NO,                                                                   ");
		sql.append("           M.TOTAL_CRS_CH,                                                                   ");
		sql.append("           M.CRD_NUM,                                                                        ");
		sql.append("           M.IS_REQUIRED,                                                                    ");
		sql.append("           M.CLASS_GROUP_CODE,                                                               ");
		sql.append("           M.CLASS_GROUP_NAME,                                                               ");
		sql.append("           M.CLASS_GROUP_COND,                                                               ");
		sql.append("           M.CLASS_GROUP_AUDIT,                                                              ");
		sql.append("           M.GROUP_TOT_CRD,                                                                  ");
		sql.append("           M.GROUP_TOT_CRS,                                                                  ");
		sql.append("           M.GROUP_COND_CRD,                                                                 ");
		sql.append("           M.GRP                                                                             ");
		sql.append("                                                                                             ");
		sql.append(" ) M                                                                                         ");
		sql.append("                                                                                             ");
		sql.append("  GROUP BY  M.STNO,                                                                          ");
		sql.append("         M.NAME,                                                                             ");
		sql.append("         M.FACULTY_CODE,                                                                     ");
		sql.append("        M.FACULTY_NAME,                                                                      ");
		sql.append("        M.TOTAL_CRS_NO,                                                                      ");
		sql.append("        M.TOTAL_CRS_CH,                                                                      ");
		sql.append("        M.CRD_NUM,                                                                           ");
		sql.append("        M.IS_REQUIRED,                                                                       ");
		sql.append("        M.CLASS_GROUP_CODE,                                                                  ");
		sql.append("        M.CLASS_GROUP_NAME,                                                                  ");
		sql.append("        M.CLASS_GROUP_COND,                                                                  ");
		sql.append("        M.CLASS_GROUP_AUDIT,                                                                 ");
		sql.append("        M.COND_CRD                                                                           ");
		sql.append(") M ");
		//GARV021 END
		sql.append(" WHERE M.STNO =  '"
				+ Utility.dbStr(Utility.nullToSpace(ht.get("STNO"))) + "'  ");
		sql.append("  AND  M.TOTAL_CRS_NO = '"
				+ Utility.dbStr(Utility.nullToSpace(ht.get("TOTAL_CRS_NO")))
				+ "' ");
		sql.append(" ORDER BY M.IS_REQUIRED ");

		String T018_CNT = "0";

		try {

			// 取得真正通過的學分數
			STUT010GATEWAY S010 = new STUT010GATEWAY(dbmanager, conn);
			Hashtable htd = S010.getPassCrd_S(ht);
			System.out.println("ht=" + ht);

			DBResult rs = null;
			rs = dbmanager.getSimpleResultSet(conn);
			rs.open();
			rs.executeQuery(sql.toString());
			int row_cnt = 0;
			while (rs.next()) {
				row_cnt++;
				String COND_CRD = UtilityX.checkNullEmpty(
						rs.getString("COND_CRD"), "0");
				String IS_REQUIRED = UtilityX.checkNullEmpty(
						rs.getString("IS_REQUIRED"), "0");
				String CLASS_GROUP_CODE = UtilityX.checkNullEmpty(
						rs.getString("CLASS_GROUP_CODE"), "0");

				String ST_CRD = UtilityX.checkNullEmpty(
						htd.get(IS_REQUIRED + "_" + CLASS_GROUP_CODE),
						"0");
				T018_CNT = UtilityX.checkNullEmpty(rs.getString("T018_CNT"),
						"0");

				System.out.println("COND_CRD=" + COND_CRD);
				System.out.println("IS_REQUIRED=" + IS_REQUIRED);
				System.out.println("CLASS_GROUP_CODE=" + CLASS_GROUP_CODE);
				System.out.println("ST_CRD=" + ST_CRD);

				if (Integer.parseInt(COND_CRD) > Integer.parseInt(ST_CRD)) {
					chk = false;
				}

				}
			// 若修得群組數不等於規定總群組數(可能是完全沒修到該科目)，則為不通過
			if (row_cnt < Integer.parseInt(T018_CNT)) {
				chk = false;
			}
		} catch (Exception e) {
			throw e;
		} finally {

		}
		return chk;
	}	
	
	
	/**
    * 學生IDNO學分銀行寫入學分學程查詢申請銀行暫存檔
    * 
    */
   public Vector getStut010toStut012(Hashtable ht) throws Exception {
       if(ht == null) {
           ht = new Hashtable();
       }
       Vector result = new Vector();
       if(sql.length() > 0) {
           sql.delete(0, sql.length());
       }
       sql.append(
               "select M.IDNO, M.BIRTHDATE, M.CRSNO, M.AYEAR, M.SMS, M.ASYS, M.STNO, M.MARK, M.CRD, M.GET_MANNER, M.RMK, M.UPD_DATE, M.UPD_TIME, M.UPD_USER_ID, M.ROWSTAMP " +
               "from  " +
               "( " +
               "SELECT T.* FROM ( " +
               "SELECT DISTINCT  " +
               "ROW_NUMBER() OVER ( PARTITION BY S10.CRSNO ORDER BY S10.GET_MANNER, S10.MARK DESC, S10.STNO,S10.AYEAR||DECODE(S10.SMS,'3','0',S10.SMS) DESC ) AS CRSNO_ORDER " +
               ",S10.IDNO, S10.BIRTHDATE, S10.CRSNO, S10.AYEAR, S10.SMS, S10.ASYS, S10.STNO, S10.MARK, S10.CRD, S10.GET_MANNER, S10.RMK, S10.UPD_DATE, S10.UPD_TIME, S10.UPD_USER_ID, S10.ROWSTAMP " + 
               ", CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE S10.AYEAR END AS GET_AYEAR   " + 
               ", CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE S10.SMS END AS GET_SMS  " + 
               ", MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE S10.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE S10.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE S10.SMS END)) OVER (PARTITION BY S10.STNO,S10.CRSNO) AS MAX_GET_AYEARSMS  " +
               "FROM STUT010 S10  " +
               "LEFT JOIN CCST003 R8 ON R8.AYEAR = S10.AYEAR AND R8.SMS = S10.SMS AND R8.STNO = S10.STNO AND R8.CRSNO = S10.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5') " +
               "WHERE 1 = 1  " +
               "AND S10.IDNO = (SELECT R1.IDNO FROM STUT003 R1 WHERE R1.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "')  " +
               "AND S10.STNO IN (SELECT R2.STNO FROM STUT003 R2 WHERE R2.STTYPE = '1')  " +
               "ORDER BY S10.CRSNO " +
               ") T where T.CRSNO_ORDER = '1' " +
               ") M WHERE 1=1 "        
               );
       
       if(!orderBy.equals("")) {
           String[] orderByArray = orderBy.split(",");
           orderBy = "";
           for(int i = 0; i < orderByArray.length; i++) {
               orderByArray[i] = "S10." + orderByArray[i].trim();

               if(i == 0) {
                   orderBy += "ORDER BY ";
               } else {
                   orderBy += ", ";
               }
               orderBy += orderByArray[i].trim();
           }
           sql.append(orderBy.toUpperCase());
           orderBy = "";
       }

       DBResult rs = null;
       try {
           if(pageQuery) {
               // 依分頁取出資料
               rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
           } else {
               // 取出所有資料
               rs = dbmanager.getSimpleResultSet(conn);
               rs.open();
               rs.executeQuery(sql.toString());
           }
           Hashtable rowHt = null;
           while (rs.next()) {
               rowHt = new Hashtable();
               /** 將欄位抄一份過去 */
               for (int i = 1; i <= rs.getColumnCount(); i++)
                   rowHt.put(rs.getColumnName(i), rs.getString(i));

               result.add(rowHt);
           }
       } catch (Exception e) {
           throw e;
       } finally {
           if(rs != null) {
               rs.close();
           }
       }
       return result;
   }
   
   public Vector getGra063qQuery(Hashtable ht) throws Exception {
       Vector result = new Vector();

       if(sql.length() > 0)
           sql.delete(0, sql.length());
       
	sql.append("  SELECT M.FACULTY_CODE ");
	sql.append("       , M.FACULTY_NAME ");
	sql.append("       , M.TOTAL_CRS_KIND ");
	sql.append("       , M.TOTAL_CRS_NO ");
	sql.append("       , M.TOTAL_CRS_CH ");
	sql.append("       , DECODE (D.TOTAL_CRS_CH_MK, 'N', '', TOTAL_CRS_CH_MK) AS TOTAL_CRS_CH_MK ");
	sql.append("       , M.STNO ");
	sql.append("       , '至少需選修' || M.CRD_NUM || '學分，已修' || SUM (M.CRD) || '學分' AS GET_COND ");
	sql.append("       ,    '必修' ");
	sql.append("         || (  SELECT DECODE (A.CLASS_GROUP_COND, '1', SUM (C.CRD) || '學分', COUNT (B.CRSNO) || '科數') ");
	sql.append("                 FROM COUT018 A ");
	sql.append("                      JOIN COUT019 B ");
	sql.append("                         ON B.FACULTY_CODE = A.FACULTY_CODE AND B.TOTAL_CRS_NO = A.TOTAL_CRS_NO AND B.CLASS_GROUP_CODE = A.CLASS_GROUP_CODE ");
	sql.append("                      JOIN COUT002 C ");
	sql.append("                         ON C.CRSNO = B.CRSNO ");
	sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '1' AND A.CLASS_GROUP_COND = '1' AND A.CLASS_GROUP_AUDIT = '1' ");
	sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
	sql.append("         || (  SELECT DECODE (A.CLASS_GROUP_COND, '1', SUM (C.CRD) || '學分', COUNT (B.CRSNO) || '科數') ");
	sql.append("                 FROM COUT018 A ");
	sql.append("                      JOIN COUT019 B ");
	sql.append("                         ON B.FACULTY_CODE = A.FACULTY_CODE AND B.TOTAL_CRS_NO = A.TOTAL_CRS_NO AND B.CLASS_GROUP_CODE = A.CLASS_GROUP_CODE ");
	sql.append("                      JOIN COUT002 C ");
	sql.append("                         ON C.CRSNO = B.CRSNO ");
	sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '1' AND A.CLASS_GROUP_COND = '2' AND A.CLASS_GROUP_AUDIT = '1' ");
	sql.append("             GROUP BY A.CLASS_GROUP_COND) ");

	sql.append("         || (  SELECT SUM (A.CRD_NUM) || DECODE (A.CLASS_GROUP_COND, '1', '學分', '科數') ");
	sql.append("                 FROM COUT018 A ");
	sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '1' AND A.CLASS_GROUP_AUDIT = '2' ");
	sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
	sql.append("         || '，已修' ");
	sql.append("            AS A_GET_COND_1 ");
	sql.append("       ,    '選修' ");
	sql.append("         || (  SELECT DECODE (A.CLASS_GROUP_COND, '1', SUM (C.CRD) || '學分', COUNT (B.CRSNO) || '科數') ");
	sql.append("                 FROM COUT018 A ");
	sql.append("                      JOIN COUT019 B ");
	sql.append("                         ON B.FACULTY_CODE = A.FACULTY_CODE AND B.TOTAL_CRS_NO = A.TOTAL_CRS_NO AND B.CLASS_GROUP_CODE = A.CLASS_GROUP_CODE ");
	sql.append("                      JOIN COUT002 C ");
	sql.append("                         ON C.CRSNO = B.CRSNO ");
	sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '2' AND A.CLASS_GROUP_AUDIT = '1' ");
	sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
	sql.append("         || (  SELECT SUM (A.CRD_NUM) || DECODE (A.CLASS_GROUP_COND, '1', '學分', '科數') ");
	sql.append("                 FROM COUT018 A ");
	sql.append("                WHERE A.FACULTY_CODE = M.FACULTY_CODE AND A.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND A.IS_REQUIRED = '2' AND A.CLASS_GROUP_AUDIT = '2' ");
	sql.append("             GROUP BY A.CLASS_GROUP_COND) ");
	sql.append("         || '，已修' ");
	sql.append("            AS B_GET_COND_1 ");
	sql.append("    FROM GRAV035S M JOIN COUT016 D ON D.FACULTY_CODE = M.FACULTY_CODE AND D.TOTAL_CRS_NO = M.TOTAL_CRS_NO AND TO_CHAR (SYSDATE, 'YYYYMMDD') < D.STOP_APPLY_DATE ");
	sql.append("   WHERE M.STNO = '"
			+ Utility.dbStr(UtilityX.checkNullEmpty(ht.get("STNO"), " "))
			+ "' ");
	sql.append("GROUP BY M.FACULTY_CODE, M.FACULTY_NAME, M.TOTAL_CRS_KIND, M.TOTAL_CRS_NO, M.TOTAL_CRS_CH, TOTAL_CRS_CH_MK, M.CRD_NUM, M.STNO ");
	sql.append("ORDER BY M.TOTAL_CRS_KIND DESC, M.FACULTY_CODE,  M.TOTAL_CRS_NO ");

       
       DBResult rs = null;
       try {
           if(pageQuery) {
               // 依分頁取出資料
               rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
           } else {
               // 取出所有資料
               rs = dbmanager.getSimpleResultSet(conn);
               rs.open();
               rs.executeQuery(sql.toString());
           }
           Hashtable rowHt = null;
           while (rs.next()) {
               rowHt = new Hashtable();
               /** 將欄位抄一份過去 */
               for (int i = 1; i <= rs.getColumnCount(); i++){
                   rowHt.put(rs.getColumnName(i), rs.getString(i));
               }
               
               //取得真正通過的學分數
               /*
        	   STUT010GATEWAY S010 = new STUT010GATEWAY(dbmanager,conn);
        	   Hashtable htt = S010.getPassCrd(rowHt);
        	   System.out.println("htt="+htt);
        	   
        	   rowHt.put("A_GET_COND", rs.getString("A_GET_COND_1")+Utility.checkNull(htt.get("1"), "")+rs.getString("A_GET_COND_2"));
        	   rowHt.put("B_GET_COND", rs.getString("B_GET_COND_1")+Utility.checkNull(htt.get("2"), "")+rs.getString("B_GET_COND_2"));
               */
               result.add(rowHt);
           }
       } catch (Exception e) {
           throw e;
       } finally {
           if(rs != null) {
               rs.close();
           }
       }
       return result;
   }
   
   /**
    * 學生IDNO學分銀行寫入微學分學程查詢申請銀行暫存檔
    * 
    */
   public Vector getStut010toStut014(Hashtable ht) throws Exception {
       if(ht == null) {
           ht = new Hashtable();
       }
       Vector result = new Vector();
       if(sql.length() > 0) {
           sql.delete(0, sql.length());
       }
       sql.append(
               "select M.IDNO, M.BIRTHDATE, M.CRSNO, M.AYEAR, M.SMS, M.ASYS, M.STNO, M.MARK, M.CRD, M.GET_MANNER, M.RMK, M.UPD_DATE, M.UPD_TIME, M.UPD_USER_ID, M.ROWSTAMP " +
               "from  " +
               "( " +
               "SELECT T.* FROM ( " +
               "SELECT DISTINCT  " +
               "ROW_NUMBER() OVER ( PARTITION BY S10.CRSNO ORDER BY S10.GET_MANNER, S10.MARK DESC, S10.STNO,S10.AYEAR||DECODE(S10.SMS,'3','0',S10.SMS) DESC ) AS CRSNO_ORDER " +
               ",S10.IDNO, S10.BIRTHDATE, S10.CRSNO, S10.AYEAR, S10.SMS, S10.ASYS, S10.STNO, S10.MARK, S10.CRD, S10.GET_MANNER, S10.RMK, S10.UPD_DATE, S10.UPD_TIME, S10.UPD_USER_ID, S10.ROWSTAMP " + 
               ", CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE S10.AYEAR END AS GET_AYEAR   " + 
               ", CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE S10.SMS END AS GET_SMS  " + 
               ", MAX (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_AYEAR ELSE S10.AYEAR END || DECODE (CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE S10.SMS END, '3', '0', CASE WHEN TRIM (R8.SOURCE_STNO) IS NOT NULL THEN R8.SOURCE_SMS ELSE S10.SMS END)) OVER (PARTITION BY S10.STNO,S10.CRSNO) AS MAX_GET_AYEARSMS  " +
               "FROM STUT010 S10  " +
               "LEFT JOIN CCST003 R8 ON R8.AYEAR = S10.AYEAR AND R8.SMS = S10.SMS AND R8.STNO = S10.STNO AND R8.CRSNO = S10.CRSNO AND R8.REPL_MK IN ('1', '2', '3', '5') " +
               "WHERE 1 = 1  " +
               "AND S10.IDNO = (SELECT R1.IDNO FROM STUT003 R1 WHERE R1.STNO = '" + Utility.nullToSpace(ht.get("STNO")) + "')  " +
               "AND S10.STNO IN (SELECT R2.STNO FROM STUT003 R2 WHERE 1=1 )  " +
               "ORDER BY S10.CRSNO " +
               ") T where T.CRSNO_ORDER = '1' " +
               ") M WHERE 1=1 "        
               );
       
       if(!orderBy.equals("")) {
           String[] orderByArray = orderBy.split(",");
           orderBy = "";
           for(int i = 0; i < orderByArray.length; i++) {
               orderByArray[i] = "M." + orderByArray[i].trim();

               if(i == 0) {
                   orderBy += "ORDER BY ";
               } else {
                   orderBy += ", ";
               }
               orderBy += orderByArray[i].trim();
           }
           sql.append(orderBy.toUpperCase());
           orderBy = "";
       }

       DBResult rs = null;
       try {
           if(pageQuery) {
               // 依分頁取出資料
               rs = Page.getPageResultSet(dbmanager, conn, sql.toString(), pageNo, pageSize);
           } else {
               // 取出所有資料
               rs = dbmanager.getSimpleResultSet(conn);
               rs.open();
               rs.executeQuery(sql.toString());
           }
           Hashtable rowHt = null;
           while (rs.next()) {
               rowHt = new Hashtable();
               /** 將欄位抄一份過去 */
               for (int i = 1; i <= rs.getColumnCount(); i++)
                   rowHt.put(rs.getColumnName(i), rs.getString(i));

               result.add(rowHt);
           }
       } catch (Exception e) {
           throw e;
       } finally {
           if(rs != null) {
               rs.close();
           }
       }
       return result;
   }
   
}