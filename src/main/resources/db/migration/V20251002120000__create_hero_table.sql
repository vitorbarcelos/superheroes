CREATE TABLE `Herois`
(
    `id`             BIGINT AUTO_INCREMENT,
    `Nome`           VARCHAR(120) NOT NULL,
    `NomeHeroi`      VARCHAR(120) NOT NULL,
    `DataNascimento` DATETIME     NULL,
    `Altura`         DOUBLE       NOT NULL,
    `Peso`           DOUBLE       NOT NULL,
    CONSTRAINT `Herois_PrimaryKey` PRIMARY KEY (`id`),
    CONSTRAINT `Herois_NomeHeroi_Unique` UNIQUE (`NomeHeroi`)
);