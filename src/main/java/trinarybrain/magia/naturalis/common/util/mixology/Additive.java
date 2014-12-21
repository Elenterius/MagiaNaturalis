package trinarybrain.magia.naturalis.common.util.mixology;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import thaumcraft.api.aspects.Aspect;

public class Additive
{
	String tag;
	ArrayList<Component> components = new ArrayList<Component>();
	
	public Additive(String tag, Component[] comp)
	{
		if(additives.containsKey(tag)) throw new IllegalArgumentException(tag+" already registered!");
		this.tag = tag;
		
		for(Component c : comp)
			this.components.add(c);
		
		additives.put(tag, this);
	}
	
	public Additive(String tag, Component comp)
	{
		if(additives.containsKey(tag)) throw new IllegalArgumentException(tag+" already registered!");
		this.tag = tag;
		this.components.add(comp);
		additives.put(tag, this);
	}

	public static LinkedHashMap<String,Additive> additives = new LinkedHashMap<String,Additive>();
	public static final Additive WATER_BASE = new Additive("liquid-base", new Component(Aspect.WATER, 1));
	public static final Additive PURIFY_HERB = new Additive("purify-herb", new Component(Aspect.PLANT, 1));
	public static final Additive CORRUPT_TAINT = new Additive("corrupt-taint", new Component(Aspect.TAINT, 1));
	public static final Additive BINDING = new Additive("binding", new Component(Aspect.SLIME, 1));
	public static final Additive BINDING_ALT_2 = new Additive("binding-alt-2", new Component(Aspect.MAGIC, 2));
	public static final Additive COMBUSTION = new Additive("combustion", new Component[]{new Component(Aspect.FIRE, 4), new Component(Aspect.ENTROPY, 4)});
	public static final Additive COMBUSTION_ALT_2 = new Additive("combustion-alt-2", new Component[]{new Component(Aspect.ENTROPY, 1), new Component(Aspect.ENERGY, 5)});
	public static final Additive POTENCY = new Additive("potency", new Component[]{new Component(Aspect.LIGHT, 2), new Component(Aspect.SENSES, 1)});
	public static final Additive POTENCY_ALT_2 = new Additive("potency-alt-2", new Component[]{new Component(Aspect.EXCHANGE, 2), new Component(Aspect.ENERGY, 4)});
	public static final Additive DURATION = new Additive("duration", new Component[]{new Component(Aspect.ENERGY, 2), new Component(Aspect.MECHANISM, 1)});
	public static final Additive DURATION_ALT_2 = new Additive("duration-alt-2", new Component[]{new Component(Aspect.TRAVEL, 2), new Component(Aspect.ELDRITCH, 1)});
}
