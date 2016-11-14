package com.javazyw.zk.util;

import com.google.common.base.Strings;

/**
 * Created by lilizhao on 16-11-11.
 */
public class IPUtils {
    // 将127.0.0.1 形式的IP地址转换成10进制整数，这里没有进行任何错误处理
    public static long ipToLong(String strIP) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIP.substring(0, position1));
        ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIP.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    // 将10进制整数形式转换成127.0.0.1形式的IP地址
    public static String longToIP(long longIP) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf(longIP >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(longIP & 0x000000FF));
        return sb.toString();
    }

    /**
     * 根据IP和掩码,查询隶属的子网
     *
     * @param StartIP
     * @param netmask
     * @return
     */
    public static String getSubNet(String StartIP, String netmask) {
        String[] temp1 = StartIP.trim().split("\\.");
        String[] temp2 = netmask.trim().split("\\.");
        int[] rets = new int[4];
        for (int i = 0; i < 4; i++) {
            rets[i] = Integer.parseInt(temp1[i]) & Integer.parseInt(temp2[i]);
        }
        return rets[0] + "." + rets[1] + "." + rets[2] + "." + rets[3];
    }

    /**
     * 根据掩码位获取掩码
     *
     * @param mask
     * @return
     */
    public static String getNetMask(int mask) {
        StringBuilder sb = new StringBuilder();
        StringBuilder ipSb = new StringBuilder();

        int i = 1;
        while (i <= 32) {
            if (mask > 0) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            if (i % 8 == 0) {
                ipSb.append(Integer.valueOf(sb.toString(), 2));
                ipSb.append(".");

//                System.out.println(sb);
                sb.delete(0, sb.length());
            }
            mask = mask - 1;
            i++;
        }

        ipSb.deleteCharAt(ipSb.length() - 1);

        return ipSb.toString();
    }

    /**
     * 得到公共前缀
     *
     * @param ip1
     * @param ip2
     * @return
     */
    public static String getSubnetInfo(String ip1, String ip2) {
        String ip1Bin = Long.toBinaryString(ipToLong(ip1));
        String fullIp1 = String.format("%32s", ip1Bin).replace(" ", "0");

        String ip2Bin = Long.toBinaryString(ipToLong(ip2));
        String fullIp2 = String.format("%32s", ip2Bin).replace(" ", "0");

        String commonPrefix = Strings.commonPrefix(fullIp1, fullIp2);
        String subnetInfo = getSubNet(ip1, getNetMask(commonPrefix.length())) + "/" + commonPrefix.length();

        return subnetInfo;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String ip1 = "10.75.36.67";
        String ip2 = "10.75.36.127";

        System.out.println(getSubnetInfo(ip1, ip2));
    }


}
