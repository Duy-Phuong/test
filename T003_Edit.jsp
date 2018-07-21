<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="fjs.cs.dto.MSTCUSTOMER" %>	
<%@ page import="java.lang.Integer" %>
<%@ page import="java.lang.String" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Edit - Training</title>
<link href="https://fonts.googleapis.com/css?family=Volkhov" rel="stylesheet">
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<link rel="stylesheet" type="text/css" href="css/style.css">

</head>
<body>
<div class="contentpage">
	<h1 id="title1">Training</h1>
	<hr />
	<div class="parent">
		<div class="bar">
		 	<span class="left"><a href="T001_LoginServlet">Login</a>	&gt; Search Customer &gt; Edit Customer Info </span>
		</div>
	
		<div>
			<span class="left" style="display: block">Welcome <%=session.getAttribute("sessionname")%></span>
			<a href="T001_LoginServlet" class="logout">Log Out</a>
		</div>
	
	<div id="blue2">Edit</div>
	
	</div>
	<div class="edit">
		<form action="T003_EditServlet" method="post" onsubmit="return myFunction()" name="form1" id="formEdit">
			<h1 id="error3"></h1>
			<div class="relative">
			<%
				int id = Integer.parseInt((String)request.getAttribute("userName"));
			%>
			Customer ID:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <%=id != 0 ? id : ""%>
			<br/><br/>
			<%
				MSTCUSTOMER l = (MSTCUSTOMER) request.getAttribute("customer");
			String name = "";
			if(session.getAttribute("CUSTOMER_NAME") != null) {
				name = (String)session.getAttribute("CUSTOMER_NAME");
			}
			if( l.getCUSTOMER_NAME() != null) {
				name = l.getCUSTOMER_NAME();
			}
			%>
			Customer name:&nbsp;&nbsp;
			<input type="text" name="txtCustomerName" id="txtCustomerName" maxlength="50" class="inputdata3" value="<%=name%>" required/> 
			<br/>
			<br/>
			Sex &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<%
				String male = ""; 
				String female = ""; 
				if(session.getAttribute("SEX") != null && session.getAttribute("SEX").toString().equalsIgnoreCase("0") == true){
					male = "selected";
				}
				if(session.getAttribute("SEX") != null && session.getAttribute("SEX").toString().equalsIgnoreCase("1") == true){
					female = "selected";
				}
				if(l.getSEX() != null) {
					if(l.getSEX().equalsIgnoreCase("0") == true) {
						male = "selected";
						female = "";
					}else if(l.getSEX().equalsIgnoreCase("1") == true) {
						female = "selected";
						male = "";
					}
				}
			%>
				<select name="cboSex" class="inputdata4" id="cboSex">
				  <option value=""></option>
				  <option value="0" <%=male%>>Male</option>
				  <option value="1" <%=female%>>Female</option>
				</select>
				<br/>
				<br/>
				<%
					String birthdayFrom = (String) session.getAttribute("BIRTHDAYFROM");
					String birthdayTo = (String) session.getAttribute("BIRTHDAYTO");
					String birthday = "";
					if(birthdayFrom.equalsIgnoreCase("") == false && birthdayTo.equalsIgnoreCase("") == true) {
						birthday = birthdayFrom;
					} else if (birthdayTo.equalsIgnoreCase("") == false && birthdayFrom.equalsIgnoreCase("") == true){
						birthday = birthdayTo;
					} else if (birthdayFrom.equalsIgnoreCase("") == false && birthdayTo.equalsIgnoreCase("") == false) {
						birthday = birthdayFrom;
					}
					if(l.getBIRTHDAY() != null) {
						birthday = l.getBIRTHDAY();
					}
				%>
				Birthday:&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" name="txtBirthday" maxlength="10" class="inputdata4"  id="txtBirthday"
				value="<%=birthday%>"/> 
				
				<br/>
				<br/>
				Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="text" name="txtEmail" id="txtEmail" maxlength="40" class="inputdata5"  value="<%=l.getEMAIL() != null ? l.getEMAIL() : ""%>" id="txtEmail"/> 
				<br/>
				<br/>
				Address&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<textarea rows="4" cols="64" class="inputdata3"   name="txtAddress" id="txtAddress"><%=l.getADDRESS()!= null ? l.getADDRESS() : ""%>
				</textarea>
				<br/>
				<br/>	
				<input type="hidden" name="mode" value="<%=session.getAttribute("mode")%>"/> 
				<input type="hidden" name="CUSTOMER_ID" value="<%=id%>"/>
				<div class="container2">
				 
				  <div class="bottomleft ">
				 		<script>

						var edit = function ()
						{
							document.getElementById("click").value = "1";							
							
						   	return myFunction();
						}
					</script>
					<%
					String click = "0";
					String status = "addNew";
						if(id != 0) {
							status = "edit";
							click = "1";
						}
					%>
				  		<input type="hidden" name="modeAdd" value="<%=request.getAttribute("modeAdd") != null ? request.getAttribute("modeAdd") : ""%>"/>
						<input type="hidden" name="id" id="id" value="<%=id != 0 ? id : "0"%>"/>	
						<input type="hidden" name="status" id="status" value="<%= status%>" />	
						<input type="hidden" name="click" id="click" value="<%= click%>"/>	
						<input type="submit" value="Save" id="btnSave" class="button" onClick=" return edit()"/>
				  </div>
				  <div class="bottomright "><button onclick="return clearAll()" class="button">Clear</button></div>
				  
				</div>
			</div>
		</form>
		</div>

		<script>
			var clearAll = function() {
				document.getElementById("error3").innerHTML = "";
				document.getElementById("txtBirthday").value = "";
				document.getElementById("txtCustomerName").value = "";
				document.getElementById("txtEmail").value = "";
				document.getElementById("txtAddress").value = "";
				document.getElementById("cboSex").selectedIndex = 0;
				
				return false;
			}
		</script>
		<div class="fixed">
		<hr />
		<p ><span class="left">&nbsp;&nbsp;&nbsp;&nbsp;Copyright(c) 2000-2008 FUJINET, All Rights Reserved.</span></p>
	</div>
	</div>
	
