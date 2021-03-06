package com.team8.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team8.game.States.GameStateManager;
import com.team8.game.States.MenuState;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	BitmapFont font;
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Arza";
    private GameStateManager gsm;
    @Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("logo.png");
        gsm = new GameStateManager();
		font = new BitmapFont();
        font.setColor(Color.WHITE);
        gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
      //  batch.begin();
	//	batch.draw(img, 0, 0);
		//batch.end();
	}
}
