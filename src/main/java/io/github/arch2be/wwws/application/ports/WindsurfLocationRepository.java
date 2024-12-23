package io.github.arch2be.wwws.application.ports;

import io.github.arch2be.wwws.application.dto.City;

import java.util.List;

public interface WindsurfLocationRepository {
    List<City> findAll();
}
