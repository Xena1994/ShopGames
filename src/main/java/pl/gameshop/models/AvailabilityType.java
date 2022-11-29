package pl.gameshop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="availability_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean inStock;
}
