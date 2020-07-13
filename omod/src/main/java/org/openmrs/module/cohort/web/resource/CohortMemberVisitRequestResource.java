package org.openmrs.module.cohort.web.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.CohortMemberVisit;
import org.openmrs.module.cohort.api.CohortService;
import org.openmrs.module.cohort.rest.v1_0.resource.CohortRest;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + CohortRest.COHORT_NAMESPACE + "/cohortmembervisit", supportedClass = CohortMemberVisit.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*", "1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*", "2.2.*"})
public class CohortMemberVisitRequestResource extends DataDelegatingCrudResource<CohortMemberVisit> {


    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {

        DelegatingResourceDescription description = null;

        if (Context.isAuthenticated()) {
            description = new DelegatingResourceDescription();
            if (rep instanceof DefaultRepresentation) {
                description.addProperty("visit", Representation.FULL);
                description.addProperty("uuid");
                description.addSelfLink();
            }
            else if (rep instanceof FullRepresentation) {
                description.addProperty("visit", Representation.FULL);
                description.addProperty("cohortVisit", Representation.REF);
                description.addProperty("uuid");
                description.addProperty("auditInfo");
                description.addSelfLink();
            }
        }
        return description;
    }

    @Override
    public CohortMemberVisit getByUniqueId(String s) {
        return Context.getService(CohortService.class).getCohortMemberVisitByUuid(s);
    }

    @Override
    protected void delete(CohortMemberVisit cohortMemberVisit, String s, RequestContext requestContext) throws ResponseException {

    }

    @Override
    public CohortMemberVisit newDelegate() {
        return new CohortMemberVisit();
    }

    @Override
    public CohortMemberVisit save(CohortMemberVisit cohortMemberVisit) {
        return Context.getService(CohortService.class).saveCohortMemberVisit(cohortMemberVisit);
    }

    @Override
    public void purge(CohortMemberVisit cohortMemberVisit, RequestContext requestContext) throws ResponseException {

    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addProperty("visit");
        description.addProperty("cohortVisit");
        return description;
    }

    @Override
    public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
        return getCreatableProperties();
    }

//    @Override
//    protected PageableResult doSearch(RequestContext context) {
//        String cohort = context.getParameter("cohort_visit");
//
//        CohortVisit cohorto = Context.getService(CohortService.class).getCohortByName(cohort);
//        if(cohorto == null) {
//            cohorto = Context.getService(CohortService.class).getCohortByUuid(cohort);
//        }
//
//        if(cohorto == null) {
//            throw new IllegalArgumentException("No valid value specified for param cohort");
//        }
//
//        List<CohortMember> list = Context.getService(CohortService.class).findCohortMembersByCohort(cohorto.getCohortId());
//        return new NeedsPaging<CohortMember>(list, context);
//    }

}
