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
package org.malai.action.library;

import org.malai.action.ActionImpl;

/**
 * Defines an abstract action that move an object to the given position.
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public abstract class PositionAction extends ActionImpl {
	/** The X-coordinate of the location to zoom. */
	protected double px;

	/** The Y-coordinate of the location to zoom. */
	protected double py;

	/**
	 * Creates the action.
	 */
	public PositionAction() {
		super();

		px = Double.NaN;
		py = Double.NaN;
	}


	@Override
	public boolean canDo() {
		return !Double.isNaN(px) && !Double.isNaN(py);
	}


	/**
	 * @param px The x-coordinate to set.
	 * @since 0.2
	 */
	public void setPx(final double px) {
		this.px = px;
	}


	/**
	 * @param py The y-coordinate to set.
	 * @since 0.2
	 */
	public void setPy(final double py) {
		this.py = py;
	}
}
