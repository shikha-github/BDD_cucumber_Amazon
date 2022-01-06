package com.amazon.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParseDynamicJSON 
{
	// How to parse dynamic JSON?
		// How to parse nested JSON?
		// Code Explanation : https://www.youtube.com/watch?v=ZjZqLUGCWxo

		public static void parseObject(JSONObject json, String key) {
			System.out.println(json.get(key));
		}

		public static void getKey(JSONObject json, String key) {

			boolean exists = json.containsKey(key);
			Set<?> keySet;
			Iterator<?> keys;
			String nextKeys;

			if (!exists) {
				keySet = json.keySet();
				keys = keySet.iterator();
				while (keys.hasNext()) {
					nextKeys = (String) keys.next();
					try {

						if (json.get(nextKeys) instanceof JSONObject) {

							if (exists == false) {
								getKey((JSONObject) json.get(nextKeys), key);
							}

						} else if (json.get(nextKeys) instanceof JSONArray) {
							JSONArray jsonarray = (JSONArray) json.get(nextKeys);
							for (int i = 0; i < jsonarray.size(); i++) {
								String jsonarrayString = jsonarray.get(i).toString();
								JSONParser jsonParser = new JSONParser();  
								JSONObject innerJSOn = (JSONObject) jsonParser.parse(jsonarrayString);
								if (exists == false) {
									getKey(innerJSOn, key);
								}
							}

						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {
				parseObject(json, key);
			}

		}

		public static void main(String[] args) 
		{
			JSONObject jsonObj=null;
			JSONParser jsonParse=null;
			try(BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\com\\amazon\\testdata\\google_book_api.json")))
			{
				jsonParse = new JSONParser();
				Object obj = jsonParse.parse(reader);
				jsonObj = (JSONObject) obj;
				//System.out.println(jsonObj.toString());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			String inputJson = "{\n" + 
					"  \"myObjects\": [\n" + 
					"    {\n" + 
					"      \"code\": \"PQ\",\n" + 
					"      \"another_objects\": [\n" + 
					"        {\n" + 
					"          \"attr1\": \"value1\",\n" + 
					"          \"attr2\": \"value2\",\n" + 
					"          \"attrN\": \"valueN\"\n" + 
					"        },\n" + 
					"        {\n" + 
					"          \"attr1\": \"value1\",\n" + 
					"          \"attr2\": \"value2\",\n" + 
					"          \"attrN\": \"valueN\"\n" + 
					"        }\n" + 
					"      ]\n" + 
					"    }\n" + 
					"  ]\n" + 
					"}";
			JSONObject inputJSONOBject = null;
			try {
				jsonParse = new JSONParser();
				inputJSONOBject = (JSONObject) jsonParse.parse(inputJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			getKey(jsonObj, "description");

		}
}
