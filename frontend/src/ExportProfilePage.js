





// CURRENTLY NOT IN USE; DISREGARD THIS PAGE////////////////////////////////////////////////////////////





import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import html2pdf from "html2pdf.js";
import "./styles/ExportProfilePage.css";

function ExportProfilePage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const handleExportPDF = () => {
    const profileElement = document.getElementById("profile-to-export");

    if (!profileElement) {
      alert("Profile data not available for PDF export.");
      return;
    }

    const opt = {
      margin: 0.5,
      filename: `${id}_Profile.pdf`,
      image: { type: "jpeg", quality: 0.98 },
      html2canvas: { scale: 2 },
      jsPDF: { unit: "in", format: "letter", orientation: "portrait" },
    };

    html2pdf().set(opt).from(profileElement).save();
  };

  return (
    <div className="export-page-wrapper">
      <div className="export-card">
        <h2>Export Profile</h2>
        <p>Choose how you'd like to export this profile:</p>
  
        <button className="export-btn disabled" onClick={() => alert("PowerPoint export is coming soon!")}>
          Export as PowerPoint (Coming Soon)
        </button>
  
        <button className="export-btn pdf" onClick={handleExportPDF}>
          Export as PDF
        </button>
  
        <button className="export-btn back" onClick={() => navigate(`/profile/${id}`)}>
          ⬅ Back to Profile
        </button>
      </div>
    </div>
  );
}

export default ExportProfilePage;
