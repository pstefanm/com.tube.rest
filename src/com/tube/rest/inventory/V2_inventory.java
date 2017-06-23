package com.tube.rest.inventory;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

@Path("/v2/inventory")
public class V2_inventory {

	
	public Response returnBrandParts() throws Exception{
		
		Response response = null;
		JSONArray jArray = new JSONArray();
		
		try {
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		
		return response;
		
		
		
	}
	
}
