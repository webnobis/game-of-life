package com.webnobis.game.of.life.view;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import com.webnobis.game.of.life.model.Config;
import com.webnobis.game.of.life.model.GameInfo;

import javafx.application.Platform;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

public class MenuBuilder {

	private final Window parent;

	private final GridPane gamePane;

	private final String author;

	private final AtomicReference<Config> configRef;

	private final AtomicReference<GameInfo> gameInfoRef;

	public MenuBuilder(Window parent, GridPane gamePane, String author) {
		this.parent = Objects.requireNonNull(parent);
		this.gamePane = Objects.requireNonNull(gamePane);
		this.author = Objects.requireNonNull(author);
		configRef = new AtomicReference<>(Config.getDefault());
		gameInfoRef = new AtomicReference<>();
	}

	public Config getConfig() {
		return configRef.get();
	}

	public Menu createConfigMenu() {
		MenuItem newItem = new MenuItem("New");
		newItem.setOnAction(event -> {
			Dialog<Config> configDialog = new ConfigDialog(parent, configRef.get(), false);
			configDialog.showAndWait().ifPresent(config -> Platform.runLater(() -> {
				double radius = Math.min(parent.getWidth() / config.cols(), parent.getHeight() / config.rows()) / 2;
				new GamePaneUpdateTimer(gamePane, radius, config, gameInfoRef::set);
				parent.sizeToScene();
				configRef.set(config);
			}));
			event.consume();
		});
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.setOnAction(event -> Platform.exit());
		return new Menu("Config", null, newItem, exitItem);
	}

	public Menu createInfoMenu() {
		MenuItem sourceMenu = new MenuItem("Source (Wikipedia)");
		sourceMenu.setOnAction(event -> {
			new SourceDialog(parent).showAndWait();
			event.consume();
		});
		Menu ideaMenu = new Menu("Game idea", null, new MenuItem("Implemented by ".concat(author)), sourceMenu);
		return new Menu("Info", null, ideaMenu);
	}

	public ContextMenu createPopupMenu() {
		MenuItem gameInfoItem = new MenuItem("Current game info");
		gameInfoItem.setOnAction(event -> {
			new GameInfoDialog(parent, gameInfoRef.get()).showAndWait();
			event.consume();
		});
		MenuItem configItem = new MenuItem("Current config");
		configItem.setOnAction(event -> {
			new ConfigDialog(parent, configRef.get(), true).showAndWait();
			event.consume();
		});
		return new ContextMenu(gameInfoItem, configItem);
	}

}
