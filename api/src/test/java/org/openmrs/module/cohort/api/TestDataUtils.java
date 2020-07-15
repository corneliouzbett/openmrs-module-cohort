/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.api;

import java.util.Collections;
import java.util.Date;

import org.openmrs.module.cohort.CohortAttribute;
import org.openmrs.module.cohort.CohortAttributeType;
import org.openmrs.module.cohort.CohortLeader;
import org.openmrs.module.cohort.CohortM;
import org.openmrs.module.cohort.CohortType;

public class TestDataUtils {

	public static CohortType COHORT_TYPE () {
		CohortType cohortType = new CohortType();
		cohortType.setId(1);
		cohortType.setCohortTypeId(100);
		cohortType.setName("TestCohortType");
		cohortType.setDescription("Test cohort description");
		return cohortType;
	}

	public static CohortAttributeType COHORT_ATTRIBUTE_TYPE() {
		CohortAttributeType cohortAttributeType = new CohortAttributeType();
		cohortAttributeType.setId(1);
		cohortAttributeType.setName("cohortAttributeType");
		cohortAttributeType.setDescription("test cohort attribute type");
		cohortAttributeType.setFormat("java.lang.String");
		cohortAttributeType.setCohortAttributeTypeId(400);
		return cohortAttributeType;
	}

	public static CohortAttribute COHORT_ATTRIBUTE () {
		CohortAttribute cohortAttribute = new CohortAttribute();
		cohortAttribute.setId(1);
		cohortAttribute.setUuid("");
		cohortAttribute.setCohortAttributeId(200);
		cohortAttribute.setValue("cohortAttribute");
		cohortAttribute.setCohortAttributeType(COHORT_ATTRIBUTE_TYPE());
		return cohortAttribute;
	}

	public static CohortM COHORT() {
		CohortM cohort = new CohortM();
		cohort.setCohortId(1);
		cohort.setUuid("");
		cohort.setCohortType(COHORT_TYPE());
		cohort.setAttributes(Collections.singletonList(COHORT_ATTRIBUTE()));
		cohort.setCohortLeaders(Collections.singletonList(COHORT_LEADER()));
		return cohort;
	}

	public static CohortLeader COHORT_LEADER() {
		CohortLeader leader = new CohortLeader();
		leader.setId(1);
		leader.setUuid("");
		leader.setCohortLeaderId(300);
		leader.setCohort(COHORT());
		leader.setStartDate(new Date());
		leader.setEndDate(new Date());
		return leader;
	}

}
