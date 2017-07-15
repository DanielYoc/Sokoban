package services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import model.SolutionDataMapper;

/**
 * return if exist solution for the given level state hash code
 */
@Path("/IsSolutionExist/{hashCode}")
public class IsSolutionExist {

	@GET
	public boolean isExist(@PathParam("hashCode") int hashCode) {
		SolutionDataMapper dataMapper=new SolutionDataMapper();
		return dataMapper.isExist(hashCode);
	}
}
