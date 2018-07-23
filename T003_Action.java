/**
 * @(#)T003_EditAction.java 2018/07/23.
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

import fjs.cs.dao.T003_DAO;
import fjs.cs.db.DBConnection;
import fjs.cs.bean.T003_EditBean;
import fjs.cs.dto.MSTCUSTOMER;

/**
 * Class T003_EditAction execute edit action 
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T003_EditAction extends Action{
	
	/**
	 * Edit Action: edit or add new customer
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
			T003_EditBean customerForm = (T003_EditBean) form;
			
			//set status is edit
			request.setAttribute("status", "edit");
			
			//create connection
			Connection conn = DBConnection.createConnection();
			
			if ("edit".equalsIgnoreCase(customerForm.getStatus()) == true) {
				
			// edit customer
			T003_DAO.editMSTCUSTOMER(conn, customerForm.getTxtCustomerName(),
									customerForm.getCboSex(), customerForm.getTxtBirthday(),
									customerForm.getTxtEmail(), customerForm.getTxtAddress(),
									customerForm.getPSN_CD(), customerForm.getCustomerID());

			// find MSTCUSTOMER to update
				MSTCUSTOMER customer = T003_DAO.findMSTCUSTOMER(conn, customerForm.getCustomerID());
				if (customer != null) {
					request.setAttribute("customer", customer);
					
					//update bean to display in form
					customerForm.setCustomerID(String.valueOf(customer.getCUSTOMER_ID()));
					customerForm.setCboSex(customer.getSEX());
					customerForm.setTxtBirthday(customer.getBIRTHDAY());
					customerForm.setTxtEmail(customer.getEMAIL());
					customerForm.setTxtAddress(customer.getADDRESS());
					customerForm.setTxtCustomerName(customer.getCUSTOMER_NAME());
	
				}
			} else if ("add".equalsIgnoreCase(customerForm.getStatus()) == true) {
				//add customer 
				 T003_DAO.addMSTCUSTOMER(conn,customerForm.getTxtCustomerName(),
										 customerForm.getCboSex(), customerForm.getTxtBirthday(),
										 customerForm.getTxtEmail(), customerForm.getTxtAddress(),
										 customerForm.getPSN_CD());
				// clear input in form
				MSTCUSTOMER customer = new MSTCUSTOMER();
				if (customer != null) {
					request.setAttribute("customer", customer);
				}
			}			
		return mapping.findForward("preaction");
	}
}
/**
 * @(#)T003_PreAction.java 2018/07/23.
 *
 * Copyright(C) 2016 by FUJINET CO., LTD.
 *
 * Last_Update 2018/07/23.
 * Version 1.00.
 */
package fjs.cs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fjs.cs.bean.T003_EditBean;
import fjs.cs.dto.MSTCUSTOMER;

/**
 * Class T003_PreAction display T003.jsp
 * 
 * @author phuong-td
 * @version 1.0
 */
public class T003_PreAction extends Action{
	
