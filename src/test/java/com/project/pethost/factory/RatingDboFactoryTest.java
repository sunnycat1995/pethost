package com.project.pethost.factory;

import com.project.pethost.constant.RatingConstant;
import com.project.pethost.dbo.PetDbo;
import com.project.pethost.dbo.UserDbo;
import com.project.pethost.dbo.rating.KeeperRatingDbo;
import com.project.pethost.dbo.rating.PetRatingDbo;
import com.project.pethost.repository.PetRepository;
import com.project.pethost.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RatingDboFactoryTest {
    private RatingDboFactory ratingDboFactory;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PetRepository petRepository;

    public RatingDboFactoryTest() {
        this.ratingDboFactory = new RatingDboFactory(new RatingConstant());
    }

    @Test
    public void createKeeperRatingDbo() {
        when(userRepository.findByEmail(anyString())).thenReturn(
                new UserDbo());
        final KeeperRatingDbo rating = ratingDboFactory.createRatingDbo("email", userRepository);
        Assert.assertTrue(rating.getCounter().equals(0L));
        Assert.assertTrue(rating.getRating().equals(0.0));
    }

    @Test
    public void createPetRatingDbo() {
        final PetDbo pet = new PetDbo();
        final Long id = 1L;
        pet.setId(id);
        when(petRepository.findById(anyLong())).thenReturn(java.util.Optional.of(pet));
        final PetRatingDbo rating = ratingDboFactory.createRatingDbo(id, petRepository);
        Assert.assertTrue(rating.getCounter().equals(0L));
        Assert.assertTrue(rating.getRating().equals(0.0));
    }
}