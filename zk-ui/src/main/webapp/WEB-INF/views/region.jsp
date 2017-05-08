<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="./include/include.jsp" %>

    <script type="text/javascript" src="<%=path%>/resources/js/user/tree.js"></script>
</head>
<body class="overflow">

<div class="content_wrap" style="width: 100%;">
    <div class="content_wrap_title" style="width: 100%">
        <span style="width: 400px">zk 地域转化(Excel -> XML) </span><br>
    </div>
    <div class="zTreeDemoBackground left" style="width: 400px; margin-left:20px">
        <div style="margin: 5px">
            Excel 数据转化:
            <form method="post" action="./importExcelData" enctype="multipart/form-data">
                <input id="excelfile" type="file" name="excelfile"/><br/>
                <input type="submit" value="数据预览">
            </form>
            <%--<button onclick="export_xml_data();">导出预览数据</button>--%>
        </div>
        <br/>

        <div style="margin: 5px">
            辅助工具1:<br/>
            开始IP: <input type="text" id="startIP" name="startIP" value="10.75.36.67" align="right"/><br/>
            结束IP: <input type="text" id="endIP" name="endIP" value="10.75.36.127" align="right"/><br/>
            <button onclick="subNetwork1();">计算子网(包含关系):</button>
            <input type="text" id="subnetInfo" name="subnetInfo" value="10.75.36.64/26" align="right"/><br/>
            <button onclick="subNetwork3();">计算子网(精确匹配):</button><br/>
            <textarea id="subnetInfo3" name="subnetInfo3" style="width:200px;height:80px;" align="right">10.75.36.67/32
10.75.36.68/30
10.75.36.72/29
10.75.36.80/28
10.75.36.96/27</textarea>
        </div>
        <br/>

        <div style="margin: 5px">
            辅助工具2:<br/>
            IP地址: <input type="text" id="ipAddress" name="ipAddress" value="10.75.36.67" align="right"/><br/>
            掩码位: <input type="text" id="maskBit" name="maskBit" value="26" align="right"/><br/>
            <button onclick="subNetwork2();">计算子网:</button>
            <input type="text" id="subnet" name="subnet" value="10.75.36.64/26" align="right"/>
        </div>
    </div>
    <div id="showview" style="margin-right: 10px;float: left;margin-top:0px" class="right">
        <ul>
            <li>
                地域配置信息(预览):
                <textarea id="exceldata" style="width: 700px;height: 700px">${exceldata}</textarea>
            </li>
        </ul>
    </div>

</div>


</body>
</html>