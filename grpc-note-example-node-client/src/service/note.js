const { v4: uuidv4 } = require('uuid');
let { notes } = require('../data/note')
const { status } = require('@grpc/grpc-js');

const noteServices = {
    getNotes: (_, callback) => {
        let response = {
            noteList: notes
        }
        console.log(response);
        callback(null, response);
    },
    getNoteById: (call, callback) => {
        let noteId = call.request.noteId;
        let note = notes.find(note => note.id === noteId);

        if (note) {
            callback(null, note);
        }
        else {
            callback({
                code: status.NOT_FOUND,
                details: "Note not found"
            })
        }
    },
    createNote: (call, callback) => {
        let note = call.request;
        console.log(note);

        note.id = uuidv4();

        notes.push(note);

        let response = {
            message: "Create note successfully",
            note: note
        }

        callback(null, response);
    },
    deleteNote: (call, callback) => {
        let noteId = call.request.noteId;
        let existingNoteIndex = notes.findIndex(note => note.id === noteId);
        console.log(existingNoteIndex);
        if (existingNoteIndex === -1) {
            callback(null, {
                message: "Note not found"
            });
        }
        else {
            notes = notes.filter(note => note.id !== noteId);
            callback(null, {
                message: "Successfully deleted"
            })
        }
    },
    updateNote: (call, callback) => {
        let updateNote = call.request;
        console.log(updateNote);
        let existingNote = notes.find(note => note.id === updateNote.id);
        if (existingNote) {
            existingNote.title = updateNote.title;
            existingNote.content = updateNote.content;
            let response = {
                message: "Successfully updated",
                note: existingNote
            }
            callback(null, response);
        }
        else {
            callback({
                code: status.NOT_FOUND,
                details: "Note not found"
            });
        }
    },
}

module.exports = noteServices;