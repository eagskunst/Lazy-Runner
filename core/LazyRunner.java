package com.lazyrunner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/*
Español:
La clase principal, en la que cargamos nuestras texturas y sonidos y creamos nuestras pantallas
con la finalidad de poder cambiarlas si es el caso.
English:
Main class, in which we load our textures and sound. We also create our screen because we need to change
 between them if the case.
 */

/*Español:
Sobre licencias
Este videojuego esta bajo la licencia Reconocimiento-NoComercial-CompartirIgual 4.0 Internacional.
Tienes derecho a:
Copiar y redistribuir el material en cualquier formato.
Hacer un trabajo nuevo basado en este.
PERO bajo los siguientes términos:
No puedes comercializar con el.
Debes darme crédito si lo usas e indicar si hicistes cambios.
Debes usar la misma licencia
Copia de la licencia:
https://creativecommons.org/licenses/by-nc-sa/4.0/

English:
About the license:
You are free to:

Share — copy and redistribute the material in any medium or format
Adapt — remix, transform, and build upon the material
Under the following terms:

Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
NonCommercial — You may not use the material for commercial purposes.
ShareAlike — If you remix, transform, or build upon the material, you must distribute your contributions under the same license as the original.
More info:
https://creativecommons.org/licenses/by-nc-sa/4.0/
*/

public class LazyRunner extends Game {

	private AssetManager manager;

	protected PantallaJuego pantallaJuego;
	protected GameOverScreen gameOver;
	protected CreditosScreen creditos;
	protected MenuPantalla menu;
	protected PantallaCarga pantallaCarga;

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("Chad.png",Texture.class);
		manager.load("Profesor1.png",Texture.class);
		manager.load("floor.png",Texture.class);
		manager.load("overfloor.png",Texture.class);
		manager.load("fondo.png",Texture.class);
		manager.load("game over.png",Texture.class);
		manager.load("logo.png",Texture.class);
		manager.load("salto.wav",Sound.class);
		manager.load("die.ogg",Sound.class);
		manager.load("fondosong.ogg",Music.class);

		pantallaCarga = new PantallaCarga(this);
		setScreen(pantallaCarga);
	}

	public void despuesDeCargar(){
		menu = new MenuPantalla(this);
		creditos = new CreditosScreen(this);
		pantallaJuego = new PantallaJuego(this);
		gameOver = new GameOverScreen(this);
		setScreen(menu);
	}

	public AssetManager getManager() {
		return manager;
	}
}
