package edu.bsu.cs222.ramtag.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import edu.bsu.cs222.ramtag.gfx.Animator;
import edu.bsu.cs222.ramtag.gfx.Inventory;

public class Player extends Sprite{
	
	private enum State{IDLE, MOVING}
	private enum Facing{NORTH, SOUTH, EAST, WEST}
	
	private static final float SPEED = 48f;
	
	private Inventory inventory = null;
	
	private int switchCounter = 0;
	
	private Vector2 velocity  = new Vector2();
	
	private TiledMapTileLayer collisionLayer;
	private TiledMapTileLayer finalRoomCollision;
	private TiledMapTileLayer conveyorLayer;
	private Cell cell = null;
	private String conveyorDirection;
	
	private Animation walkingNorth = new Animator("res/MoveNorthAnimation.png").create();
	private Animation walkingSouth = new Animator("res/MoveSouthAnimation.png").create();
	private Animation walkingEast = new Animator("res/MoveEastAnimation.png").create();
	private Animation walkingWest = new Animator("res/MoveWestAnimation.png").create();
	
	private Texture spriteFront = new Texture("res/SpriteFront.png");
	private Texture spriteBack = new Texture("res/SpriteBack.png");
	private Texture spriteLeft = new Texture("res/SpriteLeft.png");
	private Texture spriteRight = new Texture("res/SpriteRight.png");
	
	private TextureRegion currentFrame;
	private float stateTime;
	
	private State state = State.IDLE;
	private Facing facing = Facing.NORTH;
	
	private float startPosition;
	private float endPosition;

	private boolean paused = false;
	private boolean pauseChecker = true;
	private boolean textIsShown = false;

	private boolean onConveyor = false;

	private Sprite textBG = new Sprite(new Texture("res/TextfieldBackground.png"));

	
	public Player(Sprite sprite, TiledMap map){
		super(sprite);
		collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
		finalRoomCollision = (TiledMapTileLayer) map.getLayers().get("Collision2");
		conveyorLayer = (TiledMapTileLayer) map.getLayers().get("Conveyor");
		inventory = new Inventory(new Sprite(new Texture("res/InventoryScreen.png")), this);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch){
		update(Gdx.graphics.getDeltaTime());
		if (isMoving()){
			AnimateDirection(spriteBatch);
		}
		else {
			stateTime = 0f;
			switch (facing) {
			case NORTH: 
				spriteBatch.draw(spriteBack, getX(), getY());
				break;
			case SOUTH: 
				spriteBatch.draw(spriteFront, getX(), getY());
				break;
			case EAST:
				spriteBatch.draw(spriteRight, getX(), getY());
				break;
			case WEST:
				spriteBatch.draw(spriteLeft, getX(), getY());
			}
			if (paused)
				inventory.draw(spriteBatch);
		}
		if (textIsShown)
			spriteBatch.draw(textBG.getTexture(), this.getX() - 200, this.getY() - 150, 400, 85);
	}

	public void update(float deltaTime) {
		velocity.y = SPEED;
		velocity.x = SPEED;
		if (!onConveyor){
			if (!isMoving() && !paused && !textIsShown)
				handleInput(deltaTime);
			else if (paused){
				if (pauseChecker && Gdx.input.isKeyPressed(Input.Keys.I))
					paused = false;
				pauseChecker = false;
			}
			if (!Gdx.input.isKeyPressed(Input.Keys.I))
				pauseChecker = true;
			finishMovement(deltaTime);
		}
		else conveyorMovement(deltaTime);
		if (inventory.isAlmostFull()){
			collisionLayer = finalRoomCollision;
		}
	}

	private void handleInput(float deltaTime){
		if (Gdx.input.isKeyPressed(Input.Keys.W)){
			cell = collisionLayer.getCell((int) getX()/16, (int) (getY() + 16)/16);
			if (cell == null)
			{
				startPosition = getY();
				setY(getY() + velocity.y * deltaTime);
				facing = Facing.NORTH;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S)){
			cell = collisionLayer.getCell((int) getX()/16, (int) (getY() - 16)/16);
			if (cell == null){
				startPosition = getY();
				setY(getY() - velocity.y * deltaTime);
				facing = Facing.SOUTH;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.D)){
			cell = collisionLayer.getCell((int) (getX() + 16)/16, (int) getY()/16);
			if (cell == null){
				startPosition = getX();
				setX(getX() + velocity.x * deltaTime);
				facing = Facing.EAST;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.A)){
			cell = collisionLayer.getCell((int) (getX() - 16)/16, (int) getY()/16);
			if (cell == null){
				startPosition = getX();
				setX(getX() - velocity.x * deltaTime);
				facing = Facing.WEST;
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.I) && pauseChecker){
			paused = true;
			pauseChecker = false;
			Gdx.app.log("Inventory", "You have pulled up your inventory.");
		}
		
	}
	
