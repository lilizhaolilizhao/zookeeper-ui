var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootpId: "-1"
        },
        key: {
            name: "name",
            title: "fullPath"
        }
    },
    edit: {
        enable: false,
        showRemoveBtn: true,
        showRenameBtn: true
    },
    callback: {
        onClick: zTreeOnClick
        //beforeRemove : zTreeBeforeRemove,
        //onRemove : zTreeOnRemove
    },

    async: {
        enable: true,
        url: "getTree",
        autoParam: ["fullPath=path"]
    }

};

//处理异步加载返回的节点属性信息
function ajaxDataFilter(treeId, parentNode, responseData) {
    if (responseData) {
        for (var i = 0; i < responseData.length; i++) {
            if (responseData[i].parentId == 0) {
                responseData[i].isParent = "true";
            }
        }
    }
    return responseData;
};

function zTreeOnClick(event, treeId, treeNode) {
    var path = treeNode.fullPath;
    $('#data').val('');
    $('#path').text(path);

    $.ajax({
        url: contextPath + "/getNodeInfo",
        data: {"path": path},
        type: "post",
        dataType: "json",
        success: function (data) {
            $('#data').val(data.data);
            $('#dataformat').val(data.dataFormat);
            //alert(data.stat.mzxid)
            $('#czxid').text(data.stat.czxid);
            $('#mzxid').text(data.stat.mzxid);

            $('#ctime').text(getDateStr(data.stat.ctime, "yyyy-MM-dd h:m:s"));
            $('#mtime').text(getDateStr(data.stat.mtime, "yyyy-MM-dd h:m:s"));
            $('#version').text(data.stat.version);
            $('#cversion').text(data.stat.cversion);
            $('#aversion').text(data.stat.aversion);
            $('#ephemeralOwner').text(data.stat.ephemeralOwner);
            $('#dataLength').text(data.stat.dataLength);
            $('#numChildren').text(data.stat.numChildren);
            $('#pzxid').text(data.stat.pzxid);
        }
    });

}

function update() {
    var path = $('#path').text();

    if (path && path != '') {
        if (confirm("确定要修改么？")) {
            $.ajax({
                url: contextPath + "/updatePathData",
                type: "post",
                dataType: "json",
                data: {"path": path, "data": $('#data').val()},
                success: function (data) {
                    if (data.isSuccess) {
                        alert(data.content);
                        //刷新树
                    } else {
                        alert(data.content);
                    }
                }
            });
        }
    } else {
        alert("path不能为空！");
    }
}

function subNetwork1() {
    var startIP = $('#startIP').val();
    var endIP = $('#endIP').val();

    if (startIP != '' && endIP != '') {
        $.ajax({
            url: contextPath + "/subNetwork1",
            type: "post",
            dataType: "json",
            data: {"startIP": startIP, "endIP": endIP, "data": $('#data').val()},
            success: function (data) {
                $('#subnetInfo').val(data.subnetInfo);
            }
        });
    } else {
        alert("ip不能为空!");
    }
}

function subNetwork3() {
    var startIP = $('#startIP').val();
    var endIP = $('#endIP').val();

    if (startIP != '' && endIP != '') {
        $.ajax({
            url: contextPath + "/subNetwork3",
            type: "post",
            dataType: "json",
            data: {"startIP": startIP, "endIP": endIP, "data": $('#data').val()},
            success: function (data) {
                $('#subnetInfo3').val(data.subnetInfo);
            }
        });
    } else {
        alert("ip不能为空!");
    }
}

function subNetwork2() {
    var ipAddress = $('#ipAddress').val();
    var maskBit = $('#maskBit').val();

    if (ipAddress != '' && maskBit != '') {
        $.ajax({
            url: contextPath + "/subNetwork2",
            type: "post",
            dataType: "json",
            data: {"ipAddress": ipAddress, "maskBit": maskBit, "data": $('#data').val()},
            success: function (data) {
                $('#subnet').val(data.subnet);
            }
        });
    } else {
        alert("ip不能为空!");
    }
}

