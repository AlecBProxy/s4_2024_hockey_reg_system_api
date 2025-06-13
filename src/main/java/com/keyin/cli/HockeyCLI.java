package com.keyin.cli;

import com.keyin.rest.game.Game;
import com.keyin.rest.game.GameService;
import com.keyin.rest.team.Team;
import com.keyin.rest.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Scanner;

@Component
public class HockeyCLI implements CommandLineRunner {

    @Autowired
    private TeamService teamService;

    @Autowired
    private GameService gameService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the DevOps Hockey System CLI!");
        System.out.println("1. Add a team");
        System.out.println("2. Add a game");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Enter team name: ");
                String name = scanner.nextLine();

                Team team = new Team();
                team.setName(name);
                teamService.createTeam(team);
                System.out.println("Success! Team created: " + name);
            }
            case 2 -> {
                System.out.print("Enter home team ID: ");
                long homeId = Long.parseLong(scanner.nextLine());

                System.out.print("Enter away team ID: ");
                long awayId = Long.parseLong(scanner.nextLine());

                System.out.print("Enter location: ");
                String location = scanner.nextLine();

                LocalDateTime now = LocalDateTime.now(); // Or parse from input

                Team home = teamService.getTeamById(homeId);
                Team away = teamService.getTeamById(awayId);

                Game game = new Game();
                game.setHomeTeam(home);
                game.setAwayTeam(away);
                game.setLocation(location);
                game.setScheduledDate(now);

                gameService.createGame(game);
                System.out.println("✅ Game created between " + home.getName() + " and " + away.getName());
            }
            default -> System.out.println("Error: Invalid choice.");
        }

        scanner.close();
    }
}
