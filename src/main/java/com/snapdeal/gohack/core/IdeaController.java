package com.snapdeal.gohack.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;



@RestController
public class IdeaController {


	@Autowired
	private IdeaService ideaService;


	@RequestMapping(value="/idea", method=RequestMethod.POST,headers = 
			"content-type=application/x-www-form-urlencoded;charset=UTF-8" ,
			produces={"application/json"},
			consumes={"text/xml","application/json"})
	public ModelAndView submitIdea(@ModelAttribute Idea idea){
		String ideaNumber=ideaService.doSubmit(idea);
		return new ModelAndView("redirect:/ideaDetail?idea="+ideaNumber);
	}

	
	@RequestMapping(value="/ideas" ,method=RequestMethod.GET)
	public @ResponseBody List<Idea> getListofIdeas()
	{
      return ideaService.getListOfIdeas();
	}
	
	@RequestMapping(value="/idea/{ideaNumber}" ,method=RequestMethod.GET)
	public @ResponseBody Idea getIdeaDetail(@PathVariable("ideaNumber") String ideaNumber)
	{
      return ideaService.getIdeaDetail(ideaNumber);
	}
}