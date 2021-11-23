const grpc = require("@grpc/grpc-js");
const protoLoader = require("@grpc/proto-loader");
const pathLib = require("path");
const dotenv = require('dotenv')

dotenv.config();

// Client configuration
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


const NoteService = grpc.loadPackageDefinition(packageDefinition).com.wnc.example.grpc.note.service.NoteService;

const client = new NoteService(
    `${"127.0.0.1"}:${"3001"}`,
    grpc.credentials.createInsecure()
);

module.exports = client;