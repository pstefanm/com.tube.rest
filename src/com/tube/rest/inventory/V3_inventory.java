package com.tube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	
	/**
	 * This method will allow you to update data in the PC_PARTS table.
	 * In this example we are using both PathParms and the message body (payload).
	 * 
	 * @param brand
	 * @param item_code
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@PUT
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateItem(@PathParam("brand") String brand,
									@PathParam("item_number") String item_code,
									String incomingData) 
								throws Exception {
		
		System.out.println("incomingData: " + incomingData);
		System.out.println("brand: " + brand);
		System.out.println("item_number: " + item_code);
		
		int pk;
		int avail;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		SchemaSql sql = new SchemaSql();
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData); //we are using json objects to parse data
			pk = partsData.optInt("PC_PARTS_PK", 0);
			avail = partsData.optInt("PC_PARTS_AVAIL", 0);
			
			//call the correct sql method
			http_code = sql.updatePC_PARTS(pk, avail);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been updated successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	/**
	 * This method will allow you to delete data in the PC_PARTS table.
	 * 
	 * We really only need the primary key from the message body but I kept
	 * the same URL path as the update (PUT) to let you see that we can use the same
	 * URL path for each http method (GET, POST, PUT, DELETE, HEAD)
	 * 
	 * @param brand
	 * @param item_code
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/{brand}/{item_number}")
	@DELETE
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteItem(@PathParam("brand") String brand,
									@PathParam("item_number") String item_code,
									String incomingData) 
								throws Exception {
		
		System.out.println("incomingData: " + incomingData);
		System.out.println("brand: " + brand);
		System.out.println("item_number: " + item_code);
		
		int pk;
		int http_code;
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		SchemaSql sql = new SchemaSql();
		
		try {
			
			JSONObject partsData = new JSONObject(incomingData);
			pk = partsData.optInt("PC_PARTS_PK", 0);
			
			http_code = sql.deletePC_PARTS(pk);
			
			if(http_code == 200) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item has been deleted successfully");
			} else {
				return Response.status(500).entity("Server was not able to process your request").build();
			}
			
			returnString = jsonArray.put(jsonObject).toString();
			
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}

}