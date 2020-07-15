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
import org.openmrs.module.cohort.CohortType;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortTypeDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_TYPE_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortTypeDaoTest_initialTestData.xml";

	private static final String COHORT_TYPE_UUID = "94517bf9-d8d7-4726-b4f1-a2dff6b36e2d";

	private static final int COHORT_TYPE_ID = 101;

	private static final String COHORT_TYPE_NAME = "cohort type name";

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_TYPE_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void shouldFindCohortTypeByID() {
		CohortType cohortType = dao.getCohortTypeById(COHORT_TYPE_ID);
		assertThat(cohortType, notNullValue());
		assertThat(cohortType.getCohortTypeId(), equalTo(COHORT_TYPE_ID));

	}

	@Test
	public void shouldFindCohortTypeByUuid() {
		CohortType cohortType = dao.getCohortTypeByUuid(COHORT_TYPE_UUID);
		assertThat(cohortType, notNullValue());
		assertThat(cohortType.getUuid(), equalTo(COHORT_TYPE_UUID));

	}

	@Test
	public void shouldFindCohortTypeByName() {
		CohortType cohortType = dao.getCohortTypeByName(COHORT_TYPE_NAME);
		assertThat(cohortType, notNullValue());
		assertThat(cohortType.getUuid(), equalTo(COHORT_TYPE_UUID));
		assertThat(cohortType.getName(), equalTo(COHORT_TYPE_NAME));

	}

}
