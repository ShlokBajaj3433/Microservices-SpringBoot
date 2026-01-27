-- Migration will run in context of order_service database (configured in application.properties)
-- Create t_orders table with flat structure
CREATE TABLE IF NOT EXISTS t_orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_number VARCHAR(255),
    sku_code VARCHAR(255),
    price DECIMAL(19, 2),
    quantity INT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;
