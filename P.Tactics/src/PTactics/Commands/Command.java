package PTactics.Commands;

public interface Command {
	public Command parse(String argument);
	public void execute();
	public void help();
}
