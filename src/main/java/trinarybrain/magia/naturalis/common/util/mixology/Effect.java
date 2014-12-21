package trinarybrain.magia.naturalis.common.util.mixology;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import thaumcraft.api.aspects.Aspect;

public class Effect
{
	String tag;
	ArrayList<Component> components = new ArrayList<Component>();
		
	public Effect(String tag, Component[] comp)
	{
		if(effects.containsKey(tag)) throw new IllegalArgumentException(tag+" already registered.");
		this.tag = tag;
		
		for(Component c : comp)
			this.components.add(c);
		
		effects.put(tag, this);
	}
	
	public static Effect getEffect(String tag)
	{
		return effects.get(tag);
	}
	
	public static LinkedHashMap<String,Effect> effects = new LinkedHashMap<String,Effect>();
	public static final Effect STRENGTH = new Effect("strength", new Component[] {new Component(Aspect.WEAPON, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect WEAKNESS = new Effect("weakness", new Component[] {new Component(Aspect.DEATH, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect WEAKNESS_ALT_2 = new Effect("weakness-alt-2", new Component[] {new Component(Aspect.ENTROPY, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect HARMING = new Effect("harming", new Component[] {new Component(Aspect.WEAPON, 6), new Component(Aspect.MAGIC, 4)});
	public static final Effect POISON = new Effect("poison", new Component[] {new Component(Aspect.POISON, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect HEALING = new Effect("healing", new Component[] {new Component(Aspect.HEAL, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect HEALING_ALT_2 = new Effect("healing-alt-2", new Component[] {new Component(Aspect.LIFE, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect REGEN = new Effect("regeneration", new Component[] {new Component(Aspect.HEAL, 2), new Component(Aspect.LIFE, 1), new Component(Aspect.MAGIC, 2)});
	public static final Effect SWIFT = new Effect("swiftness", new Component[] {new Component(Aspect.MOTION, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect SWIFT_ALT_2 = new Effect("swiftness-alt-2", new Component[] {new Component(Aspect.MOTION, 1), new Component(Aspect.AIR, 2),new Component(Aspect.MAGIC, 2)});
	public static final Effect SLOW = new Effect("slwoness", new Component[] {new Component(Aspect.TRAP, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect NIGHT_VISION = new Effect("night-vision", new Component[] {new Component(Aspect.SENSES, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect INVISIBLE = new Effect("invisibility", new Component[] {new Component(Aspect.SENSES, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect FIRE_RESIST = new Effect("fire-resitance", new Component[] {new Component(Aspect.FIRE, 2), new Component(Aspect.ARMOR, 2), new Component(Aspect.MAGIC, 2)});
	public static final Effect ABSORBTION = new Effect("absorbtion", new Component[] {new Component(Aspect.ARMOR, 1), new Component(Aspect.LIFE, 2), new Component(Aspect.MAGIC, 2)});
	public static final Effect HEALTH_BOOST = new Effect("healt-boost", new Component[] {new Component(Aspect.LIFE, 4), new Component(Aspect.ARMOR, 2), new Component(Aspect.MAGIC, 4)});
	public static final Effect RESIST = new Effect("resistance", new Component[] {new Component(Aspect.ARMOR, 6), new Component(Aspect.MAGIC, 4)});
	public static final Effect FLUX_TAINT = new Effect("flux-taint", new Component[] {new Component(Aspect.TAINT, 6), new Component(Aspect.MAGIC, 4)});
	public static final Effect FLUX_FLU = new Effect("flux-flu", new Component[] {new Component(Aspect.TAINT, 4), new Component(Aspect.SENSES, 2), new Component(Aspect.MAGIC, 4)});
	public static final Effect WITHER = new Effect("wither", new Component[] {new Component(Aspect.DEATH, 4),new Component(Aspect.POISON, 2), new Component(Aspect.MAGIC, 4)});
	public static final Effect HUNGER = new Effect("hunger", new Component[] {new Component(Aspect.HUNGER, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect SATURATION = new Effect("saturation", new Component[] {new Component(Aspect.HUNGER, 1), new Component(Aspect.HEAL, 2), new Component(Aspect.MAGIC, 2)});
	public static final Effect SATURATION_ALT_2 = new Effect("saturation-alt-2", new Component[] {new Component(Aspect.HUNGER, 1), new Component(Aspect.FLESH, 2), new Component(Aspect.MAGIC, 2)});
	public static final Effect NAUSEA = new Effect("nausea", new Component[] {new Component(Aspect.SENSES, 4), new Component(Aspect.POISON, 2), new Component(Aspect.MAGIC, 2)});
	public static final Effect DISRELISH = new Effect("disrelish", new Component[] {new Component(Aspect.HUNGER, 4), new Component(Aspect.POISON, 2), new Component(Aspect.MAGIC, 2)});
	public static final Effect BLIND = new Effect("blindness", new Component[] {new Component(Aspect.DARKNESS, 3), new Component(Aspect.MAGIC, 2)});
	public static final Effect JUMP_BOOST = new Effect("jump-boost", new Component[] {new Component(Aspect.MOTION, 2), new Component(Aspect.AIR, 1), new Component(Aspect.MAGIC, 2)});
	public static final Effect HASTE = new Effect("haste", new Component[] {new Component(Aspect.TOOL, 4), new Component(Aspect.MOTION, 2), new Component(Aspect.MAGIC, 4)});
	public static final Effect FATIGUE = new Effect("mining-fatigue", new Component[] {new Component(Aspect.TOOL, 4), new Component(Aspect.TRAP, 2), new Component(Aspect.MAGIC, 4)});
}