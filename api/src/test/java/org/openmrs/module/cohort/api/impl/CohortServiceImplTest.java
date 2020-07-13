/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.cohort.api.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openmrs.VisitType;
import org.openmrs.module.cohort.CohortAttribute;
import org.openmrs.module.cohort.CohortAttributeType;
import org.openmrs.module.cohort.CohortEncounter;
import org.openmrs.module.cohort.CohortM;
import org.openmrs.module.cohort.CohortMember;
import org.openmrs.module.cohort.CohortType;
import org.openmrs.module.cohort.CohortVisit;
import org.openmrs.module.cohort.api.db.CohortDAO;

@RunWith(MockitoJUnitRunner.class)
public class CohortServiceImplTest {

	private static final int COHORT_TYPE_ID = 123;

	private static final String TEST_COHORT = "test cohort";

	private static final String TEST_ATTRIBUTE_TYPE = "Test attribute type";

	private static final String COHORT_ENCOUNTER_UUID = "e4102924-1a73-4b34-9c74-0278975ffab5";

	private static final String AGE = "Age";

	private static final String TEST_ATT = "test-att";

	private static final String TEST_ATTRIBUTES = "Test attributes";

	private static final int COHORT_VISIT_ID = 100;

	private static final int COHORT_ID = 203;

	private static final int VISIT_TYPE_ID = 340;

	private static final String COHORT_UUID = "5c28c01f-199a-48c7-8693-2a504dd1f4ab";

	private static final String COHORT_ATTRIBUTE_UUID = "06036a52-cf51-4182-9283-dedb15fea65a";

	private static final String COHORT_MEMBER_UUID = "4944bff6-cba2-472e-8b55-3a6c64b9002f";

	private static final String COHORT_TYPE_UUID = "96eebbd7-18c1-4328-9d3d-a5fafd17b186";

	private static final String COHORT_VISIT_UUID = "93ab8fd4-22ce-43dc-8256-f659ed64cbcc";

	@Mock
	private CohortDAO dao;

	private CohortType cohortType;

	private CohortServiceImpl cohortService;

	@Before
	public void setup() {
		cohortService = new CohortServiceImpl();
		cohortService.setDao(dao);
	}

	@Before
	public void initData() {
		cohortType = new CohortType();
		cohortType.setId(1);
		cohortType.setCohortTypeId(COHORT_TYPE_ID);
		cohortType.setName(TEST_COHORT);
	}

	@Test
	public void saveCohortMember_shouldSaveCohortMember() {
		CohortMember member = new CohortMember();
		member.setId(1);
		when(dao.saveCPatient(member)).thenReturn(member);
		CohortMember result = cohortService.saveCohortMember(member);
		assertThat(result, notNullValue());
		assertThat(result.getId(), equalTo(1));
	}

	@Test
	public void saveCohort_shouldSaveCohort() {
		CohortM cohortM = new CohortM();
		cohortM.setCohortId(12);
		cohortM.setCohortType(cohortType);
		when(dao.saveCohort(cohortM)).thenReturn(cohortM);
		CohortM result = cohortService.saveCohort(cohortM);
		assertThat(result, notNullValue());
		assertThat(result.getId(), equalTo(12));
	}

	@Test
	public void getCohortTypeById_shouldGetCohortType() {
		when(dao.getCohortType(COHORT_TYPE_ID)).thenReturn(cohortType);

		CohortType result = cohortService.getCohortTypeById(COHORT_TYPE_ID);
		assertThat(result , notNullValue());
		assertThat(result.getCohortTypeId(), equalTo(COHORT_TYPE_ID));
		assertThat(result.getName(), equalTo(TEST_COHORT));
	}

	@Test
	public void getAllCohortTypes_shouldGetAllCohortTypes() {
		when(dao.getAllCohortTypes()).thenReturn(Collections.singletonList(cohortType));
		List<CohortType> resultList = cohortService.getAllCohortTypes();
		assertThat(resultList, notNullValue());
		assertThat(resultList, Matchers.<CohortType>hasSize(greaterThanOrEqualTo(1)));
	}

	@Test
	public void getAllCohortAttributeTypes_shouldGetAllCohortAttributeTypes() {
		CohortAttributeType attributeType = mock(CohortAttributeType.class);
		when(attributeType.getCohortAttributeTypeId()).thenReturn(123);
		when(dao.getAllCohortAttributes()).thenReturn(Collections.singletonList(attributeType));
		List<CohortAttributeType> resultList = cohortService.getAllCohortAttributeTypes();
		assertThat(resultList, notNullValue());
		assertThat(resultList.size(), equalTo(1));
		assertThat(resultList.get(0).getCohortAttributeTypeId(), equalTo(123));
	}

