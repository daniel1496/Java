package spring.homework.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public String showGamesPage() {
        return "games";
    }

    @PostMapping("search")
    public String searchForTeam(@RequestParam String team, Model model) {

        // the param. team is the form field; @RequestParam is not really needed
        var games = gameService.findGamesByTeam(team);

        // the attribute name games is the key associated with the List of Games
        model.addAttribute("games", games);

        // the return value games is the logical name of a physical HTML file named games.html
        return "games";
    }
}
