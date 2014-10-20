package edu.bsu.cs222.ramtag.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.bsu.cs222.ramtag.game.Ramtag;

public class EndScreen implements Screen{
	
	private Ramtag game;
	private SpriteBatch batch;
	private Texture splashTexture;
	private TextureRegion splashTextureRegion;
	
	public EndScreen(Ramtag game){
		this.game = game;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
		
		batch.begin();
		batch.draw( splashTextureRegion, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		batch.end();
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		splashTexture = new Texture("res/EndGame.png");
		splashTexture.setFilter(TextureFilter.Linear,  TextureFilter.Linear);
		splashTextureRegion = new TextureRegion(splashTexture, 0, 0, 800, 600);
		Gdx.app.log("Game Screen", "You have entered the Endgame Screen.");
		
	}

}
