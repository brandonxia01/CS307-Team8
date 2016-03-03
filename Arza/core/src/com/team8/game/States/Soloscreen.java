package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.team8.game.MyGdxGame;
/**
 * Created by ongun on 2/27/16.
 */
public class Soloscreen extends State {
    private Sprite Sgamemode;
    private Sprite Sendless;
    private Sprite Svsai;
    private Texture gamemode;
    private Texture endless;
    private Texture vsai;
    protected Soloscreen(GameStateManager gsm) {
        super(gsm);
        gamemode = new Texture("selectgamemode.png");
        endless = new Texture("endless.png");
        vsai = new Texture("vsai.png");
        Sgamemode = new Sprite(gamemode);
        Sendless = new Sprite(endless);
        Svsai = new Sprite(vsai);

        Sgamemode.setPosition((Gdx.graphics.getWidth() / 2) - (Sgamemode.getWidth() / 2),
                Gdx.graphics.getHeight()  - Sgamemode.getHeight()-100);
        Sendless.setPosition((Gdx.graphics.getWidth() / 2) - (Sendless.getWidth() / 2),
                Gdx.graphics.getHeight()  - Sgamemode.getHeight() -Sgamemode.getHeight() -200);
        Svsai.setPosition((Gdx.graphics.getWidth() / 2) - (Svsai.getWidth() / 2),
                Gdx.graphics.getHeight()  - Sgamemode.getHeight() -Sgamemode.getHeight() -200 - Sendless.getHeight());
    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Do something
            gsm.set(new MenuState(gsm));
            dispose();
        }
        if(Gdx.input.justTouched()){
            Rectangle textureBounds=new Rectangle((int)(Sendless.getX()),(int)Sendless.getY(),
                    (int)Sendless.getWidth(),(int)Sendless.getHeight());
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            if(textureBounds.contains(touchPos.x, touchPos.y +2*Sendless.getHeight())){
                System.out.println("touched");
                gsm.set(new endlessState(gsm));
                dispose();}
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();/*
        sb.draw(gamemode, (MyGdxGame.WIDTH / 2) - (gamemode.getWidth() / 2), MyGdxGame.HEIGHT - 100 - gamemode.getHeight());
        sb.draw(endless,(MyGdxGame.WIDTH / 2) - (endless.getWidth() / 2),MyGdxGame.HEIGHT - 100 - 2*gamemode.getHeight());
        sb.draw(vsai,(MyGdxGame.WIDTH / 2) - (vsai.getWidth() / 2),MyGdxGame.HEIGHT - 100 - 2*gamemode.getHeight() - endless.getHeight());
        */
        sb.draw(Sgamemode,Sgamemode.getX(),Sgamemode.getY());
        sb.draw(Sendless,Sendless.getX(),Sendless.getY());
        sb.draw(Svsai, Svsai.getX(),Svsai.getY());
        sb.end();
    }

    @Override
    public void dispose() {
        gamemode.dispose();
        vsai.dispose();
        endless.dispose();

    }
}
