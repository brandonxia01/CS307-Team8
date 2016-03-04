package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.team8.game.Board;
import com.team8.game.Block;


/**
 * Created by ongun on 3/2/16.
 */

public class endlessState extends State {
    Sprite Lpillar;
    Sprite Rpillar;
    Sprite Ufrm;
    Sprite Dfrm;
    Sprite bs;
    Sprite yellowblock;
    Sprite redblock;
    Sprite blueblock;
    Sprite purpleblock;
    Sprite greenblock;
    protected endlessState(GameStateManager gsm) {
        super(gsm);
        Lpillar = new Sprite(new Texture("frameV.png"));
        Rpillar = new Sprite(new Texture("frameV.png"));
        Ufrm = new Sprite(new Texture("topframe.png"));
        Dfrm = new Sprite(new Texture("topframe.png"));
        bs = new Sprite(new Texture("scorestuff.png"));
        yellowblock = new Sprite(new Texture("yellowblock.png"));
        redblock = new Sprite(new Texture("redblock.png"));
        blueblock = new Sprite(new Texture("blueblock.png"));
        purpleblock = new Sprite(new Texture("purpleblock.png"));
        greenblock = new Sprite(new Texture("emptyblock.png"));

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
        renderBoard(sb);
        sb.end();
    }

    public void renderBoard(SpriteBatch sb) {
        Board b = new Board(6, 14);
        b.board[0][0] = new Block(1, 0);
        b.board[1][1] = new Block(2, 0);
        b.board[2][2] = new Block(3, 0);
        b.board[3][3] = new Block(4, 0);
        b.board[4][4] = new Block(5, 0);
        float initx = Lpillar.getWidth();
        float inity = Dfrm.getHeight();
        for(int cols = 0; cols < 6; cols++) {
            for (int row = 0; row < 12; row++) {
                if (b.board[row][cols] == null) continue;
                switch (b.board[row][cols].getColor()) {
                    case 0: //red
                        sb.draw(redblock, initx+(cols*42), inity+(row*42));
                        break;
                    case 1: //blue
                        sb.draw(blueblock, initx+(cols*42), inity+(row*42));
                        break;
                    case 2: // yellow
                        sb.draw(yellowblock, initx+(cols*42), inity+(row*42));
                        break;
                    case 3: //green
                        sb.draw(greenblock, initx+(cols*42), inity+(row*42));
                        break;
                    case 4: // purple
                        sb.draw(purpleblock, initx+(cols*42), inity+(row*42));
                        break;
                    default:
                        break;
                }

            }
        }
    }

    @Override
    public void dispose() {

    }
}
