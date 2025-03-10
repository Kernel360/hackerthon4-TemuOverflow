//package com.kernel360.ronaldo.TemuOverflow.common.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.MappedSuperclass;
//import lombok.Data;
//import lombok.Getter;
//import lombok.ToString;
//import lombok.experimental.SuperBuilder;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDateTime;
//
//@Getter
//@ToString
//@EntityListeners(AuditingEntityListener.class)
//@MappedSuperclass
//public class CommonField {
//
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @CreatedDate
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime updatedAt;
//}
