package com.tube.util;

import java.sql.ResultSet;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class toJson {

	public static JSONArray toJSON(ResultSet rs) throws Exception {

		// we need an array(that will be returned) to store all JSON objects
		JSONArray jArr = new JSONArray();
		String temp = null;

		try {

			// we will need the number of columns and the columns names which
			// are contained in the metadata
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();

			// pass trough each row in the result set and convert it to JSON
			while (rs.next()) {

				// get the total number of columns
				int noColumns = rsmd.getColumnCount();

				// each row in the result set is converted in a JSON object
				JSONObject jObj = new JSONObject();

				// second loop will iterate trough each column and place the
				// value in the JSON object
				for (int i = 1; i < noColumns + 1; i++) {
					String columnName = rsmd.getColumnName(i);

					if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
						jObj.put(columnName, rs.getArray(columnName));
						/* Debug */ System.out.println("toJSON: ARRAY");
					} else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
						jObj.put(columnName, rs.getInt(columnName));
						/* Debug */ System.out.println("toJSON: BIGINT");
					} else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
						jObj.put(columnName, rs.getBoolean(columnName));
						/* Debug */ System.out.println("toJSON: BOOLEAN");
					} else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
						jObj.put(columnName, rs.getBlob(columnName));
						/* Debug */ System.out.println("ToJson: BLOB");
					} else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
						jObj.put(columnName, rs.getDouble(columnName));
						/* Debug */ System.out.println("ToJson: DOUBLE");
					} else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
						jObj.put(columnName, rs.getFloat(columnName));
						/* Debug */ System.out.println("ToJson: FLOAT");
					} else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
						jObj.put(columnName, rs.getInt(columnName));
						/* Debug */ System.out.println("ToJson: INTEGER");
					} else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
						jObj.put(columnName, rs.getNString(columnName));
						/* Debug */ System.out.println("ToJson: NVARCHAR");
					} else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {

						temp = rs.getString(columnName);
						// saving column data
						/*
						 * // to temp variable temp =
						 * ESAPI.encoder().canonicalize(temp); // decoding //
						 * data to // base // state temp =
						 * ESAPI.encoder().encodeForHTML(temp); // encoding //
						 * to be // browser // safe
						 */ jObj.put(columnName, temp); // putting data into JSON
														// object

						// obj.put(column_name, rs.getString(column_name));
						// /*Debug*/ System.out.println("ToJson: VARCHAR");
					} else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
						jObj.put(columnName, rs.getInt(columnName));
						/* Debug */ System.out.println("ToJson: TINYINT");
					} else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
						jObj.put(columnName, rs.getInt(columnName));
						/* Debug */ System.out.println("ToJson: SMALLINT");
					} else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
						jObj.put(columnName, rs.getDate(columnName));
						/* Debug */ System.out.println("ToJson: DATE");
					} else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
						jObj.put(columnName, rs.getTimestamp(columnName));
						/* Debug */ System.out.println("ToJson: TIMESTAMP");
					} else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
						jObj.put(columnName, rs.getBigDecimal(columnName));
						// /*Debug*/ System.out.println("ToJson: NUMERIC");
					} else {
						jObj.put(columnName, rs.getObject(columnName));
						/* Debug */ System.out.println("ToJson: Object " + columnName);
					}
				} // end for

				jArr.put(jObj);

			} // end while
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jArr;

	}

}
