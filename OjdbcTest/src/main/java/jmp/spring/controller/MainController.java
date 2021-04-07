package jmp.spring.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.nio.sctp.SendFailedNotification;

import jdk.internal.org.jline.utils.Log;
import jmp.spring.vo.sampleVOList;
import jmp.spring.vo.sampleVo;
import lombok.extern.log4j.Log4j;

@Controller
public class MainController {

	@GetMapping("/main")
	public String main(sampleVo vo,sampleVOList list) {
		System.out.println("----main");
		System.out.println("age:"+vo.getAge()+" name:"+vo.getName());
		System.out.println(list.getList());
		return "/main";
	}
	@GetMapping("/login")
	public String login(@RequestParam("id") String id) {
	if("jmp".equals(id))
		return "/login/login";
	else
		return "/login/fail";
	}
	@GetMapping("/forward")
	public String forward(Model model){
		System.out.println("==========forward");
		model.addAttribute("time",new Date());
		return "forward:/res";
	}
	@GetMapping("/redirect")
	public String Redirect(Model model) {
		System.out.println("==========redirect");
		model.addAttribute("time",new Date());
		return "redirect:/res";
	}
	@GetMapping("/res")
	public String res() {
		System.out.println("==========res");
		return "res";
	}
	@GetMapping("/getJson")
	public @ResponseBody sampleVo getJson(sampleVo vo) {
		//json데이터 형식으로 리턴해준다.
		//리턴할때 @ResponseBody
		//잭슨바인드 라이브러리 추가
		//http://localhost:8090/getJson?name=aaaa&age=1&ids=id1
		System.out.println(vo);
		return vo;
	}
	
	//fileupload
	@GetMapping("/fileUpload")
	public String fileUpload() {
		return "fileUpload";
	}
	@PostMapping("/fileUpload")
	public String fileUploadExe(ArrayList<MultipartFile>files) {
		files.forEach(file -> {
			Log.info("------------");
			Log.info("name:"+file.getOriginalFilename());
			Log.info("size:"+file.getSize());
		});
		return "fileUpload";
	}
	
	
	/*
	 * @GetMapping("/responseEntity") public ResponseEntity<String>responseEntity(){
	 * Log.info("/responeseEntity........"); HttpHeaders header = new
	 * org.springframework.http.httpHeaders();
	 * header.add("Content-Type","application/json;charset-UTF-8"); return new
	 * ResponseEntity<>(msg,header,HttpStatus.ok); }
	 */
	
	
	
}

