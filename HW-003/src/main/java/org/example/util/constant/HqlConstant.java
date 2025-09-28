package org.example.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HqlConstant {

	public static final String FIND_BY_CREATOR_ALL_FAMILY = """
			select distinct f
			from Family f
			left join fetch f.members m
			where f.creator.personId = :personId
			or m.personId = :personId
			""";
	public static final String FIND_BY_ID_FAMILY = "select f from Family f where f.familyId = :familyId";
	public static final String UPDATE_BY_ID_FAMILY = "update Family f set f.familyName = :name WHERE f.familyId = :id";
	public static final String DELETE_BY_ID_FAMILY = "delete from Family f where f.familyId = :familyId AND f.creator.personId= :personId";
}
