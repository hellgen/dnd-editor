package com.helen.dnd_charachter_editor.entity.reference.table;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "race_features", schema = "dnd_editor")
public class RaceFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "race_feature_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "race_id", nullable = false)
    private Race race;

    @Size(max = 100)
    @NotNull
    @Column(name = "feature_name", nullable = false, length = 100)
    private String featureName;

    @Size(max = 1024)
    @Column(name = "feature_description", length = 1024)
    private String featureDescription;

}