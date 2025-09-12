package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HqlConstant {
	public static final String FIND_BY_CREATOR_ALL_FAMILY = "select f from Family f where f.creator.personId = :personId";
	public static final String FIND_BY_ID_FAMILY = "select f from Family f where f.familyId = :familyId";
	public static final String UPDATE_BY_ID_FAMILY = "update Family f set f.name = :name WHERE f.familyId = :id";
	public static final String DELETE_BY_ID_FAMILY = "delete from Family f where f.familyId = :familyId AND f.creator.personId= :personId";
}