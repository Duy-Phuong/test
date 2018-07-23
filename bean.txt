/**
 * @(#)T001_MstUserBean.java 2018/07/23
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23
 * Version 1.00.
 */
package fjs.cs.bean;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import fjs.cs.dao.T001_DAO;
import fjs.cs.db.DBConnection;

/**
 * Class T001_MstUserBean user bean
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T001_MstUserBean extends ActionForm {
	
	//user ID info 
	private String txtUserID = "";
	
	//password info
	private String txtPassword = "";
	
	//user name info 
	private String userName = "";
	
	//PSN_CD info
	private String PSN_CD = "";
	
	/**
	 * validate userID and password
	 * 
	 * @param HttpServletRequest mapping 
	 * @param HttpServletRequest request 
	 * @return ActionErrors errors
	 */
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if (txtUserID.trim().length() > 0 && txtPassword.trim().length() > 0) {
			// create connection
			Connection conn = DBConnection.createConnection();
			// check user exits
			int temp = T001_DAO.checkMstUser(conn, txtUserID, txtPassword);
			if (temp != 1) {
				//display error
				errors.add("txtUserID", new ActionMessage("error.userID.required"));
			}
		}
		return errors;
	}
	
	/**
	 * reset userID and password
	 * 
	 * @param HttpServletRequest mapping 
	 * @param HttpServletRequest request 
	 * @return ActionErrors errors
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		txtPassword = "";
		txtPassword = "";
	}



}
/**
 * @(#)T002_SearchBean.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.bean;

import org.apache.struts.action.ActionForm;

/**
 * Class T002_SearchBean search bean
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T002_SearchBean extends ActionForm{

	//customer name
	private String txtCustomerName = "";
	
	//sex info
	private String cboSex = "";
	
	//birthday from info
	private String txtBirthdayFrom = "";
	
	//birthday to info
	private String txtBirthdayTo = "";
	
	//mode info
	private String mode = "";
	
	//message info
	private String message = "";
	
	//page id info
	private String pageid = "1";
	
	// max page id
	private String maxpageid = "1";
	
	//list selected customer to delete
	private String selectedCustomer = "";
	
	/**
	 * constructor
	 * 
	 * @param txtCustomerName
	 * @param cboSex
	 * @param txtBirthdayFrom
	 * @param txtBirthdayTo
	 * @param mode
	 * @param message
	 * @param pageid
	 * @param maxpageid
	 */
	public T002_SearchBean(String txtCustomerName, String cboSex,
							String txtBirthdayFrom, String txtBirthdayTo, String mode,
							String message, String pageid, String maxpageid) {
		super();
		this.txtCustomerName = txtCustomerName;
		this.cboSex = cboSex;
		this.txtBirthdayFrom = txtBirthdayFrom;
		this.txtBirthdayTo = txtBirthdayTo;
		this.mode = mode;
		this.message = message;
		this.pageid = pageid;
		this.maxpageid = maxpageid;
	}
	
	
}
/**
 * @(#)T003_EditBean.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.bean;

import org.apache.struts.action.ActionForm;

/**
 * Class T003_EditBean edit bean
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T003_EditBean extends ActionForm{
	
	//customer name info
	private String txtCustomerName = "";
	
	//sex info
	private String cboSex = "";
	
	//birthday info
	private String txtBirthday = "";
	
	//mode info
	private String mode = "";
	
	//customer id info
	private String customerID = "0";
	
	//email info
	private String txtEmail = "";
	
	//address info
	private String txtAddress = "";
	
	//status info
	private String status = "";
	
	//PSN_CD customer
	private String PSN_CD = "";

	/**
	 * constructor
	 */
	public T003_EditBean(){
		
	}
	
}
/**
 * @(#)T002_DAO.java 2018/07/04.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/10.
 * Version 1.00.
 */
package fjs.cs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import fjs.cs.dto.MSTCUSTOMER;

