package com.example.bookstoreapp.controller;

import com.example.bookstoreapp.dto.LoginDTO;
import com.example.bookstoreapp.dto.ResponseDTO;
import com.example.bookstoreapp.dto.UserRegistrationDTO;
import com.example.bookstoreapp.model.EmailData;
import com.example.bookstoreapp.model.UserRegistrationData;
import com.example.bookstoreapp.service.IEmailService;
import com.example.bookstoreapp.service.IUserRegistrationService;
import com.example.bookstoreapp.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRegistrationRestController {
    @Autowired
    private IUserRegistrationService iUserRegistrationService;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private IEmailService iEmailService;


    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createUserRegistrationData(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.createUserRegistrationData(userRegistrationDTO);
        String token = tokenUtil.createToken(userRegistrationData.getUserId());
        EmailData emailData = new EmailData(userRegistrationData.getEmail(), "Successfully Registered",
                "Hi " + userRegistrationData.getFirstName() + " " +userRegistrationData.getLastName() +
                        ", Click on the given below link to verify \n" + iEmailService.getLink(token));
        iEmailService.sendEmail(emailData);
        ResponseDTO responseDTO = new ResponseDTO("Created User Registration Data", userRegistrationData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getUserRegistrationData() {
        List<UserRegistrationData> userRegistrationDataList = iUserRegistrationService.getUserRegistrationData();
        ResponseDTO responseDTO = new ResponseDTO("Getting All the Users", userRegistrationDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{token}")
    public ResponseEntity<ResponseDTO> getUserRegistrationDataById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserRegistrationDataByUserId(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Getting users by Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyemail/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("email") String email) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.getUserByEmailId(email);
        ResponseDTO responseDTO = new ResponseDTO("Getting Users by Email", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable("token") String token) {
        UserRegistrationData userRegistrationData = iUserRegistrationService.verifyUser(token);
        if (userRegistrationData.isVerified()) {
            ResponseDTO responseDTO = new ResponseDTO("User has been verified", userRegistrationData, token);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        }
        else {
            ResponseDTO responseDTO = new ResponseDTO("ERROR : Invalid Token", null, token);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        }
    }

    @PostMapping("/userlogin")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDTO loginDTO) {
        iUserRegistrationService.userLogin(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful", loginDTO);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUserRegistrationData(@PathVariable("token") String token,@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        UserRegistrationData userRegistrationData = iUserRegistrationService.updateUserRegistrationData(tokenId, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Registration Data for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}

