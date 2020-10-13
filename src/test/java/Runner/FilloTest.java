package Runner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class FilloTest {

	static HashMap<String, String> map = new LinkedHashMap<String, String>();
	static HashMap<String, String> childmap = new LinkedHashMap<String, String>();
	static LinkedList<String> list = new LinkedList<String>();
	public static void main(String[] args) throws Exception {

		String query ="Select * from Sheet1";
		Fillo fillo = new Fillo();
		Connection con = fillo.getConnection("./src/test/resources/TestData.xlsx");
		Recordset rs = con.executeQuery(query);
		list.addAll(rs.getFieldNames());
		int count =1;
		while(rs.next())
		{ 
			
			for(String s:list)
			{
				map.put(s+String.valueOf(count), rs.getField(s));
				
			}
			count++;
		}
		for(Map.Entry m:map.entrySet())
		{
			String key = m.getValue().toString();
			
			if(key.equals("searchIpad"))
			{
				String key2 =m.getKey().toString();
				String index = key2.replaceAll("[^0-9]", "");
				for(String s: list)
				{
					childmap.put(s, map.get(s+index));
				}
			}
		}
		for(Map.Entry m:childmap.entrySet())
		{
			System.out.println(m.getKey()+" "+m.getValue());
		}
			

	}

}
