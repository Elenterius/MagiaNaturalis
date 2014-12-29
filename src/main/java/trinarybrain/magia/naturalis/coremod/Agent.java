package trinarybrain.magia.naturalis.coremod;

import java.io.FileOutputStream;
import java.io.IOException;

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
			ClassReader reader = new ClassReader(classfileBuffer);
			
			ClassWriter writer = new ClassWriter(0);
			ClassVisitor profiler = new ProfileClass(writer, className);
			
			reader.accept(profiler, 0);
			result = writer.toByteArray();
			Log.logger.info("Returning Udated class: " + className);
		}
		catch(Exception e)
		{
			Log.logger.catching(e);
		}

		try(FileOutputStream fos = new FileOutputStream("D:/RendererLivingEntity_bytecode_obf.class"))
		{
			fos.write(result);
		}
		catch(IOException ioe)
		{
			Log.logger.catching(ioe);
		}

		return result;
	}

}
