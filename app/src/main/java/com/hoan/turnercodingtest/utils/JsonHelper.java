package com.hoan.turnercodingtest.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JsonHelper
{
	private JsonHelper() {

	}
	
	public static JSONObject createJSONObject(String serverResponse)
	{
		try
		{
			return new JSONObject(serverResponse);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static double getDouble(JSONObject jsonObject, String key)
	{
		try
		{
			return jsonObject.getDouble(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static long getLong(JSONObject jsonObject, String key)
	{
		try
		{
			return jsonObject.getLong(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}

		return -1;
	}

	public static JSONArray getJSONArray(JSONObject object, String key)
	{
		try
		{
			return object.getJSONArray(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static JSONObject getJSONObject(JSONObject container, String key)
	{
		try
		{
			return container.getJSONObject(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return null;

	}
	
	public static String getString(JSONArray array, int index, String key)
	{
		try
		{
			String value = array.getJSONObject(index).getString(key);
			if (value.equals("") || value.equals("null"))
			{
				value = null;
			}
			return value;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String toString(Object object, boolean indent)
	{
		try
		{
			if (object instanceof JSONObject)
			{
				if (indent)
				{
					return ((JSONObject) object).toString(4);
				}
				return ((JSONObject) object).toString();
			}
			if (indent)
			{
				return ((JSONArray) object).toString(4);	
			}
			return ((JSONArray) object).toString();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
