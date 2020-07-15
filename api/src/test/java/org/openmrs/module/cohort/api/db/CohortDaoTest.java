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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.CohortM;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortDaoTest_initialTestData.xml";

	private static final String COHORT_UUID = "7f9a2479-c14a-4bfc-bcaa-632860258519";

	private static final String COHORT_NAME = "COVID-19 patients";

	private static final int COHORT_ID = 12;

	private static final String COHORT_NAME1 = "cohort name";

	private static final String COHORT_DESCRIPTION = "Cohort description";

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void shouldGetCohortByName() {
		CohortM cohort = dao.getCohortByName(COHORT_NAME);
		assertThat(cohort, notNullValue());
		assertThat(cohort.getName(), notNullValue());
		assertThat(cohort.getName(), equalTo(COHORT_NAME));
	}

	@Test
	public void shouldGetCohortMById() {
		CohortM cohort = dao.getCohortMById(COHORT_ID);
		assertThat(cohort, notNullValue());
		assertThat(cohort.getCohortId(), notNullValue());
		assertThat(cohort.getCohortId(), equalTo(COHORT_ID));
	}

	@Test
	public void shouldGetCohortMByUuid() {
		CohortM cohort = dao.getCohortMByUuid(COHORT_UUID);
		assertThat(cohort, notNullValue());
		assertThat(cohort.getCohortId(), notNullValue());
		assertThat(cohort.getCohortId(), equalTo(COHORT_ID));
		assertThat(cohort.getUuid(), notNullValue());
		assertThat(cohort.getUuid(), equalTo(COHORT_UUID));
	}

	@Test
	public void shouldGetCohortUuid() {
		CohortM cohort = dao.getCohortUuid(COHORT_UUID);
		assertThat(cohort, notNullValue());
		assertThat(cohort.getUuid(), notNullValue());
		assertThat(cohort.getUuid(), equalTo(COHORT_UUID));
	}

	@Test
	public void shouldCreateNewCohort() {
		CohortM cohortM = new CohortM();
		cohortM.setUuid("24c266ec-ef38-4af5-bf67-608d64a7a461");
		cohortM.setCohortId(1);
		cohortM.setName(COHORT_NAME1);
		cohortM.setDescription(COHORT_DESCRIPTION);
		CohortM cohort = dao.saveCohort(cohortM);
		assertThat(cohort, notNullValue());

		CohortM savedCohort = dao.getCohortMById(cohort.getCohortId());
		assertThat(savedCohort, notNullValue());
		assertThat(savedCohort, equalTo(cohort));

	}

	@Test
	public void shouldGetCohortWhereLocationProgramAndTypeAreNull() {
		CohortM cohort = dao.getCohort(null, null, null);
		assertThat(cohort, notNullValue());
		assertThat(cohort.getLocation(), is(nullValue()));
		assertThat(cohort.getCohortProgram(), is(nullValue()));
		assertThat(cohort.getCohortType(), is(nullValue()));
	}
}
