package com.team8.game.States;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;
/**
 * Created by ongun on 2/23/16.
 */
public class GameStateManager {
    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
    MyGameCallback myGameCallback;
    public void setMyGameCallback(MyGameCallback callback) {
        myGameCallback = callback;
    }

    public interface MyGameCallback {
        void onStartActivityLeaderboard();

    }
}
