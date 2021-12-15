package io.goooler.demoapp.test.util

fun <T> unionIterable(one: Iterable<T>, two: Iterable<T>): Iterable<T> = one union two

fun <T> intersectIterable(one: Iterable<T>, two: Iterable<T>): Iterable<T> =
  one intersect two.toSet()

fun <T> subtractIterable(one: Iterable<T>, two: Iterable<T>): Iterable<T> = one subtract two.toSet()
