package com.team8.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.team8.game.Board;
import com.team8.game.Game;
import java.util.Random;

public class VersusGameState extends State implements GestureDetector.GestureListener {

    Game game = new Game(0);
    Board board = game.board;
    Board board2 = game.board2;
    GestureDetector gestureDetector;
    private int prevscore = 0;
    private int currscore = 0;
    private boolean firsttime = false;
    public String scoreString;
    public String timerString;
    float initx;
    float inity;
    float minix;
    float miniy;

    private Sound leftso;
    private Sound rightso;
    private Sound downso;
    private Sound scoresound;
    private Music bgsong;

    Sprite background;
    Texture background_tex;
    Sprite frame_l;
    Sprite frame_r;
    Sprite frame_top;
    Sprite frame_bot;
    Sprite frame_block;
    Sprite frame_l_opponent;
    Sprite frame_r_opponent;
    Sprite frame_top_opponent;
    Sprite frame_bot_opponent;
    Sprite block_red;
    Sprite block_blue;
    Sprite block_yellow;
    Sprite block_green;
    Sprite block_purple;
    Sprite block_garbage;
    Sprite guideline_red;
    Sprite guideline_blue;
    Sprite guideline_yellow;
    Sprite guideline_purple;
    Sprite guideline_green;

    BitmapFont bfont;
    BitmapFont bfont2;

    protected VersusGameState(GameStateManager gsm) {

        super(gsm);
        firsttime = false;
        cam.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

        //Initialize sounds
        bgsong =Gdx.audio.newMusic(Gdx.files.internal("testristemp.mp3"));
        scoresound = Gdx.audio.newSound(Gdx.files.internal("Cymatics Weird Snare 2.wav"));
        rightso = Gdx.audio.newSound(Gdx.files.internal("rightgo.mp3"));
        leftso = Gdx.audio.newSound(Gdx.files.internal("leftgo.mp3"));
        downso = Gdx.audio.newSound(Gdx.files.internal("downgo.mp3"));

        //Initialize sprites
        //Pick random background
        Random ran = new Random();
        int bg_pick = ran.nextInt(4);
        switch(bg_pick) {
            case 0:
                background_tex = new Texture("background1.png");
                break;
            case 1:
                background_tex = new Texture("background2.png");
                break;
            case 2:
                background_tex = new Texture("background3.png");
                break;
            case 3:
                background_tex = new Texture("background4.png");
                break;
            default:
                background_tex = new Texture("background2.png");
        }
        background = new Sprite(background_tex);

        frame_l = new Sprite(new Texture("frame_side.png"));
        frame_r = new Sprite(new Texture("frame_side.png"));
        frame_top = new Sprite(new Texture("frame_top.png"));
        frame_bot = new Sprite(new Texture("frame_bottom.png"));
        frame_block = new Sprite(new Texture("frame_block.png"));

        frame_l_opponent = new Sprite(new Texture("frame_side.png"));
        frame_r_opponent = new Sprite(new Texture("frame_side.png"));
        frame_top_opponent = new Sprite(new Texture("frame_top.png"));
        frame_bot_opponent = new Sprite(new Texture("frame_bottom.png"));

        block_red = new Sprite(new Texture("block_red.png"));
        block_blue = new Sprite(new Texture("block_blue.png"));
        block_yellow = new Sprite(new Texture("block_yellow.png"));
        block_green = new Sprite(new Texture("block_green.png"));
        block_purple = new Sprite(new Texture("block_purple.png"));
        block_garbage = new Sprite(new Texture("block_garbage.png"));

        guideline_red = new Sprite(new Texture("guideline_red.png"));
        guideline_blue = new Sprite(new Texture("guideline_blue.png"));
        guideline_yellow = new Sprite(new Texture("guideline_yellow.png"));
        guideline_green = new Sprite(new Texture("guideline_green.png"));
        guideline_purple = new Sprite(new Texture("guideline_purple.png"));

        bfont = new BitmapFont();
        bfont2 = new BitmapFont();
        bfont.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        //Initialize constant sprite positions
        frame_l.setPosition(0, frame_top.getHeight());
        frame_r.setPosition(frame_l.getWidth() + (42 * 6) - 1, frame_top.getHeight());
        frame_top.setPosition(0, frame_bot.getHeight() + (12 * 42));
        frame_bot.setPosition(0, 0);

        frame_l_opponent.setPosition(frame_top.getWidth() + 32, frame_l.getHeight() + frame_top.getHeight() + 32);
        frame_r_opponent.setPosition(frame_top.getWidth() + 32 + frame_l.getWidth() + (42 * 6), frame_l.getHeight() + frame_top.getHeight() + 32);
        frame_top_opponent.setPosition(frame_top.getWidth() + 32, frame_l.getHeight() + frame_top.getHeight() + 32 + 12 * 42);
        frame_bot_opponent.setPosition(frame_top.getWidth() + 32, frame_l.getHeight() + frame_top.getHeight() + 32);

        initx = frame_l.getWidth();
        inity = frame_l.getHeight()+(42*2);
        minix = frame_top.getWidth() + 32+frame_l.getWidth()/3;
        miniy = frame_bot_opponent.getY()+  frame_bot.getHeight()/3+frame_r.getHeight()/3-42/3;

    }

