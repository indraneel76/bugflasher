package com.inda.iot.devices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.common.collect.ImmutableList;
import com.inda.iot.Device;

import com.inda.iot.rules.Utility;
import com.inda.iot.utils.PubsubUtils;

@Path("/light")
public class LightSensor extends Device {

	public int light ;
	
	private static final Logger logger = Logger.getLogger(LightSensor.class.getName());
	
	@GET
	@Path("/senddata/{params}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sendData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("params") String params)throws IOException, ParseException {
		light=Integer.parseInt(params);
		doAction();
		String output="{\"status\":\"light received\"}" ;
		return Response.status(Status.OK).entity(output).build();
	}
	
	
	
	@GET
	@Path("/getdata")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp) throws IOException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void doAction() {
		
		try
		{
			logToThinkSpeak(); 
			
			Utility utility = new Utility();
			KnowledgeBase base = utility.getKnowledgeBase();
			StatefulKnowledgeSession kSession = base.newStatefulKnowledgeSession();
			
			    if (kSession==null)
					 logger.info("kSession  is null");
	         
			    kSession.insert(this);
			    kSession.fireAllRules();
			    kSession.dispose();
			    
	         
			 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	
	private void logToThinkSpeak()
	{
		try {
		String url = "https://api.thingspeak.com/update?api_key=LLU58GK07L4GMA57&field1="+light;
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		logger.info("response code from thinkspeak = "+responseCode );
		}
		catch (Exception e)
		{
			logger.info("Exception occurred in logToThinkSpeak");
			e.printStackTrace();
		}
	}
	

	
		
}
