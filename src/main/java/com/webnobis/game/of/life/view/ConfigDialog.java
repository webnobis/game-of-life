package com.webnobis.game.of.life.view;

import java.util.Objects;

import com.webnobis.game.of.life.model.Config;
import com.webnobis.game.of.life.model.ConfigRange;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ConfigDialog extends Dialog<Config> {

	private final SliderPane<Integer> rows;

	private final SliderPane<Integer> cols;

	private final SliderPane<Long> nextLivingPeriodMilliseconds;

	private final SliderPane<Integer> initLivingPercent;

	private final CheckBox randomInitLiving;

	private final CheckBox neighborCellBorderless;

	private final CheckBox sameRowsAndCols;

	private final ToggleGroup gameRuleGroup;

	public ConfigDialog(Window owner, Config lastConfig, boolean readonly) {
		super();
		Objects.requireNonNull(lastConfig);
		cols = new SliderPane<>("Columns:", ConfigRange.getRowsOrColsRange(), lastConfig.cols());
		rows = new SliderPane<>("Rows:", ConfigRange.getRowsOrColsRange(), lastConfig.rows());
		rows.getSlider().valueProperty().addListener((observable, oldValue, newValue) -> setColsWithRows());
		nextLivingPeriodMilliseconds = new SliderPane<>("Next living period milliseconds:",
				ConfigRange.getNextLivingPeriodMilliseconds(), lastConfig.nextLivingPeriodMilliseconds(), "ms");
		initLivingPercent = new SliderPane<>("Initial percent of living cells:", ConfigRange.getInitLivingPercent(),
				lastConfig.initLivingPercent(), "%");
		GridPane sliderPane = new GridPane();
		sliderPane.addColumn(0, rows, cols, nextLivingPeriodMilliseconds, initLivingPercent);
		randomInitLiving = new CheckBox("Random initial living");
		randomInitLiving.selectedProperty()
				.addListener((observable, oldValue, newValue) -> initLivingPercent.setDisable(newValue));
		randomInitLiving.setSelected(lastConfig.randomInitLiving());
		neighborCellBorderless = new CheckBox("Identify neighbor cells borderless");
		neighborCellBorderless.setSelected(lastConfig.neighborCellBorderless());
		sameRowsAndCols = new CheckBox("Same rows and columns");
		sameRowsAndCols.selectedProperty().addListener((observable, oldValue, newValue) -> {
			cols.getSlider().setDisable(newValue);
			setColsWithRows();
		});
		sameRowsAndCols.setSelected(lastConfig.sameRowsAndCols());
		gameRuleGroup = new ToggleGroup();
		gameRuleGroup.getToggles().addAll(new RadioButton("2G3 rule, the original"), new RadioButton("4G3 rule"),
				new RadioButton("26G3 rule"), new RadioButton("G1357 rule"));
		gameRuleGroup.selectToggle(gameRuleGroup.getToggles().get(lastConfig.gameRuleIndex()));
		GridPane gameRulePane = new GridPane();
		gameRulePane.setHgap(5);
		gameRulePane.setVgap(5);
		gameRulePane.addColumn(0, gameRuleGroup.getToggles().toArray(new Node[0]));
		gameRulePane.addColumn(1, randomInitLiving, neighborCellBorderless, sameRowsAndCols);
		super.setResultConverter(this::createConfigConverter);
		super.setTitle("Living-Configuration");
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(sliderPane);
		mainPane.setBottom(gameRulePane);
		DialogPane dialogPane = super.getDialogPane();
		dialogPane.setContent(mainPane);
		if (readonly) {
			setReadonly(mainPane);
			dialogPane.getButtonTypes().add(ButtonType.CLOSE);
		} else {
			dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			dialogPane.lookupButton(ButtonType.OK);
		}
		super.initOwner(owner);
		super.initModality(Modality.APPLICATION_MODAL);
		super.initStyle(StageStyle.DECORATED);
	}

	private void setReadonly(Pane pane) {
		pane.getChildren().forEach(node -> {
			Class<?> clazz = node.getClass();
			if (Pane.class.isAssignableFrom(clazz)) {
				setReadonly((Pane) node);
			} else if (Control.class.isAssignableFrom(clazz)) {
				((Control) node).setDisable(true);
			}
		});
	}

	private void setColsWithRows() {
		if (cols.getSlider().isDisable()) {
			cols.getSlider().setValue(rows.getSlider().getValue());
		}
	}

	private Config createConfigConverter(ButtonType buttonType) {
		if (ButtonType.OK.equals(buttonType)) {
			return new Config(rows.getValue(), cols.getValue(), nextLivingPeriodMilliseconds.getValue(),
					initLivingPercent.getValue(), randomInitLiving.isSelected(), neighborCellBorderless.isSelected(),
					sameRowsAndCols.isSelected(),
					gameRuleGroup.getToggles().indexOf(gameRuleGroup.getSelectedToggle()));
		} else {
			return null;
		}
	}

}
