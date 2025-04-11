import React from "react";
import { useNavigate } from "react-router-dom";
import "./styles/HomePage.css"; 

function HomePage() {
  const navigate = useNavigate();

  const handleBack = () => {
    const confirmLogout = window.confirm("Are you sure you want to go back? This will log you out.");
    if (confirmLogout) {
      navigate("/");
    }
  };

  return (
    <div>
      

      {/* Back Button (Now Below Header) */}
      <div className="back-button-container">
        <button className="back-button" onClick={handleBack}>&larr; Back</button>
      </div>

      {/* Main Content */}
      <div className="home-container">
        <h2 className="home-title">Profile Matcher</h2>
        <div className="home-button-container">
          <button className="home-button">Manage Profiles</button>
          <button
            className="home-button"
            onClick={() => navigate("/search")} 
          >
            Search Profiles
          </button>
          
        </div>
      </div>
    </div>
  );
}

export default HomePage;
