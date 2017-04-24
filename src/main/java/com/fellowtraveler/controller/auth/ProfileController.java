package com.fellowtraveler.controller.auth;

import com.fellowtraveler.model.Car;
import com.fellowtraveler.model.User;
import com.fellowtraveler.model.jwtauth.AuthenticatedUser;
import com.fellowtraveler.service.CarService;
import com.fellowtraveler.service.UserService;
import com.fellowtraveler.service.storage.StorageFileNotFoundException;
import com.fellowtraveler.service.storage.StorageProperties;
import com.fellowtraveler.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by igorkasyanenko on 19.03.17.
 */

@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

    private final StorageService storageService;

    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @Autowired
    public ProfileController(StorageService storageService) {
        this.storageService = storageService;
    }


    private ServletContext servletContext;

    @Autowired
    public void setServletContext(ServletContext servletCtx) {
        this.servletContext = servletCtx;
    }

//    @RequestMapping("/")
//    public @ResponseBody
//    String getUsers() {
//        return "{\"users\":[{\"firstname\":\"Richard\", \"lastname\":\"Feynman\"}," +
//                "{\"firstname\":\"Marie\",\"lastname\":\"Curie\"}]}";
//    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }


    @PostMapping("/photo")
    public String handleFileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws ExecutionException, InterruptedException {


        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        String localFilePath = storageService.store(file).get();
        String resultPath = baseUrl + localFilePath;
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        deleteImageFromStorage(user.getImageUrl());

        user.setImageUrl(resultPath);
        userService.updateUser(user);
        response.setStatus(200);
        response.setContentType("application/json");
        return "{ \"url\": \"" + resultPath + "\" }";
    }


    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) throws ExecutionException, InterruptedException {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());

        newCar.setOwner(user);
        user.getCars().add(newCar);
        //user.getCars().add(newCar);
        carService.saveCar(newCar);
        //userService.updateUser(user);
        return new ResponseEntity<Car>(newCar, HttpStatus.OK);
    }

//    @PostMapping("/cars/{id}/photo")
//    public ResponseEntity<Car> updateCarPhoto(@PathVariable(value="id") String id) throws ExecutionException, InterruptedException {
//        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
//
//        Car car = new Car("title", 3, 2, "32", 3, null);
//        //userService.saveUser(user);
//        return new ResponseEntity<Car>(car, HttpStatus.OK);
//    }


    @DeleteMapping("/cars/{id}")
    public String deleteCar(@PathVariable(value = "id") Integer id, HttpServletResponse response) throws ExecutionException, InterruptedException {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        Car car = carService.findById(id);
        if (car != null && car.getOwner().getId().equals(user.getId())) {
            deleteImageFromStorage(car.getImageUrl());
            carService.deleteCarById(car.getId());


            response.setStatus(200);
            return "";
        } else {
            response.setStatus(500);
            response.setContentType("application/json");
            return "{ \"error\": \"Car not found\" }";
        }
    }


    private void deleteImageFromStorage(String path) {
        if (path != null) {

//            Pattern p = Pattern.compile("/static/uploads/images/.+");
//            Matcher m = p.matcher(path);

            //System.out.println(path.substring(0,path.indexOf("/static/uploads/images/")));

            String substr = "/static/uploads/images/";
            String filePath = path.substring(path.indexOf(substr), path.length());
            storageService.deleteOne(filePath);

//            if (m.find()) {
//                storageService.deleteOne(m.group());
//            }
        }
    }

    @PostMapping("/cars/{id}/photo")
    public String updateCarPhoto(@PathVariable(value = "id") Integer id, HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws ExecutionException, InterruptedException {

        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());

        String localFilePath = storageService.store(file).get();

        String resultPath = baseUrl + localFilePath;

        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());
        Car car = carService.findById(id);

        if (car != null && car.getOwner().equals(user)) {
            deleteImageFromStorage(car.getImageUrl());
            car.setImageUrl(resultPath);
            carService.updateCar(car);
            response.setStatus(200);
            response.setContentType("application/json");
            return "{ \"url\": \"" + resultPath + "\" }";

        } else {
            response.setStatus(500);
            response.setContentType("application/json");
            return "{ \"error\": \"Car not found\" }";
        }
    }

    @PatchMapping("")
    public ResponseEntity<User> updateUserInfo(@RequestBody User newUser) throws ExecutionException, InterruptedException {
        User user = userService.findBySSO(SecurityContextHolder.getContext().getAuthentication().getName());

        newUser.setId(user.getId());
        return new ResponseEntity<User>(userService.updateUser(newUser), HttpStatus.OK);

    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
