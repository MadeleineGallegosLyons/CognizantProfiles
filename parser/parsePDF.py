from datetime import datetime
import os
import re
import pymupdf
import json
from azure.storage.blob import BlobServiceClient

# Add the connection string to the Azure Blob Storage Account.
# When pushing to GitHub, set this variable to None or remove it.
AZURE_STORAGE_CONNECTION_STRING = "your_connection_string_here"

# Define the sections to extract from the PDF.
REQUIRED_SECTIONS = [
    "Name",
    "Email",
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
    "Technical Expertise",
    "Functional Expertise",
    "Industry Sectors",
    "Languages Spoken",
    "Certifications",
    "Methodologies"
]

LONGFORM_SECTIONS = [
    "Executive Summary",
    "Mobility"
]

def get_file_type(file_path):
    """Determines if the uploaded file is a PDF or another type."""
    if file_path.endswith('.pdf'):
        return 'pdf'
    else:
        return 'unsupported'

def read_pdf_with_metadata(file_path):
    """Reads text and formatting metadata from a PDF file using PyMuPDF."""
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
                            text_val = span["text"].strip()
                            if text_val in REQUIRED_SECTIONS:
                                # Switch to new section.
                                current_section = text_val
                                content.append({
                                    "section": current_section,
                                    "text": text_val
                                })
                            else:
                                content.append({
                                    "section": current_section,
                                    "text": text_val
                                })
        return content
    except Exception as e:
        print(f"Error reading PDF: {e}")
        return None

def parse_profile(content):
    """
    Parses the extracted content into a structure that fits the JSON format:
    {
      "sharePointRef": null,
      "sections": [
          {
             "section_name": "SectionName",
             "section_content": "content" or [ ... ]
          },
          ...
      ]
    }
    """
    # Initialize a map to hold text for each section.
    # For Experience, use a list; for others, use a string.
    sections_map = {section: [] if section == "Experience" else "" for section in REQUIRED_SECTIONS}

    current_section = None
    for item in content:
        text = item["text"].strip()
        if item["section"]:
            current_section = item["section"]
        if current_section and current_section in sections_map:
            if current_section == "Experience":
                sections_map[current_section].append(text)
            else:
                # Append text with a space separator.
                sections_map[current_section] += (" " + text).strip()

    # Process each section using helper functions.
    for section in REQUIRED_SECTIONS:
        if not sections_map[section]:
            continue
        if section in BULLET_SECTIONS:
            sections_map[section] = bullet_section_helper(sections_map[section])
        if section in LONGFORM_SECTIONS:
            sections_map[section] = longform_section_helper(sections_map[section])
        if section == "Experience":
            sections_map[section] = experience_section_helper(sections_map[section])

    # Build a list of sections with keys "section_name" and "section_content".
    section_list = []
    for section in REQUIRED_SECTIONS:
        val = sections_map[section]
        if val:
            section_list.append({
                "section_name": section,
                "section_content": val
            })

    return {
        "sharePointRef": None,
        "sections": section_list
    }

def bullet_section_helper(section_text):
    """
    Splits a string on bullet markers '•' into a list and removes any bullet characters.
    """
    # Remove any bullet characters before splitting.
    if isinstance(section_text, list):
        return [s.replace('\u2022', '').strip() for s in section_text if s.replace('\u2022', '').strip()]
    section_text = section_text.replace('\u2022', '')
    bullets = section_text.split("•")
    return [b.strip() for b in bullets if b.strip()]

def longform_section_helper(section_text):
    """
    Removes bullet markers for longform sections.
    """
    return re.sub(r"•\s*", "", section_text).strip()

def experience_section_helper(lines):
    """
    Processes the Experience section. Filters out empty entries and removes bullet characters.
    """
    if isinstance(lines, list):
        return [line.replace('\u2022', '').strip() for line in lines if line.replace('\u2022', '').strip()]
    return [lines.replace('\u2022', '').strip()]

def upload_json_to_blob(json_data, blob_name):
    """
    Uploads the given JSON data as a blob to Azure Blob Storage using the local connection string.
    """
    connection_string = AZURE_STORAGE_CONNECTION_STRING
    if not connection_string:
        raise ValueError("Azure Storage connection string is not set. Please set the AZURE_STORAGE_CONNECTION_STRING variable.")

    blob_service_client = BlobServiceClient.from_connection_string(connection_string)
    container_name = "profiles"
    container_client = blob_service_client.get_container_client(container_name)

    try:
        container_client.create_container()
        print(f"Container '{container_name}' created.")
    except Exception as e:
        print(f"Container '{container_name}' may already exist or could not be created: {e}")

    blob_client = container_client.get_blob_client(blob_name)
    blob_client.upload_blob(json_data, overwrite=True)
    print(f"Successfully uploaded blob '{blob_name}'.")

def main():
    file_path = input("Enter the path to the profile file: ")
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

    profile_data = parse_profile(content)
    profile_json = json.dumps(profile_data, indent=2)
    print("Parsed Profile JSON:")
    print(profile_json)

    # Determine blob name based on the "Name" section if available.
    name_blob = None
    for section in profile_data.get("sections", []):
        if section.get("section_name") == "Name":
            name_blob = section.get("section_content")
            break

    if name_blob and isinstance(name_blob, str) and name_blob.strip():
        blob_name = f"{name_blob.strip()}.json"
    else:
        timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
        blob_name = f"profile_{timestamp}.json"

    try:
        upload_json_to_blob(profile_json, blob_name)
    except Exception as e:
        print(f"Failed to upload JSON to Azure Blob Storage: {e}")

if __name__ == "__main__":
    main()
