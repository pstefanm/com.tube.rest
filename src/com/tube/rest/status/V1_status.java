package com.tube.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;

import com.tube.rest.dao.*;

@Path("/v1")
public class V1_status {

	private static final String api_version = "00.10.00";

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

	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDbConStatus() throws Exception {

		PreparedStatement query = null;
		String myString = null;
		String status = null;
		Connection connection = null;
		ResultSet rs = null;

		try {

			connection = SQLdbtube.initDBConnection();
			query = connection.prepareStatement("SELECT NOW() FROM DUAL;");
			rs = query.executeQuery();

			while (rs.next()) {
				myString = rs.getString("NOW()");
			}

			query.close(); // close the connection

			status = "<h3>Database Status:\n</h3>" + "<p> Database Date/Time: " + myString + "</p>";

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.close();

		}

		return status;

	}

}
