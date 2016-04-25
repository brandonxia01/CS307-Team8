// Team 8 Arza
package com.team8.game;
import java.util.Random;

public class Block {

	private int color;
	private String picturePath;
	private int UDPosition;
	private int LRPosition;

	public Block(int c, int zero) {
		color = c;
	}

	public Block() {
		Random r = new Random();
		// 0 = red, 1 = blue, 2 = yellow, 3 = green, 4 = purple, 

		// 5 = garbage

		color = r.nextInt(5);

		switch (color) {
			case 0: //red
				// picturePath = something
				break;
			case 1: //blue

				break;
			case 2: // yellow

				break;
			case 3: //green

				break;
			case 4: // purple

				break;
		}
	}
	public Block(int garbage) {
		// overload constructor (garbage is a special type of block);
		color = 5;

	}

	public int getUD() {
		return this.UDPosition;
	}
	public int getLR() {
		return this.LRPosition;
	}
	public void setUD(int newUD) {
		this.UDPosition = newUD;
	}
	public void setLR(int newLR) {
		this.LRPosition = newLR;
	}

	public int getColor() {
		return this.color;
	}


}