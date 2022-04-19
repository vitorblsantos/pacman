package br.com.fiap.pacman;
import java.util.Random;

public class Ghost extends GameObject {
	private int direction;
	private final int[] directions = {0, 90, 180, 270};

	private int getDirection() {
		return direction;
	}
	
	private boolean handleCoordX(int value) {
		setX(value);
		return true;
	}

	private boolean handleCoordY(int value) {
		setY(value);
		return true;
	}

	private void handleDirection(int direction) {
		if(invalidDirection(direction)) return;
		setDirection(direction);
	}
	
	public void handleMove() {
		int random = new Random().nextInt(directions.length);

		switch(getDirection()) {
			case 0:
				if (getY() > 0) {
					handleCoordY(getY() - 10);
				} else if (getY() <= 0) {
					setDirection(directions[random]);
					handleCoordY(0);
				}
			break;

			case 90: 
				if (getX() < getScreenSize()) {
					handleCoordX(getX() + 10);
				} else if (getX() >= getScreenSize()) {
					setDirection(directions[random]);
					handleCoordX(getScreenSize());
				}
			break;

			case 180:
				if (getY() < getScreenSize()) {
					handleCoordY(getY() + 10);
				} else if (getY() >= getScreenSize()) {
					setDirection(directions[random]);
					handleCoordY(getScreenSize());
				}
			break;
			
			case 270:
				if (getX() > 0) {
					handleCoordX(getX() - 10);
				} else if (getX() <= 0) {
					setDirection(directions[random]);
					handleCoordX(0);
				}
			break;
		}
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Ghost(int coordX, int coordY, int initialDirection) {
		super(coordX, coordY);
		handleDirection(initialDirection);
	}
}
