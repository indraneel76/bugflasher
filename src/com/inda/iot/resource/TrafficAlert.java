package com.inda.iot.resource;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.DateTime;

import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.PublishRequest;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import com.inda.iot.utils.PubsubUtils;
import com.google.common.collect.ImmutableList;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;




@Path("/trafficalert")
public class TrafficAlert {
	
	private static final Logger logger = Logger.getLogger(TrafficAlert.class.getName());

	@GET
	@Path("/START/{origin}/{destination}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response startDeviceRest(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp,
			
			@PathParam("origin") String origin,
			@PathParam("destination") String destination
			
			) throws IOException, ParseException {
		logger.info("Inside POST request for trafficlaert.");
		
		
		long durationInMins = getTrafficDuration(origin,destination);
		String output="{\"duration\":\""+durationInMins+"\"}" ;
			return Response.status(Status.OK).entity(output).build();
		}
	
	
	long getTrafficDuration(String origin,String destination)
	{
		
		long durationInMins =0 ;  
		try
		   {
		    logger.info("inside getTrafficDuration ") ;
		    
		    GeoApiContext geoApiContext = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyAAV1QWrGTz9YGcKiN51y5boAp55hu2ono") ;
		    
		    String[] origins = new String[1];
		    origins[0]=origin ;
		    
		    String[] destinations = new String[1];
		    destinations[0]=destination ;
		    
		    DistanceMatrixApiRequest distanceMatrixApiRequest = DistanceMatrixApi.getDistanceMatrix(geoApiContext,
		    		origins,destinations);
                    
		    org.joda.time.DateTime departureTime = new DateTime();
		    departureTime=  departureTime.plusMinutes(10);
		    		logger.info("Time = "+departureTime);
		   
		    distanceMatrixApiRequest= distanceMatrixApiRequest.departureTime(departureTime);
		    
		    DistanceMatrix distanceMatrix = distanceMatrixApiRequest.await() ;
		    
		   DistanceMatrixRow[] distanceMatrixrows = distanceMatrix.rows ;
		   for (DistanceMatrixRow distanceMatrixrow :distanceMatrixrows)
		   {
			   DistanceMatrixElement[] elements = distanceMatrixrow.elements ;
			   
			   for (DistanceMatrixElement element: elements )
			   {
				   com.google.maps.model.Duration  duration  = element.durationInTraffic ;
				   durationInMins=Math.round(duration.inSeconds/60) ;
				   logger.info("Duration = "+duration.inSeconds);
				   logger.info("Duration humanReadable = "+duration.humanReadable);
			   }
			   
		   }
		   }
		   catch (Exception e)
		   {
			   logger.info("Exception occurred inside executeCommand "+e) ;
			   e.printStackTrace();
		   }
		
		return durationInMins ;
	}
	
	
	@GET
	@Path("/e/{deviceid}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response endDeviceRest(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp,
			
			@PathParam("deviceid") String deviceid
			
			) throws IOException, ParseException {
		logger.info("Inside POST request for endDeviceRest.");
		
		logger.info("deviceid=" + deviceid);
		endDevice(deviceid);
		String output="device stopped" ;
			return Response.status(Status.OK).entity(output).build();
		}
	
	
	void endDevice(String deviceid)
	{
		
		   
		    logger.info("inside endsDevice with deviceid "+deviceid) ;
	}
	

}
