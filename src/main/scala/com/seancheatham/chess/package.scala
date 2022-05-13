package com.seancheatham

package object chess {

  //
  // Piece-type Constants
  //
  /**
   * An "invalid" square, or "out-of-bounds"
   */
  final val _I: Byte =
    0

  /**
   * An "empty" square
   */
  final val _E: Byte =
    1

  /**
   * Black Pawn piece
   */
  final val BP: Byte =
    2

  /**
   * Black Bishop piece
   */
  final val BB: Byte =
    3

  /**
   * Black Knight piece ("N" is a convention to avoid conflicting with the King)
   */
  final val BN: Byte =
    4

  /**
   * Black Rook piece
   */
  final val BR: Byte =
    5

  /**
   * Black Queen piece
   */
  final val BQ: Byte =
    6

  /**
   * Black King piece
   */
  final val BK: Byte =
    7

  /**
   * White Pawn piece
   */
  final val WP: Byte =
    8

  /**
   * White Bishop piece
   */
  final val WB: Byte =
    9

  /**
   * White Knight piece ("N" is a convention to avoid conflicting with the King)
   */
  final val WN: Byte =
    10

  /**
   * White Rook piece
   */
  final val WR: Byte =
    11

  /**
   * White Queen piece
   */
  final val WQ: Byte =
    12

  /**
   * White King piece
   */
  final val WK: Byte =
    13

  //
  // Board-rank-names Constants in Algebraic notation
  //
  /**
   * Rank 1 index
   */
  final val `1`: Byte =
    90

  /**
   * Rank 2 index
   */
  final val `2`: Byte =
    80

  /**
   * Rank 3 index
   */
  final val `3`: Byte =
    70

  /**
   * Rank 4 index
   */
  final val `4`: Byte =
    60

  /**
   * Rank 5 index
   */
  final val `5`: Byte =
    50

  /**
   * Rank 6 index
   */
  final val `6`: Byte =
    40

  /**
   * Rank 7 index
   */
  final val `7`: Byte =
    30

  /**
   * Rank 8 index
   */
  final val `8`: Byte =
    20

  //
  // Board-file-names Constants in Algebraic notation
  //
  /**
   * A File index
   */
  final val `a`: Byte =
    101

  /**
   * B File index
   */
  final val `b`: Byte =
    102

  /**
   * C File index
   */
  final val `c`: Byte =
    103

  /**
   * D File index
   */
  final val `d`: Byte =
    104

  /**
   * E File index
   */
  final val `e`: Byte =
    105

  /**
   * F File index
   */
  final val `f`: Byte =
    106

  /**
   * G File index
   */
  final val `g`: Byte =
    107

  /**
   * H File index
   */
  final val `h`: Byte =
    108

  /**
   * Merges the indices of squares of coordinates of verticals and horizontals in chess algebraic notation
   * and returns the index of the square in the coordinate system of the board
   *
   * @param file An index representing the vertical address in chess algebraic notation
   *             (represented by constants `a`, `b`, `c`, `d`, `e`, `f`, `g`, `h`)
   * @param rank An index representing the horizontal address in chess algebraic notation
   *             (represented by constants `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`)
   * @return The index of the square on the chessboard corresponding to the transmitted coordinates
   */
  def merge(file: Byte, rank: Byte): Byte = (file + rank - 100).toByte

  /**
   * Indices of squares in chess algebraic notation on the board
   */
  final val a1: Byte = merge(`a`, `1`); final val b1: Byte = merge(`b`, `1`);
  final val a2: Byte = merge(`a`, `2`); final val b2: Byte = merge(`b`, `2`);
  final val a3: Byte = merge(`a`, `3`); final val b3: Byte = merge(`b`, `3`);
  final val a4: Byte = merge(`a`, `4`); final val b4: Byte = merge(`b`, `4`);
  final val a5: Byte = merge(`a`, `5`); final val b5: Byte = merge(`b`, `5`);
  final val a6: Byte = merge(`a`, `6`); final val b6: Byte = merge(`b`, `6`);
  final val a7: Byte = merge(`a`, `7`); final val b7: Byte = merge(`b`, `7`);
  final val a8: Byte = merge(`a`, `8`); final val b8: Byte = merge(`b`, `8`);

  final val c1: Byte = merge(`c`, `1`); final val d1: Byte = merge(`d`, `1`);
  final val c2: Byte = merge(`c`, `2`); final val d2: Byte = merge(`d`, `2`);
  final val c3: Byte = merge(`c`, `3`); final val d3: Byte = merge(`d`, `3`);
  final val c4: Byte = merge(`c`, `4`); final val d4: Byte = merge(`d`, `4`);
  final val c5: Byte = merge(`c`, `5`); final val d5: Byte = merge(`d`, `5`);
  final val c6: Byte = merge(`c`, `6`); final val d6: Byte = merge(`d`, `6`);
  final val c7: Byte = merge(`c`, `7`); final val d7: Byte = merge(`d`, `7`);
  final val c8: Byte = merge(`c`, `8`); final val d8: Byte = merge(`d`, `8`);

  final val e1: Byte = merge(`e`, `1`); final val f1: Byte = merge(`f`, `1`);
  final val e2: Byte = merge(`e`, `2`); final val f2: Byte = merge(`f`, `2`);
  final val e3: Byte = merge(`e`, `3`); final val f3: Byte = merge(`f`, `3`);
  final val e4: Byte = merge(`e`, `4`); final val f4: Byte = merge(`f`, `4`);
  final val e5: Byte = merge(`e`, `5`); final val f5: Byte = merge(`f`, `5`);
  final val e6: Byte = merge(`e`, `6`); final val f6: Byte = merge(`f`, `6`);
  final val e7: Byte = merge(`e`, `7`); final val f7: Byte = merge(`f`, `7`);
  final val e8: Byte = merge(`e`, `8`); final val f8: Byte = merge(`f`, `8`);

  final val g1: Byte = merge(`g`, `1`); final val h1: Byte = merge(`h`, `1`);
  final val g2: Byte = merge(`g`, `2`); final val h2: Byte = merge(`h`, `2`);
  final val g3: Byte = merge(`g`, `3`); final val h3: Byte = merge(`h`, `3`);
  final val g4: Byte = merge(`g`, `4`); final val h4: Byte = merge(`h`, `4`);
  final val g5: Byte = merge(`g`, `5`); final val h5: Byte = merge(`h`, `5`);
  final val g6: Byte = merge(`g`, `6`); final val h6: Byte = merge(`h`, `6`);
  final val g7: Byte = merge(`g`, `7`); final val h7: Byte = merge(`h`, `7`);
  final val g8: Byte = merge(`g`, `8`); final val h8: Byte = merge(`h`, `8`);

  //
  // Team/Side Constants
  //
  final val BLACK: Byte =
  0

  final val WHITE: Byte =
    1

  //
  // Board Constants
  //
  /**
   * A collection of squares which are legal on the board.  Since a [[com.seancheatham.chess.Board]] is
   * represented as 10x12, the outer squares provide padding, but are illegal for play
   */
  final val LEGAL_SQUARES: Vector[Byte] =
    (
      (a8 to h8).toVector ++
        (a7 to h7) ++
        (a6 to h6) ++
        (a5 to h5) ++
        (a4 to h4) ++
        (a3 to h3) ++
        (a2 to h2) ++
        (a1 to h1)
      )
      .map(_.toByte)

  /**
   * The basic chess board representation
   */
  final val DEFAULT_BOARD_SQUARES =
    Vector[Byte](
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I, // 9
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I, // 19
      _I, BR, BN, BB, BQ, BK, BB, BN, BR, _I, // 29
      _I, BP, BP, BP, BP, BP, BP, BP, BP, _I, // 39
      _I, _E, _E, _E, _E, _E, _E, _E, _E, _I, // 49
      _I, _E, _E, _E, _E, _E, _E, _E, _E, _I, // 59
      _I, _E, _E, _E, _E, _E, _E, _E, _E, _I, // 69
      _I, _E, _E, _E, _E, _E, _E, _E, _E, _I, // 79
      _I, WP, WP, WP, WP, WP, WP, WP, WP, _I, // 89
      _I, WR, WN, WB, WQ, WK, WB, WN, WR, _I, // 99
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I, // 109
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I // 119
    )

  /**
   * A "challenge" test board, for testing purposes
   */
  final val CHALLENGE_BOARD_SQUARES =
    Vector[Byte](
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I, // 9
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I, // 19
      _I, _E, BK, _E, BR, _E, BN, BR, _E, _I, // 29
      _I, BP, _E, BQ, _E, BB, BP, _E, _E, _I, // 39
      _I, BP, BB, BP, _E, BP, _E, _E, _E, _I, // 49
      _I, _E, _E, WN, BP, WP, BN, _E, BP, _I, // 59
      _I, WP, WP, _E, WP, _E, _E, BP, _E, _I, // 69
      _I, _E, _E, WP, _E, _E, _E, _E, _E, _I, // 79
      _I, _E, _E, _E, _E, WQ, WP, WP, WP, _I, // 89
      _I, WR, _E, WB, _E, WN, WR, WK, _E, _I, // 99
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I, // 109
      _I, _I, _I, _I, _I, _I, _I, _I, _I, _I // 119

    )

  //
  // Implicit Helpers
  //
  implicit class PieceTypeImplicits(pieceType: Byte) {

    def isWhite: Boolean =
      (WP to WK) contains pieceType

    def isBlack: Boolean =
      (BP to BK) contains pieceType

    def isEmpty: Boolean =
      pieceType == _E

    def isIllegal: Boolean =
      pieceType == _I

    def weight: Double =
      pieceType match {
        case `_E` | `_I` => 0
        case `BP` | `WP` => 1
        case `BB` | `WB` => 3
        case `BN` | `WN` => 3
        case `BR` | `WR` => 5
        case `BQ` | `WQ` => 9
        case `BK` | `WK` => 22 // If you leave Double.MaxValue here, then Evaluate.apply will normally return not 0.0, but 11.0
      }

    def isWhiteField: Boolean =
      List(
        a8, c8, e8, g8,
        b7, d7, f7, h7,
        a6, c6, e6, g6,
        b5, d5, f5, h5,
        a4, c4, e4, g4,
        b3, d3, f3, h3,
        a2, c2, e2, g2,
        b1, d1, f1, h1
      ) contains pieceType.toInt

    def isBlackField: Boolean =
      List(
        b8, d8, f8, h8,
        a7, c7, e7, g7,
        b6, d6, f6, h6,
        a5, c5, e5, g5,
        b4, d4, f4, h4,
        a3, c3, e3, g3,
        b2, d2, f2, h2,
        a1, c1, e1, g1
      ) contains pieceType.toInt

    def oneUpRight: Byte =
      (pieceType - 9).toByte

    def oneDownRight: Byte =
      (pieceType + 11).toByte

    def oneRight: Byte =
      (pieceType + 1).toByte

    def oneUpLeft: Byte =
      (pieceType - 11).toByte

    def oneDownLeft: Byte =
      (pieceType + 9).toByte

    def oneLeft: Byte =
      (pieceType - 1).toByte
  }

  val anFieldsBytesPerStrings: Map[String, Byte] = Map[String, Byte](elems =
    "a1"->a1,"a2"->a2,"a3"->a3,"a4"->a4,"a5"->a5,"a6"->a6,"a7"->a7,"a8"->a8,
    "b1"->b1,"b2"->b2,"b3"->b3,"b4"->b4,"b5"->b5,"b6"->b6,"b7"->b7,"b8"->b8,
    "c1"->c1,"c2"->c2,"c3"->c3,"c4"->c4,"c5"->c5,"c6"->c6,"c7"->c7,"c8"->c8,
    "d1"->d1,"d2"->d2,"d3"->d3,"d4"->d4,"d5"->d5,"d6"->d6,"d7"->d7,"d8"->d8,
    "e1"->e1,"e2"->e2,"e3"->e3,"e4"->e4,"e5"->e5,"e6"->e6,"e7"->e7,"e8"->e8,
    "f1"->f1,"f2"->f2,"f3"->f3,"f4"->f4,"f5"->f5,"f6"->f6,"f7"->f7,"f8"->f8,
    "g1"->g1,"g2"->g2,"g3"->g3,"g4"->g4,"g5"->g5,"g6"->g6,"g7"->g7,"g8"->g8,
    "h1"->h1,"h2"->h2,"h3"->h3,"h4"->h4,"h5"->h5,"h6"->h6,"h7"->h7,"h8"->h8,
  )

  val anFieldsStringsPerBytes: Map[Byte, String] = for((k,v) <- anFieldsBytesPerStrings) yield (v, k)

  def anFieldStringToByte(field: String): Byte = anFieldsBytesPerStrings.getOrElse(field, -1)

  def anFieldByteToString(field: Byte): String = anFieldsStringsPerBytes.getOrElse(field, "undefined")

  def moveBytesToANString(move: (Byte, Byte)): String = move match {
    case (from, to) =>
      s"${ fileByteToANString(from) }${ rankByteToANString(from) } ⇝ ${ fileByteToANString(to) }${ rankByteToANString(to) }"
  }

  private def rankByteToANString(index: Byte): String = {
    if(index >= a1 && index <= h1) {
      "1"
    } else if(index >= a2 && index <= h2) {
      "2"
    } else if(index >= a3 && index <= h3) {
      "3"
    } else if(index >= a4 && index <= h4) {
      "4"
    } else if(index >= a5 && index <= h5) {
      "5"
    } else if(index >= a6 && index <= h6) {
      "6"
    } else if(index >= a7 && index <= h7) {
      "7"
    } else {
      "8"
    }
  }

  private def fileByteToANString(index: Byte): String = {
    index % 10 match {
      case 1 => "a"
      case 2 => "b"
      case 3 => "c"
      case 4 => "d"
      case 5 => "e"
      case 6 => "f"
      case 7 => "g"
      case 8 => "h"
      case _ => "unknown"
    }
  }

}
