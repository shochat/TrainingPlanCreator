package utils

import org.joda.time.{Weeks, DateTime, Days}

/**
 * Created by ilan.s on 7/9/2014.
 */
class TimeCalculator(raceDate: DateTime) {
  var m_daysTillRace: Int = Days.daysBetween(DateTime.now(), raceDate).getDays
  var m_weeksTillRace: Int = Weeks.weeksBetween(DateTime.now(), raceDate).getWeeks
  var m_raceDayOfWeek: Int = raceDate.getDayOfWeek
}
