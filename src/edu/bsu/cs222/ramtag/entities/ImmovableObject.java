package edu.bsu.cs222.ramtag.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ImmovableObject extends Sprite{
	
	private String text = null;
	private Player player;
	private boolean activationChecker = false;
	private boolean keyIsNotPressed = true;
	private boolean switchActivated = false;
	private boolean switchValue;
	
	public ImmovableObject(Sprite sprite, Player player){
		super(sprite);
		this.player = player;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		super.draw(spriteBatch);
		isPressed(player.getSwitchCounter());
		showText(player);
	}
	
	public void isPressed(int counter){
		if (!switchActivated){
			if (getX() == player.getX() && getY() == player.getY()){
				if (switchValue){
					switchValue = false;
					this.setTexture(new Texture("res/SwitchInactive.png"));
					counter--;
				}
				else{
					switchValue = true;
					this.setTexture(new Texture("res/SwitchActive.png"));
					counter++;
				}
				switchActivated = true;
			}
		}
		else if (!(getX() == player.getX()) || !(getY() == player.getY()))
			switchActivated = false;
		player.setSwitchCounter(counter);
		
	}
	
	public boolean hasText(){
		if (text != null)
			return true;
		else
			return false;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public boolean showText(Player player){
		if (hasText()){
			if (keyIsNotPressed){
				if (player.getX() == getX() && (player.getY() + 16f) == getY() && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
					if (activationChecker)
					{
						activationChecker = false;
						keyIsNotPressed = false;
						player.setTextIsShown(activationChecker);
					}
					else{
						activationChecker = true;
						player.setTextIsShown(activationChecker);
						keyIsNotPressed = false;
					}
					return activationChecker; 
				}
				else if (activationChecker && Gdx.input.isKeyPressed(Input.Keys.ENTER)){
					activationChecker = false;
					keyIsNotPressed = false;
					player.setTextIsShown(activationChecker);
					return false;
				}
			}
			else if (!keyIsNotPressed && !Gdx.input.isKeyPressed(Input.Keys.ENTER)){
				keyIsNotPressed = true;
				return activationChecker;
			}
			return activationChecker;
		}
		else return false;
	}

}
