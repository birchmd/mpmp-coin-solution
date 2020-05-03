package birchmd

import scala.annotation.tailrec
import scala.collection.mutable.StringBuilder

case class Grid(cells: Vector[Cell])

object Grid {
  val filled = Grid(Vector.fill(10)(Cell.Filled))

  // Computes possible moves (single jumps only) on the given grid
  def moves(g: Grid): Vector[Move] =
    g.cells.iterator.zipWithIndex.flatMap {
      case (Cell.Empty, _) => Iterator.empty
      case (Cell.Filled, i) =>
        val possibleMoves = Move.jumpsFrom.getOrElse(i + 1, Vector.empty)
        possibleMoves.iterator.filter { move =>
          g.cells(move.end - 1).isEmpty && g.cells(move.over - 1).isFilled
        }
    }.toVector

  // Applies the given move to the given grid to produce a new grid
  def applyMove(m: Move, g: Grid): Grid = Grid(
    g.cells.iterator.zipWithIndex
      .map {
        case (cell, i) => (cell, i + 1)
      }
      .map {
        case (Cell.Filled, i) if i == m.start                       => Cell.Empty
        case (Cell.Empty, i) if i == m.end                          => Cell.Filled
        case (Cell.Filled, i) if i == m.over                        => Cell.Empty
        case (cell, i) if i != m.start && i != m.end && i != m.over => cell
        case _                                                      => throw new Exception(s"Invalid move!\n${show.show(g)}\n$m")
      }
      .toVector
  )

  // Set a cell to be empty (used to start the game from a full grid)
  def remove(i: Int, g: Grid): Grid = Grid(
    g.cells.patch(i - 1, Iterator.single(Cell.Empty), 1)
  )

  // Some code for printing out the triangle somewhat nicely.
  implicit val show = new cats.Show[Grid] {
    private val numbersPerRow = Vector(1, 2, 3, 4)

    @tailrec
    private def foldN[A, B](it: Iterator[A], f: (B, A) => B, b0: B, n: Int): B =
      n match {
        case x if x <= 0 => b0
        case x =>
          val a = it.next()
          val b1 = f(b0, a)
          foldN(it, f, b1, x - 1)
      }

    private def folder(builder: StringBuilder, s: (Cell, Int)): StringBuilder =
      s match {
        case (Cell.Filled, i) => builder.addAll(s"[$i] ")
        case (Cell.Empty, i)  => builder.addAll(s" $i  ")
      }

    override def show(grid: Grid): String = {
      val it = grid.cells.iterator.zipWithIndex.map {
        case (cell, i) => (cell, i + 1)
      }
      val builder = new StringBuilder()

      numbersPerRow.foreach { n =>
        foldN(it, folder _, builder, n)
        builder.addOne('\n')
      }

      builder.result()
    }
  }
}
