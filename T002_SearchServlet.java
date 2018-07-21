/**
 * @(#)T002_SearchServlet.java 2018/07/04.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/10.
 * Version 1.00.
 */
package fjs.cs.action;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

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
 * SearchServlet(Search)
 * 
 * @author PHUONG-TD
 * @version 1.00
 * 
 */
public class T002_SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Init screen
	 * 
	 * @param HttpServletRequest req
	 * @param HttpServletResponse resp
	 * @return RequestDispatcher
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			System.out.println("get");
	}

	/**
	 * Search screen
	 * 
	 * @param HttpServletRequest   req
	 * @param HttpServletResponse  resp
	 * @return RequestDispatcher
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//create connectiom
		Connection conn = DBConnection.createConnection();
		String type = "";
		if(req.getParameter("type") != null) {
			type = (String)req.getParameter("type");
		}
		//delete MSTCUSTOMER
		if ("delete".equalsIgnoreCase(type) == true) {

			// get delete id
			String id = (String) req.getParameter("idDelete");
			// get delete Date
			String date = (String) req.getParameter("date");
			// delete MSTCUSTOMER
			DataConnect.deleteMSTCUSTOMER(conn, id, date);

		}
		
		RequestDispatcher myRD = null;
			
		// get infomation of CUSTOMER to search
		String CUSTOMER_NAME = req.getParameter("txtCustomerName");
		String SEX = req.getParameter("cboSex");
		String BIRTHDAYFROM = req.getParameter("txtBirthdayFrom");
		String BIRTHDAYTO = req.getParameter("txtBirthdayTo");
		String ADDRESS = "";

		// get session
		HttpSession session = req.getSession(true);

		// if you click switch page button
		if (req.getParameter("status") != null
				&& "search".equalsIgnoreCase(req.getParameter("status")) == false) {
			//System.out.println("search");
			
			// get information of CUSTOMER to search
			CUSTOMER_NAME = (String) session.getAttribute("CUSTOMER_NAME");
			SEX = (String) session.getAttribute("SEX");
			BIRTHDAYFROM = (String) session.getAttribute("BIRTHDAYFROM");
			BIRTHDAYTO = (String) session.getAttribute("BIRTHDAYTO");
			ADDRESS = "";
		}
		
		//sav session
		session.setAttribute("CUSTOMER_NAME", CUSTOMER_NAME);
		session.setAttribute("BIRTHDAYFROM", BIRTHDAYFROM);
		session.setAttribute("BIRTHDAYTO", BIRTHDAYTO);
		session.setAttribute("SEX", SEX);
		
		String sessionName = (String) session.getAttribute("sessionname");
		session.setAttribute("sessionname", sessionName);

		// list MSTCUSTOMER
		List<MSTCUSTOMER> list = null;
		
		String pageIdString = "1";
		//get page id
		if(req.getParameter("pageid") != null) {
			pageIdString = req.getParameter("pageid");
		}
		
		//page number
		int pageid = Integer.parseInt(pageIdString);
		
		//max page number
		int count = 15;
		
		//load into list MTSCUSTOMER in the first page
		if (pageid == 1) {
					list = DataConnect.searchMSTCUSTOMERPage(0, count, conn,
					CUSTOMER_NAME, SEX, BIRTHDAYFROM, BIRTHDAYTO, ADDRESS);
		} else {
			//start position
			int start = (Integer.parseInt(pageIdString) - 1) * count;
			list = DataConnect.searchMSTCUSTOMERPage(start, count, conn, CUSTOMER_NAME, SEX, BIRTHDAYFROM, BIRTHDAYTO, ADDRESS);

		}

		//number of CUSTOMER
		int sumRow = DataConnect.countMSTCUSTOMERPage(conn, CUSTOMER_NAME, SEX,	BIRTHDAYFROM, BIRTHDAYTO, ADDRESS);
		
		// max page id
		int maxpageid = 1;
		if (sumRow % count == 0) {
			maxpageid = (sumRow / count);
		} else {
			maxpageid = (sumRow / count) + 1;
		}
		
		//max page id
		req.setAttribute("maxpageid", maxpageid);
		//current page
		req.setAttribute("stt", Integer.parseInt(pageIdString));
		
		T002_SearchServlet.disableButton(req, resp, list, pageIdString, sumRow, maxpageid);
		
		// display search screen
		myRD = req.getRequestDispatcher(Constants.T002_SEARCH);
		myRD.forward(req, resp);
		
	}
	
	/**
	 * Init the first page when you login success
	 * 
	 * @param HttpServletRequest   req
	 * @param HttpServletResponse  res
	 * @param Connection conn
	 * @return RequestDispatcher
	 */
	public static final  void Init(HttpServletRequest req, HttpServletResponse res, Connection conn) {
		//list MSTCUSTOMER
		List<MSTCUSTOMER> list = null;
		//set default this is the first page
		String pageIdString = "1";
		int pageid = Integer.parseInt(pageIdString);
		//max page number
		int count = 15;
		
		//if this is the first page
		if (pageid == 1) {
			list = DataConnect.displayMSTCUSTOMER(0, count, conn, "", "", "", "", "");
		}
		
		//get number of MSTCUSTOMER
		int sumRow = DataConnect.countMSTCUSTOMER(conn);
		
		//max page id
		int maxpageid = 1;
		if (sumRow % count == 0) {
			maxpageid = (sumRow / count);
		} else {
			maxpageid = (sumRow / count) + 1;
		}
		req.setAttribute("maxpageid", maxpageid);
		req.setAttribute("stt", Integer.parseInt(pageIdString));
		
		T002_SearchServlet.disableButton(req, res, list, pageIdString, sumRow, maxpageid);

	}
	
	/**
	 * Search screen
	 * 
	 * @param HttpServletRequest   req
	 * @param HttpServletResponse  res
	 * @param List<MSTCUSTOMER> list
	 * @param int sumRow: number MSTCUSTOMER
	 * @param int maxpageid: max page id number
	 * @return RequestDispatcher
	 */
	public static void disableButton(HttpServletRequest req, HttpServletResponse res, List<MSTCUSTOMER> list, String pageIdString, int sumRow, int maxpageid){
		//max page number
				int count = 15;
		//if list MSTCUSTOMER isn't null
				if (list != null) {
					req.setAttribute("listCustomer", list);
				}
				
				// check to enable or disable button, check = 0 is true
				int check = 0;
				
				//if list is empty and check is true we disable all button
				if (list.size() == 0 && check == 0) {
					req.setAttribute("disabled", "disabled");
					//disable Delete button
					req.setAttribute("deleteDisabled", "disabled");
					check = 1;
				}
				
				//if list is less than 15 and check is true we disable all button
				//if maxpageid is equal to 15 and check is true we disable all button
				if ((list.size() <= 15 || maxpageid == 0) && check == 0
						&& sumRow < count) {

					req.setAttribute("disabled", "disabled");
					check = 1;
				}
				
				//if sumrow <= max page number
				if (sumRow <= count) {
					req.setAttribute("disabled", "disabled");
					check = 1;
				}
				
				// if currrent page is greater than max page number
				if ((Integer.parseInt(pageIdString) > (maxpageid - 1)) && check == 0) {

					req.setAttribute("maxpageDisabled", "disabled");
					check = 1;
				}
				// if currrent page is less than min page number
				if ((Integer.parseInt(pageIdString) <= 1) && check == 0) {
					req.setAttribute("minpageDisabled", "disabled");
					check = 1;
				}
	}
	
}
