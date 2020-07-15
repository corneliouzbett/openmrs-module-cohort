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
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.CohortRole;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortRoleDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_ROLE_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortRoleDaoTest_initialTestData.xml";

	private static final String COHORT_ROLE_UUID = "3f9a2479-c14a-4bfc-bcaa-632860258518";

	private static final int COHORT_ROLE_ID = 1;

	private static final String COHORT_ROLE_NAME = "test cohort role";

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_ROLE_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void shouldFindCohortRoleByID() {
		CohortRole cohortRole = dao.getCohortRoleById(COHORT_ROLE_ID);
		assertThat(cohortRole, notNullValue());
		assertThat(cohortRole.getCohortRoleId(), equalTo(COHORT_ROLE_ID));

	}

	@Test
	public void shouldFindCohortRoleByUuid() {
		CohortRole cohortRole = dao.getCohortRoleByUuid(COHORT_ROLE_UUID);
		assertThat(cohortRole, notNullValue());
		assertThat(cohortRole.getUuid(), equalTo(COHORT_ROLE_UUID));

	}

	@Test
	public void shouldFindCohortRoleByName() {
		CohortRole cohortRole = dao.getCohortRoleByName(COHORT_ROLE_NAME);
		assertThat(cohortRole, notNullValue());
		assertThat(cohortRole.getName(), notNullValue());
		assertThat(cohortRole.getName(), equalTo(COHORT_ROLE_NAME));
	}

	@Test
	public void shouldFindAllCohortRoles() {
		List<CohortRole> cohortRoles = dao.findCohortRoles(COHORT_ROLE_NAME);
		assertThat(cohortRoles, notNullValue());
		assertThat(cohortRoles, Matchers.<CohortRole>hasSize(greaterThanOrEqualTo(1)));
		assertThat(cohortRoles.get(0).getName(), equalTo(COHORT_ROLE_NAME));
		assertThat(cohortRoles.get(0).getUuid(), equalTo(COHORT_ROLE_UUID));
	}
}
