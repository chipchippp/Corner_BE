//package com.example.profileservice.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.hibernate.annotations.*;
//import org.springframework.data.annotation.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
//import org.springframework.data.neo4j.core.schema.GeneratedValue;
//
//import java.io.Serializable;
//import java.util.Date;
//
//@Getter
//@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@MappedSuperclass
//public abstract class AbstractEntity<T extends Serializable> implements Serializable {
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
//    @Column(name = "id")
//    T id;
//
//    @CreatedBy
//    @Column(name = "created_by")
//    T createdBy;
//
//    @LastModifiedBy
//    @Column(name = "updated_by")
//    T updatedBy;
//
//    @Column(name = "created_at")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    Date createdAt;
//
//    @Column(name = "updated_at")
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    Date updatedAt;
//}
//
