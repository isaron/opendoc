package com.cloud.platform;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtil {

	public static Set convertListToSet(List list) {
		Set set = new HashSet();

		for(Object o : list) {
			set.add(o);
		}
		
		return set;
	}
}
