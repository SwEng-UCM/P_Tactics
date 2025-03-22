package PTactics.model.game;

import java.util.ArrayList;
import java.util.List;

import PTactics.utils.Position;


public class DangerMediator {
	List<DangerObject> _objects;
	
	public DangerMediator() {
		_objects = new ArrayList<>();
	}
	
	
	public void registerComponent(DangerObject obj) {
		_objects.add(obj);
	}
	
	public boolean isInDanger(DangerObject obj, Position pos) {
		boolean danger = false;
		for (DangerObject o : _objects) {
			if (!o.getId().equals(obj.getId()) && o.isInDanger(pos)) {
				danger = true;
			}
		}
		
		return danger;
	}
}
