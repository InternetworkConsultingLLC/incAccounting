package net.internetworkconsulting.rest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import net.internetworkconsulting.bootstrap.entities.User;

public class Request {
	private Object objModel = null;
	public Object getModel() { return objModel; }
	public void setModel(Object value) { objModel = value; }

	private String sContext = "";
	public String getContext() { return sContext; }
	public void setContext(String value) { sContext = value; }
	
	private String sFunction = "";
	public String getFunction() { return sFunction; }
	public void setFunction(String value) { sFunction = value; }
	
	private Object[] arrParameters = new Object[0];
	public Object[] getParameters() { return arrParameters; }
	public void setParameters(Object[] value) { arrParameters = value; }
	
	public Response execute(User user) throws Exception {
		if(sFunction == null || sFunction.length() < 1)
			throw new Exception("You must provide a method name, or 'new' to create a new object!");
		
		Class cls = null;
		if(objModel != null)
			cls = objModel.getClass();
		else if(sContext != null)
			cls = Class.forName(sContext);
		else
			throw new Exception("You must provide a model or context for which to execute the function on!");
		
		Method method = getMethod(user, cls);
		Object[] arrArgs = getArguments(user, method);
		Object ret = method.invoke(objModel, arrArgs);
		
		Response response = new Response();
		response.setModel(objModel);
		response.setValue(ret);
		return response;
	}
	
	private Method getMethod(User user, Class cls) throws Exception {
		for(Method method : cls.getMethods()) {
			if(method.getName().equals(getFunction())) {
				LinkedList<Parameter> lstConsolidatedArgs = new LinkedList<>();
				for(Parameter arg : method.getParameters()) {
					if( !net.internetworkconsulting.data.AdapterInterface.class.isAssignableFrom(arg.getType()) )
						lstConsolidatedArgs.add(arg);
				}
				
				if(lstConsolidatedArgs.size() != arrParameters.length)
					continue;
				
				boolean bFound = true;
				for(int cnt = 0; cnt < lstConsolidatedArgs.size(); cnt++) {
					if(
						arrParameters[cnt] != null && !lstConsolidatedArgs.get(cnt).getType().isAssignableFrom(arrParameters[cnt].getClass()) && 
						!(lstConsolidatedArgs.get(cnt).getType().getName().toLowerCase().contains("boolean") && arrParameters[cnt].getClass().getName().toLowerCase().contains("boolean"))
					)
						bFound = false;
				}
				
				if(bFound)
					return method;
			}
		}
		
		throw new Exception("Could not locate a method to fulfill this request!");
	}
	private Object[] getArguments(User user, Method method) throws Exception {
		// the arrParameters matchs the methods except AdapterInterfaces are missing.
		// Insert adapter interfaces into the function call's arguments where needed.
		
		int cnt = 0;
		LinkedList<Object> lstArgs = new LinkedList<>();
		for(Parameter arg : method.getParameters()) {
			if(net.internetworkconsulting.data.AdapterInterface.class.isAssignableFrom(arg.getType()))
				lstArgs.add(user.login());
			else
				lstArgs.add(arrParameters[cnt++]);				
		}
		
		return lstArgs.toArray();
	}
}
