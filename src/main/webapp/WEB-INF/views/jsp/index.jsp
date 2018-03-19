<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-03-28
  Time: 오후 4:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  login.jsp
  <form name="form" id ="form" method = "post" action ="/login">
    <input type ="text" name ="userid" value ="admin"/>
    <input type ="text" name = "passwd" value ="test"/>
      <input type ="submit" value ="전송"/>
  </form>
</body>
</html>
<script>
    if('${loginMsg}' != ''){
        alert("${loginMsg}");
    }
</script>
