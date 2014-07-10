package model

/**
 * Created by ilan.s on 7/9/2014.
 */
object WorkoutType extends Enumeration{
  type WorkoutType = Value
  val VOLUME_RUN,
  QUALITY_RUN,
  TEMPO_RUN,
  FARTLEK_RUN,
  RECOVERY_RUN,
  LITE_VOLUME_RUN,
  REST,
  CHANGED_RUN = Value
}
