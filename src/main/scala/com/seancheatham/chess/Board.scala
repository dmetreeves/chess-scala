package com.seancheatham.chess

/**
 * The state representation of a chess board.
 *
 * A "Board" provides the functionality for search, evaluation, and move
 * generation (the core components of a chess engine).
 *
 * @param pieces A sequence of Bytes of length 120.  An actual chess board consists of an 8x8 grid; however,
 *               to allow for efficient move generation for knights, the board is padded by two squares on the top
 *               and bottom, and one square on the left and right side.  This board is represented with
 *               10 columns and 12 rows.  This improves move generation for knights by being able to detect
 *               an "out-of-bounds" square without having to deal with null pointer exceptions.  It also
 *               simplifies the math involved with move generation, since each row has ten slots,
 *               which correspond nicely to base-10 arithmetic
 */
case class Board(pieces: Vector[Byte]) {

  /**
   * Executes the move from the given index to the given index. The value at the "from" index is set to Empty.
   * Any piece at the "to" index will be captured/removed from the board.
   *
   * @param from The index of the position from which the piece is moving
   * @param to   The target index to move to
   * @return A new instance of the updated Board, with all of the proper flags set
   */
  def move(from: Byte, to: Byte): Board =
    copy(
      pieces
        .updated(to, pieces(from))
        .updated(from, _E),
    )

  /**
   * removes a piece from the given index of the chessboard.
   *
   * @param index The index of the position from which the piece is dropping
   * @return A new instance of the updated Board, without dropped piece
   */
  def drop(index: Byte): Board =
    copy(
      pieces.updated(
        index, _E
      )
    )

  /**
   * Shows a chessboard with pieces in the form of ascii characters
   *
   * @return A string representing a chessboard with pieces in the form of ascii characters
   */
  def asciiView: String =
    pieces.foldLeft(("", 0)) { (acc, piece) =>
      val (view, fieldIndex) = acc
      val maybeNewLine = if((fieldIndex+1) % 10 == 0 && fieldIndex > 19 && fieldIndex < 110) "\n" else ""
      val zeroToTwoWhitespaces =
        if(fieldIndex == 100) "  "
        else if(fieldIndex > 19 && fieldIndex < 110) " "
        else ""
      (view + zeroToTwoWhitespaces + AsciiSymbolByPieceOrField(piece, fieldIndex) + maybeNewLine, fieldIndex+1)
    } match {
      case (view, _) => view
    }

  /**
   * Shows a chessboard with pieces in the form of byte numbers
   *
   * @return A string representing a chessboard with pieces in the form of byte numbers
   */
  def byteView: String =
    pieces.foldLeft(("", 1)) { (acc, piece) =>
      val (view, fieldIndex) = acc
      val maybeNewLine = if(fieldIndex % 10 == 0) "\n" else ""
      (view + " " + piece.toString + maybeNewLine, fieldIndex+1)
    } match {
      case (view, _) => view
    }

  /**
   * Converts the type of the piece or the index of the chess field into an ascii encoded character
   *
   * @param piece A type of piece in the form of Byte number
   * @param field A chessboard field index
   * @return An ascii encoded character
   */
  private def AsciiSymbolByPieceOrField(piece: Byte, field: Int): String =
    (piece, field.toByte) match {
      case (BP, _) => "♟"
      case (BB, _) => "♝"
      case (BN, _) => "♞"
      case (BR, _) => "♜"
      case (BQ, _) => "♛"
      case (BK, _) => "♚"

      case (WP, _) => "♙"
      case (WB, _) => "♗"
      case (WN, _) => "♘"
      case (WR, _) => "♖"
      case (WQ, _) => "♕"
      case (WK, _) => "♔"

      case (_, `1`) => "1"
      case (_, `2`) => "2"
      case (_, `3`) => "3"
      case (_, `4`) => "4"
      case (_, `5`) => "5"
      case (_, `6`) => "6"
      case (_, `7`) => "7"
      case (_, `8`) => "8"

      case (_, `a`) => "a"
      case (_, `b`) => "b"
      case (_, `c`) => "c"
      case (_, `d`) => "d"
      case (_, `e`) => "e"
      case (_, `f`) => "f"
      case (_, `g`) => "g"
      case (_, `h`) => "h"

      case (_, f: Byte) if f.isBlackField => "•"
      case (_, f: Byte) if f.isWhiteField => "◦"

      case _ => ""
    }
}

object Board {

  final val default: Board =
    Board( DEFAULT_BOARD_SQUARES )

  final val challenge: Board =
    Board( CHALLENGE_BOARD_SQUARES )

}
