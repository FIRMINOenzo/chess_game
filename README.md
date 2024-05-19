<h1 align="center" style="font-weight: bold;">Terminal Chess Game 💻♟</h1>

<p align="center">
  <a href="#tech">Technologies</a> • 
  <a href="#playing">Playing the game</a> • 
  <a href="#info">Pieces information</a>
</p>

<p align="center">
    <b>Simple terminal chess game with all its special moves!</b>
</p>

<h2 id="tech">💻 Technologies</h2>

- Java

<h2 id="playing">🚀 Playing the game</h2>

Learn how to run it on your machine.

<h3>Prerequisites</h3>

- [Git](https://github.com/)
- [Java LTS (21.0.3)](https://github.com)

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

Here you can list the main routes of your API, and what are their expected request bodies.
​
| Acronym | Piece description                                          
|----------------------|-----------------------------------------------------
| R | Rook ♜
| N | Knight ♞
| B | Bishop ♝
| Q | Queen ♛
| K | King ♚
| P | Pawn ♟
