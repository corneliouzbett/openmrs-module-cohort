package org.openmrs.module.cohort;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Visit;


public class CohortMemberVisit extends BaseOpenmrsData {
    private static final long serialVersionUID = 1L;
    private Visit visit;
    private CohortVisit cohortVisit;
    

    private int cohortMemberVisitId;

    public int getCohortMemberVisitId() {
        return cohortMemberVisitId;
    }

    public void setCohortMemberVisitId(int cohortMemberVisitId) {
        this.cohortMemberVisitId = cohortMemberVisitId;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public CohortVisit getCohortVisit() {
        return cohortVisit;
    }

    public void setCohortVisit(CohortVisit cohortVisit) {
        this.cohortVisit = cohortVisit;
    }


    @Override
    public Integer getId() {
        return this.getCohortMemberVisitId();
    }

    @Override
    public void setId(Integer id) {
        this.setCohortMemberVisitId(id);
    }
}
