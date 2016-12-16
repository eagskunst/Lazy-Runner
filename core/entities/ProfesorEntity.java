package com.lazyrunner.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.lazyrunner.Constantes.PIXELS_IN_METER;

/**
 * Español:
 * El Profesor. Estático y es un rectángulo, hacerlo como respectivamente se ve me estaba
 * costando mucho y decidí no complicarme.
 * English:
 * Profesor Enity. Static. It's a rectangle because while I was making it respective fixture
 * I had a lot of trouble, so I decided to don't make more trouble to it.
 */

public class ProfesorEntity extends Actor {

    private World mundo;
    private Body profBody;
    private Texture profTexture;
    private Fixture profFixture;

    /*
    En el constructor le pasamos el mundo que utilizará PantallaJuego, la textura
    y la posición en el eje X y en el eje Y.
     */
    public ProfesorEntity(World mundo, Texture profTexture,float x, float y){
        this.mundo = mundo;
        this.profTexture = profTexture;

        BodyDef def = new BodyDef();//Definmos la posicion
        def.position.set(x,y);
        profBody = mundo.createBody(def);//Creamos el cuerpo
        PolygonShape shape = new PolygonShape();
        /*Creamos un rectangulo, el primer valor corresponde a la mitad del largo y el segundo
        a la mitad del ancho
        */
        shape.setAsBox(0.4f,0.75f);
        profFixture = profBody.createFixture(shape,3);
        profFixture.setUserData("profesor");//Le asignamos un nombre a nuestra fixture.
        shape.dispose();

        setPosition((x - 0.5f)  * PIXELS_IN_METER,y  * PIXELS_IN_METER);//Asignamos posicion

        setSize(PIXELS_IN_METER,PIXELS_IN_METER);//Asignamos tamaño
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(profTexture,getX(),getY(),getWidth(),getHeight());
    }

    public void detach(){
        profBody.destroyFixture(profFixture);
        mundo.destroyBody(profBody);
    }

}
