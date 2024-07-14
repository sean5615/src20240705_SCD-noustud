package com.acer.service.scd;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

import org.springframework.stereotype.Service;

import com.acer.ajax.DataToJson;
import com.acer.db.DBManager;
import com.acer.db.query.DBResult;
import com.acer.file.RptFile;
import com.acer.report.report;
import com.acer.service.others.ServiceBase;
import com.acer.util.Utility;
import com.nou.aut.AUTCONNECT;
import com.nou.cou.dao.COUT101DAO;
import com.nou.stu.dao.STUT010GATEWAY;
import com.nou.stu.dao.STUT012DAO;
import com.nou.scd.dao.*;

@Service
public class SCD216QService extends ServiceBase {
	/** 處理查詢 Grid 資料 */
	public void doQuery(PrintWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
	{
		try
		{		
			Connection	conn		=	dbManager.getConnection(AUTCONNECT.mapConnect("COU", session));
			int			pageNo		=	Integer.parseInt(Utility.checkNull(requestMap.get("pageNo"), "1"));
			int			pageSize	=	Integer.parseInt(Utility.checkNull(requestMap.get("pageSize"), "10"));
			
			if (requestMap.get("STNO").equals("")){
			    requestMap.put("STNO","AAAAAAAAA");
			}

			SCDT021GATEWAY scd021gateway = new SCDT021GATEWAY(dbManager, conn, pageNo, pageSize);

			Vector result = scd021gateway.getScd216qQuery(requestMap);

			out.println(DataToJson.vtToJson(scd021gateway.getTotalRowCount(), result));
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			dbManager.close();
		}
	}

	/** 取得學生姓名 */
	public void getStnoName(PrintWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception
	{

		try
		{
			Connection	conn	=	dbManager.getConnection(AUTCONNECT.mapConnect("COU", session));
			STUT010GATEWAY stut010gateway = new STUT010GATEWAY(dbManager, conn);
			
			Vector result =  stut010gateway.getScd216QStnoName(requestMap);

			StringBuffer sb = new StringBuffer();
			String name = "查無此學號姓名!";
			String sttype = "";
			String enrollstatus = "";
			if(result.size()>0){
				Hashtable ht = (Hashtable)result.get(0);
				
				name = Utility.checkNull(ht.get("NAME"), "").trim();
				sttype = Utility.checkNull(ht.get("STTYPE"), "");
				enrollstatus = Utility.checkNull(ht.get("ENROLL_STATUS"), "").trim();
			}
				
	    	// 將所需顯示的資料放入Hashtalbe中
			Hashtable result_1 = new Hashtable();
			result_1.put("StnoName", name);
			result_1.put("StnoSttype", sttype);
			result_1.put("EnrollStatus", enrollstatus);
			
			out.println(DataToJson.htToJson(result_1));
		}
		catch (Exception ex)
		{
			throw ex;
		}
		finally
		{
			dbManager.close();
		}
	}
	

	public void doPrint(JspWriter out, DBManager dbManager, Hashtable requestMap, HttpSession session) throws Exception {
		try {
			Connection conn = dbManager.getConnection(AUTCONNECT.mapConnect("NOU", session));
			SCDT021GATEWAY scd021gateway = new SCDT021GATEWAY(dbManager, conn);
			Vector vt = scd021gateway.getMainDataToPrintForSCD216Q(requestMap, session); // 如有重新取號時,取得更新後的資料
			if (vt.size() == 0) {
				out.println("<script>top.close();alert(\"無符合資料可供列印!!\"); window.close(); </script>");
				return;
			}
			/** 初始化 rptFile */
			RptFile rptFile = new RptFile(session.getId());
			rptFile.setColumn("表身_1,表身_2");	
			// 取得前端列印條件
			String rank = (String) requestMap.get("print_type");
			String ASYS = Utility.dbStr(requestMap.get("ASYS"));
			String tmpstr = "";
			String tmpstr1 = "";
			String asys_name = "";

			if ("1".equals(Utility.dbStr(requestMap.get("ASYS")))) {
				asys_name = "大學部";
			} else {
				asys_name = "專科部";
			}

			if (rank.equals("1"))
				tmpstr1 = "名列";
			else if (rank.equals("2"))
				tmpstr1 = "名列中心";
			else if (rank.equals("3"))
				tmpstr1 = "名列";
			else if (rank.equals("4"))
				tmpstr1 = "名列中心第";

			for (int i = 0; i < vt.size(); i++) {
				Hashtable content = (Hashtable) vt.get(i);
				String centerName = this.getCenterNameStyle(content.get("CENTER_CODE_NAME").toString());
				String name = this.getNameStyle(content.get("NAME").toString());
				String crsName = this.getCrsrNameStyle(content.get("CRS_NAME").toString());
				String ayearSms = this.getYearSmsStyle(toChanisesFullChar((String) ((Hashtable) vt.get(i)).get("AYEAR")) + "學年度" + ((Hashtable) vt.get(i)).get("SMS_NAME"));

				// 當科目名稱少於7個字時,需再多一行空白行
				int nameLength = content.get("NAME").toString().length();
				int centerNameLength = ((Hashtable) vt.get(i)).get("CENTER_CODE_NAME").toString().length();
				String addSpace = nameLength + centerNameLength + tmpstr.length() <= 20 ? "<tr><td colspan='3' class=space4></td></tr>" : "";
				String contentHtml = "<table width=100% >" + "<tr><td align=center colspan='4' valign=top class=title><font class=title1><br><br><br>國立空中大學獎狀</font></td></tr>" + "<tr><td align=right colspan='4' valign=top style='height:0.5cm;'><font class=title2>(" + Integer.parseInt(Utility.checkNull(Utility.checkNull(content.get("PRNYEAR"), ""), "0")) + ")空大學獎字第" + ((Hashtable) vt.get(i)).get("AWARD_NO") + "號</font></td></tr>" + "<tr><td colspan='4' class=space1>　</td></tr></table>";

				if (rank.equals("1")) {
					contentHtml += "<table width=100% ><tr class=space3>" + centerName + "<td width='13%' class=td3>學生</td>" + name + "</tr></table><table width=100% >" + "<tr class=space3><td colspan='4' align='left' class=td2>"
							+ toChanisesFullChar((String) ((Hashtable) vt.get(i)).get("AYEAR")) + "學年度" + ((Hashtable) vt.get(i)).get("SMS_NAME") + "學業成績優良<br>" + tmpstr1 + ((Hashtable) vt.get(i)).get("STNO_ASYS_NAME") + "全校第" + toChanisesFullChar(String.valueOf(Integer.parseInt((String) ((Hashtable) vt.get(i)).get("RANK")))) + "名 </td></tr>"
							+ "<tr class=space3 ><td colspan='4' class=td4>殊堪嘉許特頒獎狀以資鼓勵</td></tr>";
				} else if (rank.equals("2")) {
					contentHtml += "<table width=100% ><tr class=space3>" + centerName + "<td width='13%' class=td3>學生</td>" + name + "</tr></table><table width=100% >" + "<tr class=space3><td colspan='4' align='left' class=td2>"
							+ toChanisesFullChar((String) ((Hashtable) vt.get(i)).get("AYEAR")) + "學年度" + ((Hashtable) vt.get(i)).get("SMS_NAME") + "學業成績優良<br>" + tmpstr1 + ((Hashtable) vt.get(i)).get("STNO_ASYS_NAME") + "第" + toChanisesFullChar(String.valueOf(Integer.parseInt((String) ((Hashtable) vt.get(i)).get("RANK")))) + "名 </td></tr>"
							+ "<tr class=space3 ><td colspan='4' class=td4>殊堪嘉許特頒獎狀以資鼓勵</td></tr>";
				} else if (rank.equals("3")) {
					contentHtml += "<table width=100% ><tr class=space3>" + centerName + "<td width='13%' class=td3>學生</td>" + name + "</tr></table><table width=100% >" + "<tr class=space3>" + ayearSms + crsName + "</tr>"
							+ "<tr class=space3><td colspan='4' class=td2>成績優良" + tmpstr1 + ((Hashtable) vt.get(i)).get("STNO_ASYS_NAME") + "全校第" + toChanisesFullChar(String.valueOf(Integer.parseInt((String) ((Hashtable) vt.get(i)).get("RANK")))) + "名殊堪嘉許</td></tr>"
							+ "<tr class=space3><td colspan='4' class=td3>特頒獎狀以資鼓勵 </td></tr>";

				} else if (rank.equals("4")) {
					contentHtml += "<table width=100% ><tr><font class=td1>" + centerName + "<td><font class=td1>" + ((Hashtable) vt.get(i)).get("STNO_ASYS_NAME") + "學生</td>" + name + "</tr></table><table width=100% >" + "<tr class=space3>" + ayearSms + crsName + "</tr>"
							+ "<tr class=space3><td colspan='4' class=td2 >成績優良" + tmpstr1 + toChanisesFullChar(String.valueOf(Integer.parseInt((String) ((Hashtable) vt.get(i)).get("RANK")))) + "名殊堪嘉許</td></tr>" + "<tr class=space3><td colspan='4' class=td3 >特頒獎狀以資鼓勵 </td></tr>";

				}

				String ayear = Utility.checkNull(content.get("PRNYEAR"), "");
				String month = Utility.checkNull(content.get("PRNMONTH"), "");
				String day = Utility.checkNull(content.get("PRNDAY"), "");
				System.out.println("ayear=" + ayear);
				if (ayear.length() > 0 && ayear.charAt(0) == '0')
					ayear = ayear.substring(1);
				if (month.length() > 0 && month.charAt(0) == '0')
					month = month.substring(1);
				if (day.length() > 0 && day.charAt(0) == '0')
					day = day.substring(1);
				contentHtml += addSpace + "<tr><td colspan='4' class=space2>　</td></tr>" + "<tr><td colspan='4' align=left valign=top class=down><br><br><br><br><br><br><br>&nbsp;中&nbsp;華&nbsp;民&nbsp;國&nbsp;&nbsp;" + toChanisesFullChar(ayear) + "&nbsp;&nbsp;年&nbsp;&nbsp;" + toChanisesFullChar(month) + "&nbsp;&nbsp;月&nbsp;" + toChanisesFullChar(day) + "&nbsp;&nbsp;日</td></tr>" + "</table>";

				rptFile.add(contentHtml);
				rptFile.add("<!--" + i + "-->");
			}

			if (rptFile.size() == 0) {
				out.println("<script>top.close();alert(\"無符合資料可供列印!!\");window.close();</script>");
				return;
			}

			/** 初始化報表物件 */
			report report_ = new report(dbManager, conn, out, "scd216q_01r1", report.onlineHtmlMode);

			/** 靜態變數處理 */
			Hashtable ht = new Hashtable();
			report_.setDynamicVariable(ht);

			/** 開始列印 */
			report_.genReport(rptFile);
		} catch (Exception ex) {
			throw ex;
		} finally {
			dbManager.close();
		}
	}
	

	//將姓名轉成所要顯示的格式
	private String getNameStyle(String name) {
		String result = name;

		// 姓名2-8個字
		int nameLength = name.length();
		if (nameLength <= 5)
			result = "<td width=28% align='center' colspan='2' class=name1>" + name + "</td>";
		else if (nameLength == 6)
			result = "<td width=28% align='center' colspan='2' class=name2>" + name + "</td>";
		else if (nameLength == 7)
			result = "<td width=28% align='center' colspan='2' class=name3>" + name + "</td>";
		else if (nameLength > 7)
			result = "<td width=28% align='center' colspan='2' class=name4>" + name + "</td>";
			
		return result;
	}

	//將中心轉成所要顯示的格式
	private String getCenterNameStyle(String centerName) {
		String result = centerName;

		int centerNameLength = centerName.length();
		if (centerNameLength <= 8)
			result = "<td class=CenterName1>" + centerName + "</td>";
		// 台北第二學習指導中心<====已經變成 台北(二)學習指導中心 不適用原本的字串長度等於10   20090519  by barry
		//改成長度大於8就用下面的style    20090519  by barry
		else
			result = "<td class=CenterName2>" + centerName + "</td>";
			
		return result;
	}

	//將科目名稱轉成所要顯示的格式
	private String getCrsrNameStyle(String crsName) {
		String result = crsName;

		// 超過20個字則僅顯示20個字(含科字共21個字)
		crsName = crsName.length() > 21 ? crsName.substring(0, 21) : crsName;

		int crsNameLength = this.caluateStringLength(crsName); // 因英文字的寬度和中文字寬度並不相同,因此在算字數顯示的時候會有問題,用這個method來粗略估算可以放多少中英文字
		if (crsNameLength <= 6)
			result = "<td align='center' colspan='2' class=CrsName1>" + crsName + "</td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 7)
			result = "<td align='center' colspan='2'><font style='font-size:23pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 8)
			result = "<td align='center' colspan='2'><font style='font-size:20pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 9)
			result = "<td align='center' colspan='2'><font style='font-size:18pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 10)
			result = "<td align='center' colspan='2'><font style='font-size:16pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 11)
			result = "<td align='center' colspan='2'><font style='font-size:14pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 12)
			result = "<td align='center' colspan='2'><font style='font-size:13pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 13)
			result = "<td align='center' colspan='2'><font style='font-size:12pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 14)
			result = "<td align='center' colspan='2'><font style='font-size:11pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 15 || crsNameLength == 16)
			result = "<td align='center' colspan='2'><font style='font-size:10pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 17 || crsNameLength == 18)
			result = "<td align='center' colspan='2'><font style='font-size:9pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";
		else if (crsNameLength == 19 || crsNameLength == 20)
			result = "<td align='center' colspan='2'><font style='font-size:8pt;font-family:標楷體;'>" + crsName + "</font></td><td width='6%' class=CrsName1>科</td>";

		return result;
	}

	//將學年期轉成所要顯示的格式
	private String getYearSmsStyle(String yearSms) {
		String result = yearSms;

		int yearSmsLength = yearSms.length();
		if (yearSmsLength <= 8)
			result = "<td class=YearSms>" + yearSms + "</td>";
		// 年度如為100年時,則此字串最多為9個字
		else if (yearSmsLength == 9)
			result = "<td class=YearSms>" + yearSms + "</td>";

		return result;
	}

	//判斷字串的長度(2個英文字算1個長度,1個中文字算1個長度)
	private int caluateStringLength(String string) {
		int result = 0;

		char[] stringArray = string.toCharArray();
		int notChineseCount = 0; // 該字串共有多少非中文字
		for (int i = 0; i < stringArray.length; i++) {
			// 表示為英文--粗略的判斷
			if (stringArray[i] < 200)
				notChineseCount++;
			else
				result++;
		}

		return result + (int) Math.ceil(notChineseCount / 2.2);
	}

	/** 轉成中文全型字 */
	public static String toChanisesFullChar(String s) {
		if (s == null || s.equals("")) {
			return "";
		}

		char[] ca = s.toCharArray();
		for (int i = 0; i < ca.length; i++) {
			if (ca[i] > '\200') {
				continue;
			} //超過這個應該都是中文字了…
			if (ca[i] == 32) {
				ca[i] = (char) 12288;
				continue;
			} //半型空白轉成全型空白
			if (Character.isLetterOrDigit(ca[i])) {
				ca[i] = (char) (ca[i] + 65248);
				continue;
			} //是有定義的字、數字及符號

			ca[i] = (char) 12288; //其它不合要求的，全部轉成全型空白。
		}

		return String.valueOf(ca);
	}
}
