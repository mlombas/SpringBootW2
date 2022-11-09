package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.search.GenreSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.InTitleSearchCommand;
import co.empathy.academy.demo_search.ports.requests.commands.search.SearchFilters;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/search")
public class SpringSearchController {

    @Autowired
    private PRequestReactor reactor;

    @GetMapping("/genres/{and}")
    public CompletableFuture<ResponseEntity<List<Movie>>>
    genres(@PathVariable boolean and, @RequestBody List<String> genres)
    {
        CompletableFuture<List<Movie>> movies = reactor.reactToSearch(
                new GenreSearchCommand(genres, and)
        );

        return movies
                .thenApply(m ->
                        ResponseEntity.ok(m)
                );
    }
    @GetMapping("/genres")
    public CompletableFuture<ResponseEntity<List<Movie>>>
    genres(@RequestBody List<String> genres)
    {
        return genres(true, genres);
    }

    @GetMapping("/intitle")
    public CompletableFuture<ResponseEntity<List<Movie>>>
    intitle(
            @RequestBody String intitle,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer durationMin,
            @RequestParam(required = false) Integer durationMax,
            @RequestParam(required = false) Integer yearMin,
            @RequestParam(required = false) Integer yearMax
            ) {
        var filters = new SearchFilters()
                .withGenre(genre)
                .withDurationMin(durationMin)
                .withDurationMax(durationMax)
                .withYearMin(yearMin)
                .withYearMax(yearMax);
        return reactor.reactToSearch(
                        new InTitleSearchCommand(intitle, filters)
                )
                .thenApply(m ->
                        ResponseEntity.ok(m)
                );
    }
}