package ph.com.tteh.entity.common;

import java.time.LocalDateTime;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ph.com.tteh.utils.LocalDateUtils;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity extends BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    private void prePersist() {
        createdAt = LocalDateUtils.getLocalDateTime();
        createdBy = "SYSTEM";
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateUtils.getLocalDateTime();
        updatedBy = "SYSTEM";
    }
}