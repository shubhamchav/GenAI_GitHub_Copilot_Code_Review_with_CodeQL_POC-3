package com.movieapp.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api/movies")

public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping({"", "/"})
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.createMovie(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        try {
            return ResponseEntity.ok(movieService.updateMovie(id, movie));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    // Intentionally vulnerable to SQL Injection
    @GetMapping("/search-unsafe")
    public List<Movie> searchMoviesUnsafe(@RequestParam String title) {
        String sql = "SELECT * FROM movies WHERE title = '" + title + "'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Movie m = new Movie();
            m.setId(rs.getLong("id"));
            m.setTitle(rs.getString("title"));
            m.setDescription(rs.getString("description"));
            m.setDirector(rs.getString("director"));
            m.setYear(rs.getInt("year"));
            m.setMovieImage(rs.getString("movieImage"));
            return m;
        });
    }
}
