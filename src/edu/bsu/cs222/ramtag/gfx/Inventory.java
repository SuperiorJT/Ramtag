package edu.bsu.cs222.ramtag.gfx;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.bsu.cs222.ramtag.entities.PickableItem;
import edu.bsu.cs222.ramtag.entities.Player;

public class Inventory extends Sprite{
	
	private ArrayList<PickableItem> items = new ArrayList<PickableItem>(5);
	private ArrayList<Point> positions = new ArrayList<Point>(5);
	private Player player;
	boolean isFull = false;
	
	public Inventory(Sprite sprite, Player player){
		super(sprite);
		this.player = player;
		positions.add(new Point(10, 175));
		positions.add(new Point(60, 175));
		positions.add(new Point(10, 100));
		positions.add(new Point(60, 100));
		positions.add(new Point(10, 25));
		positions.add(new Point(60, 25));
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		spriteBatch.draw(this.getTexture(), player.getX() - 200, player.getY() - 128, 100, 256);
		for (int i = 0; i < items.size(); i++){
			if (items.get(i) != null){
				spriteBatch.draw(items.get(i).getTexture(), player.getX() - 200 + (float) positions.get(i).getX(), player.getY() - 128 + (float) positions.get(i).getY(), 32, 32);
			}
		}
	}
	
	public void add(PickableItem item)
	{
		items.add(item);
		Gdx.app.log("Inventory", "An item has been added to your inventory.");
	}

	public boolean isFull(){
		if (items.size() == 6)
			return true;
		else
			return false;
	}
	public boolean isAlmostFull(){
		if (items.size() == 5)
			return true;
		else
			return false;
	}
}
