package dao;

import java.util.Collection;
import java.util.List;

import entity.Offering;


public interface GenericDAO {
	
	public Collection<Object> getAll();
	public Object findByName(String name);
	public void update(Object object);
	public void delete(Object object);
	public void create(Object object);

}
