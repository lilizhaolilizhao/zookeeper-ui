package com.javazyw.zk.controller;

import com.javazyw.zk.util.ClientFactory;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


/**
 * @author dongdehui
 * @date 2015年9月14日 下午3:57:56
 */
@Controller
public class DiscoveryController {


    private static final ServiceDiscovery<InstanceDetails> serviceDiscovery;

    static {
        JsonInstanceSerializer<InstanceDetails> serializer = new JsonInstanceSerializer<InstanceDetails>(InstanceDetails.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(InstanceDetails.class)
                .client(ClientFactory.getInstance())
                .basePath("services")
                .serializer(serializer)
                .build();

        try {
            serviceDiscovery.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "discovery")
    public ModelAndView discovery() throws Exception {
        System.out.println("discovery====");

        Collection<ServiceInstance<InstanceDetails>> is = serviceDiscovery.queryForInstances("testService");
        List<String> r = new ArrayList<String>();
        for (ServiceInstance<InstanceDetails> serviceInstance : is) {
            System.out.println(serviceInstance.buildUriSpec());
            System.out.println(serviceInstance.getPayload());
            r.add(serviceInstance.buildUriSpec());
        }

        return new ModelAndView("discovery", "list", r);
    }

    @ResponseBody
    @RequestMapping(value = "publish")
    public String publish(String ip) throws Exception {

        Collection<ServiceInstance<InstanceDetails>> is = serviceDiscovery.queryForInstances("testService");
        for (ServiceInstance<InstanceDetails> serviceInstance : is) {
            serviceDiscovery.unregisterService(serviceInstance);
        }

        ServiceInstance<InstanceDetails> instance1 = ServiceInstance.<InstanceDetails>builder()
                .name("testService")
                .port(12345)
                .address("192.168.1.101")
                .payload(new InstanceDetails(UUID.randomUUID().toString(), "192.168.1.101", 12345, "Test.instance1"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceDiscovery.registerService(instance1);

        ServiceInstance<InstanceDetails> instance2 = ServiceInstance.<InstanceDetails>builder()
                .name("testService")
                .port(12345)
                .address("192.168.1.102")
                .payload(new InstanceDetails(UUID.randomUUID().toString(), "192.168.1.102", 12345, "Test.instance2"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceDiscovery.registerService(instance2);


        ServiceInstance<InstanceDetails> instance3 = ServiceInstance.<InstanceDetails>builder()
                .name("testService")
                .port(12345)
                .address("192.168.1.103")
                .payload(new InstanceDetails(UUID.randomUUID().toString(), "192.168.1.103", 12345, "Test.instance3"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceDiscovery.registerService(instance3);

        return "Y";
    }

}
