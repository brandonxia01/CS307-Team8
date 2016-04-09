package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by ongun on 4/7/16.
 */
public class retrystate extends State {
    private Texture retrybtn;
    private Sprite retrysprite;
    private Sound click;
    private Texture logo;
    private Sprite Slogo;
    protected retrystate(GameStateManager gsm) {
        super(gsm);
        retrybtn = new Texture("retrybtn.png");
        click = Gdx.audio.newSound(Gdx.files.internal("button-3.mp3"));
        retrysprite = new Sprite(retrybtn);
        cam.setToOrtho(false,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        logo = new Texture("logoimp.png");
        Slogo = new Sprite(logo);
        Slogo.setPosition(cam.position.x - Slogo.getWidth() / 2, cam.position.y + Slogo.getHeight());
        //Slogo.setPosition();
        retrysprite.setPosition(cam.position.x - (retrysprite.getWidth() / 2),cam.position.y- retrysprite.getHeight());
    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if(Gdx.input.justTouched()){

            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

            cam.unproject(touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Rectangle textureBounds=new Rectangle(retrysprite.getX(),retrysprite.getY(),
                    (int)retrysprite.getWidth(),(int)retrysprite.getHeight());
            Rectangle logoBounds=new Rectangle(Slogo.getX(),Slogo.getY(),
                    (int)Slogo.getWidth(),(int)Slogo.getHeight());
            if(textureBounds.contains(touchPos.x, touchPos.y )){
                click.play(1.0f);
                System.out.println("reset");
                gsm.set(new endlessState(gsm));
                dispose();
                //when left side of screen is touched
                //game.p.moveLeft(board);
                //leftso.play(1.0f);
            }
            if(logoBounds.contains(touchPos.x, touchPos.y )){
                click.play(1.0f);
                System.out.println("reset");
                gsm.set(new MenuState(gsm));
                dispose();
            }
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

        sb.draw(retrysprite, retrysprite.getX(), retrysprite.getY());
        sb.draw(Slogo,Slogo.getX(),Slogo.getY());
        sb.end();

    }

    @Override
    public void dispose() {
        retrybtn.dispose();
        logo.dispose();
    }
}
