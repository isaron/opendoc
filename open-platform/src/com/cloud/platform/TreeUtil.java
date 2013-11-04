package com.cloud.platform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cloud.platform.Tree;

public class TreeUtil {
	
	/**
	 * entrance
	 * 
	 * @param datas
	 */
	public static List sortTree(List<? extends Tree> datas) {
		
		// get parent set
		Set<String> parentSet = new HashSet();
		
		for(Tree t : datas) {
			parentSet.add(t.getParentId());
		}
		
		List<Tree> result = new ArrayList();
		sortTree(result, datas, parentSet, null, 0);
		
		return result;
	}

	/**
	 * sort data tree
	 * 
	 * @param result
	 * @param datas
	 * @param parent
	 */
	private static void sortTree(List<Tree> result, List<? extends Tree> allDatas,
			Set<String> parentSet, String parentId, int level) {

		List<Tree> childs = getChilds(allDatas, parentSet, parentId);

		for (Tree data : childs) {
			data.setLevel(level);
			data.setPad(level * 18 + 5);

			result.add(data);

			// if has child, recurse to add its children
			if (data.isHasChild()) {
				sortTree(result, allDatas, parentSet, data.getId(), level + 1);
			}
		}
	}

	/**
	 * get data childs
	 * 
	 * @param allDatas
	 * @param parent
	 * @return
	 */
	private static List<Tree> getChilds(List<? extends Tree> allDatas,
			Set<String> parentSet, String parentId) {

		List<Tree> childs = new ArrayList();

		for (Tree data : allDatas) {
			
			// add child
			if ((parentId == null && StringUtil.isNullOrEmpty(data.getParentId()))
					|| (parentId != null && parentId.equals(data.getParentId()))) {
				
				// set if has child
				if (parentSet.contains(data.getId())) {
					data.setHasChild(true);
				}

				childs.add(data);
			}
		}

		return childs;
	}
}
