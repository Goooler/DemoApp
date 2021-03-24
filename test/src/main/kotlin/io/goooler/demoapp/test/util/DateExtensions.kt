@file:Suppress("NewApi", "ClassName", "unused", "UNUSED_PARAMETER")

package io.goooler.demoapp.test.util

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

object ago

object fromNow

inline val Int.nanoseconds: Duration get() = Duration.ofNanos(toLong())

inline val Int.microseconds: Duration get() = Duration.ofNanos(toLong() * 1000L)

inline val Int.milliseconds: Duration get() = Duration.ofMillis(toLong())

inline val Int.seconds: Duration get() = Duration.ofSeconds(toLong())

inline val Int.minutes: Duration get() = Duration.ofMinutes(toLong())

inline val Int.hours: Duration get() = Duration.ofHours(toLong())

inline val Int.days: Period get() = Period.ofDays(this)

inline val Int.weeks: Period get() = Period.ofWeeks(this)

inline val Int.months: Period get() = Period.ofMonths(this)

inline val Int.years: Period get() = Period.ofYears(this)

inline val Duration.ago: LocalDateTime get() = baseTime - this

inline val Duration.fromNow: LocalDateTime get() = baseTime + this

inline val Period.ago: LocalDate get() = baseDate - this

inline val Period.fromNow: LocalDate get() = baseDate + this

infix fun Int.nanoseconds(fromNow: fromNow): LocalDateTime = baseTime.plusNanos(toLong())

infix fun Int.nanoseconds(ago: ago): LocalDateTime = baseTime.minusNanos(toLong())

infix fun Int.microseconds(fromNow: fromNow): LocalDateTime = baseTime.plusNanos(1000L * toLong())

infix fun Int.microseconds(ago: ago): LocalDateTime = baseTime.minusNanos(1000L * toLong())

infix fun Int.milliseconds(fromNow: fromNow): LocalDateTime =
  baseTime.plusNanos(1000000L * toLong())

infix fun Int.milliseconds(ago: ago): LocalDateTime = baseTime.minusNanos(1000000L * toLong())

infix fun Int.seconds(fromNow: fromNow): LocalDateTime = baseTime.plusSeconds(toLong())

infix fun Int.seconds(ago: ago): LocalDateTime = baseTime.minusSeconds(toLong())

infix fun Int.minutes(fromNow: fromNow): LocalDateTime = baseTime.plusMinutes(toLong())

infix fun Int.minutes(ago: ago): LocalDateTime = baseTime.minusMinutes(toLong())

infix fun Int.hours(fromNow: fromNow): LocalDateTime = baseTime.plusHours(toLong())

infix fun Int.hours(ago: ago): LocalDateTime = baseTime.minusHours(toLong())

infix fun Int.days(fromNow: fromNow): LocalDate = baseDate.plusDays(toLong())

infix fun Int.days(ago: ago): LocalDate = baseDate.minusDays(toLong())

infix fun Int.weeks(fromNow: fromNow): LocalDate = baseDate.plusWeeks(toLong())

infix fun Int.weeks(ago: ago): LocalDate = baseDate.minusWeeks(toLong())

infix fun Int.months(fromNow: fromNow): LocalDate = baseDate.plusMonths(toLong())

infix fun Int.months(ago: ago): LocalDate = baseDate.minusMonths(toLong())

infix fun Int.years(fromNow: fromNow): LocalDate = baseDate.plusYears(toLong())

infix fun Int.years(ago: ago): LocalDate = baseDate.minusYears(toLong())

inline val baseDate: LocalDate get() = LocalDate.now()

inline val baseTime: LocalDateTime get() = LocalDateTime.now()
