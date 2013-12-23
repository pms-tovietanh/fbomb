package com.emhoclaptrinh.fbomb.systems.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class MyMapRenderer extends OrthogonalTiledMapRenderer {

	public MyMapRenderer(TiledMap map, float unitScale, SpriteBatch spriteBatch) {
		super(map, unitScale, spriteBatch);
	}
	
	public void setViewBound(float x, float y, float width, float height){
		
	}

}
