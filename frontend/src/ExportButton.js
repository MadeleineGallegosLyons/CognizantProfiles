import React, { useState, useEffect } from 'react';

export default function ExportButton({ sharepointRef }) {
    const [downloadURL, setDownloadURL] = useState(null);
    getDownloadURL(sharepointRef);
    //const response = handleExport(sharepointRef);
    return (
        <div>
            <a href={ downloadURL} className="download-button">
                <button>Export</button>
            </a>
        </div>
    );
}

async function getDownloadURL({ sharepointRef }) {
    const request = new Request("capstone-parser-app.azurewebsites.net/api/getURL", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ sharepointRef }),
    });
    const downloadUrl = await fetch(request)
    setDownloadURL(downloadUrl);
}