/**
 * T002_DAO
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T002_DAO {
				
	/**
     * count MSTCUSTOMER user
     * 
     * @param conn: Connection info
     * @return connection
     */
	public static int countMSTCUSTOMER(Connection conn) {
		
		int result = 0;
		PreparedStatement pstm = null;
		//query sentences
		String sql ="SELECT count(*) FROM MSTCUSTOMER WHERE DELETE_YMD IS NULL";
		
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception ex) {
			//print error
			String error = ex.getMessage();
			System.out.println("Error: " + error);
		}
		return result;
	}
	
	/**
     * search list MSTCUSTOMER user with condition
     * 
     * @param conn: Connection info
     * @param start: start position
     * @param count: number  limit to display
     * @param CUSTOMER_NAME: CUSTOMER_NAME info
     * @param SEX: SEX info 
     * @param BIRTHDAYFROM: BIRTHDAYFROM info
     * @param BIRTHDAYTO: BIRTHDAYFRTO info
     * @param  ADRESS: ADDRESS info
     * @return listpAGE MSTCUSTOMER
     */
	public static List<MSTCUSTOMER> searchMSTCUSTOMERPage(int start, int count, Connection conn, String CUSTOMER_NAME, String SEX, String BIRTHDAYFROM, String BIRTHDAYTO, String ADDRESS) {
		List<MSTCUSTOMER> list = null;
		//list MSTCUSTOMER user with condition
		list = new ArrayList<MSTCUSTOMER>();
		List<MSTCUSTOMER> listPage = null;
		//list MSTCUSTOMER user with no condition
		listPage = new ArrayList<MSTCUSTOMER>();
		PreparedStatement pstm = null;
		//end position
		int end = start + count;
		int i = 0;

		// query sentences
		String sql = "SELECT * FROM MSTCUSTOMER WHERE DELETE_YMD IS NULL";
		if (CUSTOMER_NAME.equalsIgnoreCase("") == false) {
			sql = sql + " AND CUSTOMER_NAME LIKE '%" + CUSTOMER_NAME + "%'";
		}
		if (SEX.equalsIgnoreCase("") == false) {
			sql = sql + " AND SEX = '" + SEX + "'";
		}
		if (BIRTHDAYFROM.equalsIgnoreCase("") == false) {
			sql = sql + " AND BIRTHDAY >= '" + BIRTHDAYFROM + "'";
		}
		if (BIRTHDAYTO.equalsIgnoreCase("") == false) {
			sql = sql + " AND BIRTHDAY <= '" + BIRTHDAYTO + "'";
		}
		
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()) {
					MSTCUSTOMER user = new MSTCUSTOMER();
					user.setCUSTOMER_ID(rs.getInt(1));
					user.setCUSTOMER_NAME(rs.getString(2));
					user.setSEX(rs.getString(3));
					user.setBIRTHDAY(rs.getString(4));
					user.setEMAIL(rs.getString(5));
					user.setADDRESS(rs.getString(6));
					list.add(user);
			}
			for(MSTCUSTOMER l: list){
				if (i >= start && i < end) {
					listPage.add(l);
				}
				i++;	
			}
		} catch (Exception ex) {
			//print error
			String error = ex.getMessage();
			System.out.println("Error: " + error);
		}
		return listPage;
	}
	
	/**
     * count MSTCUSTOMER user with condition
     * 
     * @param conn: Connection info
     * @param start: start position
     * @param count: number  limit to display
     * @param CUSTOMER_NAME: CUSTOMER_NAME info
     * @param SEX: SEX info 
     * @param BIRTHDAYFROM: BIRTHDAYFROM info
     * @param BIRTHDAYTO: BIRTHDAYFRTO info
     * @param  ADRESS: ADDRESS info
     * @return result MSTCUSTOMER
     */
	public static int countMSTCUSTOMERPage(Connection conn, String CUSTOMER_NAME, String SEX, String BIRTHDAYFROM, String BIRTHDAYTO, String ADDRESS) {
		//number MSTCUSTOMER
		int result = 0;
		PreparedStatement pstm = null;
		// query sentences
		String sql = "SELECT count(*) FROM MSTCUSTOMER WHERE DELETE_YMD IS NULL";
		if (CUSTOMER_NAME.equalsIgnoreCase("") == false) {
			sql = sql + " AND CUSTOMER_NAME LIKE '%" + CUSTOMER_NAME + "%'";
		}
		if (SEX.equalsIgnoreCase("") == false) {
			sql = sql + " AND SEX = '" + SEX + "'";
		}
		if (BIRTHDAYFROM.equalsIgnoreCase("") == false) {
			sql = sql + " AND BIRTHDAY >= '" + BIRTHDAYFROM + "'";
		}
		if (BIRTHDAYTO.equalsIgnoreCase("") == false) {
			sql = sql + " AND BIRTHDAY <= '" + BIRTHDAYTO + "'";
		}
		try {
			pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception ex) {
			//print error
			String error = ex.getMessage();
			System.out.println("Error: " + error);
		}
		return result;
	}
	
	/**
     * delete MSTCUSTOMER  
     * 
     * @param dbURL: database's url
     * @param id: MSTCUSTOMER INFO 
     * @param date: DELETE_YMD info
     * @return result
     */
	public static int deleteMSTCUSTOMER(Connection conn, String id) {
		//set default value to return if error
		int result = -1;
		PreparedStatement pstm = null;
		// query sentences
		String sql = "UPDATE MSTCUSTOMER SET DELETE_YMD = getdate() WHERE DELETE_YMD IS NULL AND CUSTOMER_ID = " + id ;
		
		try {
			pstm = conn.prepareStatement(sql);
			result = pstm.executeUpdate();
			
		} catch (Exception ex) {
			//print error
			String error = ex.getMessage();
			System.out.println("Error: " + error);
		}
		return result;
	}
}
