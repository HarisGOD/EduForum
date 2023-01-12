package ru.eduforum.challenge;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.eduforum.challenge.repositories.appCustomUser_repo;
import ru.eduforum.challenge.repositories.post_repo;
import ru.eduforum.challenge.units.post;

@Controller
@Transactional
public class PostController {
	post_repo post_repo;
	@Autowired
	void setPost_repo(post_repo pr) {
		this.post_repo=pr;
	}
	appCustomUser_repo acur;
	@Autowired
	void setAppCustomUser_repo(appCustomUser_repo acur) {
		this.acur=acur;
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/newPost")
	public String getPostForm(Model model,Principal principal) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		return "createPostForm";
	}

	@RequestMapping(method=RequestMethod.POST,value="/createPost")
	public String createNewPost(Principal principal,@RequestParam("name") String name,@RequestParam("path") String path,@RequestParam("html") String content)
	{
		if (post_repo.getPostByName(name)!=null){
			return "<h1>some fault</h1>";
			
		}
		else{
		post_repo.save(new post(name,path,content,Date.valueOf(LocalDate.now()), principal.getName()));
		return "redirect:/index";
		}
	}
	@RequestMapping(method=RequestMethod.GET,value="/seepost/{name}")
	public String seepost(@PathVariable("name") String name,Principal principal,Model model) {
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");model.addAttribute("userID",acur.findByLogin(principal.getName()).getID());}
		post post = post_repo.getPostByName(name);
		if(post.getCreator().equals(principal.getName())) {
			System.out.println(post.getCreator()+"=="+principal.getName());
			model.addAttribute("isOwner","true");
		}
		else {
			System.out.println(post.getCreator()+"=//="+principal.getName());
			model.addAttribute("isOwner","false");
		}
		model.addAttribute("postname",name);
		
		
		return "seePost";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/delete-post")
	public String deletepost(Principal principal,@RequestParam(value="postname") String postname) {
		if (post_repo.getPostByName(postname).getCreator().equals(principal.getName())) {
			post_repo.delete(post_repo.getPostByName(postname));
			return "redirect:/user";
			
		}
		else {
			return "redirect:/";
			}
	}
	@RequestMapping(method=RequestMethod.GET,value="/edit-post")
	public String editpost(Model model, Principal principal,@RequestParam(value="postname") String postname) {
		post post = post_repo.getPostByName(postname);
		if(principal== null) 
		{model.addAttribute("isAuthorized","false");}
		else {model.addAttribute("isAuthorized","true");}
		if (post.getCreator().equals(principal.getName())) {
			model.addAttribute("oldname",postname);
			model.addAttribute("postname",post.getName());
			model.addAttribute("content",post.getContent());
			return "editPostForm";
			
		}
		else {
			return "redirect:/";
			}
	}
	@RequestMapping(method=RequestMethod.POST,value="/editpost")
	public String editPost(Principal principal,
			@RequestParam("name") String name,
			@RequestParam("html") String content,
			@RequestParam(value="oldname")String oldname)
	{
		post post = post_repo.getPostByName(oldname);
		
		if (post!=null && post.getCreator().equals( principal.getName())){
			post.setContent(content);
			post.setName(name);
			post_repo.save(post);
			return "redirect:/seepost/"+name;
		}
		else{
		return "redirect:/";
		
		}
	}
	
	
}