package trinarybrain.magia.naturalis.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ProfileClass extends ClassVisitor implements Opcodes
{
	private String name;

	public ProfileClass(ClassVisitor cv, String name)
	{
		super(ASM4, cv);
		this.name = name;
	}
	
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
	{
		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
		return new ProfileMethod(mv, access, this.name, name, desc, signature);
	} 
}
