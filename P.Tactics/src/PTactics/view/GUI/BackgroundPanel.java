package PTactics.view.GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

// creation of backgroundPanel class to make sure it resizes correctly if necessary
public class BackgroundPanel extends JPanel{
	private final Image _backgroundImage;
	
	public BackgroundPanel(Image image) {
		_backgroundImage = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(_backgroundImage != null) {
			g.drawImage(_backgroundImage,  0,  0,  getWidth(),  getHeight(), this);
		}
	}
}
