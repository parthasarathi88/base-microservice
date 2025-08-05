package com.cs.base_microservice.service;

import com.cs.base_microservice.entity.ServiceInfo;
import com.cs.base_microservice.repository.ServiceInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceInfoService {
    
    @Autowired
    private ServiceInfoRepository serviceInfoRepository;
    
    public ServiceInfo saveOrUpdateServiceInfo() {
        String hostname = "Unknown";
        String ipAddress = "Unknown";
        
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            hostname = localHost.getHostName();
            ipAddress = localHost.getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "Unable to determine hostname";
            ipAddress = "Unable to determine IP address";
        }
        
        // Check if service info already exists for this hostname
        Optional<ServiceInfo> existingService = serviceInfoRepository.findByHostname(hostname)
                .stream().findFirst();
        
        ServiceInfo serviceInfo;
        if (existingService.isPresent()) {
            // Update existing record
            serviceInfo = existingService.get();
            serviceInfo.setLastAccessed(LocalDateTime.now());
            serviceInfo.setStatus("Active and Running");
            serviceInfo.setIpAddress(ipAddress);
        } else {
            // Create new record
            serviceInfo = new ServiceInfo("Base Microservice", hostname, ipAddress, "Active and Running");
        }
        
        return serviceInfoRepository.save(serviceInfo);
    }
    
    public List<ServiceInfo> getAllServiceInfo() {
        return serviceInfoRepository.findAllOrderByLastAccessedDesc();
    }
    
    public Optional<ServiceInfo> getServiceInfoById(Long id) {
        return serviceInfoRepository.findById(id);
    }
    
    public Optional<ServiceInfo> getServiceInfoByServiceName(String serviceName) {
        return serviceInfoRepository.findByServiceName(serviceName);
    }
    
    public List<ServiceInfo> getServiceInfoByHostname(String hostname) {
        return serviceInfoRepository.findByHostname(hostname);
    }
    
    public long getActiveServicesCount() {
        return serviceInfoRepository.countActiveServices();
    }
    
    public void deleteServiceInfo(Long id) {
        serviceInfoRepository.deleteById(id);
    }
}
