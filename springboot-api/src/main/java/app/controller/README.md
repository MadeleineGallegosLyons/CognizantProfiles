# Cognizant Profiles Search API Controllers

This Spring Boot application powers a search API for consultant profiles. It imports profile data from Azure Blob Storage, stores it in an Azure SQL database, and provides search functionality based on section content.

---

## 🔧 Setup Instructions

### 1. Configure application.properties

Open application.properties and enter the creditials 
```bash
cd springboot-api/src/main/resources/application.properties
```
### 2. Build the Project

Use Maven to clean and package 
```bash
cd springboot-api/
mvn clean package 
```
This deletes the `target/` directory and all its contents, then compiles the project and packages it into a JAR file.

### 3. Run the JAR File

Navigate to the target folder and run the JAR:
```bash
cd springboot-api/target
java -jar capstone-profile-0.0.1-SNAPSHOT.jar
```

### 4. Access the API
Once running, the API should now be running on http://localhost:8080.

## Hit Endpoints

### 1. Import Profiles

### 2. Search Profiles
- **GET** `/api/profile-search?query={keywords}`
- **Parameters**:
  - `keywords`: A comma-separated list of keywords to search for in profile content (ex. `azure, data, it analyst`). 
```bash
http://localhost:8080/api/profile-search?query=<keywords>
```
- **Response**: Returns a list of profiles that match one or more of the keywords, ranked by relevance (i.e., number of keyword matches).
- **Example**: 
```bash
[
  {
    "id": 7,
    "name": "J. Stevens",
    "sharePointRef": "sharepointRef",
    "executiveSummary": "J. Stevens has Salesforce Development as a technical expertise..."
  }
]
```

### 3. Profile View
- **GET** `/api/profile-search/{id}`
- **Parameters**:
  - `id`: The ID of the profile you want to view.
```bash
http://localhost:8080/api/profile-search/<id>
```
- **Response**: Returns detailed information about the selected profile, grouped by section.
- **Example**:
```bash
{
    "id": 7,
    "name": "M. Gallegos Lyons",
    "sharePointRef": "sharepointred.com",
    "email": "madeleinel@cognizant.com",
    "jobTitle": "Software Engineer",
    "sections": [
        {
            "section_name": "Executive Summary",
            "section_content": [
                "This is an executive summary. M Gallegos Lyons engineers software, - develops solutions, units the tests, and qualities the assurance."
            ]
        },
        {
            "section_name": "Functional Expertise",
            "section_content": [
                "Teamwork",
                "Crocheting",
                "Minor car repair"
            ]
        },
        {
            "section_name": "Technical Expertise",
            "section_content": [
                "Python",
                "Volkswagen",
                "Java",
                "Apex",
                "Salesforce Lightning"
            ]
        },
        {
            "section_name": "Industry Sectors",
            "section_content": [
                "Healthcare",
                "Food",
                "Finance",
                "Academia"
            ]
        },
        {
            "section_name": "Methodologies",
            "section_content": [
                "Agile",
                "Mise en place",
                "Push-Pull-Legs"
            ]
        },
        {
            "section_name": "Experience",
            "section_content": [
                "Marketing - Copy writer - Technology - wrote copy for social media messages - promoted webinars and whitepapers",
                "Apex Refactoring - Developer - Business - Rewrote and refactored and debugged and recompiled code - Testing, tested, test, unit, user, client, UI, UX, UX/UI",
                "Web app frontend overhaul - Team Lead - Manufacturing - Javascript js the front-end ui to enable a smooth and polished ux - Angular and REACT with bootstrap kendo tailwind - html htmx stateless stateful design graphics"
            ]
        },
        {
            "section_name": "Languages Spoken",
            "section_content": [
                "English",
                "Spanish",
                "French"
            ]
        },
        {
            "section_name": "Certifications",
            "section_content": [
                "National Latin Exam silver medal",
                "Salesforce Specialist",
                "Apex",
                "Leadership trophy"
            ]
        },
        {
            "section_name": "Mobility",
            "section_content": [
                "No - Evaluation Warning : The document was created with Spire.Presentation for Python"
            ]
        }
    ]
}
```
