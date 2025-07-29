-- Create the table
CREATE TABLE IF NOT EXISTS registration (
  id INT AUTO_INCREMENT PRIMARY KEY,
  registration_no VARCHAR(255),
  name VARCHAR(255)
);

-- Insert sample data
INSERT INTO registration (registration_no, name) VALUES
('REG-001', 'Alice'),
('REG-002', 'Bob'),
('REG-003', 'Charlie');

-- Query to fetch name by registration number
SELECT name FROM registration WHERE registration_no = 'REG-002';
