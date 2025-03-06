package PTactics.Game;

import java.util.ArrayList;
import java.util.List;

import PTactics.Utils.Position;


public class DangerMediator {
	List<DangerObject> _objects;
	
	public DangerMediator() {
		_objects = new ArrayList<>();
	}
	
	
	public void registerComponent(DangerObject obj) {
		_objects.add(obj);
	}
	
	public boolean isInDanger(DangerObject obj, Position pos) {
		for (DangerObject o : _objects) {
			if (!o.getId().equals(obj.getId()) && o.isInDanger(pos)) {
				return true;
			}
		}
		
		return false;
	}
}
