// Layout.js
import React from 'react';
import logo from './assets/logo.png'; 
import './Layout.css';

function Layout({ children }) {
  return (
    <div className="layout">
      <header className="header">
        <img src={logo} alt="Logo" className="header-logo" />
        <h1 className="header-text">Cognizant</h1>
      </header>
      <div className="content">{children}</div>
    </div>
  );
}

export default Layout;
