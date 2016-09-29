package com.javazyw.zk.util;

import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ClientFactory {

    private static CuratorFramework client;

    public static CuratorFramework getInstance() {

        if (client != null) {
            if (client.getState().compareTo(CuratorFrameworkState.STARTED) != 0) {
                client.start();
            }
            return client;
        } else {
            client = newClient();
            client.start();
            return client;
        }
    }

    private static CuratorFramework newClient() {
        //String connectionString = "192.168.11.56:2180,192.168.11.56:2181,192.168.11.56:2182";
        /*String connectionString = "localhost:2182";
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);*/

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ResourcesUtils.bundle.getString("connectString"))
                .sessionTimeoutMs(10000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                        //.namespace("zk")
                        //.defaultData(null) .authorization("digest", "admin:admin123321".getBytes())
                .build();
        return client;
        /*client.start();*/

    }

    public static CuratorFramework newClientAcl() {
        String up = "";
        try {
            up = DigestAuthenticationProvider.generateDigest("admin:admin123321");
            System.out.println(up);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        AuthInfo authinfo = new AuthInfo("digest", "admin:admin123321".getBytes());
        List<AuthInfo> aList = new ArrayList<AuthInfo>();
        aList.add(authinfo);

        // -Dzookeeper.DigestAuthenticationProvider.superDigest=admin:XNayzH0KHS9YkzwRXa0HNAvPPMc=
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.1.5:2181")
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace("two")
                .defaultData(null)
                .authorization("digest", "admin:admin123321".getBytes())
                        //.authorization(aList)
                .build();
        return client;
		/*client.start();*/

    }


    public static void main(String[] args) {
        try {
            String digest = DigestAuthenticationProvider.generateDigest("admin:admin123321");
            System.out.println(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
