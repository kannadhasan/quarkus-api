package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
@Tag(name = "Movie Resource", description = "Movie Rest APIs")
public class MovieResource {
    public List<Movie> movies = new ArrayList<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "getMovies", summary = "Get Movies", description = "Get all movies inside the list")
    @APIResponse(responseCode = "200", description = "operation completed", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response getMovies() {
        return Response.ok(movies).build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(operationId = "getMovies count", summary = "Get Movies count", description = "Get movies count inside the list")
    @APIResponse(responseCode = "200", description = "operation completed", content = @Content(mediaType = MediaType.TEXT_PLAIN))

    @Path("/size")
    public int countMovies() {
        return movies.size();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(operationId = "createMovies", summary = "create a new Movies", description = "create a new movie to add inside the list")
    @APIResponse(responseCode = "201", description = "Movie created", content = @Content(mediaType = MediaType.APPLICATION_JSON))

    public Response creatMovie(
            @RequestBody(description = "movie to create", required = true, content = @Content(schema = @Schema(implementation = Movie.class))) Movie newMovie) {
        movies.add(newMovie);
        return Response.status(Response.Status.CREATED).entity(newMovie).build();
    }

    @PUT
    @Path("{id}/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "updateMovies", summary = "update existing Movies", description = "update a movie inside the list")
    @APIResponse(responseCode = "200", description = "Movie updated", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    public Response updateMovie(
            @Parameter(description = "movie id", required = true) @PathParam("id") Long id,
            @Parameter(description = "movie title", required = true) @PathParam("title") String title) {
        movies = movies.stream().map(movie -> {
            if (movie.getId().equals(id)) {
                movie.setTitle(title);
            }
            return movie;
        }).collect(Collectors.toList());
        return Response.ok(movies).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "deleteMovies", summary = "delete movie from list", description = "delete a movie from the list")
    @APIResponse(responseCode = "204", description = "Movie deleted", content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @APIResponse(responseCode = "400", description = "Movie not valid", content = @Content(mediaType = MediaType.APPLICATION_JSON))

   
    public Response deleteMovie(
            @PathParam("id") Long id) {
        Optional<Movie> movieToDelete = movies.stream().filter(movie -> movie.getId().equals(id))
                .findFirst();
        boolean removed = false;
        if (movieToDelete.isPresent()) {
            movies.remove(movieToDelete.get());
        }
        if (removed) {
            return Response.noContent().build();
        } else {
            return Response.ok(movies).build();
        }

    }

}
