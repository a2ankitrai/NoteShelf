-- ----------------------------------------------------------------------------
-- MySQL Workbench Migration
-- Migrated Schemata: Note_shelf_DB
-- Source Schemata: NoteShelf_DB
-- Created: Sun May 27 11:23:23 2018
-- Workbench Version: 6.3.10
-- ----------------------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------------------------------------------
-- Schema Note_shelf_DB
-- ----------------------------------------------------------------------------
DROP SCHEMA IF EXISTS `Note_shelf_DB` ;
CREATE SCHEMA IF NOT EXISTS `Note_shelf_DB` ;

-- ----------------------------------------------------------------------------
-- Table Note_shelf_DB.NS_NOTES_META_DATA
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `Note_shelf_DB`.`NS_NOTES_META_DATA` (
  `NOTE_ID` INT(11) NOT NULL,
  `USER_ID` INT(11) NULL DEFAULT NULL,
  `NOTE_NOSQL_ID` VARCHAR(45) NULL DEFAULT NULL,
  `CREATED_DATE` DATETIME NULL DEFAULT NULL,
  `UPDATED_DATE` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`NOTE_ID`),
  INDEX `NOTE_USER_ID_KEY_idx` (`USER_ID` ASC),
  CONSTRAINT `NOTE_USER_ID_KEY`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `Note_shelf_DB`.`NS_USER` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- ----------------------------------------------------------------------------
-- Table Note_shelf_DB.NS_USER
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `Note_shelf_DB`.`NS_USER` (
  `USER_ID` INT(11) NOT NULL,
  `USER_NAME` VARCHAR(50) NOT NULL,
  `FIRST_NAME` VARCHAR(50) NULL DEFAULT NULL,
  `LAST_NAME` VARCHAR(50) NULL DEFAULT NULL,
  `EMAIL` VARCHAR(50) NULL DEFAULT NULL,
  `CREATED_DATE` DATETIME NULL DEFAULT NULL,
  `UPDATED_DATE` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE INDEX `USER_NAME_UNIQUE` (`USER_NAME` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- ----------------------------------------------------------------------------
-- Table Note_shelf_DB.NS_USER_AUTH_DETAIL
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `Note_shelf_DB`.`NS_USER_AUTH_DETAIL` (
  `USER_AUTH_DETAIL_ID` INT(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` INT(11) NOT NULL,
  `PASSWORD` VARCHAR(50) NULL DEFAULT NULL,
  `AUTH_TYPE` VARCHAR(50) NULL DEFAULT NULL,
  `ACTIVE` VARCHAR(2) NULL DEFAULT NULL,
  `LOCKED` VARCHAR(2) NULL DEFAULT NULL,
  `CREATED_DATE` DATETIME NULL DEFAULT NULL,
  `UPDATED_DATE` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`USER_AUTH_DETAIL_ID`),
  INDEX `USER_ID_idx` (`USER_ID` ASC),
  CONSTRAINT `USER_AUTH_DETAIL_KEY`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `Note_shelf_DB`.`NS_USER` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- ----------------------------------------------------------------------------
-- Table Note_shelf_DB.NS_USER_PROFILE
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `Note_shelf_DB`.`NS_USER_PROFILE` (
  `USER_PROFILE_ID` INT(11) NOT NULL,
  `USER_ID` INT(11) NOT NULL,
  `GENDER` VARCHAR(1) NULL DEFAULT NULL,
  `PROFILE_IMAGE` VARCHAR(300) NULL DEFAULT NULL,
  `WORK` VARCHAR(45) NULL DEFAULT NULL,
  `CONTACT_NUMBER` VARCHAR(20) NULL DEFAULT NULL,
  `BIRTH_DATE` VARCHAR(10) NULL DEFAULT NULL,
  `BIRTH_YEAR` VARCHAR(5) NULL DEFAULT NULL,
  `LANGUAGE` VARCHAR(45) NULL DEFAULT NULL,
  `CREATED_DATE` DATETIME NULL DEFAULT NULL,
  `UPDATED_DATE` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`USER_PROFILE_ID`),
  INDEX `USER_ID_idx` (`USER_ID` ASC),
  CONSTRAINT `USER_PROFILE_ID_KEY`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `Note_shelf_DB`.`NS_USER` (`USER_ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
SET FOREIGN_KEY_CHECKS = 1;
