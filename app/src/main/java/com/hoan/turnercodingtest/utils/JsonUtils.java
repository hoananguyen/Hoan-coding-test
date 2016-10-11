package com.hoan.turnercodingtest.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("nls")
public final class JsonUtils
{
	public static final int TYPE_JSONARRAY = 1;
	public static final int TYPE_JSONOBJECT = 2;
	
	private JsonUtils() {

	}
	
	public static boolean containValue(JSONArray array, String value)
	{
		int length = array == null ? 0 : array.length();
		for (int i = 0; i < length; i++)
		{
			String arrayValue = array.optString(i).toLowerCase().replace(",", "");
			if (arrayValue.contains(value.toLowerCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	public static int containValue(JSONArray array, String key, String value)
	{
		int length = array == null ? 0 : array.length();
		try
		{
			for (int i = 0; i < length; i++)
			{
				JSONObject object = array.getJSONObject(i);
				if (object.optString(key).equalsIgnoreCase(value))
				{
					return i;
				}
			}
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
	
	public static JSONArray createJSONArray(String serverResponse)
	{
		try
		{
			return new JSONArray(serverResponse);
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
	
	public static boolean getBoolean(JSONObject container, String key)
	{
		try
		{
			return container.getBoolean(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		return false;
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

	public static int getInt(JSONArray array, int index, String key)
	{
		try
		{
			return array.getJSONObject(index).getInt(key);
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
	
	public static int getInt(JSONObject object, String key)
	{
		int value = 0;
		try
		{
			value = object.getInt(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static int getInt(JSONObject object, String innerObjectKey, int index, String key)
	{
		int value = -1;
		try
		{
			value = object.getJSONArray(innerObjectKey).getJSONObject(index).getInt(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static int getInt(JSONObject object, String innerObjectKey, String key)
	{
		int value = -1;
		try
		{
			value = object.getJSONObject(innerObjectKey).getInt(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static JSONArray getJSONArray(JSONArray array, int index, String key)
	{
		try
		{
			JSONArray jsonArray = array.getJSONObject(index).getJSONArray(key);
			return (jsonArray.length() == 0) ? null : jsonArray;
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
	
	public static JSONArray getJSONArray(String json, int jsonType, List<String> keys)
	{
		try
		{
			return (JSONArray) getObject(json, jsonType, keys);
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONArray getJSONArray(String response, String key)
	{
		try
		{
			JSONArray array = new JSONObject(response).getJSONArray(key);
			if (array.length() == 0) array = null;
			return array;
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
	
	public static JSONObject getJSONObject(JSONArray array, int index, String key)
	{
		try
		{
			return array.getJSONObject(index).getJSONObject(key);
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
	
	public static List<String> getListString(JSONArray arrays, String containerKey, String key)
	{
		try
		{
			int arraysLength = (arrays == null) ? 0 : arrays.length();
			List<String> result = new ArrayList<String>();
			for (int i = 0; i < arraysLength; i++)
			{
				JSONObject object = getJSONObject(arrays, i, containerKey);
				result.add(object.getString(key));
			}
			return result.isEmpty() ? null : result;
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getObject(Object object, List<String> keys)
	{
		Object result = null;
		try
		{
			if (object instanceof JSONObject)
			{
				result = ((JSONObject) object).get(keys.get(0));
			}
			else
			{
				result = ((JSONArray) object).get(Integer.valueOf(keys.get(0)));
			}
			for (int i = 1; i < keys.size(); i++)
			{
				if (result instanceof JSONObject)
				{
					result = ((JSONObject) result).get(keys.get(i));
				}
				else
				{
					result = ((JSONArray) result).get(Integer.valueOf(keys.get(i)));
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object getObject(String json, int jsonType, List<String> keys)
	{
		try
		{
			Object jsonObject = (jsonType == TYPE_JSONOBJECT) ? new JSONObject(json) : new JSONArray(json);
			return getObject(jsonObject, keys);
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
	
	public static int getObject(String json, int jsonType, List<String> keys, String key)
	{
		try
		{
			Object jsonObject = (jsonType == TYPE_JSONOBJECT) ? new JSONObject(json) : new JSONArray(json);
			Object object = getObject(jsonObject, keys);
			return ((JSONObject) object).getInt(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return -1;
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
	
	public static String getString(JSONObject fromObject, List<String> keys, String key)
	{
		try
		{
			Object object = getObject(fromObject, keys);
			return ((JSONObject) object).getString(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getString(JSONObject object, String key)
	{
		String value = null;
		try
		{
			value = object.getString(key);
			if (value.equals("") || value.equals("null") || value.equals("unknown"))
			{
				value = null;
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		return value;
	}
	
	public static String getString(JSONObject outerContainer, String containerKey, String key)
	{
		try
		{
			String answer = outerContainer.getJSONObject(containerKey).getString(key);
			if ("".equals(answer))
			{
				answer = null;
			}
			return answer;
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
	
	public static String getString(String json, int jsonType, List<String> keys, int index)
	{
		try
		{
			Object jsonObject = (jsonType == TYPE_JSONOBJECT) ? new JSONObject(json) : new JSONArray(json);
			Object object = getObject(jsonObject, keys);
			return ((JSONArray) object).getString(index);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getString(String json, int jsonType, List<String> keys, String key)
	{
		try
		{
			Object jsonObject = (jsonType == TYPE_JSONOBJECT) ? new JSONObject(json) : new JSONArray(json);
			Object object = getObject(jsonObject, keys);
			return ((JSONObject) object).getString(key);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		catch (ClassCastException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getString(String json, String key)
	{
		try
		{
			JSONObject jsonObject = new JSONObject(json);
			return jsonObject.getString(key);
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
	
	
	public static void putBoolean(JSONObject container, String key, boolean flag)
	{
		try
		{
			container.put(key, flag);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putInt(JSONObject container, String key, int value)
	{
		try
		{
			container.put(key, value);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putLong(JSONObject container, String key, long value)
	{
		try
		{
			container.put(key, value);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putObject(JSONObject container, JSONObject fromObject)
	{
		try
		{
			Iterator<String> fromIterator = fromObject.keys();
			while (fromIterator.hasNext())
			{
				String key = fromIterator.next();
				Object keyValue = fromObject.get(key);
				container.put(key, keyValue);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putObject(JSONObject container, String Key, Object keyValue)
	{
		try
		{
			container.put(Key, keyValue);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void putString(JSONObject container, String key, String value)
	{
		try
		{
			container.put(key, value);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	public static JSONArray removeItem(JSONArray original, int index)
	{
		JSONArray result = new JSONArray();
		for (int i = original.length() - 1; i >= 0; i--)
		{
			try
			{
				if (index != i)
				{
					result.put(original.get(i));
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static JSONArray removeItems(JSONArray original, List<Integer> indicesToRemove)
	{
		JSONArray result = new JSONArray();
		for (int i = original.length() - 1; i >= 0; i--)
		{
			try
			{
				if (!indicesToRemove.contains(i))
				{
					result.put(original.get(i));
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		
		return result;
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
