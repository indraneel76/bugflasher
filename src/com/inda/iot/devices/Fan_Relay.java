package com.inda.iot.devices;

import java.io.IOException;
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

@Path("/fanrelay")
public class Fan_Relay extends Device {

	public static boolean doRun =false;
	
	private static final Logger logger = Logger.getLogger(Fan_Relay.class.getName());
	
	@GET
	@Path("/senddata/{params}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sendData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("params") String params)throws IOException, ParseException {
		
		String output="{\"status\":\""+doRun+"\"}" ;
		return Response.status(Status.OK).entity(output).build();
	}
	
	
	
	@GET
	@Path("/getdata")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp) throws IOException, ParseException {
		// TODO Auto-generated method stub
		String output="{\"status\":\""+doRun+"\"}" ;
		return Response.status(Status.OK).entity(output).build();
	}
	@Override
	public void doAction() {
		
			
		
	}
	
	
	private void pushToQueue(String QueueName,int temperature)
	{
		try {
			 Pubsub client = PubsubUtils.getClient();
		String fullTopicName = String.format("projects/%s/topics/%s",
                 PubsubUtils.getProjectId(),QueueName);
		
		
		PubsubMessage pubsubMessage = new PubsubMessage();
		pubsubMessage.setData(String.valueOf(temperature));
        
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setMessages(ImmutableList.of(pubsubMessage));

        client.projects().topics()
                .publish(fullTopicName, publishRequest)
                .execute();

		
		 
		}
		catch (Exception e)
		{
			
		}
		 
		
	}
	
	
}
