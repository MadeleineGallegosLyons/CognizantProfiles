
import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import jsPDF from "jspdf";
import "./styles/ProfileView.css"; 
import ExportButton from "./ExportButton"; // Import the ExportButton component

// Dummy profile data (later replaced with backend fetch)
const dummyProfiles = [
  {
    id: "j-stevens",
    name: "J. Stevens",
    summary:
      "J. Stevens has Salesforce Development as a technical expertise and 2 projects that include Salesforce.",
    executiveSummary:
      "Experienced Salesforce developer with a passion for CRM integration and automation.",
    functionalExpertise: "CRM, Automation, Project Management",
    technicalExpertise: "Salesforce, Apex, JavaScript",
    industrySectors: "Retail, E-commerce",
    methodologies: "Agile, Kanban",
    experience: "8 years in software development, 5 in Salesforce",
    languagesSpoken: "English, French",
    certifications: "Salesforce Certified Platform Developer I & II",
    mobility: "Open to relocation and frequent travel",
  },
  {
    id: "a-smith",
    name: "A. Smith",
    summary:
      "A. Smith specializes in Azure DevOps and has worked across 5 large enterprise environments.",
    executiveSummary:
      "DevOps engineer focused on continuous integration and cloud infrastructure at scale.",
    functionalExpertise: "CI/CD, Infrastructure as Code, Monitoring",
    technicalExpertise: "Azure DevOps, Terraform, Docker, Kubernetes",
    industrySectors: "Healthcare, Finance, Government",
    methodologies: "Scrum, SAFe",
    experience: "10+ years in enterprise DevOps",
    languagesSpoken: "English, Spanish",
    certifications: "Azure DevOps Engineer Expert",
    mobility: "Remote preferred, open to occasional travel",
  },
];

function Accordion({ title, content }) {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div className="accordion-section">
      <button className="accordion-header" onClick={() => setIsOpen(!isOpen)}>
        {title} <span>{isOpen ? "▲" : "▼"}</span>
      </button>
      {isOpen && <div className="accordion-content">{content}</div>}
    </div>
  );
}

function ProfileView() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [profile, setProfile] = useState(null);

  useEffect(() => {
    const found = dummyProfiles.find((p) => p.id === id);
    setProfile(found || null);
  }, [id]);

  const handleExportPDF = () => {
    if (!profile) return;
  
    const doc = new jsPDF();
    let y = 10;
    const lineHeight = 8;
    const maxWidth = 180;
  
    doc.setFontSize(16);
    doc.text(profile.name, 10, y);
    y += lineHeight;
  
    doc.setFontSize(12);
    doc.setTextColor(100);
    doc.text(profile.summary, 10, y, { maxWidth });
    y += lineHeight * 2;
  
    const addSection = (title, content) => {
      doc.setFont(undefined, "bold");
      doc.text(title, 10, y);
      y += lineHeight;
  
      doc.setFont(undefined, "normal");
      doc.text(content, 10, y, { maxWidth });
      y += lineHeight * 2;
    };
  
    addSection("Executive Summary", profile.executiveSummary);
    addSection("Functional Expertise", profile.functionalExpertise);
    addSection("Technical Expertise", profile.technicalExpertise);
    addSection("Industry Sectors", profile.industrySectors);
    addSection("Methodologies", profile.methodologies);
    addSection("Experience", profile.experience);
    addSection("Languages Spoken", profile.languagesSpoken);
    addSection("Certifications", profile.certifications);
    addSection("Mobility", profile.mobility);
  
    doc.save(`${profile.name.replace(/\s/g, "_")}_Profile.pdf`);
  };

  if (!profile) return <div>Profile not found.</div>;

  return (
    <div className="profile-wrapper">
      <div id="profile-to-export">
        <div className="profile-container">
          <button className="back-button" onClick={() => navigate("/results")}>
            ⬅ Search Results
          </button>
          <ExportButton 
            sharepointRef={{ profile.sharepointRef }} /> {/* Use the ExportButton component */}

          <h2>{profile.name}</h2>
          <p className="profile-summary">{profile.summary}</p>

          <Accordion title="Executive Summary" content={profile.executiveSummary} />
          <Accordion title="Functional Expertise" content={profile.functionalExpertise} />
          <Accordion title="Technical Expertise" content={profile.technicalExpertise} />
          <Accordion title="Industry Sectors" content={profile.industrySectors} />
          <Accordion title="Methodologies" content={profile.methodologies} />
          <Accordion title="Experience" content={profile.experience} />
          <Accordion title="Languages Spoken" content={profile.languagesSpoken} />
          <Accordion title="Certifications" content={profile.certifications} />
          <Accordion title="Mobility" content={profile.mobility} />
        </div>
      </div>

      
    </div>
  );
}

export default ProfileView;
