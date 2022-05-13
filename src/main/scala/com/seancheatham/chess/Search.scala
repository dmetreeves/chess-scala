package com.seancheatham.chess

import scala.collection.parallel.CollectionConverters._

object Search {

  /**
   * Performs alpha/beta search (with pruning) on the given board
   *
   * @param depth The maximum depth to search
   * @param game The game to search
   * @return The best move to be made from the available moves
   */
  def apply(depth: Int = Config.SEARCH_DEPTH)(game: Game): (Byte, Byte) =
    game.availableMoves.par
      .maxBy(m =>
        alphaBetaMax(Double.MinValue, Double.MaxValue, depth)(
          game.move(m._1, m._2)
        )
      )

  /**
   * Computes the "max" side of alpha/beta search, by attempting to maximize the value returned
   *
   * @param alpha          The "alpha" value
   * @param beta           The "beta" value
   * @param remainingDepth The number of levels remaining to be searched
   * @param game The game to search
   * @return A Double indicating the "max" evaluated result of child boards
   */
  def alphaBetaMax(alpha: Double, beta: Double, remainingDepth: Int)(game: Game): Double =
    if (remainingDepth <= 0)
      game.evaluate
    else {
      var v = Double.MinValue
      var a = alpha
      val _availableMoves = game.availableMoves.iterator
      var continue = true
      while (continue && _availableMoves.hasNext) {
        val (from, to) =
          _availableMoves.next()
        v = Math.max(
          v,
          alphaBetaMin(a, beta, remainingDepth - 1)(game
            .move(from, to)
          )
        )
        a = Math.max(a, v)
        if (beta <= a)
          continue = false
      }
      v
    }

  /**
   * Computes the "min" side of alpha/beta search, by attempting to minimize the value returned
   *
   * @param alpha          The "alpha" value
   * @param beta           The "beta" value
   * @param remainingDepth The number of levels remaining to be searched
   * @param game The game to search
   * @return A Double indicating the "min" evaluated result of child boards
   */
  def alphaBetaMin(alpha: Double, beta: Double, remainingDepth: Int)(game: Game): Double =
    if (remainingDepth <= 0)
      -game.evaluate
    else {
      var v = Double.MaxValue
      var b = beta
      val _availableMoves = game.availableMoves.iterator
      var continue = true
      while (continue && _availableMoves.hasNext) {
        val (from, to) =
          _availableMoves.next()
        v = Math.min(v, alphaBetaMax(alpha, b, remainingDepth - 1)(game.move(from, to)))
        b = Math.min(b, v)
        if (b <= alpha)
          continue = false
      }
      v
    }

