package com.test.jsonb.models;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;

@Data
@Entity
@Table(name = "student")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Student {

    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String age;
    
    @Type(type = "jsonb")
    @Column(name = "bio", columnDefinition = "jsonb")
    private List<Map<String, Map<String, ?>>> attributes;
}
