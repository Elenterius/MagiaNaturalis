package trinarybrain.magia.naturalis.common.util.mixology;

import thaumcraft.api.aspects.Aspect;

public class Component
{
	Aspect aspect;
	int amount;

	public Component(Aspect ap, int i)
	{
		this.aspect = ap;
		this.amount = i;
	}

	public int getAmount()
	{
		return this.amount;
	}

	public Aspect getAspect()
	{
		return this.aspect;
	}
}