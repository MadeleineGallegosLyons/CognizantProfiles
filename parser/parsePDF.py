from datetime import datetime
import os
import re
import pymupdf
import Profile as profile
import Project as project
#//TODO: make and query an SQL database to get the required sections to make it more robust and flexible for future templates
#TODO: store type of section with section i.e. longform bullet
REQUIRED_SECTIONS = [
    "state",
    "name",
    "Email ",
    "Job Title",
    "Executive Summary",
    "Technical Expertise",
    "Functional Expertise",
    "Experience",
    "Mobility",
    "Industry Sectors",
    "Languages Spoken",
    "Certifications",
    "Methodologies"
]

BULLET_SECTIONS = [
    "Functional Expertise",
    "Technical Expertise",
    "Industry Sectors",
    "Languages Spoken",
    "Certifications",
    "Methodologies",
]

LONGFORM_SECTIONS = [
    "Executive Summary",
    "Mobility",
]

REGEX_PATTERNS = {
    "header": r'^[A-Z]\.\s[A-Za-z]+\s[-–—]\s\"(.+?)\"',
    "email": r'[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}',
}

def get_file_type(file_path):
    """Determines if the uploaded file is a PDF or a PowerPoint."""
    if file_path.endswith('.pdf'):
        return 'pdf'
    else:
        return 'unsupported'

def read_pdf_with_metadata(file_path):
    """Reads text and formatting metadata from a PDF file."""
    try:
        doc = pymupdf.open(file_path)
        content = []
        current_section = None
        for page in doc:
            blocks = page.get_text("dict")["blocks"]
            for block in blocks:
                if "lines" in block:
                    for line in block["lines"]:
                        for span in line["spans"]:
                            if span["text"] in REQUIRED_SECTIONS:
                                if("Email" in span["text"]):
                                    current_section = "Email"
                                    content.append({
                                        "section": current_section,
                                        "text": span["text"],
                                    })
                                else:
                                    current_section = span["text"]
                                    break
                            content.append({
                                "section": current_section,
                                "text": span["text"],
                                })
        return content
    except Exception as e:
        print(f"Error reading PDF: {e}")
        return None

def extract_contact_information(content):
    """Extracts contact information using regex."""
    header_pattern = r'[A-Z]\.\s[A-Za-z]+(?:\s[A-Za-z]+)?\s[-–—]\s["“”](.+?)["“”]'
    email_pattern = r'[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}'
    header = None
    email = None
    for item in content:
        if re.match(header_pattern, item["text"]):
            header = item["text"]
            content.remove(item)
        elif re.match(email_pattern, item["text"]):
            email = item["text"]
            content.remove(item)
    header = re.split(r'\s*[-–—]\s*', header) if header else None
    contact_info = {
        "name": header[0] if header else None,
        "email": email if email else None,
        "job_title": header[1] if header else None
    }
    return contact_info

def parse_resume(content):
    """Parses text and metadata into resume sections."""
    sections = {section: None for section in REQUIRED_SECTIONS}
    contact_info = extract_contact_information(content)
    # Group Text by Section
    current_section = None
    for item in content:
        text = item["text"].strip()
        current_section = item["section"].strip() if item["section"] else current_section
        if current_section and text: #If the current section is not None and the text is not empty
            if current_section in sections: #If the current section is in the list of required sections
                if sections[current_section]: 
                    if current_section == "Experience":
                        sections[current_section].append(text)
                    else:
                        sections[current_section] += f" {text}"
                else:
                    sections[current_section] = [text] if current_section == "Experience" else text
    sections["Name"] = contact_info["name"]
    sections["Email"] = contact_info["email"]
    sections["Job Title"] = contact_info["job_title"]

    for section in sections: #change to a switch case
        if sections[section]:
            sections[section] = bullet_section_helper(sections[section]) if section in BULLET_SECTIONS else sections[section]
            sections[section] = longform_section_helper(sections[section]) if section in LONGFORM_SECTIONS else sections[section]
            sections[section] = experience_section_helper(sections[section]) if section == "Experience" else sections[section]
    return sections

def experience_section_helper(section):
    """Helper function to parse experience sections."""
    project_info = []
    project_details = []
    project_contents = None
    for line in section:
        if re.match(r'^[A-Z][a-z]*(?:\s+\w+)*\s[-–—]\s\w+(?:\s\w+)*\s[-–—]\s\w+(?:\s\w+)*', line):
            if project_contents:
                processed_project = bullet_section_helper(project_contents)
                project_details.append(processed_project)
                project_contents = None
            project_info.append(line)
        else:
            if project_contents:
                project_contents += f"{line}"
            else:
                project_contents = line
    else:
        processed_project = bullet_section_helper(project_contents)
        project_details.append(processed_project)
    projects = []
    for info, details in zip(project_info, project_details):
        projects.append(map_to_project(info, details))
    return projects

def longform_section_helper(section):
    """Helper function to parse longform sections."""
    section = re.sub(r"• ", "", section)
    return section

def bullet_section_helper(section):
    """Helper function to parse bullet sections."""
    # Split the text into bullet points
    bullets = section.split("•")
    bullets = [bullet.strip() for bullet in bullets if bullet.strip()]
    return bullets

def map_to_profile(sections):
    parsed_profile = profile.Profile(
        profileId = None,
        profileRef = None,
        creationDate = datetime.now(),
        profileState = sections["state"],
        profileName = sections["Name"],
        consultantName = None, #TODO: get from request context if possible
        role = None, #TODO: get from request context if possible
        profileEmail= sections["Email"],
        profileJobTitle = sections["Job Title"],
        consultantId = None, #TODO: get from request context if possible
        profileExecutiveSummary = sections["Executive Summary"],
        profileTechnicalExpertise = sections["Technical Expertise"],
        profileFunctionalExpertise = sections["Functional Expertise"],
        profileExperience = sections["Experience"],
        profileMobility = sections["Mobility"],
        profileIndustrySectors = sections["Industry Sectors"],
        profileLanguages = sections["Languages Spoken"],
        profileCertifications = sections["Certifications"],
        profileMethodologies = sections["Methodologies"]
    )
    return parsed_profile

def map_to_project(info, details):
    info = re.split(r'\s*[-–—]\s*', info)
    parsed_project = project.Project(
        projectType= info[0],
        projectRole= info[1],
        projectIndustrySector= info[2],
        projectDetails = details
    )
    return parsed_project

def main():
    """Main function to handle the program logic."""
    file_path = input("Enter the path to the resume file: ")

    if not os.path.exists(file_path):
        print("File does not exist.")
        return

    file_type = get_file_type(file_path)
    if file_type == 'unsupported':
        print("Unsupported file type. Please upload a PDF.")
        return

    content = read_pdf_with_metadata(file_path)
    if not content:
        print("Failed to extract content from the file.")
        return

    sections = parse_resume(content)
    profile = map_to_profile(sections)
    print(profile)





if __name__ == "__main__":
    main()

