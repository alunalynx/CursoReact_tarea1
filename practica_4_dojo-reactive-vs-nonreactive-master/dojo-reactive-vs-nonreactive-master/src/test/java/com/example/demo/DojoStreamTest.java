package com.example.demo;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class DojoStreamTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        List<Player> list = CsvUtilFile.getPlayers();
        Set<Player> result = list.stream()
                .filter(jugador -> jugador.getAge() > 35)
                .collect(Collectors.toSet());
        result.forEach(System.out::println);
    }

    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, List<Player>> result = list.stream()
                .filter(player -> player.getAge() > 35)
                .distinct()
                .collect(Collectors.groupingBy(Player::getClub));

        result.forEach((key, jugadores) -> {
            System.out.println("\n");
            System.out.println(key + ": ");
            jugadores.forEach(System.out::println);
        });

    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream()
                .filter(jugador -> jugador.getNational().equals("France"))
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2);
    }


    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Map<String, Set<String>> result = list.stream()
                .collect(Collectors.groupingBy(Player::getNational,
                        HashMap::new,
                        Collectors.mapping(Player::getClub, Collectors.toSet())));
        result.forEach((s, players) -> {
            System.out.println(s + ": \n");
            System.out.println(players + "\n");
        });
    }

    @Test
    void clubConElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream()
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println(player.getClub()));
    }

    @Test
    void ElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        list.stream()
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println(player.getName()));
    }
    
}
