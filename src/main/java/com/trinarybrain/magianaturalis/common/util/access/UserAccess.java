package com.trinarybrain.magianaturalis.common.util.access;

import java.util.UUID;

public class UserAccess
{
	private UUID id;
	private byte accessLevel;

	public UserAccess() {}

	public UserAccess(UUID id, byte accessType)
	{
		this.id = id;
		this.accessLevel = accessType;
	}

	public UUID getUUID() {return id;}
	public void setUUID(UUID id)
	{
		this.id = id;
	}

	public byte getAccessLevel() {return this.accessLevel;}
	public void setAccesLevel(byte accessType) {this.accessLevel = accessType;}
	public boolean hasAccess()
	{
		return this.accessLevel >= 0;
	}

	public boolean equals(Object obj)
	{
		if(obj == null || obj.getClass() != UserAccess.class) return false;
		UserAccess user = (UserAccess)obj;
		return this.id.equals(user.id) && this.accessLevel == user.accessLevel;
	}

	public String toString()
	{
		return "("+this.id.toString()+","+this.accessLevel+")";
	}
}
