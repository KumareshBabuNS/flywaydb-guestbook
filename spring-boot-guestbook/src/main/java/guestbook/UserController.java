package guestbook;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping (value = "/guest", method=RequestMethod.POST)
	// Without @ResponseBody the Controller cannot handle a POST request
	// returns an error on Screen: There was an unexpected error (type=Method Not Allowed, status=405).
	public @ResponseBody ModelAndView addComment(@RequestParam("name") String name , @RequestParam("comment") String comment, Model model ){
		User user = new User (name, comment);
		model.addAttribute("result",userRepository.save(user));
		return new ModelAndView("success");
	}
	
	@RequestMapping(value="/plain", method=RequestMethod.GET)
	public ModelAndView plain(Map<String, Object> model) {
		System.out.println("plain called");
		return new ModelAndView("ok");
	}
	
}
