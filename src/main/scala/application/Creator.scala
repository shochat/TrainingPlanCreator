package application

import com.github.nscala_time.time.Imports._
import model.{WorkoutType, Workout, WeeklyPlan}
import utils.{ApplicationConstants, TimeCalculator}
import scala.xml._

class Creator(raceDate: DateTime, raceDistance: Int, workoutDaysAmountInWeek: Int) {
  val m_weeksTillRace: Int = new TimeCalculator(raceDate).m_weeksTillRace
  val m_plan: Array[WeeklyPlan] = new Array[WeeklyPlan](m_weeksTillRace)
  val m_workoutDaysInWeek: Int = workoutDaysAmountInWeek

  def insertVolumeWorkout = {

    for (i <- 0 until m_weeksTillRace) {
      val index: String = "%s".format(i)
      if (m_plan(i) == null) m_plan(i) = new WeeklyPlan
      val xmlDoc = XML.load(getClass.getResourceAsStream("/volume-run-distances.xml"))
      val distance = ((xmlDoc \\ "root" \\ "marathon" \\ "week").filter(node => (node \\ "count").text == index) \\ "volume" ).text


      m_plan(i) addWorkout new Workout(WorkoutType.VOLUME_RUN, i * 9, distance.toInt, ApplicationConstants.VOLUME_RUN_DAY)
    }
  }

  def insertQualityWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) m_plan(i) = new WeeklyPlan

      m_plan(i) addWorkout new Workout(WorkoutType.QUALITY_RUN, i * 7, i * 3, ApplicationConstants.QUALITY_RUN_DAY)
    }
  }

  def insertTempoWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      val index: String = "%s".format(i)
      if (m_plan(i) == null) m_plan(i) = new WeeklyPlan
      val xmlDoc = XML.load(getClass.getResourceAsStream("/volume-run-distances.xml"))
      val distance = ((xmlDoc \\ "root" \\ "marathon" \\ "week").filter(node => (node \\ "count").text == index) \\ "tempo" ).text

      m_plan(i) addWorkout new Workout(WorkoutType.TEMPO_RUN, i * 5, distance.toInt, ApplicationConstants.TEMPO_RUN_DAY)
    }
  }

  def insertRecoveryWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      val index: String = String.valueOf(i)
      if (m_plan(i) == null) m_plan(i) = new WeeklyPlan
      val xmlDoc = XML.load(getClass.getResourceAsStream("/volume-run-distances.xml"))
      val distance = ((xmlDoc \\ "root" \\ "marathon" \\ "week").filter(node => (node \\ "count").text == index) \\ "recovery" ).text
      m_plan(i) addWorkout new Workout(WorkoutType.RECOVERY_RUN, i * 6, distance.toInt, ApplicationConstants.RECOVERY_RUN_DAY)
    }
  }

  def insertLiteVolumeWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) m_plan(i) = new WeeklyPlan
      m_plan(i) addWorkout new Workout(WorkoutType.LITE_VOLUME_RUN, i * 6, i * 2, ApplicationConstants.LITE_VOLUME_DAY)
    }
  }

  def insertChangedWorkout = ???

  def insertRestDay = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) m_plan(i) = new WeeklyPlan
      m_plan(i) addWorkout new Workout(WorkoutType.REST, 0, 0, ApplicationConstants.REST_DAY)
    }
  }

  def insertTaperPeriodWorkout = ???


  def insertPreparatoryRace(raceDate: DateTime, raceLength: Int) = {
    val raceWeekAndDayOfWeek = TimeCalculator.calculateEventWeekFromNowAndDayOfWeek(raceDate)
    m_plan(raceWeekAndDayOfWeek._1).addWorkout(new Workout(WorkoutType.PREPARATORY_RACE, 999999, raceLength, raceWeekAndDayOfWeek._2))
  }
  
  def createPlan = {
    if(m_workoutDaysInWeek < 4 ) {
      println("Can't create serious play for less than 4 days")
    } else {
      insertVolumeWorkout
      insertQualityWorkout
      insertTempoWorkout
      insertRecoveryWorkout
      if(workoutDaysAmountInWeek > 4) insertLiteVolumeWorkout
      if(workoutDaysAmountInWeek > 5) {
        insertChangedWorkout
        insertRestDay
      }
    }
    if(workoutDaysAmountInWeek == 7) println("You need at least one day of rest")
    if(workoutDaysAmountInWeek > 7) println("You entered a number too high, try again")
  }

  override def toString :String = {
    val sb :StringBuffer = new StringBuffer
    sb.append("WeeksTillRace: %d\n".format(m_weeksTillRace))
    for (i <- 1 to m_plan.length) {
      val weeklyPlan: WeeklyPlan = m_plan(i - 1)
      sb.append("Week %d (total %d km) schedule:".format(i, weeklyPlan.m_weeklyTotal))
      for (j <- 0 until weeklyPlan.m_weeklySchedule.length) {
        val workout: Workout = weeklyPlan.m_weeklySchedule(j)
        sb.append("\n\tWorkwout %d:\tworkoutType: %s,\tDistance: %d,\tDuration: %d,\tDayOfWeek: %d".format(j + 1,workout.m_workoutType, workout.m_distance, workout.m_duration, workout.m_dayOfWeek))
      }
      sb.append("\n")
    }
    return sb.toString
  }
}

object Creator {
  val m_raceDate: DateTime = new DateTime(2015,1,30,0,0,0)

  def apply(raceDate: DateTime, raceDistance: Int, workoutDaysAmountInWeek: Int) = new Creator(raceDate, raceDistance, workoutDaysAmountInWeek)
  
  def main(args: Array[String]) {
    val creator: Creator = Creator(m_raceDate, 42, 5)
    creator.createPlan
    println(creator.toString)
  }
}
