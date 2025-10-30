package com.alvarez.kd.nisum.adapter.out.persistence;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "phones")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class PhoneEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String number;

    private String cityCode;

    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @CreatedDate
    private LocalDateTime createdAt;


    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
}
