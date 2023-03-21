package ru.practicum.common.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.common.dto.Location;
import ru.practicum.common.models.LocationModel;

@Component
public class LocationMapper {

    public LocationModel toLocationModel(Long id, Location location) {
        return new LocationModel(
                id,
                location.getLatitude(),
                location.getLongitude());
    }

    public Location toLocation(LocationModel locationModel) {
        return new Location(
                locationModel.getLatitude(),
                locationModel.getLongitude());
    }
}
