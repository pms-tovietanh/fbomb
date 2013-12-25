package com.emhoclaptrinh.fbomb.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.GroupManager;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.emhoclaptrinh.fbomb.components.BombAttacker;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.components.Velocity;
import com.emhoclaptrinh.fbomb.utils.Constants;
import com.emhoclaptrinh.fbomb.utils.EntityFactory;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<BombAttacker> bm;
	
	private boolean left,right,up,down,setBomb;
	private boolean p2left,p2right,p2up,p2down,p2setBomb;
	
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
		if(keycode==Input.Keys.A){
			left = true;
		}
		if(keycode==Input.Keys.D){
			right = true;
		}
		if(keycode==Input.Keys.W){
			up = true;
		}
		if(keycode==Input.Keys.S){
			down = true;
		}
		if(keycode==Input.Keys.SPACE){
			setBomb = true;
		}
		if(keycode==Input.Keys.LEFT){
			p2left = true;
		}
		if(keycode==Input.Keys.RIGHT){
			p2right = true;
		}
		if(keycode==Input.Keys.UP){
			p2up = true;
		}
		if(keycode==Input.Keys.DOWN){
			p2down = true;
		}
		if(keycode==Input.Keys.INSERT){
			p2setBomb = true;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode==Input.Keys.A){
			left = false;
		}
		if(keycode==Input.Keys.D){
			right = false;
		}
		if(keycode==Input.Keys.W){
			up = false;
		}
		if(keycode==Input.Keys.S){
			down = false;
		}
		if(keycode==Input.Keys.SPACE){
			setBomb = false;
		}
		if(keycode==Input.Keys.LEFT){
			p2left = false;
		}
		if(keycode==Input.Keys.RIGHT){
			p2right = false;
		}
		if(keycode==Input.Keys.UP){
			p2up = false;
		}
		if(keycode==Input.Keys.DOWN){
			p2down = false;
		}
		if(keycode==Input.Keys.INSERT){
			p2setBomb = false;
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
		//TODO
		System.out.println(screenX+" - "+screenY +" - " + pointer+" - "+button);
		return true;
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
		if(world.getManager(GroupManager.class).getGroups(e).contains(Constants.EntityGroups.Player1)){
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
			if(setBomb&&ba.remainedBombs>0){
				//get position
				Position position = pm.get(e);
				int x = (int)Math.floor(position.x/16);
				int y = (int)Math.floor(position.y/16);
				EntityFactory.activeBomb(world, x, y, ba);
			}
		}
		if(world.getManager(GroupManager.class).getGroups(e).contains(Constants.EntityGroups.Player2)){
			if(ba.isStopped()){
				if(p2left){
					velocity.vectorX = -30;
				}
				if(p2right){
					velocity.vectorX = 30;
				}
				if(p2up){
					velocity.vectorY = 30;
				}
				if(p2down){
					velocity.vectorY = -30;
				}
			}
			if(p2setBomb&&ba.remainedBombs>0){
				//get position
				Position position = pm.get(e);
				int x = (int)Math.floor(position.x/16);
				int y = (int)Math.floor(position.y/16);
				EntityFactory.activeBomb(world, x, y, ba);
			}
		}
		
		
	}

}
