package edu.bsu.cs222.ramtag.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PickableItem extends Sprite{

	private String property;
	private String pickupText;
	private Player player;
	private boolean inInventory = false;
	private boolean textShown = false;
	
	public PickableItem(Sprite sprite, String property, Player player){
		super(sprite);
		this.property = property;
		this.player = player;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		if (!pickedUp() && !inInventory)
		super.draw(spriteBatch);
	}
	
	public String getProperty(){
		return property;
	}
	
	public String getPickupText(){
		return pickupText;
	}
	
	public boolean pickedUp(){
		if (player.getX() == getX() && player.getY() == getY() && !inInventory){
			player.addItemToInventory(this);
			textShown = true;
			player.setTextIsShown(textShown);
			switch (property){
			case "CPU": pickupText = "You picked up the 64-bit " + property + "!";
				break;
			case "HDD": pickupText = "You picked up the 16 TB " + property + "!";
				break;
			case "RAM": pickupText = "You picked up 8 GB of " + property + "!";
				break;
			default: pickupText = "You picked up the " + property + "!";
			}
			inInventory = true;
			return true;
		}
		else if (textShown){
			if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
				textShown = false;
				player.setTextIsShown(textShown);
			}
			return true;
		}
		else return false;
	}
	
}
