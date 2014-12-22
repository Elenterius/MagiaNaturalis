package trinarybrain.magia.naturalis.common.research;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import trinarybrain.magia.naturalis.common.core.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public final class LoreCollector
{
	private static final Map<String, String> map = new HashMap<String, String>();
	private static final Gson GSON = new Gson();

	static
	{
		InputStream stream = LoreCollector.class.getResourceAsStream("assets/magianaturalis/lore.json");
		try
		{
			if(stream == null) { throw new NullPointerException("Stream == null"); }
			Map<String, String> map1 = GSON.fromJson(new InputStreamReader(stream), new TypeToken<Map<String, String>>() {}.getType());
			map.putAll(map1);
		}
		catch(Exception exception)
		{
			Log.logger.catching(exception);
			exception.printStackTrace(System.err);
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
				exception.printStackTrace(System.err);
			}
		}
	}

	public static String getLore(String key)
	{
		return map.get(key);
	}
}
