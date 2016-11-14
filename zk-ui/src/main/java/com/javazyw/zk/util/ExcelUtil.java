package com.javazyw.zk.util;

import org.apache.poi.ss.usermodel.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by lilizhao on 16-11-14.
 */
public class ExcelUtil {

    public static String excel2XmlMethod(InputStream excelFileStream) throws IOException, JDOMException {
        Map<String, String> allRegionInfo = getAllRegionInfo(excelFileStream);

        //创建一个xml文档的  根节点
        Document document = new Document();

        Element locationEle = new Element("Location");
        locationEle.setAttribute("Enable", "true");

        document.addContent(locationEle);

        //默认部分
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        Element defaultRegionEle = new Element("Region");
        defaultRegionEle = new Element("Region");
        defaultRegionEle.setAttribute("Name", "默认地域");
        defaultRegionEle.setAttribute("Edit", "false");

        locationEle.addContent(defaultRegionEle);

        Element defaultAreaEle = new Element("Area");
        defaultAreaEle.setAttribute("Name", "默认区域");

        defaultRegionEle.addContent(defaultAreaEle);

        Element defalutSite1Ele = new Element("Site");
        defalutSite1Ele.setAttribute("Name", "互联网");
        Element defalutSite2Ele = new Element("Site");
        defalutSite2Ele.setAttribute("Name", "局域网");
        Element defalutSite3Ele = new Element("Site");
        defalutSite3Ele.setAttribute("Name", "本地网");

        defaultAreaEle.addContent(defalutSite1Ele);
        defaultAreaEle.addContent(defalutSite2Ele);
        defaultAreaEle.addContent(defalutSite3Ele);
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////
        //////////////////////////////////////////////////

        Iterator<Map.Entry<String, String>> iterator = allRegionInfo.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            String regionStr = next.getKey();
            String netValue = next.getValue();

            String[] split = regionStr.split("-");
            if (split.length == 3) {
                String region = split[0];
                String area = split[1];
                String site = split[2];

                Element regionEle = (Element) XPath.selectSingleNode(document, "/Location/Region[@Name='" + region + "']");
                if (regionEle != null) {
                    Element areaEle = (Element) XPath.selectSingleNode(document, "/Location/Region[@Name='" + region + "']/Area[@Name='" + area + "']");

                    if (areaEle != null) {
                        Element siteEle = (Element) XPath.selectSingleNode(document, "/Location/Region[@Name='" + region + "']/Area[@Name='" + area + "']/Site[@Name='" + site + "']");

                        if (siteEle != null) {
                            Element netEle = new Element("Net");
                            netEle.setText(netValue);

                            siteEle.addContent(netEle);
                        } else {
                            siteEle = new Element("Site");
                            siteEle.setAttribute("Name", site);

                            areaEle.addContent(siteEle);

                            Element netEle = new Element("Net");
                            netEle.setText(netValue);

                            siteEle.addContent(netEle);
                        }
                    } else {
                        areaEle = new Element("Area");
                        areaEle.setAttribute("Name", area);

                        regionEle.addContent(areaEle);

                        Element siteEle = new Element("Site");
                        siteEle.setAttribute("Name", site);

                        areaEle.addContent(siteEle);

                        Element netEle = new Element("Net");
                        netEle.setText(netValue);

                        siteEle.addContent(netEle);
                    }
                } else {
                    regionEle = new Element("Region");
                    regionEle.setAttribute("Name", region);
                    regionEle.setAttribute("Edit", "true");

                    locationEle.addContent(regionEle);

                    Element areaEle = new Element("Area");
                    areaEle.setAttribute("Name", area);

                    regionEle.addContent(areaEle);

                    Element siteEle = new Element("Site");
                    siteEle.setAttribute("Name", site);

                    areaEle.addContent(siteEle);

                    Element netEle = new Element("Net");
                    netEle.setText(netValue);

                    siteEle.addContent(netEle);
                }
            }
        }

        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
        format.setIndent("    ");
        XMLOutputter xmlout = new XMLOutputter(format);
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        xmlout.output(document, bo);

        return bo.toString();
    }

    /**
     * IP数据信息
     *
     * @param excelFileStream
     * @return
     * @throws IOException
     */
    private static Map<String, String> getAllRegionInfo(InputStream excelFileStream) throws IOException {
        //key: 省市县 value:开始－结束IP
        Map<String, String> reginIpMap = new HashMap<String, String>();
        Map<String, List<String>> reginIpListMap = new HashMap<String, List<String>>();

        Workbook book = null;
        book = getExcelWorkbook(excelFileStream);
        Sheet sheet = getSheetByNum(book, 0);

        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            Row row = null;
            row = sheet.getRow(i);
            if (row != null) {
                int lastCellNum = row.getLastCellNum();
                Cell cell = null;

                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < lastCellNum - 1; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        String cellValue = cell.getStringCellValue();

                        sb.append(cellValue + "-");
                    }
                }

                String curIp = row.getCell(lastCellNum - 1).getStringCellValue();

                String region = sb.deleteCharAt(sb.length() - 1).toString();
                if (reginIpListMap.containsKey(region)) {
                    List<String> ips = reginIpListMap.get(region);

                    ips.add(curIp);
                } else {
                    List<String> ips = new ArrayList<String>();
                    ips.add(curIp);

                    reginIpListMap.put(region, ips);
                }

            }
        }

        Iterator<Map.Entry<String, List<String>>> iterator = reginIpListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> next = iterator.next();

            String key = next.getKey();
            List<String> value = next.getValue();

            if (value.size() > 0) {
                if (value.size() == 1) {
                    if (value.get(0).contains("/")) {
                        reginIpMap.put(key, value.get(0));
                    }
                } else {
                    try {
                        String subnetInfo = IPUtils.getSubnetInfo(value.get(0), value.get(value.size() - 1));
                        reginIpMap.put(key, subnetInfo);
                    } catch (Exception e) {
                    }
                }
            }
        }

        return reginIpMap;
    }

    public static Sheet getSheetByNum(Workbook book, int number) {
        Sheet sheet = null;
        try {
            sheet = book.getSheetAt(number);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheet;
    }


    public static Workbook getExcelWorkbook(InputStream inputStream) throws IOException {
        Workbook book = null;

        try {
            book = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return book;
    }

}
