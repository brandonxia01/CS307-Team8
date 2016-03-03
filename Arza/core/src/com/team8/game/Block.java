// Team 8 Arza
package com.team8.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Block {

	private int color;
	//private String picturePath;
	private int UDPosition;
	private int LRPosition;
	Texture blockpic;
	
	public Block() {
		Random r = new Random();
		// 0 = red, 1 = blue, 2 = yellow, 3 = green, 4 = purple, 
		
		// 5 = garbage

		color = r.nextInt(5);
		
		switch (color) {
			case 0: //red
				// picturePath = something
				blockpic = new Texture("redblock.png");
			break;
			case 1: //blue
				blockpic = new Texture("blueblock.png");
			break;
			case 2: // yellow
				blockpic = new Texture("yellowblock.png");
			break;
			case 3: //green
				blockpic = new Texture("purpleblock.png");
			break;
			case 4: // purple
				//blockpic = new Texture("emptyblock.png");
			break;
			case -1:
				blockpic = new Texture("emptyblock.png");
				break;
		}
	}
	public Block(int garbage) {
		// overload constructor (garbage is a special type of block);
		color = 5;
		// picturePath = something

		
	}
	public void setColor(int clr){
		color = clr;
		switch (color) {
			case 0: //red
				// picturePath = something
				blockpic = new Texture("redblock.png");
				break;
			case 1: //blue
				blockpic = new Texture("blueblock.png");
				break;
			case 2: // yellow
				blockpic = new Texture("yellowblock.png");
				break;
			case 3: //green
				blockpic = new Texture("purpleblock.png");
				break;
			case 4: // purple
				//blockpic = new Texture("emptyblock.png");
				break;
			case -1:
				blockpic = new Texture("emptyblock.png");
				break;
		}
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
