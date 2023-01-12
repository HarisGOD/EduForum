package ru.eduforum.challenge;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import ru.eduforum.challenge.repositories.appCustomUser_repo;
import ru.eduforum.challenge.repositories.indexImage_repo;
import ru.eduforum.challenge.repositories.post_repo;
import ru.eduforum.challenge.repositories.rights_repo;
import ru.eduforum.challenge.units.appCustomUser;
import ru.eduforum.challenge.units.indexImage;
import ru.eduforum.challenge.units.post;
import ru.eduforum.challenge.units.rights;

@Controller
@SpringBootApplication(scanBasePackageClasses= {ru.eduforum.challenge.DataPortController.class,ru.eduforum.challenge.PostController.class,ru.eduforum.challenge.todo.class,ru.eduforum.challenge.commentsController.class})
@EnableConfigurationProperties
@EntityScan(basePackages = {"ru.eduforum.challenge"})
public class EduForumApplication {
	@Autowired
	public void setIndexImage_repo(indexImage_repo img) {
		this.indexImage_repo=img;
	}
	private static indexImage_repo indexImage_repo;
	@Autowired
	public void setAcur(appCustomUser_repo acur) {this.acur=acur;}
	private static appCustomUser_repo acur;
	
	@Autowired
	public void setRights_repo(rights_repo r) {this.r = r;}
	private static rights_repo r;	
	@Autowired
	public void setPost_Repo(post_repo pr) {
		this.post_repo=pr;
	}
	static post_repo post_repo;
	
	
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(EduForumApplication.class, args);
		
		Path path = Paths.get(
	            "C:\\Users\\Ахмедьяновы\\eclipse-workspace\\edu-forum\\src\\main\\resources\\static\\defaultUser.png");
	    byte[] arr;
		try {arr = Files.readAllBytes(path);} catch (IOException e) {arr=null;e.printStackTrace();}
		indexImage_repo.save(new indexImage("default",arr ,false));
		try {
			post_repo.save(new post("1","1/2/3","nothing",Date.valueOf(LocalDate.now()),"admin"));}catch (Exception e) {e.printStackTrace();}
	}
	

	@RequestMapping(value="/posts")
	public String posts(Principal principal,Model model) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		return "posters";
	}
	@RequestMapping(value={"/mainpage","/index","/"})
	public String mainStaticPage(Principal principal,Model model) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		return "mainpage";
	}
	

}
