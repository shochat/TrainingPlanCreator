package model

import model.WorkoutType.WorkoutType
import org.joda.time.DateTime
import utils.ApplicationConstants

class Workout(workoutType: WorkoutType, duration: Int, distance: Int, dayOfWeek: Int, weekCount:Int) {
  val m_workoutType: WorkoutType = workoutType
  val m_duration: Int = duration
  val m_distance: Int = distance
  val m_dayOfWeek: Int = dayOfWeek
  val m_date: DateTime = getWorkoutDate(weekCount, workoutType)

  def getWorkoutDate(weekCount: Int, workoutType: WorkoutType): DateTime = {
    val workoutStartDay = DateTime.now.getDayOfWeek match {
      case 1 => DateTime.now.plusDays(6).withTimeAtStartOfDay
      case 2 => DateTime.now.plusDays(5).withTimeAtStartOfDay
      case 3 => DateTime.now.plusDays(4).withTimeAtStartOfDay
      case 4 => DateTime.now.plusDays(3).withTimeAtStartOfDay
      case 5 => DateTime.now.plusDays(2).withTimeAtStartOfDay
      case 6 => DateTime.now.plusDays(1).withTimeAtStartOfDay
      case 7 => DateTime.now.withTimeAtStartOfDay
    }

    return new DateTime(workoutStartDay.plusDays( (weekCount * 7) + m_dayOfWeek ))
  }


}
