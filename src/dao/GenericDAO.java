package dao;

import java.util.List;

import entity.Course;

public interface GenericDAO {
	
	public List<Object> getAll();
	public void update(Course course);
	public void delete(Course course);
	public void create(Course course);

}
