// App.js
import React, { useRef } from "react";
import { Routes, Route, useLocation } from "react-router-dom";
import { SwitchTransition, CSSTransition } from "react-transition-group";

import LoginPage from "./LoginPage";
import HomePage from "./HomePage";
import SearchPage from "./SearchPage";
import SearchResultsPage from "./SearchResultsPage";
import ProfileView from "./ProfileView"; 
import ExportProfilePage from "./ExportProfilePage";
import Layout from "./Layout";
import "./App.css"; // Your transitions & layout styles

function App() {
  const location = useLocation();
  const nodeRef = useRef(null);

  return (
    <Layout>
      <SwitchTransition mode="out-in">
        <CSSTransition
          key={location.pathname}
          classNames="fade"
          timeout={150}
          nodeRef={nodeRef}
        >
          <div ref={nodeRef} className="page">
            <Routes location={location}>
              <Route path="/" element={<LoginPage />} />
              <Route path="/home" element={<HomePage />} />
              <Route path="/search" element={<SearchPage />} />
              <Route path="/results" element={<SearchResultsPage />} />
              <Route path="/profile/:id" element={<ProfileView />} /> {/* ✅ NEW */}
              <Route path="/profile/:id/export" element={<ExportProfilePage />} />

            </Routes>
          </div>
        </CSSTransition>
      </SwitchTransition>
    </Layout>
  );
}

export default App;
