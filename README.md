# Solution to Matt Parker's Coin Puzzle

See the puzzle on the [Think Maths website](https://www.think-maths.co.uk/coin-puzzle).

In this solution we use a depth-first search to find all possible end state from
a given starting state, then see what path gives the fewest number of moves to a
state with a single coin remaining.

Note that we only explore states starting from the one in which position 2 is
empty. This is because there are no solutions for states where positions 1 or 5
are empty (this can be determined by running the program with this
configuration), and all other starting states are equivalent to one of these
three by symmetry.

More details can be found on comments in the code.

Output of the program:

```
[info] running (fork) birchmd.Main
[info]  1
[info]  2  [3]
[info]  4   5   6
[info]  7   8   9   10
[info] 7-2, 1-4, 9-7-2, 6-1-4-6, 10-3
[info] ----------------------
[info]  1
[info]  2  [3]
[info]  4   5   6
[info]  7   8   9   10
[info] 7-2, 1-4, 9-7-2, 6-4-1-6, 10-3
[info] ----------------------
[info] Total moves: 5
```

Notice that there are two possible answers, depending on whether you want to do
the triple jump cycle in the clockwise or counterclockwise direction. No matter
what, we end with 3 as the only covered space at the end.

## Compiling and running yourself

This is a [Scala](https://www.scala-lang.org/) project. If you have
[sbt](https://www.scala-sbt.org/) installed then you can run it as follows:

```
$ sbt run
```