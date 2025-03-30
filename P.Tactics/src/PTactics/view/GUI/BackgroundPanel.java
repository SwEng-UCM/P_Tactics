package PTactics.view.GUI;

import java.awt.Graphics;
import java.awt.Image;

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
			int imageWidth = _backgroundImage.getWidth(this);
			int imageHeight = _backgroundImage.getHeight(this);
			
			double panelWidth = getWidth();
			double panelHeight = getHeight();
			
			double scale = Math.min(panelWidth / imageWidth, panelHeight / imageHeight);
			
			int newImageWidth = (int) (imageWidth * scale);
			int newImageHeight = (int) (imageHeight * scale);
			
			int x = (getWidth() - newImageWidth) / 2;
			int y = (getHeight() - newImageHeight) / 2;
			
			g.drawImage(_backgroundImage,  x,  y,  newImageWidth,  newImageHeight, this);
		}
	}
}
