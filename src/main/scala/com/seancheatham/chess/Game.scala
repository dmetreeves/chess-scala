package com.seancheatham.chess

/**
 * The state representation of a chess game.
 *
 * @param board The board with pieces
 * @param castling A Castling state of a game
 * @param moveCount The number of moves which have occurred on the board thus far
 */
case class Game(board: Board,
                castling: Castling,
                moveCount: Short) {

  /**
   * Executes the move from the given index to the given index. The value at the "from" index is set to Empty.
   * Any piece at the "to" index will be captured/removed from the board.
   *
   * @param from The index of the position from which the piece is moving
   * @param to   The target index to move to
   * @return A new instance of the updated Game, with all of the proper flags set
   */
  def move(from: Byte,
           to: Byte): Game = {

    if (moveIsPossible(from, to)) {
      val newCastling = castling.newState(board, from, to)
      val updatedBoard = newCastling.castledOrSameBoard(board) match {
        case castled if castled != board => castled
        case same =>
          dropCapturedPieceIfEnPassant(
            same.move(from, to),
            from, to
          )
      }
      copy(
        updatedBoard,
        newCastling,
        (moveCount + 1).toByte
      )
    } else {
      this
    }
  }

  /**
   * Flag indicating if it is White's turn to move. False indicates it is Black's turn to move.
   */
  def whiteToMove: Boolean = (moveCount % 2) == 0

  /**
   * Determines whose the next turn to move
   *
   * @return An option indicating the "side" whose turn it is now.
   *         Some([[domain.lib.chess#BLACK]]) if Black's move now
   *         Some([[domain.lib.chess#WHITE]]) if White's move now
   *         None if the game is already finished
   */
  def sideToMove: Option[Byte] = winner match {
    case None =>
      if (whiteToMove) Some(WHITE) else Some(BLACK)
    case _ => None
  }

  /**
   * Describes whose the next turn to move
   *
   * @return A String indicating the "side" whose turn it is now.
   *         "Black" if Black's move now
   *         "White" if White's move now
   *         "No one already" if the game is already finished
   */
  def sideToMoveView: String = sideToMove match {
    case Some(WHITE) => "White"
    case Some(BLACK) => "Black"
    case None => "No one already"
  }

  /**
   * Flag indicating if the move is possible according to chess rules at the current state of the game
   *
   * @param from The index of the position from which the piece is moving
   * @param to   The target index to move to
   * @return true if game in progress and move is possible, false otherwise
   */
  def moveIsPossible(from: Byte, to: Byte): Boolean =
    availableMoves.contains((from, to)) && winner.isEmpty

  /**
   * Helper alias to retrieve this board's available moves
   *
   * @return a sequence of (from, to) tuples, indicating the index of the piece being moved,
   *         and the index to move to
   */
  @inline
  def availableMoves: Vector[(Byte, Byte)] =
    Search.availableMoves(this)

  /**
   * Shows available moves at the current state of the game
   * @return available moves as string splitted by new line character, "No more" otherwise
   */
  def availableMovesView: String = winner match {
    case None =>
      availableMoves.map(moveBytesToANString).mkString("\n")
    case _ =>
      "No more"
  }

  /**
   * Helper alias to search this board
   *
   * @param depth The maximum depth to search
   * @return The best move to be made from the available moves
   */
  @inline
  def search(depth: Int) =
    Search(depth)(this)

  /**
   * Helper alias to evaluate this board
   *
   * @return A Double value representing the "favorability" of a
   *         particular side.  A negative value indicates that the BLACK side is favored; a positive value indicates that
   *         the WHITE side is favored; a value of zero represents a balanced game
   */
  @inline
  def evaluate =
    Evaluate(board)

  /**
   * Determines if there is a winner of the game, by checking for the non-existence of a King for each side.
   *
   * @return An option indicating the "side" which won.
   *         Some([[domain.lib.chess#BLACK]]) if Black won the match
   *         Some([[domain.lib.chess#WHITE]]) if White won the match
   *         None if the game is still in progress
   */
  def winner: Option[Byte] =
    if (board.pieces contains BK)
      if (board.pieces contains WK)
        None
      else
        Some(BLACK)
    else
      Some(WHITE)

  /**
   * Describes if there is a winner of the game
   *
   * @return A string indicating the "side" which won.
   *         "Black" if Black won the match
   *         "White" if White won the match
   *         "No one yet" if the game is still in progress
   */
  def winnerView: String = winner match {
    case Some(WHITE) => "White"
    case Some(BLACK) => "Black"
    case _ => "No one yet"
  }

  /**
   * Describes board as pieces in ascii encoding
   * example:
   *         8  ♜  ♞  ♝  ♛  ♚  ♝  ♞  ♜
   *         7  ♟  ♟  ♟  ♟  ♟  ♟  ♟  ♟
   *         6  .   .  .  .  .   .  .  .
   *         5  .   .  .  .  .   .  .  .
   *         4  .   .  .  .  .   .  .  .
   *         3  .   .  .  .  .   .  .  .
   *         2  ♙  ♙  ♙  ♙  ♙  ♙  ♙  ♙
   *         1  ♖  ♘  ♗  ♕  ♔  ♗  ♘  ♖
   *            a   b  c  d   e  f   g  h
   *
   * @return A string representing current chess board
   */
  def boardAsciiView: String = board.asciiView

  /**
   * Removes the captured piece from the board if current move is "En passant"
   *
   * @param _board The board from which the piece is removed
   * @param from The index of the position from which the piece is moving
   * @param to The target index to move to
   * @return
   */
  private def dropCapturedPieceIfEnPassant(_board: Board, from: Byte, to: Byte): Board = {
    if(whiteToMove) {
      if(List(a5,b5,c5,d5,e5,f5,g5,h5).contains(from)) {
        if(to == from.oneUpRight && _board.pieces(from.oneRight).isBlack) {
          _board.drop(
            from.oneRight
          )
        } else if(to == from.oneUpLeft && _board.pieces(from.oneLeft).isBlack) {
          _board.drop(
            from.oneLeft
          )
        } else
          _board
      } else
        _board
    } else { // black to move
      if(List(a4,b4,c4,d4,e4,f4,g4,h4).contains(from)) {
        if(to == from.oneDownLeft && _board.pieces(from.oneLeft).isWhite) {
          _board.drop(
            from.oneLeft
          )
        } else if(to == from.oneDownRight && _board.pieces(from.oneRight).isWhite) {
          _board.drop(
            from.oneRight
          )
        } else
          _board
      } else
        _board
    }
  }
}

object Game {

  final val start: Game =
    Game(
      Board.default,
      Castling.default,
      moveCount = 0
    )

}
