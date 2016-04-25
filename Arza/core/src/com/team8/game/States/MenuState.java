package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

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

        cam.setToOrtho(false,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        //logo.
        // Ssolobtn.setScale(Ssolobtn.getScaleX()/Gdx.graphics.getWidth(),Ssolobtn.getScaleY()/Gdx.graphics.getHeight());
        Ssolobtn.setPosition(cam.position.x - (Ssolobtn.getWidth() / 2),cam.position.y);
        Svsbtn.setPosition(cam.position.x - (Svsbtn.getWidth() / 2),
                cam.position.y-Ssolobtn.getHeight());
        Ssettingsbtn.setPosition(cam.position.x - (Ssettingsbtn.getWidth() / 2),
                cam.position.y-Ssolobtn.getHeight()-Svsbtn.getHeight());
        Sprofilebtn.setPosition(cam.position.x - (Sprofilebtn.getWidth() / 2),
                cam.position.y-Ssolobtn.getHeight()-Svsbtn.getHeight()-Sprofilebtn.getHeight());

        //Gdx.graphics.getHeight()  - logo.getHeight() -logo.getHeight()/2);
        /*Svsbtn.setPosition((Gdx.graphics.getWidth() / 2) - Svsbtn.getWidth()/2,
               Gdx.graphics.getHeight() - logo.getHeight()-logo.getHeight()/2-Ssolobtn.getHeight());
        Ssettingsbtn.setPosition((Gdx.graphics.getWidth() / 2) - Ssettingsbtn.getWidth()/2,
                Gdx.graphics.getHeight() - logo.getHeight()-logo.getHeight()/2-Ssolobtn.getHeight()-Svsbtn.getHeight());
        Sprofilebtn.setPosition((Gdx.graphics.getWidth() / 2) - Sprofilebtn.getWidth()/2,
                Gdx.graphics.getHeight() - logo.getHeight()-logo.getHeight()/2-Ssolobtn.getHeight()-Svsbtn.getHeight()-Sprofilebtn.getHeight());
  */  }
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
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0));

            Rectangle textureBounds=new Rectangle(Ssolobtn.getX(),Ssolobtn.getY(),
                    (int)Ssolobtn.getWidth(),(int)Ssolobtn.getHeight());
            Rectangle VersusBounds=new Rectangle(Svsbtn.getX(),Svsbtn.getY(),
                    (int)Svsbtn.getWidth(),(int)Svsbtn.getHeight());
            Rectangle SettingsBounds=new Rectangle(Ssettingsbtn.getX(),Ssettingsbtn.getY(),
                    (int)Ssettingsbtn.getWidth(),(int)Ssettingsbtn.getHeight());


            // cam.unproject(touchPos);

            Rectangle profileBounds=new Rectangle((int)(Sprofilebtn.getX()),(int)Sprofilebtn.getY(),
                    (int)Sprofilebtn.getWidth(),(int)Sprofilebtn.getHeight());
            if(textureBounds.contains(touchPos.x,touchPos.y)){// + Ssolobtn.getHeight()+Ssolobtn.getHeight()/2)){
                //System.out.println("touched");
                gsm.set(new Soloscreen(gsm));
                dispose();}
            if(profileBounds.contains(touchPos.x,touchPos.y)){
                //System.out.println("touched");
                gsm.myGameCallback.onStartActivityLeaderboard();
                dispose();
            }
            if(VersusBounds.contains(touchPos.x,touchPos.y)){// + Ssolobtn.getHeight()+Ssolobtn.getHeight()/2)){
                //System.out.println("touched");
                gsm.set(new VersusState(gsm));
                dispose();}
            if(SettingsBounds.contains(touchPos.x,touchPos.y)){
                //System.out.println("touched");
                gsm.set(new SettingsState(gsm));
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

        sb.draw(logo, cam.position.x - logo.getWidth() / 2, cam.position.y + logo.getHeight());

        sb.draw(Ssolobtn,Ssolobtn.getX(),Ssolobtn.getY());

        sb.draw(Svsbtn,Svsbtn.getX(),Svsbtn.getY());

        sb.draw(Ssettingsbtn,Ssettingsbtn.getX(),Ssettingsbtn.getY());

        sb.draw(Sprofilebtn,Sprofilebtn.getX(),Sprofilebtn.getY());

        sb.end();
    }

}
