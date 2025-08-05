package com.cs.base_microservice.repository;

import com.cs.base_microservice.entity.ServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceInfoRepository extends JpaRepository<ServiceInfo, Long> {
    
    // Find by service name
    Optional<ServiceInfo> findByServiceName(String serviceName);
    
    // Find by hostname
    List<ServiceInfo> findByHostname(String hostname);
    
    // Find by IP address
    Optional<ServiceInfo> findByIpAddress(String ipAddress);
    
    // Find by status
    List<ServiceInfo> findByStatus(String status);
    
    // Custom query to get the latest service info
    @Query("SELECT s FROM ServiceInfo s ORDER BY s.lastAccessed DESC")
    List<ServiceInfo> findAllOrderByLastAccessedDesc();
    
    // Count active services
    @Query("SELECT COUNT(s) FROM ServiceInfo s WHERE s.status = 'Active'")
    long countActiveServices();
}
