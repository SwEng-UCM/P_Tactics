package PTactics.view.GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonListener;

import PTactics.control.Controller;
import PTactics.control.ControllerInterface;
import PTactics.control.TroopInfo;
import PTactics.model.game.Game;
import PTactics.utils.Direction;
import PTactics.utils.Position;
import PTactics.utils.Utils;
import PTactics.view.GameObserver;

@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel implements GameObserver {
    private JButton[][] _buttons;   
    private ControllerInterface _cntr;
    private int _height;
    private int _width;
    private ControlPanel _cPanel;
    private char _keyChar;
    private JLabel _CPUText;
    private JPanel _CPUPanel;
    private JLabel _onlineText;
    private JPanel _onlinePanel;
    private boolean _lastPlayerIsCPU;
    private boolean _lastPlayerIsOnline;
    List<Position> _pathing;
	private JPanel _boardPanel;
    
    public GameBoardPanel(int width,int height,ControllerInterface cntr, ControlPanel cPanel) {
    	this._cntr=cntr;
    	this._cPanel=cPanel;
    	this._height = height;
    	this._width = width;
    	_lastPlayerIsCPU = false;
    	_cntr.addObserver(this);
    	_pathing = new ArrayList<>();
    	this.setLayout(new CardLayout());
    	
    	String CPUText = "CPU is moving troops... ";

		_CPUText = new JLabel(CPUText);
		_CPUText.setFont(new Font("Times New Roman", Font.BOLD, 38));
		_CPUText.setForeground(Color.orange);
		_CPUText.setFocusable(false);
		_CPUText.setHorizontalAlignment(SwingConstants.CENTER);
		_CPUText.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20)); // padding
		
		_CPUPanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Icons.otherIcons.LABELBACKGROUND.getImage(), 0, 0, getWidth(), width * Position.tileSize, this);
			}
		};
		
		String OnlineText = "Other player is moving troops... ";

		_onlineText = new JLabel(OnlineText);
		_onlineText.setFont(new Font("Times New Roman", Font.BOLD, 38));
		_onlineText.setForeground(Color.orange);
		_onlineText.setFocusable(false);
		_onlineText.setHorizontalAlignment(SwingConstants.CENTER);
		_onlineText.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20)); // padding
		
		_onlinePanel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Icons.otherIcons.LABELBACKGROUND.getImage(), 0, 0, getWidth(), width * Position.tileSize, this);
			}
		};
		
		this.setOpaque(false);
		_CPUPanel.setOpaque(false);
		_CPUPanel.add(_CPUText, BorderLayout.CENTER);
		_onlinePanel.setOpaque(false);
		_onlinePanel.add(_onlineText, BorderLayout.CENTER);
		_CPUPanel.setPreferredSize(new Dimension(height * Position.tileSize, width * Position.tileSize));
		
		_boardPanel = new JPanel();
		_buttons = new JButton[height][width];
		_boardPanel.setLayout(new GridLayout(width, height));
		    for (int row = 0; row < width; row++) {
		        for (int col = 0; col < height; col++) {
		        	Position pos= new Position(col,row);
		            GameBoardCell btn = new GameBoardCell(pos,this._cntr);
		            _buttons[col][row] = btn;

		            btn.addMouseListener(new BasicButtonListener(btn) {
		            	public void mouseEntered(MouseEvent e) {
		            		if (_cPanel.getControlSelection() == 0) {
		            			_pathing = _cntr.hoverPath(btn.getPosition());
		            			TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		            			List<Position> path = _cntr.getPath();
		            			updateOnHover(tInfo, path);
		            		}
		            	} 	
		            });
		            
		            btn.addKeyListener(new KeyListener() {

		    			@Override
		    			public void keyTyped(KeyEvent e) {
		    				
		    			}

		    			@Override
		    			public void keyPressed(KeyEvent e) {
		    				_keyChar = e.getKeyChar();
		    				if (_keyChar == 'm' || _keyChar == 'M') {
		    					_cPanel.resetControlSelection();
		    					_cPanel.moveButton.setSelected(true);
		    				}
		    				else if (_keyChar == 'a' || _keyChar == 'A') {
		    					_cPanel.resetControlSelection();
		    					_cPanel.aimButton.setSelected(true);
		    				}
		    				else if (_keyChar == 'b' || _keyChar == 'B') {
		    					_cPanel.resetControlSelection();
		    					_cPanel.abilityButton.setSelected(true);
		    				}
		    				_cPanel.getControlSelection();
		    			}

		    			@Override
		    			public void keyReleased(KeyEvent e) {
		    				System.out.print(e.getKeyChar());
		    			}
		    			
		    		});
		            
		            
		            btn.addActionListener(e -> {
		                //DEBUG: System.out.println("GameWidth: " + PTactics.utils.Position._gameWidth + " Cell " + btn.getPosition().getX() + ", " + btn.getPosition().getY());
		            	if(this._cntr.isTroop(btn.getPosition())) 
		            	{
		            		String[] cmdArgs = { Utils.CommandInfo.COMMAND_SELECT_NAME , Integer.toString(btn.getPosition().getY() + 1) , Integer.toString(btn.getPosition().getX() + 1)};
		    				// + 1 because parser assumes pos + 1
		            		_cntr.executeCommand(cmdArgs);
		            		/*SelectTroopCommand select= new SelectTroopCommand(btn.getPosition().getX(),btn.getPosition().getY());
		            		select.execute(cntr);*/
		            	}
		            	else if(this._cPanel.getControlSelection()==0)
		            	{
		            		String[] cmdArgs = { Utils.CommandInfo.COMMAND_MOVE_NAME , Integer.toString(btn.getPosition().getY() + 1) , Integer.toString(btn.getPosition().getX() + 1)};
		    				// + 1 because parser assumes pos + 1
		            		_cntr.executeCommand(cmdArgs);
		            		//MoveCommand move = new MoveCommand(btn.getPosition().getX(),btn.getPosition().getY());
		            		//move.execute(cntr);
		            		//_cPanel.resetControlSelection();
		            	}
		            	else if(this._cPanel.getControlSelection()==1)
		            	{   		
		            		String[] cmdArgs = { Utils.CommandInfo.COMMAND_AIM_NAME , posToDir(btn.getPosition().getX(), btn.getPosition().getY()).toString()};
		            		_cntr.executeCommand(cmdArgs);
		            		//AimCommand aim = new AimCommand(posToDir(btn.getPosition().getX(), btn.getPosition().getY()));
		            		//aim.execute(cntr);
		            		//_cPanel.resetControlSelection();

		            	}
		            	else if(this._cPanel.getControlSelection()==2)
		            	{
		            		String[] cmdArgs = { Utils.CommandInfo.COMMAND_ABILITY_NAME , Integer.toString(btn.getPosition().getY() + 1) , Integer.toString(btn.getPosition().getX() + 1)};
		    				// + 1 because parser assumes pos + 1
		            		_cntr.executeCommand(cmdArgs);
		            		//AbilityCommand ability= new AbilityCommand(btn.getPosition().getX(),btn.getPosition().getY());
		            		//ability.execute(cntr);
		            		//_cPanel.resetControlSelection();
		            	}
		            });
		            _boardPanel.add(btn);
		        }
		    }
		    
		    add(_boardPanel, "BOARD");
		    add(_CPUPanel, "CPU");
		    add(_onlinePanel, "ONLINE");
		    if (!_cntr.isMyTurn()) {
				changeToOnline();
			}
	}
    
    @Override
	public void onPlayersUpdate(Game game) {
		TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		List<Position> path = _cntr.getPath();
		updateCells(tInfo, path);
	}

	@Override
	public void onBoardUpdate(Game game) {
		TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		List<Position> path = _cntr.getPath();
		updateCells(tInfo, path);
	}

	@Override
	public void onTroopAction(Game game) {
		TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		List<Position> path = _cntr.getPath();
		updateCells(tInfo, path);
	}

	@Override
	public void onTroopSelection(Game game) {
		TroopInfo tInfo = _cntr.getCurrentTroopInfo();
		List<Position> path = _cntr.getPath();
		updateCells(tInfo, path);
		
	}

	@Override
	public void onNextTurn(Game game) {		
		changeTurn();
	}
	
	private void changeTurn() {		
		if (_cntr.cpuIsPlaying()) {
			changeToCPU();
		}
		
		else if (!_cntr.isMyTurn()) {
			changeToOnline();
		}
		
		else {
			TroopInfo tInfo = _cntr.getCurrentTroopInfo();
			List<Position> path = _cntr.getPath();
			updateCells(tInfo, path);	
			changeToPlayer();
		}
	}
    
    private void changeToCPU() {
    	CardLayout cl =((CardLayout)getLayout());
    	cl.show(this, "CPU");
    	_boardPanel.setVisible(false);
    	_lastPlayerIsCPU = true;    	
    }
    
    private void changeToOnline() {
    	CardLayout cl =((CardLayout)getLayout());
    	cl.show(this, "ONLINE");
    	_boardPanel.setVisible(false);
    	_lastPlayerIsOnline = true;    	
    }
    
    private void changeToPlayer() {
    	if (_lastPlayerIsCPU || _lastPlayerIsOnline) {
    		CardLayout cl =((CardLayout)getLayout()); 
        	cl.show(this, "BOARD");
        	_boardPanel.setVisible(true);
			_lastPlayerIsCPU = false;
			_lastPlayerIsOnline = false;
    	}
    }
    
    private void updateCells(TroopInfo tInfo, List<Position> path) {
    	for (int row = 0; row < _width; row++) {
	        for (int col = 0; col < _height; col++) {
	        	getButton(col, row).updateCell(tInfo, path);
	        }
    	}
    }
    
    private void updateOnHover(TroopInfo tInfo, List<Position> path) {
    	for (int row = 0; row < _height; row++) {
			for (int col = 0; col < _width; col++) {
				if (_pathing != null && _pathing.contains(getButton(row, col).getPosition())) {
					getButton(row, col).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
				}
				else {
					getButton(row, col).updateCell(tInfo, path);;
				}
			}
		}
    }
    
    public GameBoardCell getButton(int row, int col) {
        return (GameBoardCell)_buttons[row][col];
    }
    private Direction posToDir(int x, int y) {
    	TroopInfo tInfo = _cntr.getCurrentTroopInfo();
    	int X = x - tInfo.getPos().getX(); // given pos minus troop position (setting 0,0 at troop)
    	int Y = y - tInfo.getPos().getY();
    	int abx = Math.abs(X); // see which axis is more prominent
    	int aby = Math.abs(Y);
    	if(abx < aby) {
    		return Y < 0? Direction.UP : Direction.DOWN;
    	}
    	else {
    		return X < 0? Direction.LEFT : Direction.RIGHT;
    	}
    	
    }

	@Override
	public void onTroopUnSelection(Game game) {
		// TODO Auto-generated method stub
		
	}

	public void removeObserver() {
		_cntr.removeObserver(this);
	}
}