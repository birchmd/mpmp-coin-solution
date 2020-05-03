package birchmd

// Model double jumps as a "reduced move" in which we connect two basic moves
// together if one starts where the other ends.
sealed trait ReducedMove {
  val start: Int
  val end: Int
}

object ReducedMove {
  case class Single(move: Move) extends ReducedMove {
    override val start: Int = move.start
    override val end: Int = move.end
  }
  case class Double(one: ReducedMove, two: Move) extends ReducedMove {
    override val start: Int = one.start
    override val end: Int = two.end
  }

  implicit val show = new cats.Show[ReducedMove] {
    private def listPositions(move: ReducedMove): List[Int] = move match {
      case Single(m) => List(m.start, m.end)

      case Double(one, two) => listPositions(one) ++ List(two.end)
    }
    override def show(move: ReducedMove): String =
      listPositions(move).mkString("-")
  }
}
