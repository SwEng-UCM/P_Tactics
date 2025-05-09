package PTactics.view.GUI;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.net.URL;

public interface Icons {
    
    // Helper method to safely load icons with error handling
    public static ImageIcon loadIcon(String path) {
        try {
            URL resource = Icons.class.getResource(path);
            if (resource == null) {
                System.err.println("[ERROR] Missing icon resource: " + path);
                return createErrorIcon(32, 32); // Default size for error icon
            }
            return new ImageIcon(resource, path);
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load icon: " + path);
            e.printStackTrace();
            return createErrorIcon(32, 32);
        }
    }
    
    // Creates a visible error placeholder icon
    private static ImageIcon createErrorIcon(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.RED);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawString("ERR", 5, height/2 + 5);
        g.dispose();
        return new ImageIcon(img);
    }

    public static interface TroopIcons {
        ImageIcon TROOP_FACING_UP = loadIcon("/Icons/LightTroop_up.png");
        ImageIcon TROOP_FACING_LEFT = loadIcon("/Icons/LightTroop_left.png");
        ImageIcon TROOP_FACING_RIGHT = loadIcon("/Icons/LightTroop_right.png");
        ImageIcon TROOP_FACING_DOWN = loadIcon("/Icons/LightTroop_down.png");
        ImageIcon DEAD = loadIcon("/Icons/Dead.png");

        public static interface SniperIcons {
            ImageIcon TROOP_FACING_DOWN = loadIcon("/Icons/sniper_down.png");
            ImageIcon TROOP_FACING_UP = loadIcon("/Icons/sniper_up.png");
            ImageIcon TROOP_FACING_LEFT = loadIcon("/Icons/sniper_left.png");
            ImageIcon TROOP_FACING_RIGHT = loadIcon("/Icons/sniper_right.png");
            ImageIcon ENEMY_TROOP_FACING_UP = loadIcon("/Icons/sniper_up_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_DOWN = loadIcon("/Icons/sniper_down_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_LEFT = loadIcon("/Icons/sniper_left_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_RIGHT = loadIcon("/Icons/sniper_right_enemy.png");
        }

        public static interface SmokerIcons {
            ImageIcon TROOP_FACING_UP = loadIcon("/Icons/Smoker_up.png");
            ImageIcon TROOP_FACING_DOWN = loadIcon("/Icons/Smoker_down.png");
            ImageIcon TROOP_FACING_LEFT = loadIcon("/Icons/Smoker_left.png");
            ImageIcon TROOP_FACING_RIGHT = loadIcon("/Icons/Smoker_right.png");
            ImageIcon ENEMY_TROOP_FACING_UP = loadIcon("/Icons/smoker_up_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_DOWN = loadIcon("/Icons/smoker_down_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_LEFT = loadIcon("/Icons/smoker_left_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_RIGHT = loadIcon("/Icons/smoker_right_enemy.png");
        }

        public static interface LightTroopIcons {
            ImageIcon TROOP_FACING_UP = loadIcon("/Icons/LightTroop_up.png");
            ImageIcon TROOP_FACING_DOWN = loadIcon("/Icons/LightTroop_down.png");
            ImageIcon TROOP_FACING_LEFT = loadIcon("/Icons/LightTroop_left.png");
            ImageIcon TROOP_FACING_RIGHT = loadIcon("/Icons/LightTroop_right.png");
            ImageIcon TROOP_FACING_UP_DASH = loadIcon("/Icons/LightTroop_up_dash.png");
            ImageIcon TROOP_FACING_DOWN_DASH = loadIcon("/Icons/LightTroop_down_dash.png");
            ImageIcon TROOP_FACING_LEFT_DASH = loadIcon("/Icons/LightTroop_left_dash.png");
            ImageIcon TROOP_FACING_RIGHT_DASH = loadIcon("/Icons/LightTroop_right_dash.png");
            ImageIcon ENEMY_TROOP_FACING_UP = loadIcon("/Icons/lightTroop_up_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_DOWN = loadIcon("/Icons/lightTroop_down_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_LEFT = loadIcon("/Icons/lightTroop_left_enemy.png");
            ImageIcon ENEMY_TROOP_FACING_RIGHT = loadIcon("/Icons/lightTroop_right_enemy.png");
        }
    }

    public static interface otherIcons {
        ImageIcon START = loadIcon("/Icons/Holdfire.png");
        ImageIcon WALL = loadIcon("/Icons/inprogressWall.png");
        ImageIcon FOG = loadIcon("/Icons/FoggedFloor.png");
        ImageIcon FLOOR = loadIcon("/Icons/inprogressFloor.png");
        ImageIcon SMOKE = loadIcon("/Icons/SmokedFloor.png");
        ImageIcon LABELBACKGROUND = loadIcon("/Icons/InfoPanelLabel_bg.png");
        ImageIcon LABELBACKGROUND_DARK = loadIcon("/Icons/InfoPanelLabel_darkBg.png");
        ImageIcon LABEL_BG = loadIcon("/Icons/brickButton.png");
        ImageIcon TEXTAREABACKGROUND = loadIcon("/Icons/TextArea_bg.png");
        ImageIcon TEXTAREABACKGROUND_DARK = loadIcon("/Icons/TextArea_darkBg.png");
        ImageIcon BACKGROUND = loadIcon("/Icons/wallpaper.png");
        ImageIcon BACKGROUND2 = loadIcon("/Icons/backgroundImage2.png");
        ImageIcon BACKGROUND_HORIZONTAL = loadIcon("/Icons/backgroundRect.png");
        ImageIcon BACKGROUND_HORIZONTAL2 = loadIcon("/Icons/backgroundRect2.png");
        ImageIcon GAMEBACKGROUND = loadIcon("/Icons/gameBackground.png");
        ImageIcon GAMEBACKGROUND2 = loadIcon("/Icons/gameBackground2.png");
        ImageIcon BG_BUILDING = loadIcon("/Icons/bgInsideBuilding.png");
        ImageIcon BG_BUILDING2 = loadIcon("/Icons/bgInsideBuilding2.png");
        ImageIcon HOLDFIRE_ICON = loadIcon("/Icons/holdFire_icon.png");
        ImageIcon HOLDFIRE_ICON2 = loadIcon("/Icons/holdFire_icon2.png");
        ImageIcon HOLDFIRE_ICON3 = loadIcon("/Icons/holdFire_icon3.png");
        ImageIcon HOLDFIRE_ICON4 = loadIcon("/Icons/holdFire_icon4.png");
        ImageIcon HOLDFIRE_ICON5 = loadIcon("/Icons/holdFire_icon5.png");
    }
}