	@Test
	public void getCohortAttributeTypeByName_shouldGetCohortAttributeTypeByName() {
		CohortAttributeType attributeType = mock(CohortAttributeType.class);
		when(attributeType.getCohortAttributeTypeId()).thenReturn(123);
		when(attributeType.getName()).thenReturn(TEST_ATTRIBUTE_TYPE);
		when(dao.findCohortAttributes(TEST_ATTRIBUTE_TYPE)).thenReturn(attributeType);
		CohortAttributeType result = cohortService.getCohortAttributeTypeByName(TEST_ATTRIBUTE_TYPE);
		assertThat(result, notNullValue());
		assertThat(result.getName(), equalTo(TEST_ATTRIBUTE_TYPE));
		assertThat(result.getCohortAttributeTypeId(), equalTo(123));
	}

	@Test
	public void saveCohortEncounter_shouldCreateCohortEncounter() {
		CohortEncounter cohortEncounter = mock(CohortEncounter.class);
		when(cohortEncounter.getUuid()).thenReturn(COHORT_ENCOUNTER_UUID);
		when(dao.saveCohortEncounters(cohortEncounter)).thenReturn(cohortEncounter);
		CohortEncounter result = cohortService.saveCohortEncounter(cohortEncounter);
		assertThat(result, notNullValue());
		assertThat(result.getUuid(), equalTo(COHORT_ENCOUNTER_UUID));
	}

	@Test
	public void getAllCohorts_shouldGetAllCohorts() {
		CohortM cohort = mock(CohortM.class);
		when(cohort.getCohortId()).thenReturn(12);
		when(cohort.getCohortType()).thenReturn(cohortType);
		when(dao.findCohorts()).thenReturn(Collections.singletonList(cohort));
		List<CohortM> resultList = cohortService.getAllCohorts();
		assertThat(resultList, notNullValue());
		assertThat(resultList.get(0).getCohortId(), equalTo(12));
		assertThat(resultList.get(0).getCohortType(), equalTo(cohortType));
	}

	@Test
	public void findCohortsMatching_shouldFindMatchingCohort() {
		CohortM cohort = mock(CohortM.class);
		when(cohort.getCohortId()).thenReturn(12);
		when(cohort.getCohortType()).thenReturn(cohortType);
		Map<String, String> attributes = new HashMap<>();
		attributes.put(TEST_ATT, TEST_ATTRIBUTES);
		when(dao.findCohorts(AGE, attributes, cohortType)).thenReturn(Collections.singletonList(cohort));
		List<CohortM> resultList = cohortService.findCohortsMatching(AGE, attributes, cohortType);
		assertThat(resultList, notNullValue());
		assertThat(resultList.get(0).getCohortId(), equalTo(12));
		assertThat(resultList.get(0).getCohortType(), equalTo(cohortType));
	}

	@Test
	public void saveCohortAttribute_shouldCreateCohortAttribute() {
		CohortAttribute cohortAttribute = mock(CohortAttribute.class);
		when(cohortAttribute.getCohortAttributeId()).thenReturn(345);
		when(dao.saveCohortAttributes(cohortAttribute)).thenReturn(cohortAttribute);
		CohortAttribute attribute = cohortService.saveCohortAttribute(cohortAttribute);
		assertThat(attribute, notNullValue());
		assertThat(attribute.getCohortAttributeId(), equalTo(345));
	}

	@Test
	public void saveCohortVisit_shouldCreateCohortVisit() {
		CohortVisit cohortVisit = mock(CohortVisit.class);
		when(cohortVisit.getCohortVisitId()).thenReturn(COHORT_VISIT_ID);
		when(dao.saveCohortVisit(cohortVisit)).thenReturn(cohortVisit);
		CohortVisit result = cohortService.saveCohortVisit(cohortVisit);
		assertThat(result, notNullValue());
		assertThat(result.getCohortVisitId(), equalTo(COHORT_VISIT_ID));
	}

	@Test
	public void getCohortById_shouldGetCohortById() {
		CohortM cohortM = mock(CohortM.class);
		when(cohortM.getCohortId()).thenReturn(COHORT_ID);
		when(dao.getCohortMById(COHORT_VISIT_ID)).thenReturn(cohortM);
		CohortM result = cohortService.getCohortById(COHORT_VISIT_ID);
		assertThat(result, notNullValue());
		assertThat(result.getCohortId(), equalTo(COHORT_ID));
	}

	@Test
	public void getCohortByUuid_shouldGetCohortByUuid() {
		CohortM cohortM = mock(CohortM.class);
		when(cohortM.getUuid()).thenReturn(COHORT_UUID);
		when(dao.getCohortUuid(COHORT_UUID)).thenReturn(cohortM);
		CohortM result = cohortService.getCohortByUuid(COHORT_UUID);
		assertThat(result, notNullValue());
		assertThat(result.getUuid(), equalTo(COHORT_UUID));
	}

