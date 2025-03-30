package PTactics.view.GUI;

import javax.swing.ImageIcon;

public interface Icons {

	public static interface TroopIcons {
		public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/LightTroop_up.png");
		public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/LightTroop_left.png");
		public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/LightTroop_right.png");
		public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/LightTroop_down.png"); 
		public final ImageIcon DEAD = new ImageIcon("Icons/Dead.png");
		
		public static interface SniperIcons {
			public final ImageIcon TROOP_FACING_DOWN= new ImageIcon("Icons/sniper_down.png");
			public final ImageIcon TROOP_FACING_UP= new ImageIcon("Icons/sniper_up.png");
			public final ImageIcon TROOP_FACING_LEFT= new ImageIcon("Icons/sniper_left.png");
			public final ImageIcon TROOP_FACING_RIGHT= new ImageIcon("Icons/sniper_right.png");
		}
		
		public static interface SmokerIcons {
			public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/Smoker_up.png");
			public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/Smoker_down.png"); 
			public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/Smoker_left.png");
			public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/Smoker_right.png");
		}
		
		public static interface LightTroopIcons {
			public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/LightTroop_up.png");
			public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/LightTroop_down.png"); 
			public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/LightTroop_left.png");
			public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/LightTroop_right.png");
		}
	}

	public static interface otherIcons {
		public final ImageIcon WALL = new ImageIcon("Icons/inprogressWall.png");
		//public final ImageIcon WALL = new ImageIcon("Icons/Wall.png");
		public final ImageIcon FOG = new ImageIcon("Icons/FoggedFloor.png");
		//public final ImageIcon FOG = new ImageIcon("Icons/certainly_fog.png");
		public final ImageIcon FLOOR = new ImageIcon("Icons/inprogressFloor.png");
		//public final ImageIcon FLOOR = new ImageIcon("Icons/Floor.png");
		public final ImageIcon SMOKE= new ImageIcon("Icons/SmokedFloor.png");
		
		public final ImageIcon BACKGROUND = new ImageIcon("Icons/wallpaper.png");
		public final ImageIcon BACKGROUND2 = new ImageIcon("Icons/backgroundImage2.png");
	}
}
