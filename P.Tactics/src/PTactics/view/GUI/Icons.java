package PTactics.view.GUI;

import javax.swing.ImageIcon;

public interface Icons {

    public static interface TroopIcons {
        public final ImageIcon TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_up.png"));
        public final ImageIcon TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_left.png"));
        public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_right.png"));
        public final ImageIcon TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_down.png"));
        public final ImageIcon DEAD = new ImageIcon(Icons.class.getResource("/Icons/Dead.png"));

        public static interface SniperIcons {
            public final ImageIcon TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/sniper_down.png"));
            public final ImageIcon TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/sniper_up.png"));
            public final ImageIcon TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/sniper_left.png"));
            public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/sniper_right.png"));
            public final ImageIcon ENEMY_TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/sniper_up_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/sniper_down_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/sniper_left_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/sniper_right_enemy.png"));
        }

        public static interface SmokerIcons {
            public final ImageIcon TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/Smoker_up.png"));
            public final ImageIcon TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/Smoker_down.png"));
            public final ImageIcon TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/Smoker_left.png"));
            public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/Smoker_right.png"));
            public final ImageIcon ENEMY_TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/smoker_up_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/smoker_down_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/smoker_left_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/smoker_right_enemy.png"));
        }

        public static interface LightTroopIcons {
            public final ImageIcon TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_up.png"));
            public final ImageIcon TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_down.png"));
            public final ImageIcon TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_left.png"));
            public final ImageIcon TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_right.png"));
            public final ImageIcon TROOP_FACING_UP_DASH = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_up_dash.png"));
            public final ImageIcon TROOP_FACING_DOWN_DASH = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_down_dash.png"));
            public final ImageIcon TROOP_FACING_LEFT_DASH = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_left_dash.png"));
            public final ImageIcon TROOP_FACING_RIGHT_DASH = new ImageIcon(Icons.class.getResource("/Icons/LightTroop_right_dash.png"));
            public final ImageIcon ENEMY_TROOP_FACING_UP = new ImageIcon(Icons.class.getResource("/Icons/lightTroop_up_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_DOWN = new ImageIcon(Icons.class.getResource("/Icons/lightTroop_down_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_LEFT = new ImageIcon(Icons.class.getResource("/Icons/lightTroop_left_enemy.png"));
            public final ImageIcon ENEMY_TROOP_FACING_RIGHT = new ImageIcon(Icons.class.getResource("/Icons/lightTroop_right_enemy.png"));
        }
    }

    public static interface otherIcons {
        public final ImageIcon START = new ImageIcon(Icons.class.getResource("/Icons/Holdfire.png"));
        public final ImageIcon WALL = new ImageIcon(Icons.class.getResource("/Icons/inprogressWall.png"));
        public final ImageIcon FOG = new ImageIcon(Icons.class.getResource("/Icons/FoggedFloor.png"));
        public final ImageIcon FLOOR = new ImageIcon(Icons.class.getResource("/Icons/inprogressFloor.png"));
        public final ImageIcon SMOKE = new ImageIcon(Icons.class.getResource("/Icons/SmokedFloor.png"));
        public final ImageIcon LABELBACKGROUND = new ImageIcon(Icons.class.getResource("/Icons/InfoPanelLabel_bg.png"));
        public final ImageIcon LABELBACKGROUND_DARK = new ImageIcon(Icons.class.getResource("/Icons/InfoPanelLabel_darkBg.png"));
        public final ImageIcon LABEL_BG = new ImageIcon(Icons.class.getResource("/Icons/brickButton.png"));
        public final ImageIcon TEXTAREABACKGROUND = new ImageIcon(Icons.class.getResource("/Icons/TextArea_bg.png"));
        public final ImageIcon TEXTAREABACKGROUND_DARK = new ImageIcon(Icons.class.getResource("/Icons/TextArea_darkBg.png"));
        public final ImageIcon BACKGROUND = new ImageIcon(Icons.class.getResource("/Icons/wallpaper.png"));
        public final ImageIcon BACKGROUND2 = new ImageIcon(Icons.class.getResource("/Icons/backgroundImage2.png"));
        public final ImageIcon BACKGROUND_HORIZONTAL = new ImageIcon(Icons.class.getResource("/Icons/backgroundRect.png"));
        public final ImageIcon BACKGROUND_HORIZONTAL2 = new ImageIcon(Icons.class.getResource("/Icons/backgroundRect2.png"));
        public final ImageIcon GAMEBACKGROUND = new ImageIcon(Icons.class.getResource("/Icons/gameBackground.png"));
        public final ImageIcon GAMEBACKGROUND2 = new ImageIcon(Icons.class.getResource("/Icons/gameBackground2.png"));
        public final ImageIcon BG_BUILDING = new ImageIcon(Icons.class.getResource("/Icons/bgInsideBuilding.png"));
        public final ImageIcon BG_BUILDING2 = new ImageIcon(Icons.class.getResource("/Icons/bgInsideBuilding2.png"));
        public final ImageIcon HOLDFIRE_ICON = new ImageIcon(Icons.class.getResource("/Icons/holdFire_icon.png"));
        public final ImageIcon HOLDFIRE_ICON2 = new ImageIcon(Icons.class.getResource("/Icons/holdFire_icon2.png"));
        public final ImageIcon HOLDFIRE_ICON3 = new ImageIcon(Icons.class.getResource("/Icons/holdFire_icon3.png"));
        public final ImageIcon HOLDFIRE_ICON4 = new ImageIcon(Icons.class.getResource("/Icons/holdFire_icon4.png"));
        public final ImageIcon HOLDFIRE_ICON5 = new ImageIcon(Icons.class.getResource("/Icons/holdFire_icon5.png"));
    }
}