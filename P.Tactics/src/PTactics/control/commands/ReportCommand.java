package PTactics.control.commands;

import PTactics.control.ControllerInterface;
import PTactics.control.Tracker;

//For commands that can be undone
public abstract class ReportCommand extends Command {

	public ReportCommand(String name, String sc, String d, String h) {
		super(name, sc, d, h);
	}

	@Override
	public void execute(ControllerInterface CI) {
		Tracker.getTracker(CI).saveAction(getSnap(CI));
	}

	protected abstract Snapshot getSnap(ControllerInterface CI);

	protected void eraseSnap(ControllerInterface CI) {
		Tracker.getTracker(CI).pop();
	}
}
