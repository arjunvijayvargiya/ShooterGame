package kiloboltgame;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class StartingClass extends Applet implements Runnable,KeyListener{
	private Robot robot;
	private Image image, currentSprite, character, characterDown, characterJumped, background;
	private Graphics second;
	private URL base;
	private static Background bg1, bg2;
	
	@Override
    public void init() {
        // TODO Auto-generated method stub
		setSize(800, 480);
        setBackground(Color.BLACK);
        setFocusable(true);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("SKY-BOT");
       
        AudioClip gong = getAudioClip(getDocumentBase(), "data/tetris.au");
	    gong.play();
        addKeyListener(this);
        try {
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


		// Image Setups
		character = getImage(base, "data/character.png");
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		currentSprite = character;
		background = getImage(base, "data/painter.png");
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
    	bg1 = new Background(0, 0);
    	bg2 = new Background(2160, 0);

    	robot=new Robot();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
       
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
     
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			robot.update();
			if (robot.isJumped()){
				currentSprite = characterJumped;
			}else if (robot.isJumped() == false && robot.isDucked() == false){
				currentSprite = character;
			}
			bg1.update();
			bg2.update();
			repaint();
	        try {
	            Thread.sleep(17);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	}

	  @Override
	    public void keyPressed(KeyEvent e) {

	        switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            System.out.println("Move up");
	            break;

	        case KeyEvent.VK_DOWN:
	            currentSprite = characterDown;
	            if (robot.isJumped() == false){
	                robot.setDucked(true);
	                robot.setSpeedX(0);
	            }
	            break;

	        case KeyEvent.VK_LEFT:
	            robot.moveLeft();
	            robot.setMovingLeft(true);
	            break;

	        case KeyEvent.VK_RIGHT:
	            robot.moveRight();
	            robot.setMovingRight(true);
	            break;

	        case KeyEvent.VK_SPACE:
	            robot.jump();
	            break;

	        }

	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	        switch (e.getKeyCode()) {
	        case KeyEvent.VK_UP:
	            System.out.println("Stop moving up");
	            break;

	        case KeyEvent.VK_DOWN:
	            currentSprite = character;
	            robot.setDucked(false);
	            break;

	        case KeyEvent.VK_LEFT:
	            robot.stopLeft();
	            break;

	        case KeyEvent.VK_RIGHT:
	            robot.stopRight();
	            break;

	        case KeyEvent.VK_SPACE:
	            break;

	        }

	    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}


		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);


		g.drawImage(image, 0, 0, this);

		
	}
	 @Override
		public void paint(Graphics g) {
			
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);

		}
	 public static Background getBg1() {
	        return bg1;
	    }

	    public static Background getBg2() {
	        return bg2;
	    }

}
