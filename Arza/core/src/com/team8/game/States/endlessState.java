package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



/**
 * Created by ongun on 3/2/16.
 */

public class endlessState extends State {
    Sprite Lpillar;
    Sprite Rpillar;
    Sprite Ufrm;
    Sprite Dfrm;
    Sprite bs;
    protected endlessState(GameStateManager gsm) {
        super(gsm);
        Lpillar = new Sprite(new Texture("frameV.png"));
        Rpillar = new Sprite(new Texture("frameV.png"));
        Ufrm = new Sprite(new Texture("topframe.png"));
        Dfrm = new Sprite(new Texture("topframe.png"));
        bs = new Sprite(new Texture("scorestuff.png"));
        cam.setToOrtho(false,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/3);
      //  Lpillar.setScale(4*Lpillar.getScaleX(),4*Lpillar.getScaleY());
        Lpillar.setPosition(0, Ufrm.getHeight());
       // Rpillar.setScale(4 * Rpillar.getScaleX(), 4* Rpillar.getScaleY());
        Rpillar.setPosition(Lpillar.getWidth() * 2 + Lpillar.getWidth() / 2 + 42 * 6, Ufrm.getHeight());
        //Ufrm.setScale(4 * Ufrm.getScaleX(), 4 * Ufrm.getScaleY());
        Ufrm.setPosition(Lpillar.getWidth(), Lpillar.getHeight() + Dfrm.getHeight());
        //Dfrm.setScale(4 * Dfrm.getScaleX(),4 * Dfrm.getScaleY());
        Dfrm.setPosition(Lpillar.getWidth(),0);
        bs.setPosition(0,Gdx.graphics.getHeight());
    }

    @Override
    public void handleInput() {

        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Do something
            gsm.set(new Soloscreen(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(Ufrm, Ufrm.getX(), Ufrm.getY());
        sb.draw(Dfrm,Dfrm.getX(),Dfrm.getY());
        sb.draw(Lpillar,Lpillar.getX(),Lpillar.getY());
        sb.draw(Rpillar,Rpillar.getX(),Rpillar.getY());
        sb.draw(bs,bs.getX(),bs.getY());
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
