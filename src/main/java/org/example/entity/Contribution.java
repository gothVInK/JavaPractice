package org.example.entity;

import org.example.entity.common.Column;
import org.example.entity.common.IEntity;
import org.example.entity.common.NonEditColumn;
import org.example.entity.common.SequenceColumn;

import java.util.Map;

public class Contribution implements IEntity {
    @Column(title = "Contribution Code")
    @SequenceColumn
    @NonEditColumn
    private Integer contribution_code;

    @Column(title = "Contribution Name") private String contribution_name;
    @Column(title = "Months") private Integer months;
    @Column(title = "Rate") private Double rate;

    private ClientAccount account;

    public Contribution(Integer contributionCode, String contributionName, Integer months, Double rate) {
        this.contribution_code = contributionCode;
        this.contribution_name = contributionName;
        this.months = months;
        this.rate = rate;
    }

    public Contribution(Map<String, String> columns) {
        this(
                columns.get("contribution_code") != null ? Integer.valueOf(columns.get("contribution_code")) : null,
                columns.get("contribution_name"),
                Integer.valueOf(columns.get("months")),
                Double.valueOf(columns.get("rate"))
        );
    }

    @Override
    public Integer getPk() {
        return contribution_code;
    }
    public String getName() {
        return contribution_name;
    }
    public Integer getMonths() {
        return months;
    }
    public Double getRate(){return rate;}
}
