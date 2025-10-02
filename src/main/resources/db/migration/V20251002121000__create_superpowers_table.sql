CREATE TABLE `Superpoderes`
(
    `id`         BIGINT AUTO_INCREMENT,
    `Superpoder` VARCHAR(50)  NOT NULL,
    `Descricao`  VARCHAR(250) NULL,
    CONSTRAINT `Superpoderes_PrimaryKey` PRIMARY KEY (`id`)
);