package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by ongun on 3/3/16.
 */
public class ProfileState extends State {
    Texture barabuttlas;
    protected ProfileState(GameStateManager gsm) {
        super(gsm);
        barabuttlas = new Texture("yush.png");
    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Do something
            gsm.set(new MenuState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
       sb.begin();
        sb.draw(barabuttlas,(Gdx.graphics.getWidth() / 2) - (barabuttlas.getWidth() / 2),
                Gdx.graphics.getHeight()  - barabuttlas.getHeight()-100);
        sb.end();
    }

    @Override
    public void dispose() {
        barabuttlas.dispose();
    }
}
