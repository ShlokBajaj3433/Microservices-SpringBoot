-- Create t_inventory table
CREATE TABLE IF NOT EXISTS t_inventory (
    id BIGINT NOT NULL AUTO_INCREMENT,
    sku_code VARCHAR(255),
    quantity INT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Insert initial inventory data
INSERT INTO t_inventory (quantity, sku_code)
VALUES (100, 'iphone_15'),
       (100, 'pixel_8'),
       (100, 'galaxy_24'),
       (100, 'oneplus_12');
