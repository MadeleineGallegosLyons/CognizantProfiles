CREATE TABLE dbo.Section_Category (
    section_category_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    char_count INT
);