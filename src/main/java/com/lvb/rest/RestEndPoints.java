package com.lvb.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lvb.dao.UsersDAO;
import com.lvb.model.Account;
import com.lvb.model.Person;
import com.lvb.model.Users;
import com.lvb.service.AccountService;
import com.lvb.service.DataService;
import com.lvb.service.JedisPublish;
import com.lvb.service.SmsService;

@RestController
@RequestMapping("/secured-rest-api")
public class RestEndPoints {
	@Autowired
	DataService data;
	
	@Autowired
	JedisPublish jedis;
	
	@Autowired
	AccountService acc;
	
	@Autowired
	UsersDAO udao;

	@RequestMapping("/appname")
	public String appname(){
		return "REST SERVICE CREATED USING SPRING BOOT";
	}
	
	@RequestMapping("/people")
	public List<Person> sendPeople(){
		return data.getPeople();
	}
	
	@RequestMapping("/accounts")
	public ResponseEntity<List<Account>> getAccounts(){
		return new ResponseEntity<List<Account>>(acc.getAccount(),HttpStatus.OK);
	}
	
	@RequestMapping("/otp-verify")
	public ResponseEntity<String> otpVerify(@RequestBody Users user){
		try{
			System.out.println(udao.findUsersByUsername(user.getUsername()).getPassword());
			System.out.println(user.getPassword());
			System.out.println(udao.findUsersByUsername(user.getUsername()).getPassword().equals(user.getPassword()));
		if(udao.findUsersByUsername(user.getUsername()).getPassword().equals(user.getPassword()))
			return new ResponseEntity<String>("success",HttpStatus.OK);
		return new ResponseEntity<String>("Fail",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e){
			return new ResponseEntity<String>("Fail",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping("/pass-Update")
	public ResponseEntity<String> passVerify(@RequestBody Users user){
		try{
		    udao.save(user);
			return new ResponseEntity<String>("success",HttpStatus.OK);
		
		}
		catch(Exception e){
			return new ResponseEntity<String>("Fail",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/accounts")
	public ResponseEntity<Account> addAccount(@RequestBody Account account)
	{
		try{
			acc.addAccount(account);
			int otp= (int)(1000+Math.floor(Math.random()*8999));
			SmsService.sendSms(account.getMobile(), String.valueOf(otp));
			Users s=new Users(account.getUsername(),String.valueOf(otp));
			udao.save(s);
			return new ResponseEntity(account,HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/accounts/profile-pic")
	public ResponseEntity<Account> addAccount(@RequestParam("profilepic") String profilepic,@RequestParam("username") String username){
		Account account=acc.getAccount(username);
		System.out.println(profilepic+" "+username);
	
		account.setProfilepic(profilepic);
		try{
			acc.addAccount(account);
			return new ResponseEntity(account,HttpStatus.OK);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping("/accounts/{user}")
	public ResponseEntity<Account> getAccount(@PathVariable("user") String user){
		return new ResponseEntity(acc.getAccount(user),HttpStatus.OK);
		
	}
	
	 @RequestMapping(
			  value = "/images/{filename}",
			  produces = MediaType.IMAGE_JPEG_VALUE
			)
			public @ResponseBody byte[] getImageWithMediaType(@PathVariable String filename) throws IOException {
		     File f=new File("/root/mobileapp/images/"+filename);
			    FileInputStream in = new FileInputStream(f);
			    return IOUtils.toByteArray(in);
			}	
	 
	 @PostMapping("/uploads/pics")
	 public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file)
	 {
		 if(file.isEmpty()){
			 return new ResponseEntity<String>("File not attached",HttpStatus.BAD_REQUEST);
		 }
		
		 else{
			 String allowed[]={"jpg","jpeg","png","gif"};
			 Boolean valid=false;
			 String extension=FilenameUtils.getExtension(file.getOriginalFilename());
			 for(String x:allowed){
				 if(x.contentEquals(extension)){
					 valid=true;
					 break;
				 } 
			 }
			 if(valid){
				 try {
					 byte b[]=file.getBytes();
					 Path p=Paths.get("/root/mobileapp/images/"+file.getOriginalFilename());
					 Files.write(p,b);
				 } 
				 catch (IOException e) {
				// TODO Auto-generated catch block
					 	e.printStackTrace();
					 	return new ResponseEntity<String>("Error in Writing",HttpStatus.INTERNAL_SERVER_ERROR);
				 }
				 jedis.publish(file.getOriginalFilename());
				 return new ResponseEntity<String>("File uploaded successfully",HttpStatus.OK);
			 }
			 
			 return new ResponseEntity<String>("Only png,jpeg,jpg and gif allowed",HttpStatus.BAD_REQUEST); 
		 }
		
	 }
}
