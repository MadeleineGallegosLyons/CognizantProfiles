INSERT INTO dbo.Profile_Section (profile_id, section_category_id, section_content)
VALUES
    -- Job Title, State, Email
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Job Title'), 'Software Test Engineer'),
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'State'), 'MT (Mountain Time)'),
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Email'), 'cass.orr@umontana.edu'),

    -- Executive Summary
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Executive Summary'),
     'I work as part of a multi-disciplinary team to verify and validate embedded software
     systems, including endoscopes, cold light sources, and documentary devices.
     Developed both manual test procedures and automated test cases for various testing
     types, including end-to-end, integration, and feature testing, to support the validation
     of existing devices as new features were introduced. Responsibilities included
     assembling test hardware and fixtures, analyzing test results, defining requirements
     and acceptance criteria from user stories, and continuously improving the test and
     verification process.'),

    -- Experience
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Experience'),
     'Software Quality Assurance – Software Test Engineer – Medical Device Industry

     • Develop and execute manual test procedures for software and hardware integration
     • Created automated test cases for end-to-end, integration, and feature testing
     • Analyze test results to assess software performances and compliance
     • Collaborate with a multi-disciplinary team to ensure software quality

     Train Control System – SQA Engineer – Railway Transportation

     • Test case automation development for train control software
     • Perform Hardware-in-the-Loop (HIL) testing to simulate train movements and infrastructure signals
     • Ensure software adheres to railway safety regulations and undergoes necessary validation checks'),

    -- Certifications
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Certifications'),
     'Certified Software Quality Engineer (CSQE), Certified Software Tester (CSTE).'),

    -- Technical Expertise
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Technical Expertise'),
     'Jira, Figma, PTC, Postman, Java, Python, C#.'),

    -- Functional Expertise
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Functional Expertise'),
     'Automated Testing, Manual Testing, Test Strategy, Requirements.'),

    -- Industry Sectors
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Industry Sectors'),
     'Medical Technology.'),

    -- Methodologies
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Methodologies'),
     'Agile, Waterfall.'),

    -- Languages Spoken
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Languages Spoken'),
     'English.'),

    -- Mobility
    (1, (SELECT section_category_id FROM dbo.Section_Category WHERE name = 'Mobility'),
     '25% availability to travel.');
