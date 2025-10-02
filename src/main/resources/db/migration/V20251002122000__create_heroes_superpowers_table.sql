CREATE TABLE `HeroisSuperpoderes`
(
    `HeroiId`      BIGINT NOT NULL,
    `SuperpoderId` BIGINT NOT NULL,
    CONSTRAINT `HeroisSuperpoderes_PrimaryKey` PRIMARY KEY (`HeroiId`, `SuperpoderId`),
    CONSTRAINT `HeroisSuperpoderes_Heroi_FK` FOREIGN KEY (`HeroiId`) REFERENCES `Herois` (`id`) ON DELETE CASCADE,
    CONSTRAINT `HeroisSuperpoderes_Superpoder_FK` FOREIGN KEY (`SuperpoderId`) REFERENCES `Superpoderes` (`id`) ON DELETE CASCADE
);