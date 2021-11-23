package com.wnc.example.grpc.note.repository;

import com.wnc.example.grpc.note.data.NoteEntity;
import com.wnc.example.grpc.note.service.NoteData;
import com.wnc.example.grpc.note.service.NoteRequestID;
import com.wnc.example.grpc.note.util.HibernateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class NoteRepository{

    HibernateUtil hibernateUtil;

    public NoteRepository(){
        hibernateUtil = new HibernateUtil();
    }

    public List<NoteEntity> getNotes(){
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<NoteEntity> noteEntityList = session.createQuery("from NoteEntity").list();

        session.getTransaction().commit();
        session.close();
        return noteEntityList;
    }

    public NoteEntity getNoteById(Integer id){
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NoteEntity noteEntity = (NoteEntity) session.createQuery("from NoteEntity n where n.id=:id" ).setParameter("id", id).getSingleResult();

        session.getTransaction().commit();
        session.close();
        return noteEntity;
    }

    public NoteEntity createNote(NoteEntity note){
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Integer id = (Integer) session.save(note);

        session.getTransaction().commit();
        session.close();

        NoteEntity noteEntity = getNoteById(id);
        return noteEntity;
    }

    public boolean deleteNote(NoteRequestID noteRequestID){
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(session.load(NoteEntity.class, (int)noteRequestID.getNoteId()));

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public NoteEntity updateNote(NoteData note){
        SessionFactory sessionFactory = hibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        NoteEntity noteEntity = session.load(NoteEntity.class, (int)note.getId());
        noteEntity.setTitle(note.getTitle());
        noteEntity.setContent(note.getContent());
        session.getTransaction().commit();
        session.close();

        return noteEntity;
    }}