	/**
	 * Edit Action: display edit page
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
		T003_EditBean customerForm = (T003_EditBean) form;
		
		//if click add new in T002.jsp or when you click save to add successfully
		if("Add new".equalsIgnoreCase(customerForm.getMode()) == true || "0".equalsIgnoreCase(customerForm.getCustomerID()) == true) {
			
			// get session
			HttpSession session = request.getSession(true);
			//customer name
			String CUSTOMER_NAME = "";
			//sex
			String SEX = "";
			//birthday from
			String BIRTHDAYFROM = "";
			//birthday to
			String BIRTHDAYTO = "";
			// birthday
			String BIRTHDAY = "";
			// get information search of CUSTOMER to display in edit screen
			if (session.getAttribute("CUSTOMER_NAME") != null) {
				CUSTOMER_NAME = (String) session.getAttribute("CUSTOMER_NAME");
			}
			if (session.getAttribute("SEX") != null) {
				SEX = (String) session.getAttribute("SEX");
			}
			if (session.getAttribute("BIRTHDAYFROM") != null) {
				BIRTHDAYFROM = (String) session.getAttribute("BIRTHDAYFROM");
			}
			if (session.getAttribute("BIRTHDAYTO") != null) {
				BIRTHDAYTO = (String) session.getAttribute("BIRTHDAYTO");
			}
			// if birthday from and birthday to is not null
			if (("".equalsIgnoreCase(BIRTHDAYFROM) == false && ""
					.equalsIgnoreCase(BIRTHDAYTO) == false)
					|| ("".equalsIgnoreCase(BIRTHDAYFROM) == false && ""
							.equalsIgnoreCase(BIRTHDAYTO) == true)) {
				BIRTHDAY = BIRTHDAYFROM;
			}
			// if birthday from and birthday to is not null
			if ("".equalsIgnoreCase(BIRTHDAYFROM) == true
					&& "".equalsIgnoreCase(BIRTHDAYTO) == false) {
				BIRTHDAY = BIRTHDAYTO;
			}

			// clear input in form bean if you have searched, screen will display input information
			customerForm.setCustomerID("0");
			customerForm.setCboSex(SEX);
			customerForm.setTxtCustomerName(CUSTOMER_NAME);
			customerForm.setTxtAddress("");
			customerForm.setTxtBirthday(BIRTHDAY);
			customerForm.setTxtEmail("");
			request.setAttribute("status", "add");

			// find MSTCUSTOMER to clear input in form
			MSTCUSTOMER customer = new MSTCUSTOMER();
			if (customer != null) {
				request.setAttribute("customer", customer);
			}
		} 
		return mapping.findForward("success");
	}
}
********************************
<?xml version="1.0" encoding="ISO-8859-1" ?>

<!DOCTYPE struts-config PUBLIC
          "-//Apache Software Foundation//DTD Struts Configuration 1.2//EN"
          "http://jakarta.apache.org/struts/dtds/struts-config_1_2.dtd">

<!--
     This is a blank Struts configuration file with an example
     welcome action/page and other commented sample elements.

     Tiles and the Struts Validator are configured using the factory defaults
     and are ready-to-use.

     NOTE: If you have a generator tool to create the corresponding Java classes
     for you, you could include the details in the "form-bean" declarations.
     Otherwise, you would only define the "form-bean" element itself, with the
     corresponding "name" and "type" attributes, as shown here.
-->


<struts-config>

<!-- ============================================ Data Source Configuration -->
<!--
<data-sources>
<data-source type="org.apache.commons.dbcp.BasicDataSource">
    <set-property
      property="driverClassName"
      value="org.postgresql.Driver" />
    <set-property
      property="url"
      value="jdbc:postgresql://localhost/mydatabase" />
    <set-property
      property="username"
      value="me" />
    <set-property
      property="password"
      value="test" />
    <set-property
      property="maxActive"
      value="10" />
    <set-property
      property="maxWait"
      value="5000" />
    <set-property
      property="defaultAutoCommit"
      value="false" />
    <set-property
      property="defaultReadOnly"
      value="false" />
    <set-property
      property="validationQuery"
      value="SELECT COUNT(*) FROM market" />
</data-source>
</data-sources>
-->

<!-- ================================================ Form Bean Definitions -->

    <form-beans>
    <!-- sample form bean descriptor for an ActionForm
        <form-bean
            name="inputForm"
            type="app.InputForm"/>
    end sample -->

    <!-- sample form bean descriptor for a DynaActionForm
        <form-bean
            name="logonForm"
            type="org.apache.struts.action.DynaActionForm">
            <form-property
                name="username"
                type="java.lang.String"/>
            <form-property
                name="password"
                type="java.lang.String"/>
       </form-bean>
    end sample -->
    	<form-bean name="userFormBean" type="coreservlets.UserFormBean"/>
   		<form-bean name="demobean" type="coreservlets.DemoBean"/>
   		<form-bean name="mstUserBean" type="fjs.cs.bean.T001_MstUserBean"/>
   		<form-bean name="SearchBean" type="fjs.cs.bean.T002_SearchBean"/>
   		<form-bean name="EditBean" type="fjs.cs.bean.T003_EditBean"/>

    	<!-- bai 4 -->
    	<form-bean name="contactFormBean"
					type="coreservlets.ContactFormBean"/>
  
    </form-beans>

<!-- ========================================= Global Exception Definitions -->

    <global-exceptions>
        <!-- sample exception handler
        <exception
            key="expired.password"
            type="app.ExpiredPasswordException"
            path="/changePassword.jsp"/>
        end sample -->
    </global-exceptions>


<!-- =========================================== Global Forward Definitions -->

    <global-forwards>
        <!-- Default forward to "Welcome" action -->
        <!-- Demonstrates using index.jsp to forward 
         <forward
            name="welcome"
            path="/Welcome.do"/>-->
       
		<forward
            name="welcome"
            path="/LoginT001.do"/>
    </global-forwards>


<!-- =========================================== Action Mapping Definitions -->

    <action-mappings>
            <!-- Default "Welcome" action -->
            <!-- Forwards to Welcome.jsp 
            <action
            path="/Welcome"
            forward="/pages/Welcome.jsp"/>-->
                 
             <action
            path="/register" type="action.HelloAction">
            <forward name="success" path="/pages/Welcome.jsp"></forward>
            </action>
            <!-- ban dau 
            	<action path="/register1" type="coreservlets.RegisterAction1"	>
					<forward name="success" path="/pages/confirm.jsp"/>
				</action>
            -->	
            
    
            <!-- sau khi cau hinh lan 1 dang sd bean -->
	<action path="/register1" type="coreservlets.RegisterAction1"
		name="userFormBean" scope="request">
		<forward name="bad-address" path="/pages/bad-address2.jsp" />
		<forward name="bad-password" path="/pages/bad-password2.jsp" />
		<forward name="success" path="/pages/confirm2.jsp" />
	</action>

	<!-- use message -->
	<action path="/register3" type="coreservlets.RegistrationAction"
		name="registrationBean" scope="request">
		<forward name="success" path="/pages/confirm3.jsp" />
	</action>

	<!-- start code -->
	<action path="/register2" type="coreservlets.RegisterAction2">
		<forward name="bad-address" path="/pages/bad-address.jsp" />
		<forward name="bad-password" path="/pages/bad-password.jsp" />
		<forward name="success" path="/pages/confirm.jsp" />
	</action>

	<!-- bai 4 -->

	<action path="/actions/signup1" type="coreservlets.SignupAction1"
		name="contactFormBean" scope="request">
		<forward name="missing-value" path="/pages/missing-value.jsp" />
		<forward name="success" path="/pages/confirmation.jsp" />
	</action>

	<!-- end code -->
	<action path="/T001" type="fjs.cs.action.T001_Login" name="mstUserBean"
		scope="request">

		<forward name="bad-password" path="/pages/bad-password.jsp" />
		<forward name="success" path="/pages/T002.jsp" />
	</action>
		
	<!-- 	code here  -->
	
	
	<!-- T001_PreAction 
	<action
            path="/LoginT001"
            forward="/pages/T001.jsp"/>-->
            
	<action name="mstUserBean"  path="/LoginT001" type="fjs.cs.action.T001_PreAction" scope="session">
		<forward name="preaction" path="/login.do" />
	</action>
	
	<!-- T001_LoginAcion -->
	<action input="/pages/T001.jsp" name="mstUserBean" scope="session"
	path="/login" type="fjs.cs.action.T001_LoginAction">
		<forward name="bad-password" path="/pages/T001.jsp" />
		<forward name="success" path="/search.do" />
	</action>
	
	<!-- T002_SearchAction -->
	<action input="/pages/T002.jsp" name="SearchBean" path="/search"
		scope="session" type="fjs.cs.action.T002_SearchAction">
		<forward name="preaction" path="/searchPage.do" />
	</action>
	
	<!-- T002_PreAction -->
	<action input="/pages/T002.jsp" path="/searchPage" 
		type="fjs.cs.action.T002_PreAction">
		<forward name="success" path="/pages/T002.jsp" />
	</action>
	
	<!-- T003_PreAction -->
	<action input="/pages/T002.jsp" path="/add" type="fjs.cs.action.T003_PreAction"
	name="EditBean" scope="request"
		>
		<forward name="success" path="/pages/T003.jsp" />
	</action>
	
	<!-- T003_EditAction -->
	 <action input="/pages/T003.jsp" path="/edit" type="fjs.cs.action.T003_EditAction"
	name="EditBean" scope="request"
		>
		<forward name="preaction" path="/add.do" />
	</action>
			
		
    <!-- sample input and input submit actions  

        <action
            path="/Input"
            type="org.apache.struts.actions.ForwardAction"
            parameter="/pages/Input.jsp"/>

        <action
            path="/InputSubmit"
            type="app.InputAction"
            name="inputForm"
            scope="request"
            validate="true"
            input="/pages/Input.jsp"/>

            <action
                path="/edit*"
                type="app.Edit{1}Action"
                name="inputForm"
                scope="request"
                validate="true"
                input="/pages/Edit{1}.jsp"/>

   end samples -->
    </action-mappings>


<!-- ============================================= Controller Configuration -->

    <controller
       processorClass="org.apache.struts.tiles.TilesRequestProcessor"/>


<!-- ======================================== Message Resources Definitions -->

    <message-resources parameter="MessageResources" />

	<!-- register3.jsp file 5 -->
	<message-resources parameter="MessageResources"
null="false"/>

<!-- =============================================== Plug Ins Configuration -->

  <!-- ======================================================= Tiles plugin -->
  <!--
     This plugin initialize Tiles definition factory. This later can takes some
	 parameters explained here after. The plugin first read parameters from
	 web.xml, thenoverload them with parameters defined here. All parameters
	 are optional.
     The plugin should be declared in each struts-config file.
       - definitions-config: (optional)
            Specify configuration file names. There can be several comma
		    separated file names (default: ?? )
       - moduleAware: (optional - struts1.1)
            Specify if the Tiles definition factory is module aware. If true
            (default), there will be one factory for each Struts module.
			If false, there will be one common factory for all module. In this
            later case, it is still needed to declare one plugin per module.
            The factory will be initialized with parameters found in the first
            initialized plugin (generally the one associated with the default
            module).
			  true : One factory per module. (default)
			  false : one single shared factory for all modules
	   - definitions-parser-validate: (optional)
	        Specify if xml parser should validate the Tiles configuration file.
			  true : validate. DTD should be specified in file header (default)
			  false : no validation

	  Paths found in Tiles definitions are relative to the main context.
  -->

    <plug-in className="org.apache.struts.tiles.TilesPlugin" >

      <!-- Path to XML definition file -->
      <set-property property="definitions-config"
                       value="/WEB-INF/tiles-defs.xml" />
      <!-- Set Module-awareness to true -->
      <set-property property="moduleAware" value="true" />
    </plug-in>


  <!-- =================================================== Validator plugin -->

  <plug-in className="org.apache.struts.validator.ValidatorPlugIn">
    <set-property
        property="pathnames"
        value="/WEB-INF/validator-rules.xml,/WEB-INF/validation.xml"/>
  </plug-in>

</struts-config>

**********************************************
