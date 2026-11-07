package ph.com.tteh.entity.hris.management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.enums.OfferCallMethod;
import ph.com.tteh.enums.OfferCallStatus;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_applicant_offer_call")
@NoArgsConstructor
@AllArgsConstructor
public class AppApplicantOfferCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "call_method", length = 30)
    private OfferCallMethod callMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "call_status", length = 30)
    private OfferCallStatus callStatus;

    @Column(name = "call_date")
    private LocalDate callDate;

    @Column(name = "call_time")
    private LocalTime callTime;

    @Column(name = "meeting_link", length = 1000)
    private String meetingLink;

    @Column(name = "call_notes", columnDefinition = "TEXT")
    private String callNotes;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "applicant_offer_id", nullable = false)
    private AppApplicantOffer applicantOffer;
}
