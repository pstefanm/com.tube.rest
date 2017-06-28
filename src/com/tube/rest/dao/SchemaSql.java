package com.tube.rest.dao;

import java.sql.Connection;
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

	public JSONArray returnPartBrandsItem(String brand, String item_code) throws Exception {

		PreparedStatement query = null;
		Connection connection = null;

		toJson convertor = new toJson();
		JSONArray jArray = new JSONArray();

		try {

			connection = initDBConnection();
			query = connection.prepareStatement(
					"select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
							+ "from PC_PARTS " + "where UPPER(PC_PARTS_MAKER) = ? " + "AND PC_PARTS_CODE = ?");

			// protect against sql injection
			query.setString(1, brand.toUpperCase());
			query.setString(2, item_code);
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
	
	/**
	 * This method will insert a record into the PC_PARTS table. 
	 * 
	 * Note: there is no validation being done... if this was a real project you
	 * must do validation here!
	 * 
	 * @param PC_PARTS_TITLE
	 * @param PC_PARTS_CODE
	 * @param PC_PARTS_MAKER
	 * @param PC_PARTS_AVAIL - integer column
	 * @param PC_PARTS_DESC
	 * @return integer 200 for success, 500 for error
	 * @throws Exception
	 */
	
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, String PC_PARTS_CODE, String PC_PARTS_MAKER,
			String PC_PARTS_AVAIL, String PC_PARTS_DESC) throws Exception {

		PreparedStatement query = null;
		Connection connection = null;
		
		try {

			/*
			 * If this was a real application, you should do data validation here
			 * before starting to insert data into the database.
			 * 
			 * Important: The primary key on PC_PARTS table will auto increment.
			 * 		That means the PC_PARTS_PK column does not need to be apart of the 
			 * 		SQL insert query below.
			 */
			
			connection = initDBConnection();
			query = connection.prepareStatement(
					"INSERT INTO PC_PARTS" + "(PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC)"
							+ "VALUES (?, ?, ?, ?, ?)");
			
			query.setString(1, PC_PARTS_TITLE);
			query.setString(2, PC_PARTS_CODE);
			query.setString(3, PC_PARTS_MAKER);
			
			//PC_PARTS_AVAIL is a number column, so we need to convert the String into a integer
			int pc_parts_avail = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(4, pc_parts_avail);
			
			query.setString(5, PC_PARTS_DESC);
			
			query.executeUpdate();  //note the new command for insert statement
			

		} catch (Exception e) {
			e.printStackTrace();
			return 500; //if a error occurs, return a 500
		}finally {
			if (connection != null)  connection.close();
		}

		return 200; // SUCCESS
	}
}