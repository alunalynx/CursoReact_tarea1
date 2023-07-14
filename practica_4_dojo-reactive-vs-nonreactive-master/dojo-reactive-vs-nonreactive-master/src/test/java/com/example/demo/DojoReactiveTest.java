package com.example.demo;


import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

public class DojoReactiveTest {

    @Test
    void converterData(){
        List<Player> list = CsvUtilFile.getPlayers();
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35() {
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable.filter(jugador -> jugador.getAge() > 35)
                .subscribe(System.out::println);
    }


    @Test
    void jugadoresMayoresA35SegunClub(){
        List<Player> readCsv = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(readCsv);

        observable.filter(player -> player.getAge() > 35)
                .distinct()
                .groupBy(Player::getClub)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(list -> {
                            Map<String, List<Player>> map = new HashMap<>();
                            map.put(groupedFlux.key(), list);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((key, value) -> {
                        System.out.println("\n");
                        System.out.println(key + ": ");
                        value.forEach(System.out::println);
                    });
                });

    }


    @Test
    void mejorJugadorConNacionalidadFrancia(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);
        observable
                .filter(jugador -> jugador.getNational().equals("France"))
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .subscribe(System.out::println)
                ;
    }

    @Test
    void clubsAgrupadosPorNacionalidad(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable
                .collect(Collectors.groupingBy(Player::getNational,
                        HashMap::new,
                        Collectors.mapping(Player::getClub,Collectors.toSet())))
                .subscribe(map -> {
                    map.forEach((nac, club) -> {
                        System.out.println("Nacionalidad: " + nac);
                        System.out.println("Clubes: " + club);
                    });
                });
    }

    @Test
    void clubConElMejorJugador(){
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .subscribe(player -> System.out.println("\nClub con el mejor jugador: " + player.getClub()));
    }

    @Test
    void ElMejorJugador() {
        List<Player> list = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(list);

        observable.reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .subscribe(player -> System.out.println("\n Mejor jugador:" + player.getName() + "\n"));
    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        List<Player> readCsv = CsvUtilFile.getPlayers();
        Flux<Player> observable = Flux.fromIterable(readCsv);

        observable
                .groupBy(Player::getNational)
                .flatMap(groupedFlux -> groupedFlux
                        .collectList()
                        .map(list -> {
                            Player best = list.stream().reduce((p1, p2)->((p1.getWinners()/p1.getGames())>=(p2.getWinners()/p2.getGames())?p1:p2)).get();
                            Map<String, Player> map = new HashMap<>();
                            map.put(groupedFlux.key(), best);
                            return map;
                        }))
                .subscribe(map -> {
                    map.forEach((k, v) -> {
                        System.out.println("\n");
                        System.out.println(k + ": ");
                        System.out.println(v);
                    });
                });
    }
}
