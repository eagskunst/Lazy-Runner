package com.lazyrunner;

import com.badlogic.gdx.Screen;

/**
 * Español:
 * Una clase abstracta que implementa screen y como su nombre indica, sirve de base para las pantallas
 * del juego.
 * English:
 * An abstract class that implements from Screen and serves for 'BaseScreen' for all the other screens
 * that we use in the game.
 */

public abstract class PantallaBase implements Screen{

    LazyRunner game;

    public PantallaBase(LazyRunner game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
