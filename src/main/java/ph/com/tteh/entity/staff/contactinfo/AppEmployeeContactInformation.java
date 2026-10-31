package ph.com.tteh.entity.staff.contactinfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_employee_contact_information")
@NoArgsConstructor
@AllArgsConstructor
public class AppEmployeeContactInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "work_email")
    private String workEmail;

    @Column(name = "emergency_contact_name")
    private String emergencyContactName;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "relationship")
    private String relationship;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_profile_id", nullable = false, unique = true)
    private AppEmployeeProfile appEmployeeProfile;
}