///**
//* 导入excel
//*/
//function importExcelData() {
//    var excelFile = document.getElementById('excelfile');
//
//    if (excelFile && excelFile != '') {
//        $.ajax({
//            url: contextPath + "/addPath",
//            type: "post",
//            dataType: "json",
//            data: {"path": path, "data": $('#add_data').val(), "flag": $('#flag').val()},
//            success: function (data) {
//                if (data.isSuccess) {
//                    alert(data.content);
//                    //刷新树
//                    loadTree();
//                } else {
//                    alert(data.content);
//                }
//            }
//        });
//    } else {
//        alert("没有选择文件!");
//    }
//}

function add() {
    var path = $('#add_search').val();
    if (path && path != '') {
        if (confirm("确定要添加么？")) {
            $.ajax({
                url: contextPath + "/addPath",
                type: "post",
                dataType: "json",
                data: {"path": path, "data": $('#add_data').val(), "flag": $('#flag').val()},
                success: function (data) {
                    if (data.isSuccess) {
                        alert(data.content);
                        //刷新树
                        loadTree();
                    } else {
                        alert(data.content);
                    }
                }
            });
        }
    } else {
        alert("path不能为空！");
    }
}

function export_config() {
    var exportPaths = "";

    var nodes = treeObj.getCheckedNodes();
    for (var i = 0; i < nodes.length; i++) {
        var cur_node_fullpath = nodes[i].fullPath;
        exportPaths = exportPaths + ";" + cur_node_fullpath;
    }

    if (exportPaths && exportPaths != '') {
        window.location.href = contextPath + "/exportConfig?exportPaths=" + encodeURI(encodeURI(exportPaths));
    }
}

//function export_xml_data() {
//    var exceldata = document.getElementById("exceldata").value;
//
//    if (exceldata && exceldata != '') {
//        $.ajax({
//            url: contextPath + "/exportXmlData",
//            type: "post",
//            dataType: "json",
//            data: {"exceldata": $('#exceldata').val()},
//            success: function (data) {
//                if (data.isSuccess) {
//                    alert(data.content);
//                } else {
//                    alert(data.content);
//                }
//            }
//        });
//    } else {
//        alert("预览数据为空!");
//    }
//}

function regionConvert() {
    window.open(contextPath + "/regionConvert", "_blank");
}

function deletePath() {
    var path = $('#path').text();

    if (path && path != '') {

        if (confirm("确定要删除么？")) {
            $.ajax({
                url: contextPath + "/deletePath",
                type: "post",
                dataType: "json",
                data: {"path": path},
                success: function (data) {
                    if (data.isSuccess) {
                        alert(data.content);
                        //清空节点及子节点
                        var nodes = treeObj.getSelectedNodes();
                        if (nodes && nodes.length > 0) {
                            treeObj.removeChildNodes(nodes[0]);
                            treeObj.removeNode(nodes[0]);
                        }
                    } else {
                        alert(data.content);
                    }
                }
            });
        }
    } else {
        alert("path不能为空！");
    }
}

function search() {
    var path = $('#add_search').val();
    if (path && path != '') {
        var nodes = treeObj.getNodesByParamFuzzy("name", path);
        for (var i = 0; i < nodes.length; i++) {
            //alert(nodes[i].name);
            treeObj.expandNode(nodes[i]);
        }
    } else {
        alert("搜索节点不能为空！");
    }
}


var treeObj;
var tree;
function loadTree() {
    $.ajax({
        url: contextPath + "/getTree",
        type: "post",
        dataType: "json",
        success: function (data) {
            treeObj = $.fn.zTree.init($("#zkTree"), setting, data);
            var node = treeObj.getNodeByParam("id", 0, null);
            //treeObj.expandNode(node);
        }
    });
}

function update_zookeeper_config() {
    var zookeeper_url = $('#zookeeper_url').val();
    window.location.href = contextPath + "/updateZookeeperConfig?zookeeperUrl=" + encodeURI(encodeURI(zookeeper_url));
}

$(document).ready(function () {
    loadTree();
});

