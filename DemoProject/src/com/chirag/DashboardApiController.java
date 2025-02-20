package com.chirag;

//File: DashboardApiController.java

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/card")
public class DashboardApiController {

 private final UploadService uploadService;

 public DashboardApiController(UploadService uploadService) {
     this.uploadService = uploadService;
 }

 @PostMapping("/from-csv")
 public ResponseEntity<Map<String, Object>> fromCsv(@RequestParam("collection_id") Long collectionId,
                                                    @RequestParam("file") MultipartFile file) {
     try {
         Map<String, Object> result = uploadService.createCsvUpload(collectionId, file);
         return ResponseEntity.status(HttpStatus.OK)
                              .header("metabase-table-id", result.get("tableId").toString())
                              .body(Map.of("id", result.get("modelId")));
     } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                              .body(Map.of("message", "There was an error uploading the file"));
     }
 }
}

//File: UploadService.java

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UploadService {

 public Map<String, Object> createCsvUpload(Long collectionId, MultipartFile file) throws IOException {
     // Here you'd implement logic similar to `upload/create-csv-upload!` in the original Clojure code.
     // For example, save the file to a temporary location, process the CSV, and then return model information.

     // Simulating a response for now
     File tempFile = convertToFile(file);

     Map<String, Object> modelInfo = new HashMap<>();
     modelInfo.put("modelId", 12345); // Simulated model ID
     modelInfo.put("tableId", 67890); // Simulated table ID

     // Clean up the file after processing
     tempFile.delete();

     return modelInfo;
 }

 private File convertToFile(MultipartFile multipartFile) throws IOException {
     // Convert MultipartFile to a regular file
     File convFile = new File(multipartFile.getOriginalFilename());
     multipartFile.transferTo(convFile);
     return convFile;
 }
}

