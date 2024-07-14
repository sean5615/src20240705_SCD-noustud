<%@ page import="com.nou.aut.AUTICFM, com.acer.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/pages/checkUser.jsp" %>
<%@ include file="../../include/pages/query.jsp" %>
<%@ include file="../../include/pages/query1_2_0_2.jsp" %>
<%
String STUDENT_ID = request.getParameter("STUDENT_ID").trim();
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>list</title>
    <script language="javascript">
<!--	
	/** 初始設定頁面資訊 */
	var	printPage		=	"_01p1";	//列印頁面
	var	editMode		=	"ADD";				//編輯模式, ADD - 新增, MOD - 修改
	var	lockColumnCount		=	-1;				//鎖定欄位數
	var	listShow		=	true;				//是否一進入顯示資料
	var	_privateMessageTime	=	-1;				//訊息顯示時間(不自訂為 -1)
	var	pageRangeSize		=	10;				//畫面一次顯示幾頁資料
	var	controlPage		=	"_01c2";	//控制頁面
	var	checkObj		=	new checkObj();			//核選元件
	var	queryObj		=	new queryObj();			//查詢元件
	var	importSelect		=	false;				//匯入選取欄位功能
	var	noPermissAry		=	new Array();			//沒有權限的陣列
	
	/** 網頁初始化 */
	function page_init(loadTime)
	{
		if (loadTime != "second")
			page_init_start();
	
		
		/** 權限檢核 */
		securityCheck();
		
		/** === 初始欄位設定 === */
		/** 初始上層帶來的 Key 資料 */
		iniMasterKeyColumn();
		
		/** 初始查詢欄位 */
		Form.iniFormSet('QUERY', 'STNO', 'S', 9, 'N', 'M', 9, 'A');
		Form.iniFormSet('QUERY', 'NAME', 'S', 9, 'M', 9,'R','1');
		Form.iniFormColor();
		/** === 設定檢核條件 === */
		/** 查詢欄位 */
	
		/** ================ */
	
		if (loadTime != "second")
			page_init_end();
			
		/** 取得學生姓名 */
		getSTNO_NAME();
	}
	
	/**
	初始化 Grid 內容
	@param	stat	呼叫狀態(init -> 網頁初始化)
	*/
	function iniGrid(stat)
	{
		var	gridObj	=	new Grid();
	
		iniGrid_start(gridObj)
	
		/** 設定表頭 */
		gridObj.heaherHTML.append
		(
		        "<tr class=listHeader align=center>\
					<td width=20 style= 'display:none'>&nbsp;</td>\
					<td width=100>&nbsp;</td>\
					<td resize='on' nowrap>學年期</td>\
					<td resize='on' nowrap>學號</td>\
					<td resize='on' nowrap>姓名</td>\
					<td resize='on' nowrap>績優生類別</td>\
					<td resize='on' nowrap>中心別</td>\
					<td resize='on' nowrap>科目名稱</td>\
				</tr>"
		);
	
		if (stat == "init" && !listShow)
		{
			/** 初始化及不顯示資料只秀表頭 */
			document.getElementById("grid-scroll").innerHTML	=	gridObj.heaherHTML.toString().replace(/\t/g, "") + "</table>";
			Message.hideProcess();
		}
		else
		{
			/** 頁次區間同步 */
			Form.setInput ("QUERY", "pageSize",	Form.getInput("RESULT", "_scrollSize"));
			Form.setInput ("QUERY", "pageNo",	Form.getInput("RESULT", "_goToPage"));
	
			/** 處理連線取資料 */
			var	callBack	=	function callBack(ajaxData)
			{
				if (ajaxData == null)
					return;
	
				/** 設定表身 */
				var	keyValue	=	"";
				var	DkeyValue	=	"";
				var	editStr		=	"";
				var	delStr		=	"";
	
				for (var i = 0; i < ajaxData.data.length; i++, gridObj.rowCount++)
				{
					keyValue	=	"AYEAR|" + ajaxData.data[i].AYEAR + "|SMS|" + ajaxData.data[i].SMS + "|KIND|" + ajaxData.data[i].KIND + "|STNO|" + ajaxData.data[i].STNO + "|CRSNO|" + ajaxData.data[i].CRSNO;
					
					/** 判斷權限 */
						
					var doPrint1 = "doPrint('../../scd/scd216q/_01p1?PRINTFORM=QUERY&AYEAR="+ajaxData.data[i].AYEAR+"&SMS="+ajaxData.data[i].SMS+"&STNO="+ajaxData.data[i].STNO+"&CENTER_CODE="+ajaxData.data[i].CENTER_CODE+"&CRSNO="+ajaxData.data[i].CRSNO+"&KIND="+ajaxData.data[i].KIND+"&print_type="+ajaxData.data[i].KIND+"');";
										
	                printStr1	=	"onclick=\""+doPrint1+"\"><a href=\"javascript:void(0)\">獎狀</a>";
					
					gridObj.gridHtml.append
					(
						"<tr class=\"listColor0" + ((gridObj.rowCount % 2) + 1) + "\">\
							<td  style= 'display:none' align=center><input type=checkbox name='chkBox' value=\"" + keyValue + "\"></td>\
							<td align=center " + printStr1 + "</td>\
							<td>" + ajaxData.data[i].AYEAR_NAME + ajaxData.data[i].SMS_NAME + "&nbsp;</td>\
							<td>" + ajaxData.data[i].STNO + "&nbsp;</td>\
							<td>" + ajaxData.data[i].NAME + "&nbsp;</td>\
							<td>" + ajaxData.data[i].KIND_NAME + "&nbsp;</td>\
							<td>" + ajaxData.data[i].CENTER_CODE_NAME + "　" + "&nbsp;</td>\
							<td>" + ajaxData.data[i].CRS_NAME + "&nbsp;</td>\
						</tr>"
					);
				}
				gridObj.gridHtml.append ("<tr></tr>");
				
				/** 無符合資料 */
				if (ajaxData.data.length == 0)
					gridObj.gridHtml.append ("<tr><td colspan='15'><font color=red><b>　　　查無符合資料!!</b></font></td></tr>");
	
				iniGrid_end(ajaxData, gridObj);			
			}
			sendFormData("QUERY", controlPage, "QUERY_MODE", callBack);
		}
	}
	
	/** 查詢功能時呼叫 */
	function doQuery()
	{
		doCopy();
		doQuery_start();
	
		return doQuery_end();
	}
	
	/** 新增功能時呼叫 */
	function doAdd()
	{
		/** 開啟新增 Frame */
		top.showView();
	
		/** 呼叫新增 */
		top.viewFrame.doAdd();
	}
	
	/** ============================= 欲修正程式放置區 ======================================= */
	/** 設定功能權限 */
	function securityCheck()
	{
		try
		{
			/** 查詢 */
			if (!<%=AUTICFM.securityCheck (session, "QRY")%>)
			{
				noPermissAry[noPermissAry.length]	=	"QRY";
				try{Form.iniFormSet("QUERY", "QUERY_BTN", "D", 1);}catch(ex){}
			}
			/** 新增 */
			if (!<%=AUTICFM.securityCheck (session, "ADD")%>)
			{
				noPermissAry[noPermissAry.length]	=	"ADD";
				editMode	=	"NONE";
				try{Form.iniFormSet("EDIT", "ADD_BTN", "D", 1);}catch(ex){}
			}
			/** 修改 */
			if (!<%=AUTICFM.securityCheck (session, "UPD")%>)
			{
				noPermissAry[noPermissAry.length]	=	"UPD";
			}
			/** 新增及修改 */
			if (!chkSecure("ADD") && !chkSecure("UPD"))
			{
				try{Form.iniFormSet("EDIT", "SAVE_BTN", "D", 1);}catch(ex){}
			}
			/** 刪除 */
			if (!<%=AUTICFM.securityCheck (session, "DEL")%>)
			{
				noPermissAry[noPermissAry.length]	=	"DEL";
				try{Form.iniFormSet("RESULT", "DEL_BTN", "D", 1);}catch(ex){}
			}
			/** 匯出 */
			if (!<%=AUTICFM.securityCheck (session, "EXP")%>)
			{
				noPermissAry[noPermissAry.length]	=	"EXP";
				try{Form.iniFormSet("RESULT", "EXPORT_BTN", "D", 1);}catch(ex){}
				try{Form.iniFormSet("QUERY", "EXPORT_ALL_BTN", "D", 1);}catch(ex){}
			}
			/** 列印 */
			if (!<%=AUTICFM.securityCheck (session, "PRT")%>)
			{
				noPermissAry[noPermissAry.length]	=	"PRT";
				try{Form.iniFormSet("RESULT", "PRT_BTN", "D", 1);}catch(ex){}
				try{Form.iniFormSet("QUERY", "PRT_ALL_BTN", "D", 1);}catch(ex){}
			}
		}
		catch (ex)
		{
		}
	}
	
	/** 檢查權限 - 有權限/無權限(true/false) */
	function chkSecure(secureType)
	{
		if (noPermissAry.toString().indexOf(secureType) != -1)
			return false;
		else
			return true
	}
	/** ====================================================================================== */
	/** 初始上層帶來的 Key 資料 */
	function iniMasterKeyColumn()
	{
		/** 非 Detail 頁面不處理 */
		if (typeof(keyObj) == "undefined")
			return;
		/** 塞值 */
		for (keyName in keyObj)
		{
			try {Form.iniFormSet("QUERY", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
			try {Form.iniFormSet("EDIT", keyName, "V", keyObj[keyName], "R", 0);}catch(ex){};
		}
		Form.iniFormColor();
	}
	
	/** 處理列印動作 */
	/** 處理列印動作 */
	function doPrint(url)
	{
		/** 取消 onsubmit 功能防止重複送出 */
		//event.returnValue	=	false;
	
		/** 開始處理 */
		Message.showProcess();
	
		var	printWin	=	WindowUtil.openPrintWindow("", "Print");
	
		Form.doSubmit("RESULT", url, "post", "Print");
	
		printWin.focus();
	
		/** 停止處理 */
		Message.hideProcess();
	}
	
	
	//取得學生姓名
	function getSTNO_NAME(){
		Form.setInput ("QUERY", "NAME",	 "");
		if( Form.getInput("QUERY", "STNO") == '' ){
			return;
		}
   		var	callBack	=	function callBack(ajaxData)
   		{	   
   			if (ajaxData.data[0].StnoSttype == ""){
   			    alert("查無此學號姓名!");		    
   			    doQuery();
   			    Form.setInput ("QUERY", "STNO","");
   			    //return;
   			 	window.location = '../../home/home00/main.htm';
   		    }else{
   			    Form.setInput ("QUERY", "NAME",	 ajaxData.data[0].StnoName);
   			    doQuery();
   			}
   		}
		sendFormData("QUERY", controlPage, "GET_STNO_NAME_MODE", callBack, false);
	}
	
	
	function doPrint1(url)
	{
		/** 取消 onsubmit 功能防止重複送出 */
		//event.returnValue	=	false;
	
		/** 開始處理 */
		Message.showProcess();
	
		var	printWin	=	WindowUtil.openPrintWindow("", "Print");
	
		Form.doSubmit("RESULT", url, "post", "Print");
	
		printWin.focus();
	
		/** 停止處理 */
		Message.hideProcess();
	}	
	
    </script>
</head>

<body>
<span id="lblFunc" class="title"></span>
<jsp:include page="../../include/pages/title.jsp" />

<div align="center">
<%
		//showMsg((String)request.getAttribute("message"), out);
%>
</div>

<!----------------------查詢條件區開始------------------------------------------------------>
<div id="panelSearch" style="width: 100%;">

<form name="QUERY" method="post" id="QUERY">	
	<input type="hidden" name="control_type" value="QUERY_MODE" />
	<input type="hidden" name="pageNo" value="1" />
	<input type="hidden" id="pageSize" name="pageSize" value="<%=StringUtils.defaultString((String)request.getParameter("pageSize"), "10") %>" />
	
    <table class="boxSearch" cellSpacing="0" cellPadding="5" width="100%" align="center">
		<tr>
			<td align='right'>學號<font color=red>＊</font>：</td>
			<td>
				<input type=text name='STNO' value='<%=STUDENT_ID %>' onblur='getSTNO_NAME();' <%=STUDENT_ID.length()>0?" disabled ":"" %>>
				<input type=text name='NAME' disabled>
			</td>
		</tr>
		<tr STYLE='display:none'>			
			<td colspan="2" align="right">
	        	<div class="btnarea">	
	        		<!-- 從右到左 -->	 	        		
	        		<%=ButtonUtil.GetButton("javascript:void(0);", "doQuery();", "查  詢", false, false, true, "QUERY_BTN") %>       	
	        		<%=ButtonUtil.GetButton("javascript:void(0);", "doReset();getSTNO_NAME();", "清  除", false, false, true, "")  %>	   		
		        </div>
	        </td>
		</tr>		
    </table>	
</form>

</div>


<!----------------------查詢條件區結束------------------------------------------------------>
<div><img src="../../images/searchline.jpg" width="196" height="1" /></div>
<div style='line-height:10px'>&nbsp;</div>


<!----------------------結果列表區開始------------------------------------------------------>
<div id="panelList" style="width: 100%;">

<form name="RESULT" method="post"  id="RESULT">
	<input type="hidden" name="control_type" value="" />
	
	<input type=hidden keyColumn="Y"  name="FACULTY_CODE">
	<input type=hidden keyColumn="Y"  name="TOTAL_CRS_NO">
	<input type=hidden keyColumn="Y"  name="STNO">

    <table cellspacing="0" cellpadding="0" border="0" width="100%">
	<!--<tr>
		<td width="10">&nbsp;</td>
	    <td height="30">
		注意事項<br>說明文字
	    </td>
	</tr>-->
    </table>
    <table class="box1" cellSpacing="0" cellPadding="0" border="0" width="100%" align="center">
	<tr>
	    <td colspan="3">
		<!----------------------工具列------------------------------------------------------>
		<table cellSpacing="0" cellPadding="0" border="0" width="100%" align="center">
		    <tr height="30">
				<td style="display:none">
					<!-- 從左到右 -->
					<%=ButtonUtil.GetButton("javascript:void(0);", "Form.setCheckBox(1, 'RESULT');", "全    選", false, false, false, "SELALL_BTN") %>
					<%=ButtonUtil.GetButton("javascript:void(0);", "Form.setCheckBox(0, 'RESULT');", "全 不 選", false, false, false, "SELNONE_BTN") %>	
					<%=ButtonUtil.GetButton("javascript:void(0);", "doPrint('RESULT');", "列印學程修課資料", false, false, false, "PRINT_BTN") %>											    				   
				</td>
				<td align='right' valign='bottom'>
					<jsp:include page="../../include/pages/page.jsp" />					
				</td>
		    </tr>
		</table>
		<!----------------------結果列表------------------------------------------------------>
		<table class="box2" rules="all" id="grid-scroll" width="100%" align="center" />	
		</td>		   	   	   
	</tr>
    </table>    
</form>

</div>
</body>
</html>
<script>
document.write ("<font color=\"white\">" + document.lastModified + "</font>");
window.addEventListener("load", page_init);
</script>