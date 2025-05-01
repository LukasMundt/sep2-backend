package de.haw.se2.praktikum.speedrun.se2_speedrun.user.dataaccess.api.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("ADMINISTRATOR")
public class Administrator extends User {

}
