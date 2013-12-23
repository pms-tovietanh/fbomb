package com.emhoclaptrinh.fbomb;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FBomb";
		cfg.useGL20 = false;
		cfg.width = 400;
		cfg.height = 240;
		
		new LwjglApplication(new BomberMan(), cfg);
	}
}
