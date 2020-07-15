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

import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.CohortLeader;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortLeaderDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_LEADER_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortLeaderDaoTest_initialTestData.xml";

	private static final String COHORT_LEADER_UUID = "3f9a2479-c14a-4bfc-bcaa-632860258518";

	private static final int COHORT_LEADER_ID = 123;

	private static final int COHORT_ID = 1;

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_LEADER_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void shouldGetCohortLeaderByUuid() {
		CohortLeader cohortLeader = dao.getCohortLeaderByUuid(COHORT_LEADER_UUID);
		assertThat(cohortLeader, notNullValue());
		assertThat(cohortLeader.getUuid(), notNullValue());
		assertThat(cohortLeader.getUuid(), equalTo(COHORT_LEADER_UUID));
	}

	@Test
	public void shouldGetCohortLeaderById() {
		CohortLeader cohortLeader = dao.getCohortLeaderById(COHORT_LEADER_ID);
		assertThat(cohortLeader, notNullValue());
		assertThat(cohortLeader.getCohortLeaderId(), notNullValue());
		assertThat(cohortLeader.getCohortLeaderId(), equalTo(COHORT_LEADER_ID));
	}

	@Test
	public void shouldGetCohortLeadersByCohortId() {
		List<CohortLeader> cohortLeader = dao.getCohortLeadersByCohortId(COHORT_ID);
		assertThat(cohortLeader, notNullValue());
		assertThat(cohortLeader, Matchers.<CohortLeader>hasSize(greaterThanOrEqualTo(1)));
		assertThat(cohortLeader.get(0).getCohort().getCohortId(), equalTo(COHORT_ID));
	}

	@Test
	public void shouldCreateCohortLeader() {
		CohortLeader cohortLeader = new CohortLeader();
		cohortLeader.setUuid("1ui45-89jk3j834-nj393-323434k934");
		cohortLeader.setCohortLeaderId(321);
		cohortLeader.setStartDate(new Date());
		CohortLeader result = dao.saveCohortLeader(cohortLeader);
		assertThat(result, notNullValue());
		assertThat(result.getId(), equalTo(321));

		CohortLeader savedCohortLeader = dao.getCohortLeaderById(321);
		assertThat(savedCohortLeader, notNullValue());
		assertThat(savedCohortLeader, equalTo(cohortLeader));
	}
}
