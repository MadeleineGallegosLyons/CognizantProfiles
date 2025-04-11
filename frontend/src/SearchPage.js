
import React, { useState } from "react";
import "./styles/SearchPage.css";
import { useNavigate } from "react-router-dom";
import "./styles/HomePage.css";

function SearchPage() {
  const [searchText, setSearchText] = useState("");
  const navigate = useNavigate();
  const handleBack = () => {navigate("/")};
  const handleSearchClick = () => {
    // Build the query parameter
    const encodedQuery = encodeURIComponent(searchText);
    navigate(`/results?query=${encodedQuery}`);
  };



  return (
    <div>
        <div className="back-button-container">
        <button className="back-button" onClick={handleBack}>&larr; Back</button>
      </div>
    
    <div className="search-container">
      <h2 className="search-title">Search Profiles</h2>

      <div className="search-bar">
        <input
          type="text"
          placeholder="Search Text Here"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          className="search-input"
        />
        <button className="search-button" onClick={handleSearchClick}>Search</button>
        
      </div>

      
    </div>
    </div>
  );
}

export default SearchPage;
