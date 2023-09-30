package com.pokemonreview.api.repository;

import com.pokemonreview.api.models.Review;
import org.assertj.core.api.Assertions;
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
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnsSavedReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThanOneReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        Review review2 = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();

        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();

        assertThat(reviewList).isNotNull();
        assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsSavedReview(){
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        Review savedReview = reviewRepository.findById(review.getId()).get();

        assertThat(savedReview).isNotNull();
        assertThat(savedReview.getId()).isEqualTo(review.getId());
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        Review savedReview = reviewRepository.findById(review.getId()).get();
        savedReview.setTitle("Title1");
        savedReview.setContent("New Content");
        Review updatedReview = reviewRepository.save(savedReview);

        assertThat(updatedReview.getTitle()).isNotNull();
        assertThat(updatedReview.getContent()).isNotNull();
        assertThat(updatedReview.getStars()).isEqualTo(savedReview.getStars());
    }

    @Test
    public void ReviewRepository_DeleteReview_ReturnReviewIsEmpty() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewOptional = reviewRepository.findById(review.getId());

        assertThat(reviewOptional).isEmpty();

    }
}