package ru.eduforum.challenge;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class appCustomUserController{
	@Autowired
	public void setAcur(appCustomUser_repo acur) {this.acur=acur;}
	private static appCustomUser_repo acur;
	@Autowired
	public void setRights_repo(rights_repo r) {this.r = r;}
	private static rights_repo r;	
	@Autowired
	public void setIndexImage_repo(indexImage_repo i) {
		this.images = i;
	}
	private static indexImage_repo images;
	@Autowired
	public void setPost_repo(post_repo pr) {
		this.pr = pr;
	}
	private static post_repo pr;
	@RequestMapping(method=RequestMethod.GET,value="/register")
	public String registerUserPage(Principal principal,Model model) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		return "registerForm";
	}
	//  /\
	//	|| сделать html страницы
	//	\/
	@RequestMapping(method=RequestMethod.POST,value="/register")
	public String registerUser(@RequestParam(value="login") String login,
							   @RequestParam(value="password") String password,
							   @RequestParam(value="username") String username,
							   Principal principal,Model model
							   ) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		try {createNewUser(login,username,password);
		
		return "redirect:/user";} 
		catch (Exception e) {e.printStackTrace();
		return "registerForm";}
	}
	
	
	//√отово дл€ использовани€ 
	private static void createNewUser(String login, String username, String password) throws Exception {
		
		if ((acur.findByUsername(username)!=null) || username=="default" ) {throw new Exception();}
		else {
		
		String pw_hash = BCrypt.hashpw(password, BCrypt.gensalt("$2a",5));
		appCustomUser a = new appCustomUser(login,username,pw_hash,true, java.sql.Date.valueOf(LocalDate.now()),1 );
		rights b = new rights(username, "ROLE_USER");
		rights c = new rights(username, "USER");
		acur.save(a);
		r.save(b);
		r.save(c);
		}
	}
	@RequestMapping(value="/login")
	public String loginPage(Principal principal,Model model) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		return "login";
	}
	@RequestMapping(value="/user")
	public static String user(Principal principal,Model model) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		byte[] b=null;
		b = images.findIndexImageByID(acur.findByLogin(principal.getName()).getProfileImgId()).getBinImg();
		appCustomUser user = acur.findByLogin(principal.getName());
		String image64 = Base64.getEncoder().encodeToString(b);
		
		
		model.addAttribute("login",user.getLogin());
		model.addAttribute("username",user.getUsername());
		model.addAttribute("created",user.getPublicationDate().toString());
		model.addAttribute("img",image64);
		
		return "user";
	}
	public static String update(byte[] file,
								Principal principal) {
		appCustomUser acu = acur.findByLogin(principal.getName());
		byte[] b = null;
		b = file;
		if(acu.getProfileImgId()==1) 
			{
			images.save(new indexImage(principal.getName(),b,false));
			acu.setProfileImgId(images.findByPinTo(principal.getName()).getID());
			acur.save(acu) ;
			}
		else {
			indexImage c = images.findByPinTo(principal.getName());
			c.setBinImg(b);
			images.save(c);
		}
		
		return "redirect:/user";
	}
	@RequestMapping(method=RequestMethod.POST,value="/updateUsername")  //AUTORIZED REQ
	public String updateUsername(Principal principal,@RequestParam(value="username")String newUsername) {
		appCustomUser user = acur.findByLogin(principal.getName());
		user.setUsername(newUsername);
		acur.save(user);
		return "redirect:/user";
	}
	@RequestMapping(method=RequestMethod.POST,value="/imgcarve") //AUTORIZED REQ
	public String imgcarve(Model model,@RequestParam("img")MultipartFile img,Principal principal) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		byte[] b=null;
		try {b = img.getBytes();} /*|||*/catch (IOException e) {e.printStackTrace();}
		String image64 = Base64.getEncoder().encodeToString(b);
		model.addAttribute("img",image64);
		return "canvas";
	}
	@RequestMapping(method=RequestMethod.POST,value="/cropNsave") //AUTORIZED REQ
	public String cropNsave(Principal principal,Model model,@RequestParam("upload")String img,
			@RequestParam("x1")String x1,
			@RequestParam("x2")String x2,
			@RequestParam("y1")String y1,
			@RequestParam("y2")String y2
			) {
		
		System.out.println(x1+" "+x2+" "+y1+" "+y2);
		 try {byte[] decodedString = Base64.getDecoder().decode(new String(img).getBytes("UTF-8"));
		 InputStream is = new ByteArrayInputStream(decodedString);
		 BufferedImage bi = ImageIO.read(is);
		 float multiplier=0;
		 if (bi.getHeight()>bi.getWidth()) {
			 multiplier=(float)bi.getHeight()/300;}
		 else {
			 multiplier=(float)bi.getWidth()/300;}
		 System.out.println(multiplier+" "+bi.getHeight()+" "+bi.getWidth());
		 int w = (int)Math.round( Math.abs( Math.floor(Float.valueOf(x2)) )*multiplier ) ;
		 int h = (int)Math.round(Math.abs( Math.floor(Float.valueOf(y2)) )*multiplier);
		 int x = (int)Math.round(Integer.valueOf(x1)*multiplier);int y = (int)Math.round(Integer.valueOf(y1)*multiplier);
		 System.out.println(" "+x+" "+y+" "+w+" "+h);
		 BufferedImage bi2 = bi.getSubimage(x,y,w,h);
		 
		 ByteArrayOutputStream os = new ByteArrayOutputStream();
		 ImageIO.write(bi2, "png", os);
		 String result = Base64.getEncoder().encodeToString(os.toByteArray());
		 model.addAttribute("img",result);
		 appCustomUserController.update(os.toByteArray() , principal);
		 return"redirect:/user";
		 }
		 catch (UnsupportedEncodingException e) {byte[] decodedString = null;e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		  
		 
		 
		return "redirect:/";
	}
}