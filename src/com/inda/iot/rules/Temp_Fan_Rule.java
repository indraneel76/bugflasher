package com.inda.iot.rules;

import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Logger;


import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.kie.api.KieServices;


import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.inda.iot.devices.TempSensor;
import com.inda.iot.resource.ControlDevice;

public class Temp_Fan_Rule {
	
	private static final Logger logger = Logger.getLogger(Temp_Fan_Rule.class.getName());
	 private String[] files = {"rules/Sample.drl"};
	 
	 private static KnowledgeBase base;
	 
	public  void invoke(TempSensor tempSensor)
	{
		logger.info("Temperature passed = "+tempSensor.temperature);
		System.out.println("TEMPERATURE ========");
		try
		{
			 
			Utility utility = new Utility();
			KnowledgeBase base = utility.getKnowledgeBase();
			StatefulKnowledgeSession kSession = base.newStatefulKnowledgeSession();
			
			    if (kSession==null)
					 logger.info("kSession  is null");
	         
			    kSession.insert(tempSensor);
			    kSession.fireAllRules();
			    kSession.dispose();
			    
	         
			 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	 private  KnowledgeBuilder getKnowledgeBuilder() {
		    KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		    for(String file : files) {
		      InputStream input = getClass().getClassLoader().getResourceAsStream(file);
		      Resource resource = ResourceFactory.newInputStreamResource(input);
		      builder.add(resource, ResourceType.DRL);
		    }
		    if(builder.hasErrors())
		      System.out.println(builder.getErrors().toString());
		    return builder;
		  }
		}


