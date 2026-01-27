-- Create order_service database if it doesn't exist
CREATE DATABASE IF NOT EXISTS order_service;

-- Use the database
USE order_service;

-- Grant privileges to root user (optional, but ensures proper access)
GRANT ALL PRIVILEGES ON order_service.* TO 'root'@'%';
FLUSH PRIVILEGES;
