package com.emhoclaptrinh.fbomb.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.emhoclaptrinh.fbomb.components.Fire;
import com.emhoclaptrinh.fbomb.components.Position;

public class FireCountDownSystem extends EntityProcessingSystem {
	
	@Mapper ComponentMapper<Fire> fm;
	@Mapper ComponentMapper<Position> pm;
	
	@SuppressWarnings("unchecked")
	public FireCountDownSystem() {
		super(Aspect.getAspectForAll(Fire.class));
	}

	@Override
	protected void process(Entity e) {
		Fire f = fm.get(e);
		Position position = pm.get(e);
		f.countDown-=world.delta;
		
		if(f.countDown<0){
			e.deleteFromWorld();
			System.out.println(position.x+" - "+position.y);
		}
	}

}
