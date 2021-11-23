const client = require('../client/client');
const { StatusCodes } = require('http-status-codes')
const noteController = {
    getNotes: (req, res) => {
        client.getNotes(null, (err, data) => {
            if (err) {
                console.log(err);
                res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    msg: "Something went wrong from get notes route"
                });
            }
            console.log(data);
            res.status(StatusCodes.OK).send(data);
        })
    },
    getNoteById: (req, res) => {
        let noteId = req.params.noteId;
        client.getNoteById({ noteId }, (err, note) => {
            if (err) {
                res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    msg: "Something went wrong from get note by id route"
                });
            }
            console.log(note);
            res.status(StatusCodes.OK).send(note);
        })
    },
    createNote: (req, res) => {
        let newNote = {
            title: req.body.title,
            content: req.body.content,
        }
        console.log(newNote);
        client.createNote(newNote, (err, response) => {
            if (err) {
                res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    msg: "Something went wrong from create note route"
                });
            }
            res.status(StatusCodes.OK).send(response);
        })
    },
    deleteNote: (req, res) => {
        let noteId = req.params.noteId;
        client.deleteNote({ noteId }, (err, msg) => {
            if (err) {
                res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    msg: "Something went wrong from delete note route"
                });
            }
            res.status(StatusCodes.OK).send(msg);
        })
    },
    updateNote: (req, res) => {
        let note = {
            id: req.params.noteId,
            title: req.body.title,
            content: req.body.content
        }
        client.updateNote(note, (err, note) => {
            if (err) {
                res.status(StatusCodes.INTERNAL_SERVER_ERROR).send({
                    msg: "Something went wrong from update note route"
                });
            }
            res.status(StatusCodes.OK).send(note);
        })
    }
}

module.exports = noteController;