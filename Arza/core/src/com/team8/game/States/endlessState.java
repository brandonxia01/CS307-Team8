package com.team8.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



/**
 * Created by ongun on 3/2/16.
 */

public class endlessState extends State {
    Sprite Lpillar;
    Sprite Rpillar;

    protected endlessState(GameStateManager gsm) {
        super(gsm);
        Lpillar = new Sprite(new Texture("frameV.png"));
        Rpillar = new Sprite(new Texture("frameV.png"));
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
