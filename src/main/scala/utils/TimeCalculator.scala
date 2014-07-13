package utils

import org.joda.time.{Weeks, DateTime, Days}
import org.joda.time.DateTime.Property

/**
 * Created by ilan.s on 7/9/2014.
 */
class TimeCalculator(raceDate: DateTime) {
  var m_daysTillRace: Int = Days.daysBetween(DateTime.now(), raceDate).getDays
  var m_weeksTillRace: Int = Weeks.weeksBetween(DateTime.now(), raceDate).getWeeks
  var m_raceDayOfWeek: Int = raceDate.getDayOfWeek
}

object TimeCalculator{
  def calculateEventWeekFromNowAndDayOfWeek(eventDate: DateTime): (Int, Int) = {
    (Weeks.weeksBetween(DateTime.now, eventDate).getWeeks, eventDate.dayOfWeek().get())
  }
}
