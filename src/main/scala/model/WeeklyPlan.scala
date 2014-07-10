package model

import scala.collection.mutable.ListBuffer

/**
 * Created by ilan.s on 7/9/2014.
 */
class WeeklyPlan {
  val m_weeklySchedule: ListBuffer[Workout] = ListBuffer()
  var m_weeklyTotal: Int = m_weeklySchedule.map(workout => workout.m_distance).sum

  def addWorkout(workout: Workout) = {
    m_weeklySchedule += workout
    m_weeklyTotal += workout.m_distance
  }
}
