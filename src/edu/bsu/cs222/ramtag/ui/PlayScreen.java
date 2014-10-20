package edu.bsu.cs222.ramtag.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.bsu.cs222.ramtag.entities.ImmovableObject;
import edu.bsu.cs222.ramtag.entities.PickableItem;
import edu.bsu.cs222.ramtag.entities.Player;
import edu.bsu.cs222.ramtag.game.Ramtag;

public class PlayScreen implements Screen{
	
	private Ramtag game;
	
	private BitmapFont font;
	private MyTextInputListener textInput;
	private String answer = null;
	private boolean textInputChecker = true;
	private int foodCounter = 0;
	
	private boolean pieExists = true;
	private boolean burgerExists = true;
	private boolean iceCreamExists = true;
	private boolean coffeeExists = true;
	
	private Player player;
	private ArrayList<ImmovableObject> buttons;
	private ImmovableObject pie;
	private ImmovableObject burger;
	private ImmovableObject iceCream;
	private ImmovableObject coffee;
	private ImmovableObject buttonBuffer;
	private ImmovableObject janitor;
	private ImmovableObject safe;
	private ImmovableObject sign;
	
	private PickableItem cpu;
	private PickableItem ram;
	private PickableItem gpu;
	private PickableItem monitor;
	private PickableItem hdd;
	private PickableItem mobo;
	
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private OrthographicCamera camera;

	public PlayScreen(Ramtag game){
		this.game = game;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (player.inventoryIsFull())
			game.setScreen(new EndScreen(game));
		
		camera.update();
		camera.position.set(player.getX(), player.getY(), 0);
			
		renderer.setView(camera);
		renderer.render();
		
		renderer.getSpriteBatch().begin();
		renderItems();
		janitor.draw(renderer.getSpriteBatch());
		safe.draw(renderer.getSpriteBatch());
		sign.draw(renderer.getSpriteBatch());
		
		renderFood();
		player.draw(renderer.getSpriteBatch());
		renderText();
		renderer.getSpriteBatch().end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		map = new TmxMapLoader().load("Tilesets/RAMTAGOffice.tmx");
		
		renderer = new OrthogonalTiledMapRenderer(map);
		
		player = new Player(new Sprite(new Texture("res/SpriteFront.png")), map);
		player.setPosition(1040f, 928f);
		
		buttons = new ArrayList<ImmovableObject>(15);
		for (int y = 816; y >= 768; y = y - 16){
			for (int x = 208; x <= 256; x = x + 16){
				buttonBuffer = new ImmovableObject(new Sprite(new Texture("res/SwitchInactive.png")), player);
				buttonBuffer.setPosition(x, y);
				buttons.add(buttonBuffer);
				buttonBuffer = null;
			}
		}
		
		safe = new ImmovableObject(new Sprite(new Texture("res/safe.png")), player);
		safe.setPosition(1040f, 912f);
		safe.setText("Enter the safe combination\nHINT: Check the specs of your hardware!");
		
		sign = new ImmovableObject(new Sprite(new Texture("res/sign.png")), player);
		sign.setPosition(1024f, 960f);
		sign.setText("DO NOT try to open the safe without the CPU, HDD,\nand RAM.The lock will be unsolvable otherwise!");
		
		janitor = new ImmovableObject(new Sprite(new Texture("res/Janitor.png")), player);
		janitor.setPosition(1600f, 1008f);
		janitor.setText("This thing all things devours; birds, beasts, trees, flowers;\n gnaws iron, bites steel; grinds hard stones to meal;\n slays king ruins town, and beats high mountain down.");
		
		pie = new ImmovableObject(new Sprite(new Texture("res/pie.png")), player);
		pie.setPosition(288f, 2032f);
		pie.setText("You ate the pie, nom nom nom...");
		
		burger = new ImmovableObject(new Sprite(new Texture("res/burger.png")), player);
		burger.setPosition(48f, 2000f);
		burger.setText("Oh boy, that burger was delicious....");
		
		iceCream = new ImmovableObject(new Sprite(new Texture("res/icecream.png")), player);
		iceCream.setPosition(192f, 2320f);
		iceCream.setText("*licks* This tastes great!");
		
		coffee = new ImmovableObject(new Sprite(new Texture("res/coffee.png")), player);
		coffee.setPosition(672f, 1984f);
		coffee.setText("Bleh, that coffee was cold!");
		
		font = new BitmapFont();
		
		textInput = new MyTextInputListener();
		
		cpu = new PickableItem(new Sprite(new Texture("res/CPU.png")), "CPU", player);
		cpu.setPosition(2144f, 2048f);
		
		ram = new PickableItem(new Sprite(new Texture("res/Ram.png")), "RAM", player);
		
		mobo = new PickableItem(new Sprite(new Texture("res/Motherboard.png")), "Motherboard", player);
		
		monitor = new PickableItem(new Sprite(new Texture("res/Monitor.png")), "Monitor", player);
		monitor.setPosition(1056f, 1952f);
		
		hdd = new PickableItem(new Sprite(new Texture("res/HardDrive.png")), "HardDrive", player);
		
		gpu = new PickableItem(new Sprite(new Texture("res/GraphicsCard.png")), "GPU", player);
		
		camera = new OrthographicCamera(1600, 1600);		
		camera.position.set(player.getX(), player.getY(), 0);
		camera.zoom = 0.5f;
		
		Gdx.app.log("Game Screen", "You have entered the Play Screen.");
	}
	
