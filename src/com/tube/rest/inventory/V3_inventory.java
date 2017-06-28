package com.tube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.tube.rest.dao.SchemaSql;

@Path("/v3/inventory")
public class V3_inventory {

	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcPart(String incomingData) throws Exception {
		
		JSONArray jArray = new JSONArray();
		JSONObject jsObj = new JSONObject();
		String returnString = null;
		
		try {
			JSONObject inData = new JSONObject(incomingData);
			
			System.out.println("Incoming Data: " + inData.toString());
			
			SchemaSql sql = new SchemaSql();
						
			int http_code = sql.insertIntoPC_PARTS(inData.optString("PC_PARTS_TITLE"),
													inData.optString("PC_PARTS_CODE"),
													inData.optString("PC_PARTS_MAKER"),
													inData.optString("PC_PARTS_AVAIL"),
													inData.optString("PC_PARTS_DESC"));
			
			if (http_code == 200) {
				// returnString = jArray.toString();
				jsObj.put("HTTP_CODE", 200);
				jsObj.put("MSG", "Item has been entered successfully, Version 3");
				returnString = jArray.put(jsObj).toString();
			}else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
			System.out.println("returnString: " + returnString);
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}

}