package br.com.fiap.pacman;

public class GameObject {
	private int coordX;
	private int coordY;
	private int screenSize;

	private void handleCoordinates(int coordX, int coordY) {
		setX(coordX);
		setY(coordY);
	}

	public int getX() {
		return coordX;
	}

	public int getY() {
		return coordY;
	}

	public int getScreenSize() {
		return screenSize;
	}

	public void setX(int coordX) {
		this.coordX = coordX;
	}

	public void setY(int coordY) {
		this.coordY = coordY;
	}

	public void setScreenSize(int screenSize) {
		this.screenSize = screenSize;
	}
	
	public boolean invalidCoord(int coordX, int coordY) {
		if ((coordX < 0 || coordX > 600) || (coordY < 0 || coordY > 600)) return true;
		return false;
	}
	
	public boolean invalidDirection(int direction) {
		if (direction < 0 || direction > 600) return true;
		return false;
	}

	public GameObject(int coordX, int coordY) {
		if (invalidCoord(coordX, coordY)) return;
		handleCoordinates(coordX, coordY);
	}
}
