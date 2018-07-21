/**
 * @(#)T003_EditServlet.java 2018/07/10
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/10
 * Version 1.00.
 */
package fjs.cs.action;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fis.cs.dao.DBConnection;
import fis.cs.dao.DataConnect;
import fjs.cs.common.Constants;
import fjs.cs.dto.MSTCUSTOMER;

/**
 * EditServlet(EDIT)
 * 
 * @author PHUONG-TD
 * @version 1.00
 * 
 */
public class T003_EditServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * Init screen Edit
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @return RequestDispatcher
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		RequestDispatcher myRD = null;
		
		myRD = req.getRequestDispatcher(Constants.T003_EDIT);
		myRD.forward(req, resp);
	}

	/**
	 * ADD OR EDIT MSTCUSTOMER
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @return RequestDispatcher
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// create connection
		Connection conn = DBConnection.createConnection();
		//get CUSTOMER_ID information 
		String CUSTOMER_ID = req.getParameter("CUSTOMER_ID");
		//get CUSTOMER_NAME information 
		String CUSTOMER_NAME = req.getParameter("txtCustomerName");
		//get CUSTOMER_ID information 
		String SEX = req.getParameter("cboSex");
		//get BIRTHDAY information 
		String BIRTHDAY = req.getParameter("txtBirthday");
		//get ADDRESS information 
		String ADDRESS = req.getParameter("txtAddress");
		//get EMAIL information 
		String EMAIL = req.getParameter("txtEmail");
		
		//get PSN_CD in session
		HttpSession session = req.getSession(true);
		Integer PSN_CD = (Integer) session.getAttribute("PSN_CD");
		//get mode edit or add new
		String modeName =  (String)req.getParameter("mode");
		//save mode
		session.setAttribute("mode", modeName);
		
		//save mode into session
		String mode =  (String) session.getAttribute("mode");
		
		req.setAttribute("mode", "addNew");
		//event click
		String click =  (String)req.getParameter("click");
		int clicked = Integer.parseInt(click);
		
		//get id of MSTCUSTOMER
		String id =  (String)req.getParameter("id");
			
		//get status edit or addNew
		String status =  (String)req.getParameter("status");
		
		//mode is add new, you add a MSTCUSTOMER
		if("addNew".equalsIgnoreCase(mode) == true && "addNew".equalsIgnoreCase(status) == true) {
			
			//add MSTCUSTOMER
			if(clicked == 1) {
				 DataConnect.addMSTCUSTOMER(conn, CUSTOMER_NAME, SEX, BIRTHDAY, EMAIL, ADDRESS, PSN_CD.toString());
			}
			
			//reload page
			req.setAttribute("userName", "0");
			session.setAttribute("id", "0");
			
			MSTCUSTOMER customer = DataConnect.findMSTCUSTOMER(conn, "0");
			if(customer != null){
				req.setAttribute("customer", customer);
			}

		}else if("edit".equalsIgnoreCase(mode) == true && "edit".equalsIgnoreCase(status) == true ) {	//mode is edit, you edit a MSTCUSTOMER
			
			if(clicked == 1) {
				DataConnect.editMSTCUSTOMER(conn, CUSTOMER_NAME, SEX, BIRTHDAY, EMAIL, ADDRESS, PSN_CD.toString(),CUSTOMER_ID);
			}
			//set CUSTOMER ID to edit
			CUSTOMER_ID = id;
			
			//display MSTCUSTOMER after you have edited MSTCUSTOMER
			req.setAttribute("userName", CUSTOMER_ID);
					
			//find MSTCUSTOMER
			MSTCUSTOMER customer = DataConnect.findMSTCUSTOMER(conn, CUSTOMER_ID);
			if(customer != null){
				req.setAttribute("customer", customer);
			}
		}

		RequestDispatcher myRD = null;
		myRD = req.getRequestDispatcher(Constants.T003_EDIT);
		myRD.forward(req, resp);
	}
}

