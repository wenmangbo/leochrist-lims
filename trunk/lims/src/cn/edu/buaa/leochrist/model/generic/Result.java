package cn.edu.buaa.leochrist.model.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Result<T> {
	private int total;

	private List<T> elements = new ArrayList<T>(0);

	private Iterator iterator;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getElements() {
		if (elements == null || elements.isEmpty()) {

			if (iterator != null) {
				while (iterator.hasNext()) {

					if (elements == null) {
						elements = new ArrayList<T>();
					}

					T element = (T) iterator.next();
					elements.add(element);
				}
			}
		}
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public Iterator getIterator() {
		return iterator;
	}

	public void setIterator(Iterator iterator) {
		this.iterator = iterator;
	}
}
