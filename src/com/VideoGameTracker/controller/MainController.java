package com.VideoGameTracker.controller;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	UserService us;
	
	@Autowired
	GameService gs;
	
	@Autowired
	UserGameService ugs;
	
	@RequestMapping("/")
	public String indexHandler() {
		return "login";//view file name
	}
	
	@RequestMapping("/register")
	public String registerHandler() {
		return "register";//view file name
	}
	
	@RequestMapping("/login")
	public String loginHandler() {
		return "login";//view file name
	}
	
	@RequestMapping("/profile")
	public ModelAndView profileHandler() {
		ModelAndView mav = new ModelAndView("profile");
		List<UserGame> userGames = ugs.getAllUserGamesById("Frenchy");
		System.out.println(userGames);
		mav.addObject("profileListBean", userGames);
		return mav;//view file name
	}
	
	@RequestMapping("/addGame")
	public String addGameHandler() {
		return "addGame";//view file name
	}
	
	@RequestMapping("/playGame")
	public ModelAndView playGameHandler() {
		ModelAndView mav = new ModelAndView("playGame");
		List<Game> games = us.getById("Frenchy").getCurrentGames();
		mav.addObject("playListBean", games);
		System.out.println(games);
		return mav;//view file name
	}
	
	@RequestMapping("/editGame")
	public ModelAndView editGameHandler() {
		ModelAndView mav = new ModelAndView("editGame");
		List<UserGame> games = ugs.getAllUserGamesById("Frenchy");
		mav.addObject("editListBean", games);
		return mav;//view file name
	}
	
	@RequestMapping("/compare")
	public ModelAndView compareHandler() {
		ModelAndView mav = new ModelAndView("compare");
		List<UserGame> userGames = ugs.getAllUserGamesById("Frenchy");
		mav.addObject("compareListBean", userGames);
		return mav;//view file name
	}
	
	@RequestMapping("/addNewGame")
	public String newGameHandler(@ModelAttribute UserGame ug) {
		ugs.linkUserAndGame(ug.getUserName(), ug.getGameName(), ug.getGameHours(), ug.getTimesCompleted(), ug.getCurrentList());
		return "addGame";
	}
	
	@RequestMapping("/editGameDetails")
	public ModelAndView editGameDetailsHandler(@ModelAttribute UserGame ug) {
		ugs.updateUserGame(ug.getUserName(), ug.getGameName(), ug.getGameHours(), ug.getTimesCompleted(), ug.getCurrentList());
		return editGameHandler();
	}
	
	@RequestMapping("/compareWithUsers")
	public ModelAndView compareWithUsers(@ModelAttribute UserGame ug) {
		ModelAndView mav = compareHandler();
		List<UserGame> userGames = ugs.getAllByGameName(ug.getGameName());
		mav.addObject("compareGamesListBean", userGames);
		return mav;
	}
	
	@RequestMapping("/updateGameHours")
	public ModelAndView updateGameHoursHandler(@ModelAttribute UserGameHours ugh) {
		UserGame ug = ugs.getUserGame(ugh.getUserName(), ugh.getGameName());
		ModelAndView mav = playGameHandler();
		Double gameHours = ug.getGameHours() + ugh.getGameHours();
		gameHours *= 100;
		gameHours = (double)Math.round(gameHours)/100;
		ugs.updateGameHours(ug.getUserName(), ug.getGameName(), gameHours);
		return mav;
	}
	
}
