package com.acer.controller.scd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.acer.apps.Log4jInit;
import com.acer.controller.others.ControlPageInit;
import com.acer.db.DBManager;
import com.acer.log.MyLogger;
import com.acer.service.scd.SCD216QService;
import com.acer.service.others.ServiceBase;
import com.nou.aut.AUTICFM;

@Controller 
@Scope("prototype")
@RequestMapping(SCD216Q.folderPath + "/*")
public class SCD216Q extends ControlPageInit {

	@Autowired
	SCD216QService service;
	private static final String sysCode = "scd";
	private static final String progCode = "scd216q";
	public static final String folderPath = "/"+sysCode+"/"+progCode;	
	
	@RequestMapping("_01v1")
	public ModelAndView _01v1(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*********************************************************************/
		Init(request, response);				
		/*********************************************************************/
		
		return ServiceBase.GetModelAndView(folderPath, progCode+"_01v1");
	}
	
	@RequestMapping("_01c2")
	public void _01c2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*********************************************************************/
		Init(request, response);		
		/*********************************************************************/
		
		int	logFlag	=	-1;
		try
		{
			/** 起始 Log */
			logger		=	new MyLogger(request.getRequestURI().toString() + "(" + control_type + ")");
			logger.iniUserInfo(Log4jInit.getIP(request));

			/** 起始 DBManager Container */
			dbManager	=	new DBManager(logger);

			/** 查詢 Grid 資料 */
			if (AUTICFM.securityCheck (session, "QRY") && control_type.equals("QUERY_MODE"))
			{
				logFlag	=	4;		
				service.doQuery(out, dbManager, requestMap, session);
			}
		
			/** 取得學生姓名 */
			else if (control_type.equals("GET_STNO_NAME_MODE"))
			{
				logFlag	=	-2;
				service.getStnoName(out, dbManager, requestMap, session);
			}
			
		}
		catch(Exception ex)
		{
			logErrMessage(ex, logger);
			throw ex;
		}
		finally
		{
			try
			{
				/** 異動註記 */
				if (logFlag != -2)
				{
					com.nou.aut.AUTLOG	autlog	=	new com.nou.aut.AUTLOG(dbManager);
					autlog.setUSER_ID((String)session.getAttribute("USER_ID"));
					autlog.setPROG_CODE("scd216q");
					autlog.setUPD_MK(String.valueOf(logFlag));
					autlog.setIP_ADDR(Log4jInit.getIP(request));
					autlog.execute();
				}
				
				dbManager.close();
			}
			catch(Exception ex)
			{
				logErrMessage(ex, logger);
				throw ex;
			}

			if (logger != null)
			{
				long	endTime	=	System.currentTimeMillis();
				logger.append("全部執行時間：" + String.valueOf(endTime - startTime) + " ms");
				logger.log();
			}

			requestMap	=	null;
			logger		=	null;
		}
	}
	
	
	@RequestMapping("_01p1")
	public ModelAndView _01p1(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		/*********************************************************************/
		Init(request, response);	
		SetProgCode(progCode);
		/*********************************************************************/
		
		return ServiceBase.GetModelAndView("/include/pages", "report");
	}	
	
}

