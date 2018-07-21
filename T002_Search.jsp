<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fjs.cs.dto.MSTCUSTOMER" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.lang.Integer" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Search - Training</title>
<link href="https://fonts.googleapis.com/css?family=Volkhov"
	rel="stylesheet">

<link rel="stylesheet" type="text/css" href="css/style.css">

<script type="text/javascript" src="css/custom.js"></script>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	
  
<script>
$(document).ready(function () {
	  $('.checkAll').on('click', function () {
	    $(this).closest('table').find('tbody :checkbox')
	      .prop('checked', this.checked)
	      .closest('tr').toggleClass('selected', this.checked);
	  });

	  $('tbody :checkbox').on('click', function () {
	    $(this).closest('tr').toggleClass('selected', this.checked); 
	  
	    $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length)); 
	  });

	  $("#btnDelete").click(function(){
		
		 var numChecked = false;
			$("#table-products > tbody > tr > td > input:checked").each(function(){
				var id = $(this).val();
				var date = new Date();
				
				var dateOfMonth = date.getDate();
				var monthTemp = date.getMonth() + 1; 
				var month = date.getMonth() + 1; 
				if(date.getDate() < 10) {
					dateOfMonth = "0" + date.getDate();
				}
				if(monthTemp < 10) {
					month = "0" + monthTemp;
				}
				var dateFormat = date.getFullYear()+ "/" + month + "/" + dateOfMonth;
				
				numChecked = true;
				var This = $(this);
				This.closest("tr").remove();
	
				$.ajax({
					url:"/CustomerSystem/T002_SearchServlet",
				    type:"POST",
				    data:{
				    	idDelete: id,
				    	date: dateFormat, 
				    	pageid: document.getElementById("pageid").value,
				    	type: "delete",
				    	txtCustomerName: document.getElementById("txtCustomerName").value,
				    	cboSex: document.getElementById("cboSex").value,
				    	txtBirthdayFrom: document.getElementById("txtBirthdayFrom").value,
				    	txtBirthdayTo: document.getElementById("txtBirthdayTo").value
				    	
				    },
					success: function(value){
						This.closest("tr").remove();
						 location.reload();  
					}
				});
			});

			//check
			if(numChecked == false){
				alert("行を選択してください。");
				return false;
			}
			
		});
	});  
	
</script>


<script>
/**
 * isValidDate(str)
*/
	var isValidDate = function () {
		var txtBirthdayFrom = document.getElementById("txtBirthdayFrom").value;
		var txtBirthdayTo = document.getElementById("txtBirthdayTo").value;
		var pos = txtBirthdayFrom.indexOf("/");
    	var pos2 = txtBirthdayFrom.lastIndexOf("/");
    	var pos3 = txtBirthdayTo.indexOf("/");
    	var pos4 = txtBirthdayTo.lastIndexOf("/");
    	var check = 1;

 
    	if ($('#txtBirthdayFrom').val().length != 0 && $('#txtBirthdayTo').val().length === 0) {
    		//check day format
        	if(pos != 4 || pos2 != 7 ) {
        		 	alert("Invalid Birthday(From)");
    		        check = 0;
    		        return false;
            }
		}
    	
    	if ($('#txtBirthdayFrom').val().length === 0 && $('#txtBirthdayTo').val().length != 0) {
    		if(pos3 != 4 || pos4 != 7 ) {
    		 	alert("Invalid Birthday(To)");
    		 	 check = 0;
    		 	return false;
        	}
		}

    	//if birthdayfrom isn't empty and birthdayto isn't empty
    	if ($('#txtBirthdayFrom').val().length != 0 && $('#txtBirthdayTo').val().length != 0) {
    		//check day format
        	if(pos != 4 || pos2 != 7 ) {
        		 	alert("Invalid Birthday(From)");
    		        check = 0;
    		        
            }
        	if(pos3 != 4 || pos4 != 7 ) {
    		 	alert("Invalid Birthday(To)");
    		 	 check = 0;
    		 	
        	}
        	if(check == 0)
    			return false;
		}
    	if(check != 0 ){
    		var arrStartDate = txtBirthdayFrom.split("/");
    		var date1 = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2]);
    		var arrEndDate = txtBirthdayTo.split("/");
    		var date2 = new Date(arrEndDate[0], arrEndDate[1], arrEndDate[2]);
    		//alert(date1 + " --- " +  date2);
    		if(date1.getTime() > date2.getTime()) {
    			alert("There is an error in the range input of Birthday	");
    			check = 0;
    			return false;
    		}
    		
    	}
    	//search with no condition 
    	if ($('#txtCustomerName').val().length === 0  && $('#txtBirthdayFrom').val().length === 0 && $('#txtBirthdayTo').val().length === 0 && $('#cboSex').val().length === 0) {
				check = 1;
        }	
        //if have a condition
    	if ($('#txtCustomerName').val().length != 0 || $('#cboSex').val().length != 0 || $('#txtBirthdayFrom').val().length != 0 || $('#txtBirthdayTo').val().length != 0) {
			check = 1;
    }			    
		if(check == 0) {
			return false;
		} else {
			var form = document.getElementById("formSearch1");
		    form.submit();
		}
			   
			    return true;
	    
	}

