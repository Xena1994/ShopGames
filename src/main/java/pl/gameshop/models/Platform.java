package pl.gameshop.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@Table(name="platforms")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Platform {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
}
