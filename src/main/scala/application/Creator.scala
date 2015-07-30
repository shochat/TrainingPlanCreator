package application

import com.github.nscala_time.time.Imports._
import model.{WeeklyPlan, Workout, WorkoutType}
import utils.{ApplicationConstants, DbManager, TimeCalculator}

import scala.xml._

class Creator(raceDate: DateTime, raceDistance: Int, workoutDaysAmountInWeek: Int, targetPace: Float) {
  val m_weeksTillRace: Int = new TimeCalculator(raceDate).m_weeksTillRace
  val m_plan: Array[WeeklyPlan] = new Array[WeeklyPlan](m_weeksTillRace)
  val m_workoutDaysInWeek: Int = workoutDaysAmountInWeek
  val m_targetPace: Float = targetPace

  def extractValueFromXml(xmlPath: String, nodeCount: String, requestedValueName: String): Int = {
    val xmlDoc = XML.load(getClass.getResourceAsStream("/volume-run-distances.xml"))
    val xmlPathAsArray = xmlPath.split(" ")
    ((xmlDoc \\ xmlPathAsArray(0) \\ xmlPathAsArray(1) \\ xmlPathAsArray(2)).filter(node => (node \\ "count").text == nodeCount) \\ requestedValueName ).text.toInt
  }

  def extractDistanceFromDb(schema: String, query: String, columnName: String): Int = {
    DbManager.selectSingleCell(schema, query, columnName)
  }


