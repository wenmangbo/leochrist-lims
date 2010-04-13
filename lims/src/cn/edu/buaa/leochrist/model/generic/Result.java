package cn.edu.buaa.leochrist.model.generic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Result<T> {
	private int total;

	private List<T> elements = new ArrayList<T>(0);

	@SuppressWarnings("unchecked")
	private Iterator iterator;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public Iterator getIterator() {
		return iterator;
	}

	@SuppressWarnings("unchecked")
	public void setIterator(Iterator iterator) {
		this.iterator = iterator;
	}
}
