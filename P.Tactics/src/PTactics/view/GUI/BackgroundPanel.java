package PTactics.view.GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

// creation of backgroundPanel class to make sure it resizes correctly if necessary
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {
	private final Image _backgroundImage;

	public BackgroundPanel(Image image) {
		_backgroundImage = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (_backgroundImage != null) {
			int panelWidth = getWidth();
			int panelHeight = getHeight();

			int imageWidth = _backgroundImage.getWidth(this);
			int imageHeight = _backgroundImage.getHeight(this);

			double panelRatio = (double) panelWidth / panelHeight;
			double imageRatio = (double) imageWidth / imageHeight;

			int drawWidth, drawHeight;

			if (panelRatio > imageRatio) { // if the screen is wider than the image, expand the width
				drawWidth = panelWidth;
				drawHeight = (int) (panelWidth / imageRatio);
			} else { // is screen is taller than image, expand height
				drawHeight = panelHeight;
				drawWidth = (int) (panelHeight * imageRatio);
			}

			int x = (panelWidth - drawWidth) / 2;
			int y = (panelHeight - drawHeight) / 2;

			g.drawImage(_backgroundImage, x, y, drawWidth, drawHeight, this);
		}
	}
}
