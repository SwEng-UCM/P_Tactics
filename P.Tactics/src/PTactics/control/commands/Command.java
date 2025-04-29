package PTactics.control.commands;

import PTactics.control.ControllerInterface;

public abstract class Command {
	private String name, shortcut,details,help;
	public Command(String name,String sc, String d, String h ) 
	{
		this.name=name;
		this.shortcut=sc; 
		this.details= d;
		this.help=h;
	}
	public  String getName() 
	{
		return name;
	}
	public  String getShorcut() 
	{
		return shortcut;
	}
	public  String getDetails() 
	{
		return details;
	}
	public  String getHelp() 
	{
		return help;
	}
	protected boolean matchCommand(String c) 
	{
		return c.equalsIgnoreCase(this.getName()) || c.equalsIgnoreCase(this.getShorcut()); 
	} 
	public abstract void execute(ControllerInterface CI);
	public abstract Command parse(String[] sa);
}
