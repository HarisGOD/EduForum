package ru.eduforum.challenge;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Base64;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/*
  IT IS JUST 
  TO-DO LIST 
  AND 
  TESTING VOID
 */

//сообщения
//динамическое получение сообщений на странице сообщений
//оформление постов
//Для каждого поста сделать:
//галлерею, теги, переосмыслить Path в постах
//В форму создания поста переделать до html документа


@Controller
public class todo{
	@RequestMapping(method=RequestMethod.GET,value="/test")
	public static String test() {
		
		
		return "testpage";
		
	}
	@RequestMapping(method=RequestMethod.POST,value="/richtext")
	public String test(Model model,@RequestParam("html")String html) {
		model.addAttribute("html",html);
		return "see";
	}
	@RequestMapping(method=RequestMethod.GET,value="/imgtest") //AUTORIZED REQ
	public String imgTest(Model model) {
		return "imgtest";
	}
	@RequestMapping(method=RequestMethod.POST,value="add-test-image") 
	public String addTestImage(@RequestParam("img")MultipartFile file,Map<String, Object> model) {
		byte[] b=null;
		try {b = file.getBytes();} /*|||*/catch (IOException e) {e.printStackTrace();}
		String image64 = Base64.getEncoder().encodeToString(b);
		model.put("img", image64);
		return "img-test-see";
	}
	
	//public static void main(String[] arg) {}
}