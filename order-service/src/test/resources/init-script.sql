CREATE TABLE `order_table_seq`
(
    `sequence_name` varchar(255) NOT NULL,
    `next_val`      bigint(20) DEFAULT NULL,
    PRIMARY KEY (`sequence_name`)
);
