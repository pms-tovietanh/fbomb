package com.emhoclaptrinh.fbomb.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.emhoclaptrinh.fbomb.components.LastPosition;
import com.emhoclaptrinh.fbomb.components.Position;
import com.emhoclaptrinh.fbomb.components.Velocity;

public class MovementSystem extends EntityProcessingSystem{

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<LastPosition> lpm;
	
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Aspect.getAspectForAll(Position.class, Velocity.class));
	}

	@Override
	protected void process(Entity e) {
		Position position = pm.get(e);
		Velocity velocity = vm.get(e);
		LastPosition lp = lpm.get(e);
		
		if(lp!=null){
			lp.x = position.x;
			lp.y = position.y;
		}
		
		float d = Gdx.graphics.getDeltaTime();
		position.y+=velocity.vectorY*d;
		position.x+=velocity.vectorX*d;
	}

}
