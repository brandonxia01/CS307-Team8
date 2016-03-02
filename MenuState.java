package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.team8.game.MyGdxGame;

//import java.awt.Rectangle;

/**
 * Created by ongun on 2/23/16.
 */
public class MenuState extends State {
    private Sprite Slogo;
    private Sprite Ssolobtn;
    private Sprite Svsbtn;
    private Sprite Ssettingsbtn;
    private Sprite Sprofilebtn;
    private Texture logo;
    private Texture solobtn;
    private Texture vsbtn;
    private Texture settingsbtn;
    private Texture profilebtn;
    //butonlari ekle
    public MenuState(GameStateManager gsm) {
        super(gsm);
        logo = new Texture("logoimp.png");
        solobtn = new Texture("solo.png");
        vsbtn = new Texture("versus.png");
        settingsbtn = new Texture("settings.png");
        profilebtn = new Texture("myprofile.png");
        Slogo = new Sprite(logo);
        Ssolobtn = new Sprite(solobtn);
        Svsbtn = new Sprite(vsbtn);
        Ssettingsbtn = new Sprite(settingsbtn);
        Sprofilebtn = new Sprite(profilebtn);
       // logo.
       Ssolobtn.setPosition((Gdx.graphics.getWidth() / 2) - (Ssolobtn.getWidth() / 2),
               Gdx.graphics.getHeight()  - Slogo.getHeight() -Slogo.getHeight()/2);
        Svsbtn.setPosition((Gdx.graphics.getWidth() / 2) - Svsbtn.getWidth()/2,
               Gdx.graphics.getHeight() - Slogo.getHeight()-Slogo.getHeight()/2-Ssolobtn.getHeight());
        Ssettingsbtn.setPosition((Gdx.graphics.getWidth() / 2) - Ssettingsbtn.getWidth()/2,
                Gdx.graphics.getHeight() - Slogo.getHeight()-Slogo.getHeight()/2-Ssolobtn.getHeight()-Svsbtn.getHeight());
        Sprofilebtn.setPosition((Gdx.graphics.getWidth() / 2) - Sprofilebtn.getWidth()/2,
                Gdx.graphics.getHeight() - Slogo.getHeight()-Slogo.getHeight()/2-Ssolobtn.getHeight()-Svsbtn.getHeight()-Sprofilebtn.getHeight());
    }
    @Override
    public void dispose(){
        logo.dispose();
        solobtn.dispose();
        vsbtn.dispose();
        settingsbtn.dispose();
        profilebtn.dispose();
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Rectangle textureBounds=new Rectangle((int)(Ssolobtn.getX()),(int)Ssolobtn.getY(),
                    (int)Ssolobtn.getWidth(),(int)Ssolobtn.getHeight());
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
          // cam.unproject(touchPos);

            if(textureBounds.contains(touchPos.x, touchPos.y + Ssolobtn.getHeight()+Ssolobtn.getHeight()/2)){
                System.out.println("touched");
           gsm.set(new Soloscreen(gsm));
            dispose();}
        }
    }

    @Override
    public void update(float dt) {
    handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(logo, (MyGdxGame.WIDTH / 2) - (logo.getWidth() / 2), MyGdxGame.HEIGHT - logo.getHeight(), 330, 250);/*
        sb.draw(solobtn,(MyGdxGame.WIDTH / 2) - (solobtn.getWidth() / 2), MyGdxGame.HEIGHT - logo.getHeight() -logo.getHeight()/2);
        sb.draw(vsbtn,(MyGdxGame.WIDTH / 2) - (vsbtn.getWidth() / 2),
                MyGdxGame.HEIGHT - logo.getHeight()-logo.getHeight()/2-solobtn.getHeight());
        sb.draw(settingsbtn,(MyGdxGame.WIDTH / 2) - (settingsbtn.getWidth() / 2),
                MyGdxGame.HEIGHT - logo.getHeight() -logo.getHeight()/2-solobtn.getHeight()-vsbtn.getHeight());
        sb.draw(profilebtn,(MyGdxGame.WIDTH / 2) - (settingsbtn.getWidth() / 2),
                MyGdxGame.HEIGHT - logo.getHeight()-logo.getHeight()/2-solobtn.getHeight()-vsbtn.getHeight()-profilebtn.getHeight());
       */
        sb.draw(Ssolobtn,Ssolobtn.getX(),Ssolobtn.getY());
        sb.draw(Svsbtn,Svsbtn.getX(),Svsbtn.getY());
        sb.draw(Ssettingsbtn,Ssettingsbtn.getX(),Ssettingsbtn.getY());
        sb.draw(Sprofilebtn,Sprofilebtn.getX(),Sprofilebtn.getY());
        sb.end();
    }

}