	public void renderItems(){
		
		cpu.draw(renderer.getSpriteBatch());
		ram.draw(renderer.getSpriteBatch());
		gpu.draw(renderer.getSpriteBatch());
		mobo.draw(renderer.getSpriteBatch());
		monitor.draw(renderer.getSpriteBatch());
		hdd.draw(renderer.getSpriteBatch());
		for (ImmovableObject o : buttons){
			o.draw(renderer.getSpriteBatch());
		}
		
	}
	
	public void renderText(){
		if (janitor.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), janitor.getText(), player.getX() - 195, player.getY() - 75);
			if (textInputChecker){
				textInputChecker = false;
				Gdx.input.getTextInput(textInput, "Riddle Answer", "Type Answer Here...");
				}
			if (answer != null){
				if (answer.equals("time")){
					
					answer = null;
					janitor.setText(null);
					player.setTextIsShown(false);
					textInputChecker = true;
					textInput.reset();
					Gdx.app.log("Riddle", "You've typed the correct answer");
					ram.setPosition(1600f, 1040f);
					Gdx.app.log("Item Placement", "The RAM has appeared on screen");
				}
				else
				{
					answer = null;
					textInputChecker = true;
					textInput.reset();
				}
			}
			answer = textInput.getInput();
		}
		if (safe.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), safe.getText(), player.getX() - 195, player.getY() - 75);
			if (textInputChecker){
				textInputChecker = false;
				Gdx.input.getTextInput(textInput, "Combination", "Separate numbers with spaces...CPU ___ ___");
				}
			if (answer != null){
				if (answer.equals("64 16 8")){
					gpu.setPosition(1056f, 912f);
					answer = null;
					safe.setText(null);
					player.setTextIsShown(false);
					textInputChecker = true;
					textInput.reset();
				}
				else
				{
					answer = null;
					textInputChecker = true;
					textInput.reset();
				}
			}
			answer = textInput.getInput();
		}
		if (sign.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), sign.getText(), player.getX() - 195, player.getY() - 75);
		}
		
		if (pie.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), pie.getText(), player.getX() - 195, player.getY() - 75);
			pieExists = false;
		}
		if (!pie.showText(player)){
			if (!pieExists){
				foodCounter++;
				pie.setText(null);
				pieExists = true;
			}
		}
		
		if (burger.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), burger.getText(), player.getX() - 195, player.getY() - 75);
			burgerExists = false;
		}
		if (!burger.showText(player)){
			if (!burgerExists){
				foodCounter++;
				burger.setText(null);
				burgerExists = true;
			}
		}
		
		if (iceCream.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), iceCream.getText(), player.getX() - 195, player.getY() - 75);
			iceCreamExists = false;
		}
		if (!iceCream.showText(player)){
			if (!iceCreamExists){
				foodCounter++;
				iceCream.setText(null);
				iceCreamExists = true;
			}
		}
		
		if (coffee.showText(player)){
			font.drawMultiLine(renderer.getSpriteBatch(), coffee.getText(), player.getX() - 195, player.getY() - 75);
			coffeeExists = false;
		}
		if (!coffee.showText(player)){
			if (!coffeeExists){
				foodCounter++;
				coffee.setText(null);
				coffeeExists = true;
			}
		}
		
		if (foodCounter == 4){
			mobo.setPosition(80f, 2384f);
			foodCounter = 5;
		}
		if (cpu.pickedUp()){
			font.draw(renderer.getSpriteBatch(), cpu.getPickupText(), player.getX() - 195, player.getY() - 100);
		}
		if (ram.pickedUp()){
			font.draw(renderer.getSpriteBatch(), ram.getPickupText(), player.getX() - 195, player.getY() - 100);
		}
		if (gpu.pickedUp()){
			font.draw(renderer.getSpriteBatch(), gpu.getPickupText(), player.getX() - 195, player.getY() - 100);
		}
		if (mobo.pickedUp()){
			font.draw(renderer.getSpriteBatch(), mobo.getPickupText(), player.getX() - 195, player.getY() - 100);
		}
		if (monitor.pickedUp()){
			font.draw(renderer.getSpriteBatch(), monitor.getPickupText(), player.getX() - 195, player.getY() - 100);
		}
		if (hdd.pickedUp()){
			font.draw(renderer.getSpriteBatch(), hdd.getPickupText(), player.getX() - 195, player.getY() - 100);
		}
		if (player.getSwitchCounter() == 16){
			hdd.setPosition(224f, 672f);
			player.setSwitchCounter(50);
		}
		
	}
	
	public void renderFood(){
		if (pie.getText() != null)
		pie.draw(renderer.getSpriteBatch());
		
		if (burger.getText() != null)
		burger.draw(renderer.getSpriteBatch());
		
		if (iceCream.getText() != null)
		iceCream.draw(renderer.getSpriteBatch());
		
		if (coffee.getText() != null)
		coffee.draw(renderer.getSpriteBatch());
	}

}

class MyTextInputListener implements TextInputListener{

	private String input;
	@Override
	public void canceled() {
		
	}

	@Override
	public void input(String input) {
		this.input = input;
	}
	
	public String getInput()
	{
		return input;
	}
	
	public void reset(){
		input = null;
	}
	
}
