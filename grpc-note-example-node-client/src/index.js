const dotenv = require('dotenv');
const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const router = require('./routes');
dotenv.config();
const app = express();
const port = process.env.EXP_PORT || 8000;

function createExpressServer() {
    app.use(cors());
    app.use(express.urlencoded({ extended: true }));
    app.use(express.json());

    router(app);

    app.listen(port, () => {
        console.log("Server is listening on port:", port);
    })
}

createExpressServer();

