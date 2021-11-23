package com.wnc.example.grpc.note.service;

import com.wnc.example.grpc.note.data.NoteEntity;
import com.wnc.example.grpc.note.repository.NoteRepository;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.stream.Collectors;

public class NoteServiceGrpcImpl extends NoteServiceGrpc.NoteServiceImplBase {

    NoteRepository noteRepository = new NoteRepository();

    @Override
    public void getNotes(Empty request, StreamObserver<NoteListResponse> responseObserver) {
        List<NoteEntity> noteEntityList = noteRepository.getNotes();
        List<NoteData> noteDataList = noteEntityList.stream().map(NoteEntity::toProto).collect(Collectors.toList());
        NoteListResponse noteListResponse = NoteListResponse.newBuilder().addAllNoteList(noteDataList).build();
        responseObserver.onNext(noteListResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getNoteById(NoteRequestID noteRequestID, StreamObserver<NoteData> responseObserver) {
        NoteEntity noteEntity = noteRepository.getNoteById((int) noteRequestID.getNoteId());
        if(noteEntity == null){
            Metadata metadata = new Metadata();
            responseObserver.onError(
                    Status.UNAVAILABLE.withDescription("Note not found !")
                            .asRuntimeException(metadata));
        }else {
            responseObserver.onNext(NoteData.newBuilder()
                    .setId(noteEntity.getId())
                    .setTitle(noteEntity.getTitle())
                    .setContent(noteEntity.getContent()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void createNote(NoteData request, StreamObserver<NoteResponse> responseObserver) {
        NoteEntity note = new NoteEntity();
        NoteEntity noteEntity = noteRepository.createNote(note.fromProto(request));
        System.out.println(noteEntity.toString());
        if(noteEntity == null){
            Metadata metadata = new Metadata();
            responseObserver.onError(
                    Status.UNAVAILABLE.withDescription("Cannot create new note !")
                            .asRuntimeException(metadata));
        }else {
            responseObserver.onNext(NoteResponse.newBuilder()
                    .setMessage("create new note success !")
                    .setNote(noteEntity.toProto()).build());
            responseObserver.onCompleted();
        }
    }

    @Override
    public void deleteNote(NoteRequestID request, StreamObserver<ResponseMessage> responseObserver) {
        Boolean deleteStatus = noteRepository.deleteNote(request);
        if (deleteStatus){
            responseObserver.onNext(ResponseMessage.newBuilder().setMessage("Delete Note " + request.getNoteId() + " success").build());
        }else{
            responseObserver.onNext(ResponseMessage.newBuilder().setMessage("Delete Note " + request.getNoteId() + " fail").build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void updateNote(NoteData request, StreamObserver<NoteResponse> responseObserver) {
        NoteEntity noteEntity = noteRepository.updateNote(request);
        if(noteEntity == null){
            Metadata metadata = new Metadata();
            responseObserver.onError(
                    Status.UNAVAILABLE.withDescription("Cannot update new note !")
                            .asRuntimeException(metadata));
        }else {
            responseObserver.onNext(NoteResponse.newBuilder()
                    .setMessage("update note success !")
                    .setNote(noteEntity.toProto()).build());
            responseObserver.onCompleted();
        }
    }
}
