package com.inda.iot.rules;

import java.io.InputStream;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;

public class Utility {
	
	
	 private static String[] files = {"rules/Sample.drl"};
	 private static KnowledgeBase base;
	 
	 public  KnowledgeBase getKnowledgeBase()
	 {
		 if (base !=null)
			 return base ;
		 
		 System.setProperty("user.home", ".");
		    base = KnowledgeBaseFactory.newKnowledgeBase();
		    base.addKnowledgePackages(this.getKnowledgeBuilder().getKnowledgePackages());
		    return base ;
		    
		 
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
