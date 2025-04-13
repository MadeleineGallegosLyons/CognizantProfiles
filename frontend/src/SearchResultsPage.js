import React, { useEffect, useState } from "react";
import { useNavigate, useSearchParams, useLocation } from "react-router-dom";
import "./styles/SearchResultsPage.css";
import LoadingSpinner from "./components/LoadingSpinner";
function SearchResultsPage() {
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams] = useSearchParams();

  const [query, setQuery] = useState(
    location.state?.query || searchParams.get("query") || ""
  );

  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!query) return;

    // Optional: update the URL with ?query=...
    const params = new URLSearchParams({ query });
    window.history.replaceState({}, "", `/results?${params.toString()}`);

    const fetchResults = async () => {
      try {
        setLoading(true);
        const res = await fetch(
          `http://localhost:8080/api/profile-search?query=${encodeURIComponent(
            query
          )}`
        );
        if (!res.ok) throw new Error("Error fetching profiles");

        const data = await res.json();
        setResults(data);
      } catch (err) {
        console.error(err);
        setError("Failed to fetch search results.");
      } finally {
        setLoading(false);
      }
    };

    fetchResults();
  }, [query]);

  const handleSearchAgain = () => {
    navigate("/search");
  };

  const handleViewProfile = (id) => {
    navigate(`/profile/${id}`, { state: { query } });
  };

  if (loading) return <LoadingSpinner />;
  if (error) return <div className="results-container">{error}</div>;

  return (
    <div className="results-container">
      <div className="results-header">
        <button
          className="search-again-button"
          onClick={() => navigate("/search")}
        >
          Search Again <span className="search-icon">🔍</span>
        </button>
      </div>

      <h2 className="searchr-title">Search Profiles</h2>
      <p className="search-query">Results for: "{query}"</p>

      <ul className="results-list">
        {results.map((result) => (
          <li key={result.id} className="result-item">
            <div className="result-info">
              <span className="profile-icon">👤</span>
              <div>
                <div className="result-name">{result.name}</div>
                <div className="result-summary">
                  {result.executiveSummary}
                </div>
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
