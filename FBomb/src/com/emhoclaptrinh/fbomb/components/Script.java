package com.emhoclaptrinh.fbomb.components;

import javax.script.ScriptContext;
import javax.script.SimpleScriptContext;

import com.artemis.Component;

public class Script extends Component {

	
	public Script(String content) {
		this.content = content;
		context = new SimpleScriptContext();
	}

	public String content;
	public ScriptContext context;
}
