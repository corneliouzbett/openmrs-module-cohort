/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.api.db;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.CohortAttributeType;
import org.openmrs.module.cohort.api.TestDataUtils;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortAttributeTypeDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_ATTRIBUTE_TYPE_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortAttributeTypeDaoTest_initialTestData.xml";

	private static final String COHORT_ATTRIBUTE_TYPE_UUID = "9eb7fe43-2813-4ebc-80dc-2e5d30251bb7";

	private static final int COHORT_ATTRIBUTE_TYPE_ID = 1;

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_ATTRIBUTE_TYPE_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void shouldGetCohortAttributeTypeById() {
		CohortAttributeType cohortAttributeType = dao.getCohortAttributeTypeById(COHORT_ATTRIBUTE_TYPE_ID);
		assertThat(cohortAttributeType, notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), equalTo(COHORT_ATTRIBUTE_TYPE_ID));
	}

	@Test
	public void shouldGetCohortAttributeTypeByUuid() {
		CohortAttributeType cohortAttributeType = dao.getCohortAttributeTypeByUuid(COHORT_ATTRIBUTE_TYPE_UUID);
		assertThat(cohortAttributeType, notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), equalTo(COHORT_ATTRIBUTE_TYPE_ID));
		assertThat(cohortAttributeType.getUuid(), equalTo(COHORT_ATTRIBUTE_TYPE_UUID));
	}

	@Test
	public void shouldGetCohortAttributes() {
		CohortAttributeType cohortAttributeType = dao.getCohortAttributes(COHORT_ATTRIBUTE_TYPE_ID);
		assertThat(cohortAttributeType, notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), equalTo(COHORT_ATTRIBUTE_TYPE_ID));
	}

	@Test
	public void saveCohortAttributes() {
		CohortAttributeType cohortAttributeType = dao.saveCohortAttributes(TestDataUtils.COHORT_ATTRIBUTE_TYPE());
		assertThat(cohortAttributeType, notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), notNullValue());
		assertThat(cohortAttributeType.getCohortAttributeTypeId(), equalTo(TestDataUtils.COHORT_ATTRIBUTE_TYPE().getCohortAttributeTypeId()));

		CohortAttributeType result = dao.getCohortAttributeTypeById(cohortAttributeType.getId());
		assertThat(result, notNullValue());
		assertThat(result.getCohortAttributeTypeId(), notNullValue());
		assertThat(result.getUuid(), equalTo(cohortAttributeType.getUuid()));
		assertThat(result.getFormat(), equalTo(cohortAttributeType.getFormat()));
		assertThat(result.getCohortAttributeTypeId(), equalTo(cohortAttributeType.getCohortAttributeTypeId()));
	}
}
