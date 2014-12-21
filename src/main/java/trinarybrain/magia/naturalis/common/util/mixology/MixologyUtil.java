package trinarybrain.magia.naturalis.common.util.mixology;

import java.util.ArrayList;
import java.util.Collection;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import trinarybrain.magia.naturalis.common.util.mixology.Additive;

public class MixologyUtil
{
	//TODO: MAKE A NEW BREWING PROTOTYPE
	public static void simulatePotionMixing(AspectList list)
	{
		ArrayList<Component> aspectPool = new ArrayList<Component>();
		
		for(Aspect aspect : list.getAspects())
		aspectPool.add(new Component(aspect, list.getAmount(aspect)));
		 
		System.out.print("\nCrucible Aspect Pool");
		line();
		for(Component comp : aspectPool)
		{
			System.out.printf("%dx %s Essentia%n", comp.amount, comp.aspect.getName());
		}
		
		System.out.print("\n\n");
		System.out.print("Searching for possible Combinations");
		line();
		
		ArrayList<String> tagList = new ArrayList<String>();
		for(Effect eff : getEffects())
		{
			int i = 0;
			for(Component comp : eff.components)
			{
				for(Component cp : aspectPool)
				{
					if(comp.aspect.equals(cp.aspect) && comp.amount <= cp.amount)
						i++;
				}
			}
			if(i == eff.components.size())
			{
				tagList.add(eff.tag);
			}
		}
		
		System.out.print("Effects Found: " + tagList.size());
		System.out.print("\nEffects: " + tagList);
		
		ArrayList<String> tagList2 = new ArrayList<String>();
		for(Additive add : getAdditives())
		{
			int i = 0;
			for(Component comp : add.components)
			{
				for(Component cp : aspectPool)
				{
					if(comp.aspect.equals(cp.aspect) && comp.amount <= cp.amount)
					{
						i++;
					}
				}
			}
			if(i == add.components.size())
			{
				tagList2.add(add.tag);
			}
		}

		System.out.print("\nAdditives Found: " + tagList2.size());
		System.out.print("\nAdditives: " + tagList2);

		tagList = new ArrayList<String>();
		for(Effect eff : getEffects())
		{
			int i = 0;
			for(Component comp : eff.components)
			{
				for(Component cp : aspectPool)
				{
					if(comp.aspect.equals(cp.aspect) && comp.amount <= cp.amount)
						i++;
				}
			}
			if(i == eff.components.size())
			{
				tagList.add(eff.tag);
				for(Component comp : eff.components)
				{
					ArrayList<Component> flaged = new ArrayList<Component>();
					for(Component cp : aspectPool)
					{
						if(comp.aspect.equals(cp.aspect))
						{
							if(comp.amount <= cp.amount)
							{
								cp.amount -= comp.amount;
								if(cp.amount <= 0)
									flaged.add(cp);
							}	
						}
					}
					for(Component cp : flaged)
					{
						aspectPool.remove(cp);
					}				
				}
			}
		}
				
		tagList2 = new ArrayList<String>();
		for(Additive add : getAdditives())
		{
			int i = 0;
			for(Component comp : add.components)
			{
				for(Component cp : aspectPool)
				{
					if(comp.aspect.equals(cp.aspect) && comp.amount <= cp.amount)
					{
						i++;
					}
				}
			}
			if(i == add.components.size())
			{
				tagList2.add(add.tag);
				for(Component comp : add.components)
				{
					ArrayList<Component> flaged = new ArrayList<Component>();
					for(Component cp : aspectPool)
					{
						if(comp.aspect.equals(cp.aspect))
						{
							if(comp.amount <= cp.amount)
							{
								cp.amount -= comp.amount;
								if(cp.amount <= 0)
									flaged.add(cp);
							}	
						}
					}
					for(Component cp : flaged)
					{
						aspectPool.remove(cp);
					}				
				}
			}
		}
		
		System.out.print("\n\n");
		System.out.print("\nPotion Brewing Result");
		line();
		System.out.print("Effects: " + tagList);
		
		String str = "default";
		if(tagList2.contains(Additive.COMBUSTION.tag) && !tagList2.contains(Additive.WATER_BASE.tag))
			str = "unstable";
		System.out.print("\nType: " + str);
		
		float f0 = 1.0F;
		if(tagList2.contains(Additive.DURATION.tag))
			f0 += 0.55F;
		if(tagList2.contains(Additive.DURATION_ALT_2.tag))
			f0 += 1.21F;
		
		System.out.printf("\nDuration: %.2f min%n", f0);
		System.out.print("Additives: " + tagList2);
		
		System.out.print("\n\n\nRemaining Aspect Pool");
		line();
		for(Component comp : aspectPool)
		{
			System.out.printf("%dx %s Essentia%n", comp.amount, comp.aspect.getName());
		}
	}
	
	public static ArrayList<Effect> getEffects()
	{
		ArrayList<Effect> effects = new ArrayList<Effect>();
		Collection<Effect> ce = Effect.effects.values();
		for(Effect eff : ce)
		{
				effects.add(eff);
		}
		return effects;
	}
	
	public static ArrayList<Additive> getAdditives()
	{
		ArrayList<Additive> additives = new ArrayList<Additive>();
		Collection<Additive> ce = Additive.additives.values();
		for(Additive add : ce)
		{
			additives.add(add);
		}
		return additives;
	}
	
	public static void line()
	{
		System.out.print("\n------------------------------------------------------------------\n");
	}
}
