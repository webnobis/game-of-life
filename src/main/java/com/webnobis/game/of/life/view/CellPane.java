package com.webnobis.game.of.life.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;

/**
 * Cell corresponding pane of matrix
 * 
 * @author steffen
 *
 */
public class CellPane extends Sphere {

	private final PhongMaterial material;

	/**
	 * Colored and sized sphere depending on initial radius and living flag
	 * 
	 * @param radius radius
	 * @param living living
	 */
	public CellPane(double radius, boolean living) {
		super();
		material = new PhongMaterial(Color.LIGHTGRAY);
		update(radius);
		update(living);
		super.setCullFace(CullFace.BACK);
		super.setDrawMode(DrawMode.FILL);
		super.setMaterial(material);
	}

	/**
	 * Updates sphere color depending on living flag
	 * 
	 * @param living
	 */
	public void update(boolean living) {
		material.setDiffuseColor(living ? Color.WHITE : Color.DARKBLUE);
	}

	/**
	 * Updates sphere size depending on radius
	 * 
	 * @param radius
	 */
	public void update(double radius) {
		super.setRadius(radius);
	}

}
