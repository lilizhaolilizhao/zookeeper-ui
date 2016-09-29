package com.javazyw.zk.controller;

import com.alibaba.fastjson.JSONArray;
import com.javazyw.zk.util.ClientFactory;
import com.javazyw.zk.vo.AjaxMessage;
import com.javazyw.zk.vo.TreeInfo;
import com.javazyw.zk.vo.TreeVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.zookeeper.CreateMode;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ZkController {


    private static final Log logger = LogFactory.getLog(ZkController.class);

    static final CuratorFramework client = ClientFactory.getInstance();

    @RequestMapping(value = "/main")
    public ModelAndView mian() {
        System.out.println("---------");


        return new ModelAndView("main");
    }

    @RequestMapping(value = "getTree")
    @ResponseBody
    public List<TreeVO> getTree(String path) throws Exception {
        logger.info("gettree   path =====>>>" + path);
        List<TreeVO> treeList = new ArrayList<TreeVO>();
        if (path == null) {
            path = "/";
            TreeVO tree = new TreeVO();
            tree.setId(0);
            tree.setName("/");
            tree.setParentId(-1);
            tree.setFullPath("/");
            tree.setIsParent("true");
            treeList.add(tree);
        }

        //List<String> list =  client.getChildren().forPath("/");
        //System.out.println("list===>" + list);
        //recursionTree("", list, treeList, 0);
        getChildren(path, treeList);
        return treeList;
    }

    private void getChildren(String path, List<TreeVO> treeList) throws Exception {
        List<String> list = client.getChildren().forPath(path);

        int parentId = path.equals("/") ? 0 : path.hashCode();
        path = path.equals("/") ? "" : path;

        for (String string : list) {
            String newPath = path + "/" + string;
            System.out.println("newPath..." + newPath);

            TreeVO tree = new TreeVO();
            tree.setId(newPath.hashCode());
            tree.setParentId(parentId);
            tree.setName(string);
            tree.setFullPath(newPath);

            List<String> list1 = client.getChildren().forPath(newPath);
            if (list1.size() > 0) {
                tree.setIsParent("true");
            } else {
                tree.setIsParent("false");
            }
            treeList.add(tree);
        }

    }


    private void recursionTree(String path, List<String> pList, List<TreeVO> treeList, int parentId) {

        for (String string : pList) {
            String newPath = path + "/" + string;
            System.out.println("newPath..." + newPath);
            try {

                TreeVO tree = new TreeVO();
                tree.setId(newPath.hashCode());
                tree.setParentId(parentId);
                tree.setName(string);
                tree.setFullPath(newPath);

                List<String> list = client.getChildren().forPath(newPath);
                int pid = tree.getId();
                treeList.add(tree);
                recursionTree(newPath, list, treeList, pid);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping("getNodeInfo")
    public TreeInfo getNodeInfo(String path) throws Exception {
        System.out.println("path===>" + path);

        NodeCache node = new NodeCache(client, path);
        node.start(true); //这个参数要给true  不然下边空指针...
        String d = new String(node.getCurrentData().getData() == null ? new byte[]{} : node.getCurrentData().getData());


        TreeInfo info = new TreeInfo();
        info.setData(d);
        info.setStat(node.getCurrentData().getStat());

        node.close();

        return info;
    }

    @ResponseBody
    @RequestMapping(value = "updatePathData")
    public AjaxMessage updatePathData(String path, String data) {

        AjaxMessage msg = new AjaxMessage(true, "修改成功!");

        try {

            if (client.checkExists().forPath(path) != null) {
                client.setData().forPath(path, data.getBytes());
            } else {
                msg.setIsSuccess(false);
                msg.setContent("无此节点信息!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg.setIsSuccess(false);
            msg.setContent("服务端异常");
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "addPath")
    public AjaxMessage addPath(String path, String data, int flag) {

        AjaxMessage msg = new AjaxMessage(true, "添加成功!");

        try {

            if (client.checkExists().forPath(path) == null) {
                client.create()
                        .creatingParentsIfNeeded().withMode(CreateMode.fromFlag(flag)).forPath(path, data.getBytes());

            } else {
                msg.setIsSuccess(false);
                msg.setContent("该节点已经存在!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg.setIsSuccess(false);
            msg.setContent("服务端异常，" + e.getMessage());
        }
        return msg;
    }

    @RequestMapping("download")
    public ResponseEntity<byte[]> download() throws IOException {
        String path = "/Users/lilizhao/git/zookeeper-ui/zk-ui/src/main/webapp/WEB-INF/web.xml";
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String("你好.txt".getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "exportConfig")
    public AjaxMessage exportConfig(String exportPaths, HttpServletResponse response) throws UnsupportedEncodingException {
        AjaxMessage msg = new AjaxMessage(true, "添加成功!");

        PrintWriter writer = null;
        String filepath = "test.txt";
        String fileName = URLDecoder.decode(filepath, "utf-8");

        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + "\";");
        //attachment --- 作为附件下载

        try {
            writer = response.getWriter();
            response.reset();
            response.setContentType("application/octet-stream");

            String[] exportPathArray = exportPaths.split(";");
            for (String path : exportPathArray) {
                if (!"".equals(path)) {
                    NodeCache node = new NodeCache(client, path);
                    node.start(true); //这个参数要给true  不然下边空指针...
                    String data = new String(node.getCurrentData().getData() == null ? new byte[]{} : node.getCurrentData().getData());

                    writer.write(path + "=" + new String(Base64.encode(data.getBytes())) + "\n");
                    node.close();
                }
            }

            msg.setIsSuccess(true);
            msg.setContent("配置信息导出成功!");
        } catch (Exception e) {
            e.printStackTrace();
            msg.setIsSuccess(false);
            msg.setContent("服务端异常，" + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "deletePath")
    public AjaxMessage deletePath(String path) {

        AjaxMessage msg = new AjaxMessage(true, "删除成功!");

        try {

            if (client.checkExists().forPath(path) != null) {
                client.delete().deletingChildrenIfNeeded().forPath(path);

            } else {
                msg.setIsSuccess(false);
                msg.setContent("该节点不存在!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            msg.setIsSuccess(false);
            msg.setContent("服务端异常，" + e.getMessage());
        }
        return msg;
    }


    public static void main(String[] args) throws Exception {

        ///client.create().forPath("/ddddddd", "aaa".getBytes());
        /*client.create().forPath("/a", "aaa".getBytes());
        client.create().forPath("/a/a_1", "a_1a_1a_1".getBytes());
		client.create().forPath("/a/a_2", "a_2a_2a_2".getBytes());
		client.create().forPath("/b", "bbb".getBytes());
		client.create().forPath("/b/b_1", "b_1b_1b_1b_1".getBytes());*/
        //client.setData().forPath("/a", "aaaaaaaaaaaa".getBytes());
//        List<TreeVO> treeList = new ArrayList<TreeVO>();
//        List<String> list = client.getChildren().forPath("/");
//        System.out.println("list===>" + list);
//        recursionTree("", list, treeList, 0);
//
//        System.out.println(treeList);
//
//        System.out.println(JSONArray.toJSONString(treeList));

        byte[] encode = Base64.encode("test123".getBytes());
        String test = new String(encode);
        System.out.println(test);
    }



}
