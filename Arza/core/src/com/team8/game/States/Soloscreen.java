package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
    private Sound click;
    private Sound backclick;


    protected Soloscreen(GameStateManager gsm) {
        super(gsm);

        gamemode = new Texture("selectgamemode.png");
        endless = new Texture("endless.png");
        vsai = new Texture("vsai.png");
        Sgamemode = new Sprite(gamemode);
        Sendless = new Sprite(endless);
        Svsai = new Sprite(vsai);
        click = Gdx.audio.newSound(Gdx.files.internal("button-3.mp3"));
        backclick = Gdx.audio.newSound(Gdx.files.internal("button-09.mp3"));
        cam.setToOrtho(false,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        Sgamemode.setPosition(cam.position.x - (Sgamemode.getWidth() / 2),
                cam.position.y+4*Sgamemode.getHeight());
        Sendless.setPosition(cam.position.x - (Sendless.getWidth() / 2),
                cam.position.y);
        Svsai.setPosition(cam.position.x - (Svsai.getWidth() / 2),
                cam.position.y   - Sendless.getHeight());
    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            // Do something
            backclick.play(1.0f);
            gsm.set(new MenuState(gsm));

            dispose();

        }
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Rectangle textureBounds=new Rectangle((int)(Sendless.getX()),(int)Sendless.getY(),
                    (int)Sendless.getWidth(),(int)Sendless.getHeight());
            Rectangle AiBounds=new Rectangle((int)(Svsai.getX()),(int)Svsai.getY(),
                    (int)Svsai.getWidth(),(int)Svsai.getHeight());
            click.play(1.0f);
            if(textureBounds.contains(touchPos.x, touchPos.y )){
                //System.out.println("touched");
                gsm.set(new endlessState(gsm));
                dispose();}
            if(AiBounds.contains(touchPos.x, touchPos.y )){
                //System.out.println("touched");
                gsm.set(new VsaiState(gsm));
                dispose();}
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
        click.dispose();
        backclick.dispose();
    }
}
