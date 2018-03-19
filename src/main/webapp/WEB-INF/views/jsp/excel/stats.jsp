<%--
  Created by IntelliJ IDEA.
  User: chloe
  Date: 2017-07-06
  Time: 오전 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String fileName = request.getAttribute("fileName").toString();
    fileName = new String(fileName.getBytes("KSC5601"), "8859_1");
    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=EUC-KR");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
    response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Expires", "-1;");
%>​
<html>
<head>
    <meta http-equiv="Content-Type" content="application/vnd.ms-excel;charset=euc-kr">
</head>
<body>
    <c:set var ="titleSize" value ="${memberList.size() + 3}"/>
    <table style ="background: yellow;">
        <tr>
            <td colspan ="${titleSize}" align="center" style ="font-size: 24px; font-weight: bold;">업무시간통계</td>
        </tr>
        <tr>
            <td colspan ="${titleSize}" align="right" >
            ${fn:substringBefore(param.date, '-')}.${fn:substringAfter(param.date, '-')}
                <br/>부서: ${param.deptName}
            </td>
        </tr>
    </table>
    <table border="1">
        <tr>
            <th style ="background: #ccc;">프로젝트</th>
            <th style ="background: #ccc;">세부사항</th>
            <c:forEach var="member" items="${memberList}">
                <th style ="background: #ccc;">${member.name}</th>
            </c:forEach>
            <th style ="background: #ccc;">TOTAL</th>
        </tr>
        <c:forEach var ="data" items="${dataList}" varStatus="status">
            <c:set var = "optionList" value="${data.optionList}"/>
            <c:if test ="${data.optionSize != 0}">
                <tr>
                    <td rowspan="${data.optionSize}">${data.project_name}</td>
                    <c:forEach var ="option" items="${optionList}" varStatus="status">
                            <td>${option.get('option_val')}</td>
                            <c:forEach var="member" items="${memberList}">
                                    <td>${option.get(member.userid)}</td>
                            </c:forEach>
                            <td>${option.get('total')}</td>
                        <c:if test="${not status.last}"></tr><tr></c:if>
                    </c:forEach>
                </tr>
            </c:if>
        </c:forEach>
        <tr>
            <td colspan="2">총 업무시간</td>
            <c:forEach var ="member" items="${memberList}">
                <td>${total.get(member.userid)}</td>
            </c:forEach>
            <td>${total.get('total')}</td>
        </tr>
    </table>
</body>
</html>