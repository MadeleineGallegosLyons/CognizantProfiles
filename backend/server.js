

// CURRENTLY THIS PAGE DOES NOTHING, COULD BE USEFUL FOR BACKEND USE

require('dotenv').config();
const express = require('express');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 5000;

app.use(cors());
app.use(express.json());

// Sample API route
app.get('/api/hello', (req, res) => {
    res.json({ message: "Hello from the backend!" });
});

// Export route stub
app.get('/api/export/:id', (req, res) => {
    const profileId = req.params.id;
    console.log(`Export requested for profile ID: ${profileId}`);

    

    res.status(501).send(`Export for ${profileId} not implemented yet.`);
});

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});
