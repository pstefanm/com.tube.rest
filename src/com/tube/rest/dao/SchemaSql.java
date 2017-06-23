package com.tube.rest.dao;

import java.sql.Connection;
import java.sql.SQLType;

import org.codehaus.jettison.json.JSONArray;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tube.util.toJson;

public class SchemaSql extends SQLdbtube {

	public JSONArray returnPartBrands(String brand) throws Exception {

		PreparedStatement query = null;
		Connection connection = null;

		toJson convertor = new toJson();
		JSONArray jArray = new JSONArray();

		try {

			connection = initDBConnection();
			query = connection.prepareStatement(
					"select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ "from PC_PARTS " + "where UPPER(PC_PARTS_MAKER) = ? ");
			query.setString(1, brand.toUpperCase()); // protect against sql
														// injection
			ResultSet rs = query.executeQuery();

			jArray = convertor.toJSON(rs);
			query.close(); // close connection

		} catch (SQLException SQLe) {
			SQLe.printStackTrace();
			return jArray;
		}

		catch (Exception e) {
			e.printStackTrace();
			return jArray;
		} finally {
			if (connection != null)
				connection.close();
		}

		return jArray;
	}

}
