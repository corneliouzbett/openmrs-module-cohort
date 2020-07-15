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
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;

import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.cohort.CohortAttribute;
import org.openmrs.module.cohort.api.TestDataUtils;
import org.openmrs.module.cohort.api.TestSpringConfiguration;
import org.openmrs.module.cohort.api.db.hibernate.HibernateCohortDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = TestSpringConfiguration.class, inheritLocations = false)
public class CohortAttributeDaoTest extends BaseModuleContextSensitiveTest {

	private static final String COHORT_ATTRIBUTE_INITIAL_TEST_DATA_XML = "org/openmrs/module/cohort/api/hibernate/db/CohortAttributeDaoTest_initialTestData.xml";

	private static final String COHORT_ATTRIBUTE_UUID = "ddadadd8-8034-4a28-9441-2eb2e7679e10";

	private static final int COHORT_ATTRIBUTE_ID = 1;

	private static final String TEST_COHORT_ATTRIBUTE = "Test cohort attribute";

	private static final int TEST_COHORT_ATTRIBUTE_ID = 200;

	private static final String COHORT_ATTRIBUTE_VALUE = "cohortAttribute";

	private HibernateCohortDAO dao;

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	@Before
	public void setup() throws Exception {
		dao = new HibernateCohortDAO();
		dao.setSessionFactory(sessionFactory);
		executeDataSet(COHORT_ATTRIBUTE_INITIAL_TEST_DATA_XML);
	}

	@Test
	public void getCohortAttributeById_shouldGetCohortAttributeById() {
		CohortAttribute cohortAttribute = dao.getCohortAttributeById(COHORT_ATTRIBUTE_ID);
		assertThat(cohortAttribute, notNullValue());
		assertThat(cohortAttribute.getCohortAttributeId(), equalTo(COHORT_ATTRIBUTE_ID));
	}

	@Test
	public void getCohortAttributeByUuid_shouldGetCohortAttributeByUuid() {
		CohortAttribute cohortAttribute = dao.getCohortAttributeByUuid(COHORT_ATTRIBUTE_UUID);
		assertThat(cohortAttribute, notNullValue());
		assertThat(cohortAttribute.getCohortAttributeId(), equalTo(COHORT_ATTRIBUTE_ID));
		assertThat(cohortAttribute.getUuid(), equalTo(COHORT_ATTRIBUTE_UUID));
	}

	@Test
	public void saveCohortAttributes_shouldCreateNewCohortAttribute() {
		CohortAttribute cohortAttribute = dao.saveCohortAttributes(TestDataUtils.COHORT_ATTRIBUTE());
		assertThat(cohortAttribute, notNullValue());
		assertThat(cohortAttribute.getCohortAttributeId(), notNullValue());
		assertThat(cohortAttribute.getCohortAttributeId(), equalTo(TEST_COHORT_ATTRIBUTE_ID));
		assertThat(cohortAttribute.getValue(), equalTo(COHORT_ATTRIBUTE_VALUE));
	}

	@Test
	public void findCohortAttributes_shouldFindMatchingCohortAttributes() {
		List<CohortAttribute> results = dao.findCohortAttribute(TEST_COHORT_ATTRIBUTE);
		assertThat(results, notNullValue());
		assertThat(results, not(Matchers.<CohortAttribute>empty()));
		assertThat(results, Matchers.<CohortAttribute>hasSize(equalTo(1)));
	}
}
