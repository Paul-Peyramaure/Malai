/*
 * This file is part of Malai.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package org.malai.javafx.interaction;

import javafx.scene.input.ScrollEvent;
import org.malai.stateMachine.SourceableState;
import org.malai.stateMachine.TargetableState;

/**
 * A scroll event transition.
 * @author Arnaud Blouin
 */
public class ScrollTransition extends InputEventTransition<ScrollEvent> {
	/**
	 * Creates the transition.
	 * @param inputState The source state of the transition.
	 * @param outputState The srcObject state of the transition.
	 * @throws IllegalArgumentException If one of the given parameters is null or not valid.
	 */
	public ScrollTransition(final SourceableState inputState, final TargetableState outputState) {
		super(inputState, outputState);
	}
}
