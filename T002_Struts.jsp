**************jsp 2
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fjs.cs.dto.MSTCUSTOMER" %>
<%@ page import="java.lang.String" %>
<%@ page import="java.lang.Integer" %>
<%@ page import="java.lang.Boolean" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Search - Training</title>
<link href="https://fonts.googleapis.com/css?family=Volkhov" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="css/style.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

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
var selectedCustomer = [];
$("#table-products > tbody > tr > td > input:checked").each(function(){
var id = $(this).val();

numChecked = true;
var This = $(this);
This.closest("tr").remove();
selectedCustomer.push(id);
});
var hiddenSelected = document.getElementById("selectedCustomer");
hiddenSelected.value = selectedCustomer.join(",");

//check
if(numChecked == false){
alert("行を選択してください。");
return false;
}

});
});

</script>

<script>
/* isValidDate(str)
*/
var isValidDate = function () {
var txtBirthdayFrom = document.getElementById("txtBirthdayFrom").value;
var txtBirthdayTo = document.getElementById("txtBirthdayTo").value;

var check = 1;

regularExpression = /^(19[5-9][0-9]|20[0-4][0-9]|2050)//$/;

var form = document.getElementById("formSearch1");
if(form.txtBirthdayFrom.value != '' && !form.txtBirthdayFrom.value.match(regularExpression)) {
alert("Invalid Birthday(From)");
form.txtBirthdayFrom.focus();
check = 0;

}
if(form.txtBirthdayTo.value != '' && !form.txtBirthdayTo.value.match(regularExpression)) {
alert("Invalid Birthday(To)");
form.txtBirthdayTo.focus();
check = 0;

}
if(check == 0) {
return false;
}

if(check != 0 ){
var arrStartDate = txtBirthdayFrom.split("/");
var date1 = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2]);
var arrEndDate = txtBirthdayTo.split("/");
var date2 = new Date(arrEndDate[0], arrEndDate[1], arrEndDate[2]);
if(date1.getTime() > date2.getTime()) {
alert("There is an error in the range input of Birthday ");
check = 0;
return false;
}

}
//search with no condition
if ($('#txtCustomerName').val().length === 0 && $('#txtBirthdayFrom').val().length === 0 && $('#txtBirthdayTo').val().length === 0 && $('#cboSex').val().length === 0) {
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
<script>
function goNextPage()
{
var pageId = parseInt(document.getElementById("pageid").value) + 1;
document.getElementById("pageid").value = pageId.toString();
var form = document.getElementById("formSearch1");
form.submit();
}
function goPrevPage()
{
var pageId = parseInt(document.getElementById("pageid").value) - 1;
document.getElementById("pageid").value = pageId.toString();
var form = document.getElementById("formSearch1");
form.submit();
}
function goFirstPage()
{
document.getElementById("pageid").value = "1";
var form = document.getElementById("formSearch1");
form.submit();
}
function goLastPage()
{
document.getElementById("pageid").value = document.getElementById("maxpageid").value;
var form = document.getElementById("formSearch1");
form.submit();
}

        function search()
        {
            document.getElementById("pageid").value = "1";
            return isValidDate();
        }

        function addNew()
        {
            var form = document.getElementById("formAdd");
            form.submit();
        }

        function edit()
        {
            var form = document.getElementById("formEdit");
            form.submit();
        }

    </script>    
</head>
<body>
<div class="contentpage">
<h1 id="title1">Training</h1>
<hr />
<div class="parent">
<div class="bar">
<span class="left"><a href="/Struts-Project2/">Login</a> > Search
Customer </span>
</div>
<p><span class="space"> </span></p>
<%
int stt = (Integer) request.getAttribute("stt");
%>
<%
int maxpageid = (Integer) request.getAttribute("maxpageid");
%>

        <div>
            <span class="left" style="display: block">Welcome <bean:write name="mstUserBean" property="userName"></bean:write>
            </span> <a href="/Struts-Project2/" class="logout" id="lblLogOut">Log Out</a>
        </div>
        <p><span class="space">&nbsp;</span></p>

        <div class="box blue"></div>
         <html:form action="search" method="post" onsubmit="return isValidDate();" styleId="formSearch1">
        <div class="content">
            <div class="formSearch" id="formSearch">



                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Customer name:&nbsp;&nbsp;&nbsp;&nbsp;
                    <span class="space">ttttttttttttttttttttttttt&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <html:text name="SearchBean" property="txtCustomerName" maxlength="50" size="20" styleId="txtCustomerName" styleClass="inputdata"/>
                     &nbsp;&nbsp;&nbsp;&nbsp;

                <span class="space"></span>

                Sex &nbsp;&nbsp;
                <html:select property="cboSex" styleId="cboSex" styleClass="inputdata1">
                    <html:option value="">&nbsp;</html:option>
                    <html:option value="0">Male</html:option>
                    <html:option value="1">Female</html:option>

                </html:select>

                    <span class="space">ttttttttttttttttt</span>

                    Birthday: <html:text name="SearchBean" property="txtBirthdayFrom" maxlength="10" size="20" styleId="txtBirthdayFrom" styleClass="inputdata1"/>

                    &nbsp;&nbsp;~&nbsp;&nbsp; 
                    <html:text name="SearchBean" property="txtBirthdayTo"  maxlength="10" size="20" styleId="txtBirthdayTo" styleClass="inputdata1" />


                    <span class="space">tttttttttttttttttttttttttttttttttttt</span>
                    <span class="space">tttttttttttttttttttttttttttttttttttt</span>


                    <html:hidden name="SearchBean" property="pageid" value="<%= String.valueOf(stt) %>" styleId="pageid"/>
                    <html:hidden name="SearchBean" property="maxpageid" value="<%= String.valueOf(maxpageid) %>" styleId="maxpageid"/>
                    <html:submit property="mode" value="Search" styleId="btnSearch" onclick=" return search()" styleClass="button"/>

            </div>
        </div>

            <div class="switchPage">
        <ul id="pagination" style="list-style-type:none; text-decoration: none;"></ul>
        <div class="left"> 
            <%
                Boolean btnFirst = false;
                Boolean btnLast = false;
                Boolean btnPrevious = false;
                Boolean btnNext = false;
                Boolean btnDelete = false;

                btnDelete = request.getAttribute("deleteDisabled") != null ? true : false;
                btnFirst = request.getAttribute("minpageDisabled") != null ? true: false;
                btnPrevious = request.getAttribute("minpageDisabled") != null ? true: false;
                btnNext = request.getAttribute("maxpageDisabled") != null ? true: false;
                btnLast = request.getAttribute("maxpageDisabled") != null ? true: false;

                if(request.getAttribute("disabled") != null)
                {
                    btnFirst = request.getAttribute("disabled") != null ? true: false;
                    btnPrevious = request.getAttribute("disabled") != null ? true: false;
                    btnNext = request.getAttribute("disabled") != null ? true: false;
                    btnLast = request.getAttribute("disabled") != null ? true: false;
                }
            %>
            <html:submit property="mode" value="&lt;&lt;" styleId="btnFirst" onclick="goFirstPage()" disabled="<%= btnFirst %>"/>
            <html:submit property="mode" value="&lt;" styleId="btnPrevious" onclick="goPrevPage()" disabled="<%= btnPrevious %>"/>

            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Previous
        </div>
        <div class="right"> 
                Next&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <html:submit property="mode" value="&gt;" styleId="btnNext" onclick="goNextPage()" disabled="<%= btnNext%>"/>
                <html:submit property="mode" value="&gt;&gt;" styleId="btnLast" onclick="goLastPage()"  disabled="<%= btnLast%>"/>

        </div> 
        </div>    



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
                 <c:forEach items="${listCustomer}"  var="l">

                    <tr role="row">
                        <td><input type="checkbox" name="check" id="chkDetail" value="${l.CUSTOMER_ID}"/></td>

                         <html:form action="edit" method="post" styleId="formEdit">
                                 <html:hidden name="EditBean" property="customerID" value=" ${l.CUSTOMER_ID}"/>
                                 <html:hidden name="EditBean" property="status" value="edit"/>
                                <td>    <html:submit  property="mode" value=" ${l.CUSTOMER_ID}" styleClass="link-button"/> </td>
                            </html:form>
                        <td> ${l.CUSTOMER_NAME}</td>
                          <c:set var = "sex" scope = "session" value = "${l.SEX}"/>

                        <c:if test = "${sex == 0}">
                            <td>Male</td>
                          </c:if>
                          <c:if test = "${sex eq '1'}">
                             <td>Female</td>
                          </c:if>

                        <td>${l.BIRTHDAY}</td>
                        <td>${l.ADDRESS}</td>

                    </tr>                                                

                      </c:forEach>
                </tbody>
            </table>

                <div class="btnAdd">
                    <div class="container" style="float:left">

                        <div class="bottomleft">
                             <html:form action="add" method="post">
                                 <html:hidden name="EditBean" property="customerID" value="0"/>
                                <html:submit  property="mode" value="Add new" styleClass="button"/>
                            </html:form>

                        </div>    

                            <html:hidden name="SearchBean" property="selectedCustomer" value="" styleId="selectedCustomer"/>
                            <html:submit property="mode" value="Delete" styleId="btnDelete"  styleClass="button bottomright"  disabled="<%= btnDelete%>"/>
                    </div>
                </div>

            <div style="text-align:left; clear:both">
                <hr />
                <p>&nbsp;&nbsp;&nbsp;&nbsp;Copyright(c) 2000-2008 FUJINET, All
                    Rights Reserved.</p>
            </div>

        </div>
        </html:form>    
    </div>
</div>
</div>
</body>
</html>
