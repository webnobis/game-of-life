package com.webnobis.game.of.life;

import java.util.ResourceBundle;

import com.webnobis.game.of.life.view.MenuBuilder;
import com.webnobis.game.of.life.view.ResizeListener;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Game of life
 * 
 * @author steffen
 *
 */
public class GameOfLife extends Application {

	private static final ResourceBundle releaseProperties = ResourceBundle.getBundle("release");

	// maybe changed by tests
	static Class<? extends Application> startClass = GameOfLife.class;

	/**
	 * Creates the base dialog of the game with menu and game pane
	 */
	@Override
	public void start(Stage stage) {
		GridPane gamePane = new GridPane();
		MenuBuilder menuBuilder = new MenuBuilder(stage, gamePane, releaseProperties.getString("author"));
		gamePane.setOnContextMenuRequested(event -> {
			menuBuilder.createPopupMenu().show(gamePane, event.getScreenX(), event.getScreenY());
			event.consume();
		});
		MenuBar menuBar = new MenuBar(menuBuilder.createConfigMenu(), menuBuilder.createInfoMenu());
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(menuBar);
		mainPane.setCenter(gamePane);

		stage.setTitle(releaseProperties.getString("title"));
		stage.setScene(new Scene(mainPane));
		stage.sizeToScene();
		stage.centerOnScreen();
		ResizeListener resizeListener = new ResizeListener(stage, gamePane, menuBuilder::getConfig);
		stage.widthProperty()
				.addListener((observable, oldValue, newValue) -> resizeListener.widthChanged(newValue.doubleValue()));
		stage.heightProperty()
				.addListener((observable, oldValue, newValue) -> resizeListener.heightChanged(newValue.doubleValue()));
		stage.setOnCloseRequest(event -> Platform.exit());
		stage.show();
	}

	/**
	 * Starts the application
	 * 
	 * @param args unused
	 * @see Application#launch(Class, String...)
	 */
	public static void main(String[] args) {
		Application.launch(startClass, args);
	}

}
