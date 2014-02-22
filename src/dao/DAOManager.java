package dao;

public class DAOManager {
	
	public static GenericDAO getDAO(DAOType type){
		switch (type){
		case COURSE :
			return new CourseDAO();
		case OFFERING :
			return new OfferingDAO();
		default:
			return null;
		}
	}
	

}
