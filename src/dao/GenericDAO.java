package dao;

import java.util.List;


public interface GenericDAO {
	
	public List<Object> getAll();
	public Object findByName(String name);
	public void update(Object object);
	public void delete(Object object);
	public void create(Object object);

}
