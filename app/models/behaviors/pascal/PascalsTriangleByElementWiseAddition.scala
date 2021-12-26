package models.behaviors.pascal

import models.behaviors.ElementWiseAddition
import scala.annotation.tailrec

trait PascalsTriangleByElementWiseAddition extends PascalsTriangle {
  type _ElementWiseAddition <: ElementWiseAddition
  val addition: _ElementWiseAddition

  // Each row is the previous sum of the 2 "shifted" versions of the previous row
  // For example,  
  //    [1 2 1]
  // will be shifted both left and right
  //   [1 2 1 0]
  //   [0 1 2 1]
  // and the sum of them is 
  //   [1 3 3 1]
  // which is the next row

  private sealed trait Direction
  private case object Left extends Direction
  private case object Right extends Direction
  def shift(ns: List[BigInt], direction: Direction): List[BigInt] = direction match {
    case Left => ns:::List(BigInt(0))
    case Right => BigInt(0)::ns
  }

  final def pascalRows(row: List[BigInt]): LazyList[Seq[BigInt]] = {
    LazyList.cons(row, pascalRows(addition.add(shift(row, Left), shift(row, Right))))
  }

  override def pascalTriangle(rows: Int): Seq[Seq[BigInt]] = pascalRows(List(BigInt(1))).take(rows)
}