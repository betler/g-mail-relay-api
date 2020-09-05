CREATE SCHEMA `gmr` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE TABLE `gmr`.`message`
(
   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   `application_id` VARCHAR (30) NULL,
   `message_type` VARCHAR (30) NULL,
   `from_addr` BIGINT UNSIGNED NOT NULL,
   `reply_to_addr` BIGINT UNSIGNED NULL,
   `subject` VARCHAR (255) NULL,
   `text_format` TINYINT NOT NULL,
   `text_encoding` VARCHAR (30) NOT NULL,
   `delivery_type` TINYINT NOT NULL,
   `priority` TINYINT NOT NULL,
   `not_before` DATETIME NULL,
   `status` TINYINT NOT NULL,
   `retries` TINYINT NOT NULL DEFAULT 0,
   `creation_date` DATETIME NOT NULL,
   `last_retry_date` DATETIME NULL,
   PRIMARY KEY (`id`),
   INDEX `message_status_idx`
   (
      `status` ASC
   )
   VISIBLE,
   INDEX `message_not_before_idx`
   (
      `not_before` ASC
   )
   VISIBLE
);
CREATE TABLE `gmr`.`address`
(
   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   `address` VARCHAR (200) NOT NULL,
   PRIMARY KEY (`id`),
   INDEX `address_idx`
   (
      `address` ASC
   )
   VISIBLE
);
ALTER TABLE `gmr`.`message` ADD INDEX `message_from_fk_idx`
(
   `from_addr` ASC
)
VISIBLE;
ALTER TABLE `gmr`.`message` ADD CONSTRAINT `message_from_fk` FOREIGN KEY (`from_addr`) REFERENCES `gmr`.`address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `gmr`.`message` ADD INDEX `message_reply_to_addr_fk_idx`
(
   `reply_to_addr` ASC
)
VISIBLE;
ALTER TABLE `gmr`.`message` ADD CONSTRAINT `message_reply_to_fk` FOREIGN KEY (`reply_to_addr`) REFERENCES `gmr`.`address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
CREATE TABLE `gmr`.`to_addr`
(
   `message_id` BIGINT UNSIGNED NOT NULL,
   `address_id` BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY
   (
      `message_id`,
      `address_id`
   ),
   INDEX `to_addr_message_fk_idx`
   (
      `message_id` ASC
   )
   VISIBLE,
   INDEX `to_addr_address_fk_idx`
   (
      `address_id` ASC
   )
   VISIBLE,
   CONSTRAINT `to_addr_address_fk` FOREIGN KEY (`address_id`) REFERENCES `gmr`.`address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE `gmr`.`cc_addr`
(
   `message_id` BIGINT UNSIGNED NOT NULL,
   `address_id` BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY
   (
      `message_id`,
      `address_id`
   ),
   INDEX `cc_addr_message_idx`
   (
      `message_id` ASC
   )
   VISIBLE
);
CREATE TABLE `gmr`.`bcc_addr`
(
   `message_id` BIGINT UNSIGNED NOT NULL,
   `address_id` BIGINT UNSIGNED NOT NULL,
   PRIMARY KEY
   (
      `message_id`,
      `address_id`
   ),
   INDEX `bcc_addr_message_fk_idx`
   (
      `message_id` ASC
   )
   VISIBLE,
   INDEX `bcc_addr_address_fk_idx`
   (
      `address_id` ASC
   )
   VISIBLE,
   CONSTRAINT `bcc_addr_address_fk` FOREIGN KEY (`address_id`) REFERENCES `gmr`.`address` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE `gmr`.`body`
(
   `message_id` BIGINT UNSIGNED NOT NULL,
   `body` MEDIUMTEXT NULL,
   PRIMARY KEY (`message_id`),
   CONSTRAINT `body_message_fk` FOREIGN KEY (`message_id`) REFERENCES `gmr`.`message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
ALTER TABLE `gmr`.`body` ADD INDEX `body_message_fk_idx`
(
   `message_id` ASC
)
VISIBLE;
CREATE TABLE `gmr`.`attachment`
(
   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   `message_id` BIGINT UNSIGNED NOT NULL,
   `cid` VARCHAR(200) NULL,
   `content_type` VARCHAR(200) NULL,
   `filename` VARCHAR(50) NOT NULL,
   `value` LONGTEXT NOT NULL,
   PRIMARY KEY (`id`),
   INDEX `attachment_message_fk_idx`
   (
      `message_id` ASC
   )
   VISIBLE,
   CONSTRAINT `attachment_message_fk` FOREIGN KEY (`message_id`) REFERENCES `gmr`.`message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
CREATE TABLE `gmr`.`header`
(
   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
   `message_id` BIGINT UNSIGNED NOT NULL,
   `name` VARCHAR (200) NOT NULL,
   `value` VARCHAR (2000) NOT NULL,
   PRIMARY KEY (`id`),
   INDEX `header_message_fk_idx`
   (
      `message_id` ASC
   )
   INVISIBLE,
   CONSTRAINT `header_message_fk` FOREIGN KEY (`message_id`) REFERENCES `gmr`.`message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
);
ALTER TABLE `gmr`.`cc_addr` ADD CONSTRAINT `cc_addr_address_fk` FOREIGN KEY (`address_id`) REFERENCES `gmr`.`address` (`id`),
ADD CONSTRAINT `cc_addr_message_fk` FOREIGN KEY (`message_id`) REFERENCES `gmr`.`message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `gmr`.`bcc_addr` ADD CONSTRAINT `bcc_addr_message_fk` FOREIGN KEY (`message_id`) REFERENCES `gmr`.`message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `gmr`.`to_addr` ADD CONSTRAINT `to_addr_message_fk` FOREIGN KEY (`message_id`) REFERENCES `gmr`.`message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;