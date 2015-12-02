package io.pivotal.fe.demo.guestbook.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
	
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudException;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ServiceController {

	private Cloud cloud;

	@RequestMapping(value = "/cloudinfo", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getCloudInfo(@RequestHeader ("host") String hostName) {
		String properties = "";
		ApplicationInstanceInfo cloudInfo = null;
		try {
			cloud = new CloudFactory().getCloud();
			cloudInfo = cloud.getApplicationInstanceInfo();
		} catch (CloudException e) {
			//e.printStackTrace();
			System.out.println("No Cloud found ... continuing ...");
			//return properties;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, Object> props = new HashMap<String, Object>();
			if (cloudInfo != null) {
				props.putAll(cloudInfo.getProperties());
			}
			props.put("host_name", hostName);
			props.put("ip", InetAddress.getLocalHost().getHostAddress());
			properties = mapper.writeValueAsString(props);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		return properties;
	}
	
    @RequestMapping(value="/killApp", method = RequestMethod.GET)
    @ResponseBody
    public String kill(){
		System.exit(-1);    	
    	return "Killed";

    }  

}