  /**
   * Determines the available moves which can be made by the current side to move.  The results of
   * this list are assumed to be valid moves, with the exception of moves which would
   * put the current player into a "checked" position, or if the current player does not move
   * out of an already "checked" position.  In these cases, we simply let the next turn
   * take place, where the opposing player would capture the king, resulting in a "check mate"
   *
   * To represent a "castle", the "from" index is the index of the King, and the "to" index is the
   * position to which the king will be moved after the castle.
   * The movement of the Rook is assumed implicitly.
   *
   * @param game The game to search
   * @return a sequence of (from, to) tuples, indicating the index of the piece being moved,
   *         and the index to move to
   */
  def availableMoves(game: Game): Vector[(Byte, Byte)] = {

    def diagonals(index: Int) = {
      var results =
        Vector.empty[Int]

      var i =
        index - 9

      var done =
        false

      def handle(i1: Int,
                 incrementer: Int) =
        if (game.board.pieces(i).isEmpty) {
          results = results :+ i
          i = i + incrementer
        }
        else if (if (game.whiteToMove) game.board.pieces(i).isBlack else game.board.pieces(i).isWhite) {
          results = results :+ i
          done = true
        }
        else
          done = true

      while (i >= 21 && !done)
        handle(i, -9)

      i = index - 11
      done = false

      while (i >= 21 && !done)
        handle(i, -11)

      i = index + 9
      done = false

      while (i <= 98 && !done)
        handle(i, 9)

      i = index + 11
      done = false

      while (i <= 98 && !done)
        handle(i, 11)


      results map ((index, _))
    }

    def horizontalsVerticals(index: Int) = {
      var results =
        Vector.empty[Int]

      var i =
        index - 10

      var done =
        false

      def handle(i1: Int,
                 incrementer: Int) =
        if (game.board.pieces(i).isEmpty) {
          results = results :+ i
          i = i + incrementer
        }
        else if (if (game.whiteToMove) game.board.pieces(i).isBlack else game.board.pieces(i).isWhite) {
          results = results :+ i
          done = true
        }
        else
          done = true

      while (i >= 21 && !done)
        handle(i, -10)

      i = index - 1
      done = false

      while (i >= 21 && !done)
        handle(i, -1)

      i = index + 10
      done = false

      while (i <= 98 && !done)
        handle(i, 10)

      i = index + 1
      done = false

      while (i <= 98 && !done)
        handle(i, 1)


      results map ((index, _))
    }

    def pawn(index: Int) = {
      val direction =
        if (game.whiteToMove) -1 else 1
      val downLeft =
        if (
          if (game.whiteToMove) {
            (
              game.board.pieces(index + 9 * direction).isBlack // basic capture move
                ||
                (List(a5,b5,c5,d5,e5,f5,g5,h5).contains(index) && game.board.pieces(index + 1).isBlack) // en passant capture move
              )
          } else {
            (
              game.board.pieces(index + 9 * direction).isWhite // basic capture move
                ||
                (List(a4,b4,c4,d4,e4,f4,g4,h4).contains(index) && game.board.pieces(index - 1).isWhite) // en passant capture move
              )
          }
        )
          Some((index, index + 9 * direction))
        else
          None
      val downRight =
        if (
          if (game.whiteToMove) {
            (
              game.board.pieces(index + 11 * direction).isBlack // basic capture move
                ||
                (List(a5,b5,c5,d5,e5,f5,g5,h5).contains(index) && game.board.pieces(index - 1).isBlack) // en passant capture move
              )
          } else {
            (
              game.board.pieces(index + 11 * direction).isWhite // basic capture move
                ||
                (List(a4,b4,c4,d4,e4,f4,g4,h4).contains(index) && game.board.pieces(index + 1).isWhite) // en passant capture move
              )
          }
        )
          Some((index, index + 11 * direction))
        else
          None
      val downOne =
        if (
          if (game.whiteToMove) game.board.pieces(index + 10 * direction).isEmpty
          else game.board.pieces(index + 10 * direction).isEmpty
        )
          Some((index, index + 10 * direction))
        else
          None
      val downTwo =
        if (index / 10 == (if (game.whiteToMove) 8 else 3) &&
          game.board.pieces(index + 10 * direction).isEmpty &&
          game.board.pieces(index + 20 * direction).isEmpty
        )
          Some((index, index + 20 * direction))
        else
          None

      Vector(downLeft, downRight, downOne, downTwo).flatten
    }

    def bishop =
      diagonals _

    def knight(index: Int) =
      Vector(-21, -19, -12, -8, 8, 12, 19, 21)
        .flatMap { offset =>
          val s =
            game.board.pieces(index + offset)
          if ((if (game.whiteToMove) s.isBlack else s.isWhite) || s.isEmpty)
            Some(index + offset)
          else
            None
        }
        .map((index, _))

    def rook =
      horizontalsVerticals _

    def queen(index: Int) =
      diagonals(index) ++
        horizontalsVerticals(index)

    def king(index: Int) =
      Vector(-11, -10, -9, -1, 1, 9, 10, 11)
        .flatMap { offset =>
          val s =
            game.board.pieces(index + offset)
          if ((if (game.whiteToMove) s.isBlack else s.isWhite) || s.isEmpty)
            Some(index + offset)
          else
            None
        }
        .map((index, _))

    val bkCastle =
      if (!game.whiteToMove &&
        game.castling.blackKingCastleAvailable &&
        game.board.pieces(26).isEmpty &&
        game.board.pieces(27).isEmpty
      )
        Some((25.toByte, 27.toByte))
      else
        None

    val bqCastle =
      if (!game.whiteToMove &&
        game.castling.blackQueenCastleAvailable &&
        game.board.pieces(22).isEmpty &&
        game.board.pieces(23).isEmpty &&
        game.board.pieces(24).isEmpty
      )
        Some((25.toByte, 23.toByte))
      else
        None

    val wkCastle =
      if (game.whiteToMove &&
        game.castling.whiteKingCastleAvailable &&
        game.board.pieces(96).isEmpty &&
        game.board.pieces(97).isEmpty
      )
        Some((95.toByte, 97.toByte))
      else
        None

    val wqCastle =
      if (game.whiteToMove &&
        game.castling.whiteQueenCastleAvailable &&
        game.board.pieces(92).isEmpty &&
        game.board.pieces(93).isEmpty &&
        game.board.pieces(94).isEmpty
      )
        Some((95.toByte, 93.toByte))
      else
        None

    Vector(bkCastle, bqCastle, wkCastle, wqCastle).flatten ++
      game.board.pieces
        .indices
        // We apply several transformations here, so to avoid the
        // intermediate collections, work from a view instead
        .view
        .filter{index => if (game.whiteToMove) game.board.pieces(index).isWhite else game.board.pieces(index).isBlack}
        .flatMap(index =>
          game.board.pieces(index) match {
            case `_I` | `_E` =>
              Vector.empty
            case `BP` | `WP` =>
              pawn(index)
            case `BB` | `WB` =>
              bishop(index)
            case `BN` | `WN` =>
              knight(index)
            case `BR` | `WR` =>
              rook(index)
            case `BQ` | `WQ` =>
              queen(index)
            case `BK` | `WK` =>
              king(index)
          }
        )
        .map { case (f, t) => (f.toByte, t.toByte) }
      // To assist with the "pruning" in alpha/beta pruning, sort the result
      // by the "weight" of the piece at the destination index.  Pieces
      // with higher weights will be searched first.
      //.toVector // have to convert view to the vector due to the fact that scala.collection.SeqView changing type to the scala.collection.View after 'filter' method applied and the method 'sortBy' becomes unavailable for the view
      //.sortBy(move => -game.board.pieces(move._2).weight)
  }

}