<script>
	var myFunction = function () {
		
		var txtUserID = document.getElementById("txtCustomerName").value;
		var check = 0;
		if(txtUserID == 0){
			document.getElementById("error3").innerHTML = ('顧客名を入力してください。');
			check = 1;
			return false;
		}
		var txtBirthdayFrom = document.getElementById("txtBirthday").value;
		
		//if birthdayfrom is empty is empty
    	if (txtBirthdayFrom.length == 0 ) {
	    		
	    		document.getElementById("error3").innerHTML = ('誕生日が不正です');
	    		check = 2;
	    		return false;
	    		
		} else {
			//check day format
    		var pos = txtBirthdayFrom.indexOf("/");
    		var pos2 = txtBirthdayFrom.lastIndexOf("/");
        	if(pos != 4 || pos2 != 7 ) {
        		
        		document.getElementById("error3").innerHTML = ('誕生日が不正です');
        		check = 2;
        		return false;
            }
		}
    	//alert("email: " + document.form1.txtEmail.value);
    	var inputText = document.form1.txtEmail;
    	var length = document.getElementById("txtEmail").value;
    	//alert("mail length: " + document.getElementById("txtEmail").value);
    	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    	if(length == 0) {
    		document.getElementById("error3").innerHTML = ('メールアドレスが不正です。');
        	return false;
    	}
    	if(!inputText.value.match(mailformat))
    	{
    		document.getElementById("error3").innerHTML = ('メールアドレスが不正です。');
    		return false;
    	}
		if(check != 0) {
			return false;
		} else {
			var form = document.getElementById("formEdit");
		   	form.submit();
		}
   
	    return true;
	};

	
</script>
</body>
</html>
