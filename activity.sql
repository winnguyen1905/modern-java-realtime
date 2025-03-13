-- Disable checks for schema creation
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Create schema
CREATE SCHEMA IF NOT EXISTS `event_management` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `event_management`;

-- Account table (central user entity)
CREATE TABLE IF NOT EXISTS `event_management`.`account` (
  `id` CHAR(36) NOT NULL,
  `full_name` VARCHAR(100) NOT NULL,
  `password` VARCHAR(255) NOT NULL, -- Hashed passwords
  `email` VARCHAR(100) NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)
) ENGINE = InnoDB;

-- Class table
CREATE TABLE IF NOT EXISTS `event_management`.`class` (
  `id` CHAR(36) NOT NULL,
  `class_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- Student account
CREATE TABLE IF NOT EXISTS `event_management`.`student_account` (
  `id` CHAR(36) NOT NULL,
  `account_id` CHAR(36) NOT NULL,
  `class_id` CHAR(36) NOT NULL,
  `student_code` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_student_account_account_idx` (`account_id` ASC),
  INDEX `fk_student_account_class_idx` (`class_id` ASC),
  CONSTRAINT `fk_student_account_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_student_account_class`
    FOREIGN KEY (`class_id`)
    REFERENCES `event_management`.`class` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Admin account
CREATE TABLE IF NOT EXISTS `event_management`.`admin_account` (
  `id` CHAR(36) NOT NULL,
  `account_id` CHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_admin_account_account_idx` (`account_id` ASC),
  CONSTRAINT `fk_admin_account_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Lecturer account
CREATE TABLE IF NOT EXISTS `event_management`.`lecturer_account` (
  `id` CHAR(36) NOT NULL,
  `account_id` CHAR(36) NOT NULL,
  `inaugural_year` YEAR NOT NULL,
  `degree` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_lecturer_account_account_idx` (`account_id` ASC),
  CONSTRAINT `fk_lecturer_account_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Event category table (new)
CREATE TABLE IF NOT EXISTS `event_management`.`event_category` (
  `id` CHAR(36) NOT NULL,
  `category_name` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `category_name_UNIQUE` (`category_name` ASC)
) ENGINE = InnoDB;

-- Representative organizer
CREATE TABLE IF NOT EXISTS `event_management`.`representative_organizer` (
  `id` CHAR(36) NOT NULL,
  `organization_name` VARCHAR(100) NOT NULL,
  `representative_name` VARCHAR(100) NOT NULL,
  `representative_phone` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- Activity table (core event entity)
CREATE TABLE IF NOT EXISTS `event_management`.`activity` (
  `id` CHAR(36) NOT NULL,
  `attendance_score_unit` VARCHAR(45) NOT NULL,
  `activity_name` VARCHAR(100) NOT NULL,
  `description` TEXT,
  `representative_organizer_id` CHAR(36) NOT NULL,
  `event_category_id` CHAR(36) DEFAULT NULL,
  `start_date` DATETIME NOT NULL,
  `end_date` DATETIME NOT NULL,
  `activity_venue` VARCHAR(100) NOT NULL,
  `capacity` INT UNSIGNED DEFAULT NULL,
  `activity_status` ENUM("ONGOING", "FINISHED", "WAITING_TO_START") NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_activity_representative_organizer_idx` (`representative_organizer_id` ASC),
  INDEX `fk_activity_event_category_idx` (`event_category_id` ASC),
  CONSTRAINT `fk_activity_representative_organizer`
    FOREIGN KEY (`representative_organizer_id`)
    REFERENCES `event_management`.`representative_organizer` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_activity_event_category`
    FOREIGN KEY (`event_category_id`)
    REFERENCES `event_management`.`event_category` (`id`)
    ON DELETE SET NULL
) ENGINE = InnoDB;

-- Recurrence table (new, for repeating events)
CREATE TABLE IF NOT EXISTS `event_management`.`recurrence` (
  `id` CHAR(36) NOT NULL,
  `activity_id` CHAR(36) NOT NULL,
  `recurrence_pattern` ENUM("DAILY", "WEEKLY", "MONTHLY") NOT NULL,
  `interval` INT NOT NULL DEFAULT 1,
  `end_recurrence_date` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_recurrence_activity_idx` (`activity_id` ASC),
  CONSTRAINT `fk_recurrence_activity`
    FOREIGN KEY (`activity_id`)
    REFERENCES `event_management`.`activity` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Notification table
CREATE TABLE IF NOT EXISTS `event_management`.`notification` (
  `id` CHAR(36) NOT NULL,
  `account_id` CHAR(36) NOT NULL,
  `notification_type` ENUM("EVENT", "LEARNING", "SECURITY") NOT NULL,
  `posted_by_account_id` CHAR(36) NOT NULL,
  `message` TEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_notification_account_idx` (`account_id` ASC),
  INDEX `fk_notification_posted_by_idx` (`posted_by_account_id` ASC),
  CONSTRAINT `fk_notification_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_notification_posted_by`
    FOREIGN KEY (`posted_by_account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Participation detail (attendance and role tracking)
CREATE TABLE IF NOT EXISTS `event_management`.`participation_detail` (
  `id` CHAR(36) NOT NULL,
  `account_id` CHAR(36) NOT NULL,
  `activity_id` CHAR(36) NOT NULL,
  `status` ENUM("UNVERIFIED", "VERIFIED") NOT NULL,
  `participation_role` ENUM("PARTICIPANT", "CONTRIBUTOR") NOT NULL,
  `qr_code` VARCHAR(100) DEFAULT NULL,
  `confirmed_at` TIMESTAMP DEFAULT NULL,
  `confirmed_by_account_id` CHAR(36) DEFAULT NULL,
  `registered_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `idx_participation_account_activity` (`account_id`, `activity_id`),
  CONSTRAINT `fk_participation_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_participation_activity`
    FOREIGN KEY (`activity_id`)
    REFERENCES `event_management`.`activity` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_participation_confirmed_by`
    FOREIGN KEY (`confirmed_by_account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE SET NULL
) ENGINE = InnoDB;

-- Feedback table
CREATE TABLE IF NOT EXISTS `event_management`.`feedback` (
  `id` CHAR(36) NOT NULL,
  `participation_detail_id` CHAR(36) NOT NULL,
  `description` TEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_feedback_participation_idx` (`participation_detail_id` ASC),
  CONSTRAINT `fk_feedback_participation`
    FOREIGN KEY (`participation_detail_id`)
    REFERENCES `event_management`.`participation_detail` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Report table
CREATE TABLE IF NOT EXISTS `event_management`.`report` (
  `id` CHAR(36) NOT NULL,
  `activity_id` CHAR(36) NOT NULL,
  `reported_by_account_id` CHAR(36) NOT NULL,
  `description` TEXT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_report_activity_idx` (`activity_id` ASC),
  INDEX `fk_report_reported_by_idx` (`reported_by_account_id` ASC),
  CONSTRAINT `fk_report_activity`
    FOREIGN KEY (`activity_id`)
    REFERENCES `event_management`.`activity` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_report_reported_by`
    FOREIGN KEY (`reported_by_account_id`)
    REFERENCES `event_management`.`account` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Event schedule (detailed timeline within an activity)
CREATE TABLE IF NOT EXISTS `event_management`.`event_schedule` (
  `id` CHAR(36) NOT NULL,
  `activity_id` CHAR(36) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `activity_description` VARCHAR(100) NOT NULL,
  `location` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_schedule_activity_idx` (`activity_id` ASC),
  CONSTRAINT `fk_event_schedule_activity`
    FOREIGN KEY (`activity_id`)
    REFERENCES `event_management`.`activity` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Lecturer manager (class oversight by lecturers)
CREATE TABLE IF NOT EXISTS `event_management`.`lecturer_manager` (
  `id` CHAR(36) NOT NULL,
  `started_date` DATE NOT NULL,
  `class_id` CHAR(36) NOT NULL,
  `lecturer_account_id` CHAR(36) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_lecturer_manager_class_idx` (`class_id` ASC),
  INDEX `fk_lecturer_manager_lecturer_idx` (`lecturer_account_id` ASC),
  CONSTRAINT `fk_lecturer_manager_class`
    FOREIGN KEY (`class_id`)
    REFERENCES `event_management`.`class` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_lecturer_manager_lecturer`
    FOREIGN KEY (`lecturer_account_id`)
    REFERENCES `event_management`.`lecturer_account` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Semester (student performance tracking)
CREATE TABLE IF NOT EXISTS `event_management`.`semester` (
  `id` CHAR(36) NOT NULL,
  `student_account_id` CHAR(36) NOT NULL,
  `attendance_score` DECIMAL(5,2) NOT NULL,
  `gpa` DECIMAL(3,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_semester_student_account_idx` (`student_account_id` ASC),
  CONSTRAINT `fk_semester_student_account`
    FOREIGN KEY (`student_account_id`)
    REFERENCES `event_management`.`student_account` (`id`)
    ON DELETE CASCADE
) ENGINE = InnoDB;

-- Restore original settings
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;