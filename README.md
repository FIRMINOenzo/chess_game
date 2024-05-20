<h1 align="center" style="font-weight: bold;">Terminal Chess Game 💻♟</h1>

<p align="center">
  <a href="#tech">Technologies</a> • 
  <a href="#playing">Playing the game</a> • 
  <a href="#info">Pieces information</a> • 
  <a href="#build">How was it build</a>
</p>

<p align="center">
    <b>Simple terminal chess game with all its special moves!</b>
</p>

<h2 id="tech">💻 Technologies</h2>

- Java

<h2 id="playing">🚀 Playing the game</h2>

Learn how to run it on your machine.

<h3>Prerequisites</h3>

- [Git](https://git-scm.com/downloads)
- [Java LTS (21.0.3)](https://www.oracle.com/br/java/technologies/downloads/#java21)

<h3>Cloning</h3>

```bash
git clone git clone https://github.com/FIRMINOenzo/chess_game.git
```

<h3>Running the game</h3>

```bash
cd chess_game
javac -cp src src/Main.java -d out && java -cp ./out Main
```

If everything runs right, you should see something like this

```
   a b c d e f g h
   ---------------
8| R N B Q K B N R |8
7| P P P P P P P P |7
6| - - - - - - - - |6
5| - - - - - - - - |5
4| - - - - - - - - |4
3| - - - - - - - - |3
2| P P P P P P P P |2
1| R N B Q K B N R |1
   ---------------
   a b c d e f g h

Captured pieces:
White: []
Black: []

Turn: 1
Waiting player: WHITE

Source position:
```

<h2 id="info">♟ Pieces information</h2>

| Acronym | Piece description |
|---------|-------------------|
| R       | Rook ♜            |
| N       | Knight ♞          |
| B       | Bishop ♝          |
| Q       | Queen ♛           |
| K       | King ♚            |
| P       | Pawn ♟            |

<h2 id="build">🔨 How it was build</h2>

The project is developed using only pure Java.
Perhaps, that is what made it so enjoyable to develop.
The project is based on Object-Oriented Programming (OOP) principles.
These principles allowed me to develop it cleanly and concisely.

<h3>Some of OOP concepts used:</h3>

- Encapsulation
- Methods override
- Access modifiers
- Inheritance
- Upcasting
- Downcasting
- Static methods
- Polymorphism
- Abstract class & method
- Overloading

<h3>Others concepts:</h3>

Besides OOP, was used in this project too:

- Matrix manipulation
- Layers patterns
- Custom exceptions
- Enumerations
