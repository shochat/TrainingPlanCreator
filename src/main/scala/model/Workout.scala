package model

import model.WorkoutType.WorkoutType

/**
 * Created by ilan.s on 7/9/2014.
 */
class Workout(workoutType: WorkoutType, duration: Int, distance: Int, dayOfWeek: Int) {
  val m_workoutType: WorkoutType = workoutType
  val m_duration: Int = duration
  val m_distance: Int = distance
  val m_dayOfWeek: Int = dayOfWeek
}
