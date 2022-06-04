package com.dacs.entitymodules;

import com.dacs.entitymodules.enumType.SettingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="settings")
@Getter
@Setter
public class Setting {

    @Id
    @Column(name = "`key`")
    private String key;

    @Column(name="`value`")
    private String value;

    @Enumerated(EnumType.STRING)
    private SettingType type;
}
