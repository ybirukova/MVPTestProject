package com.example.mvptestproject.utils

import com.example.mvptestproject.domain.models.Coordinates

object Cities {

    private val citiesWithCoordinates: Map<String, Coordinates> = mapOf(
        "Minsk" to Coordinates(53.90, 27.56),
        "Berlin" to Coordinates(52.52, 13.41),
        "London" to Coordinates(51.50, -0.12),
        "Paris" to Coordinates(48.85, 2.35),
    )

    fun getCoordinatesByCity(city: String): Coordinates? {
        return citiesWithCoordinates[city]
    }
}
