package com.seancheatham.chess

case class Move(piece: Byte, from: Byte, to: Byte) {

  def asciiView: String =
    s"$pieceView: $fieldsView"

  private def fieldsView: String = {
    if(List((e1, g1), (e8, g8)) contains (from, to)) {
      s"${ moveBytesToANString((from, to)) } (castled king side)"
    } else if(List((e1, c1), (e8, c8)) contains (from, to)) {
      s"${ moveBytesToANString((from, to)) } (castled queen side)"
    } else {
      moveBytesToANString((from, to))
    }
  }

  private def pieceView: String = Map[Byte, String](
    BP -> "Black Pawn",
    BB -> "Black Bishop",
    BN -> "Black Knight",
    BR -> "Black Rook",
    BQ -> "Black Queen",
    BK -> "Black King",
    WP -> "White Pawn",
    WB -> "White Bishop",
    WN -> "White Knight",
    WR -> "White Rook",
    WQ -> "White Queen",
    WK -> "White King",
  ).getOrElse(piece, "Undefined piece")

}

