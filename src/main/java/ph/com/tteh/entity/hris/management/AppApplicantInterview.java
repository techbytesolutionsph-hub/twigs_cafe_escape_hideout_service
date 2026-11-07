package ph.com.tteh.entity.hris.management;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ph.com.tteh.entity.staff.generalinfo.AppEmployeeProfile;
import ph.com.tteh.enums.ScheduleStatus;
import ph.com.tteh.enums.hris.InterviewMethod;
import ph.com.tteh.enums.hris.InterviewResult;
import ph.com.tteh.enums.hris.InterviewStage;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "tteh_app_applicant_interview")
@NoArgsConstructor
@AllArgsConstructor
public class AppApplicantInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "interviewer")
    private AppEmployeeProfile interviewer;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_method", nullable = false)
    private InterviewMethod interviewMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_stage", nullable = false)
    private InterviewStage interviewStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_result")
    private InterviewResult interviewResult;

    @Column(name = "interview_date")
    private LocalDate interviewDate;

    @Column(name = "interview_time")
    private LocalTime interviewTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_status", nullable = false)
    private ScheduleStatus scheduleStatus;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_id", nullable = false)
    private AppApplicant applicant;
}
