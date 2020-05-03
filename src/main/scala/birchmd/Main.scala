package birchmd

import birchmd.Grid.show
import birchmd.ReducedMove.show
import cats.syntax.show._
import scala.annotation.tailrec

object Main {
  def main(args: Array[String]): Unit = {
    // Begin with cell 2 empty (there are no solutions that start with 1 or 5
    // empty, and others are the same as one of those by symmetry)
    val g = Grid.remove(2, Grid.filled)

    // Perform a depth-first search beginning from this state, keeping track of
    // the moves as we go. Note: at this stage we do not check for double jumps,
    // all moves are single jumps and we reduce them in the next step.
    val finalStates = dfs(Result(g, List.empty[Move]) :: Nil, Nil)

    // Only keep the final grids with one filled cell; also reduce moves which
    // are double jumps, e.g. `i-j`, `j-k` can be reduced to `i-j-k`.
    val reducedStates = finalStates
      .filter { case Result(g, _) => g.cells.count(_.isFilled) == 1 }
      .map {
        // We need to reverse the order of the moves since we were pushing to
        // the front of the list in the DFS loop
        case Result(g, moves) => Result(g, reduceMoves(moves).reverse)
      }

    // Find the minimal number of moves
    val minMoves = reducedStates.iterator.map(_.moves.size).min

    // Filter down the results to only those that take the minimal number
    val best = reducedStates.filter(_.moves.size == minMoves)

    // Show the answers
    best.iterator
      .foreach {
        case Result(g, ms) =>
          println(g.show)
          println(ms.map(_.show).mkString(", "))
          println("----------------------")
      }
    println(s"Total moves: $minMoves")
  }

  // Helper classes for keeping track of the data
  case class Result[A](grid: Grid, moves: List[A])
  type LoopState = List[Result[Move]]

  // Depth-first loop to find all possible end states.
  @tailrec
  def dfs(state: LoopState, acc: List[Result[Move]]): List[Result[Move]] =
    state match {
      // No more states to check, return the accumulator
      case Nil => acc

      case (r @ Result(g, ms)) :: rem =>
        // Compute available moves on this grid
        val newMoves = Grid.moves(g)

        // If there are no moves, the grid is an end state, so we add it to the
        // accumulator
        if (newMoves.isEmpty) {
          dfs(rem, r :: acc)
        } else {
          // If there are moves, then we generate a new grid to explore for each
          // move, and continue the loop.
          val newState = newMoves.foldLeft(rem) {
            case (xs, move) =>
              val newGrid = Grid.applyMove(move, g)
              Result(newGrid, move :: ms) :: xs
          }
          dfs(newState, acc)
        }
    }

  // Combine single jumps into double jumps where possible
  def reduceMoves(moves: List[Move]): List[ReducedMove] =
    moves.reverse
      .foldLeft(List.empty[ReducedMove]) {
        case (Nil, move) => ReducedMove.Single(move) :: Nil

        case (prev :: rest, next) =>
          if (prev.end == next.start)
            ReducedMove.Double(prev, next) :: rest
          else
            ReducedMove.Single(next) :: prev :: rest
      }

}
