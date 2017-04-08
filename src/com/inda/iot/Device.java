package com.inda.iot;

import java.io.IOException;
import java.text.ParseException;

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

public abstract class Device {
	String name;
	String type;
	public abstract Response sendData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp,@PathParam("params") String params) throws IOException, ParseException;
	
	public abstract Response getData(@Context HttpServletRequest req, 
			@Context HttpServletResponse resp) throws IOException, ParseException;
	public abstract void doAction();
	
	
	
	
	

}
