package models.behaviors

import scala.annotation.tailrec

trait ElementWiseAddition {
  
  @tailrec
  final def add(l1: List[BigInt], l2: List[BigInt], acc: List[BigInt] = Nil): List[BigInt] = (l1, l2) match {
    case (Nil, Nil) => acc // no elements left in either list, so yield accumulated list
    case (ns, Nil) => acc:::ns // concatenate remaining uneven sized lists (no elements left to add)
    case (Nil, ns) => acc:::ns // concatenate remaining uneven sized lists (no elements left to add)
    case (n1::ns1, n2::ns2) => add(ns1, ns2, acc:::List(n1 + n2)) // current sum goes at end of list to prevent reversing the order
  }

  def add(l1: List[Int], l2: List[Int]): List[BigInt] = add(l1.map(BigInt(_)), l2.map(BigInt(_)))
} 

