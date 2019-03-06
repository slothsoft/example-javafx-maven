# JavaFX / Maven Example

[![Build Status](https://travis-ci.org/slothsoft/example-javafx-maven.svg?branch=java8)](https://travis-ci.org/slothsoft/example-javafx-maven)

- **Author:** [Stef Schulz](mailto:s.schulz@slothsoft.de)
- **Repository:** <https://github.com/slothsoft/example-javafx-maven>
- **Open Issues:** <https://github.com/slothsoft/example-javafx-maven/issues>
- **Wiki:** <https://github.com/slothsoft/example-javafx-maven/wiki>

This example shows how to create Hello World application using JavaFX and Maven. A step by step description can be found here.

![Screenshot](https://raw.githubusercontent.com/slothsoft/example-javafx-maven/java8/readme/screenshot.png)


**Content of this file:**

- [Getting Started](#getting-started)
- [Versions](#versions)
- [Tutorial](#tutorial)
- [License](#license)



## Getting Started

### Prerequisites

You need at least **Java 8** or above to run the code.




### Using the Example

Checkout this project and run the [MainApplication](https://github.com/slothsoft/example-javafx-maven/blob/java8/src/main/java/de/slothsoft/helloworld/HelloWorld.java).
     
          

##  Versions


| Version       |
| ------------- |
| [Java8](https://github.com/slothsoft/example-javafx-maven) |
| [Java7](https://github.com/slothsoft/example-javafx-maven/tree/java7) |
   


## Tutorial

Recently I played around with JavaFX for a bit. Since there is a lot of conflicting information out there how it is used with Maven, I'm going to give a short tour of my findings. We'll create a simple Hello World application on the way.

(**Note:** This tutorial was half-assed converted from Java 7 to Java 8, so... take everything with a grain of salt.)

### Ready...

For this tutorial a functional Eclipse installation is needed. [M2E](https://eclipse.org/m2e/) is, too, so our Maven configuration can be used for compiling in Eclipse. And of course we should have a Java with JavaFX, which JDK 8 has. 

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

		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				greetingLabel.setText("Hello " + nameText.getText() + "!");
			}
		});

		final Group root = new Group();
		root.getChildren().addAll(nameText, button, greetingLabel);

		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 240, 70));
		primaryStage.show();
	}
}
```

After copying the class, it will sprout errors everywhere because Eclipse cannot find the `javafx` packages. That is okay, since Java does not contain JavaFX officially, so it's not on the class path yet.

We have to put it there by ourselves, but in Maven it's quite simple to add dependencies. So we open our *pom.xml* and add:

```xml
	<dependencies>
		<dependency>
			<groupId>javafx</groupId>
			<artifactId>javafx</artifactId>
			<version>2.2</version>
			<scope>system</scope>
			<systemPath>${java.home}/lib/ext/jfxrt.jar</systemPath>
		</dependency>
	</dependencies>
```


Amazingly, the class compiles. If it does not, you might want to check that your `JAVA_HOME` variable is set and points to a JDK (and not just a JRE). Windows users can use `Win + Pause` and "Advanced System Settings" to check their environment variables. Linux users should know how to do that on their OS, Mac users have no business programming anyway ;)

So now we can start our masterpiece:

![Screenshot](https://raw.githubusercontent.com/slothsoft/example-javafx-maven/java8/readme/screenshot.png)

Additionally, now Maven is able to compile our project. But of course, the JAR won't start because it doesn't know where our `main()` method is. We will change that by adding

```xml
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<configuration>
					<archive>
						<manifest>
							<mainClass>de.slothsoft.helloworld.HelloWorld</mainClass>
						</manifest>
					</archive>
				</configuration>
			</plugin>
		</plugins>
	</build>
```

So now the JAR file is correct, but when run, will print the following exception:

```
Exception in thread "main" java.lang.NoClassDefFoundError: javafx/application/Application
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClass(Unknown Source)
        at java.security.SecureClassLoader.defineClass(Unknown Source)
        ... 
```

This is where the magic starts.

### Go!

What is the problem? We told Maven to use the system scope for JavaFX, which means the dependency is resolved by the container. But since JavaFX is not part of the class path the dependency is never fullfilled. There are some options to change that.

~~**Adding JavaFX to Manifest**~~

The quick and dirty way would be to add the JAR to the Manifest. Why is this not a real option? We'll for starters we would need to hardcode the `JAVA_HOME` path inside the JAR, and that can't be a good thing. In fact I can't think of a way to make this work on multiple workstations.

And even if we were to use a relative path and put the JavaFX JAR next to our application JAR we'd get an approach that probably wouldn't work once Java 8 is out.


~~**Adding JavaFX to Manifest**~~

Let's remember what the original problem was: The JavaFX library is not part of the Java class path. ~~When Java 8 comes, JavaFX will be part of the class path on default.~~ For some reason it isn't.


**Creating a Virtual Class Path**

Before the left mouse button was invented, people used the command line to start Java application. Since we can add to the class path that way, that is a nice option to start JavaFX applications for now:
```
java -cp "%JAVA_HOME%\jre\lib\ext\jfxrt.jar;hello-world-1.0.0.jar" de.slothsoft.helloworld.HelloWorld
```


~~**Wait for Java 8**~~

~~JavaFX is going to be officially released in Java 8, which should be available March 2014. It's not that long, and you won't need to add an additional dependency and it will start via double click.~~ Yes, you will have to add additional dependencies.


**Maven Plug-ins**

I found a [nice looking Maven plug-in](https://github.com/zonski/javafx-maven-plugin) to achieve the packaging, but couldn't get it to work, so I decided it wasn't worth the time.


#### Conclusion

I guess at this point you have a working JAR file and are happy. If not, and you think this might due to a faulty setup, I put the created project here for a reason. And if everything fails, I'm always happy to help. Good luck!


## License

This project is licensed under the MIT License - see the [MIT license](https://opensource.org/licenses/MIT) for details.
