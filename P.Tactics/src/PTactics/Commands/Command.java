package PTactics.Commands;

public abstract class Command {
	private String name, shortcut,details,help;
	public Command(String name,String sc, String d, String h ) 
	{
		this.name=name;
		this.shortcut=sc; 
		this.details= d;
		this.help=h;
	}
	public  String GetName() 
	{
		return name;
	}
	public  String GetShortCut() 
	{
		return shortcut;
	}
	public  String GetDetails() 
	{
		return details;
	}
	public  String GetHelp() 
	{
		return help;
	}
	protected boolean matchCommand(String c) 
	{
		return c.equalsIgnoreCase(this.GetName()) || c.equalsIgnoreCase(this.GetShortCut()); 
	} 
	protected abstract void execute();
	protected abstract Command parse(String[] sa);
}
