package com.webnobis.game.of.life.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;

public class CellPane extends Sphere {

	private final PhongMaterial material;

	public CellPane(double radius, boolean living) {
		super();
		material = new PhongMaterial(Color.LIGHTGRAY);
		update(radius);
		update(living);
		super.setCullFace(CullFace.BACK);
		super.setDrawMode(DrawMode.FILL);
		super.setMaterial(material);
	}

	public void update(boolean living) {
		material.setDiffuseColor(living ? Color.WHITE : Color.DARKBLUE);
	}

	public void update(double radius) {
		super.setRadius(radius);
	}

}
