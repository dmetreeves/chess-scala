package com.seancheatham.chess

case class GameInfo(side_to_move: Option[Byte],
                    available_moves: Vector[(Byte, Byte)],
                    evaluation: Double,
                    winner: Option[Byte],
                   ) {
  def view: String =
    s"""Side to move:
       | $sideToMoveView
       |
       |Available moves:
       |$availableMovesView
       |
       |Evaluation:
       | $evaluation
       |
       |Winner:
       | $winnerView
       |""".stripMargin
  /**
   * Describes whose the next turn to move
   *
   * @return A String indicating the "side" whose turn it is now.
   *         "Black" if Black's move now
   *         "White" if White's move now
   *         "No one already" if the game is already finished
   */
  def sideToMoveView: String = side_to_move match {
    case Some(WHITE) => "White"
    case Some(BLACK) => "Black"
    case _ => "No one already"
  }

  /**
   * Shows available moves at the current state of the game
   * @return available moves as string splitted by new line character, "No more" otherwise
   */
  def availableMovesView: String = winner match {
    case None =>
      available_moves.map(moveBytesToANString).mkString("\n")
    case _ =>
      "No more"
  }

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
}
