package com.tube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1")
public class V1_status {

	private static final String api_version= "00.10.00";
	
	@Path("/status")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle() {
		return "<h3>Java Web Service Status</h3>" + "\n<p>Service up and running</>";
	}
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<h3>Java Web Service Version: </h3>" + "<p>" + api_version + "</p>";
	}

}
