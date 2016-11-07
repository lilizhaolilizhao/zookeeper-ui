<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@ include file="./include/include.jsp" %>

    <script type="text/javascript" src="<%=path%>/resources/js/user/tree.js"></script>

    <style type="text/css">
        label {
            width: 80px;
            display: inline-block;
            text-align: right;
        }

        #Tree li {
            line-height: 14px;
        }

        #showview li {
            width: 400px;
        }

    </style>
</head>
<body class="overflow">

<div class="content_wrap" style="width: 100%;">
    <div class="content_wrap_title" style="width: 100%">
        <span style="width: 400px">zk ui 树管理 （${SESSION_KEY}）  </span>
        <input type="text" id="zookeeper_url" value=${zookeeperUrl} name="zookeeper_url" align="right"/>
        <input type="button" name="update_zookeeper_config" onclick="update_zookeeper_config()" value="更换连接"
               align="right"/>
    </div>
    <div class="zTreeDemoBackground left" style="width: 400px; margin-left:20px">
        <div style="margin: 5px">
            PATH: <input id="add_search">

        </div>
        <div style="margin: 5px">
            MODE:
            <select id="flag">
                <option value="0">PERSISTENT</option>
                <option value="1">EPHEMERAL</option>
                <option value="2">PERSISTENT_SEQUENTIAL</option>
                <option value="3">EPHEMERAL_SEQUENTIAL</option>
            </select>
        </div>
        <div style="margin: 5px">
            DATA:
            <textarea style="width: 200px;height: 50px" id="add_data"></textarea>
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
        <ul id="zkTree" class="ztree" style="width: 300px;">

        </ul>
    </div>
    <div id="showview" style="margin-right: 10px;float: left;margin-top:15px" class="right">
        <ul>
            <li>
                PATH: <span id="path">
					   </span>
                (<a href="javascript:void(0)" onclick="deletePath();">删除节点</a>)
            </li>
            <li>
                DATA:
                (<a href="javascript:void(0)" onclick="update();">更新节点数据</a>)
                <textarea style="width: 400px;height: 100px" id="data"></textarea>
                DATA:数据格式化(只读)
                <textarea style="width: 700px;height: 700px" id="dataformat" disabled="disabled"></textarea>

            </li>
            <li style="line-height: 30px">
                STATE:
                <div style="margin-left: 15px">
                    <span> czxid = <span id="czxid"></span>  </span> <br>
                    <span> mzxid = <span id="mzxid"></span>  </span> <br>
                    <span> ctime = <span id="ctime"></span>  </span> <br>
                    <span> mtime = <span id="mtime"></span>  </span> <br>
                    <span> version = <span id="version"></span>  </span> <br>
                    <span> cversion = <span id="cversion"></span>  </span> <br>
                    <span> aversion = <span id="aversion"></span>  </span> <br>
                    <span> ephemeralOwner = <span id="ephemeralOwner"></span>  </span> <br>
                    <span> dataLength = <span id="dataLength"></span>  </span> <br>
                    <span> numChildren = <span id="numChildren"></span>  </span> <br>
                    <span> pzxid = <span id="pzxid"></span>  </span>
                </div>
            </li>

        </ul>
    </div>

</div>


</body>
</html>