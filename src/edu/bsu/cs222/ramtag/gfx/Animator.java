package edu.bsu.cs222.ramtag.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator{
	
	private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 5;
    
    Animation walkAnimation;
    Texture walkSheet;
    TextureRegion[] walkFrames;
    TextureRegion currentFrame;
    
    String fileName;
    
    public Animator(String fileName){
    	this.fileName = fileName;
    }
    
    public Animation create() {
    	walkSheet = new Texture(Gdx.files.internal(fileName));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);                                // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
        	for (int j = 0; j < FRAME_COLS; j++) {
        		walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.011f, walkFrames);
        return walkAnimation;
    }
}
