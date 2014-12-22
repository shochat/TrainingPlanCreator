package utils

import org.joda.time.{DateTime, Days}

class TimeCalculator(raceDate: DateTime) {
  var m_weeksTillRace: Int = calculateTrainingWeeksTillRace
  var m_raceDayOfWeek: Int = raceDate.getDayOfWeek
  var m_workoutStartDay: DateTime = new DateTime

  def calculateTrainingWeeksTillRace = {
    m_workoutStartDay = DateTime.now.getDayOfWeek match {
      case 1 => DateTime.now.plusDays(6).withTimeAtStartOfDay
      case 2 => DateTime.now.plusDays(5).withTimeAtStartOfDay
      case 3 => DateTime.now.plusDays(4).withTimeAtStartOfDay
      case 4 => DateTime.now.plusDays(3).withTimeAtStartOfDay
      case 5 => DateTime.now.plusDays(2).withTimeAtStartOfDay
      case 6 => DateTime.now.plusDays(1).withTimeAtStartOfDay
      case 7 => DateTime.now.withTimeAtStartOfDay
    }
    Days.daysBetween(m_workoutStartDay, raceDate).getDays / 7
  }

  def calculateWorkoutWeek(eventDate: DateTime): Int = {
    Days.daysBetween(eventDate, raceDate).getDays / 7
  }
}

object TimeCalculator{
  def apply(raceDate: DateTime) = new TimeCalculator(raceDate)
}
