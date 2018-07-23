/**
(#)T001_LoginServlet.java 2018/07/04.
*
Copyright(C) 2016 by FUJINET CO., LTD.
*
Last_Update 2018/07/10.
Version 1.00.
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

import fis.cs.db.DBConnection;
import fis.cs.dao.T001_DAO;
import fjs.cs.common.Constants;
import fjs.cs.dto.MSTUSER;

/**
LoginServlet(Login)
author PHUONG-TD
version 1.00

*/

public class T001_LoginServlet extends HttpServlet {


private static final long serialVersionUID = 1L;


/**

* Event screen: switch page
* 
* @param HttpServletRequest req
* @param HttpServletResponse resp
* @return RequestDispatcher
*/
protected void doGet(HttpServletRequest req, HttpServletResponse resp)

       throws ServletException, IOException {

   //remove session
   HttpSession session = req.getSession(false);

   RequestDispatcher myRD = null;
   // Display Login screen
   myRD = req.getRequestDispatcher(Constants.T001_LOGIN);
   myRD.forward(req, resp);
}


/**

* Init screen
* 
* @param HttpServletRequest req
* @param HttpServletResponse resp
* @return RequestDispatcher
*/
protected void doPost(HttpServletRequest req, HttpServletResponse resp)

       throws ServletException, IOException {

   // user_id info
   String userid = req.getParameter("txtUserID");
   // password info
   String password = req.getParameter("txtPassword");

   RequestDispatcher myRD = null;
   myRD = req.getRequestDispatcher(Constants.T001_LOGIN);

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
               //save user info into session
               HttpSession session = req.getSession(true);
               session.setAttribute("sessionname", user.getUSERNAME());
               session.setAttribute("PSN_CD", user.getPSN_CD());
               session.setAttribute("CUSTOMER_NAME", "");
               session.setAttribute("BIRTHDAYFROM", "");
               session.setAttribute("BIRTHDAYTO", "");
               session.setAttribute("SEX", "");
           }

           //Init page
           T002_SearchServlet.Init(req, resp, conn);

           // display search screen
           myRD = req.getRequestDispatcher(Constants.T002_SEARCH);

       } else if (temp != 1) {
           //display error
           req.setAttribute("message", "ユーザーIDまたはパスワードが不正です。");
           myRD = req.getRequestDispatcher(Constants.T001_LOGIN);
       }
   }
   myRD.forward(req, resp);
}

}

**jsp servlet

<%@ page language="java" contentType="text/html; charset=utf-8"

pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Login - Training</title>

<link href="https://fonts.googleapis.com/css?family=Volkhov" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="css/style.css">

<script language="JavaScript">
var myFunction = function () {
var txtUserID = document.getElementById("txtUserID").value;
var txtPassword = document.getElementById("txtPassword").value;
if(txtUserID == 0){
document.getElementById("error").innerHTML = ('ユーザーIDを入力してください。');
return false;
}else if(txtPassword == 0){
document.getElementById("error").innerHTML = ('パスワードを入力してください。');
return false;
}
return true;
}

</script>
<style>
.content1 {
text-align: center;
width: 97%;
height: 400px;
position: relative;
}
#error {
font-weight: lighter;
font-size: 14px;
text-align: center;
color: red;
position: fixed;
left: 43%;
top: 33%;
}
</style>
</head>
<body>
<div class="contentpage">
<h1 id="title1">Training</h1>
<hr />
<a href="T001_LoginServlet">Login</a>

<h3 id="title2">LOGIN</h3>
<div class="content1" style="posision:relative !important;">
<form action="T001_LoginServlet" method="post" onsubmit="return myFunction();">
<h2 id="error" style="posision:relative;"><%= request.getAttribute("message") != null ? request.getAttribute("message"): " " %></h2>
<h2 id="error" style="posision:relative;"></h2>
User Id:    <input type="text" name="txtUserID" maxlength="8" id="txtUserID" class="inputdata" />
<br />
<br />
Password: <input type="password" name="txtPassword" maxlength="8" id="txtPassword" class="inputdata" />
<br />
<br />
<div class="container">

<div class="bottomleft"><input type="submit" value="Login" class="button" id="btnLogin"/></div>
<input class="button bottomright" type="reset" value="Clear" onclick="document.getElementById('error').innerHTML = ''" id="btnClear"/>
</div>
</form>
</div>

<div class="fixed">
    <hr />
    <p >&nbsp;&nbsp;&nbsp;&nbsp;Copyright(c) 2000-2008 FUJINET, All Rights Reserved.</p>
</div>
</div>
</body>
</html>
