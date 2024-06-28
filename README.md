# Shadow-Mario
## Game Project

This project is a simple game developed using the Bagel library, which is part of the University of Melbourne's SWEN20003 Object Oriented Software Development course. The game includes various interactive elements, such as players, enemies, and collectible items.

## Project Overview

The game involves navigating a character through different levels, collecting coins, avoiding enemies, and reaching the end flag to complete the level. The player can interact with various objects in the game world, and each object has unique behaviors and characteristics.

## Running the Game

To run the project, ensure you have Java 17 or a lower version of the JDK installed. Execute the `main` method in the `ShadowMario.java` file to start the game.

## Build Instructions

The Maven project should simply work with the command:

To run the tests, you'll need to comment out line 50 of `pom.xml` so that the test classes are compiled.

## Classes

### Attacker.java
- Represents an attacker in the game.
- Extends `Enemy`.

### Coin.java
- Represents a collectible coin.
- Implements `Collectable`.

### Collectable.java
- An interface for collectible items.

### EndFlag.java
- Represents the end flag that the player must reach to complete the level.

### Enemy.java
- Represents a generic enemy in the game.

### Messages.java
- Handles game messages.

### Platform.java
- Represents a platform in the game.

### Player.java
- Represents the player character.

### ShadowMario.java
- The main game class that sets up the game world.

### SolidBody.java
- Represents solid objects that the player can collide with.

### DoubleScore.java
- Represents an item that doubles the player's score.

### EnemyBoss.java
- Represents a boss enemy with more complex behavior.

### Fireball.java
- Represents a fireball that can harm the player.

### FlyPlatform.java
- Represents a moving platform.

### Harmful.java
- An interface for harmful objects.

### Invincible.java
- Represents an item that makes the player invincible.

### Level.java
- Represents a generic level in the game.

### Level1.java
- Represents the first level in the game.

### Level2.java
- Represents the second level in the game.

### Level3.java
- Represents the third level in the game.

### Vitality.java
- Represents the player's health and vitality.

## Bagel Library

Bagel (Basic Academic Graphical Engine Library) is a simple library for Java that uses LWJGL to present a simple game interface. It is designed to be used for SWEN20003 at the University of Melbourne, with the complexities of using Java hidden from students.

