<%
				
				MSTCUSTOMER l = (MSTCUSTOMER) request.getAttribute("customer");
				int id = l.getCUSTOMER_ID(); 
			%>
			Customer ID:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <%=id != 0 ? id : ""%>
			
			Customer ID:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<logic:present name="EditBean" property="customerID" value="0">
					
			</logic:present>
			<logic:notPresent name="EditBean" property="customerID" value="0">
					${EditBean.customerID}
			</logic:notPresent>
			
************************************* BTMS QÁMs 4bug 120test case 1KLOC stepcode
create database customersystem
go
use customersystem
go
--1.create table MSTUSER
Create table MSTUSER(
	PSN_CD numeric(4) not null IDENTITY(1,1),
	USERID varchar(8),
	PASSWORD varchar(8),
	USERNAME varchar(40),
	DELETE_YMD datetime default null,
	INSERT_YMD datetime default getdate(),
	INSERT_PSN_CD numeric(5) default 0,
	UPDATE_YMD datetime default null,
	UPDATE_PSN_CD numeric(5) default 0,
	constraint PK_MSTUSER primary key (PSN_CD)
);
go
--2.create table MSTCUSTOMER
Create table MSTCUSTOMER(
	CUSTOMER_ID numeric(8) not null IDENTITY(11010001,1),
	CUSTOMER_NAME varchar(40),
	SEX varchar(1),
	BIRTHDAY varchar(10),
	EMAIL varchar(40),
	ADDRESS varchar(256),
	DELETE_YMD datetime default null,
	INSERT_YMD datetime default getdate(),
	INSERT_PSN_CD numeric(5) default 0,
	UPDATE_YMD datetime default null,
	UPDATE_PSN_CD numeric(5) default 0,
	constraint PK_MSTCUSTOMER primary key (CUSTOMER_ID)
);
--drop table MSTCUSTOMER
go
Insert into MSTUSER values (1, '1111', '1111', 'Tran Duy Phuong',  null, getdate(), 0, null, 0);


Insert into MSTCUSTOMER(CUSTOMER_NAME, SEX, BIRTHDAY, EMAIL, ADDRESS, DELETE_YMD, INSERT_YMD, INSERT_PSN_CD, UPDATE_YMD, UPDATE_PSN_CD) values ('Nguyen Van A1', '0', '1982/01/01','abc@gmail.com', '123 No Trang Long',  null, getdate(), 0, null, 0);


**************************************
				
//				String currentDate = "2018/01/01";
//				
//				String pattern = "yyyy/MM/dd";
//				SimpleDateFormat format = new SimpleDateFormat(pattern);
//				
//					// formatting
//					// System.out.println(format.format(new Date()));
//					currentDate = format.format(new Date());
**************************************
/**
 * @(#)DBConnection.java 2018/07/04.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/10.
 * Version 1.00.
 */
package fjs.cs.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Class DBConnection create connection 
 * 
 * @author phuong-td
 * @version 1.0
 */
public class DBConnection {

	//user name info 
	private static String USER_NAME = "sa";
	//password info 
	private static String PASSWORD = "Abc12345";
	//host name  info
	static String hostName = "localhost";
	//SQL instance name info 
	static String sqlInstanceName = "PHUONG-TD-W7";
	//database name info 
	static String database = "customersystem";
	//URL info 
	private static String DB_URL = "jdbc:sqlserver://" + hostName + ":1433" 
               + ";instance=" + sqlInstanceName + ";databaseName=" + database;

	 /**
     * create connection 
     * 
     * @param dbURL: database's url
     * @param userName: username is used to login
     * @param password: password is used to login
     * @return connection
     */
	public static Connection createConnection() {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			//if(conn != null)
			//	System.out.println("connect successfully!");
		} catch (Exception ex) {
			System.out.println("connect failure!");
			ex.printStackTrace();
		}

