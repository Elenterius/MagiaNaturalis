package trinarybrain.magia.naturalis.coremod;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import trinarybrain.magia.naturalis.common.core.Log;

public class Agent implements IClassTransformer
{
	@Override
	public byte[] transform(String className, String transformedName, byte[] classfileBuffer)
	{
		if(className.equals("net.minecraft.client.renderer.entity.RendererLivingEntity"))
		{
			ObfMapCollector.dev = 1;
		}
		else
		{
			if(!className.equals(ObfMapCollector.getMapping("net.minecraft.client.renderer.entity.RendererLivingEntity")))
				return classfileBuffer;
		}
		
		byte[] result = classfileBuffer;
		
		try
		{
			//Create class reader from buffer
			ClassReader reader = new ClassReader(classfileBuffer);
			
			//Make writer
			ClassWriter writer = new ClassWriter(0);
			ClassVisitor profiler = new ProfileClass(writer, className);
			
			//Add the class adapter as a modifier
			reader.accept(profiler, 0);
			result = writer.toByteArray();
			Log.logger.info("Returning Udated class: " + className);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

}
