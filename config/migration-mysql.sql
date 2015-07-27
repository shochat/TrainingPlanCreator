create DATABASE tpc;

use tpc;

CREATE TABLE `workout_type`
(
	`id` int (11) NOT NULL AUTO_INCREMENT,
	`name` varchar(20),
	`day_of_week` int(1),
	PRIMARY KEY (`id`)
);

INSERT INTO workout_type
  (name, day_of_week)
VALUES
  ('LITE_VOLUME', 1),
  ('QUALITY', 2),
  ('RECOVERY', 3),
  ('TEMPO', 4),
  ('CHANGED', 5),
  ('VOLUME', 6),
  ('REST', 7);

CREATE TABLE `race_distance`
(
	`id` int (3) NOT NULL AUTO_INCREMENT,
	`name` varchar(20) NOT NULL,
	`distance` int(3) NOT NULL,
	`minimal_training_week_count` int(3),
	PRIMARY KEY (`id`)
);

INSERT INTO race_distance
(name, distance, minimal_training_week_count)
VALUES
  ('marathon', 42, 15),
  ('half_marathon', 21, 15),
  ('10KM', 10, 10),
  ('5KM', 5, 8);

CREATE TABLE `workout_distance`
(
	`id` int (3) not null AUTO_INCREMENT,
	`workout_type` varchar(20) NOT NULL,
	`distance` int(3) NOT NULL,
	`week_before_race_count` int(3),
	PRIMARY KEY (`id`)
);

INSERT INTO workout_distance
  (workout_type, distance, week_before_race_count, race_distance)
VALUES
  ('VOLUME', 26,1, 42),('VOLUME', 30,2, 42),('VOLUME', 39,3, 42),('VOLUME', 36,4, 42),('VOLUME', 36,5, 42),
  ('VOLUME', 28,6, 42),('VOLUME', 32,7, 42),('VOLUME', 32,8, 42),('VOLUME', 30,9, 42),('VOLUME', 26,10, 42),
  ('VOLUME', 28,11, 42),('VOLUME', 26,12, 42),('VOLUME', 27,13, 42),('VOLUME', 26,14, 42),('VOLUME', 25,15, 42),
  ('VOLUME', 24,16, 42),('VOLUME', 22,17, 42),('VOLUME', 22,18, 42),('VOLUME', 20,19, 42),('VOLUME', 20,20, 42),('VOLUME', 20,21, 42),
  ('QUALITY', 13,1, 42),('QUALITY', 15,2, 42),('QUALITY', 16,3, 42),('QUALITY', 18,4, 42),('QUALITY', 20,5, 42),
  ('QUALITY', 15,6, 42),('QUALITY', 17,7, 42),('QUALITY', 16,8, 42),('QUALITY', 15,9, 42),('QUALITY', 13,10, 42),
  ('QUALITY', 14,11, 42),('QUALITY', 14,12, 42),('QUALITY', 12,13, 42),('QUALITY', 12,14, 42),('QUALITY', 10,15, 42),
  ('QUALITY', 10,16, 42),('QUALITY', 10,17, 42),('QUALITY', 10,18, 42),('QUALITY', 10,19, 42),('QUALITY', 9,20, 42),('QUALITY', 8,21, 42),
  ('RECOVERY', 10,1, 42),('RECOVERY', 10,2, 42),('RECOVERY', 10,3, 42),('RECOVERY', 10,4, 42),('RECOVERY', 10,5, 42),
  ('RECOVERY', 10,6, 42),('RECOVERY', 10,7, 42),('RECOVERY', 10,8, 42),('RECOVERY', 10,9, 42),('RECOVERY', 10,10, 42),
  ('RECOVERY', 10,11, 42),('RECOVERY', 10,12, 42),('RECOVERY', 10,13, 42),('RECOVERY', 10,14, 42),('RECOVERY', 10,15, 42),
  ('RECOVERY', 10,16, 42),('RECOVERY', 10,17, 42),('RECOVERY', 10,18, 42),('RECOVERY', 10,19, 42),('RECOVERY', 10,20, 42),('RECOVERY', 10,21, 42),
  ('TEMPO', 18,1, 42),('TEMPO', 20,2, 42),('TEMPO', 21,3, 42),('TEMPO', 17,4, 42),('TEMPO', 16,5, 42),
  ('TEMPO', 15,6, 42),('TEMPO', 15,7, 42),('TEMPO', 16,8, 42),('TEMPO', 15,9, 42),('TEMPO', 13,10, 42),
  ('TEMPO', 14,11, 42),('TEMPO', 14,12, 42),('TEMPO', 12,13, 42),('TEMPO', 12,14, 42),('TEMPO', 10,15, 42),
  ('TEMPO', 10,16, 42),('TEMPO', 10,17, 42),('TEMPO', 10,18, 42),('TEMPO', 10,19, 42),('TEMPO', 9,20, 42),('TEMPO', 8,21, 42),
  ('LITE_VOLUME', 12,1, 42),('LITE_VOLUME', 12,2, 42),('LITE_VOLUME', 14,3, 42),('LITE_VOLUME', 10,4, 42),('LITE_VOLUME', 12,5, 42),
  ('LITE_VOLUME', 12,6, 42),('LITE_VOLUME', 11,7, 42),('LITE_VOLUME', 10,8, 42),('LITE_VOLUME', 12,9, 42),('LITE_VOLUME', 12,10, 42),
  ('LITE_VOLUME', 10,11, 42),('LITE_VOLUME', 10,12, 42),('LITE_VOLUME', 10,13, 42),('LITE_VOLUME', 10,14, 42),('LITE_VOLUME', 10,15, 42),
  ('LITE_VOLUME', 10,16, 42),('LITE_VOLUME', 10,17, 42),('LITE_VOLUME', 10,18, 42),('LITE_VOLUME', 10,19, 42),('LITE_VOLUME', 9,20, 42),('LITE_VOLUME', 8,21, 42);