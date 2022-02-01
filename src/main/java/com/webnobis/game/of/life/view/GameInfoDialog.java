package com.webnobis.game.of.life.view;

import java.util.Objects;
import java.util.stream.Stream;

import com.webnobis.game.of.life.model.GameInfo;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Game information dialog
 * 
 * @author steffen
 *
 */
public class GameInfoDialog extends Dialog<Void> {

	/**
	 * Game information dialog
	 * 
	 * @param owner    owner
	 * @param gameInfo game information
	 */
	public GameInfoDialog(Window owner, GameInfo gameInfo) {
		super();
		Objects.requireNonNull(gameInfo);
		GridPane mainPane = new GridPane();
		mainPane.addColumn(0, Stream
				.of("Rows:", "Columns:", "Cells:", "Living cells:", "Living percent: ", "Generation:", "Game rule:")
				.map(Label::new).toArray(i -> new Node[i]));
		mainPane.addColumn(1,
				Stream.of(gameInfo.rows(), gameInfo.cols(), gameInfo.cells(), gameInfo.livingCells(),
						gameInfo.livingCellsPercent(), gameInfo.generation(), gameInfo.gameRuleDescription())
						.map(Object::toString).map(Label::new).toArray(i -> new Node[i]));
		DialogPane dialogPane = super.getDialogPane();
		dialogPane.setContent(mainPane);
		dialogPane.getButtonTypes().addAll(ButtonType.CLOSE);
		super.setTitle("Game info");
		super.initOwner(owner);
		super.initModality(Modality.APPLICATION_MODAL);
		super.initStyle(StageStyle.DECORATED);
	}

}
