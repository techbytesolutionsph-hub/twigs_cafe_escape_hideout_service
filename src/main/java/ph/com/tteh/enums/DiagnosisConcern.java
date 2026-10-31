package ph.com.tteh.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiagnosisConcern {

    SPEECH_DELAY("Speech Delay"),
    BEHAVIORAL_CONCERNS("Behavioral Concerns"),
    ASD("ASD"),
    ADHD("ADHD"),
    LEARNING_DIFFICULTIES("Learning Difficulties"),
    GLOBAL_DEVELOPMENTAL_DELAY("Global Developmental Delay"),
    SENSORY_CONCERNS("Sensory Concerns"),
    SOCIAL_INTERACTION_DIFFICULTIES("Social Interaction Difficulties"),
    COMMUNICATION_SKILLS("Communication Skills"),
    DEVELOPMENTAL_DELAY("Developmental Delay"),
    INTELLECTUAL_DISABILITY("Intellectual Disability");

    private final String displayName;
}