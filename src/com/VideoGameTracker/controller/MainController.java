package com.VideoGameTracker.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.VideoGameTracker.entities.Game;
import com.VideoGameTracker.entities.User;
import com.VideoGameTracker.entities.UserGame;
import com.VideoGameTracker.entities.UserGameHours;
import com.VideoGameTracker.service.GameService;
import com.VideoGameTracker.service.UserGameService;
import com.VideoGameTracker.service.UserService;

@Controller
public class MainController {

	private String userName = "";

	@Autowired
	UserService us;

	@Autowired
	GameService gs;

	@Autowired
	UserGameService ugs;

	@RequestMapping("/")
	public String indexHandler() {
		return "login";// view file name
	}

	@RequestMapping("/register")
	public String registerHandler() {
		return "register";// view file name
	}

	@RequestMapping("/login")
	public String loginHandler() {
		return "login";// view file name
	}

	@RequestMapping("/profile")
	public ModelAndView profileHandler() {
		ModelAndView mav = new ModelAndView("profile");
		List<UserGame> userGames = ugs.getAllUserGamesById(userName);
		System.out.println(userGames);
		mav.addObject("profileListBean", userGames);
		return mav;// view file name
	}

	@RequestMapping("/addGame")
	public String addGameHandler() {
		return "addGame";// view file name
	}

	@RequestMapping("/playGame")
	public ModelAndView playGameHandler() {
		ModelAndView mav = new ModelAndView("playGame");
		if (!userName.equals("")) {
			List<Game> games = us.getById(userName).getCurrentGames();
			mav.addObject("playListBean", games);
		}
		return mav;// view file name
	}

	@RequestMapping("/editGame")
	public ModelAndView editGameHandler() {
		ModelAndView mav = new ModelAndView("editGame");
		List<UserGame> games = ugs.getAllUserGamesById(userName);
		mav.addObject("editListBean", games);
		return mav;// view file name
	}

	@RequestMapping("/compare")
	public ModelAndView compareHandler() {
		ModelAndView mav = new ModelAndView("compare");
		List<UserGame> userGames = ugs.getAllUserGamesById(userName);
		mav.addObject("compareListBean", userGames);
		return mav;// view file name
	}
	
	@RequestMapping("/deleteGame")
	public ModelAndView deleteHandler() {
		ModelAndView mav = new ModelAndView("deleteGame");
		List<UserGame> userGames = ugs.getAllUserGamesById(userName);
		mav.addObject("deleteListBean", userGames);
		return mav;
	}

	@RequestMapping("/addNewGame")
	public String newGameHandler(@ModelAttribute UserGame ug) {
		if (!userName.equals("")) {
			ugs.linkUserAndGame(userName, ug.getGameName(), ug.getGameHours(), ug.getTimesCompleted(),
					ug.getCurrentList());
		}
		return "addGame";
	}

	@RequestMapping("/editGameDetails")
	public ModelAndView editGameDetailsHandler(@ModelAttribute UserGame ug) {
		if (!userName.equals("") && !ug.getGameName().equals("")) {
			double gameHours = 0.0;
			int timesCompleted = 0;
			if(ug.getGameHours() == null) {
				gameHours = ugs.getUserGame(userName, ug.getGameName()).getGameHours();
			}else {
				gameHours = ug.getGameHours();
			}
			
			if(ug.getTimesCompleted() == null) {
				timesCompleted = ugs.getUserGame(userName, ug.getGameName()).getTimesCompleted();
			}else {
				timesCompleted = ug.getTimesCompleted();
			}
			ugs.updateUserGame(userName, ug.getGameName(), gameHours, timesCompleted,
					ug.getCurrentList());
		}
		return editGameHandler();
	}

	@RequestMapping("/compareWithUsers")
	public ModelAndView compareWithUsers(@ModelAttribute UserGame ug) {
		ModelAndView mav = compareHandler();
		if (!userName.equals("")) {
			List<UserGame> userGames = ugs.getAllByGameName(ug.getGameName());
			mav.addObject("compareGamesListBean", userGames);
		}
		return mav;
	}

	@RequestMapping("/updateGameHours")
	public ModelAndView updateGameHoursHandler(@ModelAttribute UserGameHours ugh) {
		if (!userName.equals("") && ugh.getGameName() != null) {
			UserGame ug = ugs.getUserGame(userName, ugh.getGameName());
			Double gameHours = ug.getGameHours() + ugh.getGameHours();
			gameHours *= 100;
			gameHours = (double) Math.round(gameHours) / 100;
			ugs.updateGameHours(ug.getUserName(), ug.getGameName(), gameHours);
		}
		return playGameHandler();
	}

	@RequestMapping("/registerNewUser")
	public String registerNewUserHandler(@ModelAttribute User user, HttpServletRequest request) {
		if (us.registerUser(user.getUserName(), user.getPassword())) {
			return "login";
		} else {
			request.getSession().setAttribute("error", "Username is invalid or already in use");
			return "error";
		}
	}

	@RequestMapping("/loginAttempt")
	public ModelAndView loginAttemptHandler(@ModelAttribute User user, HttpServletRequest request) {
		if (us.validateUser(user.getUserName(), user.getPassword())) {
			this.userName = user.getUserName();
			request.getSession().setAttribute("userName", user.getUserName());
			return profileHandler();
		}
		request.getSession().setAttribute("error", "Invalid Login Credentials. Please try again");
		return new ModelAndView("error");
	}
	
	@RequestMapping("/error")
	public String errorHandler() {
		return "error";
	}
	
	@RequestMapping("/removeGame")
	public ModelAndView removeGameHandler(@ModelAttribute UserGame ug) {
		ugs.removeGame(userName, ug.getGameName());
		return deleteHandler();
	}

	
	@RequestMapping("/logout")
	public String logoutHandler(HttpServletRequest request) {
		request.getSession().setAttribute("userName", null);
		this.userName = "";
		return "login";
	}

}
