// package com.example.rigbazaar.RigBazaar.controllers;

// import com.mongodb.client.gridfs.model.GridFSFile;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.mongodb.core.query.Criteria;
// import org.springframework.data.mongodb.core.query.Query;
// import org.springframework.data.mongodb.gridfs.GridFsOperations;
// import org.springframework.data.mongodb.gridfs.GridFsResource;
// import org.springframework.data.mongodb.gridfs.GridFsTemplate;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/api/files")
// public class FileController {

//     @Autowired
//     private GridFsTemplate gridFsTemplate;

//     @Autowired
//     private GridFsOperations operations;

//     @GetMapping("/profile/{id}")
//     public ResponseEntity<?> getFile(@PathVariable String id) {
//         try {
//             GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));

//             if (file == null) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
//             }

//             GridFsResource resource = operations.getResource(file);

//             return ResponseEntity.ok()
//                     .contentType(MediaType.parseMediaType(file.getMetadata().getString("_contentType")))
//                     .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
//                     .body(resource);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving file");
//         }
//     }
// }
