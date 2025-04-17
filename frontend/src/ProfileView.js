import React, { useState, useEffect } from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import jsPDF from "jspdf";
import "./styles/ProfileView.css";
import LoadingSpinner from "./components/LoadingSpinner";
import ExportButton from "./ExportButton";

function Accordion({ title, content, defaultOpen = false }) {
  const [isOpen, setIsOpen] = useState(defaultOpen);

  return (
    <div className={`accordion-section ${isOpen ? "open" : ""}`}>
      <button className="accordion-header" onClick={() => setIsOpen(!isOpen)}>
        {title} <span>{isOpen ? "▲" : "▼"}</span>
      </button>
      <div
        className="accordion-content-wrapper"
        style={{
          maxHeight: isOpen ? "1000px" : "0",
          overflow: "hidden",
          transition: "max-height 0.3s ease",
        }}
      >
        <div className="accordion-content">
          {Array.isArray(content)
            ? content.map((item, index) => <p key={index}>{item}</p>)
            : <p>{content}</p>}
        </div>
      </div>
    </div>
  );
}

function ProfileView() {
  const { id } = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const query = location.state?.query || "";

  const [profile, setProfile] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/profile-search/${id}`)
      .then((res) => {
        if (!res.ok) throw new Error("Network response was not ok");
        return res.json();
      })
      .then((data) => setProfile(data))
      .catch((err) => {
        console.error("Failed to fetch profile:", err);
        setProfile(null);
      });
  }, [id]);

  const handleExportPDF = () => {
    if (!profile) return;

    const doc = new jsPDF();
    let y = 10;
    const lineHeight = 8;
    const maxWidth = 180;

    doc.setFontSize(16);
    doc.text(profile.name || "Profile", 10, y);
    y += lineHeight;

    if (profile.jobTitle) {
      doc.setFontSize(12);
      doc.text(`Job Title: ${profile.jobTitle}`, 10, y);
      y += lineHeight;
    }

    doc.setFontSize(12);
    doc.setTextColor(100);

    const addSection = (title, content) => {
      doc.setFont(undefined, "bold");
      doc.text(title, 10, y);
      y += lineHeight;

      doc.setFont(undefined, "normal");
      content.forEach((line) => {
        doc.text(line, 10, y, { maxWidth });
        y += lineHeight;
      });
      y += lineHeight / 2;
    };

    profile.sections?.forEach((section) =>
      addSection(section.section_name, section.section_content)
    );

    doc.save(`${profile.name?.replace(/\s/g, "_")}_Profile.pdf`);
  };

  if (profile === null) return <LoadingSpinner />;
  if (!profile) return <div>Profile not found.</div>;

  return (
    <div className="profile-wrapper">
      <div id="profile-to-export">
        <div className="profile-container">
          <button
            className="back-button"
            onClick={() => navigate("/results", { state: { query } })}
          >
            ⬅ Search Results
          </button>
          <ExportButton sharepointRef={ profile.sharePointRef } />
          <h2>{profile.name}</h2>
          <p className="profile-summary">{profile.jobTitle}</p>

          {profile.sections?.map((section, index) => (
            <Accordion
              key={index}
              title={section.section_name}
              content={section.section_content}
              defaultOpen={true} 
            />
          ))}
        </div>
      </div>
    </div>
  );
}

export default ProfileView;
