package ru.eduforum.challenge;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import ru.eduforum.challenge.repositories.appCustomUser_repo;
import ru.eduforum.challenge.repositories.post_repo;
import ru.eduforum.challenge.repositories.primaryComment_repo;
import ru.eduforum.challenge.repositories.secondaryComment_repo;
import ru.eduforum.challenge.units.appCustomUser;
import ru.eduforum.challenge.units.post;
import ru.eduforum.challenge.units.primaryComment;
import ru.eduforum.challenge.units.secondaryComment;

@RestController
public class DataPortController{
	private primaryComment_repo pc;
	private secondaryComment_repo sc;
	@Autowired
	public void setPrimaryComment_repo(primaryComment_repo pc) {
		this.pc=pc;
	}
	@Autowired
	public void setSecondaryComment_repo(secondaryComment_repo sc) {
		this.sc = sc;
	}
	@Autowired
	public void setPost_Repo(post_repo pr) {
		this.post_repo=pr;
	}
	private static post_repo post_repo;
	
	@Autowired
	public void setAppCustomUser_repo(appCustomUser_repo acur) {
		this.acur = acur;
	}
	private static appCustomUser_repo acur;
	@RequestMapping(method=RequestMethod.GET,value="/getPostDataByName")
	public post getData(@RequestParam(value="name") String Name){
		post post = post_repo.getPostByName(Name);
		if(post!=null) {
			post.setCreatedBy("");
			}
		return  post;
	}
	@RequestMapping(method=RequestMethod.GET,value="/getPostDataByDate")
	public post getPostByDateCreated(@RequestParam(value="year")String year,
			@RequestParam(value="month")String month,
			@RequestParam(value="day")String day,
			@RequestParam(value="id")String index)
	
	{
		int a=Integer.valueOf(year);
		int b=Integer.valueOf(month);
		int c=Integer.valueOf(day);
		int d=Integer.valueOf(index);
		Date date = Date.valueOf(LocalDate.of(a, b, c));
		post post = post_repo.getAllByCreated(date).get(d);
		if(post!=null) {
			post.setCreatedBy("");
			}
		return post;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/getPostById")
	public static post getPostById(@RequestParam(value="ID")String ID) {
		int id = Integer.valueOf(ID);
		
		post post = null;
		
		post = post_repo.getPostByID(id);
		if(post!=null) {
		post.setCreatedBy("");
		}
		
		
		return post;
	}
	@RequestMapping(method=RequestMethod.GET,value="/getPostByUsernameAndIndex")
	public static post getPostByUsernameAndIndex(@RequestParam(value="Login")String Login,
												 @RequestParam(value="Index")String Index) {
		
		try {post post = post_repo.getAllByCreator(Login).get(Integer.valueOf(Index));if(post!=null) {
			post.setCreatedBy("");
			};return post;}	catch (Exception e) {return null;}
		
	}
	@RequestMapping(method=RequestMethod.GET,value="/getPostByUsernameAndIndexByBack")
	public static post getPostByUsernameAndIndexByBack(@RequestParam(value="Login")String Login,
												 @RequestParam(value="Index")String Index) {
		List<post> a = post_repo.getAllByCreator(Login);
		try {post post = a.get(a.size()-Integer.valueOf(Index));if(post!=null) {
			post.setCreatedBy("");
			} return post;}	catch (Exception e) {return null;}
		
	}
	@RequestMapping(method=RequestMethod.GET,value="/getLeftMenuByLogin")
	public static appCustomUser getLeftMenuBy(Principal principal,@RequestParam(value="login")String login) {
		if(principal.getName()==login) {
			appCustomUser acu = acur.findByLogin(login);
			acu.setPassword("");acu.setLogin("");
			
		return  acu;
		}
		else {return null;}
	}

	public boolean isPCommentCreator(int idOfUser,int idOfPC) {
		primaryComment a =pc.findById(Long.valueOf(idOfPC)).get();
		appCustomUser b =acur.findById(Long.valueOf(idOfUser)).get();
		if (a!=null&&b!=null) {
		return a.getLogin().equals(b.getLogin());
		
		}
		return false;
		
	}
	public boolean isSCommentCreator(int idOfUser,int idOfPC) {
		secondaryComment a =sc.findById(Long.valueOf(idOfPC)).get();
		appCustomUser b =acur.findById(Long.valueOf(idOfUser)).get();
		if (a!=null&&b!=null) {
		return a.getLogin().equals(b.getLogin());
		
		}
		return false;
		
	}
	@RequestMapping(method=RequestMethod.GET,value="/getPCByPostnameAndIndex")
	public primaryComment getPCByPostnameAndId(
			@RequestParam(value="postname")String postname,
			@RequestParam(value="index")String index) {
		try {
		int index1 = Integer.valueOf(index);
		post a = post_repo.getPostByName(postname);
		List<primaryComment> b = pc.findAllByPostID(a.getID());
		primaryComment c = 	b.get(b.size()-index1);
		c.setLogin(acur.findByLogin(c.getLogin()).getUsername() );
		return c;
		}
		catch (Exception e) {
		
		return null;}
	}
	@RequestMapping(method=RequestMethod.GET,value="/getSCByPostnameAndIndex")
	public secondaryComment getSCByPostnameAndId(
			@RequestParam(value="postname")String postname,
			@RequestParam(value="index")String index) {
		try {
		int index1 = Integer.valueOf(index);
		post a = post_repo.getPostByName(postname);//TO DO DO BACKYARDS
		secondaryComment b = 	sc.findAllByPrimaryCommentId(a.getID()).get(index1);
		b.setLogin(acur.findByLogin(b.getLogin()).getUsername() );
		return b;
		}
		catch (Exception e) {
		
		return null;}
	}
}