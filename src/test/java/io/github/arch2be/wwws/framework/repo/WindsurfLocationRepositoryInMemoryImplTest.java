package io.github.arch2be.wwws.framework.repo;

import io.github.arch2be.wwws.application.dto.City;
import io.github.arch2be.wwws.application.exceptions.WindsurfLocationsAreNotDefinedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WindsurfLocationRepositoryInMemoryImplTest {

    @Mock
    private WindsurfLocationConfiguration windsurfLocationConfiguration;

    @InjectMocks
    private WindsurfLocationRepositoryInMemoryImpl sut;

    @Test
    void findAll_whenCitiesAreDefined_shouldReturnAll() {
        // Given:
        final List<City> expectedCities = List.of(new City("city1"), new City("city2"));

        // When:
        when(windsurfLocationConfiguration.getLocations()).thenReturn(List.of("city1", "city2"));

        // Then:
        final List<City> actualResponse = sut.findAll();
        assertEquals(expectedCities, actualResponse);
        verify(windsurfLocationConfiguration, times(1)).getLocations();
    }

    @Test
    void findAll_whenCitiesAreNotDefined_shouldThrowAnException() {
        // When:
        when(windsurfLocationConfiguration.getLocations()).thenReturn(null);

        // Then:
        assertThatThrownBy(() -> sut.findAll())
                .isInstanceOf(WindsurfLocationsAreNotDefinedException.class)
                .hasMessage("Windsurf locations are not defined.");

        verify(windsurfLocationConfiguration, times(1)).getLocations();
    }
}