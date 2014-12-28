package trinarybrain.magia.naturalis.coremod;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import trinarybrain.magia.naturalis.common.core.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public final class ObfMapCollector
{
	private static final Map<String, String> map = new HashMap<String, String>();
	private static final Gson GSON = new Gson();
	public static byte dev = 0;

	static
	{
		InputStream stream = ObfMapCollector.class.getResourceAsStream("/mapping.json");
		try
		{
			if(stream == null) { throw new NullPointerException("Stream == null"); }
			Map<String, String> map1 = GSON.fromJson(new InputStreamReader(stream), new TypeToken<Map<String, String>>() {}.getType());
			map.putAll(map1);
		}
		catch(Exception exception)
		{
			Log.logger.catching(exception);
		}
		finally
		{
			try
			{
				if(stream != null)
				{
					stream.close();
				}
			}
			catch(IOException exception)
			{
				Log.logger.catching(exception);
			}
		}
	}

	public static String getMapping(String key)
	{
		if(dev == 1) return key;
		
		if(map.get(key) == null)
		{
//			Log.logger.error("Mapping is missing for -> " + key);
			System.out.println("Mapping is " + map.get(key) + " for -> " + key);
			if(key.equals("net.minecraft.client.renderer.entity.RendererLivingEntity"))
			{
				map.put("net.minecraft.client.renderer.entity.RendererLivingEntity", "boh");
				return "boh";
			}
		}
		
		return map.get(key);
	}
}
