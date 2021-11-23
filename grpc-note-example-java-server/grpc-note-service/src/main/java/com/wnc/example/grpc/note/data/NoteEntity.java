package com.wnc.example.grpc.note.data;

import com.wnc.example.grpc.note.service.NoteData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Note")
public class NoteEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public NoteData toProto(){
        return NoteData.newBuilder().setId(getId())
                .setTitle(getTitle())
                .setContent(getContent())
                .build();
    }

    public NoteEntity fromProto(NoteData noteRequest){
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setTitle(noteRequest.getTitle());
        noteEntity.setContent(noteRequest.getContent());
        return noteEntity;
    }
}
