package edu.bsu.cs222.ramtag.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.bsu.cs222.ramtag.ui.Menu;

public class Ramtag extends Game{

	private static LwjglApplicationConfiguration cfg;

	@Override
	public void create() {
		setScreen(new Menu(this));
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}

	@Override
	public void pause() {
		super.pause();
		
	}

	@Override
	public void render() {
		super.render();
	    
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		super.resize(arg0, arg1);
		
	}

	@Override
	public void resume() {
		super.resume();
		
	}

	public static void main(String[] args) {
		cfg = new LwjglApplicationConfiguration();
		cfg.resizable = false;
		cfg.title = "Ramtag";
		cfg.width = 800;
		cfg.height = 600;
		cfg.useGL20 = true;
		cfg.vSyncEnabled = true;
		new LwjglApplication(new Ramtag(), cfg);
	}
}
