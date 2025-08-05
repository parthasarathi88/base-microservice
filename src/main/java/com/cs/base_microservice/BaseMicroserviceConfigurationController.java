package com.cs.base_microservice;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import com.cs.base_microservice.entity.ServiceInfo;
import com.cs.base_microservice.service.ServiceInfoService;
import java.util.List; 
@RestController  
public class BaseMicroserviceConfigurationController   
{  
    @Autowired
    private ServiceInfoService serviceInfoService;
    
@GetMapping(value = "/hello", produces = MediaType.TEXT_HTML_VALUE)
public String getHtmlResponse() {
    // Save/update service info in database
    ServiceInfo serviceInfo = serviceInfoService.saveOrUpdateServiceInfo();
    
    String hostname = serviceInfo.getHostname();
    String ipAddress = serviceInfo.getIpAddress();
    long activeServices = serviceInfoService.getActiveServicesCount();
    List<ServiceInfo> allServices = serviceInfoService.getAllServiceInfo();
    
    return "<!DOCTYPE html>" +
            "<html lang='en'>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            "<title>Base Microservice - Static Webpage</title>" +
            "<style>" +
            "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f5f5f5; }" +
            ".container { max-width: 800px; margin: 0 auto; background-color: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }" +
            "h1 { color: #333; text-align: center; margin-bottom: 30px; }" +
            "h2 { color: #007bff; border-bottom: 2px solid #007bff; padding-bottom: 10px; }" +
            ".info-section { margin-bottom: 20px; }" +
            ".highlight { background-color: #e7f3ff; padding: 15px; border-left: 4px solid #007bff; margin: 15px 0; }" +
            ".hostname { background-color: #f8f9fa; padding: 10px; border-radius: 5px; font-family: monospace; color: #495057; }" +
            "ul { list-style-type: none; padding: 0; }" +
            "li { padding: 8px 0; border-bottom: 1px solid #eee; }" +
            ".footer { text-align: center; margin-top: 30px; color: #666; font-size: 14px; }" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<div class='container'>" +
            "<h1>🚀 Base Microservice</h1>" +
            "<div class='highlight'>" +
            "<h2>Welcome to the Static Webpage</h2>" +
            "<p>This is a static webpage served by a Spring Boot microservice controller.</p>" +
            "</div>" +
            "<div class='info-section'>" +
            "<h2>Service Information</h2>" +
            "<ul>" +
            "<li><strong>Service Name:</strong> Base Microservice</li>" +
            "<li><strong>Framework:</strong> Spring Boot</li>" +
            "<li><strong>Language:</strong> Java</li>" +
            "<li><strong>Status:</strong> Active and Running</li>" +
            "<li><strong>Hostname:</strong> <span class='hostname'>" + hostname + "</span></li>" +
            "<li><strong>IP Address:</strong> <span class='hostname'>" + ipAddress + "</span></li>" +
            "<li><strong>Active Services Count:</strong> " + activeServices + "</li>" +
            "<li><strong>Database:</strong> H2 In-Memory Database</li>" +
            "</ul>" +
            "</div>" +
            "<div class='info-section'>" +
            "<h2>Database Information</h2>" +
            "<ul>" +
            "<li><strong>Total Service Records:</strong> " + allServices.size() + "</li>" +
            "<li><strong>Database Console:</strong> <a href='/h2-console' target='_blank'>/h2-console</a></li>" +
            "<li><strong>Service ID:</strong> " + serviceInfo.getId() + "</li>" +
            "<li><strong>Last Access:</strong> " + serviceInfo.getLastAccessed() + "</li>" +
            "</ul>" +
            "</div>" +
            "<div class='info-section'>" +
            "<h2>Available Endpoints</h2>" +
            "<ul>" +
            "<li><strong>/hello</strong> - This static webpage</li>" +
            "</ul>" +
            "</div>" +
            "<div class='footer'>" +
            "<p>&copy; 2025 Base Microservice. Built with Spring Boot.</p>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>";
    }
    
    @GetMapping("/service-info")
    public ServiceInfo getServiceInfo() {
        return serviceInfoService.saveOrUpdateServiceInfo();
    }
    
    @GetMapping("/all-services")
    public List<ServiceInfo> getAllServices() {
        return serviceInfoService.getAllServiceInfo();
    }
}  