CREATE TABLE dbo.Profile (
    profile_id INT IDENTITY(1,1) PRIMARY KEY,
    SharePoint_ref NVARCHAR(255),
    creation_date DATETIME DEFAULT GETDATE(),
    consultant_id INT,
    FOREIGN KEY (consultant_id) REFERENCES Consultant(consultant_id) ON DELETE SET NULL
);