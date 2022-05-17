package com.seancheatham.chess

/**
 * The state representation of a chess game castling.

 * @param blackKingCastleAvailable Flag indicating if the Black King-side Castle is still available
 * @param blackQueenCastleAvailable Flag indicating if the Black Queen-side Castle is still available
 * @param whiteKingCastleAvailable Flag indicating if the White King-side Castle is still available
 * @param whiteQueenCastleAvailable Flag indicating if the White Queen-side Castle is still available
 */
case class Castling(blackKingCastleAvailable: Boolean = true,
                    blackQueenCastleAvailable: Boolean = true,
                    whiteKingCastleAvailable: Boolean = true,
                    whiteQueenCastleAvailable: Boolean = true,
                    isBlackKingCastling: Boolean = false,
                    isBlackQueenCastling: Boolean = false,
                    isWhiteKingCastling: Boolean = false,
                    isWhiteQueenCastling: Boolean = false,
                   ) {

  def newState(board: Board, from: Byte, to: Byte): Castling = {

    val isBKCastling: Boolean =
      blackKingCastleAvailable &&
        (
          board.pieces(from.toInt) == BK &&
            from == e8 &&
            to == g8
          )

    val isBQCastling: Boolean =
      blackQueenCastleAvailable &&
        !isBKCastling &&
        (
          board.pieces(from.toInt) == BK &&
            from == e8 &&
            to == c8
          )

    val isWKCastling: Boolean =
      whiteKingCastleAvailable &&
        !isBKCastling &&
        !isBQCastling &&
        (
          board.pieces(from.toInt) == WK &&
            from == e1 &&
            to == g1
          )

    val isWQCastling: Boolean =
      whiteQueenCastleAvailable &&
        !isBKCastling &&
        !isBQCastling &&
        !isWKCastling &&
        (
          board.pieces(from.toInt) == WK &&
            from == e1 &&
            to == c1
          )

    copy(
      (blackKingCastleAvailable || isBKCastling) && from != h8 && from != e8,
      (blackQueenCastleAvailable || isBQCastling) && from != a8 && from != e8,
      (whiteKingCastleAvailable || isWKCastling) && from != h1 && from != e1,
      (whiteQueenCastleAvailable || isWQCastling) && from != a1 && from != e1,
      isBKCastling,
      isBQCastling,
      isWKCastling,
      isWQCastling
    )
  }

  def castledOrSameBoard(board: Board): Board =
    if(isWhiteKingCastling) {
      board.move(e1, g1).move(h1, f1)
    } else if(isWhiteQueenCastling) {
      board.move(e1, c1).move(a1, d1)
    } else if(isBlackKingCastling) {
      board.move(e8, g8).move(h8, f8)
    } else if(isBlackQueenCastling) {
      board.move(e8, c8).move(a8, d8)
    } else board

  def view: String =
    s""" Black king castle available: $blackKingCastleAvailable
       | White king castle available: $whiteKingCastleAvailable
       | Black queen castle available: $blackQueenCastleAvailable
       | White queen castle available: $whiteQueenCastleAvailable
       |""".stripMargin
}

object Castling {
  final val default: Castling = Castling()
}

