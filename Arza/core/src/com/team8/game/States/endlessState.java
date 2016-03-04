package com.team8.game.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.team8.game.Board;
import com.team8.game.Block;
import com.team8.game.Game;



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

    Game game = new Game();
    Board board = game.board;

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
        greenblock = new Sprite(new Texture("greenblock.png"));

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

//        Gdx.input.setCatchBackKey(true);
//        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
//            // Do something
//
//            gsm.set(new Soloscreen(gsm));
//            dispose();
//        }
//        if(Gdx.input.justTouched()){
//            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//            cam.unproject(touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0));
//            Rectangle LBounds=new Rectangle(0,0,(Gdx.graphics.getWidth()/3)/2,Gdx.graphics.getHeight()/3);
//            Rectangle RBounds=new Rectangle((Gdx.graphics.getWidth()/3)/2,0,(Gdx.graphics.getWidth()/3)/2,Gdx.graphics.getHeight()/3);
//            if(LBounds.contains(touchPos.x, touchPos.y )){
//                System.out.println("left");
//                //when left side of screen is touched
//                game.p.moveLeft(board);
//            }
//            if(RBounds.contains(touchPos.x, touchPos.y )){
//                System.out.println("right");
//                //when right is touched
//                game.p.moveRight(board);
//            }
//        }

        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new Soloscreen(gsm));
            dispose();
        }
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Rectangle Dbounds = new Rectangle(0,0,(Gdx.graphics.getWidth()/3),Gdx.graphics.getHeight()/3/3);
            Rectangle LBounds=new Rectangle(0,Gdx.graphics.getHeight()/3/3,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3- Gdx.graphics.getHeight()/3/3);
            Rectangle midbound = new Rectangle((Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3/3,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/3/3);
            Rectangle RBounds=new Rectangle(2*(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3/3,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/3/3);
            if(LBounds.contains(touchPos.x, touchPos.y )){
                System.out.println("left");
                //when left side of screen is touched
                game.p.moveLeft(board);

            }
            if(Dbounds.contains(touchPos.x, touchPos.y )){
                System.out.println("down");
                //when down is touched
                for (int gig = 0; gig < 10; gig++)
                    game.p.singleDrop(board);
            }
            if(midbound.contains(touchPos.x, touchPos.y )){
                System.out.println("middle");
                //when middle of screen is touched
                game.p.rotateCounter(board);
            }
            if(RBounds.contains(touchPos.x, touchPos.y )){
                System.out.println("right");
                //when right is touched
                game.p.moveRight(board);

            }
        }

    }

    @Override
    public void update(float dt) {
        board = game.update();
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
        float initx = Lpillar.getWidth();
        float inity = Lpillar.getHeight();
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {
                if (board.board[row][cols] == null) continue;
                switch (board.board[row][cols].getColor()) {
                    case 0: //red
                        sb.draw(redblock, initx+(cols*42), inity-(row*42));
                        break;
                    case 1: //blue
                        sb.draw(blueblock, initx+(cols*42), inity-(row*42));
                        break;
                    case 2: // yellow
                        sb.draw(yellowblock, initx+(cols*42), inity-(row*42));
                        break;
                    case 3: //green
                        sb.draw(greenblock, initx+(cols*42), inity-(row*42));
                        break;
                    case 4: // purple
                        sb.draw(purpleblock, initx+(cols*42), inity-(row*42));
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
