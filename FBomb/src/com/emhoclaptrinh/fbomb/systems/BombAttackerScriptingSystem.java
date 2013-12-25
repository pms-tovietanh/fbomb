package com.emhoclaptrinh.fbomb.systems;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.emhoclaptrinh.fbomb.components.BombAttacker;
import com.emhoclaptrinh.fbomb.components.Script;
import com.emhoclaptrinh.fbomb.components.Velocity;
import com.emhoclaptrinh.fbomb.utils.Constants;

public class BombAttackerScriptingSystem extends EntityProcessingSystem {

	@Mapper ComponentMapper<BombAttacker> bm;
	@Mapper ComponentMapper<Script> sm;
	@Mapper ComponentMapper<Velocity> vm;
	
	private ScriptEngine engine;
	
	@SuppressWarnings("unchecked")
	public BombAttackerScriptingSystem(ScriptEngine engine) {
		super(Aspect.getAspectForAll(Script.class, BombAttacker.class,Velocity.class));
		this.engine = engine;
	}

	@Override
	protected void process(Entity e) {
		BombAttacker ba = bm.get(e);
		Script s = sm.get(e);
		Velocity velocity = vm.get(e);
		if(ba.isStopped()){
			try {
				String result = engine.eval(s.content,s.context).toString();
				if(result == null)return;
				String[] actions = result.split(",");
				if(actions.length==2){
					if(actions[0].equals(Constants.ScriptingActions.Left)){
						velocity.vectorX = -30;
					}else if(actions[0].equals(Constants.ScriptingActions.Right)){
						velocity.vectorX = 30;
					}else if(actions[0].equals(Constants.ScriptingActions.Up)){
						velocity.vectorY = 30;
					}else if(actions[0].equals(Constants.ScriptingActions.Down)){
						velocity.vectorY = -30;
					}
					if(actions[1].equals(Constants.ScriptingActions.Bomb)){
						
					}
				}
			} catch (ScriptException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
