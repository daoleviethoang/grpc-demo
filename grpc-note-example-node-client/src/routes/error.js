const express = require('express');
const { StatusCodes } = require('http-status-codes')
const errorRouter = express.Router();

errorRouter.use((req, res) => {
    res.status(StatusCodes.NOT_FOUND).send({
        title: "Not found",
        content: "Data not found"
    });
})

module.exports = errorRouter;