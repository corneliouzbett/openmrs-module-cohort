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
import static org.hamcrest.Matchers.nullValue;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.CohortProgram;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortProgramDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_PROGRAM_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortProgramDaoTest_initialTestData.xml";

	private static final String COHORT_PROGRAM_UUID = "94517bf9-d8d7-4726-b4f1-a2dff6b36e2d";

	private static final String BAD_COHORT_PROGRAM_UUID = "xx0a2479-c55a-4bfc-bb99-632860xx8518";

	private static final int COHORT_PROGRAM_ID = 102;

	private static final int BAD_COHORT_PROGRAM_ID = -1;

	private static final String COHORT_PROGRAM_NAME = "cohort program name";

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_PROGRAM_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void shouldFindCohortProgramByID() {
		CohortProgram cohortProgram = dao.getCohortProgramById(COHORT_PROGRAM_ID);
		assertThat(cohortProgram, notNullValue());
		assertThat(cohortProgram.getCohortProgramId(), equalTo(COHORT_PROGRAM_ID));
		assertThat(cohortProgram.getName(), equalTo(COHORT_PROGRAM_NAME));

	}

	@Test
	public void shouldFindCohortProgramByUuid() {
		CohortProgram cohortProgram = dao.getCohortProgramByUuid(COHORT_PROGRAM_UUID);
		assertThat(cohortProgram, notNullValue());
		assertThat(cohortProgram.getUuid(), notNullValue());
		assertThat(cohortProgram.getName(), equalTo(COHORT_PROGRAM_NAME));
		assertThat(cohortProgram.getUuid(), equalTo(COHORT_PROGRAM_UUID));

	}

	@Test
	public void shouldReturnNullWhenFindCohortProgramByBadUuid() {
		CohortProgram cohortProgram = dao.getCohortProgramByUuid(BAD_COHORT_PROGRAM_UUID);
		assertThat(cohortProgram, nullValue());

	}

	@Test
	public void shouldReturnNullWhenFindCohortProgramByBadId() {
		CohortProgram cohortProgram = dao.getCohortProgramById(BAD_COHORT_PROGRAM_ID);
		assertThat(cohortProgram, nullValue());

	}
}
