package br.com.fiap.pacman;

public class Player extends GameObject {
	private int direction;
	private boolean invencible;
	private int life;

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
	
	public boolean getInvencible() {
		return invencible;
	}

	public int getLife() {
		return life;
	}

	public boolean handleMove() {
		switch(getDirection()) {
			case 0:
				if (getY() - 10 == 0) return handleCoordY(0);
				if (getY() - 10 > 0) return handleCoordY(getY() - 10);
			break;

			case 90:
				if (getX() + 10 == getScreenSize()) return handleCoordX(getScreenSize());
				if (getX() + 10 < getScreenSize()) return handleCoordX(getX() + 10);
			break;
				
			case 180:
				if (getY() + 10 == getScreenSize()) return handleCoordY(getScreenSize());
				if (getY() + 10 < getScreenSize()) return handleCoordY(getY() + 10);
			break;
			
			case 270:
				if (getX() - 10 == 0) return handleCoordX(0);
				if (getX() - 10 > 0) return handleCoordX(getX() - 10);
			break;
		} 
		return false;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setLife(int currentLife) {
		this.life = currentLife;
	}

	public void setInvencible(boolean invencible) {
		this.invencible = invencible;
	}

	public Player(int coordX, int coordY, int initialDirection) {
		super(coordX, coordY);
		handleDirection(initialDirection);
	}
}
