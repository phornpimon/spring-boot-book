package com.pornpimon.stockbackend.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "`user`")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String email;

    private String image;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private Date updateDate;
}
