package com.webnobis.game.of.life.view;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class SourceDialog extends Dialog<Void> {

	static final String SOURCE_IMAGE = ClassLoader.getSystemResource("conwaysgameoflifewikipedia.png").toString();

	/**
	 * Source
	 */
	public static final String SOURCE = "https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life";

	public SourceDialog(Window owner) {
		super();
		super.setTitle(URLDecoder.decode(SOURCE, StandardCharsets.UTF_8));
		Rectangle2D size = Screen.getPrimary().getBounds();
		ImageView imgView = new ImageView(SOURCE_IMAGE);
		DialogPane dialogPane = super.getDialogPane();
		int width = Math.min((int) (imgView.getImage().getWidth() * 1.05), (int) size.getWidth());
		int height = Math.min((int) imgView.getImage().getHeight(), (int) (size.getHeight() * 0.8));
		dialogPane.setPrefSize(width, height);
		dialogPane.setContent(new ScrollPane(imgView));
		dialogPane.getButtonTypes().add(ButtonType.CLOSE);
		super.initOwner(owner);
		super.initModality(Modality.APPLICATION_MODAL);
		super.initStyle(StageStyle.DECORATED);
	}

}
