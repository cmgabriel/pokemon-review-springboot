package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Pokemon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon() {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pokemon")
                .type("electric")
                .build();
        //Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        //Assert
        assertThat(savedPokemon).isNotNull();
        assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_FindAll_ReturnMoreThanOnePokemon() {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pokemon")
                .type("electric")
                .build();

        Pokemon pokemon2 = Pokemon.builder()
                .name("Pokemon")
                .type("electric")
                .build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        //Act
        List<Pokemon> pokemons = pokemonRepository.findAll();

        //Assert
        assertThat(pokemons).isNotNull();
        assertThat(pokemons.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnPokemon() {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pickachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        //Act
        Pokemon savedPokemon = pokemonRepository.findById(pokemon.getId()).get();

        //Assert
        assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonRepository_FindByType_ReturnPokemon() {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pickachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        //Act
        Pokemon savedPokemon = pokemonRepository.findByType(pokemon.getType()).get();

        //Assert
        assertThat(savedPokemon).isNotNull();
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnPokemonNotNull(){
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pickachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        //Act
        Pokemon savedPokemon = pokemonRepository.findByType(pokemon.getType()).get();
        savedPokemon.setType("Electric");
        savedPokemon.setName("Raichu");

        Pokemon updatedPokemon = pokemonRepository.save(savedPokemon);

        //Assert
        assertThat(updatedPokemon.getName()).isNotNull();
        assertThat(updatedPokemon.getType()).isNotNull();

    }

    @Test
    public void PokemonRepository_PokemonDelete_ReturnPokemonIsEmpty() {
        //Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("pickachu")
                .type("electric")
                .build();
        pokemonRepository.save(pokemon);

        //Act
        pokemonRepository.deleteById(pokemon.getId());
        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(pokemon.getId());

        //Assert
        assertThat(pokemonOptional).isEmpty();
    }

}