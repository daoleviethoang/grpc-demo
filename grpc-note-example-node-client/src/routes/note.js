const express = require('express');
const noteController = require('../controller/note');
const noteRouter = express.Router();

noteRouter.get('/', (req, res) => {
    res.send({
        msg: "Hello"
    });
})

// Get list of notes
noteRouter.get('/notes', noteController.getNotes);

// Get note by ID
noteRouter.get('/note/:noteId', noteController.getNoteById);

// Create note
noteRouter.post('/note', noteController.createNote);

// Delete note
noteRouter.delete('/note/:noteId', noteController.deleteNote);

// Update note
noteRouter.patch('/note/:noteId', noteController.updateNote);

module.exports = noteRouter;