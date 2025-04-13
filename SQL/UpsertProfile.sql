-- =============================
-- Stored Procedure: Update or Insert an instance of Profile to the database
-- =============================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE schema.uspUpsertProfile
(
    @ProfileId INT = NULL,
    @ConsultantId VARCHAR(50) = NULL,
    @ConsultantName VARCHAR(50) = NULL,
    @ProfileRef VARCHAR(50) = NULL,
    @CreationDate DATETIME,
    @ProfileName VARCHAR(50),
    @ProfileJobTitle VARCHAR(50),
    @ProfileExecutiveSummary VARCHAR(500),
    @ProfileEmail VARCHAR(50),
    @ProfileMobility VARCHAR(50),
    @ProfileLanguages VARCHAR (200),
    @ProfileMethodologies VARCHAR (200),
    @ProfileCertifications VARCHAR (200),
    @ProfileIndustrySectors VARCHAR (200),
    @ProfileTechnicalExpertise VARCHAR (200),
    @ProfileFunctionalExpertise VARCHAR (200),
    @ProfileExperience VARCHAR (500),
)
AS
/*
-- =============================================
-- Author: Madeleine Gallegos Lyons
-- Create Date: 02/10/2025
-- Description: Update or Insert an instance of Profile to the database
-- =============================================
*/
BEGIN
    SET NOCOUNT ON;

    var @TechnicalExpertises = STRING_SPLIT(@ProfileTechnicalExpertise, ',');
    var @FunctionalExpertises = STRING_SPLIT(@ProfileFunctionalExpertise, ',');
    var @Languages = STRING_SPLIT(@ProfileLanguages, ',');
    var @Methodologies = STRING_SPLIT(@ProfileMethodologies, ',');
    var @Certifications = STRING_SPLIT(@ProfileCertifications, ',');
    var @IndustrySectors = STRING_SPLIT(@ProfileIndustrySectors, ',');

    
END   
GO
)