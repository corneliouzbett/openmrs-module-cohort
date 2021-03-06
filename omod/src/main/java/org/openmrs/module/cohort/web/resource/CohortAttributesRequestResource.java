package org.openmrs.module.cohort.web.resource;

import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.cohort.CohortAttribute;
import org.openmrs.module.cohort.CohortAttributeType;
import org.openmrs.module.cohort.CohortM;
import org.openmrs.module.cohort.api.CohortService;
import org.openmrs.module.cohort.rest.v1_0.resource.CohortRest;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1 + CohortRest.COHORT_NAMESPACE + "/cohortattribute", supportedClass = CohortAttribute.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*", "1.10.*, 1.11.*", "1.12.*", "2.0.*", "2.1.*", "2.2.*"})
public class CohortAttributesRequestResource extends DataDelegatingCrudResource<CohortAttribute> {

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {

        DelegatingResourceDescription description = null;

        if (Context.isAuthenticated()) {
            description = new DelegatingResourceDescription();
            if (rep instanceof DefaultRepresentation) {
                description.addProperty("cohort", Representation.REF);
                description.addProperty("value");
                description.addProperty("cohortAttributeType");
                description.addProperty("uuid");
	            description.addSelfLink();
            } 
            else if (rep instanceof FullRepresentation) {
                description.addProperty("cohort", Representation.REF);
                description.addProperty("value");
                description.addProperty("cohortAttributeType");
                description.addProperty("uuid");
                description.addProperty("auditInfo");
	            description.addSelfLink();
            }
        }
        return description;
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription description = new DelegatingResourceDescription();
        description.addRequiredProperty("cohort");
        description.addRequiredProperty("value");
        description.addRequiredProperty("cohortAttributeType");
        return description;
    }
    
    @Override
    public DelegatingResourceDescription getUpdatableProperties() throws ResourceDoesNotSupportOperationException {
    	return getCreatableProperties();
    }

    @Override
    public CohortAttribute save(CohortAttribute cohortAttribute) {
        return Context.getService(CohortService.class).saveCohortAttribute(cohortAttribute);
    }

    @Override
    protected void delete(CohortAttribute cohortAttribute, String reason, RequestContext request) throws ResponseException {
        cohortAttribute.setVoided(true);
        cohortAttribute.setVoidReason(reason);
    	Context.getService(CohortService.class).saveCohortAttribute(cohortAttribute);
    }

    @Override
    public void purge(CohortAttribute cohortAttribute, RequestContext request) throws ResponseException {
    	Context.getService(CohortService.class).purgeCohortAtt(cohortAttribute);
    }

    @Override
    public CohortAttribute newDelegate() {
        return new CohortAttribute();
    }

    @Override
    public CohortAttribute getByUniqueId(String uuid) {
        return Context.getService(CohortService.class).getCohortAttributeByUuid(uuid);
    }
    
    @Override
    protected PageableResult doSearch(RequestContext context) {
    	String cohort = context.getParameter("cohort");
    	String attr = context.getParameter("attributeType");
    	
    	CohortM cohorto = Context.getService(CohortService.class).getCohortByName(cohort);
    	if(cohorto == null) {
    		cohorto = Context.getService(CohortService.class).getCohortByUuid(cohort);
    	}
    	
    	if(cohorto == null) {
    		throw new IllegalArgumentException("No valid value specified for param cohort");
    	}
    	
    	Integer attributeId = null;
    	CohortAttributeType attTypeo = null;
    	
    	if(org.apache.commons.lang3.StringUtils.isNotBlank(attr)) {
    		attTypeo = Context.getService(CohortService.class).getCohortAttributeTypeByName(attr);
    		
    		if(attTypeo == null) {
        		attTypeo = Context.getService(CohortService.class).getCohortAttributeTypeByUuid(attr);
    		}
    		
    		if(attTypeo != null) {
    			attributeId = attTypeo.getCohortAttributeTypeId();
    		}
    	}

    	    if(cohorto != null) {
                List<CohortAttribute> list = Context.getService(CohortService.class).findCohortAttributes(cohorto.getCohortId(), attributeId);
                return new NeedsPaging<CohortAttribute>(list, context);
    	    } else {
                List<CohortAttribute> list = Context.getService(CohortService.class).getCohortAttributesByAttributeType(attributeId);
                return new NeedsPaging<CohortAttribute>(list, context);
            }
    }
}
