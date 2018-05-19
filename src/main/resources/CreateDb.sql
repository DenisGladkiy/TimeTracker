-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema time_tracking
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema time_tracking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `time_tracking` DEFAULT CHARACTER SET utf8 ;
USE `time_tracking` ;

-- -----------------------------------------------------
-- Table `time_tracking`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_tracking`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(15) NOT NULL,
  `last_name` VARCHAR(15) NOT NULL,
  `email` VARCHAR(30) NOT NULL,
  `password` VARCHAR(60) NULL DEFAULT NULL,
  `role` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email` (`email` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 20
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `time_tracking`.`activities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `time_tracking`.`activities` (
  `activity_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(500) NULL DEFAULT NULL,
  `creation_date` DATE NOT NULL,
  `deadline` DATE NULL DEFAULT NULL,
  `working_time` INT(11) NULL DEFAULT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  `add_request` BIT(1) NULL DEFAULT NULL,
  `remove_request` BIT(1) NULL DEFAULT NULL,
  `completed` BIT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`activity_id`),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `activities_ibfk_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `time_tracking`.`users` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 100
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- -----------------------------------------------------
-- Insert default administrator
-- -----------------------------------------------------

INSERT INTO users(first_name,	last_name, email, password, role)
  VALUES ('Default', 'Admin', 'admin', '$2a$10$ozey/ixNoFtQeehyKCyAwO0WAgOTCuIS2bEisSb0evpPwk0o0x7/C', 'ADMIN');

