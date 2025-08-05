package com.cs.base_microservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_info")
public class ServiceInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "service_name", nullable = false)
    private String serviceName;
    
    @Column(name = "hostname")
    private String hostname;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "last_accessed")
    private LocalDateTime lastAccessed;
    
    // Default constructor
    public ServiceInfo() {
        this.createdAt = LocalDateTime.now();
        this.lastAccessed = LocalDateTime.now();
    }
    
    // Constructor with parameters
    public ServiceInfo(String serviceName, String hostname, String ipAddress, String status) {
        this();
        this.serviceName = serviceName;
        this.hostname = hostname;
        this.ipAddress = ipAddress;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getHostname() {
        return hostname;
    }
    
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }
    
    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
    
    @Override
    public String toString() {
        return "ServiceInfo{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", hostname='" + hostname + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", lastAccessed=" + lastAccessed +
                '}';
    }
}
