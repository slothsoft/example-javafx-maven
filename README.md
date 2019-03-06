# JavaFX / Maven Example

[![Build Status](https://travis-ci.org/slothsoft/example-javafx-maven.svg?branch=master)](https://travis-ci.org/slothsoft/example-javafx-maven)

- **Author:** [Stef Schulz](mailto:s.schulz@slothsoft.de)
- **Repository:** <https://github.com/slothsoft/example-javafx-maven>
- **Open Issues:** <https://github.com/slothsoft/example-javafx-maven/issues>
- **Wiki:** <https://github.com/slothsoft/example-javafx-maven/wiki>

This example shows how to create Hello World application using JavaFX and Maven. A step by step description can be found here.

![Screenshot](https://raw.githubusercontent.com/slothsoft/example-javafx-maven/master/readme/screenshot.png)


**Content of this file:**

- [Getting Started](#getting-started)
- [Versions](#versions)
- [Tutorial](#tutorial)
- [License](#license)



## Getting Started

### Prerequisites

You need at least **Java 11** or above to run the code.




### Using the Example

Checkout this project and run the [MainApplication](https://github.com/slothsoft/example-javafx-maven/blob/master/src/main/java/de/slothsoft/helloworld/HelloWorld.java).
     
          

##  Versions


| Version       |
| ------------- |
| [Java11](https://github.com/slothsoft/example-javafx-maven/) |
| [Java8](https://github.com/slothsoft/example-javafx-maven/tree/java8) |
| [Java7](https://github.com/slothsoft/example-javafx-maven/tree/java7) |
   


## Tutorial

Recently I played around with JavaFX for a bit. Since there is a lot of conflicting information out there how it is used with Maven, I'm going to give a short tour of my findings. We'll create a simple Hello World application on the way.


### Ready...

For this tutorial a functional Eclipse installation is needed.  

### Set...

We'll start simply by creating a Maven project in Eclipse via *New → Other... → Maven / Maven Project*. We create a simple project (very first check box) and I'm going to name my group `de.slothsoft.example` and my artifact `example-javafx-maven`.

Since I want to keep it really, really simple for now, we just create a package `de.slothsoft.helloworld` and use this simple class as a test.

```java
package de.slothsoft.helloworld;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HelloWorld extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final TextField nameText = new TextField();
		nameText.setText("Stef");
		nameText.setLayoutX(10);
		nameText.setLayoutY(10);

		final Button button = new Button();
		button.setLayoutX(160);
		button.setLayoutY(10);
		button.setText("Greet me!");

		final Label greetingLabel = new Label();
		greetingLabel.setLayoutX(10);
		greetingLabel.setLayoutY(40);

		button.setOnAction(event -> greetingLabel.setText("Hello " + nameText.getText() + "!"));

		final Group root = new Group();
		root.getChildren().addAll(nameText, button, greetingLabel);

		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 240, 70));
		primaryStage.show();
	}
}
```

After copying the class, it will sprout errors everywhere because Eclipse cannot find the `javafx` packages. That is okay, since Java does not contain JavaFX, so it's not on the class path yet.

We have to put it there by ourselves, but in Maven it's quite simple to add dependencies. So we open our *pom.xml* and add:

```xml
 	<properties>
		<javafx.version>11.0.2</javafx.version>
 	</properties>

	<dependencies>
		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-controls</artifactId>
			<version>${javafx.version}</version>
		</dependency>
		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-graphics </artifactId>
			<version>${javafx.version}</version>
		</dependency>
	</dependencies>
```


Amazingly, the class compiles. So now we can start our masterpiece:

![Screenshot](https://raw.githubusercontent.com/slothsoft/example-javafx-maven/master/readme/screenshot.png)

Additionally, now Maven is able to compile our project. But of course, the JAR won't start because it doesn't know where our `main()` method is. This is where the magic starts.

### Go!

What is the problem? I honestly have no problem why it is so hard to get a working JavaFX application. It might have something to do with the OS libraries it contains, but other frameworks pull that off without a hitch.

And to be frank... I did not get JavaFX to work. At all. I even thought it was not possible. But thankfully I found a [repository](https://github.com/future2r/led) where somebody figured out how to build JavaFX. So big props to Future2R!

What you want to do is copy everything in the `<build>` tag of the *pom.xml*. After the build your application is in *target/runimage*, the start point being the *target/runimage/bin/Hello World.bat*. 

I stopped working with JavaFX after seeing this example through. Not only is the setup it really hard to come up with, this little Hello World application amounts to 76 MB of data. That's just ridiculous. 

You can still copy all of that, I put the project here for a reason. And if everything fails, I'm always happy to help. Good luck!


## License

This project is licensed under the MIT License - see the [MIT license](https://opensource.org/licenses/MIT) for details.
