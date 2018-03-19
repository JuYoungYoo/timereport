<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-07-11
  Time: 오전 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String fileName = request.getAttribute("fileName").toString();
    fileName = new String(fileName.getBytes("KSC5601"), "8859_1");
    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=euc-kr");
    response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + ".xls");
    response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Expires", "-1;");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
</head>
<body>
    <table border="1">
            <tr><td colspan="${dayList.size()+3}" align="center" style ="font-weight: bold;">${title}</td></tr>
            <tr>
                <th style ="background-color: #ccc;">프로젝트</th>
                <th style ="background-color: #ccc;">옵션 명</th>
                <c:forEach var="day" items="${dayList}" varStatus="status">
                    <c:set var="dayOfWeek" value ="${(FIRST_DAY_OF_WEEK+status.index) mod 7}"/>
                    <th style ="background-color: #ccc;
                        <c:if test = "${dayOfWeek < 2}">color:red;</c:if>">${day}</th>
                </c:forEach>
                <th style ="background-color: #ccc;">TOTAL</th>
            </tr>

            <c:forEach var="data" items="${dataList}" varStatus="status">
                <c:set var ="optionList" value = "${data.optionList}"/>
            <c:if test ="${0 < optionList.size()}">
                    <tr>
                        <td rowspan ="${optionList.size()}">${data.project_name}</td>
                        <c:forEach var="option" items="${optionList}" varStatus="status">
                            <td>${option.option_name}</td>
                            <c:forEach var="day" items="${dayList}">
                                <c:if test = "${option.dateInfo.containsKey(day)}">
                                    <td>${option.dateInfo.get(day)}</td>
                                </c:if>
                                <c:if test = "${!option.dateInfo.containsKey(day)}">
                                    <td></td>
                                </c:if>
                            </c:forEach>
                            <td>${option.total}</td>
                            <c:if test="${not status.last}"></tr><tr></c:if>
                        </c:forEach>
                    </tr>
            </c:if>
            </c:forEach>
            <tr>
                <td colspan="2"> 해당 날짜의 총 업무시간</td>
                <c:forEach var ="day" items="${dayList}">
                    <c:if test = "${dateTotal.containsKey(day)}">
                        <td>${dateTotal.get(day)}</td>
                    </c:if>
                    <c:if test = "${!dateTotal.containsKey(day)}">
                        <td></td>
                    </c:if>
                </c:forEach>
            </tr>
    </table>
</body>
</html>