package com.lazyrunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lazyrunner.entities.ChadEntity;
import com.lazyrunner.entities.ProfesorEntity;
import com.lazyrunner.entities.SueloEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.lazyrunner.Constantes.PIXELS_IN_METER;

/**
 * Español:
 * Esta clase representa la pantalla de juego (como su nombre lo indica), en el cual creamos nuestros actores, sonidos
 * métodos de colisión e imagenes. Además de eso, generamos movimiento a nuestro jugador y pedimos a la cámara que lo siga.
 * English:
 * This class represents the Game Screen. In this class, we create our actors,sounds, collision methods and our textures. Also, we
 * generate the movement of our player and make the camera follow him.
 */
public class PantallaJuego extends PantallaBase {

    //Creamos el stage de Scene2D, el mundo de Box2d, la camara que se implementará en el render y nuestros actores.
    private Stage stage;
    private World mundo;
    private ChadEntity player;
    private Fondo background;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;
    private List<ProfesorEntity> profesorList = new ArrayList<ProfesorEntity>();
    private List<SueloEntity> sueloList = new ArrayList<SueloEntity>();
    private Texture profTexture = game.getManager().get("Profesor1.png"),fondoTexture = game.getManager().get("fondo.png");

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    private float posicionX,posicionY = 1; //Las posiciones de el/los primer/os profesores.
    private Sound salto,muerte;
    private Music fondoMusic;
    private Vector3 position; //Posicion inicial de la camara

    public PantallaJuego(final LazyRunner game) {


        super(game);
        //Asignamos variables. Stage recibe el tamaño de nuestra pantalla y World como actuarán las físicas
        fondoMusic = game.getManager().get("fondosong.ogg");
        salto = game.getManager().get("salto.wav");
        muerte = game.getManager().get("die.ogg");
        stage = new Stage(new FitViewport(640,360));
        mundo = new World(new Vector2(0,-10),true);
        position = new Vector3(stage.getCamera().position);
        score = 0;

        /*Un ContactListener que nos permitira determinar colisiones
        entre dos fixture pasandole un Contact y el nombre de la c/u
        de las fixtures*/
        mundo.setContactListener(new ContactListenerJuego());
    }

    @Override
    public void show() {

        /*Añadimos actores en nuestro mundo en el orden que queramos que se vean.
        Los parametros que se pasan están explicados dentro de cada clase
         */

        Texture chadTexture = game.getManager().get("Chad.png");
        Texture sueloTextura = game.getManager().get("floor.png");
        Texture overfloorTextura = game.getManager().get("overfloor.png");
        background = new Fondo(fondoTexture,stage);
        player = new ChadEntity(mundo,chadTexture,new Vector2(1.5f,1.5f));
        stage.addActor(background);
        posicionX = 8;
        GenerarProfesores();

        sueloList.add(new SueloEntity(mundo,sueloTextura,overfloorTextura,0,3000,1));

        for (SueloEntity suelo : sueloList){
            stage.addActor(suelo);
        }

        stage.addActor(player);

        stage.getCamera().position.set(position);
        stage.getCamera().update();

        //Que comience la música.
        fondoMusic.setVolume(0.75f);
        fondoMusic.play();
    }

    //El loop o bucle del juego.
    @Override
    public void render(float delta) {
        //Al comenzar el render, siempre se llama a estos métodos para evitar problemas de color.
        float inpl = 0.5f;
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        mundo.step(delta,6,2);

        stage.getCamera().position.x = player.getX() + 210; //Solución al bug que, despues de un tiempo, el personaje desaparece de la pantalla.

        if(player.getX()>100 && player.isVivo()){
            float velocidad = (Constantes.VELOCIDAD_JUGADOR * delta * PIXELS_IN_METER) - 0.03f;//Si la distancia recorrida por el jugador es > a 100, que se mueva la camara con el.
            stage.getCamera().translate(velocidad, 0, 0);
            score++;
            GenerarProfesores(); //Se generan profesores mientras este vivo.
            if(!fondoMusic.isPlaying()){
                fondoMusic.setVolume(0.75f);
                fondoMusic.play();
            }
        }
        if(Gdx.input.justTouched()){
            salto.play(0.5f);//En caso de saltar, que se reproduzca su respectivo sonido
        }
        /*
        En caso de salto continuo y no este saltando,
        que se reproduzca su respectivo sonido
         */
        if(Gdx.input.isTouched() && !player.isSaltando() && player.isVivo()){
            salto.play(0.5f);
        }


        stage.draw();
    }
    //Cuando cambie de pantalla, se elimna todo para no sobrecargar la tarjeta gráfica y de sonido
    @Override
    public void hide() {
        fondoMusic.stop();

        player.detach();
        player.remove();

        for (SueloEntity suelo : sueloList){
            suelo.detach();
            suelo.remove();
        }

        for (ProfesorEntity profesor : profesorList){
            profesor.detach();
            profesor.remove();
        }

        profesorList.clear();
        sueloList.clear();
    }

    //Eliminado tambien el World y el Stage que no se van a usar (¿Por ahora?)
    @Override
    public void dispose() {
        mundo.dispose();
        stage.dispose();
    }

    /*Método para generar profesores "infinitamente"
    Hay 4 opciones a la hora de generar profesores, por lo que utilizo un número aleatorio para que
    no sea tan repetitivo. Al final se agregan.
     */
    private void GenerarProfesores(){
        Random rand = new Random(System.nanoTime());
        int orden = rand.nextInt(4);

        if(orden == 0){
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 15;
        }

        else if(orden == 1){
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 6;
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 6;
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 15;
        }

        else if(orden == 2){
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 6;
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 15;
        }

        else if(orden == 3){
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 1;
            profesorList.add(new ProfesorEntity(mundo,profTexture,posicionX,posicionY));
            posicionX += 15;
        }


        for (ProfesorEntity profesor : profesorList){
            stage.addActor(profesor);
        }

    }

    /**
     * Español:
     * Un ContactListener que nos permitira determinar colisiones
     * entre dos fixture pasandole un Contact y el nombre de la c/u
     * de las fixtures.
     * English:
     * A ContactListener that let us determine collisions between
     * two fixture, we pass it a contact and the name of the fixture.
     */

    public class ContactListenerJuego implements ContactListener {
        private boolean hanColisionado(Contact contact, String userA, String userB){
            return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                    (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {
            if (hanColisionado(contact,"chad","profesor")){
                if(player.isVivo()){//Solo queremos morir una vez
                    player.setVivo(false);
                    muerte.play();
                    fondoMusic.stop();

                    /*
                    Añadimos una accion, la cual nos permite añadir animación a nuestro juego.
                    En este caso son secuenciales. La primera espera un segundo y la segunda
                    cambia a la pantalla de GameOver.
                     */
                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(1f),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            game.setScreen(game.gameOver); //Cambio de pantalla
                                        }
                                    })
                            )
                    );
                }

            }

            if (hanColisionado(contact,"chad","suelo")){
                player.setSaltando(false);
                if(Gdx.input.isTouched()){ //Si la pantalla sigue tocada, que el jugador salte otra vez cuando toque el suelo
                    player.setDebeSaltar(true);
                }
            }

        }

        //Métodos que no utilizamos pero que igual tenemos que sobreescribir.
        @Override
        public void endContact(Contact contact) {}

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {}

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {}

    }
}


