package birchmd

// Marker for the grid. Each position is either empty or filled.
sealed trait Cell {
  val isEmpty: Boolean
  def isFilled: Boolean = !isEmpty
}

object Cell {
  case object Filled extends Cell { val isEmpty = false }
  case object Empty extends Cell { val isEmpty = true }
}