		return conn;
	}
}

 /******************************************/
 /**
 * @(#)T001_LoginAction.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.action;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fjs.cs.bean.T001_MstUserBean;
import fjs.cs.dao.T001_DAO;
import fjs.cs.db.DBConnection;
import fjs.cs.dto.MSTUSER;

/**
 * Class T001_LoginAction execute login action 
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T001_LoginAction extends Action{
	
	/**
	 * Login Action: if login successfully go to search page
	 * 
	 * @param HttpServletRequest mapping 
	 * @param HttpServletResponse form 
	 * @param HttpServletRequest request 
	 * @param HttpServletResponse response 
	 * @return ActionForward
	 */
	 @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 		T001_MstUserBean userBean = (T001_MstUserBean) form;
		 		
		 		//user id info
				String userid = userBean.getTxtUserID();
				
				//password info
				String password = userBean.getTxtPassword();
		
				// compare userID and password in database
				if (userid.trim().length() > 0 && password.trim().length() > 0) {
					// create connection
					Connection conn = DBConnection.createConnection();
					// check user exits
					int temp = T001_DAO.checkMstUser(conn, userid, password);
					if (temp == 1) {
						MSTUSER user = null;
						// get user info
						user = T001_DAO.getMSTUSER(conn, userid, password);
						if (user != null) {
							//save user name and PSN_CD into userBean
							userBean.setUserName(user.getUSERNAME());
							userBean.setPSN_CD(String.valueOf(user.getPSN_CD()));
						}
						return (mapping.findForward("success"));	
					}
				}
				return (mapping.findForward("bad-password"));	
	}
}
*****************************
/**
 * @(#)T001_PreAction.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fjs.cs.bean.T001_MstUserBean;

/**
 * Class T001_PreAction load T001.jsp
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T001_PreAction extends Action{
	
	/**
	 * Login Action: display Login page
	 * 
	 * @param HttpServletRequest mapping 
	 * @param HttpServletResponse form 
	 * @param HttpServletRequest request 
	 * @param HttpServletResponse response 
	 * @return ActionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//reset user name and password in bean
		T001_MstUserBean user = (T001_MstUserBean) form;
		user.setTxtUserID("");
		user.setTxtPassword("");
		return mapping.findForward("preaction");
	}
}
/**
 * @(#)T002_PreAction.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fjs.cs.dto.MSTCUSTOMER;

/**
 * Class T002_PreAction load T002.jsp page
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T002_PreAction extends Action {
	
	/**
	 * Search Action: display search page
	 * 
	 * @param HttpServletRequest mapping 
	 * @param HttpServletResponse form 
	 * @param HttpServletRequest request 
	 * @param HttpServletResponse response 
	 * @return ActionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//get list customer		
		List<MSTCUSTOMER> list = null;
		list = (List<MSTCUSTOMER>) request.getAttribute("listCustomer");
		
		//set max page id
		request.setAttribute("maxpageid", request.getAttribute("maxpageid"));
		//set current page
		request.setAttribute("stt", request.getAttribute("stt"));
		//set list customer 
		request.setAttribute("listCustomer", list);
		
		return mapping.findForward("success");
	}
}
/**
 * @(#)T002_SearchAction.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.action;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.*;

import fjs.cs.bean.T002_SearchBean;
import fjs.cs.dao.T002_DAO;
import fjs.cs.db.DBConnection;
import fjs.cs.dto.MSTCUSTOMER;

/**
 * Class T002_SearchAction execute search action 
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T002_SearchAction extends Action {

	/**
	 * Login Action: search and delete customer 
	 * 
	 * @param HttpServletRequest mapping 
	 * @param HttpServletResponse form 
	 * @param HttpServletRequest request 
	 * @param HttpServletResponse response 
	 * @return ActionForward
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		  T002_SearchBean userForm = (T002_SearchBean) form;
		  
		  //create connection
		  Connection conn = DBConnection.createConnection();
		
		  
		  //list selected customer to delete
		  String selectedCustomer = userForm.getSelectedCustomer();
		  
		  //if mode is delete 
			if ("delete".equalsIgnoreCase( userForm.getMode()) == true) {
				//list is not empty
				if ("".equalsIgnoreCase(selectedCustomer) == false) {
					//split list by comma
					String[] arrayCustomer = selectedCustomer.split(",");
					for (String customer : arrayCustomer) {
						// delete MSTCUSTOMER
						T002_DAO.deleteMSTCUSTOMER(conn, customer);
					}
				}
			}
		// get information of CUSTOMER to search
		String CUSTOMER_NAME = userForm.getTxtCustomerName();
		String SEX = userForm.getCboSex();
		String BIRTHDAYFROM = userForm.getTxtBirthdayFrom();
		String BIRTHDAYTO = userForm.getTxtBirthdayTo();
		String ADDRESS = "";
		
		// get session
		HttpSession session = request.getSession(true);

		// if you click switch page button get old information 
		if ("search".equalsIgnoreCase(userForm.getMode()) == false
				&& "delete".equalsIgnoreCase(userForm.getMode()) == false) {
			// get information of CUSTOMER to search
			if(session.getAttribute("CUSTOMER_NAME") != null) {
				CUSTOMER_NAME = (String) session.getAttribute("CUSTOMER_NAME");
			}
			if(session.getAttribute("SEX") != null) {
				SEX = (String) session.getAttribute("SEX");
			}
			if(session.getAttribute("BIRTHDAYFROM") != null) {
				BIRTHDAYFROM = (String) session.getAttribute("BIRTHDAYFROM");
			}
			if(session.getAttribute("BIRTHDAYTO") != null) {
				BIRTHDAYTO = (String) session.getAttribute("BIRTHDAYTO");
			}
			
			// update bean if you don't click search
			userForm.setTxtCustomerName(CUSTOMER_NAME);
			userForm.setCboSex(SEX);
			userForm.setTxtBirthdayFrom(BIRTHDAYFROM);
			userForm.setTxtBirthdayTo(BIRTHDAYTO);
		}
		// save session
		session.setAttribute("CUSTOMER_NAME", CUSTOMER_NAME);
		session.setAttribute("BIRTHDAYFROM", BIRTHDAYFROM);
		session.setAttribute("BIRTHDAYTO", BIRTHDAYTO);
		session.setAttribute("SEX", SEX);

		// list MSTCUSTOMER
		List<MSTCUSTOMER> list = null;

		String pageIdString = userForm.getPageid();
		
		// page number
		int pageid = Integer.parseInt(pageIdString);

		// max page number
		int count = 15;

		// load into list MTSCUSTOMER in the first page
		if (pageid == 1) {
			list = T002_DAO.searchMSTCUSTOMERPage(0, count, conn,
					CUSTOMER_NAME, SEX, BIRTHDAYFROM, BIRTHDAYTO, ADDRESS);
		} else {
			// start position
			int start = (Integer.parseInt(pageIdString) - 1) * count;
			list = T002_DAO.searchMSTCUSTOMERPage(start, count, conn,
					CUSTOMER_NAME, SEX, BIRTHDAYFROM, BIRTHDAYTO, ADDRESS);
		}

		// number of CUSTOMER
		int sumRow = T002_DAO.countMSTCUSTOMERPage(conn, CUSTOMER_NAME, SEX,
				BIRTHDAYFROM, BIRTHDAYTO, ADDRESS);

		// max page id
		int maxpageid = 1;
		if (sumRow % count == 0) {
			maxpageid = (sumRow / count);
		} else {
			maxpageid = (sumRow / count) + 1;
		}

		// max page id
		request.setAttribute("maxpageid", maxpageid);
		// current page
		request.setAttribute("stt", Integer.parseInt(pageIdString));

		//disable button
		T002_SearchAction.disableButton(request, response, list, pageIdString,
				sumRow, maxpageid);

		// return mapping.findForward("success");
		return mapping.findForward("preaction");
	}
	
	/**
	 * disable button
	 * 
	 * @param HttpServletRequest   req
	 * @param HttpServletResponse  res
	 * @param List<MSTCUSTOMER> list
	 * @param String pageIdString
	 * @param int sumRow: number MSTCUSTOMER
	 * @param int maxpageid: max page id number
	 */
	public static void disableButton(HttpServletRequest req,
			HttpServletResponse res, List<MSTCUSTOMER> list,
			String pageIdString, int sumRow, int maxpageid) {
		// max page number
		int count = 15;
		// if list MSTCUSTOMER isn't null
		if (list != null) {
			req.setAttribute("listCustomer", list);
		}

		// check to enable or disable button, check = 0 is true
		int check = 0;

		// if list is empty and check is true we disable all button
		if (list.size() == 0 && check == 0) {
			
			req.setAttribute("disabled", "true");
			// disable Delete button
			req.setAttribute("deleteDisabled", "disabled");
			check = 1;
		}

		// if list is less than 15 and check is true we disable all button
		// if maxpageid is equal to 15 and check is true we disable all button
		if ((list.size() <= 15 || maxpageid == 0) && check == 0	&& sumRow < count) {
			
			req.setAttribute("disabled", "true");
			check = 1;
		}

		// if sumrow <= max page number
		if (sumRow <= count) {
			req.setAttribute("disabled", "true");
			check = 1;
		}

		// if currrent page is greater than max page number
		if ((Integer.parseInt(pageIdString) > (maxpageid - 1)) && check == 0) {
			
			req.setAttribute("maxpageDisabled", "true");
			check = 1;
		}
		// if currrent page is less than min page number
		if ((Integer.parseInt(pageIdString) <= 1) && check == 0) {
			
			req.setAttribute("minpageDisabled", "true");
			check = 1;
		}
	}
}
