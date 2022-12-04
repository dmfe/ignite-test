package xyz.dmfe.ignite.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
public class EntityWithUUID {

    @Id
    private UUID id;

    public EntityWithUUID() {
        this.id = UUID.randomUUID();
    }
}
