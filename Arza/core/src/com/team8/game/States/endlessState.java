package com.team8.game.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.audio.Sound;



public class endlessState extends State implements GestureDetector.GestureListener {
    private Sound leftso;
    private Sound rightso;
    private Sound downso;
    private Sound scoresound;

    private int prevscore = 0;
    private int currscore = 0;

    Sprite bg;
    Sprite Lpillar;
    Sprite Rpillar;
    Sprite Ufrm;
    Sprite Dfrm;
    Sprite bs;
    Sprite frame_block;
    Sprite yellowblock;
    Sprite redblock;
    Sprite blueblock;
    Sprite purpleblock;
    Sprite greenblock;
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
    Texture bgtex = new Texture("background.png");
    GestureDetector gestureDetector;
    Game game = new Game();
    Board board = game.board;
    int rotatetimer = 0;



    protected endlessState(GameStateManager gsm) {
        super(gsm);

        scoresound = Gdx.audio.newSound(Gdx.files.internal("Cymatics Weird Snare 2.wav"));
        rightso = Gdx.audio.newSound(Gdx.files.internal("rightgo.mp3"));
        leftso = Gdx.audio.newSound(Gdx.files.internal("leftgo.mp3"));
        downso = Gdx.audio.newSound(Gdx.files.internal("downgo.mp3"));

        bg = new Sprite(bgtex);
        Lpillar = new Sprite(new Texture("frameV.png"));
        Rpillar = new Sprite(new Texture("frameV.png"));
        Ufrm = new Sprite(new Texture("frame_top.png"));
        Dfrm = new Sprite(new Texture("frame_bottom.png"));
        frame_block = new Sprite(new Texture("frame_block.png"));
        bs = new Sprite(new Texture("scorestuff.png"));
        yellowblock = new Sprite(new Texture("yellowblock.png"));
        redblock = new Sprite(new Texture("redblock.png"));
        blueblock = new Sprite(new Texture("blueblock.png"));
        purpleblock = new Sprite(new Texture("purpleblock.png"));
        greenblock = new Sprite(new Texture("greenblock.png"));
        block_garbage = new Sprite(new Texture("block_garbage.png"));
        guideline_blue = new Sprite(new Texture("guideline_blue.png"));
        guideline_red = new Sprite(new Texture("guideline_red.png"));
        guideline_yellow = new Sprite(new Texture("guideline_yellow.png"));
        guideline_green = new Sprite(new Texture("guideline_green.png"));
        guideline_purple = new Sprite(new Texture("guideline_purple.png"));
        ex = new Sprite(new Texture("blueblock.png"));
        exo = new Sprite(new Texture("redblock.png"));
        bfont = new BitmapFont();
        bfont2 = new BitmapFont();

        //cam.translate(-50, -50);
        cam.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        float scale = bgtex.getWidth() / Gdx.graphics.getWidth();
        bg.setSize(bg.getWidth() / scale, bg.getHeight() / scale);
      //  Lpillar.setScale(4*Lpillar.getScaleX(),4*Lpillar.getScaleY());
        Lpillar.setPosition(0, Ufrm.getHeight());
       // Rpillar.setScale(4 * Rpillar.getScaleX(), 4* Rpillar.getScaleY());
        //Rpillar.setPosition(Lpillar.getWidth() * 2 + Lpillar.getWidth() / 2 + 42 * 6, Ufrm.getHeight());
        Rpillar.setPosition(Lpillar.getWidth() + (42 * 6), Ufrm.getHeight());
        //Ufrm.setScale(4 * Ufrm.getScaleX(), 4 * Ufrm.getScaleY());
        //Ufrm.setPosition(Lpillar.getWidth(), Lpillar.getHeight() + Dfrm.getHeight());
        Ufrm.setPosition(0, Dfrm.getHeight() + (12 * 42));
        //Dfrm.setScale(4 * Dfrm.getScaleX(),4 * Dfrm.getScaleY());
        Dfrm.setPosition(0,0);
        bs.setPosition(0,Gdx.graphics.getHeight());


        exo.setPosition(300, Ufrm.getY() + Ufrm.getHeight() + 120);

        ex.setPosition(300, Ufrm.getY() + Ufrm.getHeight() + 120 + 42);
        gestureDetector = new GestureDetector(this);
        Gdx.input.setInputProcessor(gestureDetector);
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
            Rectangle Dbounds = new Rectangle(0,0,(Gdx.graphics.getWidth()/3),Gdx.graphics.getHeight()/3/5);
            Rectangle LBounds=new Rectangle(0,Gdx.graphics.getHeight()/3/5,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3- Gdx.graphics.getHeight()/3/3);
            Rectangle midbound = new Rectangle((Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3/5,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/3/5);
            Rectangle RBounds=new Rectangle(2*(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3/5,(Gdx.graphics.getWidth()/3)/3,Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/3/5);
            if(LBounds.contains(touchPos.x, touchPos.y )){
                System.out.println("left");
                //when left side of screen is touched
    //            game.p.moveLeft(board);

            }
            if(Dbounds.contains(touchPos.x, touchPos.y )){
                System.out.println("down");
                //when down is touched
 //               for (int gig = 0; gig < 10; gig++)
 //                   game.p.singleDrop(board);
            }
            if(midbound.contains(touchPos.x, touchPos.y )){
                System.out.println("middle");
                //when middle of screen is touched
//                game.p.rotateCounter(board);
            }
            if(RBounds.contains(touchPos.x, touchPos.y )){
                System.out.println("right");
                //when right is touched
  //              game.p.moveRight(board);

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
        sb.draw(bg, bg.getX(), bg.getY(),Gdx.graphics.getWidth()/2 , Gdx.graphics.getHeight()/2);
        sb.draw(Ufrm, Ufrm.getX(), Ufrm.getY());
        sb.draw(Dfrm, Dfrm.getX(), Dfrm.getY());
        sb.draw(Lpillar, Lpillar.getX(), Lpillar.getY());
        sb.draw(Rpillar,Rpillar.getX(),Rpillar.getY());
        sb.draw(bs, bs.getX(), bs.getY());
        sb.draw(frame_block, 0, Ufrm.getY() + Ufrm.getHeight() + 12);


        sb.draw(exo, 300, Ufrm.getY()+Ufrm.getHeight()+120);
        //sb.draw(ex, 300, Ufrm.getY()+Ufrm.getHeight()+120+42);

        renderBoard(sb);
        sb.end();
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

    public void renderBoard(SpriteBatch sb) {
        float initx = Lpillar.getWidth();
        float inity = Lpillar.getHeight()+(42*2);
        //currently shit looking, will make function to make sprite from color later to replace switch statements
        bfont.setColor(1.0f,1.0f,1.0f,1.0f);
        scoreString = "Score: " + board.score;
//        timerString = "Time: " + ((System.nanoTime()-startTime)/1000000000);
        currscore = board.score;
        if(currscore != prevscore){
            scoresound.play(1.0f);
        }
        board.elapsed = (System.nanoTime()-board.startTime)/1000000000;
        board.min = board.elapsed / 60;
        board.sec = board.elapsed % 60;
        timerString = "Time: " + board.min + " : " + board.sec;
        //bfont2.draw(sb, timerString, 150, Ufrm.getY()+Ufrm.getHeight() - 50);
        // bfont.draw(sb, scoreString, 0, Ufrm.getY()+Ufrm.getHeight() - 50);
        bfont2.draw(sb, timerString, 70, Ufrm.getY() + Ufrm.getHeight() + 150);
        bfont.draw(sb, scoreString, 0, Ufrm.getY()+Ufrm.getHeight() + 150);
        System.nanoTime();

        switch(game.nextp.getA().getColor()) {
            case 0:
                sb.draw(redblock, 6, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 1:
                sb.draw(blueblock, 6, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 2:
                sb.draw(yellowblock, 6, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 3:
                sb.draw(greenblock, 6, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            case 4:
                sb.draw(purpleblock, 6, Ufrm.getY()+Ufrm.getHeight()+8+12);
                break;
            default:
        }
        switch(game.nextp.getB().getColor()) {
            case 0:
                sb.draw(redblock,6 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 1:
                sb.draw(blueblock,6 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 2:
                sb.draw(yellowblock,6 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 3:
                sb.draw(greenblock,6 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
                break;
            case 4:
                sb.draw(purpleblock,6 ,Ufrm.getY()+Ufrm.getHeight() + 42+8+12);
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
                            sb.draw(guide_spr, initx+(cols*42), inity-(bottom*42));
                        }
                        else {
                            sb.draw(guide_spr, initx+(cols*42), inity-((bottom-1)*42));
                        }
                    }
                    else if (row == fallingY2) {
                        int bottom = board.getBottom(cols);
                        if (row >= fallingY1) {

                            sb.draw(guide_spr, initx+(cols*42), inity-(bottom*42));
                        }
                        else {
                            sb.draw(guide_spr, initx+(cols*42), inity-((bottom-1)*42));
                        }
                    }
                }

                if (game.p.currentRotate[0] == fallingX1 || game.p.currentRotate[0] == fallingX2) {
                    System.out.printf("rotate %d\n", rotatetimer);
                    //System.out.printf("origin=(%f, %f) outer=(%f, %f)\n", exo.getX(), exo.getX(), ex.getX(), ex.getY());
                    //ex.setPosition(ex.getX() - 10, ex.getY());

                    if (rotatetimer < 45) {
                        int[] pos = rotate((int)ex.getX(), (int)ex.getY(), (int)exo.getX(), (int)exo.getY(), 90);
                        ex.setPosition(pos[0], pos[1]);
                        rotatetimer++;
                    }
                    else {
                        game.p.currentRotate[0] = -1;
                        game.p.currentRotate[1] = -1;
                        rotatetimer=0;
                    }

                    //sb.draw(spr, initx+(cols*42)+pos[0], inity+(row*42)+pos[1]-board.offset);
                    //rotateX=pos[0];
                    //rotateY=pos[1];
                    //rotatetimer++;
                    //if (rotatetimer > 60) {

                    //    rotatetimer=0;
                    //}
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

        if (velocityX > 0 && velocityX > 3 * velocityY) {
            game.p.moveRight(board);
            rightso.play(1.0f);
        } else if (velocityX < 0 && velocityX * - 1 > 3 * velocityY){
            game.p.moveLeft(board);
            //when left side of screen is touched
            leftso.play(1.0f);
        } else if (velocityY > 3 * velocityX || velocityY > -3 * velocityX) {
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
