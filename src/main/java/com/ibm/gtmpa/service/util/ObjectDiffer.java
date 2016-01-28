package com.ibm.gtmpa.service.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;

public class ObjectDiffer {

	List<String> ignoreFields;
	List<String> markOnlyFields;

	public ObjectDiffer(List<String> ignoreFields, List<String> markOnlyFields) {
		this.ignoreFields = ignoreFields;
		this.markOnlyFields = markOnlyFields;

		if (this.ignoreFields == null)
			this.ignoreFields = new ArrayList<String>();

		if (this.markOnlyFields == null)
			this.markOnlyFields = new ArrayList<String>();

	}

	public ObjectDiffer() {
		this.ignoreFields = new ArrayList<String>();
		this.markOnlyFields = new ArrayList<String>();

	}

	// Compares the properties of two objects and returns a diff string
	public String diff(Object obj1, Object obj2) {
		String diff = "";

		BeanMap map = new BeanMap(obj1);

		for (Iterator<String> it = map.keyIterator(); it.hasNext();) {
			String key = it.next();

			if (!ignoreFields.contains(key)) {

				String val1 = "";
				String val2 = "";

				try {
					Object val1Obj = PropertyUtils.getProperty(obj1, key);
					Object val2Obj = PropertyUtils.getProperty(obj2, key);
					if (val1Obj != null) {
						val1 = val1Obj.toString();
					}

					if (val2Obj != null) {
						val2 = val2Obj.toString();
					}
				} catch (Exception e) {
					// bad things have happened, ignore as we're trying to work
					// out the difference
					System.out.print(e);
				}

				if (!val1.equals(val2)) {
					if (diff.length() > 0) {
						diff = diff + ", ";
					}
					if (markOnlyFields.contains(key)) {
						diff = diff + key + " [changed]";
					} else {
						diff = diff + key + " [" + val1 + "]->[" + val2 + "]";
					}
				}
			}
		}
		return diff;
	}
}
