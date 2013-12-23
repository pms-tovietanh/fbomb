package com.emhoclaptrinh.fbomb.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.emhoclaptrinh.fbomb.components.BombAttacker;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.components.Velocity;
import com.emhoclaptrinh.fbomb.utils.EntityFactory;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<BombAttacker> bm;
	
	private boolean left,right,up,down,setBomb;
	
	@SuppressWarnings("unchecked")
	public PlayerInputSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class,BombAttacker.class));
	}

	@Override
	protected void initialize() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Input.Keys.LEFT){
			left = true;
		}
		if(keycode==Input.Keys.RIGHT){
			right = true;
		}
		if(keycode==Input.Keys.UP){
			up = true;
		}
		if(keycode==Input.Keys.DOWN){
			down = true;
		}
		if(keycode==Input.Keys.SPACE){
			setBomb = true;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Input.Keys.LEFT){
			left = false;
		}
		if(keycode==Input.Keys.RIGHT){
			right = false;
		}
		if(keycode==Input.Keys.UP){
			up = false;
		}
		if(keycode==Input.Keys.DOWN){
			down = false;
		}
		if(keycode==Input.Keys.SPACE){
			setBomb = false;
		}
		
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void process(Entity e) {
		Velocity velocity = vm.get(e);
		BombAttacker ba = bm.get(e);
		if(ba.isStopped()){
			if(left){
				velocity.vectorX = -30;
			}
			if(right){
				velocity.vectorX = 30;
			}
			if(up){
				velocity.vectorY = 30;
			}
			if(down){
				velocity.vectorY = -30;
			}
		}
		if(setBomb){
			//get position
			Position position = pm.get(e);
			int x = (int)Math.floor(position.x/16);
			int y = (int)Math.floor(position.y/16);
			EntityFactory.activeBomb(world, x, y);
		}
		
	}

}
