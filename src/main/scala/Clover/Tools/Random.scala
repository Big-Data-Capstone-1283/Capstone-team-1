package Clover.Tools

import scala.annotation.tailrec

class Random extends scala.util.Random{

  def nextLong(n: Long): Long = {
    require(n > 0, "n must be positive")

    /*
     * Divide n by two until small enough for nextInt. On each
     * iteration (at most 31 of them but usually much less),
     * randomly choose both whether to include high bit in result
     * (offset) and whether to continue with the lower vs upper
     * half (which makes a difference only if odd).
     */

    var offset = 0L
    var _n = n

    while (_n >= Integer.MAX_VALUE) {
      val bits = nextInt(2)
      val halfn = _n >>> 1
      val nextn =
        if ((bits & 2) == 0) halfn
        else _n - halfn
      if ((bits & 1) == 0)
        offset += _n - nextn
      _n = nextn
    }
    offset + nextInt(_n.toInt)
  }

  def between(minInclusive: Double, maxExclusive: Double): Double = {
    require(minInclusive < maxExclusive, "Invalid bounds")

    val next = nextDouble() * (maxExclusive - minInclusive) + minInclusive
    if (next < maxExclusive) next
    else Math.nextAfter(maxExclusive, Double.NegativeInfinity)
  }
  def between(minInclusive: Float, maxExclusive: Float): Float = {
    require(minInclusive < maxExclusive, "Invalid bounds")

    val next = nextFloat() * (maxExclusive - minInclusive) + minInclusive
    if (next < maxExclusive) next
    else Math.nextAfter(maxExclusive, Float.NegativeInfinity)
  }
  def between(minInclusive: Int, maxExclusive: Int): Int = {
    require(minInclusive < maxExclusive, "Invalid bounds")

    val difference = maxExclusive - minInclusive
    if (difference >= 0) {
      nextInt(difference) + minInclusive
    } else {
      /* The interval size here is greater than Int.MaxValue,
       * so the loop will exit with a probability of at least 1/2.
       */
      @tailrec
      def loop(): Int = {
        val n = nextInt()
        if (n >= minInclusive && n < maxExclusive) n
        else loop()
      }
      loop()
    }
  }
  def between(minInclusive: Long, maxExclusive: Long): Long = {
    require(minInclusive < maxExclusive, "Invalid bounds")

    val difference = maxExclusive - minInclusive
    if (difference >= 0) {
      nextLong(difference) + minInclusive
    } else {
      /* The interval size here is greater than Long.MaxValue,
       * so the loop will exit with a probability of at least 1/2.
       */
      @tailrec
      def loop(): Long = {
        val n = nextLong()
        if (n >= minInclusive && n < maxExclusive) n
        else loop()
      }
      loop()
    }
  }
}