	@Test
	public void getCohortVisitByType_shouldReturnCohortVisit() {
		CohortVisit cohortVisit = mock(CohortVisit.class);
		VisitType visitType = mock(VisitType.class);
		when(visitType.getVisitTypeId()).thenReturn(VISIT_TYPE_ID);
		when(cohortVisit.getVisitType()).thenReturn(visitType);
		when(dao.findCohortVisitByVisitType(VISIT_TYPE_ID)).thenReturn(Collections.singletonList(cohortVisit));
		List<CohortVisit> resultList = cohortService.getCohortVisitByType(VISIT_TYPE_ID);
		assertThat(resultList, notNullValue());
		assertThat(resultList.get(0).getVisitType().getVisitTypeId(), equalTo(VISIT_TYPE_ID));
	}

	@Test
	public void getCohortAttributeByUuid_shouldGetCohortAttributeByUuid() {
		CohortAttribute cohortAttribute = mock(CohortAttribute.class);
		when(cohortAttribute.getCohortAttributeId()).thenReturn(12);
		when(cohortAttribute.getUuid()).thenReturn(COHORT_ATTRIBUTE_UUID);
		when(dao.getCohortAttributeByUuid(COHORT_ATTRIBUTE_UUID)).thenReturn(cohortAttribute);
		CohortAttribute result = cohortService.getCohortAttributeByUuid(COHORT_ATTRIBUTE_UUID);
		assertThat(result, notNullValue());
		assertThat(result.getCohortAttributeId(), equalTo(12));
		assertThat(result.getUuid(), equalTo(COHORT_ATTRIBUTE_UUID));
	}

	@Test
	public void getCohortEncounterByUuid_shouldGetCohortEncounterByUuid() {
		CohortEncounter cohortEncounter = mock(CohortEncounter.class);
		when(cohortEncounter.getUuid()).thenReturn(CohortServiceImplTest.COHORT_ENCOUNTER_UUID);
		when(dao.getCohortEncounterUuid(COHORT_ENCOUNTER_UUID)).thenReturn(cohortEncounter);
		CohortEncounter result = cohortService.getCohortEncounterByUuid(COHORT_ENCOUNTER_UUID);
		assertThat(result, notNullValue());
		assertThat(result.getUuid(), equalTo(COHORT_ENCOUNTER_UUID));
	}

	@Test
	public void getCohortMemberByUuid_shouldGetCohortMemberByUuid() {
		CohortMember cohortMember = mock(CohortMember.class);
		when(cohortMember.getUuid()).thenReturn(COHORT_MEMBER_UUID);
		when(dao.getCohortMemUuid(COHORT_MEMBER_UUID)).thenReturn(cohortMember);
		CohortMember result = cohortService.getCohortMemberByUuid(COHORT_MEMBER_UUID);
		assertThat(result, notNullValue());
		assertThat(result.getUuid(), equalTo(COHORT_MEMBER_UUID));
	}

	@Test
	public void getCohortTypeByUuid_shouldGetCohortTypeByUuid() {
		CohortType cohortType = mock(CohortType.class);
		when(cohortType.getUuid()).thenReturn(COHORT_TYPE_UUID);
		when(dao.getCohortTypeByUuid(COHORT_TYPE_UUID)).thenReturn(cohortType);
		CohortType result = cohortService.getCohortTypeByUuid(COHORT_TYPE_UUID);
		assertThat(result, notNullValue());
		assertThat(result.getUuid(), equalTo(COHORT_TYPE_UUID));
	}

	@Test
	public void getCohortVisitByUuid_shouldGetCohortVisitByUuid() {
		CohortVisit cohortVisit = mock(CohortVisit.class);
		when(cohortVisit.getUuid()).thenReturn(COHORT_VISIT_UUID);
		when(dao.getCohortVisitUuid(COHORT_VISIT_UUID)).thenReturn(cohortVisit);
		CohortVisit result = cohortService.getCohortVisitByUuid(COHORT_VISIT_UUID);
		assertThat(result, notNullValue());
		assertThat(result.getUuid(), equalTo(COHORT_VISIT_UUID));
	}

	@Test
	public void getCohortsByLocationId_shouldGetCohortsByLocationId() {
		CohortM cohort = mock(CohortM.class);
		when(cohort.getCohortId()).thenReturn(12);
		when(cohort.getCohortType()).thenReturn(cohortType);
		when(dao.getCohortsByLocationId(2)).thenReturn(Collections.singletonList(cohort));
		List<CohortM> resultList = cohortService.getCohortsByLocationId(2);
		assertThat(resultList, notNullValue());
		assertThat(resultList.get(0).getCohortId(), equalTo(12));
		assertThat(resultList.get(0).getCohortType(), equalTo(cohortType));
	}
}
