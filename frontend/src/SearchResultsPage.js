
import React from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import "./styles/SearchResultsPage.css";

function SearchResultsPage() {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const query = searchParams.get("query") || "";

  // Dummy data with ID
  const dummyResults = [
  {
    id: "j-stevens",
    name: "J. Stevens (dummy profile)",
    summary:
      "J. Stevens has Salesforce Development as a technical expertise and 2 projects that include Salesforce.",
    executiveSummary: "Experienced Salesforce developer with a passion for CRM integration and automation.",
    functionalExpertise: "CRM, Automation, Project Management",
    technicalExpertise: "Salesforce, Apex, JavaScript",
    industrySectors: "Retail, E-commerce",
    methodologies: "Agile, Kanban",
    experience: "8 years in software development, 5 in Salesforce",
    languagesSpoken: "English, French",
    certifications: "Salesforce Certified Platform Developer I & II",
    mobility: "Open to relocation and frequent travel"
  },
  {
    id: "a-smith",
    name: "A. Smith (dummy profile)",
    summary:
      "A. Smith specializes in Azure DevOps and has worked across 5 large enterprise environments.",
    executiveSummary: "DevOps engineer focused on continuous integration and cloud infrastructure at scale.",
    functionalExpertise: "CI/CD, Infrastructure as Code, Monitoring",
    technicalExpertise: "Azure DevOps, Terraform, Docker, Kubernetes",
    industrySectors: "Healthcare, Finance, Government",
    methodologies: "Scrum, SAFe",
    experience: "10+ years, with extensive enterprise DevOps transformation work",
    languagesSpoken: "English, Spanish",
    certifications: "Microsoft Certified: Azure DevOps Engineer Expert",
    mobility: "Remote preferred, open to occasional travel"
  }
];


  const handleSearchAgain = () => {
    navigate("/search");
  };

  const handleViewProfile = (id) => {
    navigate(`/profile/${id}`);
  };

  return (
    <div className="results-container">
      <div className="results-header">
        <button className="search-again-button" onClick={handleSearchAgain}>
          Search Again <span className="search-icon">🔍</span>
        </button>
      </div>

      <h2 className="searchr-title">Search Profiles</h2>
      <p className="search-query">Results for: "{query}"</p>

      <ul className="results-list">
        {dummyResults.map((result) => (
          <li key={result.id} className="result-item">
            <div className="result-info">
              <span className="profile-icon">👤</span>
              <div>
                <div className="result-name">{result.name}</div>
                <div className="result-summary">{result.summary}</div>
              </div>
            </div>
            <div>
              <button
                className="view-profile-button"
                onClick={() => handleViewProfile(result.id)}
              >
                View Profile <span className="arrow">→</span>
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default SearchResultsPage;
