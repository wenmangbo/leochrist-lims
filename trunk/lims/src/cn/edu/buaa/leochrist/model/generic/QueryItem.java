package cn.edu.buaa.leochrist.model.generic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryItem {
	private String fieldName;

	private String keyword;

	private String queryType = QueryType.AND;

	private List<QueryItem> queryItems = new ArrayList<QueryItem>();

	public QueryItem() {

	}

	public QueryItem(String fieldName, String keyword, String queryType) {
		super();
		this.fieldName = fieldName;
		this.keyword = keyword;
		this.queryType = queryType;
	}

	public static class QueryType {
		public static String EQ = "==",NOT_EQ="!=", BIGGER = ">", SMALLER = "<",
				BIGGER_OR_EQ = ">=", SMALLER_OR_EQ = "<=", OR = "||",
				AND = "&&", NOT = "!",IS="is", LIKE = "like",START_WITH="startWith",END_WITH="endWith",CONTAINS="contains",NOT_LIKE="!like";

		private final static Map<String,String> map = new HashMap<String,String>();

		static{
			map.put("eq", EQ);
			map.put("gt", BIGGER);
			map.put("lt", SMALLER);
			map.put("le", SMALLER_OR_EQ);
			map.put("ge", BIGGER_OR_EQ);
			map.put("bw", START_WITH);
			map.put("in", LIKE);
			map.put("ni", NOT_LIKE);
		}

		public static String get(String key){
			return map.get(key);
		}

	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public void addSubQueryItem(QueryItem queryItem){
		if(this.queryItems==null){
			queryItems = new ArrayList<QueryItem>(0);
		}
		queryItems.add(queryItem);

	}
	public List<QueryItem> getQueryItems() {
		return queryItems;
	}
	public void setQueryItems(List<QueryItem> queryItems) {
		this.queryItems = queryItems;
	}

}
