package com.tube.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.tube.rest.dao.SQLdbtube;
import com.tube.util.toJson;

@Path("/v1/inventory")
public class V1_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnAllPcParts() throws Exception {

		PreparedStatement query = null;
		Connection connection = null;
		String returnString = null;

		try {
			connection = SQLdbtube.initDBConnection();
			query = connection.prepareStatement("select * from PC_PARTS;");

			ResultSet rs = query.executeQuery();

			// create an instance of the utility class in which the convert
			// method resides
			toJson convertor = new toJson();

			// create a JSON Array to store the results
			JSONArray json = new JSONArray();

			json = toJson.toJSON(rs);

			query.close();

			returnString = json.toString();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			//if (connection != null)
				//connection.close();
		}

		return returnString;
	}

}
