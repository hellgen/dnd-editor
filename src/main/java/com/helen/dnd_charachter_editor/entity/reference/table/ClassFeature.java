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
@Table(name = "class_features", schema = "dnd_editor")
public class ClassFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "class_feature_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "class_id", nullable = false)
    private CharacterClass characterClass;

    @Size(max = 100)
    @NotNull
    @Column(name = "feature_name", nullable = false, length = 100)
    private String featureName;

    @Size(max = 1024)
    @Column(name = "feature_description", length = 1024)
    private String featureDescription;

    @NotNull
    @Column(name = "level_required", nullable = false)
    private Integer levelRequired;

}