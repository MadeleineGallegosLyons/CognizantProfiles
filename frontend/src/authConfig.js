// src/authConfig.js

export const msalConfig = {
    auth: {
      clientId: "65ddbf99-ae7c-40eb-8709-f8fbce0563bc", // ← Azure App Registration's client ID
      authority: "https://login.microsoftonline.com/common", // Use "common" for all Microsoft accounts, or your tenant ID
      redirectUri: "http://localhost:3000", // Should match what's set in Azure
    },
  };
  
  export const loginRequest = {
    scopes: ["User.Read"], // Default scope to read user profile
  };
  