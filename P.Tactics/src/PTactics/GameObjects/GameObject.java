package PTactics.GameObjects;

import PTactics.Utils.Position;

public abstract class GameObject {
	private Position pos;
	private boolean enabled;
	public GameObject(Position pos) 
	{
		this.pos=pos;
	}
	public void  setPosition(Position setter) 
	{
		this.pos=setter;
	}
	public void Disable() 
	{
		this.enabled=false;
	}
	public void Enable() 
	{
		this.enabled=true;
	}
	public boolean GetCheckStatus() 
	{
		return enabled;
	}
	public Position GetPos() 
	{
		return this.pos;
	}
	public abstract void Update();
}
