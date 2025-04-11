
import React from "react";
import { useMsal } from "@azure/msal-react";
import { loginRequest } from "./authConfig";
import { useNavigate } from "react-router-dom";
import "./styles/LoginPage.css";

function LoginPage() {
  const { instance } = useMsal();
  const navigate = useNavigate();

  const handleLogin = () => {
    instance
      .loginPopup(loginRequest)
      .then((res) => {
        console.log("Login success:", res.account);
        navigate("/search"); 
      })
      .catch((err) => {
        console.error("Login failed:", err);
      });
  };

  return (
    <div className="login-container">
      <div className="loginsub-container">
      <h2 className="login-title">Login</h2>
      <button onClick={handleLogin} className="login-button">
        Sign in with Microsoft
      </button>
    </div>
    </div>
  );
}

export default LoginPage;
