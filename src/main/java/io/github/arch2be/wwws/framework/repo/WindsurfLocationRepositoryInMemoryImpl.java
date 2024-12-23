package io.github.arch2be.wwws.framework.repo;

import io.github.arch2be.wwws.application.dto.City;
import io.github.arch2be.wwws.application.exceptions.WindsurfLocationsAreNotDefinedException;
import io.github.arch2be.wwws.application.ports.WindsurfLocationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
class WindsurfLocationRepositoryInMemoryImpl implements WindsurfLocationRepository {

    private final WindsurfLocationConfiguration windsurfLocationConfiguration;

    WindsurfLocationRepositoryInMemoryImpl(WindsurfLocationConfiguration windsurfLocationConfiguration) {
        this.windsurfLocationConfiguration = windsurfLocationConfiguration;
    }

    @Override
    public List<City> findAll() {
        List<String> locations = windsurfLocationConfiguration.getLocations();

        if (isNull(locations) || locations.isEmpty()) {
            throw new WindsurfLocationsAreNotDefinedException();
        }

        return locations.stream()
                .map(City::new)
                .toList();
    }
}

