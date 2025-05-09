package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import PTactics.control.ControllerInterface;
import PTactics.control.TroopInfo;
import PTactics.control.commands.AbilityCommand;
import PTactics.model.game.Game;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

public class ControlPanel extends JPanel implements GameObserver {

	private static final long serialVersionUID = 1L;

	private JLabel txtrCurrentSelectedTroop;
	JToggleButton moveButton;
	JToggleButton aimButton;
	JToggleButton abilityButton;
	private ButtonGroup toggleGroup;

	private ControllerInterface _cntr;

	public ControlPanel(ControllerInterface cntr) {
		this._cntr = cntr;
		this._cntr.addObserver(this);
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		// this.setMaximumSize(new Dimension(1200, 100));

		txtrCurrentSelectedTroop = new JLabel();
		txtrCurrentSelectedTroop.setText("Current Selected Troop:");
		txtrCurrentSelectedTroop.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		txtrCurrentSelectedTroop.setForeground(Color.getHSBColor(0, 0, 0));
		txtrCurrentSelectedTroop.setHorizontalAlignment(SwingConstants.CENTER);
		txtrCurrentSelectedTroop.setVerticalAlignment(SwingConstants.CENTER);

		@SuppressWarnings("serial")
		JPanel gameInfoPanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Image img = Icons.otherIcons.LABELBACKGROUND.getImage();
				int imgWidth = img.getWidth(this);
				int imgHeight = img.getHeight(this);

				double scaleX = getWidth() / (double) imgWidth;
				double scaleY = getHeight() / (double) imgHeight;
				double scale = Math.min(scaleX, scaleY); // to maintain proportion

				int drawWidth = (int) (imgWidth * scale);
				int drawHeight = (int) (imgHeight * scale);
				int x = (getWidth() - drawWidth) / 2;
				int y = (getHeight() - drawHeight) / 2;

				g.drawImage(img, x, y, drawWidth, drawHeight, this);
			}
		};

		gameInfoPanel.setOpaque(false);
		gameInfoPanel.setPreferredSize(new Dimension(260, 100));
		gameInfoPanel.add(txtrCurrentSelectedTroop, BorderLayout.CENTER);
		this.add(gameInfoPanel, BorderLayout.WEST);

		// bottom right buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
		buttonsPanel.setOpaque(false);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 20));
		buttonsPanel.add(Box.createHorizontalGlue()); // push buttons to the right

		moveButton = createToggle("Move");
		buttonsPanel.add(moveButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		aimButton = createToggle("Aim");
		buttonsPanel.add(aimButton);
		buttonsPanel.add(Box.createRigidArea(new Dimension(10, 0)));

		abilityButton = createToggle("Ability");
		buttonsPanel.add(abilityButton);

		toggleGroup = new ButtonGroup();
		toggleGroup.add(moveButton);
		toggleGroup.add(aimButton);
		toggleGroup.add(abilityButton);

		this.add(buttonsPanel, BorderLayout.CENTER);
	}

	// generic method for the creation of buttons
	private JToggleButton createToggle(String text) {
		JToggleButton btn = new JToggleButton(text);
		btn.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btn.setForeground(Color.orange);
		btn.setIcon(Icons.otherIcons.LABELBACKGROUND);
		btn.setContentAreaFilled(false);
		btn.setBorder(null);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);

		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btn.setIcon(Icons.otherIcons.LABELBACKGROUND_DARK);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				btn.setIcon(Icons.otherIcons.LABELBACKGROUND);
			}
		});
		;

		return btn;
	}

	public int getControlSelection() { // 0 move 1 aim 2 ability : in order from left to right
		if (moveButton.isSelected())
			return 0;
		else if (aimButton.isSelected())
			return 1;
		else if (abilityButton.isSelected()) {
			TroopInfo tInfo = _cntr.getCurrentTroopInfo();
			if (_cntr.isTroopSelected() && tInfo.getId() == Utils.TroopUtils.LIGHT_TROOP_ID) {
				AbilityCommand ability = new AbilityCommand(_cntr.getCurrentTroopInfo().getPos().getX(),
						tInfo.getPos().getY());
				ability.execute(_cntr);
			} else {
				return 2;
			}
		}
		return -1; // none selected or light
	}

	@Override
	public void onPlayersUpdate(Game game) {
		updateText();

	}

	@Override
	public void onBoardUpdate(Game game) {
		updateText();
	}

	@Override
	public void onTroopAction(Game game) {
		updateText();
	}

	@Override
	public void onTroopSelection(Game game) {
		updateText();
	}

	@Override
	public void onNextTurn(Game game) {
		txtrCurrentSelectedTroop.setText("Current Selected Troop:");

		if (_cntr.cpuIsPlaying() || !_cntr.isMyTurn()) {
			disableAll();
		}

		else {
			SwingUtilities.invokeLater(() -> enableAll());
		}
	}

	public void updateText() {
		if (!_cntr.isTroopSelected()) {
			txtrCurrentSelectedTroop.setText("Current Selected Troop:");
			return;
		}
		TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		String htmlInfo = "<html><div style='text-align: center;'>" + "Current Troop Selected<br>"
				+ tInfo.getId().toUpperCase() + "<br>" + "Moves Left: " + tInfo.getMovesLeft() + "<br>"
				+ "Ability Uses Left: " + tInfo.abilityUsesLeft() + "</div></html>";

		txtrCurrentSelectedTroop.setText(htmlInfo);
	}

	private void enableAll() {
		moveButton.setEnabled(true);
		aimButton.setEnabled(true);
		abilityButton.setEnabled(true);
	}

	private void disableAll() {
		moveButton.setEnabled(false);
		aimButton.setEnabled(false);
		abilityButton.setEnabled(false);
	}

	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub

	}

	public void resetControlSelection() {
		toggleGroup.clearSelection();
	}

	public void removeObserver() {
		_cntr.removeObserver(this);
	}
}