  def insertVolumeWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) {
        m_plan(i) = new WeeklyPlan
      }
      if(! m_plan(i).m_weeklySchedule.exists(workout => (workout.m_workoutType == WorkoutType.PREPARATORY_RACE))) {
        val query: String = "SELECT * FROM workout_distance WHERE workout_type = 'VOLUME' AND week_before_race_count = " + (i + 1)
        val distance: Int = extractDistanceFromDb("tpc", query, "distance")
        m_plan(i) addWorkout new Workout(WorkoutType.VOLUME_RUN, (distance * m_targetPace).toInt, distance, ApplicationConstants.VOLUME_RUN_DAY, i)
      }
    }
  }


  def insertQualityWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) {
        m_plan(i) = new WeeklyPlan
      }
      if (! m_plan(i).m_weeklySchedule.exists(workout => (workout.m_dayOfWeek == ApplicationConstants.QUALITY_RUN_DAY))) {
        val query: String = "SELECT * FROM workout_distance WHERE workout_type = 'QUALITY' AND week_before_race_count = " + (i + 1)
        val distance: Int = extractDistanceFromDb("tpc", query, "distance")
        m_plan(i) addWorkout new Workout(WorkoutType.QUALITY_RUN, (distance * m_targetPace).toInt, distance, ApplicationConstants.QUALITY_RUN_DAY, i)
      }
    }
  }

  def insertTempoWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) {
        m_plan(i) = new WeeklyPlan
      }
      if (! m_plan(i).m_weeklySchedule.exists(workout => (workout.m_dayOfWeek == ApplicationConstants.TEMPO_RUN_DAY))) {
        val query: String = "SELECT * FROM workout_distance WHERE workout_type = 'TEMPO' AND week_before_race_count = " + (i + 1)
        val distance: Int = extractDistanceFromDb("tpc", query, "distance")
        m_plan(i) addWorkout new Workout(WorkoutType.TEMPO_RUN, (distance * m_targetPace).toInt, distance, ApplicationConstants.TEMPO_RUN_DAY, i)
      }
    }
  }

  def insertRecoveryWorkout = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) {
        m_plan(i) = new WeeklyPlan
      }
      if (! m_plan(i).m_weeklySchedule.exists(workout => (workout.m_dayOfWeek == ApplicationConstants.RECOVERY_RUN_DAY))) {
        val query: String = "SELECT * FROM workout_distance WHERE workout_type = 'RECOVERY' AND week_before_race_count = " + (i + 1)
        val distance: Int = extractDistanceFromDb("tpc", query, "distance")
        m_plan(i) addWorkout new Workout(WorkoutType.RECOVERY_RUN, (distance * m_targetPace).toInt, distance, ApplicationConstants.RECOVERY_RUN_DAY, i)
      }
    }
  }

  def insertLiteVolumeWorkout = {
    val defaultLiteRunDistance: Int = 8
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) {
        m_plan(i) = new WeeklyPlan
      }
      if (! m_plan(i).m_weeklySchedule.exists(workout => (workout.m_dayOfWeek == ApplicationConstants.LITE_VOLUME_DAY))) {
        val query: String = "SELECT * FROM workout_distance WHERE workout_type = 'LITE_VOLUME' AND week_before_race_count = " + (i + 1)
        val distance: Int = extractDistanceFromDb("tpc", query, "distance")
        m_plan(i) addWorkout new Workout(WorkoutType.LITE_VOLUME_RUN, (distance * m_targetPace).toInt, distance, ApplicationConstants.LITE_VOLUME_DAY, i)
      }
    }
  }

  def insertChangedWorkout = ???

  def insertRestDay = {
    for (i <- 0 until m_weeksTillRace) {
      if (m_plan(i) == null) {
        m_plan(i) = new WeeklyPlan
      }
      if (! m_plan(i).m_weeklySchedule.exists(workout => (workout.m_dayOfWeek == ApplicationConstants.REST_DAY))) {
        m_plan(i) addWorkout new Workout(WorkoutType.REST, 0, 0, ApplicationConstants.REST_DAY, i)
      }
    }
  }

  def insertTaperPeriodWorkout = ???


  def insertPreparatoryRace(raceDate: DateTime, raceLength: Int) = {
    val trainWeekNumber: Int = TimeCalculator(this.raceDate).calculateWorkoutWeek(raceDate)
    if(m_plan(trainWeekNumber) == null) m_plan(trainWeekNumber) = new WeeklyPlan
    m_plan(trainWeekNumber) addWorkout new Workout(WorkoutType.PREPARATORY_RACE, (raceLength * m_targetPace).toInt, raceLength, raceDate.getDayOfWeek, trainWeekNumber)
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
    val parser = DateTimeFormat.forPattern("yyyy-MM-dd")
    val sb :StringBuffer = new StringBuffer
    sb.append("WeeksTillRace: %d\n".format(m_weeksTillRace))
    for (i <- 1 to m_plan.length) {
      val weeklyPlan: WeeklyPlan = m_plan(i - 1)
      sb.append("Week %d (total %d km) schedule:".format(i, weeklyPlan.m_weeklyTotal))
      for (j <- 0 until weeklyPlan.m_weeklySchedule.length) {
        val workout: Workout = weeklyPlan.m_weeklySchedule(j)
        sb.append("\n\t%d)\tDate: %s\tworkoutType: %s,\tDistance: %d,\tDuration: %d,\tDayOfWeek: %d".format( j + 1, workout.m_date.toString(parser), workout.m_workoutType, workout.m_distance, workout.m_duration, workout.m_dayOfWeek))
      }
      sb.append("\n")
    }
    return sb.toString
  }
}

object Creator {
  def apply(raceDate: DateTime, raceDistance: Int, workoutDaysAmountInWeek: Int, targetPace: Float) = new Creator(raceDate, raceDistance, workoutDaysAmountInWeek, targetPace)

  def main(args: Array[String]) {

    val raceDate: DateTime = new DateTime(2015,8,30,0,0,0)
    val targetPace: Float = 5F
    val workoutDaysInWeek: Int = 5
    val raceDistance: Int = 42
    val creator: Creator = Creator(raceDate, raceDistance, workoutDaysInWeek, targetPace)

    creator.createPlan
    println(creator.toString)

    }



}
