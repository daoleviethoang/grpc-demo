const grpc = require("@grpc/grpc-js");
const protoLoader = require("@grpc/proto-loader");
const pathLib = require("path");
const dotenv = require('dotenv');
const noteServices = require('../service/note')
dotenv.config();

const PROTO_PATH = pathLib.join(__dirname, "../proto/note.proto");
const address = process.env.ADDRESS;
const port = process.env.RPC_PORT;

const options = {
    keepCase: true,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true,
};

const packageDefinition = protoLoader.loadSync(PROTO_PATH, options);
const noteProto = grpc.loadPackageDefinition(packageDefinition);

function createServer() {
    const server = new grpc.Server();
    server.addService(noteProto.Note.NoteService.service, {
        getNotes: noteServices.getNotes,
        getNoteById: noteServices.getNoteById,
        createNote: noteServices.createNote,
        deleteNote: noteServices.deleteNote,
        updateNote: noteServices.updateNote,
    })

    server.bindAsync(
        `${"127.0.0.1"}:${"3002"}`,
        grpc.ServerCredentials.createInsecure(),
        (error, port) => {
            if(error != null){
                console.log("Error");
            }
            console.log(`Server running at http://${address}:${port}`);
            server.start();
        }
    );
}

createServer();