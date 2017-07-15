package model.data;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import model.serverProtocol.SolutionRespone;

public class SolutionsGateway {

	private String m_Address;

	public SolutionsGateway(String serverAddress) {
		m_Address = serverAddress;
	}

	public boolean isExist(int hashCode) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://" + m_Address + "/SolutionsServer/IsSolutionExist/" + hashCode);
		Invocation.Builder invocationBuilder = webTarget.request();

		Response response = invocationBuilder.get();
		String message = response.readEntity(String.class);
		return Boolean.parseBoolean(message);
	}

	public SolutionRespone getSolution(int levelHashCode) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client
				.target("http://" + m_Address + "/SolutionsServer/SolutionsProvider/" + levelHashCode);
		Invocation.Builder invocationBuilder = webTarget.request();

		Response response = invocationBuilder.get();
		String message = response.readEntity(String.class);
		String[] split = message.split(",");

		SolutionRespone respone = new SolutionRespone();
		respone.setSolvable(Boolean.parseBoolean(split[0]));
		if (respone.getSolvable()) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 1; i < split.length; i++)
				list.add(split[i]);
			String[] arr = new String[list.size()];
			list.toArray(arr);
			respone.setActions(arr);
		}
		return respone;
	}

	public void saveSolution(int levelHashCode, boolean canSolved, String[] actionsCommand) {
		StringBuilder builder = new StringBuilder();
		builder.append(levelHashCode).append(",").append(canSolved);
		if (actionsCommand != null)
			for (String ac : actionsCommand) {
				builder.append(",");
				builder.append(ac);
			}
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client
				.target("http://" + m_Address + "/SolutionsServer/SolutionSave/" + builder.toString());
		Invocation.Builder invocationBuilder = webTarget.request();

		Response response = invocationBuilder.get();
		String message = response.readEntity(String.class);
	}
}
