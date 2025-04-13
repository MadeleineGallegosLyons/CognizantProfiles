import React from "react";
import "../styles/LoadingSpinner.css";

function LoadingSpinner() {
  return (
    <div className="spinner-overlay">
      <div className="spinner" />
    </div>
  );
}

export default LoadingSpinner;
