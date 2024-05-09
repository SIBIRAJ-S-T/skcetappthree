package com.example.demo.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.CurrentUser;
import com.example.demo.Entity.LoginHistory;
import com.example.demo.Entity.LogoutHistory;
import com.example.demo.Entity.ShowUser;
import com.example.demo.Entity.UserEntity;
import com.example.demo.Entity.YourApp;
import com.example.demo.Service.UserService;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "https://skcetappstore.vercel.app")
public class UserController {
	@Autowired
    UserService service;
	
    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity user) {
    	user.setRegistrationDate(LocalDate.now());
    	user.setRegistrationTime(LocalTime.now());
        return service.saveUser(user);
    }
    
    @Transactional
    @GetMapping("/get/{username}/{password}")
    public String get_By_Id(@PathVariable String username ,@PathVariable String password ) {
    	return service.get_By_Id(username,password);
    }
    
    @Transactional
    @DeleteMapping("/deletename/{username}/{password}")
	public String deleteNameData(@PathVariable String username, @PathVariable String password){
		return service.deleteByName(username, password);
	}
    
    @Transactional
    @DeleteMapping("/deletenameshowuser/{username}")
	public String deleteNameShowUserData(@PathVariable String username){
		return service.deleteByShowUserName(username);
	}
    
    @Transactional
    @PutMapping("/updatePassword/{username}/{oldPassword}/{newPassword}")
    public String updatePassword(@PathVariable String username, @PathVariable String oldPassword, @PathVariable String newPassword) {
        return service.updatePassword(username, oldPassword, newPassword);
    }
    
    @GetMapping("/checkUsername/{username}")
    public boolean checkUsernameExists(@PathVariable String username) {
        return service.checkUsernameExists(username);
    }
    
    @PostMapping("/loginhistory")
    public LoginHistory loginUserHistory(@RequestBody LoginHistory user) {
    	user.setRegistrationDate(LocalDate.now());
    	user.setRegistrationTime(LocalTime.now());
        return service.saveLoginHistory(user);
    }
    
    @PostMapping("/logouthistory")
    public LogoutHistory logoutUserHistory(@RequestBody LogoutHistory user) {
    	user.setRegistrationDate(LocalDate.now());
    	user.setRegistrationTime(LocalTime.now());
    	return service.saveLogoutHistory(user);
    }
    
    @PostMapping("/currentuserinsert")
    public CurrentUser currentUserInsert(@RequestBody CurrentUser user) {
    	user.setLoginTime(LocalDateTime.now());
        return service.saveCurrentUser(user);
    }
    
    @Transactional
    @DeleteMapping("/logout/{username}")
    public String logout(@PathVariable String username) {
        return service.logout(username);
    }
    
 // Schedule the deletion of inactive users every 15 minutes
    public void deleteInactiveUsers() {
        service.deleteInactiveUsers();
    }
    
    
    //live user count
    @GetMapping("/liveusers/count")
    public Long getUserCount() {
        return service.getUserCount();
    }
    
    
    //live user already logged in
    @GetMapping("/checkLiveUsername/{username}")
    public boolean checkLiveUsernameExists(@PathVariable String username) {
        return service.checkLiveUsernameExists(username);
    }
    
    
    //show users Entity
    @PostMapping("/showusers")
    public ShowUser createShowUser(@RequestBody ShowUser user) {
        return service.saveShowUser(user);
    }
    
    @GetMapping("/getuser/{username}")
    public ShowUser getUserByUsername(@PathVariable String username) {
        return service.getUserByUsername(username);
    }
    
    
    @PostMapping("/yourapp")
    public YourApp yourApp(@RequestBody YourApp yourapp) {
    	yourapp.setRegistrationDate(LocalDate.now());
    	yourapp.setRegistrationTime(LocalTime.now());
        return service.saveApp(yourapp);
    }
    
    @GetMapping("/checkappurl/{appurl}")
    public ResponseEntity<?> checkAppUrlExists(@PathVariable String appurl) {
        boolean exists =service.checkAppUrlExists(appurl);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/api/yourapp")
    public List<YourApp> getAllYourApps() {
        return service.getAllYourApps();
    }
    
}