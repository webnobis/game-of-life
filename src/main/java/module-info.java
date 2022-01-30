/**
 * https://de.wikipedia.org/wiki/Conways_Spiel_des_Lebens
 * @author steffen
 *
 */
module com.webnobis.game.of.life {
	
	requires java.base;
	
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.swing;
	
	exports com.webnobis.game.of.life;

	opens com.webnobis.game.of.life to javafx.graphics;

}