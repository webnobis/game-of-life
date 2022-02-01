package com.webnobis.game.of.life.view;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

/**
 * Slider pane containing additional informations
 * 
 * @author steffen
 *
 * @param <T> typed value
 */
public class SliderPane<T> extends BorderPane {

	private static final int WIDTH = 200;

	private final List<T> values;

	private final String valueExt;

	private final Slider slider;

	/**
	 * Named slider pane of typed values
	 * 
	 * @param name      name
	 * @param values    values
	 * @param initValue initial value
	 */
	public SliderPane(String name, List<T> values, T initValue) {
		this(name, values, initValue, null);
	}

	/**
	 * Named slider pane of typed values with extension
	 * 
	 * @param name      name
	 * @param values    values
	 * @param initValue initial value
	 * @param valueExt  value extension
	 */
	public SliderPane(String name, List<T> values, T initValue, String valueExt) {
		super();
		super.setLeft(new Label(Objects.requireNonNull(name)));
		this.values = Optional.ofNullable(values).filter(list -> !list.isEmpty())
				.orElse(Collections.singletonList(null));
		slider = new Slider(0, values.size() - 1.0, Math.max(0, values.indexOf(initValue)));
		this.valueExt = Objects.requireNonNullElse(valueExt, "");
		Label valueLabel = new Label();
		valueLabel.setAlignment(Pos.CENTER_RIGHT);
		valueLabel.setMinWidth(WIDTH);
		slider.valueProperty().addListener((observable, oldValue, newValue) -> updateValue(valueLabel));
		super.setBottom(slider);
		updateValue(valueLabel);
		super.setRight(valueLabel);
	}

	private void updateValue(Label valueLabel) {
		valueLabel.setText(MessageFormat.format("{0}{1}", getValue(), valueExt));
	}

	public T getValue() {
		return values.get((int) slider.getValue());
	}

	/**
	 * Get the nested slider
	 * 
	 * @return slider
	 */
	public Slider getSlider() {
		return slider;
	}

}
