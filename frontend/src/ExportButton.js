import React, { useState, useEffect } from 'react';

export default function ExportButton({ sharepointRef }) {
    const [downloadURL, setDownloadURL] = useState(null);

    useEffect(() => {
        async function fetchDownloadURL() {
            try {
                const url = await getDownloadURL({ sharepointRef });
                setDownloadURL(url);
            } catch (error) {
                console.error("Error fetching download URL:", error);
            }
        }
        fetchDownloadURL();
    }, [sharepointRef]);

    return (
        <div>
            {downloadURL ? (
                <a href={downloadURL} className="download-button">
                    <button>Export</button>
                </a>
            ) : (
                <button disabled>Loading...</button>
            )}
        </div>
    );
}

async function getDownloadURL({ sharepointRef }) {
    const request = new Request("https://capstone-parser-app.azurewebsites.net/api/getURL", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "x-functions-key": "capstone-parser-app-key", // Replace with your actual key
        },
        body: JSON.stringify({ sharepointRef }),
    });
    const response = await fetch(request);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.text();
    return data; // Ensure this matches the field name in your API response

}
