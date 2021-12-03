-- -----------------------------------------------------
-- Setting DB mediscreendb
-- -----------------------------------------------------
DROP DATABASE if exists mediscreendb;
CREATE DATABASE mediscreendb;
USE mediscreendb ;
-- -----------------------------------------------------
-- Table `mediscreendb`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mediscreendb`.`patients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `address` VARCHAR(200) NULL DEFAULT NULL,
  `dob` DATE NOT NULL,
  `family` VARCHAR(40) NOT NULL,
  `given` VARCHAR(40) NOT NULL,
  `phone` VARCHAR(20) NULL DEFAULT NULL,
  `sex` ENUM('F', 'M') NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = MyISAM
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci

-- -----------------------------------------------------
INSERT INTO `mediscreendb`.`patients` (`id`, `address`, `dob`, `family`, `given`, `phone`, `sex`)
VALUES
    ('1', '1 Brookside St', '1966-12-31', 'TestNone', 'Test', '100-222-3333', 'F'),
    ('2', '2 High St', '1945-06-24', 'TestBorderline', 'Test', '200-333-4444', 'F'),
    ('3', '3 Club Road', '2004-06-18', 'TestInDanger', 'Test', '300-444-5555', 'F'),
    ('4', '4 Valley Dr', '2002-06-28', 'TestEarlyOnset', 'Test', '400-555-6666', 'F');



-- INSERT INTO mediscreendb.hibernate_sequence (next_val) VALUES(10);