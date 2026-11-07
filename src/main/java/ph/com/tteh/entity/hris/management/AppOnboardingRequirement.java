package ph.com.tteh.entity.hris.management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.common.BaseEntity;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(
        name = "tteh_app_applicant_onboarding_requirement",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_onboarding_requirement_name",
                        columnNames = "name"
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class AppOnboardingRequirement extends BaseEntity {

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_required", nullable = false)
    private boolean required = true;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
