<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../include/pages/query.jsp" %>
<%@ include file="../../include/pages/titleSetup.jsp" %>
<script>
<%
//判斷使用者
java.util.Vector dep = null;
String STUDENT_ID = "";	//此參數有值代表為學生
if("1".equals( Utility.nullToSpace(session.getAttribute("ID_TYPE"))) || "4".equals( Utility.nullToSpace(session.getAttribute("ID_TYPE")) )){	//學生
	STUDENT_ID = Utility.nullToSpace(session.getAttribute("USER_ID")).trim();
}

/** 傳遞 Key 參數 */
String    keyParam    =    com.acer.util.Utility.checkNull(request.getParameter("keyParam"), "");
if(!keyParam.equals("") && keyParam.length() > 0) {
    keyParam += "&STUDENT_ID=" + STUDENT_ID;
} else {
    keyParam = "?STUDENT_ID=" + STUDENT_ID;
}

%>

	top.viewFrame.location.href	=	'about:blank';
	top.hideView();
	/** 導向第一個處理的頁面 */
	top.mainFrame.location.href	=	'_01v1<%=keyParam%>';

</script>