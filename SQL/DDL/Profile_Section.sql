CREATE TABLE dbo.Profile_Section (
    section_id INT IDENTITY(1,1) PRIMARY KEY,
    profile_id INT,
    section_category_id INT,
    section_content NVARCHAR(MAX),
    FOREIGN KEY (profile_id) REFERENCES dbo.Profile(profile_id) ON DELETE CASCADE,
    FOREIGN KEY (section_category_id) REFERENCES dbo.Section_Category(section_category_id) ON DELETE CASCADE
);
