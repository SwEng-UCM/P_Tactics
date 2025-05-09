package PTactics.view.GUI;

import javax.swing.ImageIcon;

public interface Icons {

	public static interface TroopIcons {
		public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/LightTroop_up.png", "Icons/LightTroop_up.png");
		public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/LightTroop_left.png",
				"Icons/LightTroop_left.png");
		public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/LightTroop_right.png",
				"Icons/LightTroop_right.png");
		public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/LightTroop_down.png",
				"Icons/LightTroop_down.png");
		public final ImageIcon DEAD = new ImageIcon("Icons/Dead.png", "Icons/Dead.png");

		public static interface SniperIcons {
			public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/sniper_down.png", "Icons/sniper_down.png");
			public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/sniper_up.png", "Icons/sniper_up.png");
			public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/sniper_left.png", "Icons/sniper_left.png");
			public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/sniper_right.png",
					"Icons/sniper_right.png");
			public final ImageIcon ENEMY_TROOP_FACING_UP = new ImageIcon("Icons/sniper_up_enemy.png",
					"Icons/sniper_up_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_DOWN = new ImageIcon("Icons/sniper_down_enemy.png",
					"Icons/sniper_down_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_LEFT = new ImageIcon("Icons/sniper_left_enemy.png",
					"Icons/sniper_left_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_RIGHT = new ImageIcon("Icons/sniper_right_enemy.png",
					"Icons/sniper_right_enemy.png");
		}

		public static interface SmokerIcons {
			public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/Smoker_up.png", "Icons/Smoker_up.png");
			public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/Smoker_down.png", "Icons/Smoker_down.png");
			public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/Smoker_left.png", "Icons/Smoker_left.png");
			public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/Smoker_right.png",
					"Icons/Smoker_right.png");
			public final ImageIcon ENEMY_TROOP_FACING_UP = new ImageIcon("Icons/smoker_up_enemy.png",
					"Icons/smoker_up_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_DOWN = new ImageIcon("Icons/smoker_down_enemy.png",
					"Icons/smoker_down_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_LEFT = new ImageIcon("Icons/smoker_left_enemy.png",
					"Icons/smoker_left_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_RIGHT = new ImageIcon("Icons/smoker_right_enemy.png",
					"Icons/smoker_right_enemy.png");
		}

		public static interface LightTroopIcons {
			public final ImageIcon TROOP_FACING_UP = new ImageIcon("Icons/LightTroop_up.png",
					"Icons/LightTroop_up.png");
			public final ImageIcon TROOP_FACING_DOWN = new ImageIcon("Icons/LightTroop_down.png",
					"Icons/LightTroop_down.png");
			public final ImageIcon TROOP_FACING_LEFT = new ImageIcon("Icons/LightTroop_left.png",
					"Icons/LightTroop_left.png");
			public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon("Icons/LightTroop_right.png",
					"Icons/LightTroop_right.png");
			public final ImageIcon TROOP_FACING_UP_DASH = new ImageIcon("Icons/LightTroop_up_dash.png",
					"Icons/LightTroop_up_dash.png");
			public final ImageIcon TROOP_FACING_DOWN_DASH = new ImageIcon("Icons/LightTroop_down_dash.png",
					"Icons/LightTroop_down_dash.png");
			public final ImageIcon TROOP_FACING_LEFT_DASH = new ImageIcon("Icons/LightTroop_left_dash.png",
					"Icons/LightTroop_left_dash.png");
			public final ImageIcon TROOP_FACING_RIGHT_DASH = new ImageIcon("Icons/LightTroop_right_dash.png",
					"Icons/LightTroop_right_dash.png");
			public final ImageIcon ENEMY_TROOP_FACING_UP = new ImageIcon("Icons/lightTroop_up_enemy.png",
					"Icons/lightTroop_up_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_DOWN = new ImageIcon("Icons/lightTroop_down_enemy.png",
					"Icons/lightTroop_down_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_LEFT = new ImageIcon("Icons/lightTroop_left_enemy.png",
					"Icons/lightTroop_left_enemy.png");
			public final ImageIcon ENEMY_TROOP_FACING_RIGHT = new ImageIcon("Icons/lightTroop_right_enemy.png",
					"Icons/lightTroop_right_enemy.png");
		}
	}

	public static interface otherIcons {
		public final ImageIcon START = new ImageIcon("Icons/Holdfire.png", "Icons/Holdfire.png");
		public final ImageIcon WALL = new ImageIcon("Icons/inprogressWall.png", "Icons/inprogressWall.png");
		public final ImageIcon FOG = new ImageIcon("Icons/FoggedFloor.png", "Icons/FoggedFloor.png");
		public final ImageIcon FLOOR = new ImageIcon("Icons/inprogressFloor.png", "Icons/inprogressFloor.png");
		public final ImageIcon SMOKE = new ImageIcon("Icons/SmokedFloor.png", "Icons/SmokedFloor.png");

		public final ImageIcon LABELBACKGROUND = new ImageIcon("Icons/InfoPanelLabel_bg.png",
				"Icons/InfoPanelLabel_bg.png");
		public final ImageIcon LABELBACKGROUND_DARK = new ImageIcon("Icons/InfoPanelLabel_darkBg.png",
				"Icons/InfoPanelLabel_darkBg.png");
		public final ImageIcon LABEL_BG = new ImageIcon("Icons/brickButton.png", "Icons/brickButton.png");
		public final ImageIcon TEXTAREABACKGROUND = new ImageIcon("Icons/TextArea_bg.png", "Icons/TextArea_bg.png");
		public final ImageIcon TEXTAREABACKGROUND_DARK = new ImageIcon("Icons/TextArea_darkBg.png",
				"Icons/TextArea_darkBg.png");
		public final ImageIcon BACKGROUND = new ImageIcon("Icons/wallpaper.png", "Icons/wallpaper.png");
		public final ImageIcon BACKGROUND2 = new ImageIcon("Icons/backgroundImage2.png", "Icons/backgroundImage2.png");
		public final ImageIcon BACKGROUND_HORIZONTAL = new ImageIcon("Icons/backgroundRect.png",
				"Icons/backgroundRect.png");
		public final ImageIcon BACKGROUND_HORIZONTAL2 = new ImageIcon("Icons/backgroundRect2.png",
				"Icons/backgroundRect2.png");
		public final ImageIcon GAMEBACKGROUND = new ImageIcon("Icons/gameBackground.png", "Icons/gameBackground.png");
		public final ImageIcon GAMEBACKGROUND2 = new ImageIcon("Icons/gameBackground2.png",
				"Icons/gameBackground2.png");
		public final ImageIcon BG_BUILDING = new ImageIcon("Icons/bgInsideBuilding.png", "Icons/bgInsideBuilding.png");
		public final ImageIcon BG_BUILDING2 = new ImageIcon("Icons/bgInsideBuilding2.png",
				"Icons/bgInsideBuilding2.png");

		public final ImageIcon HOLDFIRE_ICON = new ImageIcon("Icons/holdFire_icon.png", "Icons/holdFire_icon.png");
		public final ImageIcon HOLDFIRE_ICON2 = new ImageIcon("Icons/holdFire_icon2.png", "Icons/holdFire_icon2.png");
		public final ImageIcon HOLDFIRE_ICON3 = new ImageIcon("Icons/holdFire_icon3.png", "Icons/holdFire_icon3.png");
		public final ImageIcon HOLDFIRE_ICON4 = new ImageIcon("Icons/holdFire_icon4.png", "Icons/holdFire_icon4.png");
		public final ImageIcon HOLDFIRE_ICON5 = new ImageIcon("Icons/holdFire_icon5.png", "Icons/holdFire_icon5.png");
	}
}