    @Override
    public void update(float dt) {
        board = game.update();
        board2= game.updateMini();
        if(!firsttime)
        {
            bgsong.play();
            bgsong.setLooping(true);
            // firsttime = false;
            firsttime = bgsong.isPlaying();
        }
        handleInput();
    }

    public void drawBlock(int color, float x, float y, SpriteBatch sb) {
        switch (color) {
            case 0:
                sb.draw(block_red, x, y);
                break;
            case 1:
                sb.draw(block_blue, x, y);
                break;
            case 2:
                sb.draw(block_yellow, x, y);
                break;
            case 3:
                sb.draw(block_green, x, y);
                break;
            case 4:
                sb.draw(block_purple, x, y);
                break;
            case 5:
                sb.draw(block_garbage, x, y);
                break;
            default:
        }
    }

    public void drawGuide(int color, float x, float y, SpriteBatch sb) {
        switch (color) {
            case 0:
                sb.draw(guideline_red, x, y);
                break;
            case 1:
                sb.draw(guideline_blue, x, y);
                break;
            case 2:
                sb.draw(guideline_yellow, x, y);
                break;
            case 3:
                sb.draw(guideline_green, x, y);
                break;
            case 4:
                sb.draw(guideline_purple, x, y);
                break;
            default:
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(background, background.getX(), background.getY(),Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2);
        sb.draw(frame_top, frame_top.getX(), frame_top.getY());
        sb.draw(frame_bot, frame_bot.getX(), frame_bot.getY());
        sb.draw(frame_l, frame_l.getX(), frame_l.getY());
        sb.draw(frame_r, frame_r.getX(), frame_r.getY());
        sb.draw(frame_block, 50, frame_top.getY() + frame_top.getHeight() + 12);

        sb.draw(frame_bot_opponent,frame_bot_opponent.getX(),frame_bot_opponent.getY(),frame_bot.getWidth()/3, frame_bot.getHeight()/3);
        sb.draw(frame_l_opponent,frame_l_opponent.getX() ,frame_l_opponent.getY()+  frame_bot.getHeight()/3,frame_l.getWidth()/3, frame_l.getHeight()/3);
        sb.draw(frame_r_opponent,frame_l_opponent.getX()+frame_bot.getWidth()/3-14,frame_r_opponent.getY() +  frame_bot.getHeight()/3,frame_r.getWidth()/3, frame_r.getHeight()/3);
        sb.draw(frame_top_opponent, frame_bot_opponent.getX(), frame_bot_opponent.getY()+  frame_bot.getHeight()/3+frame_r.getHeight()/3,frame_top.getWidth()/3, frame_top.getHeight()/3);

        renderBoard(sb);
        sb.end();
    }

    public void renderBoard(SpriteBatch sb) {
        //Get coordinates in board for current controlled pieces
        int fallingX1 = game.p.getA().getLR();
        int fallingY1 = game.p.getA().getUD();
        int fallingX2 = game.p.getB().getLR();
        int fallingY2 = game.p.getB().getUD();

        //Go to retry screen if game over
        if(game.isover){
            gsm.set(new retrystate(gsm));
            dispose();
        }

        //Draw score and time
        scoreString = "Score: " + board.score;
        currscore = board.score;
        if(currscore != prevscore){
            scoresound.play(1.0f);
        }
        board.elapsed = (System.nanoTime()-board.startTime)/1000000000;
        board.min = board.elapsed / 60;
        board.sec = board.elapsed % 60;
        timerString = "Time: " + board.min + " : " + board.sec;
        bfont.draw(sb, scoreString, 5, frame_top.getY()+frame_top.getHeight() + 150);
        bfont2.draw(sb, timerString, 75, frame_top.getY() + frame_top.getHeight() + 150);
        System.nanoTime();

        //Draw next block preview
        drawBlock(game.nextp.getA().getColor(), 56, frame_top.getY()+frame_top.getHeight()+ 8+12, sb);
        drawBlock(game.nextp.getB().getColor(), 56, frame_top.getY()+frame_top.getHeight()+ 42+8+12, sb);

        //Draw each space in the board if it contains a block
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {
                if (board.board[row][cols] == null) continue;
                int color = board.board[row][cols].getColor();

                //Draw guidelines if block is part of falling piece
                if ((row == fallingY1 && cols == fallingX1) || (row == fallingY2 && cols == fallingX2)) {
                    if (row == fallingY1) {
                        int bottom = board.getBottom(cols);
                        if (row >= fallingY2) {
                            if (bottom >= row) drawGuide(color, initx + (cols * 42), inity - (bottom * 42), sb);
                        } else {
                            if (bottom >= row) drawGuide(color, initx + (cols * 42), inity - ((bottom - 1) * 42), sb);
                        }
                    }
                    else if (row == fallingY2) {
                        int bottom = board.getBottom(cols);
                        if (row >= fallingY1) {

                            if (bottom >= row) drawGuide(color, initx + (cols * 42), inity - (bottom * 42), sb);
                        } else {
                            if (bottom >= row) drawGuide(color, initx + (cols * 42), inity - ((bottom - 1) * 42), sb);
                        }
                    }
                }

                //Offset is used to give smooth falling animation. Only want it to apply to current falling piece
                //If block has reached the bottom don't apply offset
                if (row == 13) {
                    drawBlock(color, initx + (cols * 42), inity - (row * 42), sb);
                }
                //If the current location is part of the current falling piece...
                else if ((row == fallingY1 && cols == fallingX1) || (row == fallingY2 && cols == fallingX2)) {
                    //Apply offset if there's empty space below it
                    if (board.board[row + 1][cols] == null) {
                        drawBlock(color, initx + (cols * 42), inity - (row * 42) - board.offset, sb);
                    }
                    //Add exception to above if the block below it is part of the current falling piece
                    else if (row+1 == fallingY1 || row+1 == fallingY2) {
                        //Exception for if moving piece below has met an immovable block
                        if (row >= 12) { //immovable against bottom
                            drawBlock(color, initx + (cols * 42), inity - (row * 42), sb);
                        }
                        else if ((board.board[row+2][cols] != null) && (row + 2 != fallingY1 || row + 2 != fallingY2)) {
                            drawBlock(color, initx + (cols * 42), inity - (row * 42), sb);
                        } else drawBlock(color, initx + (cols * 42), inity - (row * 42) - board.offset, sb);
                    } else {
                        drawBlock(color, initx + (cols * 42), inity - (row * 42), sb);
                    }
                }
                else {
                    drawBlock(color, initx + (cols * 42), inity - (row * 42), sb);
                }
            }
        }

        //Draw opponent's board
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {
                if (board2.board[row][cols] == null) continue;
                //drawBlock(board2.board[row][cols].getColor(), minix+(cols*42/3), miniy-(row*42/3)+42*2/3, redblock.getWidth()/3,redblock.getHeight()/3, sb);
                switch (board2.board[row][cols].getColor()) {
                    case 0: //red
                        sb.draw(block_red, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, block_red.getWidth()/3, block_red.getHeight()/3);
                        break;
                    case 1: //blue
                        sb.draw(block_blue, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, block_blue.getWidth()/3, block_blue.getHeight()/3);
                        break;
                    case 2: // yellow
                        sb.draw(block_yellow, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, block_yellow.getWidth()/3, block_yellow.getHeight()/3);
                        break;
                    case 3: //green
                        sb.draw(block_green, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, block_green.getWidth()/3, block_green.getHeight()/3);
                        break;
                    case 4: // purple
                        sb.draw(block_purple, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, block_purple.getWidth()/3, block_purple.getHeight()/3);
                        break;
                    case 5:
                        sb.draw(block_garbage, minix+(cols*42/3), miniy-(row*42/3)+42*2/3, block_garbage.getWidth()/3, block_garbage.getHeight()/3);
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
        scoresound.dispose();
        leftso.dispose();
        rightso.dispose();
        downso.dispose();
        bgsong.dispose();
    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new Soloscreen(gsm));
            dispose();
        }
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        game.p.rotateCounter(board);
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", " + velocityY);

        if (velocityX > 0 && velocityX > 1.5 * velocityY) {
            game.p.moveRight(board);
            rightso.play(1.0f);
        } else if (velocityX < 0 && velocityX * - 1 > 1.5 * velocityY){
            game.p.moveLeft(board);
            //when left side of screen is touched
            leftso.play(1.0f);
        } else if (velocityY > 3 * Math.abs((int)velocityX) ) {
            for (int gig = 0; gig < 14; gig++)
                game.p.singleDrop(board);
            //when down is touched
            downso.play(1.0f);
        }

        //game.p.moveLeft(board);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
