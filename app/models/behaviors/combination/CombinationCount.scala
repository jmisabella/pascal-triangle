package models.behaviors.combination

import models.classes.Combination

trait CombinationCount {
  def combinationCount(n: Int, k: Int): Combination
}