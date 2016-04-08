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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.team8.game.Board;
import com.team8.game.Block;
import com.team8.game.Game;



public class VsaiState extends State implements GestureDetector.GestureListener {
    Sprite Lpillar;
    Sprite Rpillar;
    Sprite Ufrm;
    Sprite Dfrm;
    Sprite yellowblock;
    Sprite redblock;
    Sprite blueblock;
    Sprite purpleblock;
    Sprite greenblock;
    private Music bgsong;
    GestureDetector gestureDetector;
    Sprite frame_block;
    Sprite block_garbage;
    Sprite guideline_red;
    Sprite guideline_blue;
    Sprite guideline_yellow;
    Sprite guideline_purple;
    Sprite guideline_green;
    Sprite exo;
    Sprite ex;
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
    Board board2 = game.board2;
    int rotatetimer = 0;


    private int prevscore = 0;
    private int currscore = 0;

    Sprite Lpillar2;
    Sprite Rpillar2;
    Sprite Ufrm2;
    Sprite Dfrm2;

    private boolean firsttime;
    protected VsaiState(GameStateManager gsm) {
        super(gsm);
        firsttime = false;
        yellow = new Texture("newyellow.png");
        blue = new Texture("newblue.png");
        purple = new Texture("newpurp.png");
        red = new Texture("newred.png");
        green = new Texture("newgreen.png");
        sides = new Texture("sides.png");
        topbottom =new Texture("topbottom.png");

        frame_block = new Sprite(new Texture("frame_block.png"));
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

        bgsong =Gdx.audio.newMusic(Gdx.files.internal("testristemp.mp3"));
        block_garbage = new Sprite(new Texture("block_garbage.png"));
        guideline_blue = new Sprite(new Texture("guideline_blue.png"));
        guideline_red = new Sprite(new Texture("guideline_red.png"));
        guideline_yellow = new Sprite(new Texture("guideline_yellow.png"));
        guideline_green = new Sprite(new Texture("guideline_green.png"));
        guideline_purple = new Sprite(new Texture("guideline_purple.png"));
        ex = new Sprite(blueblock);
        exo = new Sprite(redblock);


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
        exo.setPosition(300, Ufrm.getY() + Ufrm.getHeight() + 120);

        ex.setPosition(300, Ufrm.getY() + Ufrm.getHeight() + 120 + 42);
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);

    }

    @Override
    public void handleInput() {
        Gdx.input.setCatchBackKey(true);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            gsm.set(new Soloscreen(gsm));
            dispose();
        }
        if(Gdx.input.justTouched()){

        }

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

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, 0, 0, bg.getWidth() / 3, bg.getHeight() / 2);
        sb.draw(Ufrm, Ufrm.getX(), Ufrm.getY());
        sb.draw(Dfrm,Dfrm.getX(),Dfrm.getY());
        sb.draw(Lpillar, Lpillar.getX(), Lpillar.getY());
        sb.draw(Rpillar, Rpillar.getX(), Rpillar.getY());

        sb.draw(Dfrm2,Dfrm2.getX(),Dfrm2.getY(),Dfrm.getWidth()/3, Dfrm.getHeight()/3);
        sb.draw(Lpillar2,Lpillar2.getX() ,Lpillar2.getY()+  Dfrm.getHeight()/3,Lpillar.getWidth()/3, Lpillar.getHeight()/3);

        sb.draw(Rpillar2,Lpillar2.getX()+Dfrm.getWidth()/3-14,Rpillar2.getY() +  Dfrm.getHeight()/3,Rpillar.getWidth()/3, Rpillar.getHeight()/3);
        sb.draw(Ufrm2, Dfrm2.getX(), Dfrm2.getY()+  Dfrm.getHeight()/3+Rpillar.getHeight()/3,Ufrm.getWidth()/3, Ufrm.getHeight()/3);
        sb.draw(frame_block, 50, Ufrm.getY() + Ufrm.getHeight() + 12);
        sb.draw(exo, 300, Ufrm.getY()+Ufrm.getHeight()+120);

        renderBoard(sb);
        sb.end();
    }

    String rotateType = "none";
    public void renderBoard(SpriteBatch sb) {
        float initx = Lpillar.getWidth();
        float inity = Lpillar.getHeight()+(42*2);
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
        bfont2.draw(sb, timerString, 70, Ufrm.getY()+Ufrm.getHeight() + 150);
        bfont.draw(sb, scoreString, 5, Ufrm.getY() + Ufrm.getHeight() + 150);
        System.nanoTime();
        if(game.isover){
            gsm.set(new retrystate(gsm));
            dispose();


        }/*
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
        }*/
        switch(game.nextp.getA().getColor()) {
            case 0:
                sb.draw(redblock, 56, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 1:
                sb.draw(blueblock, 56, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 2:
                sb.draw(yellowblock, 56, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 3:
                sb.draw(greenblock, 56, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 4:
                sb.draw(purpleblock, 56, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            default:
        }
        switch(game.nextp.getB().getColor()) {
            case 0:
                sb.draw(redblock,56 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 1:
                sb.draw(blueblock,56 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 2:
                sb.draw(yellowblock,56 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 3:
                sb.draw(greenblock,56 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 4:
                sb.draw(purpleblock,56 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            default:

        }
        int fallingX1 = game.p.getA().getLR();
        int fallingY1 = game.p.getA().getUD();
        int fallingX2 = game.p.getB().getLR();
        int fallingY2 = game.p.getB().getUD();

        Sprite spr;
        Sprite guide_spr;

        boolean draw = true;

        int rotateX = 0;
        int rotateY = 0;
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {

                draw = true;
                if (board.board[row][cols] == null) continue;
                switch (board.board[row][cols].getColor()) {
                    case 0: //red
                        spr = redblock;
                        guide_spr = guideline_red;
                        break;
                    case 1: //blue
                        spr = blueblock;
                        guide_spr = guideline_blue;
                        break;
                    case 2: // yellow
                        spr = yellowblock;
                        guide_spr = guideline_yellow;
                        break;
                    case 3: //green
                        spr = greenblock;
                        guide_spr = guideline_green;
                        break;
                    case 4: // purple
                        spr = purpleblock;
                        guide_spr = guideline_purple;
                        break;
                    case 5: // garbage
                        spr = block_garbage;
                        guide_spr = guideline_red;
                        break;
                    default:
                        spr = redblock;
                        guide_spr = guideline_red;
                        draw=false;
                        break;
                }
                if (!draw) continue;

                //Print guidelines if block is part of falling piece
                if ((row == fallingY1 && cols == fallingX1) || (row == fallingY2 && cols == fallingX2)) {
                    if (row == fallingY1) {
                        int bottom = board.getBottom(cols);
                        if (row >= fallingY2) {
                            if (bottom >= row) sb.draw(guide_spr, initx+(cols*42), inity-(bottom*42));
                        }
                        else {
                            if (bottom >= row) sb.draw(guide_spr, initx+(cols*42), inity-((bottom-1)*42));
                        }
                    }
                    else if (row == fallingY2) {
                        int bottom = board.getBottom(cols);
                        if (row >= fallingY1) {

                            if (bottom >= row) sb.draw(guide_spr, initx+(cols*42), inity-(bottom*42));
                        }
                        else {
                            if (bottom >= row) sb.draw(guide_spr, initx+(cols*42), inity-((bottom-1)*42));
                        }
                    }
                }

                if (game.p.currentRotate[0] == fallingX1 || game.p.currentRotate[0] == fallingX2 || game.p.currentRotate[1]==fallingY1 || game.p.currentRotate[1]==fallingY1) {
                    //System.out.println("rotate state");
                    float diffX = ex.getX() - exo.getX();
                    float diffY = ex.getY() - exo.getY();


                    if (rotateType.equals("none")) {
                        if (diffY == 42) rotateType = "topleft";
                        else if (diffX == -42) rotateType = "leftbottom";
                        else if (diffY == -42) rotateType = "bottomright";
                        else if (diffX == 42) rotateType = "righttop";
                    }

                    if (rotateType.equals("topleft")) {
                        if (!(ex.getY()==exo.getY())) {
                            ex.setPosition(ex.getX()-1, ex.getY()-1);
                            //set rotX and Y
                            //rotateX--; rotateY--;
                        }
                        if (ex.getY()==exo.getY()) {
                            System.out.println("reset");
                            rotateType="none";
                            game.p.currentRotate[0] = -1;
                            game.p.currentRotate[1] = -1;
                        }
                    }
                    else if (rotateType.equals("leftbottom")) {
                        System.out.printf("reset %f %f\n", diffX, diffY);
                        if (!(ex.getX()==exo.getX())) {
                            ex.setPosition(ex.getX()+1, ex.getY()-1);
                        }
                        else {
                            rotateType="none";
                            game.p.currentRotate[0] = -1;
                            game.p.currentRotate[1] = -1;
                        }
                    }
                    else if (rotateType.equals("bottomright")) {
                        if (!(ex.getY()==exo.getY())) {
                            ex.setPosition(ex.getX()+1, ex.getY()+1);
                        }
                        else {
                            System.out.println("reset");
                            rotateType="none";
                            game.p.currentRotate[0] = -1;
                            game.p.currentRotate[1] = -1;
                        }
                    }
                    else if (rotateType.equals("righttop")) {
                        if (!(ex.getX()==exo.getX())) {
                            ex.setPosition(ex.getX()-1, ex.getY()+1);
                        }
                        else {
                            System.out.println("reset");
                            rotateType="none";
                            game.p.currentRotate[0] = -1;
                            game.p.currentRotate[1] = -1;
                        }
                    }
                }

                sb.draw(ex, ex.getX(), ex.getY());
                //Offset is used to give smooth falling animation. Only want it to apply to current falling piece
                //If block has reached the bottom don't apply offset
                if (row == 13) {
                    sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)+rotateY);
                }
                //If the current location is part of the current falling piece...
                else if ((row == fallingY1 && cols == fallingX1) || (row == fallingY2 && cols == fallingX2)) {
                    //Apply offset if there's empty space below it
                    if (board.board[row+1][cols] == null) {
                        sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)-board.offset+rotateY);
                    }
                    //Add exception to above if the block below it is part of the current falling piece
                    else if (row+1 == fallingY1 || row+1 == fallingY2) {
                        //Exception for if moving piece below has met an immovable block
                        if (row >= 12) { //immovable against bottom
                            sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)+rotateY);
                        }
                        else if ((board.board[row+2][cols] != null) && (row+2 != fallingY1 || row+2 != fallingY2)) {
                            sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)+rotateY);
                        }
                        else sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)-board.offset+rotateY);
                    }
                    else {
                        sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)+rotateY);
                    }
                }
                else {
                    sb.draw(spr, initx+(cols*42)+rotateX, inity-(row*42)+rotateY);
                }
            }
        }
        for(int cols = 5; cols >= 0; cols--) {
            for (int row = 13; row >= 2; row--) {
                if (board2.board[row][cols] == null) continue;
                switch (board2.board[row][cols].getColor()) {
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
        bgsong.dispose();
    }
    public int[] rotate(int x, int y, int cx, int cy, double angle) {
        angle = (angle * (Math.PI/180));
        int rotX = (int) (Math.cos(angle) * (x - cx) - Math.sin(angle) * (y-cy) +cx);
        int rotY = (int) (Math.sin(angle) * (x - cx) + Math.cos(angle) * (y - cy) + cy);
        int[] r = new int[2];
        r[0] = rotX;
        r[1] = rotY;
        return r;
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
