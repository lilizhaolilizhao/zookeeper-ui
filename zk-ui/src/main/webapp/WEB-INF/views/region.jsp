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
        <span style="width: 400px">zk 地域转化(Excel -> XML) </span>
    </div>
    <div class="zTreeDemoBackground left" style="width: 400px; margin-left:20px">
        <div style="margin: 5px">
            Excel 数据转化:
            <form method="post" action="./importExcelData" enctype="multipart/form-data">
                <input id="excelfile" type="file" name="excelfile"/><br/>
                <input type="submit" value="数据预览">
            </form>
        </div>
        <br/>

        <div style="margin: 5px">
            ZOOKEEPER 数据转化:
            <input type="text" id="zookeeper_url" name="zookeeper_url" align="right"/>
            <input type="button" name="update_zookeeper_config" onclick="update_zookeeper_config()" value="更换连接"
                   align="right"/>
        </div>
        <div style="margin: 5px">
            <button onclick="search();">搜索</button>
            &nbsp;
            <button onclick="add();">添加节点</button>
            &nbsp;
            <button onclick="export_config();">导出配置</button>
            &nbsp;
            <form action="./importConfig" method="post" enctype="multipart/form-data">
                <input type="file" name="myfile"/><br/>
                <input type="submit" value="导入配置">
            </form>
        </div>
    </div>
    <div id="showview" style="margin-right: 10px;float: left;margin-top:0px" class="right">
        <ul>
            <li>
                地域配置信息(预览):
                <textarea style="width: 700px;height: 700px">${exceldata}</textarea>
            </li>
        </ul>
    </div>

</div>


</body>
</html>