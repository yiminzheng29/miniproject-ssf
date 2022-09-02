package vttp.ssf.SpotifyApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.ssf.SpotifyApp.models.Artist2;
import vttp.ssf.SpotifyApp.models.Username;
import vttp.ssf.SpotifyApp.services.ArtistService;
import vttp.ssf.SpotifyApp.services.UserService;

@Controller
@RequestMapping(path="/user")
public class UserController {

	@Autowired
	private UserService userSvc;

	@Autowired
	private ArtistService artistSvc;

	@PostMapping
	public String postUser(@RequestBody MultiValueMap<String, String> form
				, Model model) {

		String userName = form.getFirst("username");
		if ((null == userName) || (userName.trim().length() <= 0)) {
			userName = "anonymous";
		}
		System.out.println(userName);
		String name = form.getFirst("name");
		String token = form.getFirst("token");
		List<Artist2> artList = artistSvc.loadArtist2(name, token);
		userSvc.save(userName, artList);
		
		model.addAttribute("name", name);
		model.addAttribute("username", userName.toUpperCase());
		model.addAttribute("contents", artList);
		return "user";
	}

	@GetMapping(path={"{username}"})
	public String getUser(
		  @PathVariable String username, Model model) {

		List<Artist2> list = userSvc.getUser(username);

		model.addAttribute("username", username);
		model.addAttribute("list", list);
		return "user";
	}

}
