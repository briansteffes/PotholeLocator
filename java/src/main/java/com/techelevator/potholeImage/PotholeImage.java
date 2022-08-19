package com.techelevator.potholeImage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class PotholeImage {

    private Long id;
    private Long pothole_id;
    private String potholeImageLink;

    public Optional<String> getPotholeImageLink() {
        return Optional.ofNullable(potholeImageLink);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotholeImage that = (PotholeImage) o;
        return Objects.equals(id, that.id) && Objects.equals(pothole_id, that.pothole_id) && Objects.equals(potholeImageLink, that.potholeImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pothole_id, potholeImageLink);
    }
}
