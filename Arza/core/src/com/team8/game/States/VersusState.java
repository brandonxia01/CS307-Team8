package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by ongun on 3/4/16.
 */
public class VersusState extends State {
    //BitmapFont soon;
    Texture NoahandHisBae;
    protected VersusState(GameStateManager gsm) {
        super(gsm);
        NoahandHisBae = new Texture("definitelynoah.png");
        //soon = new BitmapFont();
        cam.setToOrtho(false, Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
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
    public void update(float dt) {handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(NoahandHisBae,0,0);
        sb.end();
    }

    @Override
    public void dispose() {
        NoahandHisBae.dispose();
    }
}
