package org.openmrs.module.cohort.web.resource;

import java.util.Date;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.CohortLeader;
import org.openmrs.module.cohort.CohortM;
import org.openmrs.module.cohort.api.CohortService;
import org.openmrs.module.cohort.rest.v1_0.resource.CohortRest;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + CohortRest.COHORT_NAMESPACE + "/cohortleader", supportedClass = CohortLeader.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*", "1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*", "2.2.*"})
public class CohortLeaderRequestResource extends DataDelegatingCrudResource<CohortLeader> {

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        if (Context.isAuthenticated()) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();

            if (rep instanceof FullRepresentation) {
                description.addProperty("person", Representation.FULL);
                description.addProperty("cohort");
                description.addProperty("startDate");
                description.addProperty("endDate");
                description.addProperty("uuid");
                description.addProperty("auditInfo");
                description.addSelfLink();
            }
            else {
                description.addProperty("person", Representation.FULL);
                description.addProperty("startDate");
                description.addProperty("endDate");
                description.addProperty("uuid");
                description.addSelfLink();
            }

            return description;
        }
        return null;
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addRequiredProperty("cohort");
        description.addRequiredProperty("person");
        description.addRequiredProperty("startDate");
        description.addProperty("endDate");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
        return getCreatableProperties();
    }

    @Override
    public CohortLeader save(CohortLeader cohortLeader) {
        CohortM cohort = cohortLeader.getCohort();
        List<CohortLeader> activeCohortLeaders = cohort.getActiveCohortLeaders();
        for(CohortLeader activeLeader: activeCohortLeaders) {
            activeLeader.setEndDate(new Date());
            activeLeader.setVoided(true);
        }
        return Context.getService(CohortService.class).saveCohortLeader(cohortLeader);
    }

    @Override
    protected void delete(CohortLeader cohortLeader, String reason, RequestContext request) throws ResponseException {
        cohortLeader.setVoided(true);
        cohortLeader.setVoidReason(reason);
        Context.getService(CohortService.class).saveCohortLeader(cohortLeader);
    }

    @Override
    public void purge(CohortLeader cohortLeader, RequestContext request) throws ResponseException {
        Context.getService(CohortService.class).purgeCohortLeader(cohortLeader);
    }

    @Override
    public CohortLeader newDelegate() {
        return new CohortLeader();
    }

    @Override
    public CohortLeader getByUniqueId(String id) {
        CohortLeader obj = Context.getService(CohortService.class).getCohortLeaderByUuid(id);

        if(obj == null) {
            // TODO add to API
            // obj = Context.getService(CohortService.class).getCohortByName(id);
        }

        return obj;
    }

    @Override
    protected PageableResult doSearch(RequestContext context) {
        String cohortUuid = context.getParameter("cohort");
        CohortM cohort = Context.getService(CohortService.class).getCohortByUuid(cohortUuid);

        if (cohort == null) {
            throw new NullPointerException("No Cohort Exists with that UUID");
        }
        int cohortId = cohort.getCohortId();
        List<CohortLeader> list = Context.getService(CohortService.class).getCohortLeadersByCohortId(cohortId);
        return new NeedsPaging<CohortLeader>(list, context);
    }
}
