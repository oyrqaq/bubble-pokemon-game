# Bubble Pokemon Game

A Java-based arcade game inspired by Bubble Bobble, featuring Pokemon characters and classic platformer gameplay.

## Description

Players control a hero character who must defeat Pokemon enemies by trapping them in bubbles. The game features 6 levels, multiple enemy types, power-ups, and a high score system.

## Deomo
![Demo](demo.gif)

## Features

- 6 unique levels with increasing difficulty
- Multiple enemy types with different behaviors  
- Power-up system for bonus points
- High score tracking for top 5 players
- Background music and sound effects

## Requirements

- Java 8 or higher
- Java Development Kit (JDK) for compilation

## Installation

1. Clone the repository:
```
git clone https://github.com/yourusername/bubble-pokemon-game.git
cd bubble-pokemon-game
```

2. Compile the Java files:
```
javac *.java
```

3. Run the game:
```
java Main
```

## Controls

- Arrow Up: Jump
- Arrow Left: Move left  
- Arrow Right: Move right
- Space: Shoot bubble
- U: Skip to next level (debug)
- D: Go to previous level (debug)

## Project Structure

```
bubble-pokemon-game/
├── src/              # Java source files
├── Images/           # Game sprites and graphics
├── MapLevels/        # Level data files (level1-level6)  
├── BGM.wav           # Background music
├── HighScores        # High score data
└── README.md
```

## Gameplay

Clear each level by defeating all enemies. Trap enemies in bubbles and pop them to earn points. Collect star power-ups for bonus points. You have 5 lives.

## Scoring

- Regular enemy: 200 points
- Shooting enemy: 400 points  
- Star power-up: 600-1000 points

## Building from Source

For Windows:
```
javac *.java
java Main
```

For Linux/macOS:
```
chmod +x build.sh
./build.sh
```

## Adding Custom Levels

1. Create a new level file in MapLevels/ directory
2. Use number grid format:
   - 0: Empty space
   - 1: Platform
   - 2: Regular enemy
   - 3: Shooting enemy


## License

MIT License