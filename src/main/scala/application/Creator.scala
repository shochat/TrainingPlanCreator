package application

import com.github.nscala_time.time.Imports._
import model.{WorkoutType, Workout, WeeklyPlan}
import utils.{ApplicationConstants, TimeCalculator}

/**
 * Created by ilan.s on 7/9/2014.
 */
class Creator(raceDate: DateTime, raceDistance: Int, workoutDaysAmountInWeek: Int) {
  val m_weeksTillRace: Int = new TimeCalculator(raceDate).m_weeksTillRace
  val m_plan: Array[WeeklyPlan] = new Array[WeeklyPlan](m_weeksTillRace)

  def insertVolumeWorkout() = {
    for (i <- 0 until m_weeksTillRace) {
      if(m_plan(i) == null) m_plan(i) = new WeeklyPlan
      m_plan(i) addWorkout new Workout(WorkoutType.VOLUME_RUN, i * 9, i * 5, ApplicationConstants.VOLUME_RUN_DAY)
    }
  }

  def print() = {

      for (i <- 0 until m_plan.length) {
        val weeklyPlan: WeeklyPlan = m_plan(i)
        for (workout <- weeklyPlan.m_weeklySchedule)
          yield String.format("Week " + i + ":\n\tWorkoutType: " + workout.m_workoutType)
      }
    }
}

object Creator {
  val m_raceDate: DateTime = new DateTime(2014,10,18,0,0,0)

  def apply(raceDate: DateTime, raceDistance: Int, workoutDaysAmountInWeek: Int) = new Creator(raceDate, raceDistance, workoutDaysAmountInWeek)
  
  def main(args: Array[String]) {
    val creator: Creator = Creator(m_raceDate, 42, 5)
    creator.insertVolumeWorkout()
    println(creator.print())


  }

}
