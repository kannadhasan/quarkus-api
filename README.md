# quarkus-api
---
"openapi: 3.0.3
info:
  title: quarkus-api API
  version: 1.0.0-SNAPSHOT
tags:
- name: Movie Resource
  description: Movie Rest APIs
paths:
  /hello:
    get:
      tags:
      - Greeting Resource
      responses:
        "200":
          description: OK
          content:
            text/plain:
              schema:
                type: string
  /movies:
    get:
      tags:
      - Movie Resource
      summary: Get Movies
      description: Get all movies inside the list
      operationId: getMovies
      responses:
        "200":
          description: operation completed
          content:
            application/json: {}
    post:
      tags:
      - Movie Resource
      summary: create a new Movies
      description: create a new movie to add inside the list
      operationId: createMovies
      requestBody:
        description: movie to create
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/Movie'
        required: true
      responses:
        "201":
          description: Movie created
          content:
            application/json: {}
  /movies/size:
    get:
      tags:
      - Movie Resource
      summary: Get Movies count
      description: Get movies count inside the list
      operationId: getMovies count
      responses:
        "200":
          description: operation completed
          content:
            text/plain: {}
  /movies/{id}:
    delete:
      tags:
      - Movie Resource
      summary: delete movie from list
      description: delete a movie from the list
      operationId: deleteMovies
      parameters:
      - name: id
        in: path
        required: true
        schema:
          format: int64
          type: integer
      responses:
        "204":
          description: Movie deleted
          content:
            application/json: {}
        "400":
          description: Movie not valid
          content:
            application/json: {}
  /movies/{id}/{title}:
    put:
      tags:
      - Movie Resource
      summary: update existing Movies
      description: update a movie inside the list
      operationId: updateMovies
      parameters:
      - name: id
        in: path
        description: movie id
        required: true
        schema:
          format: int64
          type: integer
      - name: title
        in: path
        description: movie title
        required: true
        schema:
          type: string
      responses:
        "200":
          description: Movie updated
          content:
            application/json: {}
components:
  schemas:
    Movie:
      description: Movie reprasentation
      required:
      - title
      type: object
      properties:
        id:
          format: int64
          type: integer
        title:
          type: string
  securitySchemes:
    SecurityScheme:
      type: http
      description: Authentication
      scheme: basic"