</script>

<style>
.link-button {
  background: none;
  border: none;
  color: blue;
  text-decoration: underline;
  cursor: pointer;
  font-size: 1em;
  font-family: serif;
}
.link-button:focus {
  outline: none;
}
.link-button:active {
  color:red;
}
</style>
</head>
<body>
	<div class="contentpage">
		<h1 id="title1">Training</h1>
		<hr />
		<div class="parent">
			<div class="bar">
				<span class="left"><a href="T001_LoginServlet">Login</a> &gt; Search
					Customer </span>
			</div>
			<%
				int stt = (Integer) request.getAttribute("stt");					
			%>
			<%
				int maxpageid = (Integer) request.getAttribute("maxpageid");
			%>
			
			<div>
				<span class="left" style="display: block">Welcome  <%=session.getAttribute("sessionname") != null ? session
					.getAttribute("sessionname") : ""%>
				</span> <a href="T001_LoginServlet" class="logout" id="lblLogOut">Log Out</a>
			</div>

			<div class="box blue"></div>
		<form action="T002_SearchServlet" method="post" onsubmit="return isValidDate();" id="formSearch1">
			<div class="content">
				<div class="formSearch" id="formSearch">
					
						<h1 id="error"><%=request.getAttribute("message") != null ? request.getAttribute("message") : " "%></h1>
						
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Customer name:&nbsp;&nbsp;&nbsp;&nbsp;
						<span class="space">ttttttttttttttttttttttttt&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
						<input type="text"
							name="txtCustomerName" maxlength="50" class="inputdata"
							id="txtCustomerName" value="<%=session.getAttribute("CUSTOMER_NAME") != null ? session
					.getAttribute("CUSTOMER_NAME") : ""%>"/> &nbsp;&nbsp;&nbsp;&nbsp;
					
					<span class="space"></span>
					
					Sex &nbsp;&nbsp;
						<select name="cboSex" id="cboSex"  class="inputdata1">
							<option value="" ></option>
							<option value="0" <%=(session.getAttribute("SEX") != null && session
					.getAttribute("SEX").toString().equalsIgnoreCase("0") == true) ? "selected"
					: ""%> >Male</option>
							<option value="1" <%=(session.getAttribute("SEX") != null && session
					.getAttribute("SEX").toString().equalsIgnoreCase("1") == true) ? "selected"
					: ""%>>Female</option>
						</select> 
						
						<span class="space">ttttttttttttttttt</span>
						
						Birthday: <input type="text" name="txtBirthdayFrom"
							maxlength="10" class="inputdata1" id="txtBirthdayFrom" value="<%=session.getAttribute("BIRTHDAYFROM") != null ? session.getAttribute("BIRTHDAYFROM") : ""%>"/>
						&nbsp;&nbsp;~&nbsp;&nbsp; <input type="text" name="txtBirthdayTo"
							maxlength="10" class="inputdata1" id="txtBirthdayTo" value="<%=session.getAttribute("BIRTHDAYTO") != null ? session.getAttribute("BIRTHDAYTO") : ""%>"/>
				
								
						<span class="space">tttttttttttttttttttttttttttttttttttt</span>
						<span class="space">tttttttttttttttttttttttttttttttttttt</span>
		<script>
			function goNextPage()
			{
				document.getElementById("status").value = "next";
				var pageId = parseInt(document.getElementById("pageid").value) + 1;
				document.getElementById("pageid").value = pageId.toString();
				var form = document.getElementById("formSearch1");
			    form.submit();
			}
			function goPrevPage()
			{
				document.getElementById("status").value = "previous";
				var pageId = parseInt(document.getElementById("pageid").value) - 1;
				document.getElementById("pageid").value = pageId.toString();
				var form = document.getElementById("formSearch1");
			    form.submit();
			}
			function goFirstPage()
			{
				document.getElementById("status").value = "first";
				document.getElementById("pageid").value = "1";
				var form = document.getElementById("formSearch1");
			    form.submit();
			}
			function goLastPage()
			{
				document.getElementById("status").value = "last";
				document.getElementById("pageid").value = document.getElementById("maxpageid").value;
				var form = document.getElementById("formSearch1");
			    form.submit();
			}
			
			function search()
			{
				document.getElementById("status").value = "search";
				document.getElementById("pageid").value = "1";
			/*	var form = document.getElementById("formSearch1");
			    form.submit(); */

			    return isValidDate();
			}
			
			function addNew()
			{
				document.getElementById("mode").value = "addNew";
				var form = document.getElementById("formAdd");
			    form.submit();
			}
			
			function edit()
			{
				document.getElementById("mode").value = "edit";
				var form = document.getElementById("formEdit");
			    form.submit();
			}
		
		</script>			
						<input type="hidden" name="pageid" id="pageid" value="<%=stt%>"/>	
						<input type="hidden" name="status" id="status" value=""/>	
						<input type="hidden" name="type" id="type" value="search"/>	
						<input type="hidden" name="maxpageid" id="maxpageid" value="<%=maxpageid%>"/>	
						<button type="submit" id="btnSearch" onClick=" return search()" class="button"> Search </button>
				</div>
			</div>
			
			<div class="switchPage">
			<ul id="pagination" style="list-style-type:none; text-decoration: none;"></ul>
			<div class="left"> 
				<button id="btnFirst" onClick="goFirstPage()" <%=request.getAttribute("disabled") != null ? "disabled": ""%> 
						  <%=request.getAttribute("minpageDisabled") != null ? "disabled": ""%>
						 > &lt;&lt; </button>
				<button  id="btnPrevious" onClick="goPrevPage()" <%=request.getAttribute("disabled") != null ? "disabled": ""%> 
			 				<%=request.getAttribute("minpageDisabled") != null ? "disabled": ""%>> &lt;</button>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Previous
			</div>
			<div class="right"> 
			Next&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="btnNext" onClick="goNextPage()" <%=request.getAttribute("disabled") != null ? "disabled": ""%>
						<%=request.getAttribute("maxpageDisabled") != null ? "disabled": ""%>> &gt;</button>
					<button  id="btnLast" onClick="goLastPage()" <%=request.getAttribute("disabled") != null ? "disabled": ""%>
						  <%=request.getAttribute("maxpageDisabled") != null ? "disabled"	: ""%>> &gt;&gt;</button>
			 
			</div> 
			</div>
			</form>
			
			<div class="content3">
				<table class="table" id="table-products">
					<thead>
					<col width="5">
  					<col width="160">
  					<col width="140">
  					<col width="100">
  					<col width="100">
  					<col width="300">
						<tr>
							<th><input type="checkbox" class="checkAll" name="checkAll" id="chkAll" /></th>
							<th>Customer ID</th>
							<th>Customer Name</th>
							<th>Sex</th>
							<th>Birthday</th>
							<th>Address</th>

						</tr>
					</thead>
					<tbody>
					<%
						List<MSTCUSTOMER> list = (List<MSTCUSTOMER>) request.getAttribute("listCustomer");
						for (MSTCUSTOMER l : list) {
					%>
						<tr role="row">
							<td><input type="checkbox" name="check" id="chkDetail" value="<%=l.getCUSTOMER_ID()%>"/></td>
							<form action="T003_EditServlet" method="POST" id="formEdit"> 
								<input type="hidden" name="mode" id="mode" value="edit"/>	
								<input type="hidden" name="id" id="id" value="<%=l.getCUSTOMER_ID()%>"/>
								<input type="hidden" name="click" id="click" value="0"/>		
								<input type="hidden" name="status" id="status" value="edit" />	
								<td> <button type="submit" name="submit_param" value="submit_value" class="link-button ">
									   <%=l.getCUSTOMER_ID()%>
									  </button></td>
							</form>
							<td><%=l.getCUSTOMER_NAME()%></td>
							<%
								String sex = "";
								if(l.getSEX() != null && "".equalsIgnoreCase(l.getSEX()) == false) {
								
									if (Integer.parseInt(l.getSEX()) == 0) {
										sex = "Male";
									} else {
										sex = "Female";
									}
							}
							%>
							<td><%=sex%></td>
							<td><%=l.getBIRTHDAY()%></td>
							<td><%=l.getADDRESS()%></td>

						</tr>												
					<%	}	%>
						
					</tbody>
				</table>
				
					<div class="btnAdd">
						<div class="container" style="float:left">

							<div class="bottomleft">
							<form action="T003_EditServlet" method="POST" id="formAdd">
								
								<input type="hidden" name="mode" id="mode" value="addNew"/>	
								<input type="hidden" name="id" id="id" value="0"/>	
								<input type="hidden" name="click" id="click" value="0"/>	
								<input type="hidden" name="status" id="status" value="addNew" />	
								<button  id="btnAddnew" onClick="addNew()" class="button"> Add new </button>
					
							</form>
							</div>						
							
								
								<button name="btnDelete" value="btnDelete" class="button bottomright" id="btnDelete" <%=request.getAttribute("deleteDisabled") != null ? request.getAttribute("deleteDisabled") : ""%>>Delete</button>
								<!-- 		<input type="submit" value="Delete" class="button bottomright" <%=request.getAttribute("deleteDisabled") != null ? request.getAttribute("deleteDisabled") : ""%>	id="delete" />
						 -->	
						</div>
					</div>
	
				<div style="text-align:left; clear:both">
					<hr />
					<p>&nbsp;&nbsp;&nbsp;&nbsp;Copyright(c) 2000-2008 FUJINET, All
						Rights Reserved.</p>
				</div>

			</div>
		</div>
	</div>
	</div>
</body>
</html>
