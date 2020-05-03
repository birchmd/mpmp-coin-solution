package birchmd

// All possible moves on a size 10 triangle grid
sealed trait Move {
  val start: Int
  val end: Int
  val over: Int
}

object Move {
  val allMoves = Vector(
    OneFour,
    OneSix,
    TwoSeven,
    TwoNine,
    ThreeEight,
    ThreeTen,
    FourOne,
    FourSix,
    SixOne,
    SixFour,
    SevenTwo,
    SevenNine,
    EightThree,
    EightTen,
    NineTwo,
    NineSeven,
    TenThree,
    TenEight
  )

  val jumpsFrom = allMoves.groupBy(_.start)

  def show(m: Move): String = s"${m.start}-${m.end}"

  case object OneFour extends Move { val start = 1; val end = 4; val over = 2 }
  case object OneSix extends Move { val start = 1; val end = 6; val over = 3 }
  case object TwoSeven extends Move { val start = 2; val end = 7; val over = 4 }
  case object TwoNine extends Move { val start = 2; val end = 9; val over = 5 }
  case object ThreeEight extends Move {
    val start = 3; val end = 8; val over = 5
  }
  case object ThreeTen extends Move {
    val start = 3; val end = 10; val over = 6
  }
  case object FourOne extends Move { val start = 4; val end = 1; val over = 2 }
  case object FourSix extends Move { val start = 4; val end = 6; val over = 5 }
  case object SixOne extends Move { val start = 6; val end = 1; val over = 3 }
  case object SixFour extends Move { val start = 6; val end = 4; val over = 5 }
  case object SevenTwo extends Move { val start = 7; val end = 2; val over = 4 }
  case object SevenNine extends Move {
    val start = 7; val end = 9; val over = 8
  }
  case object EightThree extends Move {
    val start = 8; val end = 3; val over = 5
  }
  case object EightTen extends Move {
    val start = 8; val end = 10; val over = 9
  }
  case object NineTwo extends Move { val start = 9; val end = 2; val over = 5 }
  case object NineSeven extends Move {
    val start = 9; val end = 7; val over = 8
  }
  case object TenThree extends Move {
    val start = 10; val end = 3; val over = 6
  }
  case object TenEight extends Move {
    val start = 10; val end = 8; val over = 9
  }
}
