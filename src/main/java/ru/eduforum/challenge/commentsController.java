package ru.eduforum.challenge;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.eduforum.challenge.repositories.appCustomUser_repo;
import ru.eduforum.challenge.repositories.post_repo;
import ru.eduforum.challenge.repositories.primaryComment_repo;
import ru.eduforum.challenge.repositories.secondaryComment_repo;
import ru.eduforum.challenge.units.appCustomUser;
import ru.eduforum.challenge.units.post;
import ru.eduforum.challenge.units.primaryComment;
import ru.eduforum.challenge.units.secondaryComment;

@Controller
public class commentsController {
	private primaryComment_repo pc;
	private secondaryComment_repo sc;
	private post_repo pr;
	private appCustomUser_repo acur;
	@Autowired
	public void setPrimaryComment_repo(primaryComment_repo pc) {
		this.pc=pc;
	}
	@Autowired
	public void setSecondaryComment_repo(secondaryComment_repo sc) {
		this.sc = sc;
	}
	@Autowired
	public void setPost_repo(post_repo pr) {
		this.pr = pr;
	}
	@Autowired
	public void setAppCustomUser_repo(appCustomUser_repo acur) {
		this.acur = acur;
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/addPComment")
	public String addPrimaryComment(Principal principal,
									@RequestParam(value="postname")String postname,
									@RequestParam(value="comment")String comment) {
		if (principal!=null&&pr.getPostByName(postname)!=null) {
		savePC(principal.getName(),postname,comment,java.sql.Date.valueOf(LocalDate.now()));
			return "redirect:/seepost/"+postname;
		}
		else {
			return "redirect:/fault";
		}
		
	}
	@RequestMapping(method=RequestMethod.POST,value="/deletePComment")
	public String deletePrimaryComment(Principal principal,
			@RequestParam(value="pcID")String ID,
			@RequestParam(value="postname")String postname) {
		primaryComment a = pc.findById(Long.valueOf(ID)).get();
		if (principal!=null&&a!=null) {
		deletePC(a,principal.getName());
			return "redirect:/seepost/"+postname;
		}
		else {
			return "fault";
		}
		
	}
	@RequestMapping(method=RequestMethod.POST,value="/editPComment")
	public String editPrimaryComment(Principal principal,
			@RequestParam(value="pcID")String ID,
			@RequestParam(value="postname")String postname,
			@RequestParam(value="newComment")String newComment) {
		primaryComment a = pc.findById(Long.valueOf(ID)).get();
		if (principal!=null&&a!=null) {
		editPC(a,newComment,principal.getName());
			return "redirect:/seepost/"+postname;
		}
		else {
			return "fault";
		}
		
	}
	
	private void savePC(String login,String postname,String comment,Date date) {
		pc.save(new primaryComment(login,pr.getPostByName(postname).getID() ,comment,date));
	}
	private void deletePC(primaryComment a,String Login) {
		if (a!=null&&a.getLogin().equals(Login)) {
		pc.delete(a);
		}
	}
	private void editPC(primaryComment a,String newComment,String Login) {
		if (a!=null&&a.getLogin().equals(Login)) {
			a.setComment(newComment);
		pc.save(a);
		}
	}
	
	

	
	
	@RequestMapping(method=RequestMethod.POST,value="/addSComment")
	public String addSecondaryComment(Principal principal, String postID, String comment) {
		post a = pr.findById(Long.valueOf(postID)).get();
		if (principal!=null&& a!=null&&comment!=null&&comment!="" ) {
			sc.save(new secondaryComment(principal.getName(),Integer.valueOf(postID),comment,java.sql.Date.valueOf(LocalDate.now())));
			return "redirect:/"+a.getName();
		}
		else {
			return "fault";
		}
		
	}
	@RequestMapping(method=RequestMethod.POST,value="/deleteSComment")
	public String deleteSecondaryComment(Principal principal, String scID) {
		secondaryComment a = sc.findById(Long.valueOf(scID)).get();
		if(principal.getName()!=null&&a!=null&&a.getLogin().equals(principal.getName()) ) {
			sc.deleteById(Long.valueOf(scID));
		}
		return"";
	}
	@RequestMapping(method=RequestMethod.POST,value="/editSComment")
	public String editSecondaryComment(Principal principal, String scID,String newComment) {
		secondaryComment a = sc.findById(Long.valueOf(scID)).get();
		
		if(principal.getName()!=null&&a!=null&&newComment!=null&&newComment!="") {
			a.setComment(newComment);
			sc.save(a);
			
		}
		return "";
	}

	

	
}