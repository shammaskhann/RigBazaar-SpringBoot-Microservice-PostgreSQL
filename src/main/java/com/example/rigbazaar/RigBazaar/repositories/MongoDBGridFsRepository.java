// package com.example.rigbazaar.RigBazaar.repositories;


// import io.jsonwebtoken.io.IOException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.mongodb.gridfs.GridFsTmplate;
// import org.springframework.stereotype.Repository;
// import org.springframework.web.multipart.MultipartFile;

// @Repository
// public class MongoDBGridFsRepository {

//     @Autowired
//     private GridFsTemplate gridFsTemplate;

//     public String uploadToGridFs(MultipartFile file) throws java.io.IOException {
//         return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType()).toString();
//     }
// }
