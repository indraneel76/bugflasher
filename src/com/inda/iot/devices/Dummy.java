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



@Path("/dummy")
public class Dummy {

	public static String value="undefined" ; 
	private static final Logger logger = Logger.getLogger(Dummy.class.getName());
	
	
	@GET
	@Path("/getdata")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response sendData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp
			)throws IOException, ParseException {
		
		
		String output="{\"status\":\""+value+"\"}" ;
		return Response.status(Status.OK).entity(output).build();
	}
	
	
	
	public static void setHigh()
	{
		logger.info("Dummy setHigh called");
		value="High";
	}
	
	public static void setLow()
	{
		logger.info("Dummy setLow called");
		value="Low";
	}

}
