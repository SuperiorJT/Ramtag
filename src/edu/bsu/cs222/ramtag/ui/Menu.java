package edu.bsu.cs222.ramtag.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import edu.bsu.cs222.ramtag.game.Ramtag;

public class Menu implements Screen {
	
	private Stage stage;
	private Skin skin;
	private List list;
	private ScrollPane scrollPane;
	private Table table;
	private int index = 0;
	private boolean isPressed = true;
	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	private SpriteBatch batch;
	
	private Ramtag game;

	
	public Menu(Ramtag game){
		this.game = game;
	}

	@Override
	public void dispose(){
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (isPressed){
				index -= 1;
				if (index < 0) index = 2;
				list.setSelectedIndex(index);
				isPressed = false;
			}
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.S)) isPressed = true;
		
		
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (isPressed){
				index += 1;
				if (index > 2) index = 0;
				list.setSelectedIndex(index);
				isPressed = false;
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			if (index == 0)
				game.setScreen(new PlayScreen(game));
			if (index == 1)
				game.setScreen(new Instructions(game));
			if (index == 2)
				Gdx.app.exit();
		}
		
		batch.begin();
		batch.draw( splashTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		batch.end();
		
		stage.act(delta);
		stage.draw();
		
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin(Gdx.files.internal("res/uiskin.json"));
		
		list = new List(new String[] {"Start Game", "Instructions", "Exit"}, skin);
		
		batch = new SpriteBatch();
		splashTexture = new Texture("res/RamtagMenu.png");
		splashTexture.setFilter(TextureFilter.Linear,  TextureFilter.Linear);
		splashTextureRegion = new TextureRegion(splashTexture, 0, 0, 800, 600);
		
		scrollPane = new ScrollPane(list, skin);
		scrollPane.setSize(250, 500);
		
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.add(scrollPane);
		
		stage.addActor(table);
		Gdx.app.log("Game Screen", "You have entered the Main Menu.");
		
	}
}
