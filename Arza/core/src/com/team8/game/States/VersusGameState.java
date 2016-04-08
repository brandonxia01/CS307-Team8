package com.team8.game.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.team8.game.Board;
import com.team8.game.Block;
import com.team8.game.Game;



public class VersusGameState extends State {
    Sprite Lpillar;
    Sprite Rpillar;
    Sprite Ufrm;
    Sprite Dfrm;
    Sprite yellowblock;
    Sprite redblock;
    Sprite blueblock;
    Sprite purpleblock;
    Sprite greenblock;


    public String scoreString;
    BitmapFont bfont;
    BitmapFont bfont2;
    public String timerString;
    private Sound leftso;
    private Sound rightso;
    private Sound downso;
    private Sound scoresound;

    private Texture bg;
    private Texture yellow;
    private Texture blue;
    private Texture purple;
    private Texture red;
    private Texture green;
    private Texture sides;
    private Texture topbottom;
    Game game = new Game();
    Board board = game.board;

    private int prevscore = 0;
    private int currscore = 0;

    Sprite Lpillar2;
    Sprite Rpillar2;
    Sprite Ufrm2;
    Sprite Dfrm2;
    protected VersusGameState(GameStateManager gsm) {
        super(gsm);

        yellow = new Texture("newyellow.png");
        blue = new Texture("newblue.png");
        purple = new Texture("newpurp.png");
        red = new Texture("newred.png");
        green = new Texture("newgreen.png");
        sides = new Texture("sides.png");
        topbottom =new Texture("topbottom.png");


        Lpillar = new Sprite(sides);
        Rpillar = new Sprite(sides);
        Ufrm = new Sprite(topbottom);
        Dfrm = new Sprite(topbottom);
        Lpillar2 = new Sprite(sides);
        Rpillar2 = new Sprite(sides);
        Ufrm2 = new Sprite(topbottom);
        Dfrm2 = new Sprite(topbottom);

        yellowblock = new Sprite(yellow);
        redblock = new Sprite(red);
        blueblock = new Sprite(blue);
        purpleblock = new Sprite(purple);
        greenblock = new Sprite(green);
        bfont = new BitmapFont();
        bfont2 = new BitmapFont();

        bg = new Texture("bg.png");
        scoresound = Gdx.audio.newSound(Gdx.files.internal("Cymatics Weird Snare 2.wav"));

        rightso = Gdx.audio.newSound(Gdx.files.internal("rightgo.mp3"));
        leftso = Gdx.audio.newSound(Gdx.files.internal("leftgo.mp3"));
        downso = Gdx.audio.newSound(Gdx.files.internal("downgo.mp3"));
        cam.setToOrtho(false, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 3);

        Lpillar.setPosition(0, Ufrm.getHeight());

        Rpillar.setPosition(Lpillar.getWidth() + (42 * 6), Ufrm.getHeight());

        Ufrm.setPosition(0, 13 * 42);

        Dfrm.setPosition(0, 0);


        Lpillar2.setPosition(Ufrm.getWidth() + 32, Lpillar.getHeight() + Ufrm.getHeight() + 32);

        Rpillar2.setPosition(Ufrm.getWidth() + 32 + Lpillar.getWidth() + (42 * 6), Lpillar.getHeight() + Ufrm.getHeight() + 32);

        Ufrm2.setPosition(Ufrm.getWidth() + 32, Lpillar.getHeight() + Ufrm.getHeight() + 32 + 12 * 42);

        Dfrm2.setPosition(Ufrm.getWidth() + 32, Lpillar.getHeight() + Ufrm.getHeight() + 32);


    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new Soloscreen(gsm));
            dispose();
        }
        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0));
            Rectangle Dbounds = new Rectangle(0,0,(Gdx.graphics.getWidth()/3),Gdx.graphics.getHeight()/3/5);
            Rectangle LBounds=new Rectangle(0,Gdx.graphics.getHeight()/3/5,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3- Gdx.graphics.getHeight()/3/3);
            Rectangle midbound = new Rectangle((Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3/5,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/3/5);
            Rectangle RBounds=new Rectangle(2*(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3/5,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/3/5);
            if(LBounds.contains(touchPos.x, touchPos.y )){
                System.out.println("left");
                //when left side of screen is touched
                game.p.moveLeft(board);
                leftso.play(1.0f);
            }
            if(Dbounds.contains(touchPos.x, touchPos.y )){
                System.out.println("down");
                //when down is touched
                downso.play(1.0f);
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
                rightso.play(1.0f);
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
        sb.draw(bg,0,0,bg.getWidth()/3,bg.getHeight()/2);
        sb.draw(Ufrm, Ufrm.getX(), Ufrm.getY());
        sb.draw(Dfrm,Dfrm.getX(),Dfrm.getY());
        sb.draw(Lpillar, Lpillar.getX(), Lpillar.getY());
        sb.draw(Rpillar, Rpillar.getX(), Rpillar.getY());

        sb.draw(Dfrm2,Dfrm2.getX(),Dfrm2.getY(),Dfrm.getWidth()/3, Dfrm.getHeight()/3);
        sb.draw(Lpillar2,Lpillar2.getX() ,Lpillar2.getY()+  Dfrm.getHeight()/3,Lpillar.getWidth()/3, Lpillar.getHeight()/3);

        sb.draw(Rpillar2,Lpillar2.getX()+Dfrm.getWidth()/3-14,Rpillar2.getY() +  Dfrm.getHeight()/3,Rpillar.getWidth()/3, Rpillar.getHeight()/3);
        sb.draw(Ufrm2, Dfrm2.getX(), Dfrm2.getY()+  Dfrm.getHeight()/3+Rpillar.getHeight()/3,Ufrm.getWidth()/3, Ufrm.getHeight()/3);


        renderBoard(sb);
        sb.end();
    }

    public void renderBoard(SpriteBatch sb) {
        float initx = Lpillar.getWidth();
        float inity = Lpillar.getHeight();
        float minix = Ufrm.getWidth() + 32+Lpillar.getWidth()/3;
        float miniy = Dfrm2.getY()+  Dfrm.getHeight()/3+Rpillar.getHeight()/3-42/3; //Lpillar.getHeight() + Ufrm.getHeight() + 32;
        //currently shit looking, will make function to make sprite from color later to replace switch statements
        bfont.setColor(1.0f,1.0f,1.0f,1.0f);
        currscore = board.score;
        if(currscore != prevscore){
            scoresound.play(1.0f);
        }
        scoreString = "Score: " + board.score;
//        timerString = "Time: " + ((System.nanoTime()-startTime)/1000000000);
        board.elapsed = (System.nanoTime()-board.startTime)/1000000000;
        board.min = board.elapsed / 60;
        board.sec = board.elapsed % 60;
        timerString = "Time: " + board.min + " : " + board.sec;
        bfont2.draw(sb, timerString, 70, Ufrm.getY()+Ufrm.getHeight() + 120);
        bfont.draw(sb, scoreString, 0, Ufrm.getY() + Ufrm.getHeight() + 120);
        System.nanoTime();
        if(game.isover){
            gsm.set(new retrystate(gsm));
            dispose();


        }
        switch(game.nextp.getA().getColor()) {
            case 0:
                sb.draw(redblock, 0, Ufrm.getY()+Ufrm.getHeight());
                break;
            case 1:
                sb.draw(blueblock, 0, Ufrm.getY()+Ufrm.getHeight());
                break;
            case 2:
                sb.draw(yellowblock, 0, Ufrm.getY()+Ufrm.getHeight());
                break;
            case 3:
                sb.draw(greenblock, 0, Ufrm.getY()+Ufrm.getHeight());
                break;
            case 4:
                sb.draw(purpleblock, 0, Ufrm.getY()+Ufrm.getHeight());
                break;
            default:

        }
        switch(game.nextp.getB().getColor()) {
            case 0:
                sb.draw(redblock,0 ,Ufrm.getY()+Ufrm.getHeight() + 42);
                break;
            case 1:
                sb.draw(blueblock,0 ,Ufrm.getY()+Ufrm.getHeight() + 42);
                break;
            case 2:
                sb.draw(yellowblock,0 ,Ufrm.getY()+Ufrm.getHeight() + 42);
                break;
            case 3:
                sb.draw(greenblock,0 ,Ufrm.getY()+Ufrm.getHeight() + 42);
                break;
            case 4:
                sb.draw(purpleblock,0 ,Ufrm.getY()+Ufrm.getHeight() + 42);
                break;
            default:

        }
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {
                if (board.board[row][cols] == null) continue;
                switch (board.board[row][cols].getColor()) {
                    case 0: //red
                        sb.draw(redblock, initx+(cols*42), inity-(row*42)+42*2);
                        break;
                    case 1: //blue
                        sb.draw(blueblock, initx+(cols*42), inity-(row*42)+42*2);
                        break;
                    case 2: // yellow
                        sb.draw(yellowblock, initx+(cols*42), inity-(row*42)+42*2);
                        break;
                    case 3: //green
                        sb.draw(greenblock, initx+(cols*42), inity-(row*42)+42*2);
                        break;
                    case 4: // purple
                        sb.draw(purpleblock, initx+(cols*42), inity-(row*42)+42*2);
                        break;
                    default:
                        break;
                }

            }
        }
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {
                if (board.board[row][cols] == null) continue;
                switch (board.board[row][cols].getColor()) {
                    case 0: //red
                        sb.draw(redblock, minix+(cols*42/3), miniy-(row*42/3)+42*2/3,redblock.getWidth()/3,redblock.getHeight()/3);
                        break;
                    case 1: //blue
                        sb.draw(blueblock, minix+(cols*42/3), miniy-(row*42/3)+42*2/3,blueblock.getWidth()/3,blueblock.getHeight()/3);
                        break;
                    case 2: // yellow
                        sb.draw(yellowblock, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, yellowblock.getWidth()/3,yellowblock.getHeight()/3);
                        break;
                    case 3: //green
                        sb.draw(greenblock, minix+(cols*42/3), miniy-(row*42/3)+42*2/3,greenblock.getWidth()/3,greenblock.getHeight()/3);
                        break;
                    case 4: // purple
                        sb.draw(purpleblock,  minix+(cols*42/3), miniy-(row*42/3)+42*2/3,purpleblock.getWidth()/3,purpleblock.getHeight()/3);
                        break;
                    default:
                        break;
                }

            }
        }
        prevscore = currscore;
    }

    @Override
    public void dispose() {
        rightso.dispose();
        leftso.dispose();
        downso.dispose();
        yellow.dispose();
        blue.dispose();
        green.dispose();
        red.dispose();
        purple.dispose();
        sides.dispose();
        topbottom.dispose();
        scoresound.dispose();
        leftso.dispose();
        rightso.dispose();
        downso.dispose();
    }
}