	private void finishMovement(float deltaTime){
		switch (facing){
			case NORTH:
				if (0 != getY()%16f){
					state = State.MOVING;
					endPosition = startPosition + 16f;
					setY(getY() + velocity.y * deltaTime);
					if (getY() > endPosition || getY() == endPosition){
						setY(endPosition);
						state = State.IDLE;
					}
				}
				break;
			case SOUTH:
				if (0 != getY()%16f){
					state = State.MOVING;
					endPosition = startPosition - 16f;
					setY(getY() - velocity.y * deltaTime);
					if (getY() < endPosition || getY() == endPosition){
						setY(endPosition);
						state = State.IDLE;
					}
				}
				break;
			case EAST:
				if (0 != getX()%16f){
					state = State.MOVING;
					endPosition = startPosition + 16f;
					setX(getX() + velocity.x * deltaTime);
					if (getX() > endPosition || getX() == endPosition){
						setX(endPosition);
						state = State.IDLE;
					}
				}
				break;
			case WEST:
				if (0 != getX()%16f){
					state = State.MOVING;
					endPosition = startPosition - 16f;
					setX(getX() - velocity.x * deltaTime);
					if (getX() < endPosition || getX() == endPosition){
						setX(endPosition);
						state = State.IDLE;
					}
				}
				break;
		}
		cell = conveyorLayer.getCell((int) getX()/16, (int) getY()/16);
		
		if (cell != null && cell.getTile().getProperties().containsKey("direction") && state == State.IDLE){
			conveyorDirection = (String) cell.getTile().getProperties().get("direction");
			onConveyor = true;
		}
		else onConveyor = false;
	}
	
	public void conveyorMovement(float deltaTime){
		switch (conveyorDirection){
		case "west":
			facing = Facing.WEST;
			startPosition = getX();
			setX(getX() - velocity.x * deltaTime);
			finishMovement(deltaTime);
			break;
			
		case "east":
			facing = Facing.EAST;
			startPosition = getX();
			setX(getX() + velocity.x * deltaTime);
			finishMovement(deltaTime);
			break;
			
		case "north":
			facing = Facing.NORTH;
			startPosition = getY();
			setY(getY() + velocity.y * deltaTime);
			finishMovement(deltaTime);
			break;
			
		case "south":
			facing = Facing.SOUTH;
			startPosition = getY();
			setY(getY() - velocity.y * deltaTime);
			finishMovement(deltaTime);
			break;
		}
		cell = conveyorLayer.getCell((int) getX()/16, (int) getY()/16);
		if (cell != null && cell.getTile().getProperties().containsKey("direction") && state == State.IDLE){
			conveyorDirection = (String) cell.getTile().getProperties().get("direction");
		}
		else onConveyor = false;
	}
	
	private void AnimateDirection(SpriteBatch spriteBatch) {
		switch (facing){
			case NORTH:
				Animate(spriteBatch, walkingNorth);
				break;
			case SOUTH:
				Animate(spriteBatch, walkingSouth);
				break;
			case EAST:
				Animate(spriteBatch, walkingEast);
				break;
			case WEST:
				Animate(spriteBatch, walkingWest);
				break;
		}
		
	}
	
	private void Animate(SpriteBatch spriteBatch, Animation animation){
		stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = animation.getKeyFrame(stateTime, true);
        spriteBatch.draw(currentFrame, getX(), getY());
	}
	
	public boolean isMoving() {
		if (state == State.MOVING)
			return true;
		else
			return false;
	}
	
	public Facing getDirection()
	{
		return facing;
	}
	
	public void addItemToInventory(PickableItem item){
		inventory.add(item);
	}
	
	public boolean inventoryIsFull(){
		return inventory.isFull();
	}
	
	public void setTextIsShown(boolean textIsShown){
		this.textIsShown = textIsShown;
	}
	
	public int getSwitchCounter() {
		return switchCounter;
	}

	public void setSwitchCounter(int switchCounter) {
		this.switchCounter = switchCounter;
	}
	
}
