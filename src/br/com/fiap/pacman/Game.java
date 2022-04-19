package br.com.fiap.pacman;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Game extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	
	private Player player = new Player(0, 0, 180);
	private Ghost ghost1 = new Ghost(0, 400, 90);
	private Ghost ghost2 = new Ghost(500,150,0);
	private Ghost ghost3 = new Ghost(300,250,0);
	private Ghost ghost4 = new Ghost(300,300,0);
	private Bomb bomb = new Bomb(100,100);
	private Booster booster = new Booster(100, 20);

	private JLabel imgPlayer = new JLabel(new ImageIcon("src/images/pacman.png"));
	private JLabel imgGhost1 = new JLabel(new ImageIcon("src/images/ghost.png"));
	private JLabel imgGhost2 = new JLabel(new ImageIcon("src/images/ghost.png"));
	private JLabel imgGhost3 = new JLabel(new ImageIcon("src/images/ghost.png"));
	private JLabel imgGhost4 = new JLabel(new ImageIcon("src/images/ghost.png"));
	private JLabel imgBomb = new JLabel(new ImageIcon("src/images/bomb.png"));
	private JLabel imgBooster = new JLabel(new ImageIcon("src/images/booster.png"));

	private final int SCREENSIZE = 600;
	private final int sleep = 1;
	
	private void init() {
		setLayout(null);
		player.setScreenSize(SCREENSIZE);
		player.setLife(15);

		ghost1.setScreenSize(SCREENSIZE);
		ghost2.setScreenSize(SCREENSIZE);
		ghost3.setScreenSize(SCREENSIZE);
		ghost4.setScreenSize(SCREENSIZE);
		
		add(imgPlayer);
		add(imgGhost1);
		add(imgGhost2);
		add(imgGhost3);
		add(imgGhost4);
		add(imgBomb);
		add(imgBooster);

		render();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(SCREENSIZE + 100, SCREENSIZE + 100);
		setVisible(true);
		addKeyListener(this);

		run();
	}

	private void render() {
		updateLocation(imgPlayer, player);
		updateLocation(imgGhost1, ghost1);
		updateLocation(imgGhost2, ghost2);
		updateLocation(imgGhost3, ghost3);
		updateLocation(imgGhost4, ghost4);
		updateLocation(imgBomb, bomb);
		updateLocation(imgBooster, booster);
		setTitle("Life: " + player.getLife());
		SwingUtilities.updateComponentTreeUI(this);
	}

	private void updateLocation(JLabel label, GameObject object) {
		label.setBounds(object.getX(), object.getY(), 50, 50);
		ImageIcon myImage = (ImageIcon) label.getIcon();
        Image img = myImage.getImage();
        Image newImg = img.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
        label.setIcon( new ImageIcon(newImg) );
	}

	private void run() {
		while (player.getLife() > 0) {

			boolean collisionBomb = (bomb.getX() == player.getX() && bomb.getY() == player.getY());
			boolean collisionBooster = (booster.getX() == player.getX() && booster.getY() == player.getY());
			boolean collisionGhost1 = (ghost1.getX() == player.getX() && ghost1.getY() == player.getY());
			boolean collisionGhost2 = (ghost2.getX() == player.getX() && ghost2.getY() == player.getY());
			boolean collisionGhost3 = (ghost3.getX() == player.getX() && ghost3.getY() == player.getY());
			boolean collisionGhost4 = (ghost4.getX() == player.getX() && ghost4.getY() == player.getY());
			boolean invencible = player.getInvencible();

			// I do this multiplication so that the value is always a multiple of 10
			// e.g., 600 / 10 = 6 * 10 = 60
		    Random rand = new Random();
			int randomX = (rand.nextInt(SCREENSIZE / 10)*10);
			int randomY = (rand.nextInt(SCREENSIZE / 10)*10);

			player.handleMove();
			ghost1.handleMove();
			ghost2.handleMove();
			ghost3.handleMove();
			ghost4.handleMove();
			
			if (collisionBooster) {
				booster.setX(randomX);
				booster.setY(randomX);
				remove(imgBooster);
				player.setInvencible(true);

				CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
					add(imgBooster);
					player.setInvencible(false);
				});
			}

			if(!invencible) {
				if (collisionBomb) {
					bomb.setX(randomX);
					bomb.setY(randomY);
				}
				
				if (collisionGhost1) {
					ghost1.setX(randomX);
					ghost1.setY(randomY);
				}

				if (collisionGhost2) {
					ghost2.setX(randomX);
					ghost2.setY(randomY);
				}
				
				if (collisionGhost3) {
					ghost3.setX(randomX);
					ghost3.setY(randomY);
					ghost3.handleMove();
				}
				
				if (collisionGhost4) {
					ghost4.setX(randomX);
					ghost4.setY(randomY);
				}

				if ((collisionBomb || collisionGhost1 || collisionGhost2 || collisionGhost3 || collisionGhost4)) {
					player.setLife(player.getLife() - 1);
				}
			}
			
			try {
				Thread.sleep(sleep);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			render();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (c == '8' || c == 'w') player.setDirection(0);
		if (c == '6' || c == 'd') player.setDirection(90);
		if (c == '2' || c == 's') player.setDirection(180);
		if (c == '4' || c == 'a') player.setDirection(270);
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	public static void main(String[] args) {
		new Game().init();
	}
}
