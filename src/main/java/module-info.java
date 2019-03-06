module de.slothsoft.helloworld {

	requires javafx.base;
	requires javafx.controls;

	opens de.slothsoft.helloworld to javafx.graphics;
}