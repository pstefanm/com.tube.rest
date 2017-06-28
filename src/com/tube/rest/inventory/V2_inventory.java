package com.tube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.tube.rest.dao.SchemaSql;

@Path("/v2/inventory")
public class V2_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {

		JSONArray jArray = new JSONArray();
		String returnString = null;

		try {

			if (brand == null) {
				return Response.status(400).entity("ERROR: Please specify the brand for this search").build();
			}
			SchemaSql sql = new SchemaSql();
			jArray = sql.returnPartBrands(brand);

			returnString = jArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();

	}
/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnErrorOnBrand() throws Exception {

		return Response.status(400).entity("Error: Cannot process: no brand mentione").build();
	}
*/
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{brand}")
	public Response returnBrand(@PathParam("brand") String brand) throws Exception {

		JSONArray jArray = new JSONArray();
		String returnString = null;

		try {

			if (brand == null) {
				return Response.status(400).entity("ERROR: Server was not able to process your request").build();
			}
			SchemaSql sql = new SchemaSql();
			jArray = sql.returnPartBrands(brand);

			returnString = jArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{brand}/{item_code}")
	public Response returnBrandItemCode(@PathParam("brand") String brand, @PathParam("item_code") String item_code) throws Exception {

		JSONArray jArray = new JSONArray();
		String returnString = null;

		try {

			if (brand == null) {
				return Response.status(400).entity("ERROR: Server was not able to process your request").build();
			}
			SchemaSql sql = new SchemaSql();
			jArray = sql.returnPartBrandsItem(brand, item_code);

			returnString = jArray.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();

	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {
		
		//JSONArray jArray = new JSONArray();
		String returnString = null;
		
		try {
			
			System.out.println("Incoming Data: " + incomingData);
			
			SchemaSql sql = new SchemaSql();
			ObjectMapper mapper = new ObjectMapper();
			
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int http_code = sql.insertIntoPC_PARTS(itemEntry.PC_PARTS_TITLE,
													itemEntry.PC_PARTS_CODE,
													itemEntry.PC_PARTS_MAKER,
													itemEntry.PC_PARTS_AVAIL,
													itemEntry.PC_PARTS_DESC);
			
			if (http_code == 200) {
				// returnString = jArray.toString();
				returnString = "Item Inserted.";
			}else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}

}

class ItemEntry{
	
	public String PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC;
	
}
